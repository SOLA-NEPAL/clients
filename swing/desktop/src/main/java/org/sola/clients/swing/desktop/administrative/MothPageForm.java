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
import javax.swing.JDialog;
import org.sola.clients.beans.administrative.LocWithMothBean;
import org.sola.clients.beans.administrative.MothListBean;
import org.sola.clients.beans.referencedata.MothTypeListBean;
import org.sola.clients.beans.referencedata.VdcListBean;
import org.sola.clients.swing.common.controls.autocomplete.Configurator;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 */
public class MothPageForm extends javax.swing.JDialog {

    public static final String LOC_SAVED = "locSaved";
    public static final String LOC_WITH_MOTH_BEAN_PROPERTY = "locWithMothBean";
    public static final String LOC_FOUND = "locFound";
    private LocWithMothBean locWithMothBean;
    boolean editflag = true;

    private VdcListBean createVdcLst() {
        VdcListBean list = new VdcListBean();
        list.loadListByOffice(false);
        if (locWithMothBean != null && !locWithMothBean.isNew()) {
            list.setSelectedVdc(locWithMothBean.getMoth().getVdc());
        }
        return list;
    }

    private MothTypeListBean createMothTypeList() {
        createMothList();
        if (mothTypeListBean1 == null) {
            mothTypeListBean1 = new MothTypeListBean();
        }
        mothTypeListBean1.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(MothTypeListBean.SELECTED_MOTH_TYPE_PROPERTY)) {
                    searchMoth();
                    customizeTextFieldForMothPageNo();
                }
            }
        });
        if (locWithMothBean != null && !locWithMothBean.isNew()) {
            mothTypeListBean1.setSelectedMothType(locWithMothBean.getMoth().getMothType());
        }
        return mothTypeListBean1;
    }

    private MothListBean createMothList() {
        if (mothListBean1 == null) {
            mothListBean1 = new MothListBean();
        }
        return mothListBean1;
    }

    /**
     * Creates new form MothPageForm
     */
    public MothPageForm(java.awt.Frame parent, boolean modal, LocWithMothBean locWithMothBean) {
        super(parent, modal);
        if (locWithMothBean == null) {
            this.locWithMothBean = new LocWithMothBean();
        } else {
            this.locWithMothBean = locWithMothBean;
        }
        initComponents();
        postInit();
    }

    public LocWithMothBean getLocWithMothBean() {
        return locWithMothBean;
    }

    private void postInit() {
        Configurator.enableAutoCompletion(cmbMothLujNo);
        Configurator.enableAutoCompletion(cmbMothType);
        Configurator.enableAutoCompletion(cmbVdc);
        if (locWithMothBean.isNew()) {
            editflag = false;
            cmbVdc.setSelectedIndex(-1);
            customizeMothList();
            customizeMothType();
            customizeTextFieldForMothPageNo();
        }

        vdcListBean1.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(VdcListBean.SELECTED_VDC_PROPERTY)) {
                    customizeMothType();
                }
            }
        });
    }

    private void searchMoth() {
        if (vdcListBean1.getSelectedVdc() != null && mothTypeListBean1.getSelectedMothType() != null) {
            if (cmbMothLujNo != null) {
                cmbMothLujNo.setEnabled(true);
            }
            mothListBean1.loadMothList(vdcListBean1.getSelectedVdc().getCode(),
                    mothTypeListBean1.getSelectedMothType().getMothTypeCode());
        } else {
            if (cmbMothLujNo != null) {
                cmbMothLujNo.setEnabled(false);
            }
        }
        if (locWithMothBean.isNew()) {
            cmbMothLujNo.setSelectedIndex(-1);
        }
    }

    private void customizeTextFieldForMothPageNo() {
        boolean enable = mothTypeListBean1.getSelectedMothType() != null;
        if (mothTypeListBean1.getSelectedMothType() == null) {
            if (txtPageNo != null) {
                txtPageNo.setEnabled(enable);
                txtTmpPageNo.setEnabled(enable);
            }
            return;
        }
        if ("Moth".equals(mothTypeListBean1.getSelectedMothType().getMothTypeName())) {
            if (txtPageNo != null) {
                txtPageNo.setEnabled(enable);
                txtTmpPageNo.setEnabled(!enable);
            }
        }
        if ("Luj".equals(mothTypeListBean1.getSelectedMothType().getMothTypeName())) {
            if (txtPageNo != null) {
                txtTmpPageNo.setEnabled(enable);
                txtPageNo.setEnabled(!enable);
            }
        }
    }

    private void customizeMothList() {
        boolean enable = mothTypeListBean1.getSelectedMothType() != null;
        cmbMothLujNo.setEnabled(enable);
    }

    private void customizeMothType() {
        boolean enable = vdcListBean1.getSelectedVdc() != null;
        cmbMothType.setEnabled(enable);
        cmbMothType.setSelectedIndex(-1);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        vdcListBean1 = createVdcLst();
        mothTypeListBean1 = createMothTypeList();
        mothListBean1 = createMothList();
        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbVdc = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmbMothType = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbMothLujNo = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtTmpPageNo = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtPageNo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/save.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/administrative/Bundle"); // NOI18N
        btnSave.setText(bundle.getString("MothPageForm.btnSave.text")); // NOI18N
        btnSave.setFocusable(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSave);

        jPanel6.setLayout(new java.awt.GridLayout(2, 3, 15, 15));

        jLabel1.setText(bundle.getString("MothPageForm.jLabel1.text")); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${vdcs}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vdcListBean1, eLProperty, cmbVdc);
        bindingGroup.addBinding(jComboBoxBinding);
        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vdcListBean1, org.jdesktop.beansbinding.ELProperty.create("${selectedVdc}"), cmbVdc, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(cmbVdc, 0, 117, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbVdc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel1);

        jLabel3.setText(bundle.getString("MothPageForm.jLabel3.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${mothTypes}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothTypeListBean1, eLProperty, cmbMothType);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothTypeListBean1, org.jdesktop.beansbinding.ELProperty.create("${selectedMothType}"), cmbMothType, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(cmbMothType, 0, 117, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbMothType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel3);

        jLabel2.setText(bundle.getString("MothPageForm.jLabel2.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${moths}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothListBean1, eLProperty, cmbMothLujNo);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${locWithMothBean.moth}"), cmbMothLujNo, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 36, Short.MAX_VALUE))
            .addComponent(cmbMothLujNo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbMothLujNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel2);

        jLabel4.setText(bundle.getString("MothPageForm.jLabel4.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${locWithMothBean.tmpPanaNo}"), txtTmpPageNo, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 25, Short.MAX_VALUE))
            .addComponent(txtTmpPageNo)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTmpPageNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel4);

        jLabel5.setText(bundle.getString("MothPageForm.jLabel5.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${locWithMothBean.panaNo}"), txtPageNo, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 54, Short.MAX_VALUE))
            .addComponent(txtPageNo)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPageNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        createLoc();

    }//GEN-LAST:event_btnSaveActionPerformed

    private void createLoc() {
        if ((locWithMothBean.getMoth() != null && !txtPageNo.getText().isEmpty())
                || (locWithMothBean.getMoth() != null && !txtTmpPageNo.getText().isEmpty())) {
            LocWithMothBean loc = LocWithMothBean.searchLocByMothAndPage(
                    locWithMothBean.getMoth(), txtPageNo.getText());
            if (loc != null) {
                MessageUtility.displayMessage(ClientMessage.LOC_EXIST);
                return;
            }
            if (loc == null) {
                if (MessageUtility.displayMessage(ClientMessage.LOC_NOT_FOUND_CREATE_NEW)
                        == MessageUtility.BUTTON_ONE) {
                    saveLoc(false);
                } else {
                    return;
                }
            }
        }
    }

    public boolean validateLoc(boolean showMessage) {
        return locWithMothBean.validate(showMessage).size() < 1;
    }

    public boolean saveLoc() {
        if (validateLoc(true)) {
            return locWithMothBean.saveLoc();
        } else {
            return false;
        }
    }

    private void saveLoc(final boolean allowClose) {
        final JDialog dlg = this;
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
                    } else {
                        MessageUtility.displayMessage(ClientMessage.LOC_SAVED);
                        if (editflag == true) {
                            dlg.setVisible(false);
                        }
                        cmbVdc.setSelectedIndex(-1);
                        customizeMothType();
                        cmbMothLujNo.setSelectedIndex(-1);
                        txtPageNo.setText(null);
                        txtTmpPageNo.setText(null);
                    }
                }
            }
        };
        TaskManager.getInstance().runTask(t);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cmbMothLujNo;
    private javax.swing.JComboBox cmbMothType;
    private javax.swing.JComboBox cmbVdc;
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
    private javax.swing.JToolBar jToolBar1;
    private org.sola.clients.beans.administrative.MothListBean mothListBean1;
    private org.sola.clients.beans.referencedata.MothTypeListBean mothTypeListBean1;
    private javax.swing.JTextField txtPageNo;
    private javax.swing.JTextField txtTmpPageNo;
    private org.sola.clients.beans.referencedata.VdcListBean vdcListBean1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
