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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;
import org.sola.clients.beans.cadastre.CadastreObjectBean;
import org.sola.clients.beans.cadastre.ParcelSearchResultListBean;
import org.sola.clients.beans.referencedata.DistrictListBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Allows to search parcels and manage them.
 */
public class ParcelSearchPanel extends JPanel {

    public static final String SELECT_PARCEL_PROPERTY = "selectParcel";
    private CadastreObjectBean parcel;

    /**
     * Creates new form ParcelSearchPanel
     */
    public ParcelSearchPanel() {
        initComponents();

        parcelSearchResultListBean.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(ParcelSearchResultListBean.SELECTED_PARCEL_SEARCH_RESULT)) {
                    customizeParcelButtons();
                }
            }
        });
        customizeParcelButtons();
        postInit();
    }

    public boolean isShowSelectButton() {
        return btnSelect.isVisible();
    }

    public void setShowSelectButton(boolean isVisible) {
        btnSelect.setVisible(isVisible);
        menuSelect.setVisible(isVisible);
    }

    /**
     * Enables or disables Parcel management buttons, based on security rights.
     */
    private void customizeParcelButtons() {
        boolean selected = parcelSearchResultListBean.getSelectedParcelSearchResult() != null;
        boolean enabled = selected == true
                && parcelSearchResultListBean.getSelectedParcelSearchResult().checkAccessByOffice();
        menuSelect.setEnabled(btnSelect.isEnabled());
        btnSelect.setEnabled(selected);
        menuSelect.setEnabled(btnSelect.isEnabled());
    }

    /**
     * Searches Parcels with given criteria.
     */
    private void search() {
        SolaTask t = new SolaTask<Void, Void>() {
            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_PARCEL_SEARCHING));
                parcelSearchResultListBean.search(parcelSearchParamsBean);
                return null;
            }

            @Override
            public void taskDone() {
                if (parcelSearchResultListBean.getParcelSearchResults().size() > 100) {
                    MessageUtility.displayMessage(ClientMessage.SEARCH_TOO_MANY_RESULTS, new String[]{"100"});
                } else if (parcelSearchResultListBean.getParcelSearchResults().size() < 1) {
                    MessageUtility.displayMessage(ClientMessage.SEARCH_NO_RESULTS);
                }
            }
        };
        TaskManager.getInstance().runTask(t);
    }

    private void fireParcelEvent(String propertyName) {
        if (parcelSearchResultListBean.getSelectedParcelSearchResult() != null) {
            firePropertyChange(propertyName, null,
                    parcel = CadastreObjectBean.getParcel(parcelSearchResultListBean.getSelectedParcelSearchResult().getId()));
        }
    }

    public CadastreObjectBean getParcel() {
        return parcel;
    }

    private void selectParcel() {
        fireParcelEvent(SELECT_PARCEL_PROPERTY);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        popupParcels = new javax.swing.JPopupMenu();
        menuSelect = new javax.swing.JMenuItem();
        districtListBean = new org.sola.clients.beans.referencedata.DistrictListBean();
        vdcListBean = new org.sola.clients.beans.referencedata.VdcListBean();
        parcelSearchParamsBean = new org.sola.clients.beans.cadastre.ParcelSearchParamsBean();
        parcelSearchResultListBean = new org.sola.clients.beans.cadastre.ParcelSearchResultListBean();
        scrlSearchPanel = new javax.swing.JScrollPane();
        pnlSearch = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableParcelSearchResults = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        jToolBar1 = new javax.swing.JToolBar();
        btnSelect = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cbxDistrict = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cbxVdc = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txtWardNo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtParcelNo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();

        popupParcels.setName("popupParcels"); // NOI18N

        menuSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/select.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/ui/party/Bundle"); // NOI18N
        menuSelect.setText(bundle.getString("PartySearchPanel.menuSelect.text")); // NOI18N
        menuSelect.setName("menuSelect"); // NOI18N
        menuSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSelectActionPerformed(evt);
            }
        });
        popupParcels.add(menuSelect);

        scrlSearchPanel.setBorder(null);
        scrlSearchPanel.setName("scrlSearchPanel"); // NOI18N

        pnlSearch.setMinimumSize(new java.awt.Dimension(300, 300));
        pnlSearch.setName("pnlSearch"); // NOI18N
        pnlSearch.setPreferredSize(new java.awt.Dimension(300, 300));

        jPanel7.setName("jPanel7"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tableParcelSearchResults.setComponentPopupMenu(popupParcels);
        tableParcelSearchResults.setName("tableParcelSearchResults"); // NOI18N
        tableParcelSearchResults.getTableHeader().setReorderingAllowed(false);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${parcelSearchResults}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, parcelSearchResultListBean, eLProperty, tableParcelSearchResults);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mapsheetId}"));
        columnBinding.setColumnName("Mapsheet Id");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${firstName}"));
        columnBinding.setColumnName("First Name");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${lastName}"));
        columnBinding.setColumnName("Last Name");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${parcelno}"));
        columnBinding.setColumnName("Parcelno");
        columnBinding.setColumnClass(Integer.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, parcelSearchResultListBean, org.jdesktop.beansbinding.ELProperty.create("${selectedParcelSearchResult}"), tableParcelSearchResults, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        tableParcelSearchResults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableParcelSearchResultsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableParcelSearchResults);
        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("org/sola/clients/swing/ui/cadastre/Bundle"); // NOI18N
        tableParcelSearchResults.getColumnModel().getColumn(0).setHeaderValue(bundle1.getString("ParcelSearchPanel.tableParcelSearchResults.columnModel.title0")); // NOI18N
        tableParcelSearchResults.getColumnModel().getColumn(1).setHeaderValue(bundle1.getString("ParcelSearchPanel.tableParcelSearchResults.columnModel.title1")); // NOI18N
        tableParcelSearchResults.getColumnModel().getColumn(2).setHeaderValue(bundle1.getString("ParcelSearchPanel.tableParcelSearchResults.columnModel.title2")); // NOI18N
        tableParcelSearchResults.getColumnModel().getColumn(3).setHeaderValue(bundle1.getString("ParcelSearchPanel.tableParcelSearchResults.columnModel.title3")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        btnSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/select.png"))); // NOI18N
        btnSelect.setText(bundle.getString("PartySearchPanel.btnSelect.text")); // NOI18N
        btnSelect.setFocusable(false);
        btnSelect.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSelect.setName("btnSelect"); // NOI18N
        btnSelect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSelect);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
        );

        jPanel8.setName("jPanel8"); // NOI18N

        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(new java.awt.GridLayout(1, 3, 15, 0));

        jPanel2.setName("jPanel2"); // NOI18N

        cbxDistrict.setName("cbxDistrict"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${districts}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, districtListBean, eLProperty, cbxDistrict);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, districtListBean, org.jdesktop.beansbinding.ELProperty.create("${selectedDistrict}"), cbxDistrict, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jLabel1.setText(bundle1.getString("ParcelSearchPanel.jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbxDistrict, 0, 94, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel2);

        jPanel3.setName("jPanel3"); // NOI18N

        cbxVdc.setName("cbxVdc"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${vdcs}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vdcListBean, eLProperty, cbxVdc);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, parcelSearchParamsBean, org.jdesktop.beansbinding.ELProperty.create("${vdc}"), cbxVdc, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jLabel2.setText(bundle1.getString("ParcelSearchPanel.jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbxVdc, 0, 94, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxVdc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel3);

        jPanel6.setName("jPanel6"); // NOI18N

        txtWardNo.setName("txtWardNo"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, parcelSearchParamsBean, org.jdesktop.beansbinding.ELProperty.create("${wardNo}"), txtWardNo, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel3.setText(bundle1.getString("ParcelSearchPanel.jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtWardNo, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtWardNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel6);

        jPanel1.setName("jPanel1"); // NOI18N

        txtParcelNo.setName("txtParcelNo"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, parcelSearchParamsBean, org.jdesktop.beansbinding.ELProperty.create("${parcelNo}"), txtParcelNo, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel5.setText(bundle1.getString("ParcelSearchPanel.jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtParcelNo, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtParcelNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel1);

        jPanel5.setName("jPanel5"); // NOI18N

        jLabel4.setText(bundle.getString("PartySearchPanel.jLabel4.text")); // NOI18N
        jLabel4.setName(bundle.getString("PartySearchPanel.jLabel4.name")); // NOI18N

        btnSearch.setText(bundle.getString("PartySearchPanel.btnSearch.text")); // NOI18N
        btnSearch.setName("btnSearch"); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlSearchLayout = new javax.swing.GroupLayout(pnlSearch);
        pnlSearch.setLayout(pnlSearchLayout);
        pnlSearchLayout.setHorizontalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        scrlSearchPanel.setViewportView(pnlSearch);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrlSearchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrlSearchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        search();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        selectParcel();
    }//GEN-LAST:event_btnSelectActionPerformed

    private void menuSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSelectActionPerformed
        selectParcel();
    }//GEN-LAST:event_menuSelectActionPerformed

    private void tableParcelSearchResultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableParcelSearchResultsMouseClicked
    }//GEN-LAST:event_tableParcelSearchResultsMouseClicked

    public ParcelSearchResultListBean getParcelSearchResuls() {
        return parcelSearchResultListBean;
    }

    private void postInit() {
        cbxDistrict.setSelectedIndex(-1);
        districtListBean.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(DistrictListBean.SELECTED_DISTRICT_PROPERTY)) {
                    searchVdcs();
                }
            }
        });
        searchVdcs();
    }

    private void searchVdcs() {
        if (districtListBean.getSelectedDistrict() != null) {
            cbxVdc.setEnabled(true);
            vdcListBean.loadList(false, districtListBean.getSelectedDistrict().getCode());
        } else {
            cbxVdc.setEnabled(false);
        }
        cbxVdc.setSelectedIndex(-1);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSelect;
    private javax.swing.JComboBox cbxDistrict;
    private javax.swing.JComboBox cbxVdc;
    private org.sola.clients.beans.referencedata.DistrictListBean districtListBean;
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
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem menuSelect;
    private org.sola.clients.beans.cadastre.ParcelSearchParamsBean parcelSearchParamsBean;
    private org.sola.clients.beans.cadastre.ParcelSearchResultListBean parcelSearchResultListBean;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPopupMenu popupParcels;
    private javax.swing.JScrollPane scrlSearchPanel;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableParcelSearchResults;
    private javax.swing.JTextField txtParcelNo;
    private javax.swing.JTextField txtWardNo;
    private org.sola.clients.beans.referencedata.VdcListBean vdcListBean;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}