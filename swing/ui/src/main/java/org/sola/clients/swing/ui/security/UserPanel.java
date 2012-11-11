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
package org.sola.clients.swing.ui.security;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.sola.clients.beans.referencedata.DepartmentBean;
import org.sola.clients.beans.referencedata.VdcBean;
import org.sola.clients.beans.security.SecurityBean;
import org.sola.clients.beans.security.UserBean;
import org.sola.clients.beans.security.UserVdcBean;
import org.sola.clients.swing.common.controls.BrowseControlListener;
import org.sola.clients.swing.common.utils.Utils;
import org.sola.clients.swing.ui.renderers.TableCellTextAreaRenderer;
import org.sola.common.FrameUtility;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Displays {@link UserBean} information and allows to edit it.
 */
public class UserPanel extends javax.swing.JPanel {

    private UserBean user;

    /**
     * Default constructor.
     */
    public UserPanel() {
        this(null);
    }

    /**
     * Constructs panel with predefined {@link UserBean} instance.
     *
     * @param user {@link UserBean} instance to bind on the form.
     */
    public UserPanel(UserBean user) {
        setupUserBean(user);
        initComponents();
        browseDepartments.addBrowseControlEventListener(new BrowseControlListener() {

            @Override
            public void deleteButtonClicked(MouseEvent e) {
                clearDepartment();
            }

            @Override
            public void browseButtonClicked(MouseEvent e) {
                showDepartmentSelectionForm();
            }

            @Override
            public void controlClicked(MouseEvent e) {
            }

            @Override
            public void textClicked(MouseEvent e) {
            }
        });
        postInit();
    }

