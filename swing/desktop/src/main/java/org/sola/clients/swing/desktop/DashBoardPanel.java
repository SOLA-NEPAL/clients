/**
 * ******************************************************************************************
 * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations
 * (FAO). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,this
 * list of conditions and the following disclaimer. 2. Redistributions in binary
 * form must reproduce the above copyright notice,this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. 3. Neither the name of FAO nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT,STRICT LIABILITY,OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * *********************************************************************************************
 */
package org.sola.clients.swing.desktop;

import java.awt.ComponentOrientation;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;
import org.sola.clients.beans.application.ApplicationBean;
import org.sola.clients.beans.application.ApplicationSearchResultBean;
import org.sola.clients.beans.application.ApplicationSearchResultsListBean;
import org.sola.clients.beans.application.ApplicationSummaryBean;
import org.sola.clients.beans.security.SecurityBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.desktop.application.ApplicationPanel;
import org.sola.clients.swing.desktop.application.ApplicationAssignmentPanel;
import org.sola.clients.swing.desktop.application.ApplicationTransferForm;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.MainContentPanel;
import org.sola.clients.swing.ui.renderers.BooleanCellRenderer;
import org.sola.clients.swing.ui.renderers.DateTimeRenderer;
import org.sola.common.RolesConstants;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * This panel displays assigned and unassigned applications.<br />
 * {@link ApplicationSummaryListBean} is used to bind the data on the panel.
 */
public class DashBoardPanel extends ContentPanel {

