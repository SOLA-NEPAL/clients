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
package org.sola.clients.swing.desktop.cadastre;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.cadastre.MapSheetBean;
import org.sola.clients.beans.cadastre.MapSheetListBean;
import org.sola.clients.beans.referencedata.OfficeBean;
import org.sola.clients.beans.security.SecurityBean;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.common.RolesConstants;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

public class MapSheetNoManagementPanel extends ContentPanel {

    public static final String MAPSHEET_BEAN_PROPERTY = "mapSheetBean";
    public static final String OFFICE_BEAN_PROPERTY = "currentOffice";
    private MapSheetBean mapSheetBean;
    private OfficeBean currentOffice;
    private boolean editMode;
    private ObservableList<Integer> srids;

    public OfficeBean getCurrentOffice() {
        return currentOffice;
    }

    public void setCurrentOffice(OfficeBean currentOffice) {
        if (currentOffice != null) {
            this.currentOffice = currentOffice;
        } else {
            this.currentOffice = new OfficeBean();

        }
        firePropertyChange(OFFICE_BEAN_PROPERTY, null, this.currentOffice);
    }

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

        firePropertyChange(MAPSHEET_BEAN_PROPERTY, null, this.mapSheetBean);
    }

    private void checkUserRole() {
        if (!SecurityBean.getCurrentUser().isInRole(RolesConstants.ADMIN_MANAGE_SETTINGS, RolesConstants.CADASTRE_MAP_SHEET_SAVE)) {
            // toolbarMapsheet.setEnabled(false);
            btnAdd.setEnabled(false);
            btnEdit.setEnabled(false);
            btnRemove.setEnabled(false);
            tblMapsheets.setEnabled(false);
        }
    }

    private void sridsManagement() {
        srids = ObservableCollections.observableList(new ArrayList<Integer>());
        srids.add(97260);
        srids.add(97261);
        srids.add(97262);
        setCurrentOffice(OfficeBean.getCurrentOffice());
    }

    public List<Integer> getSrids() {
        return srids;
    }

    /**
     * Creates new form MapSheetNoManagementPanel
     */
    public MapSheetNoManagementPanel() {
        sridsManagement();
        initComponents();
        checkUserRole();
        postInit();
        customizePanel();
        showHidePanel(false);
    }

    private void postInit() {
        loadMapsheets();
        mapSheetListBean.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(MapSheetListBean.SELECTED_MAPSHEET)) {
                    customizePanel();
                }
            }
        });
    }

    private void showHidePanel(boolean show) {
        panelCreateEdit.setVisible(show);
    }

    private void loadMapsheets() {
        if (OfficeBean.getCurrentOffice() != null) {
            mapSheetListBean.loadList(currentOffice.getCode(), false);
            mapSheetListBean.setSelectedMapSheet(null);
        }
    }

    private void customizePanel() {
        boolean enabled = mapSheetListBean.getSelectedMapSheet() != null;
        btnEdit.setEnabled(enabled);
        btnRemove.setEnabled(enabled);
        menuEdit.setEnabled(enabled);
        menuRemove.setEnabled(enabled);
    }

    private void saveMapSheet() {
        mapSheetBean.setOffice(currentOffice);
        if (mapSheetBean.validate(true).size() > 0) {
            return;
        }
        mapSheetBean.saveMapSheet();
        MessageUtility.displayMessage(ClientMessage.ADMIN_MAPSHEET_SAVED,
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
    }

    private void disableButton() {
        if (!editMode) {
            btnSave.setText(MessageUtility.getLocalizedMessageText(ClientMessage.GENERAL_LABELS_CREATE));
        } else {
            btnSave.setText(MessageUtility.getLocalizedMessageText(ClientMessage.GENERAL_LABELS_SAVE));
        }
    }

    private void addMapSheet() {
        editMode = false;
        setMapSheetBean(new MapSheetBean());
        showHidePanel(true);
        disableButton();
    }

    private void editMapSheet() {
        editMode = true;
        if (mapSheetListBean.getSelectedMapSheet() != null) {
            setMapSheetBean((MapSheetBean) mapSheetListBean.getSelectedMapSheet().copy());
            showHidePanel(true);
        }
        disableButton();
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        mapSheetListBean = new org.sola.clients.beans.cadastre.MapSheetListBean();
        officeListBean1 = new org.sola.clients.beans.referencedata.OfficeListBean();
        officeListBean = new org.sola.clients.beans.referencedata.OfficeListBean();
        popUpMapSheetList = new javax.swing.JPopupMenu();
        menuAdd = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenuItem();
        menuRemove = new javax.swing.JMenuItem();
        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        toolbarMapsheet = new javax.swing.JToolBar();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMapsheets = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        txtOffice1 = new javax.swing.JTextField();
        panelCreateEdit = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtOffice = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbMapSheetType = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMapSheetNo = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cmbSRID = new javax.swing.JComboBox();
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

        headerPanel1.setTitleText("Map Sheets Management");

        toolbarMapsheet.setFloatable(false);
        toolbarMapsheet.setRollover(true);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/cadastre/Bundle"); // NOI18N
        btnAdd.setText(bundle.getString("ReferenceDataManagementPanel.btnAddRefData.text")); // NOI18N
        btnAdd.setFocusable(false);
        btnAdd.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        toolbarMapsheet.add(btnAdd);

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        btnEdit.setText(bundle.getString("ReferenceDataManagementPanel.btnEditRefData.text")); // NOI18N
        btnEdit.setFocusable(false);
        btnEdit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        toolbarMapsheet.add(btnEdit);

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemove.setText(bundle.getString("ReferenceDataManagementPanel.btnRemoveRefData.text")); // NOI18N
        btnRemove.setFocusable(false);
        btnRemove.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRemove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        toolbarMapsheet.add(btnRemove);

        jLabel5.setText("Office:");

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${mapSheets}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mapSheetListBean, eLProperty, tblMapsheets);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${sheetTypeString}"));
        columnBinding.setColumnName("Sheet Type");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mapNumber}"));
        columnBinding.setColumnName("Map Number");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${office.displayValue}"));
        columnBinding.setColumnName("Office");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${srid}"));
        columnBinding.setColumnName("Srid");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mapSheetListBean, org.jdesktop.beansbinding.ELProperty.create("${selectedMapSheet}"), tblMapsheets, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(tblMapsheets);

        txtOffice1.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${currentOffice.displayValue}"), txtOffice1, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(toolbarMapsheet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(txtOffice1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(toolbarMapsheet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtOffice1)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE))
        );

        jPanel2.setLayout(new java.awt.GridLayout(4, 0, 0, 5));

        jLabel3.setText("Office");

        txtOffice.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${currentOffice.displayValue}"), txtOffice, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 359, Short.MAX_VALUE))
            .addComponent(txtOffice)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOffice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel6);

        jLabel2.setText("Map Sheet Type");

        cmbMapSheetType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Control Sheet", "Free Sheet" }));
        cmbMapSheetType.setName(""); // NOI18N
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
            .addComponent(cmbMapSheetType, 0, 388, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbMapSheetType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
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
                .addGap(0, 321, Short.MAX_VALUE))
            .addComponent(txtMapSheetNo)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMapSheetNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3);

        jLabel4.setText("SRID");

        cmbSRID.setName(""); // NOI18N
        cmbSRID.setOpaque(false);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${srids}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, cmbSRID);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${mapSheetBean.srid}"), cmbSRID, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbSRID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSRIDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(cmbSRID, 0, 388, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSRID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel8);

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

        javax.swing.GroupLayout panelCreateEditLayout = new javax.swing.GroupLayout(panelCreateEdit);
        panelCreateEdit.setLayout(panelCreateEditLayout);
        panelCreateEditLayout.setHorizontalGroup(
            panelCreateEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(groupPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCreateEditLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelCreateEditLayout.setVerticalGroup(
            panelCreateEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCreateEditLayout.createSequentialGroup()
                .addComponent(groupPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCreateEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnSave))
                .addContainerGap(185, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelCreateEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelCreateEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1020, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveMapSheet();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        addMapSheet();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editMapSheet();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        removeMapSheet();
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        setMapSheetBean(null);
        showHidePanel(false);
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

    private void cmbSRIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSRIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSRIDActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cmbMapSheetType;
    private javax.swing.JComboBox cmbSRID;
    private org.sola.clients.swing.ui.GroupPanel groupPanel1;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private org.sola.clients.beans.cadastre.MapSheetListBean mapSheetListBean;
    private javax.swing.JMenuItem menuAdd;
    private javax.swing.JMenuItem menuEdit;
    private javax.swing.JMenuItem menuRemove;
    private org.sola.clients.beans.referencedata.OfficeListBean officeListBean;
    private org.sola.clients.beans.referencedata.OfficeListBean officeListBean1;
    private javax.swing.JPanel panelCreateEdit;
    private javax.swing.JPopupMenu popUpMapSheetList;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tblMapsheets;
    private javax.swing.JToolBar toolbarMapsheet;
    private javax.swing.JTextField txtMapSheetNo;
    private javax.swing.JTextField txtOffice;
    private javax.swing.JTextField txtOffice1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
