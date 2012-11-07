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
package org.sola.clients.swing.desktop.application;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.sola.clients.beans.administrative.BaUnitBean;
import org.sola.clients.beans.administrative.BaUnitSearchResultBean;
import org.sola.clients.beans.administrative.BaUnitSearchResultListBean;
import org.sola.clients.beans.application.ApplicationBean;
import org.sola.clients.beans.application.ApplicationServiceBean;
import org.sola.clients.beans.security.SecurityBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.desktop.administrative.PropertyPanel;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.common.RolesConstants;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;
import org.sola.services.common.StatusConstants;

/**
 * Shows list of application properties and properties, affected by transaction.
 */
public class PropertyTransactionForm extends ContentPanel {

    public static final String SELECTED_APP_PROPERTY = "selectedAppProperty";
    private ApplicationBean appBean;
    private ApplicationServiceBean appService;
    private BaUnitSearchResultBean selectedAppProperty;
    private boolean readOnly;

    /**
     * Default constructor.
     */
    public PropertyTransactionForm() {
        this(null, null, true);
    }

    /**
     * Form constructor with {@link ApplicationBean} and {@link ApplicationServiceBean}
     * parameters.
     */
    public PropertyTransactionForm(ApplicationBean appBean, ApplicationServiceBean appService, boolean readOnly) {
        if (appBean == null) {
            this.appBean = new ApplicationBean();
        } else {
            this.appBean = appBean;
        }
        this.readOnly = readOnly;
        if(!readOnly){
            this.readOnly = !SecurityBean.isInRole(RolesConstants.ADMINISTRATIVE_BA_UNIT_SAVE);
        }
        this.appService = appService;
        initComponents();
        postInit();
    }

