/*
 * Copyright 2013 Food and Agriculture Organization of the United Nations (FAO).
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
package org.sola.clients.swing.desktop.party;

import org.sola.clients.swing.ui.ContentPanel;

/**
 *
 * @author Kumar
 */
public class LandOwnerQuery extends ContentPanel {

    /**
     * Creates new form LandOwnerQuery
     */
    public LandOwnerQuery() {
        initComponents();
        customizeFields();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        buttonGroup1 = new javax.swing.ButtonGroup();
        partySearchResultListBean1 = new org.sola.clients.beans.party.PartySearchResultListBean();
        nepaliDateBean1 = new org.sola.clients.beans.system.NepaliDateBean();
        nepaliDateBean2 = new org.sola.clients.beans.system.NepaliDateBean();
        nepaliDateBean3 = new org.sola.clients.beans.system.NepaliDateBean();
        nepaliDateBean4 = new org.sola.clients.beans.system.NepaliDateBean();
        nepaliDateBean5 = new org.sola.clients.beans.system.NepaliDateBean();
        nepaliDateBean6 = new org.sola.clients.beans.system.NepaliDateBean();
        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        jPanel6 = new javax.swing.JPanel();
        rdbFromTo = new javax.swing.JRadioButton();
        rdbUptoDate = new javax.swing.JRadioButton();
        rdbFiscalYear = new javax.swing.JRadioButton();
        rdbFrom = new javax.swing.JRadioButton();
        btnSearch = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtFromDate = new org.sola.clients.swing.common.controls.NepaliDateField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtToDate = new org.sola.clients.swing.common.controls.NepaliDateField();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtUpToDate = new org.sola.clients.swing.common.controls.NepaliDateField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtFiscalYear = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtUpToYear = new org.sola.clients.swing.common.controls.NepaliDateField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableWithDefaultStyles1 = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        lblResult = new javax.swing.JLabel();

        setHeaderPanel(headerPanel1);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/party/Bundle"); // NOI18N
        headerPanel1.setTitleText(bundle.getString("LandOwnerQuery.headerPanel1.titleText")); // NOI18N

        buttonGroup1.add(rdbFromTo);
        rdbFromTo.setText(bundle.getString("LandOwnerQuery.rdbFromTo.text")); // NOI18N
        rdbFromTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbFromToActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbUptoDate);
        rdbUptoDate.setText(bundle.getString("LandOwnerQuery.rdbUptoDate.text")); // NOI18N
        rdbUptoDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbUptoDateActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbFiscalYear);
        rdbFiscalYear.setText(bundle.getString("LandOwnerQuery.rdbFiscalYear.text")); // NOI18N
        rdbFiscalYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbFiscalYearActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbFrom);
        rdbFrom.setText(bundle.getString("LandOwnerQuery.rdbFrom.text")); // NOI18N
        rdbFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbFromActionPerformed(evt);
            }
        });

        btnSearch.setText(bundle.getString("LandOwnerQuery.btnSearch.text")); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdbFromTo, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rdbUptoDate, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rdbFiscalYear, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rdbFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbFromTo)
                    .addComponent(rdbUptoDate)
                    .addComponent(rdbFiscalYear)
                    .addComponent(rdbFrom)
                    .addComponent(btnSearch))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setLayout(new java.awt.GridLayout(1, 5, 15, 0));

        jLabel1.setText(bundle.getString("LandOwnerQuery.jLabel1.text")); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, nepaliDateBean1, org.jdesktop.beansbinding.ELProperty.create("${nepaliDate}"), txtFromDate, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 81, Short.MAX_VALUE))
            .addComponent(txtFromDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel1);

        jLabel2.setText(bundle.getString("LandOwnerQuery.jLabel2.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, nepaliDateBean2, org.jdesktop.beansbinding.ELProperty.create("${nepaliDate}"), txtToDate, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtToDate, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtToDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel2);

        jLabel5.setText(bundle.getString("LandOwnerQuery.jLabel5.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, nepaliDateBean3, org.jdesktop.beansbinding.ELProperty.create("${nepaliDate}"), txtUpToDate, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtUpToDate, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUpToDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel5);

        jLabel3.setText(bundle.getString("LandOwnerQuery.jLabel3.text")); // NOI18N

        try {
            txtFiscalYear.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 62, Short.MAX_VALUE))
            .addComponent(txtFiscalYear)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFiscalYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel3);

        jLabel4.setText(bundle.getString("LandOwnerQuery.jLabel4.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, nepaliDateBean6, org.jdesktop.beansbinding.ELProperty.create("${nepaliDate}"), txtUpToYear, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtUpToYear, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUpToYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel4);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${partySearchResults}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, partySearchResultListBean1, eLProperty, jTableWithDefaultStyles1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fullName}"));
        columnBinding.setColumnName("Full Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${address}"));
        columnBinding.setColumnName("Address");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idOfficeType.displayValue}"));
        columnBinding.setColumnName("Id Office Type.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idNumber}"));
        columnBinding.setColumnName("Id Number");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fatherName}"));
        columnBinding.setColumnName("Father Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${grandfatherName}"));
        columnBinding.setColumnName("Grandfather Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${genderType.displayValue}"));
        columnBinding.setColumnName("Gender Type.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(jTableWithDefaultStyles1);
        jTableWithDefaultStyles1.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("LandOwnerQuery.jTableWithDefaultStyles1.columnModel.title0_2")); // NOI18N
        jTableWithDefaultStyles1.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("LandOwnerQuery.jTableWithDefaultStyles1.columnModel.title1_2")); // NOI18N
        jTableWithDefaultStyles1.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("LandOwnerQuery.jTableWithDefaultStyles1.columnModel.title2_1")); // NOI18N
        jTableWithDefaultStyles1.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("LandOwnerQuery.jTableWithDefaultStyles1.columnModel.title3_1")); // NOI18N
        jTableWithDefaultStyles1.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("LandOwnerQuery.jTableWithDefaultStyles1.columnModel.title4")); // NOI18N
        jTableWithDefaultStyles1.getColumnModel().getColumn(5).setHeaderValue(bundle.getString("LandOwnerQuery.jTableWithDefaultStyles1.columnModel.title5")); // NOI18N
        jTableWithDefaultStyles1.getColumnModel().getColumn(6).setHeaderValue(bundle.getString("LandOwnerQuery.jTableWithDefaultStyles1.columnModel.title6")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText(bundle.getString("LandOwnerQuery.jButton1.text")); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton1);

        lblResult.setText(bundle.getString("LandOwnerQuery.lblResult.text")); // NOI18N
        jToolBar1.add(lblResult);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void rdbFiscalYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbFiscalYearActionPerformed
        // TODO add your handling code here:
        txtFromDate.setEnabled(false);
        txtToDate.setEnabled(false);
        txtFiscalYear.setEnabled(true);
        txtUpToDate.setEnabled(false);
        txtUpToYear.setEnabled(false);
    }//GEN-LAST:event_rdbFiscalYearActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        if (rdbFromTo.isSelected()) {
            searchFromTo();
        } else if (rdbUptoDate.isSelected()) {
            searchUptoDate();
        } else if (rdbFiscalYear.isSelected()) {
            searchByFiscalYear();
        } else if (rdbFrom.isSelected()) {
            searchFrom();
        }
    }//GEN-LAST:event_btnSearchActionPerformed
    private void searchFrom() {
        partySearchResultListBean1.searchFrom(nepaliDateBean6.getGregorean_date(), "ne");
        countResult(partySearchResultListBean1.getPartySearchResults().size());
    }

    private void searchByFiscalYear() {
        String firstPart = txtFiscalYear.getText().substring(0, 2);
        String secondPart = txtFiscalYear.getText().substring(3);
        String fromFiscalYear = "20" + firstPart + "0401";
        String toFiscalYear = "20" + secondPart + "0401";
        nepaliDateBean4.setNepaliDate(fromFiscalYear);
        nepaliDateBean5.setNepaliDate(toFiscalYear);
        partySearchResultListBean1.searchByFiscalYear(nepaliDateBean4.getGregorean_date(), nepaliDateBean5.getGregorean_date(), "ne");
        countResult(partySearchResultListBean1.getPartySearchResults().size());

    }

    private void searchUptoDate() {
       partySearchResultListBean1.searchUpTo(nepaliDateBean3.getGregorean_date(), "ne");
       countResult(partySearchResultListBean1.getPartySearchResults().size());

    }
    private void countResult(int size){
        lblResult.setText(Integer.toString(size));
    }
        

    private void searchFromTo() {
        partySearchResultListBean1.searchFromTo(nepaliDateBean1.getGregorean_date(), nepaliDateBean2.getGregorean_date(), "ne");
        countResult(partySearchResultListBean1.getPartySearchResults().size());
    }
    private void rdbUptoDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbUptoDateActionPerformed
        // TODO add your handling code here:
        txtFromDate.setEnabled(false);
        txtToDate.setEnabled(false);
        txtFiscalYear.setEnabled(false);
        txtUpToDate.setEnabled(true);
        txtUpToYear.setEnabled(false);
    }//GEN-LAST:event_rdbUptoDateActionPerformed

    private void rdbFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbFromActionPerformed
        // TODO add your handling code here:
        txtFromDate.setEnabled(false);
        txtToDate.setEnabled(false);
        txtFiscalYear.setEnabled(false);
        txtUpToDate.setEnabled(false);
        txtUpToYear.setEnabled(true);
    }//GEN-LAST:event_rdbFromActionPerformed

    private void rdbFromToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbFromToActionPerformed
        // TODO add your handling code here:
        txtFromDate.setEnabled(true);
        txtToDate.setEnabled(true);
        txtFiscalYear.setEnabled(false);
        txtUpToDate.setEnabled(false);
        txtUpToYear.setEnabled(false);
    }//GEN-LAST:event_rdbFromToActionPerformed

    private void customizeFields() {
        txtFromDate.setEnabled(false);
        txtToDate.setEnabled(false);
        txtFiscalYear.setEnabled(false);
        txtUpToDate.setEnabled(false);
        txtUpToYear.setEnabled(false);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.ButtonGroup buttonGroup1;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JButton jButton1;
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
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles jTableWithDefaultStyles1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblResult;
    private org.sola.clients.beans.system.NepaliDateBean nepaliDateBean1;
    private org.sola.clients.beans.system.NepaliDateBean nepaliDateBean2;
    private org.sola.clients.beans.system.NepaliDateBean nepaliDateBean3;
    private org.sola.clients.beans.system.NepaliDateBean nepaliDateBean4;
    private org.sola.clients.beans.system.NepaliDateBean nepaliDateBean5;
    private org.sola.clients.beans.system.NepaliDateBean nepaliDateBean6;
    private org.sola.clients.beans.party.PartySearchResultListBean partySearchResultListBean1;
    private javax.swing.JRadioButton rdbFiscalYear;
    private javax.swing.JRadioButton rdbFrom;
    private javax.swing.JRadioButton rdbFromTo;
    private javax.swing.JRadioButton rdbUptoDate;
    private javax.swing.JFormattedTextField txtFiscalYear;
    private org.sola.clients.swing.common.controls.NepaliDateField txtFromDate;
    private org.sola.clients.swing.common.controls.NepaliDateField txtToDate;
    private org.sola.clients.swing.common.controls.NepaliDateField txtUpToDate;
    private org.sola.clients.swing.common.controls.NepaliDateField txtUpToYear;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
