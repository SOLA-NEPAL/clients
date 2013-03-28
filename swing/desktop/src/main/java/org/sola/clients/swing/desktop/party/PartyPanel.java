/**
 * ******************************************************************************************
 *
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
package org.sola.clients.swing.desktop.party;

import java.awt.ComponentOrientation;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.sola.clients.beans.party.PartyBean;
import org.sola.clients.beans.party.PartyRoleBean;
import org.sola.clients.beans.referencedata.*;
import org.sola.clients.swing.common.controls.autocomplete.Configurator;
import org.sola.clients.swing.common.utils.BindingTools;
import org.sola.clients.swing.ui.renderers.SimpleComboBoxRenderer;
import org.sola.common.FileUtility;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;
import org.sola.webservices.transferobjects.EntityAction;

/**
 * Used to create or edit party object. {@link PartyBean} is used to bind data
 * on the form.
 */
public class PartyPanel extends javax.swing.JPanel {

    private PartyBean partyBean;
    private static final String individualLabel = MessageUtility.getLocalizedMessage(
            ClientMessage.GENERAL_LABELS_INDIVIDUAL).getMessage();
    private static final String entityLabel = MessageUtility.getLocalizedMessage(
            ClientMessage.GENERAL_LABELS_ENTITY).getMessage();
    private boolean readOnly = false;
    private FileFilter filter = FileUtility.getFileFilter(new String[]{"png", "bmp", "gif", "jpg"},
            "Image Files (*.png|*.bmp|*.gif|*.jpg)");

    /**
     * Default form constructor.
     */
    public PartyPanel() {
        readOnly = false;
        initComponents();
        postInit();
    }

    /**
     * Form constructor.
     *
     * @param savePartyOnAction Boolean flag to indicate whether to save party
     * when Save button is clicked.
     * @param partyBean {@link PartyBean} instance to display.
     * @param readOnly Indicates whether to allow any changes on the form.
     */
    public PartyPanel(PartyBean partyBean, boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();
        postInit();
        setupPartyBean(partyBean);
    }

