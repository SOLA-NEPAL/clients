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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PointSurveyListForm.java
 *
 * Created on Oct 19, 2011, 5:12:45 PM
 */
package org.sola.clients.swing.gis.ui.control;

import com.vividsolutions.jts.geom.Geometry;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.beans.cadastre.CadastreObjectBean;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.swing.gis.PublicMethod;
import org.sola.clients.swing.gis.layer.CadastreChangeNewCadastreObjectLayer;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.cadastre.CadastreObjectTO;

/**
 * This form is used to display information about the new cadastre objects 
 * during the cadastre change process.
 * 
 * @author Elton Manoku
 */
public class CadastreChangeNewCadastreObjectListForm extends javax.swing.JDialog {

    private CadastreChangeNewCadastreObjectLayer layer;
    private String transaction_id=null;

    /** Creates new form PointSurveyListForm */
    public CadastreChangeNewCadastreObjectListForm() {
        initComponents();
        this.setAlwaysOnTop(true);
        this.setModalityType(ModalityType.MODELESS);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {

                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        cmdRemove.setEnabled(true);
                        btnEdit.setEnabled(true);
                    }
                });
    }

    public CadastreChangeNewCadastreObjectListForm(
            CadastreChangeNewCadastreObjectLayer cadastreObjectLayer,String transaction_id) {
        this();
        this.layer = cadastreObjectLayer;
        this.transaction_id=transaction_id;
    }

    /**
     * Gets the table which displays for each cadastre object a row with information
     * @return 
     */
    public JTable getTable(){
        return this.table;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        urbanRural = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        cmdRemove = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnShowOnMap = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/gis/ui/control/Bundle"); // NOI18N
        jButton1.setText(bundle.getString("CadastreChangeNewCadastreObjectListForm.jButton1.text")); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        cmdRemove.setText(bundle.getString("CadastreChangeNewCadastreObjectListForm.cmdRemove.text")); // NOI18N
        cmdRemove.setEnabled(false);
        cmdRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRemoveActionPerformed(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nr", "First part", "Last part", "Official Area (m2)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);
        table.getColumnModel().getColumn(0).setResizable(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(2);
        table.getColumnModel().getColumn(3).setResizable(false);

        btnShowOnMap.setText(bundle.getString("CadastreChangeNewCadastreObjectListForm.btnShowOnMap.text")); // NOI18N
        btnShowOnMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowOnMapActionPerformed(evt);
            }
        });

        btnEdit.setText(bundle.getString("CadastreChangeNewCadastreObjectListForm.btnEdit.text")); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnShowOnMap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmdRemove)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdRemove)
                    .addComponent(btnShowOnMap)
                    .addComponent(btnEdit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void cmdRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRemoveActionPerformed

    int selectedRowIndex = this.table.getSelectedRow();
    if (selectedRowIndex > -1) {
        String fid = this.table.getModel().getValueAt(
                selectedRowIndex, this.layer.getFieldIndex(
                CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_FID)).toString();
        this.layer.removeFeature(fid);
        cmdRemove.setEnabled(false);
        btnEdit.setEnabled(false);
    }
}//GEN-LAST:event_cmdRemoveActionPerformed

    private CadastreObjectBean getSelected_parcel(){
        int r=table.getSelectedRow();
        String firstpartname=table.getValueAt(r, 1).toString();
        String lastpartname=table.getValueAt(r, 2).toString();
        String unique_id= firstpartname + " " + lastpartname;
        List<CadastreObjectTO> parcels=
                WSManager.getInstance().getCadastreService()
                            .getPendingParcelsByParts(unique_id);
        //Assuming that part combination gives unique id, the list will have single parcel.
        CadastreObjectBean parcel=null;
        if (parcels==null || parcels.size()<1){
            parcel = new CadastreObjectBean();
            SimpleFeatureCollection sFeatures=this.layer.getFeatureCollection();
            SimpleFeatureIterator feaIter=sFeatures.features();
            while (feaIter.hasNext()){
                SimpleFeature fea=feaIter.next();
                String tfirstpartname=fea.getAttribute(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_FIRST_PART).toString();
                String tlastpartname=fea.getAttribute(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_LAST_PART).toString();
                if (tfirstpartname.equals(firstpartname) && tlastpartname.equals(lastpartname)){
                    //assign attribute to variable.
                    parcel.setNameFirstPart(firstpartname);
                    parcel.setNameLastPart(lastpartname);
                    String mapsheetid=fea.getAttribute(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_MAP_SHEET).toString();
                    String parceltype=fea.getAttribute(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_PARCEL_TYPE).toString();
                    parcel.setMapSheetId(mapsheetid);
                    parcel.setLandTypeCode(parceltype);
                    //parcel.setParcelno(0);
                    parcel.setTransactionId(transaction_id);
                }
            }
        }
        else{
            CadastreObjectTO tmp_parcel=parcels.get(0);
            parcel=TypeConverters.TransferObjectToBean(
                tmp_parcel, CadastreObjectBean.class, null);
        }
        return parcel;
    }
    
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        CadastreObjectBean parcel=getSelected_parcel();
        
        EditParcelAttributeForm editParcel=new EditParcelAttributeForm();
        //Event delegate passing to the child JPanel.
        Class[] cls=new Class[]{CadastreObjectBean.class};
        Class workingForm=this.getClass();
        Method taskCompletion=null;
        try {
            taskCompletion = workingForm.getMethod("refresh_Data", cls);
        } catch (Exception ex) {
            Logger.getLogger(
                    CadastreChangeNewCadastreObjectListForm.class.getName()).log(
                            Level.SEVERE, null, ex);
        }
        editParcel.set_SearchCompletedTriggers(taskCompletion, this);
        
        editParcel.setParcel(parcel);
        editParcel.setTitle("Edit Attributes of the parcels");
        editParcel.setVisible(true);
        editParcel.setAlwaysOnTop(true);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnShowOnMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowOnMapActionPerformed
        // TODO add your handling code here:
        SimpleFeatureCollection featrs=this.layer.getFeatureCollection();
        SimpleFeatureIterator feaIter=featrs.features();
        //collect features first.
        List<SimpleFeature> tmpFeatures=new ArrayList<SimpleFeature>();
        while (feaIter.hasNext()){
            SimpleFeature fea=feaIter.next();
            tmpFeatures.add(fea);
        }
        //remove and append the features.
        String geomfld=PublicMethod.theGeomFieldName(this.layer.getFeatureCollection());
        featrs.clear();
        
        Geometry the_Polygon=null;
        this.layer.setAvoid_table_append(true);
        for (SimpleFeature fea:tmpFeatures){
            int selected= getFeature_SelectionStatus(fea);
            if (selected==1){
                the_Polygon=(Geometry)fea.getAttribute(geomfld);
            }
            //refresh the colleciton
            updateFeatureCollection(fea,selected,geomfld);
        }
        this.layer.setAvoid_table_append(false);
        //zoom to the panel.
        if (the_Polygon!=null){
            ReferencedEnvelope ref_Envelope= JTS.toEnvelope(the_Polygon);
            double expand_by=ref_Envelope.getHeight() * 0.5;//expand 50 % of height
            ref_Envelope.expandBy(expand_by);
            this.layer.getMapControl().setDisplayArea(ref_Envelope);
        }   
        this.layer.getMapControl().refresh(false);
    }//GEN-LAST:event_btnShowOnMapActionPerformed

