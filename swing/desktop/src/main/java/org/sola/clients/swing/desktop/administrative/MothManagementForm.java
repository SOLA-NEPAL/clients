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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.sola.clients.beans.administrative.LocBean;
import org.sola.clients.beans.administrative.LocListBean;
import org.sola.clients.beans.administrative.LocWithMothBean;
import org.sola.clients.beans.administrative.MothBean;
import org.sola.clients.beans.administrative.MothListBean;
import org.sola.clients.beans.referencedata.MothTypeBean;
import org.sola.clients.beans.referencedata.MothTypeListBean;
import org.sola.clients.beans.referencedata.VdcBean;
import org.sola.clients.beans.referencedata.VdcListBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.desktop.MainForm;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author KumarKhadka
 */
public class MothManagementForm extends ContentPanel {

    public static final String MOTH_SAVED = "mothSaved";
    public static final String LOC_SAVED = "locSaved";
    public static final String MOTH_BEAN_PROPERTY = "mothBean";
    public static final String LOC_BEAN_PROPERTY = "locBean";
    public static final String LOC_FOUND = "locFound";
    private MothBean mothBean;
    private LocBean locBean;
    private boolean editMode = false;

    /**
     * Creates new form MothManagementForm
     */
    public MothManagementForm() {
        initComponents();
        createMothSetup();
        customizeMothType();
        mothButtonEnable();
        locButtonEnable();
        setUpMothBean(mothBean);
        setUpLocBean(locBean);
        pnlPageNo.setVisible(false);
        postInit();
    }

    private void mothButtonEnable() {
        btnEditMoth.setEnabled(mothList.getSelectedMoth() != null);
        btnRemoveMoth.setEnabled(mothList.getSelectedMoth() != null);
    }

    private void locButtonEnable() {
        btnRemoveLoc.setEnabled(locList.getSelectedLoc() != null);
    }

    public LocBean getLocBean() {
        return locBean;
    }

    public void setLocBean(LocBean locBean) {
        setUpLocBean(locBean);
    }

    private void setUpLocBean(LocBean locBean) {
        if (locBean != null) {
            this.locBean = locBean;
        } else {
            this.locBean = new LocBean();
        }
        firePropertyChange(LOC_BEAN_PROPERTY, null, this.locBean);
    }

    public MothBean getMothBean() {
        return mothBean;
    }

    private void setUpMothBean(MothBean mothBean) {
        if (mothBean != null) {
            this.mothBean = mothBean;
        } else {
            this.mothBean = new MothBean();
        }
        firePropertyChange(MOTH_BEAN_PROPERTY, null, this.mothBean);
    }

    public void setMothBean(MothBean mothBean) {
        setUpMothBean(mothBean);
    }

