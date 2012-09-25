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

import java.awt.Color;
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
import org.sola.clients.swing.common.utils.BindingTools;
import org.sola.clients.swing.ui.renderers.SimpleComboBoxRenderer;
import org.sola.common.FileUtility;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Used to create or edit party object. {@link PartyBean} is used to bind data
 * on the form.
 */
public class PartyPanel extends javax.swing.JPanel {

    private PartyBean partyBean;
    static String individualString = "naturalPerson";
    static String entityString = "nonNaturalPerson";
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
                    String districtCode = ((DistrictBean) evt.getNewValue()).getCode();
                    vdcListBean.loadList(false, districtCode);
                    cmbVDCs.setSelectedIndex(-1);
                    officeListBean.loadList(false, districtCode);
                    cmbIdOffice.setSelectedIndex(-1);
                }
            }
        });

        customizeParentChildPanel(false);

        customizeAddRoleButton(null);
        customizeRoleButtons(null);
    }

    private void postInit() {
        Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
                partyBean, ELProperty.create("${parent}"),
                parentPanel, BeanProperty.create("partySummary"));
        bindingGroup.addBinding(binding);
        bindingGroup.bind();
    }

    private void customizeParentChildPanel(boolean show) {
        parentCustomizePanel.setVisible(show);
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
            firePropertyChange("partyBean", null, this.partyBean);
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
            cmbVDCs.setSelectedIndex(-1);
            cmbIdOffice.setSelectedIndex(-1);
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
                }
            }
        });
        if (partyBean != null) {
            vdcListBean.loadList(false, partyBean.getAddress().getDistrictCode());
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
            individualButton.setSelected(true);
        } else {
            if (partyBean.getTypeCode() != null && partyBean.getTypeCode().equals(entityString)) {
                entityButton.setSelected(true);
                labName.setText(entityLabel);
                enableIndividualFields(false);
            } else {
                individualButton.setSelected(true);
                labName.setText(individualLabel);
                enableIndividualFields(true);
            }
        }

        if (readOnly) {
            individualButton.setEnabled(false);
            entityButton.setEnabled(false);
            txtFirstName.setEnabled(false);
            txtLastName.setEnabled(false);
            cmbIdType.setEnabled(false);
            cmbGender.setEnabled(false);
            cbxPartyRoleTypes.setEnabled(false);
            txtIdref.setEnabled(false);
            txtDateOfBirth.setEnabled(false);
            btnAddRole.setEnabled(false);
            btnRemoveRole.setEnabled(false);
            menuRemoveRole.setEnabled(false);
            // txtFatherFirstName.setEnabled(false);
            txtFatherName.setEnabled(false);
            txtAlias.setEnabled(false);
            txtPhone.setEnabled(false);
            txtEmail.setEnabled(false);
            txtMobile.setEnabled(false);
            //additional fields.
            cmbDistrict.setEnabled(false);
            cmbIdOffice.setEnabled(false);
            cmbVDCs.setEnabled(false);
            txtIssueDate.setEditable(false);
            txtIssueDate.setBackground(Color.LIGHT_GRAY);
            txtBirthDate.setEnabled(false);
            //txtGrandFatherFirstName.setEnabled(false);
            txtGrandFatherName.setEnabled(false);
            txtWardNo.setEnabled(false);
            txtRemarks.setEnabled(false);
            //image browsing buttons.
            btnBrowsePhoto.setEnabled(false);
            btnBrowseLeftFinger.setEnabled(false);
            btnBrowseRightFinger.setEnabled(false);
            btnBrowseSignature.setEnabled(false);
            btnClearImg.setEnabled(false);
            btnClearLFP.setEnabled(false);
            btnClearRFP.setEnabled(false);
            btnClearSignature.setEnabled(false);
        }
    }

    /**
     * Switch individual and entity type
     */
    private void switchPartyType(boolean isIndividual) {
        if (isIndividual) {
            partyBean.setTypeCode(individualString);
            labName.setText(individualLabel);
        } else {
            partyBean.setTypeCode(entityString);
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
        partyBean.setOfficeBean(null);
        partyBean.setGrandFatherName(null);
        partyBean.setPhotoDoc(null);
        partyBean.setLeftFingerDoc(null);
        partyBean.setRightFingerDoc(null);
        partyBean.setSignatureDoc(null);
    }

    private void enableIndividualFields(boolean enable) {
        txtLastName.setEnabled(enable);
        //txtFatherFirstName.setEnabled(enable);
        txtFatherName.setEnabled(enable);
        txtAlias.setEnabled(enable);
        cmbGender.setEnabled(enable);
        cmbIdType.setEnabled(enable);
        txtIdref.setEnabled(enable);
        //additional fields.
        cmbIdOffice.setEnabled(enable);
        txtIssueDate.setEditable(enable);
        if (enable) {
            txtIssueDate.setBackground(Color.WHITE);
        } else {
            txtIssueDate.setBackground(Color.LIGHT_GRAY);
        }
        //txtGrandFatherFirstName.setEnabled(enable);
        txtGrandFatherName.setEnabled(enable);
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
        buttonGroup1 = new javax.swing.ButtonGroup();
        popupRoles = new javax.swing.JPopupMenu();
        menuRemoveRole = new javax.swing.JMenuItem();
        genderTypes = createGenderTypes();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        officeListBean = new org.sola.clients.beans.referencedata.OfficeListBean();
        districtListBean = new org.sola.clients.beans.referencedata.DistrictListBean();
        vdcListBean = new org.sola.clients.beans.referencedata.VdcListBean();
        communicationTypeListBean = new org.sola.clients.beans.referencedata.CommunicationTypeListBean();
        grandFatherTypeListBean = new org.sola.clients.beans.referencedata.GrandFatherTypeListBean();
        idOfficeTypeListBean = new org.sola.clients.beans.referencedata.IdOfficeTypeListBean();
        districtListBean1 = new org.sola.clients.beans.referencedata.DistrictListBean();
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
        jPanel4 = new javax.swing.JPanel();
        labName = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        labLastName = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        lblGender = new javax.swing.JLabel();
        cmbGender = new javax.swing.JComboBox();
        jPanel27 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cmbDistrict = new javax.swing.JComboBox();
        jPanel21 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmbVDCs = new javax.swing.JComboBox();
        jPanel22 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtWardNo = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        labIdType = new javax.swing.JLabel();
        cmbIdType = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        labIdref = new javax.swing.JLabel();
        txtIdref = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtIssueDate = new javax.swing.JFormattedTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbIdOffice = new javax.swing.JComboBox();
        jPanel13 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cmbIdIssuingDistrict = new javax.swing.JComboBox();
        jPanel9 = new javax.swing.JPanel();
        labAddress = new javax.swing.JLabel();
        txtDateOfBirth = new javax.swing.JTextField();
        jPanel26 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtBirthDate = new javax.swing.JFormattedTextField();
        fullPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        labFatherFirstName = new javax.swing.JLabel();
        cmbFatherType = new javax.swing.JComboBox();
        jPanel17 = new javax.swing.JPanel();
        labFatherLastName = new javax.swing.JLabel();
        txtFatherName = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        labAlias = new javax.swing.JLabel();
        txtAlias = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cmbGrandFatherType = new javax.swing.JComboBox();
        jPanel24 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtGrandFatherName = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtRemarks = new javax.swing.JTextField();
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
        jPanel30 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        cmbPreferredCommunication = new javax.swing.JComboBox();
        jPanel14 = new javax.swing.JPanel();
        txtEmail = new javax.swing.JTextField();
        labEmail = new javax.swing.JLabel();
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
        jPanel34 = new javax.swing.JPanel();
        chkIsChild = new javax.swing.JCheckBox();
        entityButton = new javax.swing.JRadioButton();
        individualButton = new javax.swing.JRadioButton();

        buttonGroup1.add(individualButton);
        buttonGroup1.add(entityButton);

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
        tablePartyRole.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("PartyPanel.tablePartyRole.columnModel.title0_1")); // NOI18N

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
                .addComponent(roleTableScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
        );

        jPanel10.setName("jPanel10"); // NOI18N
        jPanel10.setLayout(new java.awt.GridLayout(0, 3, 15, 0));

        jPanel4.setName("jPanel4"); // NOI18N

        labName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        labName.setText(bundle1.getString("ApplicationForm.labName.text")); // NOI18N
        labName.setIconTextGap(1);
        labName.setName("labName"); // NOI18N

        txtFirstName.setDisabledTextColor(new java.awt.Color(240, 240, 240));
        txtFirstName.setName("txtFirstName"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.name}"), txtFirstName, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtFirstName.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        txtFirstName.setHorizontalAlignment(JTextField.LEADING);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtFirstName)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(labName)
                .addGap(0, 175, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(labName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel4);

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
            .addComponent(txtLastName, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(labLastName)
                .addGap(0, 187, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(labLastName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel5);

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
            .addComponent(cmbGender, 0, 237, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(lblGender)
                .addGap(0, 202, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(lblGender)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel8);

        jPanel27.setName(bundle1.getString("PartyPanel.jPanel27.name")); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel9.setText(bundle.getString("PartyPanel.jLabel9.text")); // NOI18N
        jLabel9.setName(bundle1.getString("PartyPanel.jLabel9.name")); // NOI18N

        cmbDistrict.setBackground(new java.awt.Color(226, 244, 224));
        cmbDistrict.setName(bundle1.getString("PartyPanel.cboDistrict.name")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${districts}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, districtListBean, eLProperty, cmbDistrict);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.address.districtBean}"), cmbDistrict, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
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
                .addGap(0, 160, Short.MAX_VALUE))
            .addComponent(cmbDistrict, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel27);

        jPanel21.setName(bundle1.getString("PartyPanel.jPanel21.name")); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel3.setText(bundle.getString("PartyPanel.jLabel3.text")); // NOI18N
        jLabel3.setName(bundle1.getString("PartyPanel.jLabel3.name")); // NOI18N

        cmbVDCs.setBackground(new java.awt.Color(226, 244, 224));
        cmbVDCs.setName(bundle1.getString("PartyPanel.cboVDCs.name")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${vdcs}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vdcListBean, eLProperty, cmbVDCs);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.address.vdcBean}"), cmbVDCs, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cmbVDCs, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 144, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbVDCs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel21);

        jPanel22.setName(bundle1.getString("PartyPanel.jPanel22.name")); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel4.setText(bundle.getString("PartyPanel.jLabel4.text")); // NOI18N
        jLabel4.setName(bundle1.getString("PartyPanel.jLabel4.name")); // NOI18N

        txtWardNo.setName(bundle1.getString("PartyPanel.txtWardNo.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.address.wardNo}"), txtWardNo, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtWardNo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 181, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtWardNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel22);

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
            .addComponent(cmbIdType, 0, 237, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(labIdType)
                .addGap(0, 200, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(labIdType)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbIdType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
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
                .addGap(0, 187, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(labIdref)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIdref, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel7);

        jPanel20.setName(bundle1.getString("PartyPanel.jPanel20.name")); // NOI18N

        jLabel2.setText(bundle.getString("PartyPanel.jLabel2.text")); // NOI18N
        jLabel2.setName(bundle1.getString("PartyPanel.jLabel2.name")); // NOI18N

        txtIssueDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        txtIssueDate.setText(bundle.getString("PartyPanel.txtIssueDate.text_1")); // NOI18N
        txtIssueDate.setName(bundle1.getString("PartyPanel.txtIssueDate.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.idIssueDate}"), txtIssueDate, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 172, Short.MAX_VALUE))
            .addComponent(txtIssueDate)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIssueDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel20);

        jPanel19.setName(bundle1.getString("PartyPanel.jPanel19.name")); // NOI18N

        jLabel1.setText(bundle.getString("PartyPanel.jLabel1.text")); // NOI18N
        jLabel1.setName(bundle1.getString("PartyPanel.jLabel1.name")); // NOI18N

        cmbIdOffice.setBackground(new java.awt.Color(226, 244, 224));
        cmbIdOffice.setMinimumSize(new java.awt.Dimension(24, 20));
        cmbIdOffice.setName(bundle1.getString("PartyPanel.cboOffice.name")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${idOfficeTypeList}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, idOfficeTypeListBean, eLProperty, cmbIdOffice);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.idOfficeType}"), cmbIdOffice, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 160, Short.MAX_VALUE))
            .addComponent(cmbIdOffice, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbIdOffice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel19);

        jPanel13.setName("jPanel13"); // NOI18N

        jLabel10.setText(bundle.getString("PartyPanel.jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        cmbIdIssuingDistrict.setName("cmbIdIssuingDistrict"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${districts}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, districtListBean1, eLProperty, cmbIdIssuingDistrict);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.idIssuingDistrict}"), cmbIdIssuingDistrict, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(0, 160, Short.MAX_VALUE))
            .addComponent(cmbIdIssuingDistrict, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbIdIssuingDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel13);

        jPanel9.setName("jPanel9"); // NOI18N

        labAddress.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        labAddress.setText(bundle.getString("PartyPanel.labAddress.text")); // NOI18N
        labAddress.setName("labAddress"); // NOI18N

        txtDateOfBirth.setName("txtDateOfBirth"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.address.description}"), txtDateOfBirth, org.jdesktop.beansbinding.BeanProperty.create("text"), "");
        bindingGroup.addBinding(binding);

        txtDateOfBirth.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        txtDateOfBirth.setHorizontalAlignment(JTextField.LEADING);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtDateOfBirth)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(labAddress)
                .addGap(0, 104, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(labAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel9);

        jPanel26.setName(bundle1.getString("PartyPanel.jPanel26.name")); // NOI18N

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel8.setText(bundle.getString("PartyPanel.jLabel8.text")); // NOI18N
        jLabel8.setName(bundle1.getString("PartyPanel.jLabel8.name")); // NOI18N

        txtBirthDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        txtBirthDate.setText(bundle.getString("PartyPanel.txtBirthDate.text")); // NOI18N
        txtBirthDate.setName("txtBirthDate"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.birthDate}"), txtBirthDate, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 162, Short.MAX_VALUE))
            .addComponent(txtBirthDate)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBirthDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel26);

        javax.swing.GroupLayout basicPanelLayout = new javax.swing.GroupLayout(basicPanel);
        basicPanel.setLayout(basicPanelLayout);
        basicPanelLayout.setHorizontalGroup(
            basicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, basicPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(basicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        basicPanelLayout.setVerticalGroup(
            basicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basicPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        detailsPanel.addTab(bundle.getString("PartyPanel.basicPanel.TabConstraints.tabTitle"), basicPanel); // NOI18N

        fullPanel.setName("fullPanel"); // NOI18N

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new java.awt.GridLayout(0, 3, 15, 0));

        jPanel2.setName("jPanel2"); // NOI18N

        labFatherFirstName.setText(bundle.getString("PartyPanel.labFatherFirstName.text")); // NOI18N
        labFatherFirstName.setName("labFatherFirstName"); // NOI18N

        cmbFatherType.setName("cmbFatherType"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${grandFatherTypeList}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, grandFatherTypeListBean, eLProperty, cmbFatherType);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.grandFatherType}"), cmbFatherType, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(labFatherFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
            .addComponent(cmbFatherType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(labFatherFirstName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmbFatherType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.add(jPanel2);

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
                .addContainerGap(210, Short.MAX_VALUE))
            .addComponent(txtFatherName, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(labFatherLastName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFatherName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel17);

        jPanel16.setName("jPanel16"); // NOI18N

        labAlias.setText(bundle.getString("PartyPanel.labAlias.text")); // NOI18N
        labAlias.setName("labAlias"); // NOI18N

        txtAlias.setName("txtAlias"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.alias}"), txtAlias, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(labAlias)
                .addContainerGap(215, Short.MAX_VALUE))
            .addComponent(txtAlias, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(labAlias)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAlias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel16);

        jPanel23.setName(bundle1.getString("PartyPanel.jPanel23.name")); // NOI18N

        jLabel5.setText(bundle.getString("PartyPanel.jLabel5.text")); // NOI18N
        jLabel5.setName(bundle1.getString("PartyPanel.jLabel5.name")); // NOI18N

        cmbGrandFatherType.setName("cmbGrandFatherType"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${grandFatherTypeList}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, grandFatherTypeListBean, eLProperty, cmbGrandFatherType);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.grandFatherType}"), cmbGrandFatherType, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 88, Short.MAX_VALUE))
            .addComponent(cmbGrandFatherType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmbGrandFatherType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.add(jPanel23);

        jPanel24.setName(bundle1.getString("PartyPanel.jPanel24.name")); // NOI18N

        jLabel6.setText(bundle.getString("PartyPanel.jLabel6.text")); // NOI18N
        jLabel6.setName(bundle1.getString("PartyPanel.jLabel6.name")); // NOI18N

        txtGrandFatherName.setName(bundle1.getString("PartyPanel.txtGrandFatherLastName.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.grandFatherName}"), txtGrandFatherName, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtGrandFatherName, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGrandFatherName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel24);

        jPanel25.setName(bundle1.getString("PartyPanel.jPanel25.name")); // NOI18N

        jLabel7.setText(bundle.getString("PartyPanel.jLabel7.text")); // NOI18N
        jLabel7.setName(bundle1.getString("PartyPanel.jLabel7.name")); // NOI18N

        txtRemarks.setName(bundle1.getString("PartyPanel.txtRemarks.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.remarks}"), txtRemarks, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 143, Short.MAX_VALUE))
            .addComponent(txtRemarks)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRemarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel25);

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
                .addGap(0, 104, Short.MAX_VALUE))
            .addComponent(txtPhone)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
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
                .addContainerGap(104, Short.MAX_VALUE))
            .addComponent(txtMobile, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(labMobile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
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
                .addGap(0, 116, Short.MAX_VALUE))
            .addComponent(txtFax)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );

        jPanel18.add(jPanel15);

        jPanel30.setName("jPanel30"); // NOI18N

        jLabel12.setText(bundle.getString("PartyPanel.jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        cmbPreferredCommunication.setName("cmbPreferredCommunication"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${communicationTypeList}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, communicationTypeListBean, eLProperty, cmbPreferredCommunication);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.preferredCommunication}"), cmbPreferredCommunication, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addGap(0, 13, Short.MAX_VALUE))
            .addComponent(cmbPreferredCommunication, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbPreferredCommunication, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );

        jPanel18.add(jPanel30);

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
                .addContainerGap(106, Short.MAX_VALUE))
            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(labEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel18.add(jPanel14);

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

        lblPhoto.setText(bundle.getString("PartyPanel.lblPhoto.text")); // NOI18N
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

        lblRightFinger.setText(bundle.getString("PartyPanel.lblRightFinger.text")); // NOI18N
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

        lblSignature.setText(bundle.getString("PartyPanel.lblSignature.text")); // NOI18N
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

        lblLeftFinger.setText(bundle.getString("PartyPanel.lblLeftFinger.text")); // NOI18N
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
            .addComponent(groupPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        parentCustomizePanelLayout.setVerticalGroup(
            parentCustomizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parentCustomizePanelLayout.createSequentialGroup()
                .addComponent(groupPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel34.setName("jPanel34"); // NOI18N

        chkIsChild.setText(bundle.getString("PartyPanel.chkIsChild.text")); // NOI18N
        chkIsChild.setName("chkIsChild"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${partyBean.child}"), chkIsChild, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        chkIsChild.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkIsChildItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chkIsChild, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addComponent(chkIsChild)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout fullPanelLayout = new javax.swing.GroupLayout(fullPanel);
        fullPanel.setLayout(fullPanelLayout);
        fullPanelLayout.setHorizontalGroup(
            fullPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fullPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fullPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fullPanelLayout.createSequentialGroup()
                        .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fullPanelLayout.createSequentialGroup()
                        .addGroup(fullPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(parentCustomizePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(groupPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(groupPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, fullPanelLayout.createSequentialGroup()
                                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        fullPanelLayout.setVerticalGroup(
            fullPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fullPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groupPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(parentCustomizePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(groupPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        detailsPanel.addTab(bundle.getString("PartyPanel.fullPanel.TabConstraints.tabTitle"), fullPanel); // NOI18N

        buttonGroup1.add(entityButton);
        entityButton.setText(bundle.getString("PartyPanel.entityButton.text")); // NOI18N
        entityButton.setActionCommand(bundle.getString("PartyPanel.entityButton.actionCommand")); // NOI18N
        entityButton.setName("entityButton"); // NOI18N
        entityButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entityButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(individualButton);
        individualButton.setText(bundle.getString("PartyPanel.individualButton.text")); // NOI18N
        individualButton.setActionCommand(bundle.getString("PartyPanel.individualButton.actionCommand")); // NOI18N
        individualButton.setName("individualButton"); // NOI18N
        individualButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                individualButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(individualButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityButton)
                .addContainerGap())
            .addComponent(detailsPanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(individualButton)
                    .addComponent(entityButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(detailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void individualButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_individualButtonActionPerformed
        if (individualButton.isSelected()) {
            switchPartyType(true);
        }
    }//GEN-LAST:event_individualButtonActionPerformed

    private void entityButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entityButtonActionPerformed
        if (entityButton.isSelected()) {
            switchPartyType(false);
        }
    }//GEN-LAST:event_entityButtonActionPerformed

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

    private void chkIsChildItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkIsChildItemStateChanged
        // TODO add your handling code here:
        customizeParentChildPanel(chkIsChild.isSelected());
    }//GEN-LAST:event_chkIsChildItemStateChanged

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
                cmbVDCs.setSelectedIndex(-1);
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
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbxPartyRoleTypes;
    private javax.swing.JCheckBox chkIsChild;
    private javax.swing.JComboBox cmbDistrict;
    private javax.swing.JComboBox cmbFatherType;
    public javax.swing.JComboBox cmbGender;
    private javax.swing.JComboBox cmbGrandFatherType;
    private javax.swing.JComboBox cmbIdIssuingDistrict;
    private javax.swing.JComboBox cmbIdOffice;
    public javax.swing.JComboBox cmbIdType;
    private javax.swing.JComboBox cmbPreferredCommunication;
    private javax.swing.JComboBox cmbVDCs;
    private org.sola.clients.beans.referencedata.CommunicationTypeListBean communicationTypeListBean;
    private javax.swing.JTabbedPane detailsPanel;
    private org.sola.clients.beans.referencedata.DistrictListBean districtListBean;
    private org.sola.clients.beans.referencedata.DistrictListBean districtListBean1;
    private javax.swing.JRadioButton entityButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JPanel fullPanel;
    private org.sola.clients.beans.referencedata.GenderTypeListBean genderTypes;
    private org.sola.clients.beans.referencedata.GrandFatherTypeListBean grandFatherTypeListBean;
    private org.sola.clients.swing.ui.GroupPanel groupPanel1;
    private org.sola.clients.swing.ui.GroupPanel groupPanel2;
    private org.sola.clients.swing.ui.GroupPanel groupPanel3;
    private org.sola.clients.swing.ui.GroupPanel groupPanel4;
    private org.sola.clients.beans.referencedata.IdOfficeTypeListBean idOfficeTypeListBean;
    private org.sola.clients.beans.referencedata.IdTypeListBean idTypes;
    private javax.swing.JRadioButton individualButton;
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
    private javax.swing.JPanel jPanel34;
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
    private javax.swing.JLabel labAlias;
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
    private org.sola.clients.beans.referencedata.OfficeListBean officeListBean;
    private javax.swing.JPanel parentCustomizePanel;
    private org.sola.clients.swing.desktop.party.PartySelectExtPanel parentPanel;
    private org.sola.clients.beans.referencedata.PartyRoleTypeListBean partyRoleTypes;
    private javax.swing.JPopupMenu popupRoles;
    private javax.swing.JPanel rightFinger;
    private javax.swing.JScrollPane roleTableScrollPanel;
    private javax.swing.JPanel signature;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tablePartyRole;
    private javax.swing.JTextField txtAlias;
    private javax.swing.JFormattedTextField txtBirthDate;
    public javax.swing.JTextField txtDateOfBirth;
    public javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFatherName;
    private javax.swing.JTextField txtFax;
    public javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtGrandFatherName;
    private javax.swing.JTextField txtIdref;
    private javax.swing.JFormattedTextField txtIssueDate;
    public javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtMobile;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtRemarks;
    private javax.swing.JTextField txtWardNo;
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