//    private CadastreObjectBean getSelected_parcel(){
//        int r=table.getSelectedRow();
//        String unique_id= table.getValueAt(r, 1).toString() + " " +
//                                table.getValueAt(r, 2).toString();
//        List<CadastreObjectTO> parcels=
//                WSManager.getInstance().getCadastreService()
//                            .getPendingParcelsByParts(unique_id);
//        //Assuming that part combination gives unique id, the list will have single parcel.
//        if (parcels==null || parcels.size()<1) return null;
//        CadastreObjectTO tmp_parcel=parcels.get(0);
//        CadastreObjectBean parcel=TypeConverters.TransferObjectToBean(
//                tmp_parcel, CadastreObjectBean.class, null);
//        
//        return parcel;
//    }
    
    private void updateFeatureCollection(SimpleFeature fea,int selected,String geomfld){
         //record attributes.
        Object fea_id=fea.getAttribute(
            CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_FID);
        if (fea_id==null) fea_id=fea.hashCode();
        
        String firstpart= fea.getAttribute(
             CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_FIRST_PART).toString();
        String lastpart=fea.getAttribute(
             CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_LAST_PART).toString();
        String officical_area=fea.getAttribute(
             CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_OFFICIAL_AREA).toString();
        
        String id=String.valueOf(fea_id);
        //prepare new params.
        HashMap<String, Object> fieldsWithValues = new HashMap<String, Object>();
        fieldsWithValues.put(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_FIRST_PART, firstpart);
        fieldsWithValues.put(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_LAST_PART, lastpart);
        fieldsWithValues.put(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_SELECTED,selected);
        fieldsWithValues.put(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_OFFICIAL_AREA,officical_area);
        
        Geometry geom=(Geometry)fea.getAttribute(geomfld);
        this.layer.addFeature(id, geom,fieldsWithValues,false);
    }
    
    private int getFeature_SelectionStatus(SimpleFeature fea){
        int r= table.getSelectedRow();
        if (r<0) return 0;
        
        String firstpart=table.getValueAt(r, 1).toString();
        String lastpart=table.getValueAt(r, 2).toString();
        
        String selected_firstpart= fea.getAttribute(
             CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_FIRST_PART).toString();
        String selected_lastpart=fea.getAttribute(
             CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_LAST_PART).toString();
        
        if (firstpart.equals(selected_firstpart) && lastpart.equals(selected_lastpart))
            return 1;
        else
            return 0;
    }
    
    public void refresh_Data(CadastreObjectBean parcel){
        int r=table.getSelectedRow();
        if (r<0) return;
        
        table.setValueAt(parcel.getNameFirstPart(), r, 1);
        table.setValueAt(parcel.getNameLastPart(), r, 2);
        table.repaint();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnShowOnMap;
    private javax.swing.JButton cmdRemove;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private javax.swing.ButtonGroup urbanRural;
    // End of variables declaration//GEN-END:variables
}
