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
package org.sola.clients.swing.admin.referencedata;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.referencedata.DepartmentBean;
import org.sola.clients.beans.referencedata.DepartmentListBean;
import org.sola.clients.beans.referencedata.OfficeListBean;
import org.sola.clients.swing.common.controls.autocomplete.Configurator;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.MainContentPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Shows list of departments and allows to manage them.
 */
public class DepartmentManagementPanel extends ContentPanel {

    /**
     * Creates new form DepartmentManagementPanel
     */
    public DepartmentManagementPanel() {
        initComponents();
        postInit();
    }

    private void postInit() {
        Configurator.enableAutoCompletion(cbxOffices);
        cbxOffices.setSelectedIndex(-1);
        departments.getDepartments().clear();
        offices.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(OfficeListBean.SELECTED_OFFICE_PROPERTY)) {
                    search(true);
                }
            }
        });

        departments.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(DepartmentListBean.SELECTED_DEPARTMENT_PROPERTY)) {
                    customizeButtons();
                }
            }
        });

        customizeButtons();
    }

    private void customizeButtons() {
        boolean enabled = departments.getSelectedDepartment() != null;
        btnEdit.setEnabled(enabled);
        btnRemove.setEnabled(enabled);
        menuEdit.setEnabled(enabled);
        menuRemove.setEnabled(enabled);
    }

    private void search(boolean showMessage) {
        if (offices.getSelectedOffice() != null) {
            departments.loadUntranslatedList(offices.getSelectedOffice().getCode());
            if (showMessage && departments.getDepartments().size() < 1) {
                MessageUtility.displayMessage(ClientMessage.SEARCH_NO_RESULTS);
            }
        }
    }

    private void showDepartmentForm(final DepartmentBean departmentBean) {
        final DepartmentPanelForm form = new DepartmentPanelForm(departmentBean, departmentBean != null);
        form.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(ReferenceDataPanelForm.REFDATA_SAVED_PROPERTY)) {
                    CacheManager.remove(CacheManager.DEPARTMENT_KEY + form.getDepartmentBean().getOfficeCode());
                    if (departmentBean == null) {
                        form.setDepartmentBean(null);
                    }
                    search(false);
                }
            }
        });
        getMainContentPanel().addPanel(form, MainContentPanel.CARD_DEPARTMENT, true);
    }

    private void removeDepartment() {
        if (departments.getSelectedDepartment() != null) {
            if (MessageUtility.displayMessage(
                    ClientMessage.ADMIN_CONFIRM_DEPARTMENT_REMOVAL,
                    new String[]{departments.getSelectedDepartment().getTranslatedDisplayValue()}) == MessageUtility.BUTTON_ONE) {
                CacheManager.remove(CacheManager.DEPARTMENT_KEY + departments.getSelectedDepartment().getOfficeCode());
                departments.removeSelectedDepartment();
                search(true);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        departments = new org.sola.clients.beans.referencedata.DepartmentListBean();
        offices = new org.sola.clients.beans.referencedata.OfficeListBean();
        popupDepartments = new javax.swing.JPopupMenu();
        menuAdd = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenuItem();
        menuRemove = new javax.swing.JMenuItem();
        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDepartments = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        jLabel1 = new javax.swing.JLabel();
        cbxOffices = new javax.swing.JComboBox();

        menuAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/admin/referencedata/Bundle"); // NOI18N
        menuAdd.setText(bundle.getString("DepartmentManagementPanel.menuAdd.text")); // NOI18N
        menuAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddActionPerformed(evt);
            }
        });
        popupDepartments.add(menuAdd);

        menuEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        menuEdit.setText(bundle.getString("DepartmentManagementPanel.menuEdit.text")); // NOI18N
        menuEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditActionPerformed(evt);
            }
        });
        popupDepartments.add(menuEdit);

        menuRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        menuRemove.setText(bundle.getString("DepartmentManagementPanel.menuRemove.text")); // NOI18N
        menuRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRemoveActionPerformed(evt);
            }
        });
        popupDepartments.add(menuRemove);

        setHeaderPanel(headerPanel1);

        headerPanel1.setTitleText(bundle.getString("DepartmentManagementPanel.headerPanel1.titleText")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        btnAdd.setText(bundle.getString("DepartmentManagementPanel.btnAdd.text")); // NOI18N
        btnAdd.setFocusable(false);
        btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAdd);

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        btnEdit.setText(bundle.getString("DepartmentManagementPanel.btnEdit.text")); // NOI18N
        btnEdit.setFocusable(false);
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEdit);

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemove.setText(bundle.getString("DepartmentManagementPanel.btnRemove.text")); // NOI18N
        btnRemove.setFocusable(false);
        btnRemove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRemove);

        tableDepartments.setComponentPopupMenu(popupDepartments);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${departments}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, departments, eLProperty, tableDepartments);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${translatedDisplayValue}"));
        columnBinding.setColumnName("Translated Display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${office.displayValue}"));
        columnBinding.setColumnName("Office.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${translatedDescription}"));
        columnBinding.setColumnName("Translated Description");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, departments, org.jdesktop.beansbinding.ELProperty.create("${selectedDepartment}"), tableDepartments, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(tableDepartments);
        tableDepartments.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("DepartmentManagementPanel.tableDepartments.columnModel.title0_1")); // NOI18N
        tableDepartments.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("DepartmentManagementPanel.tableDepartments.columnModel.title2_1")); // NOI18N
        tableDepartments.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("DepartmentManagementPanel.tableDepartments.columnModel.title1_1")); // NOI18N

        jLabel1.setText(bundle.getString("DepartmentManagementPanel.jLabel1.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${officesFiltered}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, offices, eLProperty, cbxOffices);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, offices, org.jdesktop.beansbinding.ELProperty.create("${selectedOffice}"), cbxOffices, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(cbxOffices, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxOffices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        removeDepartment();
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (departments.getSelectedDepartment() != null) {
            showDepartmentForm(departments.getSelectedDepartment());
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        showDepartmentForm(null);
    }//GEN-LAST:event_btnAddActionPerformed

    private void menuAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAddActionPerformed
        showDepartmentForm(null);
    }//GEN-LAST:event_menuAddActionPerformed

    private void menuEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditActionPerformed
        if (departments.getSelectedDepartment() != null) {
            showDepartmentForm(departments.getSelectedDepartment());
        }
    }//GEN-LAST:event_menuEditActionPerformed

    private void menuRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRemoveActionPerformed
        removeDepartment();
    }//GEN-LAST:event_menuRemoveActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRemove;
    private javax.swing.JComboBox cbxOffices;
    private org.sola.clients.beans.referencedata.DepartmentListBean departments;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem menuAdd;
    private javax.swing.JMenuItem menuEdit;
    private javax.swing.JMenuItem menuRemove;
    private org.sola.clients.beans.referencedata.OfficeListBean offices;
    private javax.swing.JPopupMenu popupDepartments;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableDepartments;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
