/**
 * ******************************************************************************************
 * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations (FAO).
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice,this list
 *       of conditions and the following disclaimer.
 *    2. Redistributions in binary form must reproduce the above copyright notice,this list
 *       of conditions and the following disclaimer in the documentation and/or other
 *       materials provided with the distribution.
 *    3. Neither the name of FAO nor the names of its contributors may be used to endorse or
 *       promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,STRICT LIABILITY,OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * *********************************************************************************************
 */
package org.sola.clients.swing.desktop.application;

import java.awt.ComponentOrientation;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListModel;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.administrative.BaUnitSearchResultBean;
import org.sola.clients.beans.cadastre.MapSheetBean;
import org.sola.clients.beans.controls.SolaList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.cadastre.CadastreObjectTO;

/**
 * Popup window to select property object from the list.
 */
public class PropertiesList extends javax.swing.JDialog {

    public static final String SELECTED_PROPERTY = "selectedProperty";
    
    private SolaList propertyList;
    private BaUnitSearchResultBean selectedProperty;
    
    //CadastreObjectSummaryBean
    
    /** Creates new form PropertiesList */
    public PropertiesList() {
        this(new SolaList());
    }

    public PropertiesList(SolaList<BaUnitSearchResultBean> propertyList) {
        super((Frame)null, true);  
        this.propertyList = propertyList;
        initComponents();       
        this.setIconImage(new ImageIcon(PropertiesList.class.getResource("/images/sola/logo_icon.jpg")).getImage());
        
        showSelectedParcelMapSheet();
    }
    
    public ObservableList<BaUnitSearchResultBean> getPropertyList() {
        return propertyList.getFilteredList();
    }
     
     

    public BaUnitSearchResultBean getSelectedProperty() {
        return selectedProperty;
    }

    public void setSelectedProperty(BaUnitSearchResultBean selectedProperty) {
        this.selectedProperty = selectedProperty;
    }
    
    private void AppendItemInListBox(JList dest,MapSheetBean mapsheet) {
        //append to destination list box.
        DefaultListModel defDisplay=new DefaultListModel();
        defDisplay.addElement(mapsheet.getMapNumber());
        dest.setModel(defDisplay);
    }
    
    private void showSelectedParcelMapSheet(){
        tabPropertyDetails.addRowSelectionInterval(0, 0);
        
        BaUnitSearchResultBean property=this.getSelectedProperty();
        String firstpart= property.getNameFirstPart();
        String lastpart= property.getNameLastPart();
        List<CadastreObjectTO> parcel=
              WSManager.getInstance().getCadastreService().getCadastreObjectByExactParts(firstpart, lastpart); //searching by number.
        //the valued returned will be only one.
        if (parcel!=null && parcel.size()>0){
            CadastreObjectTO selected_parcel= parcel.get(0);
            MapSheetBean mapsht= TypeConverters.TransferObjectToBean(
                    selected_parcel.getMapSheet(),MapSheetBean.class,null);
            
            AppendItemInListBox(lstMapDisplay,mapsht);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        scrollPropertyDetails = new javax.swing.JScrollPane();
        tabPropertyDetails = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstMapDisplay = new javax.swing.JList();
        btnDisplayMap = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/application/Bundle"); // NOI18N
        setTitle(bundle.getString("PropertiesList.title")); // NOI18N
        setName("Form"); // NOI18N

        scrollPropertyDetails.setFont(new java.awt.Font("Tahoma 12 12", 0, 12)); // NOI18N
        scrollPropertyDetails.setName("scrollPropertyDetails"); // NOI18N
        scrollPropertyDetails.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

        tabPropertyDetails.setName("tabPropertyDetails"); // NOI18N
        tabPropertyDetails.getTableHeader().setReorderingAllowed(false);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${propertyList}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, tabPropertyDetails, "");
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nameFirstPart}"));
        columnBinding.setColumnName("Name First Part");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nameLastPart}"));
        columnBinding.setColumnName("Name Last Part");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${vdc.code}"));
        columnBinding.setColumnName("Vdc.code");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${vdc.displayValue}"));
        columnBinding.setColumnName("Vdc.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${selectedProperty}"), tabPropertyDetails, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        tabPropertyDetails.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        tabPropertyDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePropertiesMouseCliecked(evt);
            }
        });
        scrollPropertyDetails.setViewportView(tabPropertyDetails);
        tabPropertyDetails.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("PropertiesList.tabPropertyDetails.columnModel.title0")); // NOI18N
        tabPropertyDetails.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("PropertiesList.tabPropertyDetails.columnModel.title1")); // NOI18N
        tabPropertyDetails.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("PropertiesList.tabPropertyDetails.columnModel.title2")); // NOI18N
        tabPropertyDetails.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("PropertiesList.tabPropertyDetails.columnModel.title3")); // NOI18N

        jLabel2.setText(bundle.getString("PropertiesList.jLabel2.text")); // NOI18N
        jLabel2.setName(bundle.getString("PropertiesList.jLabel2.name")); // NOI18N

        jScrollPane2.setName(bundle.getString("PropertiesList.jScrollPane2.name")); // NOI18N

        lstMapDisplay.setName(bundle.getString("PropertiesList.lstMapDisplay.name")); // NOI18N
        jScrollPane2.setViewportView(lstMapDisplay);

        btnDisplayMap.setText(bundle.getString("PropertiesList.btnDisplayMap.text")); // NOI18N
        btnDisplayMap.setName(bundle.getString("PropertiesList.btnDisplayMap.name")); // NOI18N
        btnDisplayMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayMapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPropertyDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDisplayMap))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scrollPropertyDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDisplayMap))
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablePropertiesMouseCliecked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePropertiesMouseCliecked
        showSelectedParcelMapSheet();
    }//GEN-LAST:event_tablePropertiesMouseCliecked

    private void btnDisplayMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayMapActionPerformed
        if(selectedProperty!=null){
            firePropertyChange(SELECTED_PROPERTY, null, selectedProperty);
        }
    }//GEN-LAST:event_btnDisplayMapActionPerformed
    
    public List<String> getMapSheets(){
        List<String> mapsheets=new ArrayList<>();
        ListModel model=lstMapDisplay.getModel();
        for (int i=0; i<model.getSize();i++){
            String sheet=model.getElementAt(i).toString();
            mapsheets.add(sheet);
        }
        
        return mapsheets;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDisplayMap;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList lstMapDisplay;
    private javax.swing.JScrollPane scrollPropertyDetails;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tabPropertyDetails;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
