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
package org.sola.clients.swing.admin.system;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.cadastre.MapSheetBean;
import org.sola.clients.beans.cadastre.MapSheetListBean;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author Dinesh
 */
public class MapSheetNoManagementPanel extends ContentPanel {

    public static final String SELECTED_MAPSHEET_NO = "selectedMapsheetNo";
    private MapSheetBean mapSheetBean;
    private boolean editMode;

    public MapSheetBean getMapSheetBean() {
        return mapSheetBean;
    }

    /**
     * Sets reference data bean, to bind on the panel.
     */
    public void setMapSheetBean(MapSheetBean mapSheetBean) {
        if (mapSheetBean != null) {
            this.mapSheetBean = mapSheetBean;
        } else {
            this.mapSheetBean = new MapSheetBean();
        }

        firePropertyChange("mapSheetBean", null, this.mapSheetBean);
    }

    /**
     * Creates new form MapSheetNoManagementPanel
     */
    public MapSheetNoManagementPanel() {
        initComponents();
        initMapSheetList();
        enableTable(true);

        customizeMapSheetListButtons();
        mapSheetListBean.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(MapSheetListBean.SELECTED_MAPSHEET)) {
                    customizeMapSheetListButtons();
                }
            }
        });
    }

    private void customizeMapSheetListButtons() {
        boolean enabled = mapSheetListBean.getSelectedMapSheet() != null;
        btnEditRefData.setEnabled(enabled);
        btnRemoveRefData.setEnabled(enabled);
        menuEdit.setEnabled(enabled);
        menuRemove.setEnabled(enabled);
    }

    private void initMapSheetList() {
        mapSheetListBean.loadMapSheetList();
    }

    private void enableTable(boolean enabled) {
        btnAddRefData.setEnabled(enabled);
        menuAdd.setEnabled(enabled);
        if (enabled) {
            customizeMapSheetListButtons();
        } else {
            btnEditRefData.setEnabled(enabled);
            btnRemoveRefData.setEnabled(enabled);
            menuEdit.setEnabled(enabled);
            menuRemove.setEnabled(enabled);
        }
        tableMapSheets.setEnabled(enabled);

        cmbOffice.setEnabled(!enabled);
        cmbMapSheetType.setEnabled(!enabled);
        txtMapSheetNo.setEnabled(!enabled);
        btnSave.setEnabled(!enabled);
        btnCancel.setEnabled(!enabled);

        if (!enabled) {
            if (!editMode) {
                btnSave.setText(MessageUtility.getLocalizedMessageText(ClientMessage.GENERAL_LABELS_CREATE));
            } else {
                btnSave.setText(MessageUtility.getLocalizedMessageText(ClientMessage.GENERAL_LABELS_SAVE));
            }
        }
    }

    private void saveMapSheet() {
        if(mapSheetBean.validate(true).size()>0){
            return;
        }
        
        mapSheetBean.saveMapSheet();
        MessageUtility.displayMessage(ClientMessage.ADMIN_VDC_SAVED,
                new String[]{mapSheetBean.getMapNumber()});

        if (editMode) {
            if (mapSheetListBean.getSelectedMapSheet() != null) {
                mapSheetListBean.updateSelectedMapSheet((MapSheetBean) mapSheetBean.copy());
            }
        } else {
            mapSheetListBean.addMapSheet(mapSheetBean);
            addMapSheet();
        }
        setMapSheetBean(null);
        enableTable(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        mapSheetListBean = new org.sola.clients.beans.cadastre.MapSheetListBean();
        officeListBean = new org.sola.clients.beans.referencedata.OfficeListBean();
        popUpMapSheetList = new javax.swing.JPopupMenu();
        menuAdd = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenuItem();
        menuRemove = new javax.swing.JMenuItem();
        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableMapSheets = new javax.swing.JTable();
        toolbarRefData = new javax.swing.JToolBar();
        btnAddRefData = new javax.swing.JButton();
        btnEditRefData = new javax.swing.JButton();
        btnRemoveRefData = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmbOffice = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbMapSheetType = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMapSheetNo = new javax.swing.JTextField();
        groupPanel1 = new org.sola.clients.swing.ui.GroupPanel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        menuAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        menuAdd.setText("Add");
        menuAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddActionPerformed(evt);
            }
        });
        popUpMapSheetList.add(menuAdd);

        menuEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        menuEdit.setText("Edit");
        menuEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditActionPerformed(evt);
            }
        });
        popUpMapSheetList.add(menuEdit);

        menuRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        menuRemove.setText("Remove");
        menuRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRemoveActionPerformed(evt);
            }
        });
        popUpMapSheetList.add(menuRemove);

        setHeaderPanel(headerPanel1);

        headerPanel1.setTitleText("Map Sheet No. Management");

        jPanel5.setLayout(new java.awt.GridLayout(1, 2, 15, 0));

        tableMapSheets.setComponentPopupMenu(popUpMapSheetList);
        tableMapSheets.getTableHeader().setReorderingAllowed(false);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${mapSheets}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mapSheetListBean, eLProperty, tableMapSheets);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${sheetTypeString}"));
        columnBinding.setColumnName("Sheet Type");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mapNumber}"));
        columnBinding.setColumnName("Map Number");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${office.displayValue}"));
        columnBinding.setColumnName("Office");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mapSheetListBean, org.jdesktop.beansbinding.ELProperty.create("${selectedMapSheet}"), tableMapSheets, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(tableMapSheets);

        toolbarRefData.setFloatable(false);
        toolbarRefData.setRollover(true);

        btnAddRefData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/admin/referencedata/Bundle"); // NOI18N
        btnAddRefData.setText(bundle.getString("ReferenceDataManagementPanel.btnAddRefData.text")); // NOI18N
        btnAddRefData.setFocusable(false);
        btnAddRefData.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAddRefData.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddRefData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRefDataActionPerformed(evt);
            }
        });
        toolbarRefData.add(btnAddRefData);

        btnEditRefData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        btnEditRefData.setText(bundle.getString("ReferenceDataManagementPanel.btnEditRefData.text")); // NOI18N
        btnEditRefData.setFocusable(false);
        btnEditRefData.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEditRefData.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEditRefData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditRefDataActionPerformed(evt);
            }
        });
        toolbarRefData.add(btnEditRefData);

        btnRemoveRefData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemoveRefData.setText(bundle.getString("ReferenceDataManagementPanel.btnRemoveRefData.text")); // NOI18N
        btnRemoveRefData.setFocusable(false);
        btnRemoveRefData.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRemoveRefData.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemoveRefData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveRefDataActionPerformed(evt);
            }
        });
        toolbarRefData.add(btnRemoveRefData);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbarRefData, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(toolbarRefData, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel1);

        jPanel2.setLayout(new java.awt.GridLayout(3, 0, 0, 5));

        jLabel3.setText("Office");

        cmbOffice.setName("");
        cmbOffice.setOpaque(false);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${offices}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, officeListBean, eLProperty, cmbOffice);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${mapSheetBean.office}"), cmbOffice, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(cmbOffice, 0, 330, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbOffice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel6);

        jLabel2.setText("Map Sheet Type");

        cmbMapSheetType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Control Sheet", "Free Sheet" }));
        cmbMapSheetType.setName("");
        cmbMapSheetType.setOpaque(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${mapSheetBean.sheetTypeString}"), cmbMapSheetType, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(cmbMapSheetType, 0, 330, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbMapSheetType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel4);

        jLabel1.setText("Map Sheet No");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${mapSheetBean.mapNumber}"), txtMapSheetNo, org.jdesktop.beansbinding.BeanProperty.create("text"), "");
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 263, Short.MAX_VALUE))
            .addComponent(txtMapSheetNo)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMapSheetNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3);

        groupPanel1.setTitleText("Create/Edit map sheet");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(groupPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(groupPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnSave))
                .addContainerGap(115, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveMapSheet();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnAddRefDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRefDataActionPerformed
        addMapSheet();
    }//GEN-LAST:event_btnAddRefDataActionPerformed

    private void btnEditRefDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditRefDataActionPerformed
        editMapSheet();
    }//GEN-LAST:event_btnEditRefDataActionPerformed

    private void btnRemoveRefDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveRefDataActionPerformed
        removeMapSheet();
    }//GEN-LAST:event_btnRemoveRefDataActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        setMapSheetBean(null);
        enableTable(true);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void menuAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAddActionPerformed
        addMapSheet();
    }//GEN-LAST:event_menuAddActionPerformed

    private void menuEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditActionPerformed
        editMapSheet();
    }//GEN-LAST:event_menuEditActionPerformed

    private void menuRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRemoveActionPerformed
        removeMapSheet();
    }//GEN-LAST:event_menuRemoveActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddRefData;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnEditRefData;
    private javax.swing.JButton btnRemoveRefData;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cmbMapSheetType;
    private javax.swing.JComboBox cmbOffice;
    private org.sola.clients.swing.ui.GroupPanel groupPanel1;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private org.sola.clients.beans.cadastre.MapSheetListBean mapSheetListBean;
    private javax.swing.JMenuItem menuAdd;
    private javax.swing.JMenuItem menuEdit;
    private javax.swing.JMenuItem menuRemove;
    private org.sola.clients.beans.referencedata.OfficeListBean officeListBean;
    private javax.swing.JPopupMenu popUpMapSheetList;
    private javax.swing.JTable tableMapSheets;
    private javax.swing.JToolBar toolbarRefData;
    private javax.swing.JTextField txtMapSheetNo;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private void addMapSheet() {
        editMode=false;
        setMapSheetBean(new MapSheetBean());
        enableTable(false);
    }

    private void editMapSheet() {
        editMode=true;
        if (mapSheetListBean.getSelectedMapSheet() != null) {
            setMapSheetBean((MapSheetBean) mapSheetListBean.getSelectedMapSheet().copy());
            enableTable(false);
        }
    }

    private void removeMapSheet() {
        if (mapSheetListBean.getSelectedMapSheet() != null) {
            if (MessageUtility.displayMessage(
                    ClientMessage.ADMIN_CONFIRM_MAPSHEET_REMOVAL,
                    new String[]{mapSheetListBean.getSelectedMapSheet().getMapNumber()}) == MessageUtility.BUTTON_ONE) {
                CacheManager.remove(CacheManager.MAP_SHEET_KEY + mapSheetListBean.getSelectedMapSheet().getOfficeCode());
                mapSheetListBean.removeSelected();
            }
        }

    }
}
