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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.application.ApplicationSearchResultBean;
import org.sola.clients.beans.application.ApplicationSearchResultsListBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.desktop.DesktopApplication;
import org.sola.clients.swing.ui.MainContentPanel;
import org.sola.clients.swing.ui.renderers.BooleanCellRenderer;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Used to show list of provided applications
 */
public class ApplicationListPanel extends javax.swing.JPanel {

    public static final String APPLICATION_REMOVED_FROM_LIST = "applicationRemovedFromList";
    
    private List<ApplicationSearchResultBean> applications;
    
    private ApplicationSearchResultsListBean createApplicationsList() {
        if (applicationSearchResults == null) {
            applicationSearchResults = new ApplicationSearchResultsListBean(applications);
        }
        return applicationSearchResults;
    }
    
    /**
     * Default constructor
     */
    public ApplicationListPanel() {
        initComponents();
    }

    /**
     * Component constructor with search results to show
     */
    public ApplicationListPanel(List<ApplicationSearchResultBean> applications) {
        this.applications = applications;
        initComponents();
        postInit();
    }
    
    private void postInit() {
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
    
    private void customizeToolbarButtons(ApplicationSearchResultBean searchResult){
        boolean enabled = searchResult!=null;
        btnView.setEnabled(enabled);
        btnRemoveFromList.setEnabled(enabled);
        menuView.setEnabled(btnView.isEnabled());
        menuRemoveFromList.setEnabled(btnRemoveFromList.isEnabled());
    }
    
    private void removeFromList(){
        applicationSearchResults.removeSelectedResultFromList();
        firePropertyChange(APPLICATION_REMOVED_FROM_LIST, false, true);
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
                DesktopApplication.getMainForm().getMainContentPanel()
                        .addPanel(panel, MainContentPanel.CARD_APPDETAILS, true);
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }
    
    public ObservableList<ApplicationSearchResultBean> getApplicationSearchResultsList(){
        return applicationSearchResults.getApplicationSearchResultsList();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        applicationSearchResults = createApplicationsList();
        popupApplications = new javax.swing.JPopupMenu();
        menuView = new javax.swing.JMenuItem();
        menuRemoveFromList = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        btnView = new javax.swing.JButton();
        btnRemoveFromList = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableApplications = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();

        menuView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/view.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/application/Bundle"); // NOI18N
        menuView.setText(bundle.getString("ApplicationListPanel.menuView.text")); // NOI18N
        popupApplications.add(menuView);

        menuRemoveFromList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        menuRemoveFromList.setText(bundle.getString("ApplicationListPanel.menuRemoveFromList.text")); // NOI18N
        popupApplications.add(menuRemoveFromList);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/view.png"))); // NOI18N
        btnView.setText(bundle.getString("ApplicationListPanel.btnView.text")); // NOI18N
        btnView.setFocusable(false);
        btnView.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });
        jToolBar1.add(btnView);

        btnRemoveFromList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemoveFromList.setText(bundle.getString("ApplicationListPanel.btnRemoveFromList.text")); // NOI18N
        btnRemoveFromList.setFocusable(false);
        btnRemoveFromList.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemoveFromList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveFromListActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRemoveFromList);

        tableApplications.setComponentPopupMenu(popupApplications);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${applicationSearchResultsList}");
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
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, applicationSearchResults, org.jdesktop.beansbinding.ELProperty.create("${selectedApplication}"), tableApplications, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(tableApplications);
        tableApplications.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("ApplicationListPanel.tableApplications.columnModel.title0_1")); // NOI18N
        tableApplications.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("ApplicationListPanel.tableApplications.columnModel.title1_1")); // NOI18N
        tableApplications.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("ApplicationListPanel.tableApplications.columnModel.title2_1")); // NOI18N
        tableApplications.getColumnModel().getColumn(3).setMinWidth(180);
        tableApplications.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("ApplicationListPanel.tableApplications.columnModel.title3_1")); // NOI18N
        tableApplications.getColumnModel().getColumn(3).setCellRenderer(new org.sola.clients.swing.ui.renderers.CellDelimitedListRenderer());
        tableApplications.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("ApplicationListPanel.tableApplications.columnModel.title4")); // NOI18N
        tableApplications.getColumnModel().getColumn(5).setHeaderValue(bundle.getString("ApplicationListPanel.tableApplications.columnModel.title5")); // NOI18N
        tableApplications.getColumnModel().getColumn(6).setHeaderValue(bundle.getString("ApplicationListPanel.tableApplications.columnModel.title6")); // NOI18N
        tableApplications.getColumnModel().getColumn(6).setCellRenderer(new BooleanCellRenderer());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        viewApplication();
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnRemoveFromListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveFromListActionPerformed
        removeFromList();
    }//GEN-LAST:event_btnRemoveFromListActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sola.clients.beans.application.ApplicationSearchResultsListBean applicationSearchResults;
    private javax.swing.JButton btnRemoveFromList;
    private javax.swing.JButton btnView;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem menuRemoveFromList;
    private javax.swing.JMenuItem menuView;
    private javax.swing.JPopupMenu popupApplications;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableApplications;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