    private void postInit() {
        Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
                this, ELProperty.create("${partyBean.parent}"),
                parentPanel, BeanProperty.create("partySummary"), "parentGroup");
        bindingGroup.addBinding(binding);
        bindingGroup.bind();
        comboBoxManageMent();
        districtListBean.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(DistrictListBean.SELECTED_DISTRICT_PROPERTY)) {
                    searchVdc();
                }
            }
        });

        this.partyRoleTypes.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(PartyRoleTypeListBean.SELECTED_PARTYROLETYPE_PROPERTY)) {
                    customizeAddRoleButton((PartyRoleTypeBean) evt.getNewValue());
                }
            }
        });

        districtListBean.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(DistrictListBean.SELECTED_DISTRICT_PROPERTY)) {
                    String districtCode = null;
                    if (evt.getNewValue() != null) {
                        districtCode = ((DistrictBean) evt.getNewValue()).getCode();
                    }
                    vdcListBean.loadList(false, districtCode);
                    cbxVdcs.setSelectedIndex(-1);
                }
            }
        });
        customizeAddRoleButton(null);
        customizeRoleButtons(null);
    }

    private void comboBoxManageMent() {
        Configurator.enableAutoCompletion(cmbDistrict);
        Configurator.enableAutoCompletion(cmbFatherType);
        Configurator.enableAutoCompletion(cmbGender);
        Configurator.enableAutoCompletion(cmbGrandFatherType);
        Configurator.enableAutoCompletion(cmbIdIssuingDistrict);
        Configurator.enableAutoCompletion(cmbIdOffice);
        Configurator.enableAutoCompletion(cmbIdType);
        Configurator.enableAutoCompletion(cmbPreferredCommunication);
        Configurator.enableAutoCompletion(cbxPartyRoleTypes);
        Configurator.enableAutoCompletion(cbxVdcs);
        Configurator.enableAutoCompletion(cbxPartyType);
    }

    private void searchVdc() {
        String code = null;
        if (districtListBean.getSelectedDistrict() != null) {
            code = districtListBean.getSelectedDistrict().getCode();
        }
        vdcListBean.loadList(false, code);
        cbxVdcs.setSelectedIndex(-1);
    }

    private PartyRoleTypeListBean createPartyRolesList() {
        if (partyRoleTypes == null) {
            partyRoleTypes = new PartyRoleTypeListBean(true);
        }
        return partyRoleTypes;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        customizePanel();
    }

    public PartyBean getPartyBean() {
        if (partyBean == null) {
            partyBean = new PartyBean();
        }
        return partyBean;
    }

    public void setPartyBean(PartyBean partyBean) {
        setupPartyBean(partyBean);
    }

    private IdTypeListBean createIdTypes() {
        if (idTypes == null) {
            idTypes = new IdTypeListBean(true);
        }
        return idTypes;
    }

    private IdOfficeTypeListBean createIdOfficeTypes() {
        if (idOfficeTypes == null) {
            idOfficeTypes = new IdOfficeTypeListBean(true);
        }
        return idOfficeTypes;
    }

    private DistrictListBean createIdOfficeDistricts() {
        if (idOfficeDistricts == null) {
            idOfficeDistricts = new DistrictListBean(true);
        }
        return idOfficeDistricts;
    }

    private FatherTypeListBean createFatherTypes() {
        if (fatherTypes == null) {
            fatherTypes = new FatherTypeListBean(true);
        }
        return fatherTypes;
    }

    private GrandFatherTypeListBean createGrandFatherTypes() {
        if (grandFatherTypes == null) {
            grandFatherTypes = new GrandFatherTypeListBean(true);
        }
        return grandFatherTypes;
    }

    private GenderTypeListBean createGenderTypes() {
        if (genderTypes == null) {
            genderTypes = new GenderTypeListBean(true);
        }
        return genderTypes;
    }

    /**
     * Setup reference data bean object, used to bind data on the form.
     */
    private void setupPartyBean(PartyBean partyBean) {
        detailsPanel.setSelectedIndex(0);
        cbxPartyRoleTypes.setSelectedIndex(0);

        if (partyBean != null) {
            this.partyBean = partyBean;
        } else {
            this.partyBean = new PartyBean();
            cmbDistrict.setSelectedIndex(-1);
            cbxVdcs.setSelectedIndex(-1);
            cmbIdOffice.setSelectedIndex(-1);
            cbxPartyType.setSelectedIndex(-1);
            cmbPreferredCommunication.setSelectedIndex(-1);
            cmbFatherType.setSelectedIndex(-1);
            cmbGrandFatherType.setSelectedIndex(-1);
            cmbIdIssuingDistrict.setSelectedIndex(-1);
        }

        idTypes.setExcludedCodes(this.partyBean.getIdTypeCode());
        genderTypes.setExcludedCodes(this.partyBean.getGenderCode());

        this.partyBean.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(PartyBean.SELECTED_ROLE_PROPERTY)) {
                    customizeRoleButtons((PartyRoleBean) evt.getNewValue());
                } else if (evt.getPropertyName().equals(PartyBean.TYPE_PROPERTY)) {
                    PartyTypeBean partyType = (PartyTypeBean) evt.getNewValue();
                    if (partyType != null) {
                        switchPartyType(partyType.isIndividual());
                    } else {
                        switchPartyType(true);
                    }
                }
            }
        });
        if (partyBean != null) {
            DistrictBean district = partyBean.getAddress().getVdcBean().getDistrict();
            VdcBean vdc = this.partyBean.getAddress().getVdcBean();
            districtListBean.setSelectedDistrict(district);
            this.partyBean.getAddress().setVdcBean(vdc);
        }
        if (!readOnly) {
            readOnly = !this.partyBean.checkAccessByOffice();
        }
        customizePanel();
        firePropertyChange("partyBean", null, this.partyBean);
        BindingTools.refreshBinding(bindingGroup, "rolesGroup");
    }

    /**
     * Enables or disables "add", depending on selection in the list of role
     * types and user rights.
     */
    private void customizeAddRoleButton(PartyRoleTypeBean partyRoleType) {
        btnAddRole.setEnabled(partyRoleType != null && !readOnly && partyRoleType.getCode() != null);
    }

    /**
     * Enables or disables "remove" and "view" buttons for roles, depending on
     * selection in the roles list and user rights.
     */
    private void customizeRoleButtons(PartyRoleBean partyRole) {
        btnRemoveRole.setEnabled(partyRole != null && !readOnly);
        menuRemoveRole.setEnabled(btnRemoveRole.isEnabled());
    }

    /**
     * Applies post initialization settings.
     */
    private void customizePanel() {
        if (partyBean.isNew()) {
            switchPartyType(true);
        } else {
            if (partyBean.getPartyType() != null && !partyBean.getPartyType().isIndividual()) {
                labName.setText(entityLabel);
                enableIndividualFields(false);
            } else {
                labName.setText(individualLabel);
                enableIndividualFields(true);
            }
        }

        if (readOnly) {
            cbxPartyType.setEnabled(false);
            txtFirstName.setEnabled(false);
            txtLastName.setEnabled(false);
            cmbIdType.setEnabled(false);
            cmbGender.setEnabled(false);
            cbxPartyRoleTypes.setEnabled(false);
            txtIdref.setEnabled(false);
            txtStreet.setEnabled(false);
            btnAddRole.setEnabled(false);
            btnRemoveRole.setEnabled(false);
            menuRemoveRole.setEnabled(false);
            txtFatherName.setEnabled(false);
            txtPhone.setEnabled(false);
            txtEmail.setEnabled(false);
            txtMobile.setEnabled(false);
            cmbDistrict.setEnabled(false);
            cmbIdOffice.setEnabled(false);
            cbxVdcs.setEnabled(false);
            txtIdIssueDate.setEnabled(false);
            txtBirthday.setEnabled(false);
            txtGrandFatherName.setEnabled(false);
            txtWardNo.setEnabled(false);
            txtRemarks.setEnabled(false);
            btnBrowsePhoto.setEnabled(false);
            btnBrowseLeftFinger.setEnabled(false);
            btnBrowseRightFinger.setEnabled(false);
            btnBrowseSignature.setEnabled(false);
            btnClearImg.setEnabled(false);
            btnClearLFP.setEnabled(false);
            btnClearRFP.setEnabled(false);
            btnClearSignature.setEnabled(false);
            parentPanel.setReadOnly(true);
            txtFax.setEnabled(false);
            cmbPreferredCommunication.setEnabled(false);
            cmbFatherType.setEnabled(false);
            cmbGrandFatherType.setEnabled(false);
            cmbIdIssuingDistrict.setEnabled(false);
        }
    }

    /**
     * Switch individual and entity type
     */
    private void switchPartyType(boolean isIndividual) {
        if (isIndividual) {
            labName.setText(individualLabel);
        } else {
            labName.setText(entityLabel);
        }
        cmbGender.setSelectedIndex(0);
        cmbIdType.setSelectedIndex(0);
        partyBean.setPreferredCommunication(null);
        partyBean.setName(null);
        partyBean.setLastName(null);
        partyBean.setFatherName(null);
        partyBean.setAlias(null);
        partyBean.setIdNumber(null);
        partyBean.setGenderCode(null);
        partyBean.setIdType(null);
        enableIndividualFields(isIndividual);
        cmbIdOffice.setSelectedIndex(-1);
        partyBean.setIdIssueDate(null);
        partyBean.setGrandfatherName(null);
        partyBean.setPhoto(null, 0, 0);
        partyBean.setLeftFingerPrint(null, 0, 0);
        partyBean.setRightFingerPrint(null, 0, 0);
        partyBean.setSignature(null, 0, 0);
        partyBean.setBirthDate(null);
        cmbFatherType.setSelectedIndex(0);
        cmbGrandFatherType.setSelectedIndex(0);
        cmbIdIssuingDistrict.setSelectedIndex(0);
        partyBean.setRemarks(null);
        if (partyBean.getParent() != null) {
            partyBean.getParent().setEntityAction(EntityAction.DISASSOCIATE);
        }
    }

    private void enableIndividualFields(boolean enable) {
        txtLastName.setEnabled(enable);
        txtFatherName.setEnabled(enable);
        cmbGender.setEnabled(enable);
        cmbIdType.setEnabled(enable);
        txtIdref.setEnabled(enable);
        cmbIdOffice.setEnabled(enable);
        txtIdIssueDate.setEnabled(enable);
        txtGrandFatherName.setEnabled(enable);
        txtBirthday.setEnabled(enable);
        cmbFatherType.setEnabled(enable);
        cmbGrandFatherType.setEnabled(enable);
        txtIdIssueDate.setEditable(enable);
        cmbIdIssuingDistrict.setEnabled(enable);
        parentPanel.setReadOnly(!enable);
        btnBrowsePhoto.setEnabled(enable);
        btnBrowseLeftFinger.setEnabled(enable);
        btnBrowseRightFinger.setEnabled(enable);
        btnBrowseSignature.setEnabled(enable);
        btnClearImg.setEnabled(enable);
        btnClearLFP.setEnabled(enable);
        btnClearRFP.setEnabled(enable);
        btnClearSignature.setEnabled(enable);
    }

    public boolean validateParty(boolean showMessage) {
        return partyBean.validate(showMessage).size() < 1;
    }

    public boolean saveParty() {
        if (validateParty(true)) {
            return partyBean.saveParty();
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        idTypes = createIdTypes();
        partyRoleTypes = createPartyRolesList();
        popupRoles = new javax.swing.JPopupMenu();
        menuRemoveRole = new javax.swing.JMenuItem();
        genderTypes = createGenderTypes();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        districtListBean = new org.sola.clients.beans.referencedata.DistrictListBean();
        vdcListBean = new org.sola.clients.beans.referencedata.VdcListBean();
        communicationTypes = new org.sola.clients.beans.referencedata.CommunicationTypeListBean();
        grandFatherTypes = createGrandFatherTypes();
        idOfficeTypes = createIdOfficeTypes();
        fatherTypes = createFatherTypes();
        idOfficeDistricts = createIdOfficeDistricts();
        partyTypeListBean = new org.sola.clients.beans.referencedata.PartyTypeListBean();
        partyCategoryBean1 = new org.sola.clients.beans.referencedata.PartyCategoryBean();
        detailsPanel = new javax.swing.JTabbedPane();
        basicPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        cbxPartyRoleTypes = new javax.swing.JComboBox();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        btnAddRole = new javax.swing.JButton();
        btnRemoveRole = new javax.swing.JButton();
        groupPanel1 = new org.sola.clients.swing.ui.GroupPanel();
        roleTableScrollPanel = new javax.swing.JScrollPane();
        tablePartyRole = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        jPanel10 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        labIdType = new javax.swing.JLabel();
        cmbIdType = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        labIdref = new javax.swing.JLabel();
        txtIdref = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtIdIssueDate = new org.sola.clients.swing.common.controls.NepaliDateField();
        jPanel19 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbIdOffice = new javax.swing.JComboBox();
        jPanel13 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cmbIdIssuingDistrict = new javax.swing.JComboBox();
        groupPanel5 = new org.sola.clients.swing.ui.GroupPanel();
        jPanel32 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cbxPartyType = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        txtFirstName = new javax.swing.JTextField();
        labName = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        labLastName = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        jPanel26 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtBirthday = new org.sola.clients.swing.common.controls.NepaliDateField();
        jPanel8 = new javax.swing.JPanel();
        lblGender = new javax.swing.JLabel();
        cmbGender = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        labFatherFirstName = new javax.swing.JLabel();
        cmbFatherType = new javax.swing.JComboBox();
        jPanel17 = new javax.swing.JPanel();
        labFatherLastName = new javax.swing.JLabel();
        txtFatherName = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cmbGrandFatherType = new javax.swing.JComboBox();
        jPanel24 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtGrandFatherName = new javax.swing.JTextField();
        groupPanel6 = new org.sola.clients.swing.ui.GroupPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cmbDistrict = new javax.swing.JComboBox();
        jPanel21 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cbxVdcs = new javax.swing.JComboBox();
        jPanel22 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtWardNo = new org.sola.clients.swing.common.controls.JTextBoxIntegerNumber();
        jPanel9 = new javax.swing.JPanel();
        labAddress = new javax.swing.JLabel();
        txtStreet = new javax.swing.JTextField();
        groupPanel7 = new org.sola.clients.swing.ui.GroupPanel();
        fullPanel = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        txtMobile = new javax.swing.JTextField();
        labMobile = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtFax = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        txtEmail = new javax.swing.JTextField();
        labEmail = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        cmbPreferredCommunication = new javax.swing.JComboBox();
        groupPanel2 = new org.sola.clients.swing.ui.GroupPanel();
        groupPanel3 = new org.sola.clients.swing.ui.GroupPanel();
        jPanel28 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        lblPhoto = new javax.swing.JLabel();
        btnBrowsePhoto = new javax.swing.JButton();
        btnClearImg = new javax.swing.JButton();
        rightFinger = new javax.swing.JPanel();
        lblRightFinger = new javax.swing.JLabel();
        btnBrowseRightFinger = new javax.swing.JButton();
        btnClearRFP = new javax.swing.JButton();
        signature = new javax.swing.JPanel();
        lblSignature = new javax.swing.JLabel();
        btnBrowseSignature = new javax.swing.JButton();
        btnClearSignature = new javax.swing.JButton();
        leftFinger = new javax.swing.JPanel();
        lblLeftFinger = new javax.swing.JLabel();
        btnBrowseLeftFinger = new javax.swing.JButton();
        btnClearLFP = new javax.swing.JButton();
        parentCustomizePanel = new javax.swing.JPanel();
        parentPanel = new org.sola.clients.swing.desktop.party.PartySelectExtPanel();
        groupPanel4 = new org.sola.clients.swing.ui.GroupPanel();
        jPanel25 = new javax.swing.JPanel();
        txtRemarks = new javax.swing.JTextField();
        groupPanel8 = new org.sola.clients.swing.ui.GroupPanel();
        jPanel16 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();

        popupRoles.setName("popupRoles"); // NOI18N

        menuRemoveRole.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/party/Bundle"); // NOI18N
        menuRemoveRole.setText(bundle.getString("PartyPanel.menuRemoveRole.text")); // NOI18N
        menuRemoveRole.setName("menuRemoveRole"); // NOI18N
        menuRemoveRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRemoveRoleActionPerformed(evt);
            }
        });
        popupRoles.add(menuRemoveRole);

        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("org/sola/clients/swing/ui/party/Bundle"); // NOI18N
        jScrollPane1.setName(bundle1.getString("PartyPanel.jScrollPane1.name")); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setName(bundle1.getString("PartyPanel.jTextArea1.name")); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        setMinimumSize(new java.awt.Dimension(645, 510));
        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(778, 550));

        detailsPanel.setFont(new java.awt.Font("Thaoma", 0, 12));
        detailsPanel.setName("detailsPanel"); // NOI18N

        basicPanel.setName("basicPanel"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        cbxPartyRoleTypes.setName("cbxPartyRoleTypes"); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${partyRoleTypeList}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, partyRoleTypes, eLProperty, cbxPartyRoleTypes);
        bindingGroup.addBinding(jComboBoxBinding);
        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, partyRoleTypes, org.jdesktop.beansbinding.ELProperty.create("${selectedPartyRoleType}"), cbxPartyRoleTypes, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jToolBar1.add(cbxPartyRoleTypes);

        filler1.setName("filler1"); // NOI18N
        jToolBar1.add(filler1);

        btnAddRole.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        btnAddRole.setText(bundle.getString("PartyPanel.btnAddRole.text")); // NOI18N
        btnAddRole.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAddRole.setName("btnAddRole"); // NOI18N
        btnAddRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRoleActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAddRole);

        btnRemoveRole.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemoveRole.setText(bundle.getString("PartyPanel.btnRemoveRole.text")); // NOI18N
        btnRemoveRole.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRemoveRole.setName("btnRemoveRole"); // NOI18N
        btnRemoveRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveRoleActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRemoveRole);

        groupPanel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        groupPanel1.setName("groupPanel1"); // NOI18N
        groupPanel1.setTitleText(bundle.getString("PartyPanel.groupPanel1.titleText")); // NOI18N

        roleTableScrollPanel.setName("roleTableScrollPanel"); // NOI18N

        tablePartyRole.setComponentPopupMenu(popupRoles);
        tablePartyRole.setName("tablePartyRole"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${partyBean.filteredRoleList}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, tablePartyRole, "rolesGroup");
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${role.displayValue}"));
        columnBinding.setColumnName("Role.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.selectedRole}"), tablePartyRole, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        roleTableScrollPanel.setViewportView(tablePartyRole);
        tablePartyRole.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("PartyPanel.tablePartyRole.columnModel.title0")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(groupPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(roleTableScrollPanel, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(groupPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roleTableScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
        );

        jPanel10.setName("jPanel10"); // NOI18N
        jPanel10.setLayout(new java.awt.GridLayout(0, 5, 15, 0));

        jPanel6.setName("jPanel6"); // NOI18N

        labIdType.setText(bundle.getString("PartyPanel.labIdType.text")); // NOI18N
        labIdType.setName("labIdType"); // NOI18N

        cmbIdType.setBackground(new java.awt.Color(226, 244, 224));
        cmbIdType.setName("cmbIdType"); // NOI18N
        cmbIdType.setRenderer(new SimpleComboBoxRenderer("getDisplayValue"));

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${idTypeList}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, idTypes, eLProperty, cmbIdType);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.idType}"), cmbIdType, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cmbIdType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(labIdType)
                .addGap(0, 103, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(labIdType)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbIdType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel6);

        jPanel7.setName("jPanel7"); // NOI18N

        labIdref.setText(bundle.getString("PartyPanel.labIdref.text")); // NOI18N
        labIdref.setName("labIdref"); // NOI18N

        txtIdref.setName("txtIdref"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.idNumber}"), txtIdref, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtIdref)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(labIdref)
                .addGap(0, 89, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(labIdref)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIdref, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel7);

        jPanel20.setName(bundle1.getString("PartyPanel.jPanel20.name")); // NOI18N

        jLabel2.setText(bundle.getString("PartyPanel.jLabel2.text")); // NOI18N
        jLabel2.setName(bundle1.getString("PartyPanel.jLabel2.name")); // NOI18N

        txtIdIssueDate.setName(bundle.getString("PartyPanel.txtIdIssueDate.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.idIssueDate}"), txtIdIssueDate, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 76, Short.MAX_VALUE))
            .addComponent(txtIdIssueDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIdIssueDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel20);

        jPanel19.setName(bundle1.getString("PartyPanel.jPanel19.name")); // NOI18N

        jLabel1.setText(bundle.getString("PartyPanel.jLabel1.text")); // NOI18N
        jLabel1.setName(bundle1.getString("PartyPanel.jLabel1.name")); // NOI18N

        cmbIdOffice.setName(bundle.getString("PartyPanel.cmbIdOffice.name")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${idOfficeTypeList}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, idOfficeTypes, eLProperty, cmbIdOffice);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.idOfficeType}"), cmbIdOffice, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 63, Short.MAX_VALUE))
            .addComponent(cmbIdOffice, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbIdOffice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel19);

        jPanel13.setName("jPanel13"); // NOI18N

        jLabel10.setText(bundle.getString("PartyPanel.jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        cmbIdIssuingDistrict.setName("cmbIdIssuingDistrict"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${districts}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, idOfficeDistricts, eLProperty, cmbIdIssuingDistrict);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.idIssuingDistrict}"), cmbIdIssuingDistrict, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(0, 58, Short.MAX_VALUE))
            .addComponent(cmbIdIssuingDistrict, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbIdIssuingDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel13);

        groupPanel5.setName(bundle.getString("PartyPanel.groupPanel5.name")); // NOI18N
        groupPanel5.setTitleText(bundle.getString("PartyPanel.groupPanel5.titleText")); // NOI18N

        jPanel32.setName(bundle.getString("PartyPanel.jPanel32.name")); // NOI18N
        jPanel32.setLayout(new java.awt.GridLayout(3, 4, 15, 0));

        jPanel3.setName(bundle.getString("PartyPanel.jPanel3.name")); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel7.setText(bundle.getString("PartyPanel.jLabel7.text")); // NOI18N
        jLabel7.setName(bundle.getString("PartyPanel.jLabel7.name")); // NOI18N

        cbxPartyType.setName(bundle.getString("PartyPanel.cbxPartyType.name")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${partyTypes}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, partyTypeListBean, eLProperty, cbxPartyType);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.partyType}"), cbxPartyType, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbxPartyType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 203, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxPartyType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel32.add(jPanel3);

        jPanel4.setName("jPanel4"); // NOI18N

        txtFirstName.setDisabledTextColor(new java.awt.Color(240, 240, 240));
        txtFirstName.setName("txtFirstName"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.name}"), txtFirstName, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtFirstName.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        txtFirstName.setHorizontalAlignment(JTextField.LEADING);

        labName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        labName.setText(bundle.getString("PartyPanel.labName.text")); // NOI18N
        labName.setName(bundle.getString("PartyPanel.labName.name")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(labName)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(labName)
                .addGap(6, 6, 6)
                .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel32.add(jPanel4);

        jPanel5.setName("jPanel5"); // NOI18N

        labLastName.setText(bundle1.getString("ApplicationForm.labLastName.text")); // NOI18N
        labLastName.setIconTextGap(1);
        labLastName.setName("labLastName"); // NOI18N

        txtLastName.setName("txtLastName"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.lastName}"), txtLastName, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtLastName.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        txtLastName.setHorizontalAlignment(JTextField.LEADING);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtLastName)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(labLastName)
                .addGap(0, 192, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(labLastName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel32.add(jPanel5);

        jPanel26.setName(bundle1.getString("PartyPanel.jPanel26.name")); // NOI18N

        jLabel8.setText(bundle.getString("PartyPanel.jLabel8.text")); // NOI18N
        jLabel8.setName(bundle1.getString("PartyPanel.jLabel8.name")); // NOI18N

        txtBirthday.setName(bundle.getString("PartyPanel.txtBirthday.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.birthDate}"), txtBirthday, org.jdesktop.beansbinding.BeanProperty.create("value"), "");
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtBirthday, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel32.add(jPanel26);

        jPanel8.setName("jPanel8"); // NOI18N

        lblGender.setText(bundle1.getString("PartyPanel.labGender.text")); // NOI18N
        lblGender.setToolTipText(bundle1.getString("PartyPanel.labGender.toolTipText")); // NOI18N
        lblGender.setName("labGender"); // NOI18N

        cmbGender.setBackground(new java.awt.Color(226, 244, 224));
        cmbGender.setName("cmbGender"); // NOI18N
        cmbGender.setRenderer(new SimpleComboBoxRenderer("getDisplayValue"));

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${genderTypeList}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, genderTypes, eLProperty, cmbGender);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.genderType}"), cmbGender, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cmbGender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(lblGender)
                .addGap(0, 206, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(lblGender)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel32.add(jPanel8);

        jPanel2.setName("jPanel2"); // NOI18N

        labFatherFirstName.setText(bundle.getString("PartyPanel.labFatherFirstName.text")); // NOI18N
        labFatherFirstName.setName("labFatherFirstName"); // NOI18N

        cmbFatherType.setName("cmbFatherType"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${fatherTypes}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, fatherTypes, eLProperty, cmbFatherType);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.fatherType}"), cmbFatherType, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cmbFatherType, 0, 208, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(labFatherFirstName)
                .addGap(0, 163, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(labFatherFirstName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbFatherType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel32.add(jPanel2);

        jPanel17.setName("jPanel17"); // NOI18N

        labFatherLastName.setText(bundle.getString("PartyPanel.labFatherLastName.text")); // NOI18N
        labFatherLastName.setName("labFatherLastName"); // NOI18N

        txtFatherName.setName("txtFatherName"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.fatherName}"), txtFatherName, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(labFatherLastName)
                .addContainerGap(134, Short.MAX_VALUE))
            .addComponent(txtFatherName, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(labFatherLastName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFatherName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel32.add(jPanel17);

        jPanel23.setName(bundle1.getString("PartyPanel.jPanel23.name")); // NOI18N

        jLabel5.setText(bundle.getString("PartyPanel.jLabel5.text")); // NOI18N
        jLabel5.setName(bundle1.getString("PartyPanel.jLabel5.name")); // NOI18N

        cmbGrandFatherType.setName("cmbGrandFatherType"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${grandFatherTypeList}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, grandFatherTypes, eLProperty, cmbGrandFatherType);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.grandFatherType}"), cmbGrandFatherType, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cmbGrandFatherType, 0, 208, Short.MAX_VALUE)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 157, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbGrandFatherType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel32.add(jPanel23);

        jPanel24.setName(bundle1.getString("PartyPanel.jPanel24.name")); // NOI18N

        jLabel6.setText(bundle.getString("PartyPanel.jLabel6.text")); // NOI18N
        jLabel6.setName(bundle1.getString("PartyPanel.jLabel6.name")); // NOI18N

        txtGrandFatherName.setName(bundle1.getString("PartyPanel.txtGrandFatherLastName.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.grandfatherName}"), txtGrandFatherName, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtGrandFatherName, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(0, 84, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGrandFatherName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel32.add(jPanel24);

        groupPanel6.setName(bundle.getString("PartyPanel.groupPanel6.name")); // NOI18N
        groupPanel6.setTitleText(bundle.getString("PartyPanel.groupPanel6.titleText")); // NOI18N

        jPanel11.setName(bundle.getString("PartyPanel.jPanel11.name")); // NOI18N
        jPanel11.setLayout(new java.awt.GridLayout(1, 4, 15, 0));

        jPanel27.setName(bundle1.getString("PartyPanel.jPanel27.name")); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel9.setText(bundle.getString("PartyPanel.jLabel9.text")); // NOI18N
        jLabel9.setName(bundle1.getString("PartyPanel.jLabel9.name")); // NOI18N

        cmbDistrict.setBackground(new java.awt.Color(226, 244, 224));
        cmbDistrict.setName(bundle1.getString("PartyPanel.cboDistrict.name")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${districts}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, districtListBean, eLProperty, cmbDistrict);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, districtListBean, org.jdesktop.beansbinding.ELProperty.create("${selectedDistrict}"), cmbDistrict, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cmbDistrict.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDistrictItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(0, 101, Short.MAX_VALUE))
            .addComponent(cmbDistrict, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel11.add(jPanel27);

        jPanel21.setName(bundle1.getString("PartyPanel.jPanel21.name")); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel3.setText(bundle.getString("PartyPanel.jLabel3.text")); // NOI18N
        jLabel3.setName(bundle1.getString("PartyPanel.jLabel3.name")); // NOI18N

        cbxVdcs.setBackground(new java.awt.Color(226, 244, 224));
        cbxVdcs.setName(bundle1.getString("PartyPanel.cboVDCs.name")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${vdcs}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vdcListBean, eLProperty, cbxVdcs);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.address.vdcBean}"), cbxVdcs, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbxVdcs, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 84, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxVdcs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.add(jPanel21);

        jPanel22.setName(bundle1.getString("PartyPanel.jPanel22.name")); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel4.setText(bundle.getString("PartyPanel.jLabel4.text")); // NOI18N
        jLabel4.setName(bundle1.getString("PartyPanel.jLabel4.name")); // NOI18N

        txtWardNo.setName(bundle.getString("PartyPanel.txtWardNo.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.address.wardNo}"), txtWardNo, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 98, Short.MAX_VALUE))
            .addComponent(txtWardNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtWardNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.add(jPanel22);

        jPanel9.setName("jPanel9"); // NOI18N

        labAddress.setText(bundle.getString("PartyPanel.labAddress.text")); // NOI18N
        labAddress.setName("labAddress"); // NOI18N

        txtStreet.setName("txtStreet"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.address.street}"), txtStreet, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtStreet.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        txtStreet.setHorizontalAlignment(JTextField.LEADING);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtStreet)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(labAddress)
                .addGap(0, 147, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(labAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.add(jPanel9);

        groupPanel7.setName(bundle.getString("PartyPanel.groupPanel7.name")); // NOI18N
        groupPanel7.setTitleText(bundle.getString("PartyPanel.groupPanel7.titleText")); // NOI18N

        javax.swing.GroupLayout basicPanelLayout = new javax.swing.GroupLayout(basicPanel);
        basicPanel.setLayout(basicPanelLayout);
        basicPanelLayout.setHorizontalGroup(
            basicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basicPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(basicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(groupPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(groupPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(groupPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        basicPanelLayout.setVerticalGroup(
            basicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basicPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(groupPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groupPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groupPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        detailsPanel.addTab(bundle.getString("PartyPanel.basicPanel.TabConstraints.tabTitle"), basicPanel); // NOI18N

        fullPanel.setName("fullPanel"); // NOI18N

        jPanel18.setName("jPanel18"); // NOI18N
        jPanel18.setLayout(new java.awt.GridLayout(0, 5, 15, 0));

        jPanel31.setName("jPanel31"); // NOI18N

        jLabel13.setText(bundle.getString("PartyPanel.jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        txtPhone.setName("txtPhone"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.phone}"), txtPhone, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addGap(0, 108, Short.MAX_VALUE))
            .addComponent(txtPhone)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel18.add(jPanel31);

        jPanel12.setName("jPanel12"); // NOI18N

        txtMobile.setName("txtMobile"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.mobile}"), txtMobile, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        labMobile.setText(bundle.getString("PartyPanel.labMobile.text")); // NOI18N
        labMobile.setName("labMobile"); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(labMobile)
                .addContainerGap(108, Short.MAX_VALUE))
            .addComponent(txtMobile)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(labMobile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel18.add(jPanel12);

        jPanel15.setName("jPanel15"); // NOI18N

        jLabel11.setText(bundle.getString("PartyPanel.jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        txtFax.setName("txtFax"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.fax}"), txtFax, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(0, 120, Short.MAX_VALUE))
            .addComponent(txtFax)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel18.add(jPanel15);

        jPanel14.setName("jPanel14"); // NOI18N

        txtEmail.setName("txtEmail"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.email}"), txtEmail, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtEmail.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        txtEmail.setHorizontalAlignment(JTextField.LEADING);

        labEmail.setText(bundle.getString("PartyPanel.labEmail.text")); // NOI18N
        labEmail.setName("labEmail"); // NOI18N

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(labEmail)
                .addContainerGap(110, Short.MAX_VALUE))
            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(labEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel18.add(jPanel14);

        jPanel30.setName("jPanel30"); // NOI18N

        jLabel12.setText(bundle.getString("PartyPanel.jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        cmbPreferredCommunication.setName("cmbPreferredCommunication"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${communicationTypeList}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, communicationTypes, eLProperty, cmbPreferredCommunication);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.preferredCommunication}"), cmbPreferredCommunication, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addGap(0, 19, Short.MAX_VALUE))
            .addComponent(cmbPreferredCommunication, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbPreferredCommunication, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel18.add(jPanel30);

        groupPanel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        groupPanel2.setName("groupPanel2"); // NOI18N
        groupPanel2.setTitleText(bundle.getString("PartyPanel.groupPanel2.titleText")); // NOI18N

        groupPanel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        groupPanel3.setName(bundle1.getString("PartyPanel.groupPanel3.name")); // NOI18N
        groupPanel3.setTitleText(bundle.getString("PartyPanel.groupPanel3.titleText")); // NOI18N

        jPanel28.setName(bundle1.getString("PartyPanel.jPanel28.name")); // NOI18N
        jPanel28.setLayout(new java.awt.GridLayout(1, 4, 5, 0));

        jPanel29.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("PartyPanel.jPanel29.border.title"))); // NOI18N
        jPanel29.setName(bundle1.getString("PartyPanel.jPanel29.name")); // NOI18N

        lblPhoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 255, 51)));
        lblPhoto.setName(bundle1.getString("PartyPanel.lblPhoto.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.photo}"), lblPhoto, org.jdesktop.beansbinding.BeanProperty.create("icon"));
        bindingGroup.addBinding(binding);

        btnBrowsePhoto.setText(bundle.getString("PartyPanel.btnBrowsePhoto.text")); // NOI18N
        btnBrowsePhoto.setName(bundle1.getString("PartyPanel.btnBrowsePhoto.name")); // NOI18N
        btnBrowsePhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowsePhotoActionPerformed(evt);
            }
        });

        btnClearImg.setText(bundle.getString("PartyPanel.btnClearImg.text")); // NOI18N
        btnClearImg.setName(bundle1.getString("PartyPanel.btnClearImg.name")); // NOI18N
        btnClearImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearImgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addComponent(btnClearImg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBrowsePhoto)
                .addContainerGap(13, Short.MAX_VALUE))
            .addComponent(lblPhoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addComponent(lblPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClearImg)
                    .addComponent(btnBrowsePhoto))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel28.add(jPanel29);

        rightFinger.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("PartyPanel.rightFinger.border.title"))); // NOI18N
        rightFinger.setName(bundle1.getString("PartyPanel.rightFinger.name")); // NOI18N

        lblRightFinger.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 0)));
        lblRightFinger.setName(bundle1.getString("PartyPanel.lblRightFinger.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.rightFingerPrint}"), lblRightFinger, org.jdesktop.beansbinding.BeanProperty.create("icon"));
        bindingGroup.addBinding(binding);

        btnBrowseRightFinger.setText(bundle.getString("PartyPanel.btnBrowseRightFinger.text")); // NOI18N
        btnBrowseRightFinger.setName(bundle1.getString("PartyPanel.btnBrowseRightFinger.name")); // NOI18N
        btnBrowseRightFinger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseRightFingerActionPerformed(evt);
            }
        });

        btnClearRFP.setText(bundle.getString("PartyPanel.btnClearRFP.text")); // NOI18N
        btnClearRFP.setName(bundle1.getString("PartyPanel.btnClearRFP.name")); // NOI18N
        btnClearRFP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearRFPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rightFingerLayout = new javax.swing.GroupLayout(rightFinger);
        rightFinger.setLayout(rightFingerLayout);
        rightFingerLayout.setHorizontalGroup(
            rightFingerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightFingerLayout.createSequentialGroup()
                .addComponent(btnClearRFP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBrowseRightFinger)
                .addGap(0, 13, Short.MAX_VALUE))
            .addComponent(lblRightFinger, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        rightFingerLayout.setVerticalGroup(
            rightFingerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightFingerLayout.createSequentialGroup()
                .addComponent(lblRightFinger, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(rightFingerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClearRFP)
                    .addComponent(btnBrowseRightFinger))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel28.add(rightFinger);

        signature.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("PartyPanel.signature.border.title"))); // NOI18N
        signature.setName(bundle1.getString("PartyPanel.signature.name")); // NOI18N

        lblSignature.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 255, 0)));
        lblSignature.setName(bundle1.getString("PartyPanel.lblSignature.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.signature}"), lblSignature, org.jdesktop.beansbinding.BeanProperty.create("icon"));
        bindingGroup.addBinding(binding);

        btnBrowseSignature.setText(bundle.getString("PartyPanel.btnBrowseSignature.text")); // NOI18N
        btnBrowseSignature.setName(bundle1.getString("PartyPanel.btnBrowseSignature.name")); // NOI18N
        btnBrowseSignature.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseSignatureActionPerformed(evt);
            }
        });

        btnClearSignature.setText(bundle.getString("PartyPanel.btnClearSignature.text")); // NOI18N
        btnClearSignature.setName(bundle1.getString("PartyPanel.btnClearSignature.name")); // NOI18N
        btnClearSignature.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearSignatureActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout signatureLayout = new javax.swing.GroupLayout(signature);
        signature.setLayout(signatureLayout);
        signatureLayout.setHorizontalGroup(
            signatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signatureLayout.createSequentialGroup()
                .addComponent(btnClearSignature)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBrowseSignature)
                .addGap(0, 13, Short.MAX_VALUE))
            .addComponent(lblSignature, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        signatureLayout.setVerticalGroup(
            signatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signatureLayout.createSequentialGroup()
                .addComponent(lblSignature, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(signatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClearSignature)
                    .addComponent(btnBrowseSignature))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel28.add(signature);

        leftFinger.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("PartyPanel.leftFinger.border.title"))); // NOI18N
        leftFinger.setName(bundle1.getString("PartyPanel.leftFinger.name")); // NOI18N

        lblLeftFinger.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 0)));
        lblLeftFinger.setName(bundle1.getString("PartyPanel.lblLeftFinger.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.leftFingerPrint}"), lblLeftFinger, org.jdesktop.beansbinding.BeanProperty.create("icon"));
        bindingGroup.addBinding(binding);

        btnBrowseLeftFinger.setText(bundle.getString("PartyPanel.btnBrowseLeftFinger.text")); // NOI18N
        btnBrowseLeftFinger.setName(bundle1.getString("PartyPanel.btnBrowseLeftFinger.name")); // NOI18N
        btnBrowseLeftFinger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseLeftFingerActionPerformed(evt);
            }
        });

        btnClearLFP.setText(bundle.getString("PartyPanel.btnClearLFP.text")); // NOI18N
        btnClearLFP.setName(bundle1.getString("PartyPanel.btnClearLFP.name")); // NOI18N
        btnClearLFP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearLFPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout leftFingerLayout = new javax.swing.GroupLayout(leftFinger);
        leftFinger.setLayout(leftFingerLayout);
        leftFingerLayout.setHorizontalGroup(
            leftFingerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftFingerLayout.createSequentialGroup()
                .addComponent(btnClearLFP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBrowseLeftFinger)
                .addGap(0, 13, Short.MAX_VALUE))
            .addComponent(lblLeftFinger, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        leftFingerLayout.setVerticalGroup(
            leftFingerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftFingerLayout.createSequentialGroup()
                .addComponent(lblLeftFinger, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(leftFingerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClearLFP)
                    .addComponent(btnBrowseLeftFinger))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel28.add(leftFinger);

        parentCustomizePanel.setName("parentCustomizePanel"); // NOI18N

        parentPanel.setName("parentPanel"); // NOI18N

        groupPanel4.setName("groupPanel4"); // NOI18N
        groupPanel4.setTitleText(bundle.getString("PartyPanel.groupPanel4.titleText")); // NOI18N

        javax.swing.GroupLayout parentCustomizePanelLayout = new javax.swing.GroupLayout(parentCustomizePanel);
        parentCustomizePanel.setLayout(parentCustomizePanelLayout);
        parentCustomizePanelLayout.setHorizontalGroup(
            parentCustomizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(groupPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        parentCustomizePanelLayout.setVerticalGroup(
            parentCustomizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parentCustomizePanelLayout.createSequentialGroup()
                .addComponent(groupPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel25.setName(bundle1.getString("PartyPanel.jPanel25.name")); // NOI18N

        txtRemarks.setName(bundle1.getString("PartyPanel.txtRemarks.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.remarks}"), txtRemarks, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        groupPanel8.setName(bundle.getString("PartyPanel.groupPanel8.name")); // NOI18N
        groupPanel8.setTitleText(bundle.getString("PartyPanel.groupPanel8.titleText")); // NOI18N

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtRemarks)
            .addComponent(groupPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(groupPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtRemarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setName(bundle.getString("PartyPanel.jPanel16.name")); // NOI18N

        jCheckBox1.setText(bundle.getString("PartyPanel.jCheckBox1.text")); // NOI18N
        jCheckBox1.setName(bundle.getString("PartyPanel.jCheckBox1.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.handicapped}"), jCheckBox1, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        jCheckBox2.setText(bundle.getString("PartyPanel.jCheckBox2.text")); // NOI18N
        jCheckBox2.setName(bundle.getString("PartyPanel.jCheckBox2.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.deprived}"), jCheckBox2, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        jCheckBox3.setText(bundle.getString("PartyPanel.jCheckBox3.text")); // NOI18N
        jCheckBox3.setName(bundle.getString("PartyPanel.jCheckBox3.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.martyr}"), jCheckBox3, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox3)
                .addContainerGap(127, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout fullPanelLayout = new javax.swing.GroupLayout(fullPanel);
        fullPanel.setLayout(fullPanelLayout);
        fullPanelLayout.setHorizontalGroup(
            fullPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fullPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fullPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(parentCustomizePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(fullPanelLayout.createSequentialGroup()
                        .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(groupPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE))
                .addContainerGap())
        );
        fullPanelLayout.setVerticalGroup(
            fullPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fullPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parentCustomizePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(groupPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groupPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(fullPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fullPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fullPanelLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        detailsPanel.addTab(bundle.getString("PartyPanel.fullPanel.TabConstraints.tabTitle"), fullPanel); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(detailsPanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(detailsPanel, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRoleActionPerformed
        addRole();
    }//GEN-LAST:event_btnAddRoleActionPerformed

    private void btnRemoveRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveRoleActionPerformed
        removeRole();
    }//GEN-LAST:event_btnRemoveRoleActionPerformed

    private void menuRemoveRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRemoveRoleActionPerformed
        removeRole();
    }//GEN-LAST:event_menuRemoveRoleActionPerformed

    private void cmbDistrictItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDistrictItemStateChanged
        // TODO add your handling code here:
        filterVdcsFromSelectedDistricts();
    }//GEN-LAST:event_cmbDistrictItemStateChanged

    private void btnClearLFPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearLFPActionPerformed
        partyBean.setLeftFingerPrint(null, 0, 0);
    }//GEN-LAST:event_btnClearLFPActionPerformed

    private void btnBrowseLeftFingerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseLeftFingerActionPerformed
        partyBean.setLeftFingerPrint(FileUtility.getInstance().getFile(filter, this),
                lblLeftFinger.getWidth() - 2, lblLeftFinger.getHeight());
    }//GEN-LAST:event_btnBrowseLeftFingerActionPerformed

    private void btnClearSignatureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSignatureActionPerformed
        partyBean.setSignature(null, 0, 0);
    }//GEN-LAST:event_btnClearSignatureActionPerformed

    private void btnBrowseSignatureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseSignatureActionPerformed
        partyBean.setSignature(FileUtility.getInstance().getFile(filter, this),
                lblSignature.getWidth() - 2, lblSignature.getHeight());
    }//GEN-LAST:event_btnBrowseSignatureActionPerformed

    private void btnClearRFPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearRFPActionPerformed
        partyBean.setRightFingerPrint(null, 0, 0);
    }//GEN-LAST:event_btnClearRFPActionPerformed

    private void btnBrowseRightFingerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseRightFingerActionPerformed
        partyBean.setRightFingerPrint(FileUtility.getInstance().getFile(filter, this),
                lblRightFinger.getWidth() - 2, lblRightFinger.getHeight());
    }//GEN-LAST:event_btnBrowseRightFingerActionPerformed

    private void btnClearImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearImgActionPerformed
        partyBean.setPhoto(null, 0, 0);
    }//GEN-LAST:event_btnClearImgActionPerformed

    private void btnBrowsePhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowsePhotoActionPerformed
        partyBean.setPhoto(FileUtility.getInstance().getFile(filter, this),
                lblPhoto.getWidth() - 2, lblPhoto.getHeight());
    }//GEN-LAST:event_btnBrowsePhotoActionPerformed

    private void filterVdcsFromSelectedDistricts() {
        // TODO add your handling code here:        
        try {
            if (cmbDistrict.getSelectedItem() == null) {
                return;
            }

            DistrictBean district = (DistrictBean) cmbDistrict.getSelectedItem();
            if (district != null) {
                String districCode = district.getCode();

                vdcListBean.loadList(false, districCode);
                cbxVdcs.setSelectedIndex(-1);
            }
        } catch (Exception e) {
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel basicPanel;
    private javax.swing.JButton btnAddRole;
    private javax.swing.JButton btnBrowseLeftFinger;
    private javax.swing.JButton btnBrowsePhoto;
    private javax.swing.JButton btnBrowseRightFinger;
    private javax.swing.JButton btnBrowseSignature;
    private javax.swing.JButton btnClearImg;
    private javax.swing.JButton btnClearLFP;
    private javax.swing.JButton btnClearRFP;
    private javax.swing.JButton btnClearSignature;
    private javax.swing.JButton btnRemoveRole;
    private javax.swing.JComboBox cbxPartyRoleTypes;
    private javax.swing.JComboBox cbxPartyType;
    private javax.swing.JComboBox cbxVdcs;
    private javax.swing.JComboBox cmbDistrict;
    private javax.swing.JComboBox cmbFatherType;
    public javax.swing.JComboBox cmbGender;
    private javax.swing.JComboBox cmbGrandFatherType;
    private javax.swing.JComboBox cmbIdIssuingDistrict;
    private javax.swing.JComboBox cmbIdOffice;
    public javax.swing.JComboBox cmbIdType;
    private javax.swing.JComboBox cmbPreferredCommunication;
    private org.sola.clients.beans.referencedata.CommunicationTypeListBean communicationTypes;
    private javax.swing.JTabbedPane detailsPanel;
    private org.sola.clients.beans.referencedata.DistrictListBean districtListBean;
    private org.sola.clients.beans.referencedata.FatherTypeListBean fatherTypes;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JPanel fullPanel;
    private org.sola.clients.beans.referencedata.GenderTypeListBean genderTypes;
    private org.sola.clients.beans.referencedata.GrandFatherTypeListBean grandFatherTypes;
    private org.sola.clients.swing.ui.GroupPanel groupPanel1;
    private org.sola.clients.swing.ui.GroupPanel groupPanel2;
    private org.sola.clients.swing.ui.GroupPanel groupPanel3;
    private org.sola.clients.swing.ui.GroupPanel groupPanel4;
    private org.sola.clients.swing.ui.GroupPanel groupPanel5;
    private org.sola.clients.swing.ui.GroupPanel groupPanel6;
    private org.sola.clients.swing.ui.GroupPanel groupPanel7;
    private org.sola.clients.swing.ui.GroupPanel groupPanel8;
    private org.sola.clients.beans.referencedata.DistrictListBean idOfficeDistricts;
    private org.sola.clients.beans.referencedata.IdOfficeTypeListBean idOfficeTypes;
    private org.sola.clients.beans.referencedata.IdTypeListBean idTypes;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JPanel jPanel14;
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
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel labAddress;
    private javax.swing.JLabel labEmail;
    private javax.swing.JLabel labFatherFirstName;
    private javax.swing.JLabel labFatherLastName;
    private javax.swing.JLabel labIdType;
    private javax.swing.JLabel labIdref;
    private javax.swing.JLabel labLastName;
    private javax.swing.JLabel labMobile;
    private javax.swing.JLabel labName;
    private javax.swing.JLabel lblGender;
    private javax.swing.JLabel lblLeftFinger;
    private javax.swing.JLabel lblPhoto;
    private javax.swing.JLabel lblRightFinger;
    private javax.swing.JLabel lblSignature;
    private javax.swing.JPanel leftFinger;
    private javax.swing.JMenuItem menuRemoveRole;
    private javax.swing.JPanel parentCustomizePanel;
    private org.sola.clients.swing.desktop.party.PartySelectExtPanel parentPanel;
    private org.sola.clients.beans.referencedata.PartyCategoryBean partyCategoryBean1;
    private org.sola.clients.beans.referencedata.PartyRoleTypeListBean partyRoleTypes;
    private org.sola.clients.beans.referencedata.PartyTypeListBean partyTypeListBean;
    private javax.swing.JPopupMenu popupRoles;
    private javax.swing.JPanel rightFinger;
    private javax.swing.JScrollPane roleTableScrollPanel;
    private javax.swing.JPanel signature;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tablePartyRole;
    private org.sola.clients.swing.common.controls.NepaliDateField txtBirthday;
    public javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFatherName;
    private javax.swing.JTextField txtFax;
    public javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtGrandFatherName;
    private org.sola.clients.swing.common.controls.NepaliDateField txtIdIssueDate;
    private javax.swing.JTextField txtIdref;
    public javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtMobile;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtRemarks;
    public javax.swing.JTextField txtStreet;
    private org.sola.clients.swing.common.controls.JTextBoxIntegerNumber txtWardNo;
    private org.sola.clients.beans.referencedata.VdcListBean vdcListBean;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private void addRole() {
        if (partyRoleTypes.getSelectedPartyRoleType() == null) {
            MessageUtility.displayMessage(ClientMessage.PARTY_SELECT_ROLE);
            return;
        } else {
            if (partyBean.checkRoleExists(partyRoleTypes.getSelectedPartyRoleType())) {
                MessageUtility.displayMessage(ClientMessage.PARTY_ALREADYSELECTED_ROLE);
                return;
            }
        }
        partyBean.addRole(partyRoleTypes.getSelectedPartyRoleType());
    }

    private void removeRole() {
        partyBean.removeSelectedRole();
    }
}
