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
import org.sola.clients.beans.application.ApplicationBean;
import org.sola.clients.beans.application.ApplicationSearchResultBean;
import org.sola.clients.beans.referencedata.DepartmentListBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.WizardButtonsEvent;
import org.sola.clients.swing.ui.WizardButtonsListener;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Used to transfer applications between departments
 */
public class ApplicationTransferForm extends ContentPanel {

    public static final String APPLICATIONS_TRANSFERED = "applicationsTransfered";
    private List<ApplicationSearchResultBean> applications;

    private ApplicationListPanel createApplicationListPanel() {
        return new ApplicationListPanel(applications);
    }

    /**
     * Panel constructor
     */
    public ApplicationTransferForm(List<ApplicationSearchResultBean> applications) {
        this.applications = applications;
        initComponents();
        postInit();
    }

    private void postInit() {
        departments.loadList(false);
        departmentUsers.filterByDepartment(null);
        departmentUsers.loadUsersWithAssignRightByOffice();
        
        departments.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getPropertyName().equals(DepartmentListBean.SELECTED_DEPARTMENT_PROPERTY)){
                    if(departments.getSelectedDepartment() == null){
                        departmentUsers.filterByDepartment(null);
                    } else {
                        departmentUsers.filterByDepartment(departments.getSelectedDepartment().getCode());
                    }
                }
            }
        });
        
        wizardButtons1.addWizardButtonsListener(new WizardButtonsListener() {

            @Override
            public void buttonPressed(WizardButtonsEvent evt) {
                if (evt.getCurrentCard() == pnlStep1 && 
                        evt.getButtonPressed() == WizardButtonsEvent.BUTTON_PRESSED.NEXT) {
                    evt.setStopProcessing(!checkApplicationList());
                } else if (evt.getCurrentCard() == pnlStep2 && 
                        evt.getButtonPressed() == WizardButtonsEvent.BUTTON_PRESSED.FINISH) {
                    evt.setStopProcessing(!transferApplications());
                }
            }
        });
    }

    private boolean transferApplications() {
        if (!checkApplicationList()) {
            return false;
        }

        if (departmentUsers.getSelectedUser() == null) {
            MessageUtility.displayMessage(ClientMessage.CHECK_USER_NOT_SELECTED);
            return false;
        }

        SolaTask t = new SolaTask<Void, Void>() {

            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.APPLICATION_TRANSFERRING));
                ApplicationBean.transferApplicationBulkFromSearchResults(
                        applicationList.getApplicationSearchResultsList(),
                        departmentUsers.getSelectedUser().getId());
                return null;
            }

            @Override
            protected void taskDone() {
                MessageUtility.displayMessage(ClientMessage.APPLICATION_TRANSFERRED);
                firePropertyChange(APPLICATIONS_TRANSFERED, false, true);
                close();
            }
        };
        TaskManager.getInstance().runTask(t);
        return true;
    }

    private boolean checkApplicationList() {
        if (applicationList.getApplicationSearchResultsList().size() < 1) {
            MessageUtility.displayMessage(ClientMessage.APPLICATION_LIST_EMPTY);
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        departments = new org.sola.clients.beans.referencedata.DepartmentListBean();
        departmentUsers = new org.sola.clients.beans.security.UserSearchResultListBean();
        headerPanel = new org.sola.clients.swing.ui.HeaderPanel();
        pnlWizard = new javax.swing.JPanel();
        pnlStep1 = new javax.swing.JPanel();
        applicationList = createApplicationListPanel();
        pnlStep2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        groupPanel1 = new org.sola.clients.swing.ui.GroupPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableDepartments = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        jPanel2 = new javax.swing.JPanel();
        groupPanel2 = new org.sola.clients.swing.ui.GroupPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableUsers = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        wizardButtons1 = new org.sola.clients.swing.ui.WizardButtons();

        setHeaderPanel(headerPanel);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/application/Bundle"); // NOI18N
        headerPanel.setToolTipText(bundle.getString("ApplicationTransferForm.headerPanel.toolTipText")); // NOI18N
        headerPanel.setTitleText(bundle.getString("ApplicationTransferForm.headerPanel.titleText")); // NOI18N

        pnlWizard.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout pnlStep1Layout = new javax.swing.GroupLayout(pnlStep1);
        pnlStep1.setLayout(pnlStep1Layout);
        pnlStep1Layout.setHorizontalGroup(
            pnlStep1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(applicationList, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
        );
        pnlStep1Layout.setVerticalGroup(
            pnlStep1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(applicationList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
        );

        pnlWizard.add(pnlStep1, "card4");

        jPanel3.setLayout(new java.awt.GridLayout(2, 1, 0, 20));

        groupPanel1.setTitleText(bundle.getString("ApplicationTransferForm.groupPanel1.titleText")); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${departments}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, departments, eLProperty, tableDepartments);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${displayValue}"));
        columnBinding.setColumnName("Display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, departments, org.jdesktop.beansbinding.ELProperty.create("${selectedDepartment}"), tableDepartments, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane3.setViewportView(tableDepartments);
        tableDepartments.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("ApplicationTransferForm.tableDepartments.columnModel.title0_1")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(groupPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
            .addComponent(jScrollPane3)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(groupPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel1);

        groupPanel2.setTitleText(bundle.getString("ApplicationTransferForm.groupPanel2.titleText")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${users}");
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, departmentUsers, eLProperty, tableUsers);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fullUserName}"));
        columnBinding.setColumnName("Full User Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, departmentUsers, org.jdesktop.beansbinding.ELProperty.create("${selectedUser}"), tableUsers, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(tableUsers);
        tableUsers.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("ApplicationTransferForm.tableUsers.columnModel.title0_1")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
            .addComponent(groupPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(groupPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel2);

        javax.swing.GroupLayout pnlStep2Layout = new javax.swing.GroupLayout(pnlStep2);
        pnlStep2.setLayout(pnlStep2Layout);
        pnlStep2Layout.setHorizontalGroup(
            pnlStep2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlStep2Layout.setVerticalGroup(
            pnlStep2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStep2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE))
        );

        pnlWizard.add(pnlStep2, "card3");

        wizardButtons1.setWizardPanel(pnlWizard);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlWizard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(wizardButtons1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlWizard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wizardButtons1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sola.clients.swing.desktop.application.ApplicationListPanel applicationList;
    private org.sola.clients.beans.security.UserSearchResultListBean departmentUsers;
    private org.sola.clients.beans.referencedata.DepartmentListBean departments;
    private org.sola.clients.swing.ui.GroupPanel groupPanel1;
    private org.sola.clients.swing.ui.GroupPanel groupPanel2;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel pnlStep1;
    private javax.swing.JPanel pnlStep2;
    private javax.swing.JPanel pnlWizard;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableDepartments;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableUsers;
    private org.sola.clients.swing.ui.WizardButtons wizardButtons1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
