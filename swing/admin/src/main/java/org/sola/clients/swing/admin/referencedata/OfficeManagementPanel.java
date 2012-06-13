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
import org.sola.clients.beans.referencedata.OfficeBean;
import org.sola.clients.beans.referencedata.OfficeListBean;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.MainContentPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Shows list of offices and allows to create new office or modify exiting
 */
public class OfficeManagementPanel extends ContentPanel {

    /**
     * Default constructor
     */
    public OfficeManagementPanel() {
        initComponents();
        offices.loadListUntranslated();
        offices.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(OfficeListBean.SELECTED_OFFICE_PROPERTY)) {
                    customizeButtons();
                }
            }
        });
        customizeButtons();
    }

    private void customizeButtons() {
        boolean enable = offices.getSelectedOffice() != null;
        btnEdit.setEnabled(enable);
        btnRemove.setEnabled(enable);
        menuEdit.setEnabled(enable);
        menuRemove.setEnabled(enable);
    }

    private void showOfficeForm(final OfficeBean office) {
        final OfficePanelForm form = new OfficePanelForm(office, office != null);
        form.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(ReferenceDataPanelForm.REFDATA_SAVED_PROPERTY)) {
                    if (office == null) {
                        form.setOfficeBean(null);
                    }
                    CacheManager.remove(CacheManager.OFFICE_KEY);
                    offices.loadListUntranslated();
                }
            }
        });
        getMainContentPanel().addPanel(form, MainContentPanel.CARD_OFFICE, true);
    }

    private void removeOffice() {
        if (offices.getSelectedOffice() != null) {
            if (MessageUtility.displayMessage(
                    ClientMessage.ADMIN_CONFIRM_OFFICE_REMOVAL,
                    new String[]{offices.getSelectedOffice().getTranslatedDisplayValue()}) == MessageUtility.BUTTON_ONE) {
                CacheManager.remove(CacheManager.OFFICE_KEY);
                offices.removeSelectedOffice();
                offices.loadListUntranslated();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        offices = new org.sola.clients.beans.referencedata.OfficeListBean();
        popupOffices = new javax.swing.JPopupMenu();
        menuAdd = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenuItem();
        menuRemove = new javax.swing.JMenuItem();
        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableOffices = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();

        menuAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/admin/referencedata/Bundle"); // NOI18N
        menuAdd.setText(bundle.getString("OfficeManagementPanel.menuAdd.text")); // NOI18N
        menuAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddActionPerformed(evt);
            }
        });
        popupOffices.add(menuAdd);

        menuEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        menuEdit.setText(bundle.getString("OfficeManagementPanel.menuEdit.text")); // NOI18N
        menuEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditActionPerformed(evt);
            }
        });
        popupOffices.add(menuEdit);

        menuRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        menuRemove.setText(bundle.getString("OfficeManagementPanel.menuRemove.text")); // NOI18N
        menuRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRemoveActionPerformed(evt);
            }
        });
        popupOffices.add(menuRemove);

        setHeaderPanel(headerPanel1);

        headerPanel1.setTitleText(bundle.getString("OfficeManagementPanel.headerPanel1.titleText")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        btnAdd.setText(bundle.getString("OfficeManagementPanel.btnAdd.text")); // NOI18N
        btnAdd.setFocusable(false);
        btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAdd);

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        btnEdit.setText(bundle.getString("OfficeManagementPanel.btnEdit.text")); // NOI18N
        btnEdit.setFocusable(false);
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEdit);

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemove.setText(bundle.getString("OfficeManagementPanel.btnRemove.text")); // NOI18N
        btnRemove.setFocusable(false);
        btnRemove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRemove);

        tableOffices.setComponentPopupMenu(popupOffices);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${offices}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, offices, eLProperty, tableOffices);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${translatedDisplayValue}"));
        columnBinding.setColumnName("Translated Display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${translatedDescription}"));
        columnBinding.setColumnName("Translated Description");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, offices, org.jdesktop.beansbinding.ELProperty.create("${selectedOffice}"), tableOffices, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(tableOffices);
        tableOffices.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("OfficeManagementPanel.tableOffices.columnModel.title0_1")); // NOI18N
        tableOffices.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("OfficeManagementPanel.tableOffices.columnModel.title1_1")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        showOfficeForm(null);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (offices.getSelectedOffice() != null) {
            showOfficeForm(offices.getSelectedOffice());
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        removeOffice();
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void menuAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAddActionPerformed
        showOfficeForm(null);
    }//GEN-LAST:event_menuAddActionPerformed

    private void menuEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditActionPerformed
        if (offices.getSelectedOffice() != null) {
            showOfficeForm(offices.getSelectedOffice());
        }
    }//GEN-LAST:event_menuEditActionPerformed

    private void menuRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRemoveActionPerformed
        removeOffice();
    }//GEN-LAST:event_menuRemoveActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRemove;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem menuAdd;
    private javax.swing.JMenuItem menuEdit;
    private javax.swing.JMenuItem menuRemove;
    private org.sola.clients.beans.referencedata.OfficeListBean offices;
    private javax.swing.JPopupMenu popupOffices;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableOffices;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
