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
import org.sola.clients.beans.security.SecurityBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.common.RolesConstants;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Used to assign applications to the users
 */
public class ApplicationAssignmentPanel extends ContentPanel {

    public static final String APPLICATIONS_ASSGINED = "applicationsAssigned";
    private List<ApplicationSearchResultBean> applications;

    private ApplicationListPanel createApplicationListPanel() {
        return new ApplicationListPanel(applications);
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
        if (SecurityBean.isInRole(RolesConstants.APPLICATION_ASSIGN_TO_ALL)) {
            assigneeList.loadMyOfficeUsers();
        } else {
            assigneeList.loadMyDepartmentUsers();
        }
        customizeAssignButton();
        applicationList.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(ApplicationListPanel.APPLICATION_REMOVED_FROM_LIST)) {
                    customizeAssignButton();
                }
            }
        });
    }

    private void customizeAssignButton() {
        if (assigneeList.getUsers().size() < 1 || applicationList.getApplicationSearchResultsList().size() < 1) {
            btnAssign.setEnabled(false);
        } else {
            btnAssign.setEnabled(true);
        }
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
                            applicationList.getApplicationSearchResultsList(),
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        assigneeList = new org.sola.clients.beans.security.UserSearchResultListBean();
        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbxUsers = new javax.swing.JComboBox();
        btnAssign = new javax.swing.JButton();
        applicationList = createApplicationListPanel();

        setHeaderPanel(headerPanel1);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/application/Bundle"); // NOI18N
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(applicationList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(applicationList, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAssignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssignActionPerformed
        assign();
    }//GEN-LAST:event_btnAssignActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sola.clients.swing.desktop.application.ApplicationListPanel applicationList;
    private org.sola.clients.beans.security.UserSearchResultListBean assigneeList;
    private javax.swing.JButton btnAssign;
    private javax.swing.JComboBox cbxUsers;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
