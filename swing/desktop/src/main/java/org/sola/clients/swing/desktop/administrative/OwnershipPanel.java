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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.validation.groups.Default;
import org.sola.clients.beans.administrative.LocWithMothBean;
import org.sola.clients.beans.administrative.RrrBean;
import org.sola.clients.beans.administrative.validation.OwnershipValidationGroup;
import org.sola.clients.beans.application.ApplicationBean;
import org.sola.clients.beans.application.ApplicationServiceBean;
import org.sola.clients.beans.party.PartySummaryBean;
import org.sola.clients.beans.referencedata.StatusConstants;
import org.sola.clients.swing.common.LafManager;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.desktop.MainForm;
import org.sola.clients.swing.desktop.party.PartyPanelForm;
import org.sola.clients.swing.desktop.party.PersonSearchForm;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.MainContentPanel;
import org.sola.clients.swing.ui.administrative.LocSearchCreatePanel;
import org.sola.clients.swing.ui.party.PartySearchPanel;
import org.sola.clients.swing.ui.source.DocumentsManagementPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
* Form for managing ownership right. {@link RrrBean} is used to bind the data
* on the form.
*/
public class OwnershipPanel extends ContentPanel {

    private class RightHolderFormListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals(PartyPanelForm.PARTY_SAVED)) {
                rrrBean.addOrUpdateRightholder((PartySummaryBean) ((PartyPanelForm) evt.getSource()).getParty());
                tableRightholders.clearSelection();
            }
        }
    }

    private class PersonSearchFormListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals(PartySearchPanel.SELECT_PARTY_PROPERTY)) {
                getMainContentPanel().closePanel(MainContentPanel.CARD_SEARCH_PERSONS);
                rrrBean.addOrUpdateRightholder((PartySummaryBean) evt.getNewValue());
                tableRightholders.clearSelection();
            }
        }
    }
    private ApplicationBean applicationBean;
    private ApplicationServiceBean appService;
    private RrrBean.RRR_ACTION rrrAction;
    public static final String UPDATED_RRR = "updatedRRR";
    private final RightHolderFormListener personFormListener = new RightHolderFormListener();
    private final PersonSearchFormListener personSearchFormListener = new PersonSearchFormListener();

    private DocumentsManagementPanel createDocumentsPanel() {
        if (rrrBean == null) {
            rrrBean = new RrrBean();
        }
        if (applicationBean == null) {
            applicationBean = new ApplicationBean();
        }

        boolean allowEdit = true;
        if (rrrAction == RrrBean.RRR_ACTION.VIEW) {
            allowEdit = false;
        }

        DocumentsManagementPanel panel = new DocumentsManagementPanel(
                rrrBean.getSourceList(), applicationBean, allowEdit);
        return panel;
    }

    private RrrBean createRrrBean() {
        if (rrrBean == null) {
            rrrBean = new RrrBean();
        }
        return rrrBean;
    }

    public OwnershipPanel(RrrBean rrrBean, ApplicationBean applicationBean,
            ApplicationServiceBean applicationService, RrrBean.RRR_ACTION rrrAction) {

        this.applicationBean = applicationBean;
        this.appService = applicationService;
        this.rrrAction = rrrAction;
        prepareRrrBean(rrrBean, rrrAction);

        initComponents();

        headerPanel.setTitleText(rrrBean.getRrrType().getDisplayValue());
        customizeForm();
        saveRrrState();
    }

    private void prepareRrrBean(RrrBean rrrBean, RrrBean.RRR_ACTION rrrAction) {
        if (rrrBean == null) {
            this.rrrBean = new RrrBean();
            this.rrrBean.setStatusCode(StatusConstants.PENDING);
        } else {
            this.rrrBean = rrrBean.makeCopyByAction(rrrAction);
        }
    }

    private void customizeOwnersButtons() {
        PartySummaryBean partyBean = rrrBean.getSelectedRightHolder();
        boolean isReadOnly = rrrAction == RrrBean.RRR_ACTION.VIEW
                || rrrBean.getLoc() == null || rrrBean.isTerminating();

        btnAddOwner.setEnabled(!isReadOnly);
        btnEditOwner.setEnabled(partyBean != null && !isReadOnly);
        btnRemoveOwner.setEnabled(partyBean != null && !isReadOnly);
        btnViewOwner.setEnabled(partyBean != null);

        menuAddOwner.setEnabled(btnAddOwner.isEnabled());
        menuEditOwner.setEnabled(btnEditOwner.isEnabled());
        menuRemoveOwner.setEnabled(btnRemoveOwner.isEnabled());
        menuViewOwner.setEnabled(btnViewOwner.isEnabled());
    }

    private void customizeDocumentsPanel() {
        boolean isReadOnly = rrrAction == RrrBean.RRR_ACTION.VIEW
                || rrrBean.getLoc() == null || rrrBean.isTerminating();
        documentsPanel.setAllowEdit(!isReadOnly);
    }

    private void customizeForm() {
        if (rrrAction == RrrBean.RRR_ACTION.NEW) {
            btnSave.setText(MessageUtility.getLocalizedMessage(
                    ClientMessage.GENERAL_LABELS_CREATE_AND_CLOSE).getMessage());
        }
        if (rrrAction == RrrBean.RRR_ACTION.CANCEL) {
            btnSave.setText(MessageUtility.getLocalizedMessage(
                    ClientMessage.GENERAL_LABELS_TERMINATE_AND_CLOSE).getMessage());
        }

        if (rrrAction != RrrBean.RRR_ACTION.EDIT && rrrAction != RrrBean.RRR_ACTION.VIEW
                && appService != null) {
            // Set default noation text from the selected application service
                txtNotationText.setText(appService.getRequestType().getNotationTemplate());
        }

        rrrBean.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(RrrBean.SELECTED_RIGHTHOLDER_PROPERTY)) {
                    customizeOwnersButtons();
                }
            }
        });

        locSearchCreatePanel.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(LocSearchCreatePanel.LOC_FOUND)) {
                    changeOwnersByLoc((LocWithMothBean) evt.getNewValue());
                }
                if (evt.getPropertyName().equals(LocSearchCreatePanel.CLOSE_PANEL)) {
                    showLocSearch(false);
                }
            }
        });
        rrrTypes.filterByOwnerShip();
        showLocSearch(false);
        customizeDocumentsPanel();
        customizeOwnersButtons();
        customizeFormButtons();
    }

    private void customizeFormButtons() {
        boolean readOnly = rrrAction == RrrBean.RRR_ACTION.VIEW;
        btnSave.setEnabled(!readOnly);

        boolean isLocReadOnly = readOnly || rrrBean.isTerminating() || rrrBean.getLocId() == null;

        txtNotationText.setEnabled(!(readOnly || rrrBean.getLocId() == null));
        cbxOwnerType.setEnabled(!isLocReadOnly);
        cbxRrrTypes.setEnabled(!isLocReadOnly);
        cbxShareType.setEnabled(!isLocReadOnly);
        txtRegDatetime.setEditable(!isLocReadOnly);
        btnEditLoc.setEnabled(!(readOnly || rrrBean.isTerminating()));
        btnRevertLocToCurrentState.setEnabled(!isLocReadOnly);
    }

    private void openRightHolderForm(final PartySummaryBean partySummaryBean, final boolean isReadOnly) {

        SolaTask t = new SolaTask<Void, Void>() {

            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_PERSON));
                PartyPanelForm partyForm;

                if (partySummaryBean != null) {
                    partyForm = new PartyPanelForm(true, partySummaryBean, isReadOnly, true);
                } else {
                    partyForm = new PartyPanelForm(true, null, isReadOnly, true);
                }
                partyForm.addPropertyChangeListener(personFormListener);
                getMainContentPanel().addPanel(partyForm, MainContentPanel.CARD_PERSON, true);
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }

    private void changeOwnersByLoc(LocWithMothBean loc) {
        rrrBean.changeLoc(loc);
        showLocSearch(false);
        customizeOwnersButtons();
        customizeDocumentsPanel();
        customizeFormButtons();
    }
    
    private void revertLocToCurrentState(){
        SolaTask<Boolean, Void> t = new SolaTask<Boolean, Void>() {

            @Override
            public Boolean doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_REVERT_LOC));
                return rrrBean.revertLocToCurrentState();
            }

            @Override
            public void taskDone() {
                if (get() == false) {
                    MessageUtility.displayMessage(ClientMessage.BAUNIT_LOC_CURRENT_STATE_NOT_FOUND);
                }
            }
        };
        TaskManager.getInstance().runTask(t);
    }

    private void showLocSearch(boolean show) {
        pnlLocDetails.setVisible(!show);
        pnlLocSearch.setVisible(show);
    }

    private void viewOwner() {
        if (rrrBean.getSelectedRightHolder() != null) {
            openRightHolderForm(rrrBean.getSelectedRightHolder(), true);
        }
    }

    private void removeOwner() {
        if (rrrBean.getSelectedRightHolder() != null
                && MessageUtility.displayMessage(ClientMessage.CONFIRM_DELETE_RECORD) == MessageUtility.BUTTON_ONE) {
            rrrBean.removeSelectedRightHolder();
        }
    }

    private void addOwner() {
        PersonSearchForm partySearchForm = new PersonSearchForm();
        partySearchForm.getPartySearchPanel().setShowSelectButton(true);
        partySearchForm.getPartySearchPanel().addPropertyChangeListener(personSearchFormListener);
        getMainContentPanel().addPanel(partySearchForm, MainContentPanel.CARD_SEARCH_PERSONS, true);
    }

    private void editOwner() {
        if (rrrBean.getSelectedRightHolder() != null) {
            openRightHolderForm(rrrBean.getSelectedRightHolder(), false);
        }
    }

    private boolean saveRrr() {
        if (rrrBean.validate(true, Default.class, OwnershipValidationGroup.class).size() < 1) {
            firePropertyChange(UPDATED_RRR, null, rrrBean);
            close();
            return true;
        }
        return false;
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

        rrrBean = createRrrBean();
        popUpOwners = new javax.swing.JPopupMenu();
        menuAddOwner = new javax.swing.JMenuItem();
        menuRemoveOwner = new javax.swing.JMenuItem();
        menuEditOwner = new javax.swing.JMenuItem();
        menuViewOwner = new javax.swing.JMenuItem();
        rrrTypes = new org.sola.clients.beans.referencedata.RrrTypeListBean();
        shareTypeListBean = new org.sola.clients.beans.referencedata.OwnershipTypeListBean();
        ownerTypeListBean = new org.sola.clients.beans.referencedata.OwnerTypeListBean();
        headerPanel = new org.sola.clients.swing.ui.HeaderPanel();
        jToolBar2 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(7, 0), new java.awt.Dimension(7, 0), new java.awt.Dimension(7, 32767));
        jLabel1 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        pnlLocDetails = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        btnEditLoc = new javax.swing.JButton();
        btnRevertLocToCurrentState = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtDistrict = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtVdc = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtMothtype = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtMothNumber = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtPageNumber = new javax.swing.JTextField();
        pnlLocSearch = new javax.swing.JPanel();
        locSearchCreatePanel = new org.sola.clients.swing.ui.administrative.LocSearchCreatePanel();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnAddOwner = new javax.swing.JButton();
        btnRemoveOwner = new javax.swing.JButton();
        btnEditOwner = new javax.swing.JButton();
        btnViewOwner = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableRightholders = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        groupPanel1 = new org.sola.clients.swing.ui.GroupPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtRegDatetime = new javax.swing.JFormattedTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbxOwnerType = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtNotationText = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        cbxRrrTypes = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        cbxShareType = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        groupPanel2 = new org.sola.clients.swing.ui.GroupPanel();
        documentsPanel = createDocumentsPanel();

        popUpOwners.setName("popUpOwners"); // NOI18N

        menuAddOwner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/administrative/Bundle"); // NOI18N
        menuAddOwner.setText(bundle.getString("OwnershipPanel.menuAddOwner.text")); // NOI18N
        menuAddOwner.setName("menuAddOwner"); // NOI18N
        menuAddOwner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddOwnerActionPerformed(evt);
            }
        });
        popUpOwners.add(menuAddOwner);

        menuRemoveOwner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        menuRemoveOwner.setText(bundle.getString("OwnershipPanel.menuRemoveOwner.text")); // NOI18N
        menuRemoveOwner.setName("menuRemoveOwner"); // NOI18N
        menuRemoveOwner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRemoveOwnerActionPerformed(evt);
            }
        });
        popUpOwners.add(menuRemoveOwner);

        menuEditOwner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        menuEditOwner.setText(bundle.getString("OwnershipPanel.menuEditOwner.text")); // NOI18N
        menuEditOwner.setName("menuEditOwner"); // NOI18N
        menuEditOwner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditOwnerActionPerformed(evt);
            }
        });
        popUpOwners.add(menuEditOwner);

        menuViewOwner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/view.png"))); // NOI18N
        menuViewOwner.setText(bundle.getString("OwnershipPanel.menuViewOwner.text")); // NOI18N
        menuViewOwner.setName("menuViewOwner"); // NOI18N
        menuViewOwner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewOwnerActionPerformed(evt);
            }
        });
        popUpOwners.add(menuViewOwner);

        setHeaderPanel(headerPanel);
        setHelpTopic(bundle.getString("OwnershipPanel.helpTopic")); // NOI18N
        setName("Form"); // NOI18N

        headerPanel.setName("headerPanel"); // NOI18N
        headerPanel.setTitleText(bundle.getString("OwnershipPanel.headerPanel.titleText")); // NOI18N

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setName("jToolBar2"); // NOI18N

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/save.png"))); // NOI18N
        btnSave.setText(bundle.getString("OwnershipPanel.btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar2.add(btnSave);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jToolBar2.add(jSeparator1);

        filler1.setName("filler1"); // NOI18N
        jToolBar2.add(filler1);

        jLabel1.setText(bundle.getString("OwnershipPanel.jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jToolBar2.add(jLabel1);

        lblStatus.setFont(LafManager.getInstance().getLabFontBold());
        lblStatus.setName("lblStatus"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${status.displayValue}"), lblStatus, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jToolBar2.add(lblStatus);

        jPanel8.setName(bundle.getString("OwnershipPanel.jPanel8.name")); // NOI18N

        pnlLocDetails.setName(bundle.getString("OwnershipPanel.pnlLocDetails.name")); // NOI18N

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);
        jToolBar3.setName(bundle.getString("OwnershipPanel.jToolBar3.name")); // NOI18N

        btnEditLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        btnEditLoc.setText(bundle.getString("OwnershipPanel.btnEditLoc.text")); // NOI18N
        btnEditLoc.setFocusable(false);
        btnEditLoc.setName(bundle.getString("OwnershipPanel.btnEditLoc.name")); // NOI18N
        btnEditLoc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEditLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditLocActionPerformed(evt);
            }
        });
        jToolBar3.add(btnEditLoc);

        btnRevertLocToCurrentState.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/revert.png"))); // NOI18N
        btnRevertLocToCurrentState.setText(bundle.getString("OwnershipPanel.btnRevertLocToCurrentState.text")); // NOI18N
        btnRevertLocToCurrentState.setToolTipText(bundle.getString("OwnershipPanel.btnRevertLocToCurrentState.toolTipText")); // NOI18N
        btnRevertLocToCurrentState.setFocusable(false);
        btnRevertLocToCurrentState.setName(bundle.getString("OwnershipPanel.btnRevertLocToCurrentState.name")); // NOI18N
        btnRevertLocToCurrentState.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRevertLocToCurrentState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRevertLocToCurrentStateActionPerformed(evt);
            }
        });
        jToolBar3.add(btnRevertLocToCurrentState);

        jPanel15.setName(bundle.getString("OwnershipPanel.jPanel15.name")); // NOI18N
        jPanel15.setLayout(new java.awt.GridLayout(1, 5, 15, 0));

        jPanel10.setName(bundle.getString("OwnershipPanel.jPanel10.name")); // NOI18N

        jLabel3.setText(bundle.getString("OwnershipPanel.jLabel3.text")); // NOI18N
        jLabel3.setName(bundle.getString("OwnershipPanel.jLabel3.name")); // NOI18N

        txtDistrict.setEditable(false);
        txtDistrict.setName(bundle.getString("OwnershipPanel.txtDistrict.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${loc.moth.vdc.district.displayValue}"), txtDistrict, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtDistrict, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel15.add(jPanel10);

        jPanel11.setName(bundle.getString("OwnershipPanel.jPanel11.name")); // NOI18N

        jLabel4.setText(bundle.getString("OwnershipPanel.jLabel4.text")); // NOI18N
        jLabel4.setName(bundle.getString("OwnershipPanel.jLabel4.name")); // NOI18N

        txtVdc.setEditable(false);
        txtVdc.setName(bundle.getString("OwnershipPanel.txtVdc.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${loc.moth.vdc.displayValue}"), txtVdc, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 48, Short.MAX_VALUE))
            .addComponent(txtVdc)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVdc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel15.add(jPanel11);

        jPanel12.setName(bundle.getString("OwnershipPanel.jPanel12.name")); // NOI18N

        jLabel5.setText(bundle.getString("OwnershipPanel.jLabel5.text")); // NOI18N
        jLabel5.setName(bundle.getString("OwnershipPanel.jLabel5.name")); // NOI18N

        txtMothtype.setEditable(false);
        txtMothtype.setName(bundle.getString("OwnershipPanel.txtMothtype.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${loc.moth.mothType.mothTypeName}"), txtMothtype, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtMothtype, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMothtype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel15.add(jPanel12);

        jPanel13.setName(bundle.getString("OwnershipPanel.jPanel13.name")); // NOI18N

        jLabel6.setText(bundle.getString("OwnershipPanel.jLabel6.text")); // NOI18N
        jLabel6.setName(bundle.getString("OwnershipPanel.jLabel6.name")); // NOI18N

        txtMothNumber.setEditable(false);
        txtMothNumber.setName(bundle.getString("OwnershipPanel.txtMothNumber.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${loc.moth.mothlujNumber}"), txtMothNumber, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtMothNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMothNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel15.add(jPanel13);

        jPanel14.setName(bundle.getString("OwnershipPanel.jPanel14.name")); // NOI18N

        jLabel7.setText(bundle.getString("OwnershipPanel.jLabel7.text")); // NOI18N
        jLabel7.setName(bundle.getString("OwnershipPanel.jLabel7.name")); // NOI18N

        txtPageNumber.setEditable(false);
        txtPageNumber.setName(bundle.getString("OwnershipPanel.txtPageNumber.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${loc.pageNumber}"), txtPageNumber, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtPageNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPageNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel15.add(jPanel14);

        javax.swing.GroupLayout pnlLocDetailsLayout = new javax.swing.GroupLayout(pnlLocDetails);
        pnlLocDetails.setLayout(pnlLocDetailsLayout);
        pnlLocDetailsLayout.setHorizontalGroup(
            pnlLocDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlLocDetailsLayout.setVerticalGroup(
            pnlLocDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLocDetailsLayout.createSequentialGroup()
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlLocSearch.setName(bundle.getString("OwnershipPanel.pnlLocSearch.name")); // NOI18N

        locSearchCreatePanel.setName(bundle.getString("OwnershipPanel.locSearchCreatePanel.name")); // NOI18N

        javax.swing.GroupLayout pnlLocSearchLayout = new javax.swing.GroupLayout(pnlLocSearch);
        pnlLocSearch.setLayout(pnlLocSearchLayout);
        pnlLocSearchLayout.setHorizontalGroup(
            pnlLocSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(locSearchCreatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        pnlLocSearchLayout.setVerticalGroup(
            pnlLocSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(locSearchCreatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel2.setName("jPanel2"); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        btnAddOwner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        btnAddOwner.setText(bundle.getString("OwnershipPanel.btnAddOwner.text")); // NOI18N
        btnAddOwner.setName("btnAddOwner"); // NOI18N
        btnAddOwner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddOwnerActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAddOwner);

        btnRemoveOwner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemoveOwner.setText(bundle.getString("OwnershipPanel.btnRemoveOwner.text")); // NOI18N
        btnRemoveOwner.setName("btnRemoveOwner"); // NOI18N
        btnRemoveOwner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveOwnerActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRemoveOwner);

        btnEditOwner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        btnEditOwner.setText(bundle.getString("OwnershipPanel.btnEditOwner.text")); // NOI18N
        btnEditOwner.setName("btnEditOwner"); // NOI18N
        btnEditOwner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditOwnerActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEditOwner);

        btnViewOwner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/view.png"))); // NOI18N
        btnViewOwner.setText(bundle.getString("OwnershipPanel.btnViewOwner.text")); // NOI18N
        btnViewOwner.setFocusable(false);
        btnViewOwner.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnViewOwner.setName("btnViewOwner"); // NOI18N
        btnViewOwner.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnViewOwner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewOwnerActionPerformed(evt);
            }
        });
        jToolBar1.add(btnViewOwner);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(300, 80));
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tableRightholders.setComponentPopupMenu(popUpOwners);
        tableRightholders.setName("tableRightholders"); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${filteredRightHolderList}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, eLProperty, tableRightholders);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fullName}"));
        columnBinding.setColumnName("Full Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${selectedRightHolder}"), tableRightholders, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        tableRightholders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableRightholdersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableRightholders);
        tableRightholders.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("OwnershipPanel.tableRightholders.columnModel.title0")); // NOI18N

        groupPanel1.setName("groupPanel1"); // NOI18N
        groupPanel1.setTitleText(bundle.getString("OwnershipPanel.groupPanel1.titleText")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(groupPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(groupPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
        );

        jPanel3.setName("jPanel3"); // NOI18N

        jPanel6.setName(bundle.getString("OwnershipPanel.jPanel6.name")); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel13.setText(bundle.getString("OwnershipPanel.jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        txtRegDatetime.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        txtRegDatetime.setName("txtRegDatetime"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${registrationDate}"), txtRegDatetime, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addGap(0, 8, Short.MAX_VALUE))
            .addComponent(txtRegDatetime)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRegDatetime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel5.setName(bundle.getString("OwnershipPanel.jPanel5.name")); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel2.setText(bundle.getString("OwnershipPanel.jLabel2.text")); // NOI18N
        jLabel2.setName(bundle.getString("OwnershipPanel.jLabel2.name")); // NOI18N

        cbxOwnerType.setName(bundle.getString("OwnershipPanel.cbxOwnerType.name")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${ownerTypes}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, ownerTypeListBean, eLProperty, cbxOwnerType);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${ownerType}"), cbxOwnerType, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 40, Short.MAX_VALUE))
            .addComponent(cbxOwnerType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxOwnerType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel7.setName(bundle.getString("OwnershipPanel.jPanel7.name")); // NOI18N

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel15.setText(bundle.getString("OwnershipPanel.jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        txtNotationText.setName("txtNotationText"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${notation.notationText}"), txtNotationText, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel15)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtNotationText)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNotationText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel9.setName(bundle.getString("OwnershipPanel.jPanel9.name")); // NOI18N

        cbxRrrTypes.setName(bundle.getString("OwnershipPanel.cbxRrrTypes.name")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${rrrTypeBeanList}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrTypes, eLProperty, cbxRrrTypes);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${rrrType}"), cbxRrrTypes, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel10.setText(bundle.getString("OwnershipPanel.jLabel10.text")); // NOI18N
        jLabel10.setName(bundle.getString("OwnershipPanel.jLabel10.name")); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbxRrrTypes, 0, 123, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxRrrTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.setName(bundle.getString("OwnershipPanel.jPanel4.name")); // NOI18N

        cbxShareType.setName(bundle.getString("OwnershipPanel.cbxShareType.name_1")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${shareTypes}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, shareTypeListBean, eLProperty, cbxShareType);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, rrrBean, org.jdesktop.beansbinding.ELProperty.create("${shareType}"), cbxShareType, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel9.setText(bundle.getString("OwnershipPanel.jLabel9.text_1")); // NOI18N
        jLabel9.setName(bundle.getString("OwnershipPanel.jLabel9.name_1")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbxShareType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(0, 41, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxShareType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlLocDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlLocSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(pnlLocDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlLocSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setName("jPanel1"); // NOI18N

        groupPanel2.setName("groupPanel2"); // NOI18N
        groupPanel2.setTitleText(bundle.getString("OwnershipPanel.groupPanel2.titleText")); // NOI18N

        documentsPanel.setName("documentsPanel"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(groupPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(documentsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(groupPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(documentsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void tableRightholdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableRightholdersMouseClicked
        if (evt.getClickCount() > 1) {
            viewOwner();
        }
    }//GEN-LAST:event_tableRightholdersMouseClicked

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveRrr();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnAddOwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOwnerActionPerformed
        addOwner();
    }//GEN-LAST:event_btnAddOwnerActionPerformed

    private void btnRemoveOwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveOwnerActionPerformed
        removeOwner();
    }//GEN-LAST:event_btnRemoveOwnerActionPerformed

    private void btnEditOwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditOwnerActionPerformed
        editOwner();
    }//GEN-LAST:event_btnEditOwnerActionPerformed

    private void btnViewOwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewOwnerActionPerformed
        viewOwner();
    }//GEN-LAST:event_btnViewOwnerActionPerformed

    private void menuAddOwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAddOwnerActionPerformed
        addOwner();
    }//GEN-LAST:event_menuAddOwnerActionPerformed

    private void menuRemoveOwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRemoveOwnerActionPerformed
        removeOwner();
    }//GEN-LAST:event_menuRemoveOwnerActionPerformed

    private void menuEditOwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditOwnerActionPerformed
        editOwner();
    }//GEN-LAST:event_menuEditOwnerActionPerformed

    private void menuViewOwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuViewOwnerActionPerformed
        viewOwner();
    }//GEN-LAST:event_menuViewOwnerActionPerformed

    private void btnEditLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditLocActionPerformed
        showLocSearch(true);
    }//GEN-LAST:event_btnEditLocActionPerformed

    private void btnRevertLocToCurrentStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRevertLocToCurrentStateActionPerformed
        revertLocToCurrentState();
    }//GEN-LAST:event_btnRevertLocToCurrentStateActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddOwner;
    private javax.swing.JButton btnEditLoc;
    private javax.swing.JButton btnEditOwner;
    private javax.swing.JButton btnRemoveOwner;
    private javax.swing.JButton btnRevertLocToCurrentState;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnViewOwner;
    private javax.swing.JComboBox cbxOwnerType;
    private javax.swing.JComboBox cbxRrrTypes;
    private javax.swing.JComboBox cbxShareType;
    private org.sola.clients.swing.ui.source.DocumentsManagementPanel documentsPanel;
    private javax.swing.Box.Filler filler1;
    private org.sola.clients.swing.ui.GroupPanel groupPanel1;
    private org.sola.clients.swing.ui.GroupPanel groupPanel2;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JLabel lblStatus;
    private org.sola.clients.swing.ui.administrative.LocSearchCreatePanel locSearchCreatePanel;
    private javax.swing.JMenuItem menuAddOwner;
    private javax.swing.JMenuItem menuEditOwner;
    private javax.swing.JMenuItem menuRemoveOwner;
    private javax.swing.JMenuItem menuViewOwner;
    private org.sola.clients.beans.referencedata.OwnerTypeListBean ownerTypeListBean;
    private javax.swing.JPanel pnlLocDetails;
    private javax.swing.JPanel pnlLocSearch;
    private javax.swing.JPopupMenu popUpOwners;
    private org.sola.clients.beans.administrative.RrrBean rrrBean;
    private org.sola.clients.beans.referencedata.RrrTypeListBean rrrTypes;
    private org.sola.clients.beans.referencedata.OwnershipTypeListBean shareTypeListBean;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableRightholders;
    private javax.swing.JTextField txtDistrict;
    private javax.swing.JTextField txtMothNumber;
    private javax.swing.JTextField txtMothtype;
    private javax.swing.JTextField txtNotationText;
    private javax.swing.JTextField txtPageNumber;
    private javax.swing.JFormattedTextField txtRegDatetime;
    private javax.swing.JTextField txtVdc;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}