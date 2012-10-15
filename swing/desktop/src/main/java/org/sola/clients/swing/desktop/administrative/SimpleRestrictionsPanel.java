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
package org.sola.clients.swing.desktop.administrative;

import javax.validation.groups.Default;
import org.sola.clients.beans.administrative.RrrBean;
import org.sola.clients.beans.administrative.validation.RestrictionReleaseValidationGroup;
import org.sola.clients.beans.administrative.validation.RestrictionValidationGroup;
import org.sola.clients.beans.application.ApplicationBean;
import org.sola.clients.beans.application.ApplicationServiceBean;
import org.sola.clients.beans.referencedata.StatusConstants;
import org.sola.clients.swing.common.LafManager;
import org.sola.clients.swing.desktop.MainForm;
import org.sola.clients.swing.desktop.source.DocumentsManagementExtPanel;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.source.DocumentsManagementPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Used to create and manage simple types of rights. {@link RrrBean} is used to
 * bind the data on the form.
 */
public class SimpleRestrictionsPanel extends ContentPanel {

    private ApplicationBean appBean;
    private ApplicationServiceBean appService;
    private RrrBean.RRR_ACTION rrrAction;
    public static final String UPDATED_RRR = "updatedRRR";

    /**
     * Creates {@link DocumentsManagementPanel} instance.
     */
    private DocumentsManagementExtPanel createDocumentsPanel() {
        if (rrrBean == null) {
            rrrBean = new RrrBean();
        }
        if (appBean == null) {
            appBean = new ApplicationBean();
        }

        boolean allowEdit = true;
        if (rrrAction == RrrBean.RRR_ACTION.VIEW) {
            allowEdit = false;
        }
        DocumentsManagementExtPanel panel = new DocumentsManagementExtPanel(
                rrrBean.getSourceList(), appBean, allowEdit);
        return panel;
    }

    /**
     * Creates {@link RrrBean} instance.
     */
    private RrrBean CreateRrrBean() {
        if (rrrBean == null) {
            rrrBean = new RrrBean();
        }
        return rrrBean;
    }

    /**
     * Form constructor.
     *     
* @param parent Parent form.
     * @param modal Indicates form modality.
     * @param rrrBean {
     * @RrrBean} instance to bind on the form.
     * @param applicationBean {@link ApplicationBean} instance, used to get list
     * of application documents.
     * @param rrrAction {@link RrrBean#RRR_ACTION} type, used to customize form
     * view.
     */
    public SimpleRestrictionsPanel(RrrBean rrrBean, ApplicationBean applicationBean,
            ApplicationServiceBean applicationService, RrrBean.RRR_ACTION rrrAction) {

        this.appBean = applicationBean;
        this.appService = applicationService;
        this.rrrAction = rrrAction;
        prepareRrrBean(rrrBean, rrrAction);
        initComponents();
        titleConf(rrrAction);
        //checkUserRole();
        customizeForm(rrrAction);
        saveRrrState();
    }

//    private void checkUserRole() {
//        if (!SecurityBean.getCurrentUser().isInRole(RolesConstants.ADMIN_MANAGE_SETTINGS, RolesConstants.CADASTRE_MAP_SHEET_SAVE)) {
//            printRegistrationLetter.setEnabled(true);
//        }
//    }
    private void titleConf(RrrBean.RRR_ACTION action) {
        if (action == RrrBean.RRR_ACTION.CANCEL) {
            headerPanel.setTitleText("Release Simple Restriction");
        } else {
            headerPanel.setTitleText(rrrBean.getRrrType().getDisplayValue());
        }
    }

    private void showHidePanel(boolean show) {
        restrictionPanel.setVisible(show);
        restrictionReleasePanel.setVisible(!show);
    }

    /**
     * Checks provided {@link RrrBean} and makes a copy if needed.
     */
    private void prepareRrrBean(RrrBean rrrBean, RrrBean.RRR_ACTION rrrAction) {
        if (rrrBean == null) {
            this.rrrBean = new RrrBean();
            this.rrrBean.setStatusCode(StatusConstants.PENDING);
        } else {
            this.rrrBean = rrrBean.makeCopyByAction(rrrAction);
        }
    }

