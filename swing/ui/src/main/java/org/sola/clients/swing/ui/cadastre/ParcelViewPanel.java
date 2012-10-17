/**
 * ******************************************************************************************
 * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations
 * (FAO). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,this
 * list of conditions and the following disclaimer. 2. Redistributions in binary
 * form must reproduce the above copyright notice,this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. 3. Neither the name of FAO nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT,STRICT LIABILITY,OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * *********************************************************************************************
 */
package org.sola.clients.swing.ui.cadastre;

import org.sola.clients.beans.cadastre.CadastreObjectBean;
import org.sola.clients.beans.cadastre.CadastreObjectSearchResultBean;
import org.sola.clients.beans.cadastre.CadastreObjectSummaryBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Allows to display parcel details.
 */
public class ParcelViewPanel extends javax.swing.JPanel {

    private CadastreObjectSummaryBean cadastreObject;

    public ParcelViewPanel() {
        setupCadastreObject(null);
        initComponents();
    }

    private void setupCadastreObject(CadastreObjectSummaryBean cadastreObjectBean) {
        CadastreObjectSummaryBean oldValue = this.cadastreObject;
        if (cadastreObjectBean == null) {
            this.cadastreObject = new CadastreObjectSummaryBean();
        } else {
            this.cadastreObject = cadastreObjectBean;
        }
        firePropertyChange("cadastreObject", null, this.cadastreObject);
    }

    public CadastreObjectSummaryBean getCadastreObject() {
        return cadastreObject;
    }

    public void setCadastreObject(CadastreObjectSummaryBean cadastreObjectBean) {
        setupCadastreObject(cadastreObjectBean);
    }
    