    private void postInit() {

        locList.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(LocListBean.SELECTED_LOC)) {
                    locButtonEnable();
                }
            }
        });
        mothList.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(MothListBean.SELECTED_MOTH)) {
                    mothButtonEnable();
                }
            }
        });

        vdcListBean2.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(VdcListBean.SELECTED_VDC_PROPERTY)) {
                    customizeMothType();
                }
            }
        });


        mothTypes2.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(MothTypeListBean.SELECTED_MOTH_TYPE_PROPERTY)) {
                    VdcBean vdc = (VdcBean) cmbVdc2.getSelectedItem();
                    MothTypeBean mothType = (MothTypeBean) cmbMothLuj2.getSelectedItem();
                    mothList.loadMothList(vdc.getCode(), mothType.getMothTypeCode());
                }

            }
        });

        mothTypes3.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(MothTypeListBean.SELECTED_MOTH_TYPE_PROPERTY)) {
                    VdcBean vdc = (VdcBean) cmbVdc3.getSelectedItem();
                    MothTypeBean mothType = (MothTypeBean) cmbMothLuj3.getSelectedItem();
                    mothList2.loadMothList(vdc.getCode(), mothType.getMothTypeCode());
                    cmbMothLujNo.setSelectedIndex(-1);
                }
            }
        });

        mothList2.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(MothListBean.SELECTED_MOTH)) {
                    MothBean moth = (MothBean) cmbMothLujNo.getSelectedItem();
                    if (moth != null) {
                        locList.loadLocList(moth.getId());
                        cmbPageNo.setSelectedIndex(-1);
                    }
                }
            }
        });

    }

    private void customizeMothType() {
        boolean enable = vdcListBean2.getSelectedVdc() != null;
        cmbMothLuj2.setEnabled(enable);
    }

    private void createMothSetup() {
        vdcListBean1.loadListByOffice(true);
        cmbVdc1.setSelectedIndex(-1);
        vdcListBean2.loadListByOffice(true);
        cmbVdc2.setSelectedIndex(-1);
        vdcListBean3.loadListByOffice(true);
        cmbVdc3.setSelectedIndex(-1);

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

        mothList = new org.sola.clients.beans.administrative.MothListBean();
        vdcListBean2 = new org.sola.clients.beans.referencedata.VdcListBean();
        vdcListBean1 = new org.sola.clients.beans.referencedata.VdcListBean();
        mothTypes2 = new org.sola.clients.beans.referencedata.MothTypeListBean();
        locList = new org.sola.clients.beans.administrative.LocListBean();
        mothTypes1 = new org.sola.clients.beans.referencedata.MothTypeListBean();
        vdcListBean3 = new org.sola.clients.beans.referencedata.VdcListBean();
        mothTypes3 = new org.sola.clients.beans.referencedata.MothTypeListBean();
        mothList2 = new org.sola.clients.beans.administrative.MothListBean();
        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        mothPanel = new javax.swing.JPanel();
        groupPanel1 = new org.sola.clients.swing.ui.GroupPanel();
        jToolBar2 = new javax.swing.JToolBar();
        btnNewMoth = new javax.swing.JButton();
        btnSaveMoth = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbVdc1 = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        cmbMothLuj1 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtMothLujNo = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        groupPanel4 = new org.sola.clients.swing.ui.GroupPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnNewLoc = new javax.swing.JButton();
        btnSaveLoc = new javax.swing.JButton();
        btnEditLoc = new javax.swing.JButton();
        btnRemoveLoc = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cmbVdc3 = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbMothLuj3 = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmbMothLujNo = new javax.swing.JComboBox();
        pnlPageNo = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        cmbPageNo = new javax.swing.JComboBox();
        jPanel8 = new javax.swing.JPanel();
        txtPageNo = new javax.swing.JTextField();
        lblPageNo = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jToolBar6 = new javax.swing.JToolBar();
        btnEditMoth = new javax.swing.JButton();
        btnRemoveMoth = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableWithDefaultStyles3 = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        jPanel15 = new javax.swing.JPanel();
        cmbVdc2 = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbMothLuj2 = new javax.swing.JComboBox();

        setHeaderPanel(headerPanel1);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/administrative/Bundle"); // NOI18N
        headerPanel1.setTitleText(bundle.getString("MothSrestaEntry.headerPanel1.titleText")); // NOI18N

        groupPanel1.setTitleText(bundle.getString("MothSrestaEntry.groupPanel1.titleText")); // NOI18N

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        btnNewMoth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/new.png"))); // NOI18N
        btnNewMoth.setText(bundle.getString("MothSrestaEntry.btnNewMoth.text")); // NOI18N
        btnNewMoth.setFocusable(false);
        btnNewMoth.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNewMoth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewMothActionPerformed(evt);
            }
        });
        jToolBar2.add(btnNewMoth);

        btnSaveMoth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/save.png"))); // NOI18N
        btnSaveMoth.setText(bundle.getString("MothSrestaEntry.btnSaveMoth.text")); // NOI18N
        btnSaveMoth.setFocusable(false);
        btnSaveMoth.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSaveMoth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveMothActionPerformed(evt);
            }
        });
        jToolBar2.add(btnSaveMoth);

        jPanel7.setLayout(new java.awt.GridLayout(1, 2, 15, 0));

        jLabel1.setText(bundle.getString("MothSrestaEntry.jLabel1.text")); // NOI18N

        cmbVdc1.setEditable(true);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${vdcs}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vdcListBean1, eLProperty, cmbVdc1);
        bindingGroup.addBinding(jComboBoxBinding);
        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${mothBean.vdc}"), cmbVdc1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addContainerGap())
            .addComponent(cmbVdc1, 0, 243, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbVdc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 16, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel1);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${mothTypes}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothTypes1, eLProperty, cmbMothLuj1);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${mothBean.mothType}"), cmbMothLuj1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbMothLuj1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMothLuj1ItemStateChanged(evt);
            }
        });

        jLabel4.setText(bundle.getString("MothSrestaEntry.jLabel4.text")); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cmbMothLuj1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addContainerGap(201, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbMothLuj1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel5);

        jLabel5.setText(bundle.getString("MothSrestaEntry.jLabel5.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${mothBean.mothlujNumber}"), txtMothLujNo, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addContainerGap(185, Short.MAX_VALUE))
            .addComponent(txtMothLujNo)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMothLujNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel6);

        javax.swing.GroupLayout mothPanelLayout = new javax.swing.GroupLayout(mothPanel);
        mothPanel.setLayout(mothPanelLayout);
        mothPanelLayout.setHorizontalGroup(
            mothPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(groupPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
        );
        mothPanelLayout.setVerticalGroup(
            mothPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mothPanelLayout.createSequentialGroup()
                .addComponent(groupPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(444, 444, 444))
        );

        groupPanel4.setTitleText(bundle.getString("MothSrestaEntry.groupPanel4.titleText")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnNewLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/new.png"))); // NOI18N
        btnNewLoc.setText(bundle.getString("MothSrestaEntry.btnNewLoc.text")); // NOI18N
        btnNewLoc.setFocusable(false);
        btnNewLoc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNewLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewLocActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNewLoc);

        btnSaveLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/save.png"))); // NOI18N
        btnSaveLoc.setText(bundle.getString("MothSrestaEntry.btnSaveLoc.text")); // NOI18N
        btnSaveLoc.setFocusable(false);
        btnSaveLoc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSaveLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveLocActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSaveLoc);

        btnEditLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        btnEditLoc.setText(bundle.getString("MothSrestaEntry.btnEditLoc.text")); // NOI18N
        btnEditLoc.setFocusable(false);
        btnEditLoc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEditLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditLocActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEditLoc);

        btnRemoveLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemoveLoc.setText(bundle.getString("MothSrestaEntry.btnRemoveLoc.text")); // NOI18N
        btnRemoveLoc.setFocusable(false);
        btnRemoveLoc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemoveLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveLocActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRemoveLoc);

        jPanel4.setLayout(new java.awt.GridLayout(1, 3, 15, 0));

        jLabel9.setText(bundle.getString("MothSrestaEntry.jLabel9.text")); // NOI18N

        cmbVdc3.setEditable(true);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${vdcs}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vdcListBean3, eLProperty, cmbVdc3);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vdcListBean3, org.jdesktop.beansbinding.ELProperty.create("${selectedVdc}"), cmbVdc3, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbVdc3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbVdc3ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addContainerGap(152, Short.MAX_VALUE))
            .addComponent(cmbVdc3, 0, 190, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbVdc3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 27, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel11);

        jLabel2.setText(bundle.getString("MothSrestaEntry.jLabel2.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${mothTypes}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothTypes3, eLProperty, cmbMothLuj3);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothTypes3, org.jdesktop.beansbinding.ELProperty.create("${selectedMothType}"), cmbMothLuj3, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addContainerGap(121, Short.MAX_VALUE))
            .addComponent(cmbMothLuj3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbMothLuj3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 27, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel2);

        jLabel3.setText(bundle.getString("MothSrestaEntry.jLabel3.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${moths}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothList2, eLProperty, cmbMothLujNo);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothList2, org.jdesktop.beansbinding.ELProperty.create("${selectedMoth}"), cmbMothLujNo, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addContainerGap(132, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(cmbMothLujNo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbMothLujNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 27, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel3);

        jLabel18.setText(bundle.getString("MothSrestaEntry.jLabel18.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${locs}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, locList, eLProperty, cmbPageNo);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, locList, org.jdesktop.beansbinding.ELProperty.create("${selectedLoc}"), cmbPageNo, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout pnlPageNoLayout = new javax.swing.GroupLayout(pnlPageNo);
        pnlPageNo.setLayout(pnlPageNoLayout);
        pnlPageNoLayout.setHorizontalGroup(
            pnlPageNoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cmbPageNo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlPageNoLayout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlPageNoLayout.setVerticalGroup(
            pnlPageNoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPageNoLayout.createSequentialGroup()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbPageNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 27, Short.MAX_VALUE))
        );

        txtPageNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPageNoActionPerformed(evt);
            }
        });

        lblPageNo.setText(bundle.getString("MothSrestaEntry.lblPageNo.text")); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(lblPageNo)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtPageNo)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(lblPageNo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPageNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(groupPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlPageNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(groupPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlPageNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jToolBar6.setFloatable(false);
        jToolBar6.setRollover(true);

        btnEditMoth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        btnEditMoth.setText(bundle.getString("MothSrestaEntry.btnEditMoth.text")); // NOI18N
        btnEditMoth.setFocusable(false);
        btnEditMoth.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEditMoth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditMothActionPerformed(evt);
            }
        });
        jToolBar6.add(btnEditMoth);

        btnRemoveMoth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemoveMoth.setText(bundle.getString("MothSrestaEntry.btnRemoveMoth.text")); // NOI18N
        btnRemoveMoth.setFocusable(false);
        btnRemoveMoth.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemoveMoth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveMothActionPerformed(evt);
            }
        });
        jToolBar6.add(btnRemoveMoth);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${moths}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothList, eLProperty, jTableWithDefaultStyles3);
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
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothList, org.jdesktop.beansbinding.ELProperty.create("${selectedMoth}"), jTableWithDefaultStyles3, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane3.setViewportView(jTableWithDefaultStyles3);
        jTableWithDefaultStyles3.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("MothSrestaEntry.jTableWithDefaultStyles3.columnModel.title0_1")); // NOI18N
        jTableWithDefaultStyles3.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("MothSrestaEntry.jTableWithDefaultStyles3.columnModel.title1_1")); // NOI18N
        jTableWithDefaultStyles3.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("MothSrestaEntry.jTableWithDefaultStyles3.columnModel.title2_1")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${vdcs}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vdcListBean2, eLProperty, cmbVdc2);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vdcListBean2, org.jdesktop.beansbinding.ELProperty.create("${selectedVdc}"), cmbVdc2, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jLabel7.setText(bundle.getString("MothSrestaEntry.jLabel7.text")); // NOI18N

        jLabel8.setText(bundle.getString("MothSrestaEntry.jLabel8.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${mothTypes}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothTypes2, eLProperty, cmbMothLuj2);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothTypes2, org.jdesktop.beansbinding.ELProperty.create("${selectedMothType}"), cmbMothLuj2, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbMothLuj2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMothLuj2ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbVdc2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(cmbMothLuj2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cmbVdc2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(cmbMothLuj2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jToolBar6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jToolBar6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mothPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mothPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveMothActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveMothActionPerformed
        saveMoth(false);
    }//GEN-LAST:event_btnSaveMothActionPerformed

    public boolean validateLoc(boolean showMessage) {
        return locBean.validate(showMessage).size() < 1;
    }

    public boolean saveLoc() {
        if (validateLoc(true)) {
            return locBean.saveLoc();
        } else {
            return false;
        }
    }

    private void saveLoc(final boolean allowClose) {
        SolaTask<Boolean, Boolean> t = new SolaTask<Boolean, Boolean>() {
            @Override
            public Boolean doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_SAVING));
                return saveLoc();
            }

            @Override
            public void taskDone() {
                if (get() != null && get()) {
                    firePropertyChange(LOC_SAVED, false, true);
                    if (allowClose) {
                        close();
                    } else {
                        MessageUtility.displayMessage(ClientMessage.LOC_SAVED);
                        txtPageNo.setText("");
                        MainForm.saveBeanState(locBean);
                    }
                }
            }
        };
        TaskManager.getInstance().runTask(t);
    }

    public boolean validateMoth(boolean showMessage) {
        return mothBean.validate(showMessage).size() < 1;
    }

    public boolean saveMoth() {
        if (validateMoth(true)) {
            return mothBean.saveMoth();
        } else {
            return false;
        }
    }

    private void saveMoth(final boolean allowClose) {
        SolaTask<Boolean, Boolean> t = new SolaTask<Boolean, Boolean>() {
            @Override
            public Boolean doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_SAVING));
                return saveMoth();
            }

            @Override
            public void taskDone() {
                if (get() != null && get()) {
                    firePropertyChange(MOTH_SAVED, false, true);
                    if (allowClose) {
                        close();
                    } else {
                        MessageUtility.displayMessage(ClientMessage.MOTH_SAVED);
                        txtMothLujNo.setText(null);
                        MainForm.saveBeanState(mothBean);
                    }
                }
            }
        };
        TaskManager.getInstance().runTask(t);
    }

    private void createLoc() {
        if (mothList2.getSelectedMoth() != null && !txtPageNo.getText().isEmpty()) {
            LocWithMothBean loc = LocWithMothBean.searchLocByMothAndPage(
                    mothList2.getSelectedMoth(), txtPageNo.getText());
            if (loc != null) {
                MessageUtility.displayMessage(ClientMessage.LOC_EXIST);
                return;
            }
            if (loc == null) {
                if (MessageUtility.displayMessage(ClientMessage.LOC_NOT_FOUND_CREATE_NEW)
                        == MessageUtility.BUTTON_ONE) {
                    locBean.setMothId(mothList2.getSelectedMoth().getId());
                    locBean.setPanaNo(txtPageNo.getText());
                    saveLoc(false);
                } else {
                    return;
                }
            }
        }
    }

    private void cmbVdc3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbVdc3ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbVdc3ItemStateChanged

    private void txtPageNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPageNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPageNoActionPerformed

    private void btnEditLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditLocActionPerformed
        // TODO add your handling code here:
        editMothPage();
    }//GEN-LAST:event_btnEditLocActionPerformed

    private void editMothPage() {
        editMode = true;
        btnSaveLoc.setText("Save");
        pnlPageNo.setVisible(true);
        lblPageNo.setText("New Page No.");
    }
    private void btnRemoveLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveLocActionPerformed
        // TODO add your handling code here:
        if (MessageUtility.displayMessage(
                ClientMessage.LOC_REMOVED,
                new String[]{locList.getSelectedLoc().getPanaNo()}) == MessageUtility.BUTTON_ONE) {
            locList.removeSelected();
        }

    }//GEN-LAST:event_btnRemoveLocActionPerformed

    private void btnEditMothActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditMothActionPerformed
        // TODO add your handling code here:
        editMoth();

    }//GEN-LAST:event_btnEditMothActionPerformed

    private void editMoth() {
        btnSaveMoth.setText("Save");
        if (mothList.getSelectedMoth() != null) {
            setMothBean((MothBean) mothList.getSelectedMoth().copy());
        }
    }
    private void btnRemoveMothActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveMothActionPerformed
        // TODO add your handling code here:
        if (MessageUtility.displayMessage(
                ClientMessage.MOTH_REMOVED,
                new String[]{mothList.getSelectedMoth().getMothlujNumber()}) == MessageUtility.BUTTON_ONE) {
            mothList.removeSelected();
        }

    }//GEN-LAST:event_btnRemoveMothActionPerformed

    private void cmbMothLuj1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMothLuj1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbMothLuj1ItemStateChanged

    private void btnNewMothActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewMothActionPerformed
        // TODO add your handling code here:
        addNewMoth();
    }//GEN-LAST:event_btnNewMothActionPerformed

    private void cmbMothLuj2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMothLuj2ItemStateChanged
    }//GEN-LAST:event_cmbMothLuj2ItemStateChanged

    private void btnSaveLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveLocActionPerformed
        // TODO add your handling code here:
        if (editMode == false) {
            createLoc();
        } else {
            setLocBean(locList.getSelectedLoc());
            locBean.setPanaNo(txtPageNo.getText());
            saveLoc(false);
        }
    }//GEN-LAST:event_btnSaveLocActionPerformed

    private void btnNewLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewLocActionPerformed
        // TODO add your handling code here:
        createNewMothPage();
    }//GEN-LAST:event_btnNewLocActionPerformed

    private void createNewMothPage() {
        editMode = false;
        btnSaveLoc.setText("Create");
        pnlPageNo.setVisible(false);
        lblPageNo.setText("Page No.");
        setLocBean(new LocBean());
    }

    private void addNewMoth() {
        btnSaveMoth.setText("Create");
        setMothBean(new MothBean());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditLoc;
    private javax.swing.JButton btnEditMoth;
    private javax.swing.JButton btnNewLoc;
    private javax.swing.JButton btnNewMoth;
    private javax.swing.JButton btnRemoveLoc;
    private javax.swing.JButton btnRemoveMoth;
    private javax.swing.JButton btnSaveLoc;
    private javax.swing.JButton btnSaveMoth;
    private javax.swing.JComboBox cmbMothLuj1;
    private javax.swing.JComboBox cmbMothLuj2;
    private javax.swing.JComboBox cmbMothLuj3;
    private javax.swing.JComboBox cmbMothLujNo;
    private javax.swing.JComboBox cmbPageNo;
    private javax.swing.JComboBox cmbVdc1;
    private javax.swing.JComboBox cmbVdc2;
    private javax.swing.JComboBox cmbVdc3;
    private org.sola.clients.swing.ui.GroupPanel groupPanel1;
    private org.sola.clients.swing.ui.GroupPanel groupPanel4;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane3;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles jTableWithDefaultStyles3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar6;
    private javax.swing.JLabel lblPageNo;
    private org.sola.clients.beans.administrative.LocListBean locList;
    private org.sola.clients.beans.administrative.MothListBean mothList;
    private org.sola.clients.beans.administrative.MothListBean mothList2;
    private javax.swing.JPanel mothPanel;
    private org.sola.clients.beans.referencedata.MothTypeListBean mothTypes1;
    private org.sola.clients.beans.referencedata.MothTypeListBean mothTypes2;
    private org.sola.clients.beans.referencedata.MothTypeListBean mothTypes3;
    private javax.swing.JPanel pnlPageNo;
    private javax.swing.JTextField txtMothLujNo;
    private javax.swing.JTextField txtPageNo;
    private org.sola.clients.beans.referencedata.VdcListBean vdcListBean1;
    private org.sola.clients.beans.referencedata.VdcListBean vdcListBean2;
    private org.sola.clients.beans.referencedata.VdcListBean vdcListBean3;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