    /**
     * Customizes form view, disabling or enabling different parts, depending on
     * the given {@link RrrBean#RRR_ACTION} and user rights.
     */
    private void customizeForm(RrrBean.RRR_ACTION rrrAction) {
        if (rrrAction == RrrBean.RRR_ACTION.NEW) {
            btnSave.setText("Create & Close");
            showHidePanel(true);
        }
        if (rrrAction == RrrBean.RRR_ACTION.CANCEL) {
            btnSave.setText(MessageUtility.getLocalizedMessage(
                    ClientMessage.GENERAL_LABELS_EXTINGUISH_AND_CLOSE).getMessage());
            showHidePanel(false);
            customizeDocumentsPanel();
        }
        if (rrrAction == RrrBean.RRR_ACTION.EDIT) {
            showHidePanel(true);
        }

        if (rrrAction != RrrBean.RRR_ACTION.EDIT && rrrAction != RrrBean.RRR_ACTION.VIEW
                && appService != null) {
            // Set default noation text from the selected application service
            txtNotationText.setText(appService.getRequestType().getNotationTemplate());
        }

        if (rrrAction == RrrBean.RRR_ACTION.VIEW) {
            btnSave.setVisible(false);
            editView();
            editField(false);
            customizeDocumentsPanel();
        }
    }

    private void editView() {
        if (rrrBean.getStatusCode().equals(StatusConstants.HISTORIC)) {
            showHidePanel(false);
            headerPanel.setTitleText("Release Simple Restriction");
        } else {
            showHidePanel(true);
        }
    }

    private void editField(boolean editable) {
        txtNotationText.setEditable(editable);
        txtRegistrationNo.setEditable(editable);
        txtSerialNumber.setEditable(editable);
        txtBundleNo.setEditable(editable);
        txtBundlePageNo.setEditable(editable);
        txtPrice.setEditable(editable);
        cmbRestrictionReason.setEditable(editable);
        cmbRestrictionReleaseReason.setEditable(editable);
        txtRestrictionOfficeAddress.setEditable(editable);
        txtRestrictionOfficeName.setEditable(editable);
        txtRegDatetime.setEditable(editable);
    }

    private void customizeDocumentsPanel() {
        boolean isReadOnly = rrrAction == RrrBean.RRR_ACTION.VIEW;
        documentsPanel.setAllowEdit(!isReadOnly);
    }

    private boolean saveRrr() {
        if (rrrAction == RrrBean.RRR_ACTION.CANCEL) {
            if (rrrBean.validate(true, Default.class, RestrictionReleaseValidationGroup.class).size() <= 0) {
                firePropertyChange(UPDATED_RRR, null, rrrBean);
                close();
                return true;
            }
            return false;
        } else {
            if (rrrBean.validate(true, Default.class, RestrictionValidationGroup.class).size() <= 0) {
                firePropertyChange(UPDATED_RRR, null, rrrBean);
                close();
                return true;
            }
            return false;

        }
    }

    private void saveRrrState() {
        MainForm.saveBeanState(rrrBean);
    }

