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
package org.sola.clients.swing.admin;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperPrint;
import org.sola.clients.beans.AbstractCodeBean;
import org.sola.clients.beans.referencedata.*;
import org.sola.clients.beans.security.SecurityBean;
import org.sola.clients.reports.ReportManager;
import org.sola.clients.swing.admin.referencedata.*;
import org.sola.clients.swing.admin.security.GroupsManagementPanel;
import org.sola.clients.swing.admin.security.RolesManagementPanel;
import org.sola.clients.swing.admin.security.UsersManagementPanel;
import org.sola.clients.swing.admin.system.BrManagementPanel;
import org.sola.clients.swing.admin.system.MapSheetNoManagementPanel;
import org.sola.clients.swing.common.LafManager;
import org.sola.clients.swing.ui.MainContentPanel;
import org.sola.common.RolesConstants;

/**
 * Main form of the Admin application.
 */
public class MainForm extends javax.swing.JFrame {

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();

        URL imgURL = this.getClass().getResource("/images/common/admin.png");
        this.setIconImage(new ImageIcon(imgURL).getImage());
        lblUserName.setText(SecurityBean.getCurrentUser().getUserName());
        customizeForm();
    }

    /**
     * Customizes main form regarding user access rights.
     */
    private void customizeForm() {
        boolean hasSecurityRole = SecurityBean.isInRole(RolesConstants.ADMIN_MANAGE_SECURITY);
        boolean hasRefdataRole = SecurityBean.isInRole(RolesConstants.ADMIN_MANAGE_REFDATA);
        boolean hasSettingsRole = SecurityBean.isInRole(RolesConstants.ADMIN_MANAGE_SETTINGS);
        boolean hasBRRole = SecurityBean.isInRole(RolesConstants.ADMIN_MANAGE_BR);

        btnRoles.setEnabled(hasSecurityRole);
        btnUsers.setEnabled(hasSecurityRole);
        btnGroups.setEnabled(hasSecurityRole);
        menuRoles.setEnabled(btnRoles.isEnabled());
        menuUsers.setEnabled(btnUsers.isEnabled());
        menuGroups.setEnabled(btnGroups.isEnabled());
        btnBr.setEnabled(hasBRRole);

        menuRefData.setEnabled(hasRefdataRole);
    }

    /**
     * Opens reference data management panel for different reference data type.
     */
    private <T extends AbstractCodeBean> void openReferenceDataPanel(
            Class<T> refDataClass, String headerTitle) {
        ReferenceDataManagementPanel panel = new ReferenceDataManagementPanel(refDataClass, headerTitle);
        mainContentPanel.addPanel(panel, MainContentPanel.CARD_ADMIN_REFDATA_MANAGE, true);
    }

    private void showOfficesForm() {
        OfficeManagementPanel form = new OfficeManagementPanel();
        mainContentPanel.addPanel(form, MainContentPanel.CARD_OFFICES, true);
    }

    private void showDepartmentsForm() {
        DepartmentManagementPanel form = new DepartmentManagementPanel();
        mainContentPanel.addPanel(form, MainContentPanel.CARD_DEPARTMENTS, true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainToolbar = new javax.swing.JToolBar();
        btnRoles = new javax.swing.JButton();
        btnGroups = new javax.swing.JButton();
        btnUsers = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnBr = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnCalendar = new javax.swing.JButton();
        statusPanel = new javax.swing.JPanel();
        taskPanel1 = new org.sola.clients.swing.common.tasks.TaskPanel();
        jLabel1 = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        mainContentPanel = new org.sola.clients.swing.ui.MainContentPanel();
        mainMenu = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuExit = new javax.swing.JMenuItem();
        menuSecurity = new javax.swing.JMenu();
        menuRoles = new javax.swing.JMenuItem();
        menuGroups = new javax.swing.JMenuItem();
        menuUsers = new javax.swing.JMenuItem();
        menuRefData = new javax.swing.JMenu();
        menuApplications = new javax.swing.JMenu();
        menuRequestCategory = new javax.swing.JMenuItem();
        menuRequestTypes = new javax.swing.JMenuItem();
        menuTypeActions = new javax.swing.JMenuItem();
        menuServiceActionTypes = new javax.swing.JMenuItem();
        menuServiceStatusTypes = new javax.swing.JMenuItem();
        menuAdministrative = new javax.swing.JMenu();
        menuBaUnitType = new javax.swing.JMenuItem();
        menuBaUnitRelationTypes = new javax.swing.JMenuItem();
        menuMortgageTypes = new javax.swing.JMenuItem();
        menuRrrGroupTypes = new javax.swing.JMenuItem();
        menuRrrTypes = new javax.swing.JMenuItem();
        menuSources = new javax.swing.JMenu();
        menuSourceTypes = new javax.swing.JMenuItem();
        menuParty = new javax.swing.JMenu();
        menuCommunicationType = new javax.swing.JMenuItem();
        menuIdTypes = new javax.swing.JMenuItem();
        menuGenders = new javax.swing.JMenuItem();
        menuPartyRoleType = new javax.swing.JMenuItem();
        menuPartyType = new javax.swing.JMenuItem();
        menuSystem = new javax.swing.JMenu();
        menuBRSeverityType = new javax.swing.JMenuItem();
        menuBRValidationTargetType = new javax.swing.JMenuItem();
        menuBRTechnicalType = new javax.swing.JMenuItem();
        menuTransaction = new javax.swing.JMenu();
        menuRegistrationStatusType = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenu1 = new javax.swing.JMenu();
        menuVDCSetup = new javax.swing.JMenuItem();
        menuDistrict = new javax.swing.JMenuItem();
        menuOffices = new javax.swing.JMenuItem();
        menuDepartments = new javax.swing.JMenuItem();
        menuMapsheet = new javax.swing.JMenuItem();
        menuRestrictionRelease = new javax.swing.JMenu();
        menuRestrictionType = new javax.swing.JMenuItem();
        menuRestrictionReason = new javax.swing.JMenuItem();
        menuRestrictionReleaseReason = new javax.swing.JMenuItem();
        menuRestrictionOffice = new javax.swing.JMenuItem();
        menuOwnerTypes = new javax.swing.JMenuItem();
        menuShareTypes = new javax.swing.JMenuItem();
        menuTenantTypes = new javax.swing.JMenuItem();
        menuParcelTypes = new javax.swing.JMenuItem();
        menuLandClass = new javax.swing.JMenuItem();
        menuLandUses = new javax.swing.JMenuItem();
        menuGuthiNames = new javax.swing.JMenuItem();
        menuReports = new javax.swing.JMenu();
        menuLodgementReport = new javax.swing.JMenuItem();
        menuTimeReport = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/admin/Bundle"); // NOI18N
        setTitle(bundle.getString("MainForm.title")); // NOI18N

        mainToolbar.setFloatable(false);
        mainToolbar.setRollover(true);
        mainToolbar.setName("mainToolbar"); // NOI18N

        btnRoles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/roles.png"))); // NOI18N
        btnRoles.setText(bundle.getString("MainForm.btnRoles.text")); // NOI18N
        btnRoles.setFocusable(false);
        btnRoles.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRoles.setName("btnRoles"); // NOI18N
        btnRoles.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRoles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRolesActionPerformed(evt);
            }
        });
        mainToolbar.add(btnRoles);

        btnGroups.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/group.png"))); // NOI18N
        btnGroups.setText(bundle.getString("MainForm.btnGroups.text")); // NOI18N
        btnGroups.setFocusable(false);
        btnGroups.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGroups.setName("btnGroups"); // NOI18N
        btnGroups.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGroups.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGroupsActionPerformed(evt);
            }
        });
        mainToolbar.add(btnGroups);

        btnUsers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/user.png"))); // NOI18N
        btnUsers.setText(bundle.getString("MainForm.btnUsers.text")); // NOI18N
        btnUsers.setFocusable(false);
        btnUsers.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnUsers.setName("btnUsers"); // NOI18N
        btnUsers.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsersActionPerformed(evt);
            }
        });
        mainToolbar.add(btnUsers);

        jSeparator1.setName("jSeparator1"); // NOI18N
        mainToolbar.add(jSeparator1);

        btnBr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/traffic-light.png"))); // NOI18N
        btnBr.setText(bundle.getString("MainForm.btnBr.text")); // NOI18N
        btnBr.setFocusable(false);
        btnBr.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBr.setName("btnBr"); // NOI18N
        btnBr.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrActionPerformed(evt);
            }
        });
        mainToolbar.add(btnBr);

        jSeparator4.setName(bundle.getString("MainForm.jSeparator4.name")); // NOI18N
        mainToolbar.add(jSeparator4);

        btnCalendar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/calendar.png"))); // NOI18N
        btnCalendar.setText(bundle.getString("MainForm.btnCalendar.text")); // NOI18N
        btnCalendar.setFocusable(false);
        btnCalendar.setName(bundle.getString("MainForm.btnCalendar.name")); // NOI18N
        btnCalendar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCalendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalendarActionPerformed(evt);
            }
        });
        mainToolbar.add(btnCalendar);

        statusPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        statusPanel.setName("statusPanel"); // NOI18N

        taskPanel1.setName("taskPanel1"); // NOI18N

        jLabel1.setFont(LafManager.getInstance().getLabFontBold());
        jLabel1.setText(bundle.getString("MainForm.jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        lblUserName.setText(bundle.getString("MainForm.lblUserName.text")); // NOI18N
        lblUserName.setName("lblUserName"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(taskPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(lblUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(taskPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainContentPanel.setName("mainContentPanel"); // NOI18N

        mainMenu.setName("mainMenu"); // NOI18N

        menuFile.setText(bundle.getString("MainForm.menuFile.text")); // NOI18N
        menuFile.setName("menuFile"); // NOI18N

        menuExit.setText(bundle.getString("MainForm.menuExit.text")); // NOI18N
        menuExit.setName("menuExit"); // NOI18N
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        menuFile.add(menuExit);

        mainMenu.add(menuFile);

        menuSecurity.setText(bundle.getString("MainForm.menuSecurity.text")); // NOI18N
        menuSecurity.setName("menuSecurity"); // NOI18N
        menuSecurity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSecurityActionPerformed(evt);
            }
        });

        menuRoles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/roles.png"))); // NOI18N
        menuRoles.setText(bundle.getString("MainForm.menuRoles.text")); // NOI18N
        menuRoles.setName("menuRoles"); // NOI18N
        menuRoles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRolesActionPerformed(evt);
            }
        });
        menuSecurity.add(menuRoles);

        menuGroups.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/group.png"))); // NOI18N
        menuGroups.setText(bundle.getString("MainForm.menuGroups.text")); // NOI18N
        menuGroups.setName("menuGroups"); // NOI18N
        menuGroups.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGroupsActionPerformed(evt);
            }
        });
        menuSecurity.add(menuGroups);

        menuUsers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/user.png"))); // NOI18N
        menuUsers.setText(bundle.getString("MainForm.menuUsers.text")); // NOI18N
        menuUsers.setName("menuUsers"); // NOI18N
        menuSecurity.add(menuUsers);

        mainMenu.add(menuSecurity);

        menuRefData.setText(bundle.getString("MainForm.menuRefData.text")); // NOI18N
        menuRefData.setName("menuRefData"); // NOI18N

        menuApplications.setText(bundle.getString("MainForm.menuApplications.text")); // NOI18N
        menuApplications.setName("menuApplications"); // NOI18N

        menuRequestCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuRequestCategory.setText(bundle.getString("MainForm.menuRequestCategory.text")); // NOI18N
        menuRequestCategory.setName("menuRequestCategory"); // NOI18N
        menuRequestCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRequestCategoryActionPerformed(evt);
            }
        });
        menuApplications.add(menuRequestCategory);

        menuRequestTypes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuRequestTypes.setText(bundle.getString("MainForm.menuRequestTypes.text")); // NOI18N
        menuRequestTypes.setName("menuRequestTypes"); // NOI18N
        menuRequestTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRequestTypesActionPerformed(evt);
            }
        });
        menuApplications.add(menuRequestTypes);

        menuTypeActions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuTypeActions.setText(bundle.getString("MainForm.menuTypeActions.text")); // NOI18N
        menuTypeActions.setName("menuTypeActions"); // NOI18N
        menuTypeActions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTypeActionsActionPerformed(evt);
            }
        });
        menuApplications.add(menuTypeActions);

        menuServiceActionTypes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuServiceActionTypes.setText(bundle.getString("MainForm.menuServiceActionTypes.text")); // NOI18N
        menuServiceActionTypes.setName("menuServiceActionTypes"); // NOI18N
        menuServiceActionTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuServiceActionTypesActionPerformed(evt);
            }
        });
        menuApplications.add(menuServiceActionTypes);

        menuServiceStatusTypes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuServiceStatusTypes.setText(bundle.getString("MainForm.menuServiceStatusTypes.text")); // NOI18N
        menuServiceStatusTypes.setName("menuServiceStatusTypes"); // NOI18N
        menuServiceStatusTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuServiceStatusTypesActionPerformed(evt);
            }
        });
        menuApplications.add(menuServiceStatusTypes);

        menuRefData.add(menuApplications);

        menuAdministrative.setText(bundle.getString("MainForm.menuAdministrative.text")); // NOI18N
        menuAdministrative.setName("menuAdministrative"); // NOI18N

        menuBaUnitType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuBaUnitType.setText(bundle.getString("MainForm.menuBaUnitType.text")); // NOI18N
        menuBaUnitType.setName("menuBaUnitType"); // NOI18N
        menuBaUnitType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBaUnitTypeActionPerformed(evt);
            }
        });
        menuAdministrative.add(menuBaUnitType);

        menuBaUnitRelationTypes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuBaUnitRelationTypes.setText(bundle.getString("MainForm.menuBaUnitRelationTypes.text")); // NOI18N
        menuBaUnitRelationTypes.setName("menuBaUnitRelationTypes"); // NOI18N
        menuBaUnitRelationTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBaUnitRelationTypesActionPerformed(evt);
            }
        });
        menuAdministrative.add(menuBaUnitRelationTypes);

        menuMortgageTypes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuMortgageTypes.setText(bundle.getString("MainForm.menuMortgageTypes.text")); // NOI18N
        menuMortgageTypes.setName("menuMortgageTypes"); // NOI18N
        menuMortgageTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMortgageTypesActionPerformed(evt);
            }
        });
        menuAdministrative.add(menuMortgageTypes);

        menuRrrGroupTypes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuRrrGroupTypes.setText(bundle.getString("MainForm.menuRrrGroupTypes.text")); // NOI18N
        menuRrrGroupTypes.setName("menuRrrGroupTypes"); // NOI18N
        menuRrrGroupTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRrrGroupTypesActionPerformed(evt);
            }
        });
        menuAdministrative.add(menuRrrGroupTypes);

        menuRrrTypes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuRrrTypes.setText(bundle.getString("MainForm.menuRrrTypes.text")); // NOI18N
        menuRrrTypes.setName("menuRrrTypes"); // NOI18N
        menuRrrTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRrrTypesActionPerformed(evt);
            }
        });
        menuAdministrative.add(menuRrrTypes);

        menuRefData.add(menuAdministrative);

        menuSources.setText(bundle.getString("MainForm.menuSources.text")); // NOI18N
        menuSources.setName("menuSources"); // NOI18N

        menuSourceTypes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuSourceTypes.setText(bundle.getString("MainForm.menuSourceTypes.text")); // NOI18N
        menuSourceTypes.setName("menuSourceTypes"); // NOI18N
        menuSourceTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSourceTypesActionPerformed(evt);
            }
        });
        menuSources.add(menuSourceTypes);

        menuRefData.add(menuSources);

        menuParty.setText(bundle.getString("MainForm.menuParty.text")); // NOI18N
        menuParty.setName("menuParty"); // NOI18N

        menuCommunicationType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuCommunicationType.setText(bundle.getString("MainForm.menuCommunicationType.text")); // NOI18N
        menuCommunicationType.setName("menuCommunicationType"); // NOI18N
        menuCommunicationType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCommunicationTypeActionPerformed(evt);
            }
        });
        menuParty.add(menuCommunicationType);

        menuIdTypes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuIdTypes.setText(bundle.getString("MainForm.menuIdTypes.text")); // NOI18N
        menuIdTypes.setName("menuIdTypes"); // NOI18N
        menuIdTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuIdTypesActionPerformed(evt);
            }
        });
        menuParty.add(menuIdTypes);

        menuGenders.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuGenders.setText(bundle.getString("MainForm.menuGenders.text")); // NOI18N
        menuGenders.setName("menuGenders"); // NOI18N
        menuGenders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGendersActionPerformed(evt);
            }
        });
        menuParty.add(menuGenders);

        menuPartyRoleType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuPartyRoleType.setText(bundle.getString("MainForm.menuPartyRoleType.text")); // NOI18N
        menuPartyRoleType.setName("menuPartyRoleType"); // NOI18N
        menuPartyRoleType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPartyRoleTypeActionPerformed(evt);
            }
        });
        menuParty.add(menuPartyRoleType);

        menuPartyType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuPartyType.setText(bundle.getString("MainForm.menuPartyType.text")); // NOI18N
        menuPartyType.setName("menuPartyType"); // NOI18N
        menuPartyType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPartyTypeActionPerformed(evt);
            }
        });
        menuParty.add(menuPartyType);

        menuRefData.add(menuParty);

        menuSystem.setText(bundle.getString("MainForm.menuSystem.text")); // NOI18N
        menuSystem.setName("menuSystem"); // NOI18N

        menuBRSeverityType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuBRSeverityType.setText(bundle.getString("MainForm.menuBRSeverityType.text")); // NOI18N
        menuBRSeverityType.setName("menuBRSeverityType"); // NOI18N
        menuBRSeverityType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBRSeverityTypeActionPerformed(evt);
            }
        });
        menuSystem.add(menuBRSeverityType);

        menuBRValidationTargetType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuBRValidationTargetType.setText(bundle.getString("MainForm.menuBRValidationTargetType.text")); // NOI18N
        menuBRValidationTargetType.setName("menuBRValidationTargetType"); // NOI18N
        menuBRValidationTargetType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBRValidationTargetTypeActionPerformed(evt);
            }
        });
        menuSystem.add(menuBRValidationTargetType);

        menuBRTechnicalType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuBRTechnicalType.setText(bundle.getString("MainForm.menuBRTechnicalType.text")); // NOI18N
        menuBRTechnicalType.setName("menuBRTechnicalType"); // NOI18N
        menuBRTechnicalType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBRTechnicalTypeActionPerformed(evt);
            }
        });
        menuSystem.add(menuBRTechnicalType);

        menuRefData.add(menuSystem);

        menuTransaction.setText(bundle.getString("MainForm.menuTransaction.text")); // NOI18N
        menuTransaction.setName("menuTransaction"); // NOI18N

        menuRegistrationStatusType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/book-open.png"))); // NOI18N
        menuRegistrationStatusType.setText(bundle.getString("MainForm.menuRegistrationStatusType.text")); // NOI18N
        menuRegistrationStatusType.setName("menuRegistrationStatusType"); // NOI18N
        menuRegistrationStatusType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRegistrationStatusTypeActionPerformed(evt);
            }
        });
        menuTransaction.add(menuRegistrationStatusType);

        menuRefData.add(menuTransaction);

        jSeparator5.setName(bundle.getString("MainForm.jSeparator5.name")); // NOI18N
        menuRefData.add(jSeparator5);

        jMenu1.setText(bundle.getString("MainForm.jMenu1.text")); // NOI18N
        jMenu1.setName(bundle.getString("MainForm.jMenu1.name")); // NOI18N

        menuVDCSetup.setText(bundle.getString("MainForm.menuVDCSetup.text")); // NOI18N
        menuVDCSetup.setName(bundle.getString("MainForm.menuVDCSetup.name")); // NOI18N
        menuVDCSetup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuVDCSetupActionPerformed(evt);
            }
        });
        jMenu1.add(menuVDCSetup);

        menuDistrict.setText(bundle.getString("MainForm.menuDistrict.text")); // NOI18N
        menuDistrict.setName(bundle.getString("MainForm.menuDistrict.name")); // NOI18N
        menuDistrict.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDistrictActionPerformed(evt);
            }
        });
        jMenu1.add(menuDistrict);

        menuOffices.setText(bundle.getString("MainForm.menuOffices.text")); // NOI18N
        menuOffices.setName(bundle.getString("MainForm.menuOffices.name")); // NOI18N
        menuOffices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOfficesActionPerformed(evt);
            }
        });
        jMenu1.add(menuOffices);

        menuDepartments.setText(bundle.getString("MainForm.menuDepartments.text")); // NOI18N
        menuDepartments.setName(bundle.getString("MainForm.menuDepartments.name")); // NOI18N
        menuDepartments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDepartmentsActionPerformed(evt);
            }
        });
        jMenu1.add(menuDepartments);

        menuMapsheet.setText(bundle.getString("MainForm.menuMapsheet.text")); // NOI18N
        menuMapsheet.setName(bundle.getString("MainForm.menuMapsheet.name")); // NOI18N
        menuMapsheet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMapsheetActionPerformed(evt);
            }
        });
        jMenu1.add(menuMapsheet);

        menuRestrictionRelease.setText(bundle.getString("MainForm.menuRestrictionRelease.text")); // NOI18N
        menuRestrictionRelease.setName(bundle.getString("MainForm.menuRestrictionRelease.name")); // NOI18N

        menuRestrictionType.setText(bundle.getString("MainForm.menuRestrictionType.text")); // NOI18N
        menuRestrictionType.setName(bundle.getString("MainForm.menuRestrictionType.name")); // NOI18N
        menuRestrictionType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRestrictionTypeActionPerformed(evt);
            }
        });
        menuRestrictionRelease.add(menuRestrictionType);

        menuRestrictionReason.setText(bundle.getString("MainForm.menuRestrictionReason.text")); // NOI18N
        menuRestrictionReason.setName(bundle.getString("MainForm.menuRestrictionReason.name")); // NOI18N
        menuRestrictionReason.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRestrictionReasonActionPerformed(evt);
            }
        });
        menuRestrictionRelease.add(menuRestrictionReason);

        menuRestrictionReleaseReason.setText(bundle.getString("MainForm.menuRestrictionReleaseReason.text")); // NOI18N
        menuRestrictionReleaseReason.setName(bundle.getString("MainForm.menuRestrictionReleaseReason.name")); // NOI18N
        menuRestrictionReleaseReason.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRestrictionReleaseReasonActionPerformed(evt);
            }
        });
        menuRestrictionRelease.add(menuRestrictionReleaseReason);

        menuRestrictionOffice.setText(bundle.getString("MainForm.menuRestrictionOffice.text")); // NOI18N
        menuRestrictionOffice.setName(bundle.getString("MainForm.menuRestrictionOffice.name")); // NOI18N
        menuRestrictionOffice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRestrictionOfficeActionPerformed(evt);
            }
        });
        menuRestrictionRelease.add(menuRestrictionOffice);

        jMenu1.add(menuRestrictionRelease);

        menuOwnerTypes.setText(bundle.getString("MainForm.menuOwnerTypes.text")); // NOI18N
        menuOwnerTypes.setName(bundle.getString("MainForm.menuOwnerTypes.name")); // NOI18N
        menuOwnerTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOwnerTypesActionPerformed(evt);
            }
        });
        jMenu1.add(menuOwnerTypes);

        menuShareTypes.setText(bundle.getString("MainForm.menuShareTypes.text")); // NOI18N
        menuShareTypes.setName(bundle.getString("MainForm.menuShareTypes.name")); // NOI18N
        menuShareTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuShareTypesActionPerformed(evt);
            }
        });
        jMenu1.add(menuShareTypes);

        menuTenantTypes.setText(bundle.getString("MainForm.menuTenantTypes.text")); // NOI18N
        menuTenantTypes.setName(bundle.getString("MainForm.menuTenantTypes.name")); // NOI18N
        menuTenantTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTenantTypesActionPerformed(evt);
            }
        });
        jMenu1.add(menuTenantTypes);

        menuParcelTypes.setText(bundle.getString("MainForm.menuParcelTypes.text")); // NOI18N
        menuParcelTypes.setName(bundle.getString("MainForm.menuParcelTypes.name")); // NOI18N
        menuParcelTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuParcelTypesActionPerformed(evt);
            }
        });
        jMenu1.add(menuParcelTypes);

        menuLandClass.setText(bundle.getString("MainForm.menuLandClass.text")); // NOI18N
        menuLandClass.setName(bundle.getString("MainForm.menuLandClass.name")); // NOI18N
        menuLandClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLandClassActionPerformed(evt);
            }
        });
        jMenu1.add(menuLandClass);

        menuLandUses.setText(bundle.getString("MainForm.menuLandUses.text")); // NOI18N
        menuLandUses.setName(bundle.getString("MainForm.menuLandUses.name")); // NOI18N
        menuLandUses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLandUsesActionPerformed(evt);
            }
        });
        jMenu1.add(menuLandUses);

        menuGuthiNames.setText(bundle.getString("MainForm.menuGuthiNames.text")); // NOI18N
        menuGuthiNames.setName(bundle.getString("MainForm.menuGuthiNames.name")); // NOI18N
        menuGuthiNames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGuthiNamesActionPerformed(evt);
            }
        });
        jMenu1.add(menuGuthiNames);

        menuRefData.add(jMenu1);

        mainMenu.add(menuRefData);

        menuReports.setText(bundle.getString("MainForm.menuReports.text_1")); // NOI18N
        menuReports.setName("menuReports"); // NOI18N

        menuLodgementReport.setText(bundle.getString("MainForm.menuLodgementReport.text_1")); // NOI18N
        menuLodgementReport.setName("menuLodgementReport"); // NOI18N
        menuLodgementReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLodgementReportActionPerformed(evt);
            }
        });
        menuReports.add(menuLodgementReport);

        menuTimeReport.setText(bundle.getString("MainForm.menuTimeReport.text_1")); // NOI18N
        menuTimeReport.setName("menuTimeReport"); // NOI18N
        menuTimeReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTimeReportActionPerformed(evt);
            }
        });
        menuReports.add(menuTimeReport);

        jMenuItem1.setText(bundle.getString("MainForm.jMenuItem1.text")); // NOI18N
        jMenuItem1.setName(bundle.getString("MainForm.jMenuItem1.name")); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuReports.add(jMenuItem1);

        mainMenu.add(menuReports);

        menuHelp.setText(bundle.getString("MainForm.menuHelp.text")); // NOI18N
        menuHelp.setName("menuHelp"); // NOI18N
        mainMenu.add(menuHelp);

        setJMenuBar(mainMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainContentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(statusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainContentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Opens {@link ReportViewerForm} to display report.
     */
    private void showReport(JasperPrint report) {
        ReportViewerForm form = new ReportViewerForm(report);
        form.setVisible(true);
    }
    private void menuLodgementReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLodgementReportActionPerformed

        showReport(ReportManager.getBrReport());     }//GEN-LAST:event_menuLodgementReportActionPerformed

    private void menuTimeReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTimeReportActionPerformed

        showReport(ReportManager.getBrValidaction());     }//GEN-LAST:event_menuTimeReportActionPerformed

    private void menuBaUnitRelationTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBaUnitRelationTypesActionPerformed
        openReferenceDataPanel(BaUnitRelTypeBean.class, menuBaUnitRelationTypes.getText());
    }//GEN-LAST:event_menuBaUnitRelationTypesActionPerformed

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menuExitActionPerformed

    private void menuRolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRolesActionPerformed
        manageRoles();
    }//GEN-LAST:event_menuRolesActionPerformed

    private void btnRolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRolesActionPerformed
        manageRoles();
    }//GEN-LAST:event_btnRolesActionPerformed

    private void menuGroupsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGroupsActionPerformed
        manageGroups();
    }//GEN-LAST:event_menuGroupsActionPerformed

    private void btnGroupsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGroupsActionPerformed
        manageGroups();
    }//GEN-LAST:event_btnGroupsActionPerformed

    private void menuSecurityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSecurityActionPerformed
        manageUsers();
    }//GEN-LAST:event_menuSecurityActionPerformed

    private void btnUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsersActionPerformed
        manageUsers();
    }//GEN-LAST:event_btnUsersActionPerformed

    private void menuRequestCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRequestCategoryActionPerformed
        manageRequestCategories();
    }//GEN-LAST:event_menuRequestCategoryActionPerformed

    private void menuRequestTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRequestTypesActionPerformed
        manageRequestTypes();
    }//GEN-LAST:event_menuRequestTypesActionPerformed

    private void menuTypeActionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTypeActionsActionPerformed
        manageTypeActions();
    }//GEN-LAST:event_menuTypeActionsActionPerformed

    private void menuServiceActionTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuServiceActionTypesActionPerformed
        manageServiceActionTypes();
    }//GEN-LAST:event_menuServiceActionTypesActionPerformed

    private void menuServiceStatusTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuServiceStatusTypesActionPerformed
        manageServiceStatusTypes();
    }//GEN-LAST:event_menuServiceStatusTypesActionPerformed

    private void menuBaUnitTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBaUnitTypeActionPerformed
        manageBAUnitType();
    }//GEN-LAST:event_menuBaUnitTypeActionPerformed

    private void menuMortgageTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMortgageTypesActionPerformed
        manageMortgageTypes();
    }//GEN-LAST:event_menuMortgageTypesActionPerformed

    private void menuRrrGroupTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRrrGroupTypesActionPerformed
        manageRrrGroupTypes();
    }//GEN-LAST:event_menuRrrGroupTypesActionPerformed

    private void menuRrrTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRrrTypesActionPerformed
        manageRrrTypes();
    }//GEN-LAST:event_menuRrrTypesActionPerformed

    private void menuSourceTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSourceTypesActionPerformed
        manageSourceTypes();
    }//GEN-LAST:event_menuSourceTypesActionPerformed

    private void menuCommunicationTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCommunicationTypeActionPerformed
        manageCommunicationTypes();
    }//GEN-LAST:event_menuCommunicationTypeActionPerformed

    private void menuIdTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuIdTypesActionPerformed
        manageIdTypes();
    }//GEN-LAST:event_menuIdTypesActionPerformed

    private void menuGendersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGendersActionPerformed
        manageGender();
    }//GEN-LAST:event_menuGendersActionPerformed

    private void menuPartyRoleTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPartyRoleTypeActionPerformed
        managePartyRoleTypes();
    }//GEN-LAST:event_menuPartyRoleTypeActionPerformed

    private void menuPartyTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPartyTypeActionPerformed
        managePartyTypes();
    }//GEN-LAST:event_menuPartyTypeActionPerformed

    private void menuBRSeverityTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBRSeverityTypeActionPerformed
        manageBRSeverityTypes();
    }//GEN-LAST:event_menuBRSeverityTypeActionPerformed

    private void menuBRValidationTargetTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBRValidationTargetTypeActionPerformed
        manageBRValidationTargetTypes();
    }//GEN-LAST:event_menuBRValidationTargetTypeActionPerformed

    private void menuBRTechnicalTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBRTechnicalTypeActionPerformed
        manageBRTechnicalTypes();
    }//GEN-LAST:event_menuBRTechnicalTypeActionPerformed

    private void btnBrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrActionPerformed
        manageBr();
    }//GEN-LAST:event_btnBrActionPerformed

    private void menuRegistrationStatusTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRegistrationStatusTypeActionPerformed
        manageRegistrationStatusTypes();
    }//GEN-LAST:event_menuRegistrationStatusTypeActionPerformed

    private void btnCalendarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalendarActionPerformed
        // TODO add your handling code here:
        //NepaliCalendar nepCal=new NepaliCalendar();
        CalendarManagement nepCal = new CalendarManagement();
        mainContentPanel.addPanel(nepCal, MainContentPanel.CARD_ADMIN_CALENDAR, true);
    }//GEN-LAST:event_btnCalendarActionPerformed

    private void menuVDCSetupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuVDCSetupActionPerformed
        // TODO add your handling code here:
        manageVDCs();
    }//GEN-LAST:event_menuVDCSetupActionPerformed

    private void menuDistrictActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDistrictActionPerformed
        manageDistricts();
    }//GEN-LAST:event_menuDistrictActionPerformed

    private void menuOfficesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOfficesActionPerformed
        showOfficesForm();
    }//GEN-LAST:event_menuOfficesActionPerformed

    private void menuDepartmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDepartmentsActionPerformed
        showDepartmentsForm();
    }//GEN-LAST:event_menuDepartmentsActionPerformed

    private void menuMapsheetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMapsheetActionPerformed
        manageMapSheet();
    }//GEN-LAST:event_menuMapsheetActionPerformed

    private void menuRestrictionTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRestrictionTypeActionPerformed
        manageRestrictionType();
    }//GEN-LAST:event_menuRestrictionTypeActionPerformed

    private void menuRestrictionReleaseReasonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRestrictionReleaseReasonActionPerformed
        manageRestrictionReleaseType();
    }//GEN-LAST:event_menuRestrictionReleaseReasonActionPerformed

    private void menuRestrictionReasonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRestrictionReasonActionPerformed
        manageRestrictionReason();
    }//GEN-LAST:event_menuRestrictionReasonActionPerformed

    private void menuRestrictionOfficeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRestrictionOfficeActionPerformed
        manageRestrictionOffice();
    }//GEN-LAST:event_menuRestrictionOfficeActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        //showReport(ReportManager.getMapSheetListReport());
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void menuOwnerTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOwnerTypesActionPerformed
        // TODO add your handling code here:
        manageOwnershipTpes();
    }//GEN-LAST:event_menuOwnerTypesActionPerformed

    private void menuShareTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuShareTypesActionPerformed
        // TODO add your handling code here:
        manageShareTypes();
    }//GEN-LAST:event_menuShareTypesActionPerformed

    private void menuTenantTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTenantTypesActionPerformed
        // TODO add your handling code here:
        manageTenantTypes();
    }//GEN-LAST:event_menuTenantTypesActionPerformed

    private void menuParcelTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuParcelTypesActionPerformed
        // TODO add your handling code here:
        manageParcelTypes();
    }//GEN-LAST:event_menuParcelTypesActionPerformed

    private void menuLandClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLandClassActionPerformed
        // TODO add your handling code here:
        manageLandClasses();
    }//GEN-LAST:event_menuLandClassActionPerformed

    private void menuLandUsesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLandUsesActionPerformed
        // TODO add your handling code here:
        manageLandUses();
    }//GEN-LAST:event_menuLandUsesActionPerformed

    private void menuGuthiNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGuthiNamesActionPerformed
        // TODO add your handling code here:
        manageGuthiNames();
    }//GEN-LAST:event_menuGuthiNamesActionPerformed

    /**
     * Opens roles management panel.
     */
    private void manageRoles() {
        if (mainContentPanel.isPanelOpened(MainContentPanel.CARD_ADMIN_ROLES_MANAGE)) {
            mainContentPanel.showPanel(MainContentPanel.CARD_ADMIN_ROLES_MANAGE);
        } else {
            RolesManagementPanel panel = new RolesManagementPanel();
            mainContentPanel.addPanel(panel, MainContentPanel.CARD_ADMIN_ROLES_MANAGE, true);
        }
    }

    /**
     * Opens groups management panel.
     */
    private void manageGroups() {
        if (mainContentPanel.isPanelOpened(MainContentPanel.CARD_ADMIN_GROUP_MANAGE)) {
            mainContentPanel.showPanel(MainContentPanel.CARD_ADMIN_GROUP_MANAGE);
        } else {
            GroupsManagementPanel groupManagementPanel = new GroupsManagementPanel();
            mainContentPanel.addPanel(groupManagementPanel, MainContentPanel.CARD_ADMIN_GROUP_MANAGE, true);
        }
    }

    /**
     * Opens users management panel.
     */
    private void manageUsers() {
        if (mainContentPanel.isPanelOpened(MainContentPanel.CARD_ADMIN_USER_MANAGE)) {
            mainContentPanel.showPanel(MainContentPanel.CARD_ADMIN_USER_MANAGE);
        } else {
            UsersManagementPanel panel = new UsersManagementPanel();
            mainContentPanel.addPanel(panel, MainContentPanel.CARD_ADMIN_USER_MANAGE, true);
        }
    }

    private void manageSystemSettings() {
        JOptionPane.showMessageDialog(this, "Not yet implemented.");
    }

    private void manageGisSettings() {
        JOptionPane.showMessageDialog(this, "Not yet implemented.");
    }

    private void manageCommunicationTypes() {
        openReferenceDataPanel(CommunicationTypeBean.class,
                menuCommunicationType.getText());
    }

    private void manageBAUnitType() {
        openReferenceDataPanel(BaUnitTypeBean.class, menuBaUnitType.getText());
    }

    private void manageIdTypes() {
        openReferenceDataPanel(IdTypeBean.class, menuIdTypes.getText());
    }

    private void manageGender() {
        openReferenceDataPanel(GenderTypeBean.class, menuGenders.getText());
    }

    private void managePartyRoleTypes() {
        openReferenceDataPanel(PartyRoleTypeBean.class, menuPartyRoleType.getText());
    }

    private void managePartyTypes() {
        openReferenceDataPanel(PartyTypeBean.class, menuPartyType.getText());
    }

    private void manageMortgageTypes() {
        openReferenceDataPanel(MortgageTypeBean.class, menuMortgageTypes.getText());
    }

    private void manageRrrGroupTypes() {
        openReferenceDataPanel(RrrGroupTypeBean.class, menuRrrGroupTypes.getText());
    }

    private void manageRrrTypes() {
        openReferenceDataPanel(RrrTypeBean.class, menuRrrTypes.getText());
    }

    private void manageSourceTypes() {
        openReferenceDataPanel(SourceTypeBean.class, menuSourceTypes.getText());
    }

    private void manageRequestTypes() {
        openReferenceDataPanel(RequestTypeBean.class, menuRequestTypes.getText());
    }

    private void manageTypeActions() {
        openReferenceDataPanel(TypeActionBean.class, menuTypeActions.getText());
    }

    private void manageServiceActionTypes() {
        openReferenceDataPanel(ServiceActionTypeBean.class, menuServiceActionTypes.getText());
    }

    private void manageServiceStatusTypes() {
        openReferenceDataPanel(ServiceStatusTypeBean.class, menuServiceStatusTypes.getText());
    }

    private void manageRequestCategories() {
        openReferenceDataPanel(RequestCategoryTypeBean.class, menuRequestCategory.getText());
    }

    private void manageRegistrationStatusTypes() {
        openReferenceDataPanel(RegistrationStatusTypeBean.class, menuRegistrationStatusType.getText());
    }

    private void manageBRSeverityTypes() {
        openReferenceDataPanel(BrSeverityTypeBean.class, menuBRSeverityType.getText());
    }

    private void manageBRValidationTargetTypes() {
        openReferenceDataPanel(BrValidationTargetTypeBean.class, menuBRValidationTargetType.getText());
    }

    private void manageBRTechnicalTypes() {
        openReferenceDataPanel(BrTechnicalTypeBean.class, menuBRTechnicalType.getText());
    }

    private void manageVDCs() {
        VdcManagementPanel panel = new VdcManagementPanel();
        mainContentPanel.addPanel(panel, MainContentPanel.CARD_ADMIN_REFDATA_MANAGE, true);
    }

    private void manageDistricts() {
        openReferenceDataPanel(DistrictBean.class, menuDistrict.getText());
    }

    private void manageMapSheet() {
        MapSheetNoManagementPanel panel = new MapSheetNoManagementPanel();
        mainContentPanel.addPanel(panel, MainContentPanel.CARD_ADMIN_REFDATA_MANAGE, true);
    }

    private void manageBr() {
        if (mainContentPanel.isPanelOpened(MainContentPanel.CARD_ADMIN_BR_MANAGE)) {
            mainContentPanel.showPanel(MainContentPanel.CARD_ADMIN_BR_MANAGE);
        } else {
            BrManagementPanel panel = new BrManagementPanel();
            mainContentPanel.addPanel(panel, MainContentPanel.CARD_ADMIN_BR_MANAGE, true);
        }
    }

    private void manageRestrictionType() {
        openReferenceDataPanel(RestrictionTypeBean.class, menuRestrictionType.getText());
    }

    private void manageRestrictionReason() {
        openReferenceDataPanel(RestrictionReasonBean.class, menuRestrictionReason.getText());
    }

    private void manageRestrictionReleaseType() {
        openReferenceDataPanel(RestrictionReleaseReasonBean.class, menuRestrictionReleaseReason.getText());
    }

    private void manageRestrictionOffice() {
        openReferenceDataPanel(RestrictionOfficeBean.class, menuRestrictionOffice.getText());
    }

    private void manageOwnershipTpes() {
        openReferenceDataPanel(OwnershipTypeBean.class, menuOwnerTypes.getText());
    }

    private void manageShareTypes() {
        openReferenceDataPanel(ShareTypeBean.class, menuShareTypes.getText());
    }

    private void manageTenantTypes() {
        openReferenceDataPanel(TenantTypeBean.class, menuTenantTypes.getText());
    }

    private void manageParcelTypes() {
        openReferenceDataPanel(ParcelTypeBean.class, menuParcelTypes.getText());
    }

    private void manageLandClasses() {
        openReferenceDataPanel(LandClassBean.class, menuLandClass.getText());
    }

    private void manageLandUses() {
        openReferenceDataPanel(LandUseBean.class, menuLandUses.getText());
    }

    private void manageGuthiNames() {
        openReferenceDataPanel(GuthiNameBean.class, menuGuthiNames.getText());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBr;
    private javax.swing.JButton btnCalendar;
    private javax.swing.JButton btnGroups;
    private javax.swing.JButton btnRoles;
    private javax.swing.JButton btnUsers;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JLabel lblUserName;
    private org.sola.clients.swing.ui.MainContentPanel mainContentPanel;
    private javax.swing.JMenuBar mainMenu;
    private javax.swing.JToolBar mainToolbar;
    private javax.swing.JMenu menuAdministrative;
    private javax.swing.JMenu menuApplications;
    private javax.swing.JMenuItem menuBRSeverityType;
    private javax.swing.JMenuItem menuBRTechnicalType;
    private javax.swing.JMenuItem menuBRValidationTargetType;
    private javax.swing.JMenuItem menuBaUnitRelationTypes;
    private javax.swing.JMenuItem menuBaUnitType;
    private javax.swing.JMenuItem menuCommunicationType;
    private javax.swing.JMenuItem menuDepartments;
    private javax.swing.JMenuItem menuDistrict;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuGenders;
    private javax.swing.JMenuItem menuGroups;
    private javax.swing.JMenuItem menuGuthiNames;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuIdTypes;
    private javax.swing.JMenuItem menuLandClass;
    private javax.swing.JMenuItem menuLandUses;
    private javax.swing.JMenuItem menuLodgementReport;
    private javax.swing.JMenuItem menuMapsheet;
    private javax.swing.JMenuItem menuMortgageTypes;
    private javax.swing.JMenuItem menuOffices;
    private javax.swing.JMenuItem menuOwnerTypes;
    private javax.swing.JMenuItem menuParcelTypes;
    private javax.swing.JMenu menuParty;
    private javax.swing.JMenuItem menuPartyRoleType;
    private javax.swing.JMenuItem menuPartyType;
    private javax.swing.JMenu menuRefData;
    private javax.swing.JMenuItem menuRegistrationStatusType;
    private javax.swing.JMenu menuReports;
    private javax.swing.JMenuItem menuRequestCategory;
    private javax.swing.JMenuItem menuRequestTypes;
    private javax.swing.JMenuItem menuRestrictionOffice;
    private javax.swing.JMenuItem menuRestrictionReason;
    private javax.swing.JMenu menuRestrictionRelease;
    private javax.swing.JMenuItem menuRestrictionReleaseReason;
    private javax.swing.JMenuItem menuRestrictionType;
    private javax.swing.JMenuItem menuRoles;
    private javax.swing.JMenuItem menuRrrGroupTypes;
    private javax.swing.JMenuItem menuRrrTypes;
    private javax.swing.JMenu menuSecurity;
    private javax.swing.JMenuItem menuServiceActionTypes;
    private javax.swing.JMenuItem menuServiceStatusTypes;
    private javax.swing.JMenuItem menuShareTypes;
    private javax.swing.JMenuItem menuSourceTypes;
    private javax.swing.JMenu menuSources;
    private javax.swing.JMenu menuSystem;
    private javax.swing.JMenuItem menuTenantTypes;
    private javax.swing.JMenuItem menuTimeReport;
    private javax.swing.JMenu menuTransaction;
    private javax.swing.JMenuItem menuTypeActions;
    private javax.swing.JMenuItem menuUsers;
    private javax.swing.JMenuItem menuVDCSetup;
    private javax.swing.JPanel statusPanel;
    private org.sola.clients.swing.common.tasks.TaskPanel taskPanel1;
    // End of variables declaration//GEN-END:variables
}