    /**
     * Setup {@link UserBean} object, used to bind data on the form.
     */
    private void setupUserBean(UserBean user) {
        clearDepartment();
        if (user != null) {
            this.user = user;
        } else {
            this.user = new UserBean();
        }
        this.user.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getPropertyName().equals(UserBean.SELECTED_VDC_PROPERTY)){
                    customizeVdcButtons();
                }
            }
        });
        firePropertyChange("user", null, this.user);
    }

    private void customizeVdcButtons(){
        boolean enabled = user.getSelectedVdc()!=null;
        btnRemoveVdc.setEnabled(enabled);
        menuRemoveVdc.setEnabled(enabled);
    }
    
    private void clearDepartment() {
        if (browseDepartments != null) {
            browseDepartments.setText("");
            getUser().setDepartment(null);
        }
    }

    private void loadDepartmentsList() {
        departments.loadList(false);
    }

    /**
     * Runs post initialization tasks.
     */
    private void postInit() {
        userGroupHelperList.setUserGroups(this.user.getUserGroups());
        pnlPassword.setVisible(getUser().isNew());
        passwordBean.setPassword(null);
        passwordBean.setPasswordConfirmation(null);
        txtUserName.setEnabled(getUser().isNew());
        customizeVdcButtons();
    }

    private void showDepartmentSelectionForm() {
        final DepartmentSelectionForm form = new DepartmentSelectionForm(null, true);
        form.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(DepartmentSelectionForm.DEPARTMENT_SELECTED_PROPERTY)) {
                    user.setDepartment((DepartmentBean) evt.getNewValue());
                    form.setVisible(false);
                }
            }
        });
        form.setVisible(true);
    }

    /**
     * Returns {@link UserBean} object, bound on the form.
     */
    public UserBean getUser() {
        return user;
    }

    /**
     * Sets {@link UserBean} object to bind on the form.
     */
    public void setUser(UserBean user) {
        setupUserBean(user);
        postInit();
    }

    /**
     * Disables or enables panel components.
     */
    public void lockForm(boolean isLocked) {
        txtDescription.setEnabled(!isLocked);
        txtFirstName.setEnabled(!isLocked);
        txtLastName.setEnabled(!isLocked);
        txtPassword.setEnabled(!isLocked);
        txtPasswordConfirmation.setEnabled(!isLocked);
    }

    /**
     * Validates user.
     */
    public boolean validateUser(boolean showMessage) {
        if (user.validate(showMessage).size() < 1 && (!pnlPassword.isVisible()
                || (pnlPassword.isVisible() && passwordBean.validate(showMessage).size() < 1))) {
            if (user.getUserName().equals(SecurityBean.getCurrentUser().getUserName())
                    && !user.isActive()) {
                if (showMessage) {
                    MessageUtility.displayMessage(ClientMessage.ADMIN_CURRENT_USER_DISABLE_ERROR);
                }
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Saves user.
     */
    public boolean saveUser(boolean showMessage) {
        if (validateUser(showMessage)) {
            user.save();
            if (pnlPassword.isVisible()) {
                user.changePassword(passwordBean.getPassword());
            }
            userGroupHelperList.setChecks(user.getUserGroups());
            return true;
        }
        return false;
    }
    
    private void removeVdc(){
        user.removeSelectedVdc();
    }

    private void showAddVdcForm(){
        UserVdcForm form = new UserVdcForm(null, true);
        form.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getPropertyName().equals(UserVdcForm.VDC_PROPERTY)){
                    user.addVdc((UserVdcBean)evt.getNewValue());
                }
            }
        });
        Utils.positionFormCentrally(form);
        form.setVisible(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        passwordBean = new org.sola.clients.beans.security.PasswordBean();
        userGroupHelperList = new org.sola.clients.beans.security.UserGroupHelperListBean();
        departments = new org.sola.clients.beans.referencedata.DepartmentListBean();
        loadDepartmentsList();
        menuVdcs = new javax.swing.JPopupMenu();
        menuAddVdc = new javax.swing.JMenuItem();
        menuRemoveVdc = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        cbxActive = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        jPanel9 = new javax.swing.JPanel();
        pnlPassword = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtPasswordConfirmation = new javax.swing.JPasswordField();
        pnlGroups = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        browseDepartments = new org.sola.clients.swing.common.controls.BrowseControl();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        groupPanel1 = new org.sola.clients.swing.ui.GroupPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableGroups = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        jPanel10 = new javax.swing.JPanel();
        groupPanel2 = new org.sola.clients.swing.ui.GroupPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableVdcs = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        jToolBar1 = new javax.swing.JToolBar();
        btnAddVdc = new javax.swing.JButton();
        btnRemoveVdc = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/ui/security/Bundle"); // NOI18N
        menuVdcs.setName(bundle.getString("UserPanel.menuVdcs.name")); // NOI18N

        menuAddVdc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        menuAddVdc.setText(bundle.getString("UserPanel.menuAddVdc.text")); // NOI18N
        menuAddVdc.setName(bundle.getString("UserPanel.menuAddVdc.name")); // NOI18N
        menuAddVdc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddVdcActionPerformed(evt);
            }
        });
        menuVdcs.add(menuAddVdc);

        menuRemoveVdc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        menuRemoveVdc.setText(bundle.getString("UserPanel.menuRemoveVdc.text")); // NOI18N
        menuRemoveVdc.setName(bundle.getString("UserPanel.menuRemoveVdc.name")); // NOI18N
        menuRemoveVdc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRemoveVdcActionPerformed(evt);
            }
        });
        menuVdcs.add(menuRemoveVdc);

        setAlignmentX(0.0F);
        setAlignmentY(0.0F);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jPanel5.setName("jPanel5"); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel1.setText(bundle.getString("UserPanel.jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        txtFirstName.setName("txtFirstName"); // NOI18N
        txtFirstName.setNextFocusableComponent(txtLastName);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${user.firstName}"), txtFirstName, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel5.setText(bundle.getString("UserPanel.jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        txtUserName.setName("txtUserName"); // NOI18N
        txtUserName.setNextFocusableComponent(cbxActive);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${user.userName}"), txtUserName, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        cbxActive.setText(bundle.getString("UserPanel.cbxActive.text")); // NOI18N
        cbxActive.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cbxActive.setName("cbxActive"); // NOI18N
        cbxActive.setNextFocusableComponent(txtDescription);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${user.active}"), cbxActive, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                    .addComponent(cbxActive, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                    .addComponent(jLabel5))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxActive)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5);

        jPanel4.setName("jPanel4"); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel2.setText(bundle.getString("UserPanel.jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        txtLastName.setName("txtLastName"); // NOI18N
        txtLastName.setNextFocusableComponent(txtUserName);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${user.lastName}"), txtLastName, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel4.setText(bundle.getString("UserPanel.jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        txtDescription.setColumns(20);
        txtDescription.setLineWrap(true);
        txtDescription.setRows(2);
        txtDescription.setName("txtDescription"); // NOI18N
        txtDescription.setNextFocusableComponent(txtPassword);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${user.description}"), txtDescription, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(txtDescription);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtLastName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                .addGap(11, 11, 11))
        );

        jPanel1.add(jPanel4);

        jPanel9.setName("jPanel9"); // NOI18N
        jPanel9.setLayout(new java.awt.BorderLayout());

        pnlPassword.setName("pnlPassword"); // NOI18N

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jPanel7.setName("jPanel7"); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel7.setLabelFor(txtPassword);
        jLabel7.setText(bundle.getString("UserPanel.jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        txtPassword.setName("txtPassword"); // NOI18N
        txtPassword.setNextFocusableComponent(txtPasswordConfirmation);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, passwordBean, org.jdesktop.beansbinding.ELProperty.create("${password}"), txtPassword, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                    .addComponent(jLabel7))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel7);

        jPanel8.setName("jPanel8"); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel6.setLabelFor(txtPasswordConfirmation);
        jLabel6.setText(bundle.getString("UserPanel.jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        txtPasswordConfirmation.setName("txtPasswordConfirmation"); // NOI18N
        txtPasswordConfirmation.setNextFocusableComponent(tableGroups);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, passwordBean, org.jdesktop.beansbinding.ELProperty.create("${passwordConfirmation}"), txtPasswordConfirmation, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPasswordConfirmation, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(51, 51, 51)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPasswordConfirmation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel8);

        javax.swing.GroupLayout pnlPasswordLayout = new javax.swing.GroupLayout(pnlPassword);
        pnlPassword.setLayout(pnlPasswordLayout);
        pnlPasswordLayout.setHorizontalGroup(
            pnlPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 617, Short.MAX_VALUE)
            .addGroup(pnlPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE))
        );
        pnlPasswordLayout.setVerticalGroup(
            pnlPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 61, Short.MAX_VALUE)
            .addGroup(pnlPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlPasswordLayout.createSequentialGroup()
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel9.add(pnlPassword, java.awt.BorderLayout.PAGE_START);

        pnlGroups.setName("pnlGroups"); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel3.setText(bundle.getString("UserPanel.jLabel3.text")); // NOI18N
        jLabel3.setName(bundle.getString("UserPanel.jLabel3.name")); // NOI18N

        browseDepartments.setColor(new java.awt.Color(0, 0, 0));
        browseDepartments.setDisplayDeleteButton(false);
        browseDepartments.setName(bundle.getString("UserPanel.browseDepartments.name")); // NOI18N
        browseDepartments.setUnderline(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${user.department.departmentAndOfficeName}"), browseDepartments, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jPanel6.setName(bundle.getString("UserPanel.jPanel6.name")); // NOI18N
        jPanel6.setLayout(new java.awt.GridLayout(1, 0, 15, 0));

        jPanel3.setName(bundle.getString("UserPanel.jPanel3.name")); // NOI18N

        groupPanel1.setName("groupPanel1"); // NOI18N
        groupPanel1.setTitleText(bundle.getString("UserPanel.groupPanel1.titleText")); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tableGroups.setAlignmentX(0.0F);
        tableGroups.setAlignmentY(0.0F);
        tableGroups.setName("tableGroups"); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${userGroupHelpers}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, userGroupHelperList, eLProperty, tableGroups);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${inUserGroups}"));
        columnBinding.setColumnName("In User Groups");
        columnBinding.setColumnClass(Boolean.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${groupSummary.name}"));
        columnBinding.setColumnName("Group Summary.name");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${groupSummary.description}"));
        columnBinding.setColumnName("Group Summary.description");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane2.setViewportView(tableGroups);
        tableGroups.getColumnModel().getColumn(0).setMaxWidth(30);
        tableGroups.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("UserPanel.tableGroups.columnModel.title0_1")); // NOI18N
        tableGroups.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableGroups.getColumnModel().getColumn(1).setMaxWidth(300);
        tableGroups.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("UserPanel.tableGroups.columnModel.title1_1")); // NOI18N
        tableGroups.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("UserPanel.tableGroups.columnModel.title2_1")); // NOI18N
        tableGroups.getColumnModel().getColumn(2).setCellRenderer(new TableCellTextAreaRenderer());

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(groupPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(groupPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel3);

        jPanel10.setName(bundle.getString("UserPanel.jPanel10.name")); // NOI18N

        groupPanel2.setName(bundle.getString("UserPanel.groupPanel2.name")); // NOI18N
        groupPanel2.setTitleText(bundle.getString("UserPanel.groupPanel2.titleText")); // NOI18N

        jScrollPane3.setName(bundle.getString("UserPanel.jScrollPane3.name")); // NOI18N

        tableVdcs.setComponentPopupMenu(menuVdcs);
        tableVdcs.setName(bundle.getString("UserPanel.tableVdcs.name")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${user.filteredVdcs}");
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, tableVdcs);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${vdc.displayValue}"));
        columnBinding.setColumnName("Vdc.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${wardNumber}"));
        columnBinding.setColumnName("Ward Number");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${user.selectedVdc}"), tableVdcs, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane3.setViewportView(tableVdcs);
        tableVdcs.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("UserPanel.tableVdcs.columnModel.title0_1")); // NOI18N
        tableVdcs.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("UserPanel.tableVdcs.columnModel.title1_1")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName(bundle.getString("UserPanel.jToolBar1.name")); // NOI18N

        btnAddVdc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        btnAddVdc.setText(bundle.getString("UserPanel.btnAddVdc.text")); // NOI18N
        btnAddVdc.setFocusable(false);
        btnAddVdc.setName(bundle.getString("UserPanel.btnAddVdc.name")); // NOI18N
        btnAddVdc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddVdcActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAddVdc);

        btnRemoveVdc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemoveVdc.setText(bundle.getString("UserPanel.btnRemoveVdc.text")); // NOI18N
        btnRemoveVdc.setFocusable(false);
        btnRemoveVdc.setName(bundle.getString("UserPanel.btnRemoveVdc.name")); // NOI18N
        btnRemoveVdc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveVdcActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRemoveVdc);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(groupPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(groupPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel10);

        javax.swing.GroupLayout pnlGroupsLayout = new javax.swing.GroupLayout(pnlGroups);
        pnlGroups.setLayout(pnlGroupsLayout);
        pnlGroupsLayout.setHorizontalGroup(
            pnlGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGroupsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlGroupsLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(browseDepartments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlGroupsLayout.setVerticalGroup(
            pnlGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGroupsLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(browseDepartments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jPanel9.add(pnlGroups, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void menuRemoveVdcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRemoveVdcActionPerformed
        removeVdc();
    }//GEN-LAST:event_menuRemoveVdcActionPerformed

    private void btnRemoveVdcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveVdcActionPerformed
        removeVdc();
    }//GEN-LAST:event_btnRemoveVdcActionPerformed

    private void btnAddVdcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddVdcActionPerformed
        showAddVdcForm();
    }//GEN-LAST:event_btnAddVdcActionPerformed

    private void menuAddVdcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAddVdcActionPerformed
        showAddVdcForm();
    }//GEN-LAST:event_menuAddVdcActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sola.clients.swing.common.controls.BrowseControl browseDepartments;
    private javax.swing.JButton btnAddVdc;
    private javax.swing.JButton btnRemoveVdc;
    private javax.swing.JCheckBox cbxActive;
    private org.sola.clients.beans.referencedata.DepartmentListBean departments;
    private org.sola.clients.swing.ui.GroupPanel groupPanel1;
    private org.sola.clients.swing.ui.GroupPanel groupPanel2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem menuAddVdc;
    private javax.swing.JMenuItem menuRemoveVdc;
    private javax.swing.JPopupMenu menuVdcs;
    private org.sola.clients.beans.security.PasswordBean passwordBean;
    private javax.swing.JPanel pnlGroups;
    private javax.swing.JPanel pnlPassword;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableGroups;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableVdcs;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtPasswordConfirmation;
    private javax.swing.JTextField txtUserName;
    private org.sola.clients.beans.security.UserGroupHelperListBean userGroupHelperList;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
