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
package org.sola.clients.swing.desktop.administrative;

import java.awt.Frame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.sola.clients.beans.administrative.LocSearchByMothParamsListBean;
import org.sola.clients.beans.administrative.LocWithMothBean;
import org.sola.clients.beans.administrative.MothBean;
import org.sola.clients.beans.referencedata.MothTypeListBean;
import org.sola.clients.beans.referencedata.VdcListBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 */
public class MothManagementForm2 extends ContentPanel {

    public static final String LOC_SAVED = "locSaved";

    /**
     * Creates new form MothManagementForm2
     */
    public MothManagementForm2() {
        initComponents();
        postInit();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        vdcListBean1 = new org.sola.clients.beans.referencedata.VdcListBean();
        vdcListBean2 = new org.sola.clients.beans.referencedata.VdcListBean();
        mothTypeListBean1 = new org.sola.clients.beans.referencedata.MothTypeListBean();
        mothTypeListBean2 = new org.sola.clients.beans.referencedata.MothTypeListBean();
        mothListBean1 = new org.sola.clients.beans.administrative.MothListBean();
        mothListBean2 = new org.sola.clients.beans.administrative.MothListBean();
        mothSearchParamsBean1 = new org.sola.clients.beans.administrative.MothSearchParamsBean();
        locSearchByMothParamsBean1 = new org.sola.clients.beans.administrative.LocSearchByMothParamsBean();
        locSearchByMothParamsListBean1 = new org.sola.clients.beans.administrative.LocSearchByMothParamsListBean();
        popUpForMoth = new javax.swing.JPopupMenu();
        newMothMenu = new javax.swing.JMenuItem();
        editMothMenu = new javax.swing.JMenuItem();
        removeMothMenu = new javax.swing.JMenuItem();
        popUpforLoc = new javax.swing.JPopupMenu();
        newPageMenu = new javax.swing.JMenuItem();
        editPageMenu = new javax.swing.JMenuItem();
        multiplePageMenu = new javax.swing.JMenuItem();
        removePageMenu = new javax.swing.JMenuItem();
        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMoths = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        jToolBar1 = new javax.swing.JToolBar();
        btnNewMoth = new javax.swing.JButton();
        btnEditMoth = new javax.swing.JButton();
        btnRemoveMoth = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbVdc1 = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbMothType = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtMothLujNumber = new javax.swing.JTextField();
        groupPanel1 = new org.sola.clients.swing.ui.GroupPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnSearch1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePages = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        jToolBar2 = new javax.swing.JToolBar();
        btnNewPage = new javax.swing.JButton();
        btnEditPage = new javax.swing.JButton();
        btnMultipleEdit = new javax.swing.JButton();
        btnRemovePage = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cmbVdc2 = new javax.swing.JComboBox();
        jPanel11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cmbMothType2 = new javax.swing.JComboBox();
        jPanel12 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        cmbMothLujNo = new javax.swing.JComboBox();
        jPanel13 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtPageNumber = new javax.swing.JTextField();
        groupPanel2 = new org.sola.clients.swing.ui.GroupPanel();

        newMothMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/administrative/Bundle"); // NOI18N
        newMothMenu.setText(bundle.getString("MothManagementForm2.newMothMenu.text")); // NOI18N
        newMothMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMothMenuActionPerformed(evt);
            }
        });
        popUpForMoth.add(newMothMenu);

        editMothMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        editMothMenu.setText(bundle.getString("MothManagementForm2.editMothMenu.text")); // NOI18N
        editMothMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMothMenuActionPerformed(evt);
            }
        });
        popUpForMoth.add(editMothMenu);

        removeMothMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        removeMothMenu.setText(bundle.getString("MothManagementForm2.removeMothMenu.text")); // NOI18N
        removeMothMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeMothMenuActionPerformed(evt);
            }
        });
        popUpForMoth.add(removeMothMenu);

        newPageMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        newPageMenu.setText(bundle.getString("MothManagementForm2.newPageMenu.text")); // NOI18N
        newPageMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newPageMenuActionPerformed(evt);
            }
        });
        popUpforLoc.add(newPageMenu);

        editPageMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        editPageMenu.setText(bundle.getString("MothManagementForm2.editPageMenu.text")); // NOI18N
        editPageMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editPageMenuActionPerformed(evt);
            }
        });
        popUpforLoc.add(editPageMenu);

        multiplePageMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/documents_edit.png"))); // NOI18N
        multiplePageMenu.setText(bundle.getString("MothManagementForm2.multiplePageMenu.text")); // NOI18N
        multiplePageMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                multiplePageMenuActionPerformed(evt);
            }
        });
        popUpforLoc.add(multiplePageMenu);

        removePageMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        removePageMenu.setText(bundle.getString("MothManagementForm2.removePageMenu.text")); // NOI18N
        removePageMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removePageMenuActionPerformed(evt);
            }
        });
        popUpforLoc.add(removePageMenu);

        setHeaderPanel(headerPanel1);

        headerPanel1.setTitleText(bundle.getString("MothManagementForm2.headerPanel1.titleText")); // NOI18N

        jLabel4.setText(bundle.getString("MothManagementForm2.jLabel4.text")); // NOI18N

        btnSearch.setText(bundle.getString("MothManagementForm2.btnSearch.text")); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4)
            .addComponent(btnSearch)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch))
        );

        tableMoths.setComponentPopupMenu(popUpForMoth);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${moths}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothListBean2, eLProperty, tableMoths);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${vdc.displayValue}"));
        columnBinding.setColumnName("Vdc.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mothType.mothTypeName}"));
        columnBinding.setColumnName("Moth Type.moth Type Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mothlujNumber}"));
        columnBinding.setColumnName("Mothluj Number");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothListBean2, org.jdesktop.beansbinding.ELProperty.create("${selectedMoth}"), tableMoths, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(tableMoths);
        tableMoths.getColumnModel().getColumn(0).setResizable(false);
        tableMoths.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("MothManagementForm2.tableMoths.columnModel.title0")); // NOI18N
        tableMoths.getColumnModel().getColumn(1).setResizable(false);
        tableMoths.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("MothManagementForm2.tableMoths.columnModel.title1")); // NOI18N
        tableMoths.getColumnModel().getColumn(2).setResizable(false);
        tableMoths.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("MothManagementForm2.tableMoths.columnModel.title2")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnNewMoth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        btnNewMoth.setText(bundle.getString("MothManagementForm2.btnNewMoth.text")); // NOI18N
        btnNewMoth.setFocusable(false);
        btnNewMoth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewMothActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNewMoth);

        btnEditMoth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        btnEditMoth.setText(bundle.getString("MothManagementForm2.btnEditMoth.text")); // NOI18N
        btnEditMoth.setFocusable(false);
        btnEditMoth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditMothActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEditMoth);

        btnRemoveMoth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemoveMoth.setText(bundle.getString("MothManagementForm2.btnRemoveMoth.text")); // NOI18N
        btnRemoveMoth.setFocusable(false);
        btnRemoveMoth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveMothActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRemoveMoth);

        jPanel4.setLayout(new java.awt.GridLayout(1, 3, 15, 0));

        jLabel1.setText(bundle.getString("MothManagementForm2.jLabel1.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${vdcs}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vdcListBean1, eLProperty, cmbVdc1);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothSearchParamsBean1, org.jdesktop.beansbinding.ELProperty.create("${vdc}"), cmbVdc1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(cmbVdc1, 0, 198, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbVdc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel1);

        jLabel2.setText(bundle.getString("MothManagementForm2.jLabel2.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${mothTypes}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothTypeListBean1, eLProperty, cmbMothType);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothSearchParamsBean1, org.jdesktop.beansbinding.ELProperty.create("${mothTypeBean}"), cmbMothType, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(cmbMothType, 0, 198, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbMothType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel2);

        jLabel3.setText(bundle.getString("MothManagementForm2.jLabel3.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothSearchParamsBean1, org.jdesktop.beansbinding.ELProperty.create("${mothlujNumber}"), txtMothLujNumber, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 117, Short.MAX_VALUE))
            .addComponent(txtMothLujNumber)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMothLujNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel3);

        groupPanel1.setTitleText(bundle.getString("MothManagementForm2.groupPanel1.titleText")); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(groupPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(groupPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel5.setText(bundle.getString("MothManagementForm2.jLabel5.text")); // NOI18N

        btnSearch1.setText(bundle.getString("MothManagementForm2.btnSearch1.text")); // NOI18N
        btnSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5)
            .addComponent(btnSearch1)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch1))
        );

        tablePages.setComponentPopupMenu(popUpforLoc);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${searchResults}");
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, locSearchByMothParamsListBean1, eLProperty, tablePages);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${checked}"));
        columnBinding.setColumnName("Checked");
        columnBinding.setColumnClass(Boolean.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${moth.vdc.displayValue}"));
        columnBinding.setColumnName("Moth.vdc.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${moth.mothType.mothTypeName}"));
        columnBinding.setColumnName("Moth.moth Type.moth Type Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${moth.mothlujNumber}"));
        columnBinding.setColumnName("Moth.mothluj Number");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${tmpPanaNo}"));
        columnBinding.setColumnName("Tmp Pana No");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${panaNo}"));
        columnBinding.setColumnName("Pana No");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, locSearchByMothParamsListBean1, org.jdesktop.beansbinding.ELProperty.create("${selectedResult}"), tablePages, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(tablePages);
        tablePages.getColumnModel().getColumn(0).setPreferredWidth(30);
        tablePages.getColumnModel().getColumn(0).setMaxWidth(30);
        tablePages.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("MothManagementForm2.tablePages.columnModel.title5_1")); // NOI18N
        tablePages.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("MothManagementForm2.tablePages.columnModel.title0")); // NOI18N
        tablePages.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("MothManagementForm2.tablePages.columnModel.title1")); // NOI18N
        tablePages.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("MothManagementForm2.tablePages.columnModel.title2")); // NOI18N
        tablePages.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("MothManagementForm2.tablePages.columnModel.title3_1")); // NOI18N
        tablePages.getColumnModel().getColumn(5).setHeaderValue(bundle.getString("MothManagementForm2.tablePages.columnModel.title4_1")); // NOI18N

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        btnNewPage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        btnNewPage.setText(bundle.getString("MothManagementForm2.btnNewPage.text")); // NOI18N
        btnNewPage.setFocusable(false);
        btnNewPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewPageActionPerformed(evt);
            }
        });
        jToolBar2.add(btnNewPage);

        btnEditPage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        btnEditPage.setText(bundle.getString("MothManagementForm2.btnEditPage.text")); // NOI18N
        btnEditPage.setFocusable(false);
        btnEditPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditPageActionPerformed(evt);
            }
        });
        jToolBar2.add(btnEditPage);

        btnMultipleEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/documents_edit.png"))); // NOI18N
        btnMultipleEdit.setText(bundle.getString("MothManagementForm2.btnMultipleEdit.text")); // NOI18N
        btnMultipleEdit.setFocusable(false);
        btnMultipleEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMultipleEditActionPerformed(evt);
            }
        });
        jToolBar2.add(btnMultipleEdit);

        btnRemovePage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemovePage.setText(bundle.getString("MothManagementForm2.btnRemovePage.text")); // NOI18N
        btnRemovePage.setFocusable(false);
        btnRemovePage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemovePageActionPerformed(evt);
            }
        });
        jToolBar2.add(btnRemovePage);

        jPanel9.setLayout(new java.awt.GridLayout(1, 3, 15, 0));

        jLabel6.setText(bundle.getString("MothManagementForm2.jLabel6.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${vdcs}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vdcListBean2, eLProperty, cmbVdc2);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vdcListBean2, org.jdesktop.beansbinding.ELProperty.create("${selectedVdc}"), cmbVdc2, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(cmbVdc2, 0, 144, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbVdc2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel10);

        jLabel7.setText(bundle.getString("MothManagementForm2.jLabel7.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${mothTypes}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothTypeListBean2, eLProperty, cmbMothType2);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothTypeListBean2, org.jdesktop.beansbinding.ELProperty.create("${selectedMothType}"), cmbMothType2, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(cmbMothType2, 0, 144, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbMothType2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel11);

        jLabel8.setText(bundle.getString("MothManagementForm2.jLabel8.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${moths}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothListBean1, eLProperty, cmbMothLujNo);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, locSearchByMothParamsBean1, org.jdesktop.beansbinding.ELProperty.create("${moth}"), cmbMothLujNo, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 63, Short.MAX_VALUE))
            .addComponent(cmbMothLujNo, 0, 144, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbMothLujNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel12);

        jLabel9.setText(bundle.getString("MothManagementForm2.jLabel9.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, locSearchByMothParamsBean1, org.jdesktop.beansbinding.ELProperty.create("${pageNumber}"), txtPageNumber, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(0, 81, Short.MAX_VALUE))
            .addComponent(txtPageNumber)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPageNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel13);

        groupPanel2.setTitleText(bundle.getString("MothManagementForm2.groupPanel2.titleText")); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(groupPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(groupPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void postInit() {
        vdcListBean1.loadListByOffice(false);
        vdcListBean2.loadListByOffice(false);
        cmbVdc1.setSelectedIndex(-1);
        cmbVdc2.setSelectedIndex(-1);

        vdcListBean2.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(VdcListBean.SELECTED_VDC_PROPERTY)) {
                    customizeMothType();
                }
            }
        });
        mothTypeListBean2.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(MothTypeListBean.SELECTED_MOTH_TYPE_PROPERTY)) {
                    searchMoth();
                }
            }
        });
        mothListBean2.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                customizeMothButton();
            }
        });

        locSearchByMothParamsListBean1.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                customizeLocButton(evt);
            }
        });

        customizeLocButton(null);
        customizeMothType();
        customizeMothButton();
    }

    /**
     * Enables or disables toolbar buttons for loc list, .
     */
    private void customizeLocButton(PropertyChangeEvent evt) {
        if (evt == null) {
            btnEditPage.setEnabled(false);
            btnRemovePage.setEnabled(false);
            btnMultipleEdit.setEnabled(false);
            editPageMenu.setEnabled(false);
            removePageMenu.setEnabled(false);
            multiplePageMenu.setEnabled(false);
            return;
        }

        switch (evt.getPropertyName()) {
            case LocSearchByMothParamsListBean.CHECKED_LOCS_PROPERTY:
                boolean enable = (Boolean) evt.getNewValue();
                btnMultipleEdit.setEnabled(enable);
                btnRemovePage.setEnabled(enable);
                multiplePageMenu.setEnabled(enable);
                removePageMenu.setEnabled(enable);
                break;
            case LocSearchByMothParamsListBean.SELECTED_RESULT_PROPERTY:
                boolean isEditEnabled;
                LocWithMothBean loc = (LocWithMothBean) evt.getNewValue();
                if (loc == null) {
                    isEditEnabled = false;
                } else {
                    isEditEnabled = true;
                }
                btnEditPage.setEnabled(isEditEnabled);
                editPageMenu.setEnabled(isEditEnabled);
                break;
        }
    }

    private void customizeMothButton() {
        boolean enable = mothListBean2.getSelectedMoth() != null;
        btnEditMoth.setEnabled(enable);
        btnRemoveMoth.setEnabled(enable);
        editMothMenu.setEnabled(enable);
        removeMothMenu.setEnabled(enable);
    }

    private void customizeMothType() {
        cmbMothType2.setEnabled(vdcListBean2.getSelectedVdc() != null);
        cmbMothType2.setSelectedIndex(-1);
    }

    private void searchMoth() {
        mothListBean1.getMoths().clear();
        if (vdcListBean2.getSelectedVdc() != null && mothTypeListBean2.getSelectedMothType() != null) {
            cmbMothLujNo.setEnabled(true);
            mothListBean1.loadMothList(vdcListBean2.getSelectedVdc().getCode(),
                    mothTypeListBean2.getSelectedMothType().getMothTypeCode());
        } else {
            cmbMothLujNo.setEnabled(false);
        }
        cmbMothLujNo.setSelectedIndex(-1);

    }
    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        searchMoths();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void searchMoths() {
        SolaTask t = new SolaTask<Void, Void>() {
            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_PROPERTY_SEARCHING));
                mothListBean2.search(mothSearchParamsBean1);
                return null;
            }

            @Override
            public void taskDone() {
                if (mothListBean2.getMoths().size() < 1) {
                    MessageUtility.displayMessage(ClientMessage.SEARCH_NO_RESULTS);
                } else if (mothListBean2.getMoths().size() > 100) {
                    MessageUtility.displayMessage(ClientMessage.SEARCH_TOO_MANY_RESULTS, new String[]{"100"});
                }
            }
        };
        TaskManager.getInstance().runTask(t);
    }
    private void btnNewMothActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewMothActionPerformed
        // TODO add your handling code here:
        openMothForm();
    }//GEN-LAST:event_btnNewMothActionPerformed

    private void openMothForm() {
        Frame frame = new Frame();
        MothForm frm = new MothForm(frame, true, null);
        frm.setVisible(true);
    }

    private void btnSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch1ActionPerformed
        // TODO add your handling code here:
        if (locSearchByMothParamsBean1.getMoth() != null) {
            searchLocs();
        } else {
            locSearchByMothParamsListBean1.getSearchResults().clear();
            MessageUtility.displayMessage(ClientMessage.SEARCH_NO_RESULTS);
            return;
        }

    }//GEN-LAST:event_btnSearch1ActionPerformed

    private void btnNewPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewPageActionPerformed
        // TODO add your handling code here:
        openMothPageForm();
    }//GEN-LAST:event_btnNewPageActionPerformed
    private void openMothForEdit() {
        Frame frame = new Frame();
        MothForm frm = new MothForm(frame, true, (MothBean) mothListBean2.getSelectedMoth());
        frm.setVisible(true);
        searchMoths();
    }
    private void btnEditMothActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditMothActionPerformed
        // TODO add your handling code here:
        openMothForEdit();
    }//GEN-LAST:event_btnEditMothActionPerformed

    private void openMothPageForEdit() {
        Frame frame = new Frame();
        MothPageForm frm = new MothPageForm(frame, true, locSearchByMothParamsListBean1.getSelectedResult());
        frm.setVisible(true);
        searchLocs();
    }
    private void btnEditPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditPageActionPerformed
        // TODO add your handling code here:
        openMothPageForEdit();
    }//GEN-LAST:event_btnEditPageActionPerformed

    private void btnRemoveMothActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveMothActionPerformed
        // TODO add your handling code here:
        removeMoth();
    }//GEN-LAST:event_btnRemoveMothActionPerformed

    private void removePage() {
        if (MessageUtility.displayMessage(ClientMessage.CONFIRM_DELETE_RECORD)
                == MessageUtility.BUTTON_ONE) {
            locSearchByMothParamsListBean1.removeSelected();
        }
    }
    private void btnRemovePageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemovePageActionPerformed
        // TODO add your handling code here:  
        removePage();
    }//GEN-LAST:event_btnRemovePageActionPerformed
    private void openMultipleEdit() {
        SolaTask t = new SolaTask<Void, Void>() {
            @Override
            public Void doTask() {
                Frame frame = new Frame();
                MothMultiplePageEditForm frm = new MothMultiplePageEditForm(frame, true, locSearchByMothParamsListBean1.getCheckedLocs());
                cmbVdc2.setSelectedIndex(-1);
                frm.setVisible(true);
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }
    private void btnMultipleEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMultipleEditActionPerformed
        // TODO add your handling code here:
        openMultipleEdit();
    }//GEN-LAST:event_btnMultipleEditActionPerformed

    private void editMothMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMothMenuActionPerformed
        // TODO add your handling code here:
        openMothForEdit();
    }//GEN-LAST:event_editMothMenuActionPerformed

    private void removeMothMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeMothMenuActionPerformed
        // TODO add your handling code here:
        removeMoth();
    }//GEN-LAST:event_removeMothMenuActionPerformed

    private void removeMoth() {
        if (MessageUtility.displayMessage(ClientMessage.CONFIRM_DELETE_RECORD)
                == MessageUtility.BUTTON_ONE) {
            mothListBean2.removeSelected();
        }
    }
    private void newMothMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMothMenuActionPerformed
        // TODO add your handling code here:
        openMothForm();
    }//GEN-LAST:event_newMothMenuActionPerformed

    private void removePageMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removePageMenuActionPerformed
        // TODO add your handling code here:
        if (MessageUtility.displayMessage(ClientMessage.CONFIRM_DELETE_RECORD)
                == MessageUtility.BUTTON_ONE) {
            locSearchByMothParamsListBean1.removeSelected();
        }
    }//GEN-LAST:event_removePageMenuActionPerformed

    private void newPageMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newPageMenuActionPerformed
        // TODO add your handling code here:
        openMothPageForm();
    }//GEN-LAST:event_newPageMenuActionPerformed

    private void editPageMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editPageMenuActionPerformed
        // TODO add your handling code here:
        openMothPageForEdit();
    }//GEN-LAST:event_editPageMenuActionPerformed

    private void multiplePageMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_multiplePageMenuActionPerformed
        // TODO add your handling code here:
        openMultipleEdit();
    }//GEN-LAST:event_multiplePageMenuActionPerformed

    private void openMothPageForm() {
        Frame frame = new Frame();
        MothPageForm frm = new MothPageForm(frame, true, null);
        frm.setVisible(true);
    }

    private void searchLocs() {
        SolaTask t = new SolaTask<Void, Void>() {
            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_PROPERTY_SEARCHING));
                locSearchByMothParamsListBean1.search(locSearchByMothParamsBean1);
                return null;
            }

            @Override
            public void taskDone() {
                if (locSearchByMothParamsListBean1.getSearchResults().size() < 1) {
                    MessageUtility.displayMessage(ClientMessage.SEARCH_NO_RESULTS);
                } else if (locSearchByMothParamsListBean1.getSearchResults().size() > 100) {
                    MessageUtility.displayMessage(ClientMessage.SEARCH_TOO_MANY_RESULTS, new String[]{"100"});
                }
            }
        };
        TaskManager.getInstance().runTask(t);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditMoth;
    private javax.swing.JButton btnEditPage;
    private javax.swing.JButton btnMultipleEdit;
    private javax.swing.JButton btnNewMoth;
    private javax.swing.JButton btnNewPage;
    private javax.swing.JButton btnRemoveMoth;
    private javax.swing.JButton btnRemovePage;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearch1;
    private javax.swing.JComboBox cmbMothLujNo;
    private javax.swing.JComboBox cmbMothType;
    private javax.swing.JComboBox cmbMothType2;
    private javax.swing.JComboBox cmbVdc1;
    private javax.swing.JComboBox cmbVdc2;
    private javax.swing.JMenuItem editMothMenu;
    private javax.swing.JMenuItem editPageMenu;
    private org.sola.clients.swing.ui.GroupPanel groupPanel1;
    private org.sola.clients.swing.ui.GroupPanel groupPanel2;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private org.sola.clients.beans.administrative.LocSearchByMothParamsBean locSearchByMothParamsBean1;
    private org.sola.clients.beans.administrative.LocSearchByMothParamsListBean locSearchByMothParamsListBean1;
    private org.sola.clients.beans.administrative.MothListBean mothListBean1;
    private org.sola.clients.beans.administrative.MothListBean mothListBean2;
    private org.sola.clients.beans.administrative.MothSearchParamsBean mothSearchParamsBean1;
    private org.sola.clients.beans.referencedata.MothTypeListBean mothTypeListBean1;
    private org.sola.clients.beans.referencedata.MothTypeListBean mothTypeListBean2;
    private javax.swing.JMenuItem multiplePageMenu;
    private javax.swing.JMenuItem newMothMenu;
    private javax.swing.JMenuItem newPageMenu;
    private javax.swing.JPopupMenu popUpForMoth;
    private javax.swing.JPopupMenu popUpforLoc;
    private javax.swing.JMenuItem removeMothMenu;
    private javax.swing.JMenuItem removePageMenu;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableMoths;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tablePages;
    private javax.swing.JTextField txtMothLujNumber;
    private javax.swing.JTextField txtPageNumber;
    private org.sola.clients.beans.referencedata.VdcListBean vdcListBean1;
    private org.sola.clients.beans.referencedata.VdcListBean vdcListBean2;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
