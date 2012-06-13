/*
 * Copyright 2012 Food and Agriculture Organization of the United Nations (FAO).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sola.clients.swing.desktop.application;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import org.sola.clients.beans.application.ApplicationBean;
import org.sola.clients.beans.application.ApplicationSearchResultBean;
import org.sola.clients.beans.application.ApplicationSearchResultsListBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.MainContentPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Used to assign applications to the users
 */
public class ApplicationAssignmentPanel extends ContentPanel {

    public static final String APPLICATIONS_ASSGINED = "applicationsAssigned";
    private List<ApplicationSearchResultBean> applications;

    private ApplicationSearchResultsListBean createApplicationsList() {
        if (applicationSearchResults == null) {
            applicationSearchResults = new ApplicationSearchResultsListBean(applications);
        }
        return applicationSearchResults;
    }

    /**
     * Panel constructor
     */
    public ApplicationAssignmentPanel(List<ApplicationSearchResultBean> applications) {
        this.applications = applications;
        initComponents();
        postInit();
    }

    private void postInit() {
        assigneeList.loadMyDepartmentUsers();
        customizeAssignButton();
        customizeToolbarButtons(null);
        applicationSearchResults.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getPropertyName().equals(ApplicationSearchResultsListBean.SELECTED_APPLICATION_PROPERTY)){
                    customizeToolbarButtons((ApplicationSearchResultBean)evt.getNewValue());
                }
            }
        });
    }

    private void customizeAssignButton(){
        if (assigneeList.getUsers().size() < 1 || applicationSearchResults.getApplicationSearchResultsList().size() < 1) {
            btnAssign.setEnabled(false);
        } else {
            btnAssign.setEnabled(true);
        }
    }
    
    private void customizeToolbarButtons(ApplicationSearchResultBean searchResult){
        boolean enabled = searchResult!=null;
        btnView.setEnabled(enabled);
        btnRemoveFromList.setEnabled(enabled);
        menuView.setEnabled(btnView.isEnabled());
        menuRemoveFromList.setEnabled(btnRemoveFromList.isEnabled());
    }
    
    /**
     * Assigns application to the selected user.
     */
    private void assign() {
        if (assigneeList.getSelectedUser() == null) {
            MessageUtility.displayMessage(ClientMessage.APPLICATION_NOSEL_USER);
            return;
        }
        if (MessageUtility.displayMessage(ClientMessage.APPLICATION_ASSIGN,
                new String[]{assigneeList.getSelectedUser().getFullUserName()})
                == MessageUtility.BUTTON_ONE) {

            SolaTask t = new SolaTask<Void, Void>() {

                @Override
                public Void doTask() {
                    setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.APPLICATION_ASSIGNING));
                    ApplicationBean.assignUserBulkFromSearchResults(
                            applicationSearchResults.getApplicationSearchResultsList(),
                            assigneeList.getSelectedUser().getId());
                    return null;
                }

                @Override
                protected void taskDone() {
                    MessageUtility.displayMessage(ClientMessage.APPLICATION_ASSIGNED);
                    firePropertyChange(APPLICATIONS_ASSGINED, false, true);
                    close();
                }
            };
            TaskManager.getInstance().runTask(t);
        }
    }

    private void removeFromList(){
        applicationSearchResults.removeSelectedResultFromList();
        customizeAssignButton();
    }
    
    private void viewApplication(){
        if(applicationSearchResults.getSelectedApplication()==null){
            return;
        }
        
        SolaTask t = new SolaTask<Void, Void>() {

            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_APP));
                ApplicationDetailsForm panel = new ApplicationDetailsForm(applicationSearchResults.getSelectedApplication().getId());
                getMainContentPanel().addPanel(panel, MainContentPanel.CARD_APPDETAILS, true);
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        assigneeList = new org.sola.clients.beans.security.UserSearchResultListBean();
        applicationSearchResults = createApplicationsList();
        popupApplicationSearchReults = new javax.swing.JPopupMenu();
        menuView = new javax.swing.JMenuItem();
        menuRemoveFromList = new javax.swing.JMenuItem();
        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbxUsers = new javax.swing.JComboBox();
        btnAssign = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        btnView = new javax.swing.JButton();
        btnRemoveFromList = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableApplications = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();

        menuView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/view.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/application/Bundle"); // NOI18N
        menuView.setText(bundle.getString("ApplicationAssignmentPanel.menuView.text")); // NOI18N
        menuView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewActionPerformed(evt);
            }
        });
        popupApplicationSearchReults.add(menuView);

        menuRemoveFromList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        menuRemoveFromList.setText(bundle.getString("ApplicationAssignmentPanel.menuRemoveFromList.text")); // NOI18N
        menuRemoveFromList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRemoveFromListActionPerformed(evt);
            }
        });
        popupApplicationSearchReults.add(menuRemoveFromList);

        setHeaderPanel(headerPanel1);

        headerPanel1.setTitleText(bundle.getString("ApplicationAssignmentPanel.headerPanel1.titleText")); // NOI18N

        jLabel1.setText(bundle.getString("ApplicationAssignmentPanel.jLabel1.text")); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${users}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, assigneeList, eLProperty, cbxUsers);
        bindingGroup.addBinding(jComboBoxBinding);
        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, assigneeList, org.jdesktop.beansbinding.ELProperty.create("${selectedUser}"), cbxUsers, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        btnAssign.setText(bundle.getString("ApplicationAssignmentPanel.btnAssign.text")); // NOI18N
        btnAssign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAssignActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(cbxUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAssign, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAssign))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/view.png"))); // NOI18N
        btnView.setText(bundle.getString("ApplicationAssignmentPanel.btnView.text")); // NOI18N
        btnView.setFocusable(false);
        btnView.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });
        jToolBar1.add(btnView);

        btnRemoveFromList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemoveFromList.setText(bundle.getString("ApplicationAssignmentPanel.btnRemoveFromList.text")); // NOI18N
        btnRemoveFromList.setFocusable(false);
        btnRemoveFromList.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemoveFromList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveFromListActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRemoveFromList);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${applicationSearchResultsList}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, applicationSearchResults, eLProperty, tableApplications);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nr}"));
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
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${agent}"));
        columnBinding.setColumnName("Agent");
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
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, applicationSearchResults, org.jdesktop.beansbinding.ELProperty.create("${selectedApplication}"), tableApplications, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        tableApplications.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableApplicationsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableApplications);
        tableApplications.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("ApplicationAssignmentPanel.tableApplications.columnModel.title0_2")); // NOI18N
        tableApplications.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("ApplicationAssignmentPanel.tableApplications.columnModel.title1_1")); // NOI18N
        tableApplications.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("ApplicationAssignmentPanel.tableApplications.columnModel.title2_1")); // NOI18N
        tableApplications.getColumnModel().getColumn(3).setMinWidth(180);
        tableApplications.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("ApplicationAssignmentPanel.tableApplications.columnModel.title3_1")); // NOI18N
        tableApplications.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("ApplicationAssignmentPanel.tableApplications.columnModel.title4")); // NOI18N
        tableApplications.getColumnModel().getColumn(5).setHeaderValue(bundle.getString("ApplicationAssignmentPanel.tableApplications.columnModel.title5")); // NOI18N
        tableApplications.getColumnModel().getColumn(6).setHeaderValue(bundle.getString("ApplicationAssignmentPanel.tableApplications.columnModel.title6")); // NOI18N
        tableApplications.getColumnModel().getColumn(7).setHeaderValue(bundle.getString("ApplicationAssignmentPanel.tableApplications.columnModel.title7_1")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAssignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssignActionPerformed
        assign();
    }//GEN-LAST:event_btnAssignActionPerformed

    private void btnRemoveFromListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveFromListActionPerformed
        removeFromList();
    }//GEN-LAST:event_btnRemoveFromListActionPerformed

    private void menuRemoveFromListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRemoveFromListActionPerformed
        removeFromList();
    }//GEN-LAST:event_menuRemoveFromListActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        viewApplication();
    }//GEN-LAST:event_btnViewActionPerformed

    private void menuViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuViewActionPerformed
        viewApplication();
    }//GEN-LAST:event_menuViewActionPerformed

    private void tableApplicationsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableApplicationsMouseClicked
        if(evt.getClickCount()>1 && evt.getButton() == MouseEvent.BUTTON1){
            viewApplication();
        }
    }//GEN-LAST:event_tableApplicationsMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sola.clients.beans.application.ApplicationSearchResultsListBean applicationSearchResults;
    private org.sola.clients.beans.security.UserSearchResultListBean assigneeList;
    private javax.swing.JButton btnAssign;
    private javax.swing.JButton btnRemoveFromList;
    private javax.swing.JButton btnView;
    private javax.swing.JComboBox cbxUsers;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem menuRemoveFromList;
    private javax.swing.JMenuItem menuView;
    private javax.swing.JPopupMenu popupApplicationSearchReults;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableApplications;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