    public void setCadastreObject(final CadastreObjectSearchResultBean cadastreObjectSearchResultBean) {
        SolaTask t = new SolaTask<Void, Void>() {
            CadastreObjectSummaryBean cadastreObject;
            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_GETTING_PARCEL));
                cadastreObject = CadastreObjectBean.getParcel(cadastreObjectSearchResultBean.getId());
                return null;
            }

            @Override
            public void taskDone() {
                setupCadastreObject(cadastreObject);
            }
        };
        TaskManager.getInstance().runTask(t);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPanel23 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtParcelCode = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtParcelVdc = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtParcelWardNumber = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtParcelMapSheet = new javax.swing.JTextField();
        txtParcelMapSheet2 = new javax.swing.JTextField();
        txtParcelMapSheet3 = new javax.swing.JTextField();
        txtParcelMapSheet4 = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtParcelGuthiNum = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtParcelLandType = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtLandClass = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtLandUse = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtParcelArea = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();

        setName("Form"); // NOI18N

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/ui/cadastre/Bundle"); // NOI18N
        jPanel23.setName(bundle.getString("ParcelViewPanel.jPanel23.name")); // NOI18N
        jPanel23.setLayout(new java.awt.GridLayout(3, 4, 15, 15));

        jPanel4.setName(bundle.getString("ParcelViewPanel.jPanel4.name")); // NOI18N

        jLabel4.setText(bundle.getString("ParcelViewPanel.jLabel4.text")); // NOI18N
        jLabel4.setName(bundle.getString("ParcelViewPanel.jLabel4.name")); // NOI18N

        txtParcelCode.setEditable(false);
        txtParcelCode.setName(bundle.getString("ParcelViewPanel.txtParcelCode.name")); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cadastreObject.propertyIdCode}"), txtParcelCode, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .add(jLabel4)
                .add(0, 83, Short.MAX_VALUE))
            .add(txtParcelCode)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .add(jLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtParcelCode, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 11, Short.MAX_VALUE))
        );

        jPanel23.add(jPanel4);

        jPanel15.setName(bundle.getString("ParcelViewPanel.jPanel15.name")); // NOI18N

        jLabel9.setText(bundle.getString("ParcelViewPanel.jLabel9.text")); // NOI18N
        jLabel9.setName(bundle.getString("ParcelViewPanel.jLabel9.name")); // NOI18N

        txtParcelVdc.setEditable(false);
        txtParcelVdc.setName(bundle.getString("ParcelViewPanel.txtParcelVdc.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cadastreObject.address.vdcBean.displayValue}"), txtParcelVdc, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        org.jdesktop.layout.GroupLayout jPanel15Layout = new org.jdesktop.layout.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel15Layout.createSequentialGroup()
                .add(jLabel9)
                .add(0, 59, Short.MAX_VALUE))
            .add(txtParcelVdc)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel15Layout.createSequentialGroup()
                .add(jLabel9)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtParcelVdc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 11, Short.MAX_VALUE))
        );

        jPanel23.add(jPanel15);

        jPanel16.setName(bundle.getString("ParcelViewPanel.jPanel16.name")); // NOI18N

        jLabel10.setText(bundle.getString("ParcelViewPanel.jLabel10.text")); // NOI18N
        jLabel10.setName(bundle.getString("ParcelViewPanel.jLabel10.name")); // NOI18N

        txtParcelWardNumber.setEditable(false);
        txtParcelWardNumber.setName(bundle.getString("ParcelViewPanel.txtParcelWardNumber.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cadastreObject.address.wardNo}"), txtParcelWardNumber, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        org.jdesktop.layout.GroupLayout jPanel16Layout = new org.jdesktop.layout.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel16Layout.createSequentialGroup()
                .add(jLabel10)
                .add(0, 112, Short.MAX_VALUE))
            .add(txtParcelWardNumber)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel16Layout.createSequentialGroup()
                .add(jLabel10)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtParcelWardNumber, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 11, Short.MAX_VALUE))
        );

        jPanel23.add(jPanel16);

        jPanel17.setName(bundle.getString("ParcelViewPanel.jPanel17.name")); // NOI18N

        jLabel11.setText(bundle.getString("ParcelViewPanel.jLabel11.text")); // NOI18N
        jLabel11.setName(bundle.getString("ParcelViewPanel.jLabel11.name")); // NOI18N

        jPanel2.setName(bundle.getString("ParcelViewPanel.jPanel2.name")); // NOI18N
        jPanel2.setLayout(new java.awt.GridLayout(1, 4));

        txtParcelMapSheet.setEditable(false);
        txtParcelMapSheet.setName(bundle.getString("ParcelViewPanel.txtParcelMapSheet.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cadastreObject.mapSheet.mapNumber}"), txtParcelMapSheet, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jPanel2.add(txtParcelMapSheet);

        txtParcelMapSheet2.setEditable(false);
        txtParcelMapSheet2.setName(bundle.getString("ParcelViewPanel.txtParcelMapSheet2.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cadastreObject.mapSheet2.mapNumber}"), txtParcelMapSheet2, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jPanel2.add(txtParcelMapSheet2);

        txtParcelMapSheet3.setEditable(false);
        txtParcelMapSheet3.setName(bundle.getString("ParcelViewPanel.txtParcelMapSheet3.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cadastreObject.mapSheet3.mapNumber}"), txtParcelMapSheet3, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jPanel2.add(txtParcelMapSheet3);

        txtParcelMapSheet4.setEditable(false);
        txtParcelMapSheet4.setName(bundle.getString("ParcelViewPanel.txtParcelMapSheet4.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cadastreObject.mapSheet4.mapNumber}"), txtParcelMapSheet4, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jPanel2.add(txtParcelMapSheet4);

        org.jdesktop.layout.GroupLayout jPanel17Layout = new org.jdesktop.layout.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel17Layout.createSequentialGroup()
                .add(jLabel11)
                .add(0, 41, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel17Layout.createSequentialGroup()
                .add(jLabel11)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel23.add(jPanel17);

        jPanel22.setName(bundle.getString("ParcelViewPanel.jPanel22.name")); // NOI18N

        jLabel18.setText(bundle.getString("ParcelViewPanel.jLabel18.text")); // NOI18N
        jLabel18.setName(bundle.getString("ParcelViewPanel.jLabel18.name")); // NOI18N

        txtParcelGuthiNum.setEditable(false);
        txtParcelGuthiNum.setName(bundle.getString("ParcelViewPanel.txtParcelGuthiNum.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cadastreObject.parcelno}"), txtParcelGuthiNum, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        org.jdesktop.layout.GroupLayout jPanel22Layout = new org.jdesktop.layout.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel22Layout.createSequentialGroup()
                .add(jLabel18)
                .add(0, 70, Short.MAX_VALUE))
            .add(txtParcelGuthiNum)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel22Layout.createSequentialGroup()
                .add(jLabel18)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtParcelGuthiNum, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 11, Short.MAX_VALUE))
        );

        jPanel23.add(jPanel22);

        jPanel19.setName(bundle.getString("ParcelViewPanel.jPanel19.name")); // NOI18N

        jLabel13.setText(bundle.getString("ParcelViewPanel.jLabel13.text")); // NOI18N
        jLabel13.setName(bundle.getString("ParcelViewPanel.jLabel13.name")); // NOI18N

        txtParcelLandType.setEditable(false);
        txtParcelLandType.setName(bundle.getString("ParcelViewPanel.txtParcelLandType.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cadastreObject.landTypeBean.displayValue}"), txtParcelLandType, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        org.jdesktop.layout.GroupLayout jPanel19Layout = new org.jdesktop.layout.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel19Layout.createSequentialGroup()
                .add(jLabel13)
                .add(0, 90, Short.MAX_VALUE))
            .add(txtParcelLandType)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel19Layout.createSequentialGroup()
                .add(jLabel13)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtParcelLandType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 11, Short.MAX_VALUE))
        );

        jPanel23.add(jPanel19);

        jPanel20.setName(bundle.getString("ParcelViewPanel.jPanel20.name")); // NOI18N

        jLabel14.setText(bundle.getString("ParcelViewPanel.jLabel14.text")); // NOI18N
        jLabel14.setName(bundle.getString("ParcelViewPanel.jLabel14.name")); // NOI18N

        txtLandClass.setEditable(false);
        txtLandClass.setName(bundle.getString("ParcelViewPanel.txtLandClass.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cadastreObject.landClassBean.displayValue}"), txtLandClass, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        org.jdesktop.layout.GroupLayout jPanel20Layout = new org.jdesktop.layout.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel20Layout.createSequentialGroup()
                .add(jLabel14)
                .add(0, 89, Short.MAX_VALUE))
            .add(txtLandClass)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel20Layout.createSequentialGroup()
                .add(jLabel14)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtLandClass, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 11, Short.MAX_VALUE))
        );

        jPanel23.add(jPanel20);

        jPanel21.setName(bundle.getString("ParcelViewPanel.jPanel21.name")); // NOI18N

        jLabel17.setText(bundle.getString("ParcelViewPanel.jLabel17.text")); // NOI18N
        jLabel17.setName(bundle.getString("ParcelViewPanel.jLabel17.name")); // NOI18N

        txtLandUse.setEditable(false);
        txtLandUse.setName(bundle.getString("ParcelViewPanel.txtLandUse.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cadastreObject.landUseBean.displayValue}"), txtLandUse, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        org.jdesktop.layout.GroupLayout jPanel21Layout = new org.jdesktop.layout.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel21Layout.createSequentialGroup()
                .add(jLabel17)
                .add(0, 95, Short.MAX_VALUE))
            .add(txtLandUse)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel21Layout.createSequentialGroup()
                .add(jLabel17)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtLandUse, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 11, Short.MAX_VALUE))
        );

        jPanel23.add(jPanel21);

        jPanel18.setName(bundle.getString("ParcelViewPanel.jPanel18.name")); // NOI18N

        jLabel12.setText(bundle.getString("ParcelViewPanel.jLabel12.text")); // NOI18N
        jLabel12.setName(bundle.getString("ParcelViewPanel.jLabel12.name")); // NOI18N

        txtParcelArea.setEditable(false);
        txtParcelArea.setName(bundle.getString("ParcelViewPanel.txtParcelArea.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cadastreObject.officialAreaFormatted}"), txtParcelArea, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        org.jdesktop.layout.GroupLayout jPanel18Layout = new org.jdesktop.layout.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel18Layout.createSequentialGroup()
                .add(jLabel12)
                .add(0, 73, Short.MAX_VALUE))
            .add(txtParcelArea)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel18Layout.createSequentialGroup()
                .add(jLabel12)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtParcelArea, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 11, Short.MAX_VALUE))
        );

        jPanel23.add(jPanel18);

        jPanel1.setName(bundle.getString("ParcelViewPanel.jPanel1.name")); // NOI18N

        jLabel1.setText(bundle.getString("ParcelViewPanel.jLabel1.text")); // NOI18N
        jLabel1.setName(bundle.getString("ParcelViewPanel.jLabel1.name")); // NOI18N

        txtStatus.setEditable(false);
        txtStatus.setName(bundle.getString("ParcelViewPanel.txtStatus.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cadastreObject.status.displayValue}"), txtStatus, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jLabel1)
                .add(0, 107, Short.MAX_VALUE))
            .add(txtStatus)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel23.add(jPanel1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel23, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel23, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField txtLandClass;
    private javax.swing.JTextField txtLandUse;
    private javax.swing.JTextField txtParcelArea;
    private javax.swing.JTextField txtParcelCode;
    private javax.swing.JTextField txtParcelGuthiNum;
    private javax.swing.JTextField txtParcelLandType;
    private javax.swing.JTextField txtParcelMapSheet;
    private javax.swing.JTextField txtParcelMapSheet2;
    private javax.swing.JTextField txtParcelMapSheet3;
    private javax.swing.JTextField txtParcelMapSheet4;
    private javax.swing.JTextField txtParcelVdc;
    private javax.swing.JTextField txtParcelWardNumber;
    private javax.swing.JTextField txtStatus;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