    private void postInit() {
        loadProperties();
        if (appService != null && appService.getRequestType() != null) {
            headerPanel1.setTitleText(String.format(headerPanel1.getTitleText(),
                    appService.getRequestType().getDisplayValue()));
        } else {
            headerPanel1.setTitleText(String.format(headerPanel1.getTitleText(),
                    MessageUtility.getLocalizedMessageText(ClientMessage.GENERAL_LABELS_NEW)));
        }

        baUnitList.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(BaUnitSearchResultListBean.SELECTED_BAUNIT_SEARCH_RESULT_PROPERTY)) {
                    customizeTransactionPropertyButtons();
                }
            }
        });
        addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(SELECTED_APP_PROPERTY)) {
                    customizeAppPropertyButtons();
                }
            }
        });
        customizeAppPropertyButtons();
        customizeTransactionPropertyButtons();
    }

    private void loadProperties() {
        if (appService != null) {
            SolaTask t = new SolaTask<Void, Void>() {

                @Override
                public Void doTask() {
                    setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_GETTING_PROPERTIES));
                    baUnitList.searchAllBaUnitsByService(appService.getId());
                    return null;
                }
            };
            TaskManager.getInstance().runTask(t);
        }
    }

    private void customizeAppPropertyButtons() {
        boolean enabled = selectedAppProperty != null;
        
        btnViewAppProperty.setEnabled(enabled);
        btnVaryAppProperty.setEnabled(!readOnly && enabled);
        btnSplitAppProperty.setEnabled(!readOnly && enabled);
        menuViewAppProperty.setEnabled(btnViewAppProperty.isEnabled());
        menuChangeAppProperty.setEnabled(btnVaryAppProperty.isEnabled());
        menuSplitAppProperty.setEnabled(btnSplitAppProperty.isEnabled());
    }

    private void customizeTransactionPropertyButtons() {
        boolean enabled = baUnitList.getSelectedBaUnitSearchResult() != null;
        
        boolean pending = false;
        if (enabled) {
            pending = baUnitList.getSelectedBaUnitSearchResult() != null
                    && baUnitList.getSelectedBaUnitSearchResult().getStatusCode().equals(StatusConstants.PENDING);
        }

        btnViewTransactionedProperty.setEnabled(enabled);
        btnAddTransactionedProperty.setEnabled(!readOnly);
        btnEditTransactionedProperty.setEnabled(enabled && !readOnly);
        btnRemoveTransactionedProperty.setEnabled(enabled && !readOnly && pending);
        btnRefresh.setEnabled(!readOnly);

        menuViewTransactionProperty.setEnabled(btnViewTransactionedProperty.isEnabled());
        menuAddTransactionProperty.setEnabled(btnAddTransactionedProperty.isEnabled());
        menuEditTransactionProperty.setEnabled(btnEditTransactionedProperty.isEnabled());
        menuRemoveTransactionProperty.setEnabled(btnRemoveTransactionedProperty.isEnabled());
        menuRefresh.setEnabled(btnRefresh.isEnabled());
    }

    public ApplicationBean getAppBean() {
        return appBean;
    }

    public BaUnitSearchResultBean getSelectedAppProperty() {
        return selectedAppProperty;
    }

    public void setSelectedAppProperty(BaUnitSearchResultBean selectedAppProperty) {
        BaUnitSearchResultBean oldValue = this.selectedAppProperty;
        this.selectedAppProperty = selectedAppProperty;
        firePropertyChange(SELECTED_APP_PROPERTY, oldValue, this.selectedAppProperty);
    }

    private void openPropertyForm(final BaUnitSearchResultBean baUnit, final boolean readOnly, final boolean splitting) {
        SolaTask t = new SolaTask<Void, Void>() {

            PropertyPanel propertyPnl;
            String baUnitId = null;

            @Override
            public Void doTask() {

                if (baUnit != null) {
                    baUnitId = baUnit.getId();
                }
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_PROPERTY));
                ApplicationBean applicationBean = appBean;
                if (readOnly) {
                    propertyPnl = new PropertyPanel(baUnitId);
                } else {
                    propertyPnl = new PropertyPanel(applicationBean, appService, baUnitId, readOnly);
                }
                getMainContentPanel().addPanel(propertyPnl, getThis().getId(), propertyPnl.getId(), true);
                return null;
            }

            @Override
            protected void taskDone() {
                if (splitting && !readOnly) {
                    propertyPnl.showNewTitleWizard(false);
                }
            }
        };
        TaskManager.getInstance().runTask(t);
    }

    private PropertyTransactionForm getThis() {
        return this;
    }

    private void removeBaUnit(BaUnitSearchResultBean baUnit) {
        if (baUnit != null) {
            if (MessageUtility.displayMessage(ClientMessage.CONFIRM_DELETE_RECORD) == MessageUtility.BUTTON_ONE) {
                BaUnitBean.deletePendingBaUnit(baUnit.getId());
                if (appService != null) {
                    loadProperties();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        baUnitList = new org.sola.clients.beans.administrative.BaUnitSearchResultListBean();
        popupAppProperties = new javax.swing.JPopupMenu();
        menuViewAppProperty = new javax.swing.JMenuItem();
        menuChangeAppProperty = new javax.swing.JMenuItem();
        menuSplitAppProperty = new javax.swing.JMenuItem();
        popupTransactionProperties = new javax.swing.JPopupMenu();
        menuViewTransactionProperty = new javax.swing.JMenuItem();
        menuAddTransactionProperty = new javax.swing.JMenuItem();
        menuEditTransactionProperty = new javax.swing.JMenuItem();
        menuRemoveTransactionProperty = new javax.swing.JMenuItem();
        menuRefresh = new javax.swing.JMenuItem();
        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnViewAppProperty = new javax.swing.JButton();
        btnVaryAppProperty = new javax.swing.JButton();
        btnSplitAppProperty = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAppProperty = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        groupPanel1 = new org.sola.clients.swing.ui.GroupPanel();
        jPanel2 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        btnViewTransactionedProperty = new javax.swing.JButton();
        btnAddTransactionedProperty = new javax.swing.JButton();
        btnEditTransactionedProperty = new javax.swing.JButton();
        btnRemoveTransactionedProperty = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableTransactionProperty = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        groupPanel2 = new org.sola.clients.swing.ui.GroupPanel();

        menuViewAppProperty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/view.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/application/Bundle"); // NOI18N
        menuViewAppProperty.setText(bundle.getString("PropertyTransactionForm.menuViewAppProperty.text")); // NOI18N
        menuViewAppProperty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewAppPropertyActionPerformed(evt);
            }
        });
        popupAppProperties.add(menuViewAppProperty);

        menuChangeAppProperty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        menuChangeAppProperty.setText(bundle.getString("PropertyTransactionForm.menuChangeAppProperty.text")); // NOI18N
        menuChangeAppProperty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuChangeAppPropertyActionPerformed(evt);
            }
        });
        popupAppProperties.add(menuChangeAppProperty);

        menuSplitAppProperty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/change-share.png"))); // NOI18N
        menuSplitAppProperty.setText(bundle.getString("PropertyTransactionForm.menuSplitAppProperty.text")); // NOI18N
        menuSplitAppProperty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSplitAppPropertyActionPerformed(evt);
            }
        });
        popupAppProperties.add(menuSplitAppProperty);

        menuViewTransactionProperty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/view.png"))); // NOI18N
        menuViewTransactionProperty.setText(bundle.getString("PropertyTransactionForm.menuViewTransactionProperty.text")); // NOI18N
        menuViewTransactionProperty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewTransactionPropertyActionPerformed(evt);
            }
        });
        popupTransactionProperties.add(menuViewTransactionProperty);

        menuAddTransactionProperty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        menuAddTransactionProperty.setText(bundle.getString("PropertyTransactionForm.menuAddTransactionProperty.text")); // NOI18N
        menuAddTransactionProperty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddTransactionPropertyActionPerformed(evt);
            }
        });
        popupTransactionProperties.add(menuAddTransactionProperty);

        menuEditTransactionProperty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        menuEditTransactionProperty.setText(bundle.getString("PropertyTransactionForm.menuEditTransactionProperty.text")); // NOI18N
        menuEditTransactionProperty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditTransactionPropertyActionPerformed(evt);
            }
        });
        popupTransactionProperties.add(menuEditTransactionProperty);

        menuRemoveTransactionProperty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        menuRemoveTransactionProperty.setText(bundle.getString("PropertyTransactionForm.menuRemoveTransactionProperty.text")); // NOI18N
        menuRemoveTransactionProperty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRemoveTransactionPropertyActionPerformed(evt);
            }
        });
        popupTransactionProperties.add(menuRemoveTransactionProperty);

        menuRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/refresh.png"))); // NOI18N
        menuRefresh.setText(bundle.getString("PropertyTransactionForm.menuRefresh.text")); // NOI18N
        menuRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRefreshActionPerformed(evt);
            }
        });
        popupTransactionProperties.add(menuRefresh);

        setHeaderPanel(headerPanel1);

        headerPanel1.setTitleText(bundle.getString("PropertyTransactionForm.headerPanel1.titleText")); // NOI18N

        jPanel3.setLayout(new java.awt.GridLayout(2, 1, 0, 15));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnViewAppProperty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/view.png"))); // NOI18N
        btnViewAppProperty.setText(bundle.getString("PropertyTransactionForm.btnViewAppProperty.text")); // NOI18N
        btnViewAppProperty.setFocusable(false);
        btnViewAppProperty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewAppPropertyActionPerformed(evt);
            }
        });
        jToolBar1.add(btnViewAppProperty);

        btnVaryAppProperty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        btnVaryAppProperty.setText(bundle.getString("PropertyTransactionForm.btnVaryAppProperty.text")); // NOI18N
        btnVaryAppProperty.setFocusable(false);
        btnVaryAppProperty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVaryAppPropertyActionPerformed(evt);
            }
        });
        jToolBar1.add(btnVaryAppProperty);

        btnSplitAppProperty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/change-share.png"))); // NOI18N
        btnSplitAppProperty.setText(bundle.getString("PropertyTransactionForm.btnSplitAppProperty.text")); // NOI18N
        btnSplitAppProperty.setFocusable(false);
        btnSplitAppProperty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSplitAppPropertyActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSplitAppProperty);

        tableAppProperty.setComponentPopupMenu(popupAppProperties);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${appBean.filteredPropertyList}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, tableAppProperty);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${propertyIdCode}"));
        columnBinding.setColumnName("Property Id Code");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${vdc.displayValue}"));
        columnBinding.setColumnName("Vdc.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${wardNo}"));
        columnBinding.setColumnName("Ward No");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mapNumber}"));
        columnBinding.setColumnName("Map Number");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${parcelNo}"));
        columnBinding.setColumnName("Parcel No");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mothNo}"));
        columnBinding.setColumnName("Moth No");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${panaNo}"));
        columnBinding.setColumnName("Pana No");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${registrationStatus.displayValue}"));
        columnBinding.setColumnName("Registration Status.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${selectedAppProperty}"), tableAppProperty, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(tableAppProperty);
        tableAppProperty.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("PropertyTransactionForm.tableAppProperty.columnModel.title0_1")); // NOI18N
        tableAppProperty.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("PropertyTransactionForm.tableAppProperty.columnModel.title1_1")); // NOI18N
        tableAppProperty.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("PropertyTransactionForm.tableAppProperty.columnModel.title2_1")); // NOI18N
        tableAppProperty.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("PropertyTransactionForm.tableAppProperty.columnModel.title3_1")); // NOI18N
        tableAppProperty.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("PropertyTransactionForm.tableAppProperty.columnModel.title4_1")); // NOI18N
        tableAppProperty.getColumnModel().getColumn(5).setHeaderValue(bundle.getString("PropertyTransactionForm.tableAppProperty.columnModel.title5")); // NOI18N
        tableAppProperty.getColumnModel().getColumn(6).setHeaderValue(bundle.getString("PropertyTransactionForm.tableAppProperty.columnModel.title6")); // NOI18N
        tableAppProperty.getColumnModel().getColumn(7).setHeaderValue(bundle.getString("PropertyTransactionForm.tableAppProperty.columnModel.title7")); // NOI18N

        groupPanel1.setTitleText(bundle.getString("PropertyTransactionForm.groupPanel1.titleText")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(groupPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(groupPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel1);

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        btnViewTransactionedProperty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/view.png"))); // NOI18N
        btnViewTransactionedProperty.setText(bundle.getString("PropertyTransactionForm.btnViewTransactionedProperty.text")); // NOI18N
        btnViewTransactionedProperty.setFocusable(false);
        btnViewTransactionedProperty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewTransactionedPropertyActionPerformed(evt);
            }
        });
        jToolBar2.add(btnViewTransactionedProperty);

        btnAddTransactionedProperty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        btnAddTransactionedProperty.setText(bundle.getString("PropertyTransactionForm.btnAddTransactionedProperty.text")); // NOI18N
        btnAddTransactionedProperty.setFocusable(false);
        btnAddTransactionedProperty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTransactionedPropertyActionPerformed(evt);
            }
        });
        jToolBar2.add(btnAddTransactionedProperty);

        btnEditTransactionedProperty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        btnEditTransactionedProperty.setText(bundle.getString("PropertyTransactionForm.btnEditTransactionedProperty.text")); // NOI18N
        btnEditTransactionedProperty.setFocusable(false);
        btnEditTransactionedProperty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditTransactionedPropertyActionPerformed(evt);
            }
        });
        jToolBar2.add(btnEditTransactionedProperty);

        btnRemoveTransactionedProperty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemoveTransactionedProperty.setText(bundle.getString("PropertyTransactionForm.btnRemoveTransactionedProperty.text")); // NOI18N
        btnRemoveTransactionedProperty.setFocusable(false);
        btnRemoveTransactionedProperty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveTransactionedPropertyActionPerformed(evt);
            }
        });
        jToolBar2.add(btnRemoveTransactionedProperty);

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/refresh.png"))); // NOI18N
        btnRefresh.setText(bundle.getString("PropertyTransactionForm.btnRefresh.text")); // NOI18N
        btnRefresh.setFocusable(false);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jToolBar2.add(btnRefresh);

        tableTransactionProperty.setComponentPopupMenu(popupTransactionProperties);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${baUnitSearchResults}");
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, baUnitList, eLProperty, tableTransactionProperty);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${propertyIdCode}"));
        columnBinding.setColumnName("Property Id Code");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${vdc.displayValue}"));
        columnBinding.setColumnName("Vdc.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${wardNo}"));
        columnBinding.setColumnName("Ward No");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mapNumber}"));
        columnBinding.setColumnName("Map Number");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${parcelNo}"));
        columnBinding.setColumnName("Parcel No");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mothNo}"));
        columnBinding.setColumnName("Moth No");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${panaNo}"));
        columnBinding.setColumnName("Pana No");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${actionDescription}"));
        columnBinding.setColumnName("Action Description");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${registrationStatus.displayValue}"));
        columnBinding.setColumnName("Registration Status.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, baUnitList, org.jdesktop.beansbinding.ELProperty.create("${selectedBaUnitSearchResult}"), tableTransactionProperty, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(tableTransactionProperty);
        tableTransactionProperty.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("PropertyTransactionForm.tableTransactionProperty.columnModel.title0_1")); // NOI18N
        tableTransactionProperty.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("PropertyTransactionForm.tableTransactionProperty.columnModel.title1_1")); // NOI18N
        tableTransactionProperty.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("PropertyTransactionForm.tableTransactionProperty.columnModel.title2_1")); // NOI18N
        tableTransactionProperty.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("PropertyTransactionForm.tableTransactionProperty.columnModel.title3_1")); // NOI18N
        tableTransactionProperty.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("PropertyTransactionForm.tableTransactionProperty.columnModel.title4_1")); // NOI18N
        tableTransactionProperty.getColumnModel().getColumn(5).setHeaderValue(bundle.getString("PropertyTransactionForm.tableTransactionProperty.columnModel.title5_1")); // NOI18N
        tableTransactionProperty.getColumnModel().getColumn(6).setHeaderValue(bundle.getString("PropertyTransactionForm.tableTransactionProperty.columnModel.title6")); // NOI18N
        tableTransactionProperty.getColumnModel().getColumn(7).setHeaderValue(bundle.getString("PropertyTransactionForm.tableTransactionProperty.columnModel.title7")); // NOI18N
        tableTransactionProperty.getColumnModel().getColumn(8).setHeaderValue(bundle.getString("PropertyTransactionForm.tableTransactionProperty.columnModel.title8")); // NOI18N

        groupPanel2.setTitleText(bundle.getString("PropertyTransactionForm.groupPanel2.titleText")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
            .addComponent(groupPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(groupPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewAppPropertyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewAppPropertyActionPerformed
        openPropertyForm(selectedAppProperty, true, false);
    }//GEN-LAST:event_btnViewAppPropertyActionPerformed

    private void btnViewTransactionedPropertyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewTransactionedPropertyActionPerformed
        openPropertyForm(baUnitList.getSelectedBaUnitSearchResult(), true, false);
    }//GEN-LAST:event_btnViewTransactionedPropertyActionPerformed

    private void btnVaryAppPropertyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVaryAppPropertyActionPerformed
        openPropertyForm(selectedAppProperty, readOnly, false);
    }//GEN-LAST:event_btnVaryAppPropertyActionPerformed

    private void btnSplitAppPropertyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSplitAppPropertyActionPerformed
        openPropertyForm(null, readOnly, true);
    }//GEN-LAST:event_btnSplitAppPropertyActionPerformed

    private void menuViewAppPropertyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuViewAppPropertyActionPerformed
        openPropertyForm(selectedAppProperty, true, false);
    }//GEN-LAST:event_menuViewAppPropertyActionPerformed

    private void menuChangeAppPropertyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuChangeAppPropertyActionPerformed
        openPropertyForm(selectedAppProperty, readOnly, false);
    }//GEN-LAST:event_menuChangeAppPropertyActionPerformed

    private void menuSplitAppPropertyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSplitAppPropertyActionPerformed
        openPropertyForm(null, readOnly, true);
    }//GEN-LAST:event_menuSplitAppPropertyActionPerformed

    private void btnAddTransactionedPropertyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTransactionedPropertyActionPerformed
        openPropertyForm(null, readOnly, false);
    }//GEN-LAST:event_btnAddTransactionedPropertyActionPerformed

    private void btnEditTransactionedPropertyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditTransactionedPropertyActionPerformed
        openPropertyForm(baUnitList.getSelectedBaUnitSearchResult(), readOnly, false);
    }//GEN-LAST:event_btnEditTransactionedPropertyActionPerformed

    private void menuViewTransactionPropertyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuViewTransactionPropertyActionPerformed
        openPropertyForm(baUnitList.getSelectedBaUnitSearchResult(), true, false);
    }//GEN-LAST:event_menuViewTransactionPropertyActionPerformed

    private void menuAddTransactionPropertyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAddTransactionPropertyActionPerformed
        openPropertyForm(null, readOnly, false);
    }//GEN-LAST:event_menuAddTransactionPropertyActionPerformed

    private void menuEditTransactionPropertyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditTransactionPropertyActionPerformed
        openPropertyForm(baUnitList.getSelectedBaUnitSearchResult(), readOnly, false);
    }//GEN-LAST:event_menuEditTransactionPropertyActionPerformed

    private void btnRemoveTransactionedPropertyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveTransactionedPropertyActionPerformed
        removeBaUnit(baUnitList.getSelectedBaUnitSearchResult());
    }//GEN-LAST:event_btnRemoveTransactionedPropertyActionPerformed

    private void menuRemoveTransactionPropertyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRemoveTransactionPropertyActionPerformed
        removeBaUnit(baUnitList.getSelectedBaUnitSearchResult());
    }//GEN-LAST:event_menuRemoveTransactionPropertyActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadProperties();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void menuRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRefreshActionPerformed
        loadProperties();
    }//GEN-LAST:event_menuRefreshActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sola.clients.beans.administrative.BaUnitSearchResultListBean baUnitList;
    private javax.swing.JButton btnAddTransactionedProperty;
    private javax.swing.JButton btnEditTransactionedProperty;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRemoveTransactionedProperty;
    private javax.swing.JButton btnSplitAppProperty;
    private javax.swing.JButton btnVaryAppProperty;
    private javax.swing.JButton btnViewAppProperty;
    private javax.swing.JButton btnViewTransactionedProperty;
    private org.sola.clients.swing.ui.GroupPanel groupPanel1;
    private org.sola.clients.swing.ui.GroupPanel groupPanel2;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JMenuItem menuAddTransactionProperty;
    private javax.swing.JMenuItem menuChangeAppProperty;
    private javax.swing.JMenuItem menuEditTransactionProperty;
    private javax.swing.JMenuItem menuRefresh;
    private javax.swing.JMenuItem menuRemoveTransactionProperty;
    private javax.swing.JMenuItem menuSplitAppProperty;
    private javax.swing.JMenuItem menuViewAppProperty;
    private javax.swing.JMenuItem menuViewTransactionProperty;
    private javax.swing.JPopupMenu popupAppProperties;
    private javax.swing.JPopupMenu popupTransactionProperties;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableAppProperty;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableTransactionProperty;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
