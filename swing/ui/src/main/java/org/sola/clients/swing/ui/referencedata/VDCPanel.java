/**
 * ******************************************************************************************
 * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations (FAO).
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice,this list
 *       of conditions and the following disclaimer.
 *    2. Redistributions in binary form must reproduce the above copyright notice,this list
 *       of conditions and the following disclaimer in the documentation and/or other
 *       materials provided with the distribution.
 *    3. Neither the name of FAO nor the names of its contributors may be used to endorse or
 *       promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,STRICT LIABILITY,OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * *********************************************************************************************
 */
package org.sola.clients.swing.ui.referencedata;

import org.sola.clients.beans.referencedata.DistrictBean;
import org.sola.clients.beans.referencedata.VdcBean;
import org.sola.clients.swing.common.controls.autocomplete.Configurator;
import org.sola.clients.swing.ui.renderers.TableCellTextAreaRenderer;
import org.sola.webservices.transferobjects.referencedata.VdcTO;

/**
 * Used to manage beans, inheriting from {@link VdcBean}.
 */
public class VDCPanel extends javax.swing.JPanel {

    private VdcBean vdcBean;
    private Class<? extends VdcBean> vdcClass;
    private Class<? extends VdcTO> vdcTOClass;
    
    /** Default constructor. */
    public VDCPanel() {
        initComponents();
    }

    /** 
     * Creates panel with predefined parameters. 
     * @param refDataClass Class of reference data bean to create and bind on the panel.
     * @param refDataTOClass Class of reference data transfer object to be used for saving on the server side.
     */
    public <T extends VdcBean, S extends VdcTO> VDCPanel
            (Class<T> refDataClass, Class<S> refDataTOClass) {
        this.vdcClass = refDataClass;
        this.vdcTOClass = refDataTOClass;
        initComponents();
        Configurator.enableAutoCompletion(cmbDistrict);
        setupVdcBean(vdcBean);
    }

    /** Setup reference data bean object, used to bind data on the form. */
    private void setupVdcBean(VdcBean vdcBean) {
        txtCode.setEnabled(vdcBean == null);
        
        if (vdcBean != null) {
            this.vdcBean = vdcBean;
        } else {
            try {
                this.vdcBean = vdcClass.newInstance();
            } catch (Exception ex) {
            }
        }
        
        descriptionValues.loadLocalizedValues(this.vdcBean.getDescription());
        displayValues.loadLocalizedValues(this.vdcBean.getDisplayValue());
        firePropertyChange("vdcBean", null, this.vdcBean);
    }

    /** Returns reference data bean, bound on the panel. */
    public VdcBean getVdcBean() {
        return vdcBean;
    }

    /** Sets reference data bean, to bind on the panel. */
    public <T extends VdcBean, S extends VdcTO> void 
            setVdcBean(Class<T> refDataClass, 
            Class<S> vdcTOClass, VdcBean vdcBean) {
        this.vdcTOClass = vdcTOClass;
        this.vdcClass = refDataClass;
        setupVdcBean(vdcBean);
    }
    
    /** Validates reference data object. */
    public boolean validateVdc(boolean showMessage){
        return vdcBean.validate(showMessage).size()<1;
    }

    /** Calls saving procedure of reference data object. */
    public boolean save(boolean showMessage){
        vdcBean.setDisplayValue(displayValues.buildMultilingualString());
        vdcBean.setDescription(descriptionValues.buildMultilingualString());
        vdcBean.setDistrictCode(((DistrictBean)cmbDistrict.getSelectedItem()).getCode());
        if(validateVdc(showMessage)){
            VdcBean.saveRefData(vdcBean, vdcTOClass);
            return true;
        }else {
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        displayValues = new org.sola.clients.beans.system.LocalizedValuesListBean();
        descriptionValues = new org.sola.clients.beans.system.LocalizedValuesListBean();
        districts = new org.sola.clients.beans.referencedata.DistrictListBean();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmbDistrict = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtStatus1 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDisplayValues = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDescription = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        jLabel2 = new javax.swing.JLabel();

        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setLayout(new java.awt.GridLayout(1, 0, 15, 5));

        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/ui/referencedata/Bundle"); // NOI18N
        jLabel1.setText(bundle.getString("VDCPanel.jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        txtCode.setName("txtCode"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${vdcBean.code}"), txtCode, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1)
            .addComponent(txtCode, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel1);

        jPanel2.setName("jPanel2"); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel3.setText(bundle.getString("VDCPanel.jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        cmbDistrict.setName(bundle.getString("VDCPanel.cmbDistrict.name")); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${districts}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, districts, eLProperty, cmbDistrict);
        bindingGroup.addBinding(jComboBoxBinding);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addContainerGap(117, Short.MAX_VALUE))
            .addComponent(cmbDistrict, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.add(jPanel2);

        jPanel7.setName(bundle.getString("VDCPanel.jPanel7.name")); // NOI18N

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel5.setText(bundle.getString("VDCPanel.jLabel5.text")); // NOI18N
        jLabel5.setName(bundle.getString("VDCPanel.jLabel5.name")); // NOI18N

        txtStatus1.setName(bundle.getString("VDCPanel.txtStatus1.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${vdcBean.status}"), txtStatus1, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addContainerGap(115, Short.MAX_VALUE))
            .addComponent(txtStatus1, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStatus1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.add(jPanel7);

        jPanel6.setName("jPanel6"); // NOI18N
        jPanel6.setLayout(new java.awt.GridLayout(2, 0, 0, 17));

        jPanel3.setName("jPanel3"); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel4.setText(bundle.getString("VDCPanel.jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tableDisplayValues.setName("tableDisplayValues"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${localizedValues}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, displayValues, eLProperty, tableDisplayValues);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${language.displayValue}"));
        columnBinding.setColumnName("Language.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${localizedValue}"));
        columnBinding.setColumnName("Localized Value");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(tableDisplayValues);
        tableDisplayValues.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableDisplayValues.getColumnModel().getColumn(0).setMaxWidth(150);
        tableDisplayValues.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("VDCPanel.tableDisplayValues.columnModel.title0_1")); // NOI18N
        tableDisplayValues.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("VDCPanel.tableDisplayValues.columnModel.title1_1")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addContainerGap(436, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel3);

        jPanel4.setName("jPanel4"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tableDescription.setName("tableDescription"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${localizedValues}");
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, descriptionValues, eLProperty, tableDescription);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${language.displayValue}"));
        columnBinding.setColumnName("Language.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${localizedValue}"));
        columnBinding.setColumnName("Localized Value");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane2.setViewportView(tableDescription);
        tableDescription.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableDescription.getColumnModel().getColumn(0).setMaxWidth(150);
        tableDescription.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("VDCPanel.tableDescription.columnModel.title0_1")); // NOI18N
        tableDescription.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("VDCPanel.tableDescription.columnModel.title1_1")); // NOI18N
        tableDescription.getColumnModel().getColumn(1).setCellRenderer(new TableCellTextAreaRenderer());

        jLabel2.setText(bundle.getString("VDCPanel.jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addContainerGap(460, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbDistrict;
    private org.sola.clients.beans.system.LocalizedValuesListBean descriptionValues;
    private org.sola.clients.beans.system.LocalizedValuesListBean displayValues;
    private org.sola.clients.beans.referencedata.DistrictListBean districts;
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
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableDescription;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableDisplayValues;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtStatus1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
