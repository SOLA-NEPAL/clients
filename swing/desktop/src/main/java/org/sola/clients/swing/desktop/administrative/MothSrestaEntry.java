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
import org.sola.clients.beans.referencedata.VdcBean;
import org.sola.clients.swing.desktop.party.LandownerDecription;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.MainContentPanel;

/**
 *
 * @author KumarKhadka
 */
public class MothSrestaEntry extends ContentPanel {

    /**
     * Creates new form MothSrestaEntry
     */
    public MothSrestaEntry() {
        initComponents();
        //vdcListBean.loadList(false, OfficeBean.getCurrentOffice().getDistrictCode());
        //vdcListBean.loadList(false, OfficeBean.getCurrentOffice().getDistrictCode());
         vdcListBean.loadVdcList();
         vdcListBean1.loadVdcList();
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

        mothBean = new org.sola.clients.beans.administrative.MothBean();
        mothListBean = new org.sola.clients.beans.administrative.MothListBean();
        vdcListBean = new org.sola.clients.beans.referencedata.VdcListBean();
        vdcListBean1 = new org.sola.clients.beans.referencedata.VdcListBean();
        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        jToolBar1 = new javax.swing.JToolBar();
        toolMnuParcelEntry = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        toolLandOwnerDetail = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cmbVDC1 = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbMothLuj2 = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmbMothLujNo = new javax.swing.JComboBox();
        jToolBar2 = new javax.swing.JToolBar();
        toolMenuSave = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbVDC = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        cmbMothLuj = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtMothLujNo = new javax.swing.JTextField();

        setHeaderPanel(headerPanel1);

        headerPanel1.setTitleText("Moth Management");

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        toolMnuParcelEntry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        toolMnuParcelEntry.setText("Pracel Entry");
        toolMnuParcelEntry.setFocusable(false);
        toolMnuParcelEntry.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolMnuParcelEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolMnuParcelEntryActionPerformed(evt);
            }
        });
        jToolBar1.add(toolMnuParcelEntry);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/document-view.png"))); // NOI18N
        jButton4.setText("Parcel description");
        jButton4.setFocusable(false);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton4);

        toolLandOwnerDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/users.png"))); // NOI18N
        toolLandOwnerDetail.setText("Land owner details entry");
        toolLandOwnerDetail.setFocusable(false);
        toolLandOwnerDetail.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolLandOwnerDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolLandOwnerDetailActionPerformed(evt);
            }
        });
        jToolBar1.add(toolLandOwnerDetail);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/document--pencil.png"))); // NOI18N
        jButton6.setText("Lower shresta entry");
        jButton6.setFocusable(false);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton6);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/document-link.png"))); // NOI18N
        jButton7.setText("Lower description entry");
        jButton7.setFocusable(false);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton7);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Choose Moth according to VDC/MP", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Aharoni", 0, 18), new java.awt.Color(0, 102, 51))); // NOI18N
        jPanel4.setLayout(new java.awt.GridLayout(1, 3, 15, 0));

        jLabel6.setText("VDC/MP");

        cmbVDC1.setEditable(true);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${vdc}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vdcListBean1, eLProperty, cmbVDC1);
        bindingGroup.addBinding(jComboBoxBinding);

        cmbVDC1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbVDC1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(37, 161, Short.MAX_VALUE))
            .addComponent(cmbVDC1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbVDC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 41, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel8);

        jLabel2.setText("Moth/Luj Type");

        cmbMothLuj2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "M", "L" }));
        cmbMothLuj2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMothLuj2ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addContainerGap(130, Short.MAX_VALUE))
            .addComponent(cmbMothLuj2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbMothLuj2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 41, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel2);

        jLabel3.setText("Moth/Luj No");

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${moths}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothListBean, eLProperty, cmbMothLujNo);
        bindingGroup.addBinding(jComboBoxBinding);
        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothListBean, org.jdesktop.beansbinding.ELProperty.create("${selectedMoth}"), cmbMothLujNo, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addContainerGap(141, Short.MAX_VALUE))
            .addComponent(cmbMothLujNo, 0, 199, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbMothLujNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 41, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel3);

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        toolMenuSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/save.png"))); // NOI18N
        toolMenuSave.setText("Save");
        toolMenuSave.setFocusable(false);
        toolMenuSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolMenuSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolMenuSaveActionPerformed(evt);
            }
        });
        jToolBar2.add(toolMenuSave);

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/refresh.png"))); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.setFocusable(false);
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jToolBar2.add(btnRefresh);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Create Moth or Luj", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Aharoni", 0, 18), new java.awt.Color(0, 102, 51))); // NOI18N
        jPanel7.setLayout(new java.awt.GridLayout(1, 2, 40, 0));

        jLabel1.setText("VDC/MP");

        cmbVDC.setEditable(true);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${vdc}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vdcListBean, eLProperty, cmbVDC);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothBean, org.jdesktop.beansbinding.ELProperty.create("${vdc}"), cmbVDC, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(37, 145, Short.MAX_VALUE))
            .addComponent(cmbVDC, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbVDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 41, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel1);

        cmbMothLuj.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "M", "L" }));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothBean, org.jdesktop.beansbinding.ELProperty.create("${mothLuj}"), cmbMothLuj, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jLabel4.setText("Moth/Luj");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addContainerGap())
            .addComponent(cmbMothLuj, 0, 183, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbMothLuj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel5);

        jLabel5.setText("Moth/Luj No");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothBean, org.jdesktop.beansbinding.ELProperty.create("${mothlujNumber}"), txtMothLujNo, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addContainerGap(125, Short.MAX_VALUE))
            .addComponent(txtMothLujNo)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMothLujNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(118, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void toolMnuParcelEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolMnuParcelEntryActionPerformed
        // TODO add your handling code here:
        parcelsEntry();
    }//GEN-LAST:event_toolMnuParcelEntryActionPerformed

    private void toolLandOwnerDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolLandOwnerDetailActionPerformed
        // TODO add your handling code here:
        landOwnerDetails();
    }//GEN-LAST:event_toolLandOwnerDetailActionPerformed

    private void toolMenuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolMenuSaveActionPerformed
        mothBean.saveMoth();
    }//GEN-LAST:event_toolMenuSaveActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void cmbVDC1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbVDC1ItemStateChanged
        VdcBean vdc = (VdcBean) cmbVDC1.getSelectedItem();
        mothListBean.loadMothList(vdc.getCode(), cmbMothLuj2.getSelectedItem().toString());
    }//GEN-LAST:event_cmbVDC1ItemStateChanged

    private void cmbMothLuj2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMothLuj2ItemStateChanged
        // TODO add your handling code here:
        VdcBean vdc = (VdcBean) cmbVDC1.getSelectedItem();
        mothListBean.loadMothList(vdc.getCode(), cmbMothLuj2.getSelectedItem().toString());
    }//GEN-LAST:event_cmbMothLuj2ItemStateChanged

    private void parcelsEntry() {
        if (!getMainContentPanel().isPanelOpened(MainContentPanel.CARD_Parcel_Entry)) {
            ParcelMothEntry pclMoth = new ParcelMothEntry(mothListBean.getSelectedMoth());
            getMainContentPanel().addPanel(pclMoth, MainContentPanel.CARD_Parcel_Entry);
        }
        getMainContentPanel().showPanel(MainContentPanel.CARD_Parcel_Entry);
    }

    private void landOwnerDetails() {
        if (!getMainContentPanel().isPanelOpened(MainContentPanel.CARD_LandOwner_Entry)) {
            LandownerDecription lndOwner = new LandownerDecription();
            getMainContentPanel().addPanel(lndOwner, MainContentPanel.CARD_LandOwner_Entry);
        }
        getMainContentPanel().showPanel(MainContentPanel.CARD_LandOwner_Entry);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox cmbMothLuj;
    private javax.swing.JComboBox cmbMothLuj2;
    private javax.swing.JComboBox cmbMothLujNo;
    private javax.swing.JComboBox cmbVDC;
    private javax.swing.JComboBox cmbVDC1;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private org.sola.clients.beans.administrative.MothBean mothBean;
    private org.sola.clients.beans.administrative.MothListBean mothListBean;
    private javax.swing.JButton toolLandOwnerDetail;
    private javax.swing.JButton toolMenuSave;
    private javax.swing.JButton toolMnuParcelEntry;
    private javax.swing.JTextField txtMothLujNo;
    private org.sola.clients.beans.referencedata.VdcListBean vdcListBean;
    private org.sola.clients.beans.referencedata.VdcListBean vdcListBean1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