    @Override
    protected boolean panelClosing() {
        if (btnSave.isEnabled() && MainForm.checkSaveBeforeClose(rrrBean)) {
            return saveRrr();
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        restrictionReasonListBean = new org.sola.clients.beans.referencedata.RestrictionReasonListBean();
        rrrBean = CreateRrrBean();
        restrictionReleaseReasonListBean = new org.sola.clients.beans.referencedata.RestrictionReleaseReasonListBean();
        jLabel15 = new javax.swing.JLabel();
        txtNotationText = new javax.swing.JTextField();
        headerPanel = new org.sola.clients.swing.ui.HeaderPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        printRegistrationLetter = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(7, 0), new java.awt.Dimension(7, 0), new java.awt.Dimension(7, 32767));
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtRegDatetime = new org.sola.clients.swing.common.controls.NepaliDateField();
        jPanel6 = new javax.swing.JPanel();
        txtRegistrationNo = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        groupPanel1 = new org.sola.clients.swing.ui.GroupPanel();
        restrictionPanel = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtSerialNumber = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        txtBundleNo = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        txtBundlePageNo = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        cmbRestrictionReason = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        txtRestrictionOfficeName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtRestrictionOfficeAddress = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        txtPrice = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        restrictionReleasePanel = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cmbRestrictionReleaseReason = new javax.swing.JComboBox();
        jPanel15 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtRestrictionReleaseOfficeName = new javax.swing.JTextField();
        documentsPanel = createDocumentsPanel();

        setCloseOnHide(true);
        setHeaderPanel(headerPanel);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/administrative/Bundle"); // NOI18N
        setHelpTopic(bundle.getString("SimpleRestrictionsPanel.helpTopic")); // NOI18N
        setName("Form"); // NOI18N

        jLabel15.setFont(LafManager.getInstance().getLabFontBold());
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel15.setText(bundle.getString("SimpleRestrictionsPanel.jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        txtNotationText.setName("txtNotationText"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${notation.notationText}"), txtNotationText, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        headerPanel.setName("headerPanel"); // NOI18N
        headerPanel.setTitleText(bundle.getString("SimpleRestrictionsPanel.headerPanel.titleText")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/save.png"))); // NOI18N
        btnSave.setText(bundle.getString("SimpleRestrictionsPanel.btnSave.text")); // NOI18N
        btnSave.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSave.setName("btnSave"); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSave);

        jSeparator2.setName(bundle.getString("SimpleRestrictionsPanel.jSeparator2.name")); // NOI18N
        jToolBar1.add(jSeparator2);

        printRegistrationLetter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/print.png"))); // NOI18N
        printRegistrationLetter.setText(bundle.getString("SimpleRestrictionsPanel.printRegistrationLetter.text")); // NOI18N
        printRegistrationLetter.setFocusable(false);
        printRegistrationLetter.setName(bundle.getString("SimpleRestrictionsPanel.printRegistrationLetter.name")); // NOI18N
        printRegistrationLetter.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(printRegistrationLetter);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jToolBar1.add(jSeparator1);

        filler1.setName("filler1"); // NOI18N
        jToolBar1.add(filler1);

        jLabel1.setText(bundle.getString("SimpleRestrictionsPanel.jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jToolBar1.add(jLabel1);

        jLabel2.setFont(LafManager.getInstance().getLabFontBold());
        jLabel2.setName("jLabel2"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${status.displayValue}"), jLabel2, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jToolBar1.add(jLabel2);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.GridLayout(1, 3, 15, 0));

        jPanel2.setName(bundle.getString("SimpleRestrictionsPanel.jPanel2.name")); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel13.setText(bundle.getString("SimpleRestrictionsPanel.jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        txtRegDatetime.setName(bundle.getString("SimpleRestrictionsPanel.txtRegDatetime.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${registrationDate}"), txtRegDatetime, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jLabel13, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
            .add(txtRegDatetime, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jLabel13)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtRegDatetime, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 15, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2);

        jPanel6.setName(bundle.getString("SimpleRestrictionsPanel.jPanel6.name")); // NOI18N

        txtRegistrationNo.setName(bundle.getString("SimpleRestrictionsPanel.txtRegistrationNo.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${registrationNumber}"), txtRegistrationNo, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel24.setText(bundle.getString("SimpleRestrictionsPanel.jLabel24.text")); // NOI18N
        jLabel24.setName(bundle.getString("SimpleRestrictionsPanel.jLabel24.name")); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .add(jLabel24)
                .add(0, 108, Short.MAX_VALUE))
            .add(txtRegistrationNo)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .add(jLabel24)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtRegistrationNo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel6);

        groupPanel1.setName("groupPanel1"); // NOI18N
        groupPanel1.setTitleText(bundle.getString("SimpleRestrictionsPanel.groupPanel1.titleText")); // NOI18N

        restrictionPanel.setName("restrictionPanel"); // NOI18N
        restrictionPanel.setLayout(new java.awt.GridLayout(2, 4, 15, 5));

        jPanel9.setName("jPanel9"); // NOI18N

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel5.setText(bundle.getString("SimpleRestrictionsPanel.jLabel5.text_1")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        txtSerialNumber.setName("txtSerialNumber"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${sn}"), txtSerialNumber, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        org.jdesktop.layout.GroupLayout jPanel9Layout = new org.jdesktop.layout.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .add(jLabel5)
                .add(0, 88, Short.MAX_VALUE))
            .add(txtSerialNumber)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .add(jLabel5)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtSerialNumber, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 13, Short.MAX_VALUE))
        );

        restrictionPanel.add(jPanel9);

        jPanel7.setName(bundle.getString("SimpleRestrictionsPanel.jPanel7.name")); // NOI18N

        txtBundleNo.setName(bundle.getString("SimpleRestrictionsPanel.txtBundleNo.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${bundleNumber}"), txtBundleNo, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel22.setText(bundle.getString("SimpleRestrictionsPanel.jLabel22.text")); // NOI18N
        jLabel22.setName(bundle.getString("SimpleRestrictionsPanel.jLabel22.name")); // NOI18N

        jPanel11.setName("jPanel11"); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel11Layout = new org.jdesktop.layout.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(txtBundleNo)
            .add(jPanel7Layout.createSequentialGroup()
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel22)
                    .add(jPanel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(0, 72, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel7Layout.createSequentialGroup()
                .add(jLabel22)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtBundleNo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        restrictionPanel.add(jPanel7);

        jPanel8.setName(bundle.getString("SimpleRestrictionsPanel.jPanel8.name")); // NOI18N

        txtBundlePageNo.setName(bundle.getString("SimpleRestrictionsPanel.txtPanaNo.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${bundlePageNo}"), txtBundlePageNo, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel23.setText(bundle.getString("SimpleRestrictionsPanel.jLabel23.text")); // NOI18N
        jLabel23.setName(bundle.getString("SimpleRestrictionsPanel.jLabel23.name")); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel8Layout = new org.jdesktop.layout.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(txtBundlePageNo)
            .add(jPanel8Layout.createSequentialGroup()
                .add(jLabel23)
                .add(0, 79, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .add(jLabel23)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtBundlePageNo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        restrictionPanel.add(jPanel8);

        jPanel10.setName("jPanel10"); // NOI18N

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel8.setText(bundle.getString("SimpleRestrictionsPanel.jLabel8.text_1")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        cmbRestrictionReason.setName("cmbRestrictionReason"); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${restrictionReasons}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionReasonListBean, eLProperty, cmbRestrictionReason);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${restrictionReason}"), cmbRestrictionReason, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        org.jdesktop.layout.GroupLayout jPanel10Layout = new org.jdesktop.layout.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel10Layout.createSequentialGroup()
                .add(jLabel8)
                .add(0, 63, Short.MAX_VALUE))
            .add(cmbRestrictionReason, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel10Layout.createSequentialGroup()
                .add(jLabel8)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cmbRestrictionReason, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 13, Short.MAX_VALUE))
        );

        restrictionPanel.add(jPanel10);

        jPanel4.setName(bundle.getString("SimpleRestrictionsPanel.jPanel4.name")); // NOI18N

        txtRestrictionOfficeName.setName("txtRestrictionOfficeName"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${restrictionOfficeName}"), txtRestrictionOfficeName, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel3.setText(bundle.getString("SimpleRestrictionsPanel.jLabel3.text_1")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(txtRestrictionOfficeName)
            .add(jPanel4Layout.createSequentialGroup()
                .add(jLabel3)
                .add(0, 4, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .add(jLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtRestrictionOfficeName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        restrictionPanel.add(jPanel4);

        jPanel5.setName("jPanel5"); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel4.setText(bundle.getString("SimpleRestrictionsPanel.jLabel4.text_1")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        txtRestrictionOfficeAddress.setName("txtRestrictionOfficeAddress"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${restrictionOfficeAddress}"), txtRestrictionOfficeAddress, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jLabel4)
                .add(0, 33, Short.MAX_VALUE))
            .add(txtRestrictionOfficeAddress)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtRestrictionOfficeAddress, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 13, Short.MAX_VALUE))
        );

        restrictionPanel.add(jPanel5);

        jPanel12.setName(bundle.getString("SimpleRestrictionsPanel.jPanel12.name")); // NOI18N

        txtPrice.setName(bundle.getString("SimpleRestrictionsPanel.txtPrice.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${mortgageAmount}"), txtPrice, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel20.setText(bundle.getString("SimpleRestrictionsPanel.jLabel20.text")); // NOI18N
        jLabel20.setName(bundle.getString("SimpleRestrictionsPanel.jLabel20.name")); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel12Layout = new org.jdesktop.layout.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jLabel20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 123, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(txtPrice, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel12Layout.createSequentialGroup()
                .add(jLabel20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtPrice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        restrictionPanel.add(jPanel12);

        restrictionReleasePanel.setName("restrictionReleasePanel"); // NOI18N
        restrictionReleasePanel.setLayout(new java.awt.GridLayout(1, 2, 15, 0));

        jPanel14.setName("jPanel14"); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel6.setText(bundle.getString("SimpleRestrictionsPanel.jLabel6.text_1")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        cmbRestrictionReleaseReason.setName("cmbRestrictionReleaseReason"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${restrictionReleaseReasons}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionReleaseReasonListBean, eLProperty, cmbRestrictionReleaseReason);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${restrictionReleaseReason}"), cmbRestrictionReleaseReason, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        org.jdesktop.layout.GroupLayout jPanel14Layout = new org.jdesktop.layout.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel14Layout.createSequentialGroup()
                .add(jLabel6)
                .add(0, 209, Short.MAX_VALUE))
            .add(cmbRestrictionReleaseReason, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel14Layout.createSequentialGroup()
                .add(jLabel6)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cmbRestrictionReleaseReason, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 19, Short.MAX_VALUE))
        );

        restrictionReleasePanel.add(jPanel14);

        jPanel15.setName("jPanel15"); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel7.setText(bundle.getString("SimpleRestrictionsPanel.jLabel7.text_1")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        txtRestrictionReleaseOfficeName.setName("txtRestrictionReleaseOfficeName"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${restrictionReleaseOfficeName}"), txtRestrictionReleaseOfficeName, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        org.jdesktop.layout.GroupLayout jPanel15Layout = new org.jdesktop.layout.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel15Layout.createSequentialGroup()
                .add(jLabel7)
                .add(0, 191, Short.MAX_VALUE))
            .add(txtRestrictionReleaseOfficeName)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel15Layout.createSequentialGroup()
                .add(jLabel7)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtRestrictionReleaseOfficeName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 19, Short.MAX_VALUE))
        );

        restrictionReleasePanel.add(jPanel15);

        documentsPanel.setName(bundle.getString("SimpleRestrictionsPanel.documentsPanel.name")); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(headerPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jToolBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jLabel15)
                        .addContainerGap())
                    .add(layout.createSequentialGroup()
                        .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 415, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, documentsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, groupPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, txtNotationText)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, restrictionReleasePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .add(restrictionPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(headerPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(restrictionPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(restrictionReleasePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jLabel15)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(txtNotationText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(groupPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(documentsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveRrr();
    }//GEN-LAST:event_btnSaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cmbRestrictionReason;
    private javax.swing.JComboBox cmbRestrictionReleaseReason;
    private org.sola.clients.swing.desktop.source.DocumentsManagementExtPanel documentsPanel;
    private javax.swing.Box.Filler filler1;
    private org.sola.clients.swing.ui.GroupPanel groupPanel1;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton printRegistrationLetter;
    private javax.swing.JPanel restrictionPanel;
    private org.sola.clients.beans.referencedata.RestrictionReasonListBean restrictionReasonListBean;
    private javax.swing.JPanel restrictionReleasePanel;
    private org.sola.clients.beans.referencedata.RestrictionReleaseReasonListBean restrictionReleaseReasonListBean;
    private org.sola.clients.beans.administrative.RrrBean rrrBean;
    private javax.swing.JTextField txtBundleNo;
    private javax.swing.JTextField txtBundlePageNo;
    private javax.swing.JTextField txtNotationText;
    private javax.swing.JTextField txtPrice;
    private org.sola.clients.swing.common.controls.NepaliDateField txtRegDatetime;
    private javax.swing.JTextField txtRegistrationNo;
    private javax.swing.JTextField txtRestrictionOfficeAddress;
    private javax.swing.JTextField txtRestrictionOfficeName;
    private javax.swing.JTextField txtRestrictionReleaseOfficeName;
    private javax.swing.JTextField txtSerialNumber;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