    private class AssignmentPanelListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent e) {
            refreshApplications();
        }
    }
    private AssignmentPanelListener assignmentPanelListener;

    /**
     * Panel constructor.
     *
     * @param mainForm Parent form.
     */
    public DashBoardPanel() {
        assignmentPanelListener = new AssignmentPanelListener();
        initComponents();
        postInit();
    }

    /**
     * Runs post initialization tasks to add listeners.
     */
    private void postInit() {
        tbApplications.setColumnSelectionAllowed(false);
        tbApplications.setCellSelectionEnabled(false);
        tbApplications.setRowSelectionAllowed(true);
        boolean canAssign = SecurityBean.isInRole(RolesConstants.APPLICATION_ASSIGN_TO_ALL)
                    || SecurityBean.isInRole(RolesConstants.APPLICATION_ASSIGN_TO_DEPARTMENT);
        
        btnRefresh.setEnabled(SecurityBean.isInRole(RolesConstants.APPLICATION_VIEW_APPS));
        cbxShowOnlyMy.setVisible(canAssign);
        separator1.setVisible(canAssign);
        menuRefreshApplication.setEnabled(btnRefresh.isEnabled());

        refreshApplications();
        customizeAssignedAppButtons(null);

        appListBean.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                customizeAssignedAppButtons(evt);
            }
        });
    }

    /**
     * Enables or disables toolbar buttons for applications list, .
     */
    private void customizeAssignedAppButtons(PropertyChangeEvent evt) {
        if (evt == null) {
            btnAssignApplication.setEnabled(false);
            btnTransferApplications.setEnabled(false);
            btnOpenApplication.setEnabled(false);
            menuOpenApplication.setEnabled(false);
            menuAssignApplication.setEnabled(false);
            menuTransferApplication.setEnabled(false);
            return;
        }
        
        switch (evt.getPropertyName()) {
            case ApplicationSearchResultsListBean.CHECKED_APPLICATION_PROPERTY:
                boolean canAssign = SecurityBean.isInRole(RolesConstants.APPLICATION_ASSIGN_TO_ALL)
                        || SecurityBean.isInRole(RolesConstants.APPLICATION_ASSIGN_TO_DEPARTMENT);
                if (canAssign) {
                    canAssign = (Boolean) evt.getNewValue();
                }
                btnAssignApplication.setEnabled(canAssign);
                btnTransferApplications.setEnabled(canAssign);
                menuAssignApplication.setEnabled(btnAssignApplication.isEnabled());
                menuTransferApplication.setEnabled(btnTransferApplications.isEnabled());
                break;
            case ApplicationSearchResultsListBean.SELECTED_APPLICATION_PROPERTY:
                boolean isEditEnabled;
                ApplicationSearchResultBean app = (ApplicationSearchResultBean) evt.getNewValue();
                if (app == null) {
                    isEditEnabled = false;
                } else {
                    isEditEnabled = SecurityBean.isInRole(RolesConstants.APPLICATION_EDIT_APPS);
                }
                btnOpenApplication.setEnabled(isEditEnabled);
                menuOpenApplication.setEnabled(isEditEnabled);
                break;
        }
    }

    /**
     * Opens application assignment.
     */
    private void openAssignmentForm() {
        for (ApplicationSearchResultBean searchResult : appListBean.getCheckedApplications()) {
            if (!searchResult.isFeePaid()) {
                MessageUtility.displayMessage(ClientMessage.CHECK_FEES_NOT_PAID);
                return;
            }
        }

        SolaTask t = new SolaTask<Void, Void>() {

            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_APPASSIGN));
                ApplicationAssignmentPanel panel = new ApplicationAssignmentPanel(appListBean.getCheckedApplications());
                panel.addPropertyChangeListener(ApplicationAssignmentPanel.APPLICATIONS_ASSGINED, assignmentPanelListener);
                getMainContentPanel().addPanel(panel, MainContentPanel.CARD_APPASSIGNMENT, true);
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }
    
    /** Opens application transfer form */
    private void openTransferForm(){
        SolaTask t = new SolaTask<Void, Void>() {

            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_APP_TRANSFER));
                ApplicationTransferForm panel = new ApplicationTransferForm(appListBean.getCheckedApplications());
                panel.addPropertyChangeListener(ApplicationTransferForm.APPLICATIONS_TRANSFERED, assignmentPanelListener);
                getMainContentPanel().addPanel(panel, MainContentPanel.CARD_APPTRANSFER, true);
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }

    /**
     * Opens application form.
     *
     * @param appBean Selected application summary bean.
     */
    private void openApplication(final ApplicationSummaryBean appBean) {
        if (appBean == null || getMainContentPanel() == null) {
            return;
        }

        SolaTask t = new SolaTask<Void, Void>() {

            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_APP));
                PropertyChangeListener listener = new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent e) {
                        if (e.getPropertyName().equals(ApplicationPanel.APPLICATION_SAVED_PROPERTY)) {
                            refreshApplications();
                        }
                    }
                };

                if (getMainContentPanel() != null) {
                    ApplicationPanel applicationPanel = new ApplicationPanel(appBean.getId());
                    applicationPanel.addPropertyChangeListener(ApplicationBean.APPLICATION_PROPERTY, listener);
                    getMainContentPanel().addPanel(applicationPanel, MainContentPanel.CARD_APPLICATION, true);
                }
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        appListBean = new org.sola.clients.beans.application.ApplicationSearchResultsListBean();
        popUpApplications = new javax.swing.JPopupMenu();
        menuAssignApplication = new javax.swing.JMenuItem();
        menuOpenApplication = new javax.swing.JMenuItem();
        menuTransferApplication = new javax.swing.JMenuItem();
        menuRefreshApplication = new javax.swing.JMenuItem();
        headerPanel = new org.sola.clients.swing.ui.HeaderPanel();
        toolbarApplications = new javax.swing.JToolBar();
        btnOpenApplication = new javax.swing.JButton();
        btnAssignApplication = new javax.swing.JButton();
        btnTransferApplications = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        separator1 = new javax.swing.JToolBar.Separator();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        cbxShowOnlyMy = new javax.swing.JCheckBox();
        inprogressScrollPanel = new javax.swing.JScrollPane();
        tbApplications = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();

        popUpApplications.setName("popUpApplications"); // NOI18N

        menuAssignApplication.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/assign.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/Bundle"); // NOI18N
        menuAssignApplication.setText(bundle.getString("DashBoardPanel.menuAssignApplication.text")); // NOI18N
        menuAssignApplication.setName("menuAssignApplication"); // NOI18N
        menuAssignApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAssignApplicationActionPerformed(evt);
            }
        });
        popUpApplications.add(menuAssignApplication);

        menuOpenApplication.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/folder-open-document.png"))); // NOI18N
        menuOpenApplication.setText(bundle.getString("DashBoardPanel.menuOpenApplication.text")); // NOI18N
        menuOpenApplication.setToolTipText(bundle.getString("DashBoardPanel.menuOpenApplication.toolTipText")); // NOI18N
        menuOpenApplication.setName("menuOpenApplication"); // NOI18N
        menuOpenApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOpenApplicationActionPerformed(evt);
            }
        });
        popUpApplications.add(menuOpenApplication);

        menuTransferApplication.setText(bundle.getString("DashBoardPanel.menuTransferApplication.text")); // NOI18N
        menuTransferApplication.setName(bundle.getString("DashBoardPanel.menuTransferApplication.name")); // NOI18N
        popUpApplications.add(menuTransferApplication);

        menuRefreshApplication.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/refresh.png"))); // NOI18N
        menuRefreshApplication.setText(bundle.getString("DashBoardPanel.menuRefreshApplication.text")); // NOI18N
        menuRefreshApplication.setToolTipText(bundle.getString("DashBoardPanel.menuRefreshApplication.toolTipText")); // NOI18N
        menuRefreshApplication.setName("menuRefreshApplication"); // NOI18N
        menuRefreshApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRefreshApplicationActionPerformed(evt);
            }
        });
        popUpApplications.add(menuRefreshApplication);

        setHeaderPanel(headerPanel);
        setHelpTopic(bundle.getString("DashBoardPanel.helpTopic")); // NOI18N
        setMinimumSize(new java.awt.Dimension(354, 249));
        setName("Form"); // NOI18N

        headerPanel.setName("headerPanel"); // NOI18N
        headerPanel.setTitleText(bundle.getString("DashBoardPanel.headerPanel.titleText")); // NOI18N

        toolbarApplications.setFloatable(false);
        toolbarApplications.setRollover(true);
        toolbarApplications.setName("toolbarApplications"); // NOI18N

        btnOpenApplication.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/folder-open-document.png"))); // NOI18N
        btnOpenApplication.setText(bundle.getString("DashBoardPanel.btnOpenApplication.text")); // NOI18N
        btnOpenApplication.setFocusable(false);
        btnOpenApplication.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnOpenApplication.setName("btnOpenApplication"); // NOI18N
        btnOpenApplication.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOpenApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenApplicationActionPerformed(evt);
            }
        });
        toolbarApplications.add(btnOpenApplication);

        btnAssignApplication.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/assign.png"))); // NOI18N
        btnAssignApplication.setText(bundle.getString("DashBoardPanel.btnAssignApplication.text")); // NOI18N
        btnAssignApplication.setToolTipText(bundle.getString("DashBoardPanel.btnAssignApplication.toolTipText")); // NOI18N
        btnAssignApplication.setFocusable(false);
        btnAssignApplication.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAssignApplication.setName("btnAssignApplication"); // NOI18N
        btnAssignApplication.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAssignApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAssignApplicationActionPerformed(evt);
            }
        });
        toolbarApplications.add(btnAssignApplication);

        btnTransferApplications.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/document-link.png"))); // NOI18N
        btnTransferApplications.setText(bundle.getString("DashBoardPanel.btnTransferApplications.text")); // NOI18N
        btnTransferApplications.setFocusable(false);
        btnTransferApplications.setName(bundle.getString("DashBoardPanel.btnTransferApplications.name")); // NOI18N
        btnTransferApplications.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTransferApplications.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransferApplicationsActionPerformed(evt);
            }
        });
        toolbarApplications.add(btnTransferApplications);

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/refresh.png"))); // NOI18N
        btnRefresh.setText(bundle.getString("DashBoardPanel.btnRefresh.text")); // NOI18N
        btnRefresh.setToolTipText(bundle.getString("DashBoardPanel.btnRefresh.toolTipText")); // NOI18N
        btnRefresh.setFocusable(false);
        btnRefresh.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRefresh.setName("btnRefresh"); // NOI18N
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        toolbarApplications.add(btnRefresh);

        separator1.setName(bundle.getString("DashBoardPanel.separator1.name")); // NOI18N
        toolbarApplications.add(separator1);

        filler1.setName(bundle.getString("DashBoardPanel.filler1.name")); // NOI18N
        toolbarApplications.add(filler1);

        cbxShowOnlyMy.setText(bundle.getString("DashBoardPanel.cbxShowOnlyMy.text")); // NOI18N
        cbxShowOnlyMy.setFocusable(false);
        cbxShowOnlyMy.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        cbxShowOnlyMy.setName(bundle.getString("DashBoardPanel.cbxShowOnlyMy.name")); // NOI18N
        cbxShowOnlyMy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cbxShowOnlyMy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbxShowOnlyMyMouseClicked(evt);
            }
        });
        toolbarApplications.add(cbxShowOnlyMy);

        inprogressScrollPanel.setName("inprogressScrollPanel"); // NOI18N
        inprogressScrollPanel.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

        tbApplications.setCellSelectionEnabled(true);
        tbApplications.setComponentPopupMenu(popUpApplications);
        tbApplications.setGridColor(new java.awt.Color(135, 127, 115));
        tbApplications.setName("tbApplications"); // NOI18N
        tbApplications.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbApplications.setShowVerticalLines(false);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${applicationSearchResultsList}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, appListBean, eLProperty, tbApplications);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${checked}"));
        columnBinding.setColumnName("Checked");
        columnBinding.setColumnClass(Boolean.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nr}"));
        columnBinding.setColumnName("Nr");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${lodgingDatetime}"));
        columnBinding.setColumnName("Lodging Datetime");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${expectedCompletionDate}"));
        columnBinding.setColumnName("Expected Completion Date");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${serviceList}"));
        columnBinding.setColumnName("Service List");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${assigneeName}"));
        columnBinding.setColumnName("Assignee Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${status}"));
        columnBinding.setColumnName("Status");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${feePaid}"));
        columnBinding.setColumnName("Fee Paid");
        columnBinding.setColumnClass(Boolean.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, appListBean, org.jdesktop.beansbinding.ELProperty.create("${selectedApplication}"), tbApplications, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        tbApplications.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbApplicationsMouseClicked(evt);
            }
        });
        inprogressScrollPanel.setViewportView(tbApplications);
        tbApplications.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tbApplications.getColumnModel().getColumn(0).setPreferredWidth(30);
        tbApplications.getColumnModel().getColumn(0).setMaxWidth(30);
        tbApplications.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("DashBoardPanel.tbApplications.columnModel.title7")); // NOI18N
        tbApplications.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("DashBoardPanel.tbApplications.columnModel.title0")); // NOI18N
        tbApplications.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("DashBoardPanel.tbApplications.columnModel.title1")); // NOI18N
        tbApplications.getColumnModel().getColumn(2).setCellRenderer(new DateTimeRenderer());
        tbApplications.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("DashBoardPanel.tbApplications.columnModel.title2")); // NOI18N
        tbApplications.getColumnModel().getColumn(3).setCellRenderer(new DateTimeRenderer());
        tbApplications.getColumnModel().getColumn(4).setMinWidth(180);
        tbApplications.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("DashBoard.tbAssigned.columnModel.title6")); // NOI18N
        tbApplications.getColumnModel().getColumn(4).setCellRenderer(new org.sola.clients.swing.ui.renderers.CellDelimitedListRenderer());
        tbApplications.getColumnModel().getColumn(5).setHeaderValue(bundle.getString("DashBoardPanel.tbApplications.columnModel.title5")); // NOI18N
        tbApplications.getColumnModel().getColumn(6).setHeaderValue(bundle.getString("DashBoard.tbAssigned.columnModel.title4")); // NOI18N
        tbApplications.getColumnModel().getColumn(7).setHeaderValue(bundle.getString("DashBoardPanel.tbApplications.columnModel.title8")); // NOI18N
        tbApplications.getColumnModel().getColumn(7).setCellRenderer(new BooleanCellRenderer());

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(headerPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
            .add(toolbarApplications, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, inprogressScrollPanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(headerPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(toolbarApplications, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(inprogressScrollPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void tbApplicationsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbApplicationsMouseClicked
        if (evt.getClickCount() == 2 && btnOpenApplication.isEnabled()) {
            openApplication(appListBean.getSelectedApplication());
        }
    }//GEN-LAST:event_tbApplicationsMouseClicked

    private void btnOpenApplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenApplicationActionPerformed
        editApplication();
    }//GEN-LAST:event_btnOpenApplicationActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        refreshApplications();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void menuAssignApplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAssignApplicationActionPerformed
    }//GEN-LAST:event_menuAssignApplicationActionPerformed

    private void menuOpenApplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOpenApplicationActionPerformed
        editApplication();
    }//GEN-LAST:event_menuOpenApplicationActionPerformed

    private void menuRefreshApplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRefreshApplicationActionPerformed
        refreshApplications();
    }//GEN-LAST:event_menuRefreshApplicationActionPerformed

    private void btnAssignApplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssignApplicationActionPerformed
        openAssignmentForm();
    }//GEN-LAST:event_btnAssignApplicationActionPerformed

    private void cbxShowOnlyMyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxShowOnlyMyMouseClicked
        appListBean.showOnlyMyApplications(cbxShowOnlyMy.isSelected());
    }//GEN-LAST:event_cbxShowOnlyMyMouseClicked

    private void btnTransferApplicationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTransferApplicationsActionPerformed
        openTransferForm();
    }//GEN-LAST:event_btnTransferApplicationsActionPerformed

    /**
     * Refreshes assigned and unassigned application lists.
     */
    private void refreshApplications() {
        SolaTask t = new SolaTask<Void, Void>() {

            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(
                        ClientMessage.APPLICATION_LOADING_ASSIGNED));
                appListBean.FillAssigned(cbxShowOnlyMy.isSelected());
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }

    /**
     * Opens application form for the selected application from assigned list.
     */
    private void editApplication() {
        openApplication(appListBean.getSelectedApplication());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sola.clients.beans.application.ApplicationSearchResultsListBean appListBean;
    private javax.swing.JButton btnAssignApplication;
    private javax.swing.JButton btnOpenApplication;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnTransferApplications;
    private javax.swing.JCheckBox cbxShowOnlyMy;
    private javax.swing.Box.Filler filler1;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel;
    private javax.swing.JScrollPane inprogressScrollPanel;
    private javax.swing.JMenuItem menuAssignApplication;
    private javax.swing.JMenuItem menuOpenApplication;
    private javax.swing.JMenuItem menuRefreshApplication;
    private javax.swing.JMenuItem menuTransferApplication;
    private javax.swing.JPopupMenu popUpApplications;
    private javax.swing.JToolBar.Separator separator1;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tbApplications;
    private javax.swing.JToolBar toolbarApplications;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
