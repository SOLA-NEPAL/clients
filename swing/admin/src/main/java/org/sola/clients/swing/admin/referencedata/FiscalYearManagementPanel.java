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
import org.sola.clients.beans.referencedata.FiscalYearBean;
import org.sola.clients.beans.referencedata.FiscalYearListBean;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.MainContentPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author Kumar
 */
public class FiscalYearManagementPanel extends ContentPanel {

    /**
     * Creates new form FiscalYearManagementPanel
     */
    public FiscalYearManagementPanel() {
        initComponents();
        postInit();
    }

    private void postInit() {
        // fiscalYearListBean.getFiscalYears().clear();
        fiscalYearListBean.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(FiscalYearListBean.SELECTED_FISCAL_YEAR_PROPERTY)) {
                    customizeButtons();
                }
            }
        });
        customizeButtons();
    }

    private void customizeButtons() {
        boolean enabled = fiscalYearListBean.getSelectedFiscalYear() != null;
        btnEdit.setEnabled(enabled);
        btnRemove.setEnabled(enabled);
        // menuEdit.setEnabled(enabled);
        // menuRemove.setEnabled(enabled);
    }

    private void showFiscalYearPanelForm(final FiscalYearBean fiscalYearBean) {
        final FiscalYearPanelForm form = new FiscalYearPanelForm(fiscalYearBean, fiscalYearBean != null);
        form.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(ReferenceDataPanelForm.REFDATA_SAVED_PROPERTY)) {
                    CacheManager.remove(CacheManager.FISCAL_YEAR_KEY);
                    if (fiscalYearBean == null) {
                        form.setFiscalYearBean(null);
                    }
                    fiscalYearListBean.refreshList();
                }
            }
        });
        getMainContentPanel().addPanel(form, MainContentPanel.CARD_FISCAL_YEAR, true);
    }

    private void removeFiscalYear() {
        if (fiscalYearListBean.getSelectedFiscalYear() != null) {
            if (MessageUtility.displayMessage(
                    ClientMessage.ADMIN_CONFIRM_FISCAL_YEAR_REMOVAL,
                    new String[]{fiscalYearListBean.getSelectedFiscalYear().getTranslatedDisplayValue()}) == MessageUtility.BUTTON_ONE) {
                CacheManager.remove(CacheManager.FISCAL_YEAR_KEY);
                fiscalYearListBean.removeSelectedFiscalYear();
                fiscalYearListBean.refreshList();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        fiscalYearListBean = new org.sola.clients.beans.referencedata.FiscalYearListBean();
        jToolBar1 = new javax.swing.JToolBar();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFiscalYears = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();

        setHeaderPanel(headerPanel1);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/admin/referencedata/Bundle"); // NOI18N
        btnAdd.setText(bundle.getString("FiscalYearManagementPanel.btnAdd.text")); // NOI18N
        btnAdd.setFocusable(false);
        btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAdd);

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        btnEdit.setText(bundle.getString("FiscalYearManagementPanel.btnEdit.text")); // NOI18N
        btnEdit.setFocusable(false);
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEdit);

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemove.setText(bundle.getString("FiscalYearManagementPanel.btnRemove.text")); // NOI18N
        btnRemove.setFocusable(false);
        btnRemove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRemove);

        headerPanel1.setTitleText(bundle.getString("FiscalYearManagementPanel.headerPanel1.titleText")); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${fiscalYears}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, fiscalYearListBean, eLProperty, tblFiscalYears);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${translatedDisplayValue}"));
        columnBinding.setColumnName("Translated Display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${startDate}"));
        columnBinding.setColumnName("Start Date");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${endDate}"));
        columnBinding.setColumnName("End Date");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${translatedDescription}"));
        columnBinding.setColumnName("Translated Description");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${current}"));
        columnBinding.setColumnName("Current");
        columnBinding.setColumnClass(Boolean.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, fiscalYearListBean, org.jdesktop.beansbinding.ELProperty.create("${selectedFiscalYear}"), tblFiscalYears, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(tblFiscalYears);
        tblFiscalYears.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("FiscalYearManagementPanel.tblFiscalYears.columnModel.title0")); // NOI18N
        tblFiscalYears.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("FiscalYearManagementPanel.tblFiscalYears.columnModel.title1")); // NOI18N
        tblFiscalYears.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("FiscalYearManagementPanel.tblFiscalYears.columnModel.title2")); // NOI18N
        tblFiscalYears.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("FiscalYearManagementPanel.tblFiscalYears.columnModel.title3")); // NOI18N
        tblFiscalYears.getColumnModel().getColumn(4).setPreferredWidth(80);
        tblFiscalYears.getColumnModel().getColumn(4).setMaxWidth(80);
        tblFiscalYears.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("FiscalYearManagementPanel.tblFiscalYears.columnModel.title4")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        showFiscalYearPanelForm(null);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        if (fiscalYearListBean.getSelectedFiscalYear() != null) {
            showFiscalYearPanelForm(fiscalYearListBean.getSelectedFiscalYear());
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        // TODO add your handling code here:
        removeFiscalYear();
    }//GEN-LAST:event_btnRemoveActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRemove;
    private org.sola.clients.beans.referencedata.FiscalYearListBean fiscalYearListBean;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tblFiscalYears;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
