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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CadastreChangePointSurveyListForm.java
 *
 * Created on Oct 19, 2011, 5:12:45 PM
 */
package org.sola.clients.swing.gis.ui.control;

import java.io.*;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import org.sola.clients.swing.gis.ClsGeneral;
import org.sola.clients.swing.gis.Messaging;
import org.sola.clients.swing.gis.data.PojoDataAccess;
import org.sola.clients.swing.gis.layer.CadastreChangeNewSurveyPointLayer;
import org.sola.common.messaging.GisMessage;

/**
 * This form is used to display information about the survey points during the
 * cadastre change process.
 *
 * @author Elton Manoku
 */
public class CadastreChangePointSurveyListForm extends javax.swing.JDialog {

    private CadastreChangeNewSurveyPointLayer layer;

    /**
     * Creates new form CadastreChangePointSurveyListForm
     */
    public CadastreChangePointSurveyListForm() {
        initComponents();
        this.setAlwaysOnTop(true);
        //this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.txtAcceptableShift.setText(
                optionRural.isSelected()
                ? this.getAcceptanceShift(true).toString()
                : this.getAcceptanceShift(false).toString());
        this.txtMeanShift.setText(Double.toString(0.0));
        this.txtStandardDeviation.setText(Double.toString(0.0));
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {

                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        cmdRemove.setEnabled(true);
                    }
                });
        this.table.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                treatTableChange(e);
            }
        });
    }

    public CadastreChangePointSurveyListForm(
            CadastreChangeNewSurveyPointLayer pointLayer) {
        this();
        this.layer = pointLayer;
    }

    /**
     * Gets the table which shows for each survey point a row with information
     * about the survey point
     *
     * @return
     */
    public JTable getTable() {
        return this.table;
    }

    /**
     * Gets the accepted shift for survey points shifts from their original
     * position
     *
     * @param forRuralArea
     * @return
     */
    private Double getAcceptanceShift(boolean forRuralArea) {
        if (forRuralArea) {
            return PojoDataAccess.getInstance().getMapDefinition().getSurveyPointShiftRuralArea();
        }
        return PojoDataAccess.getInstance().getMapDefinition().getSurveyPointShiftUrbanArea();
    }

    private void treatTableChange(TableModelEvent e) {
        int colIndex = e.getColumn();
        if (colIndex == -1) {
            //It means is new row inserted. Column not defined
            return;
        }
        if (this.layer.getFieldIndex(CadastreChangeNewSurveyPointLayer.LAYER_FIELD_SHIFT)
                == colIndex) {
            //Calculate mean and standard deviation
            DecimalFormat df = new DecimalFormat("#.##");
            this.txtMeanShift.setText(df.format(this.layer.getMean()));
            this.txtStandardDeviation.setText(df.format(this.layer.getStandardDeviation()));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        urbanRural = new javax.swing.ButtonGroup();
        txtMeanShift = new javax.swing.JTextField();
        txtStandardDeviation = new javax.swing.JTextField();
        lblMeanShift = new javax.swing.JLabel();
        lblStandardDeviation = new javax.swing.JLabel();
        cmdAdd = new javax.swing.JButton();
        optionRural = new javax.swing.JRadioButton();
        optionUrban = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        txtX = new javax.swing.JTextField();
        txtY = new javax.swing.JTextField();
        lblX = new javax.swing.JLabel();
        lblY = new javax.swing.JLabel();
        cmdRemove = new javax.swing.JButton();
        txtAcceptableShift = new javax.swing.JTextField();
        lblAcceptableShift = new javax.swing.JLabel();
        cmdLoadFromExternalSource = new javax.swing.JButton();
        btnShowPointsOnMap = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        txtMeanShift.setEditable(false);

        txtStandardDeviation.setEditable(false);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/gis/ui/control/Bundle"); // NOI18N
        lblMeanShift.setText(bundle.getString("CadastreChangePointSurveyListForm.lblMeanShift.text")); // NOI18N

        lblStandardDeviation.setText(bundle.getString("CadastreChangePointSurveyListForm.lblStandardDeviation.text")); // NOI18N

        cmdAdd.setText(bundle.getString("CadastreChangePointSurveyListForm.cmdAdd.text")); // NOI18N
        cmdAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAddActionPerformed(evt);
            }
        });

        urbanRural.add(optionRural);
        optionRural.setSelected(true);
        optionRural.setLabel(bundle.getString("CadastreChangePointSurveyListForm.optionRural.label")); // NOI18N
        optionRural.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionRuralActionPerformed(evt);
            }
        });

        urbanRural.add(optionUrban);
        optionUrban.setLabel(bundle.getString("CadastreChangePointSurveyListForm.optionUrban.label")); // NOI18N
        optionUrban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionUrbanActionPerformed(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nr", "x", "y", "Is boundary", "Is linked", "Shift distance (m)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("CadastreChangePointSurveyListForm.table.columnModel.title0")); // NOI18N
        table.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("CadastreChangePointSurveyListForm.table.columnModel.title1")); // NOI18N
        table.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("CadastreChangePointSurveyListForm.table.columnModel.title2")); // NOI18N
        table.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("CadastreChangePointSurveyListForm.table.columnModel.title3")); // NOI18N
        table.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("CadastreChangePointSurveyListForm.table.columnModel.title4")); // NOI18N
        table.getColumnModel().getColumn(5).setHeaderValue(bundle.getString("CadastreChangePointSurveyListForm.table.columnModel.title5")); // NOI18N

        txtX.setText(bundle.getString("CadastreChangePointSurveyListForm.txtX.text")); // NOI18N

        txtY.setText(bundle.getString("CadastreChangePointSurveyListForm.txtY.text")); // NOI18N

        lblX.setText(bundle.getString("CadastreChangePointSurveyListForm.lblX.text")); // NOI18N

        lblY.setText(bundle.getString("CadastreChangePointSurveyListForm.lblY.text")); // NOI18N

        cmdRemove.setText(bundle.getString("CadastreChangePointSurveyListForm.cmdRemove.text")); // NOI18N
        cmdRemove.setEnabled(false);
        cmdRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRemoveActionPerformed(evt);
            }
        });

        txtAcceptableShift.setEditable(false);

        lblAcceptableShift.setText(bundle.getString("CadastreChangePointSurveyListForm.lblAcceptableShift.text")); // NOI18N

        cmdLoadFromExternalSource.setText(bundle.getString("CadastreChangePointSurveyListForm.cmdLoadFromExternalSource.text")); // NOI18N
        cmdLoadFromExternalSource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLoadFromExternalSourceActionPerformed(evt);
            }
        });

        btnShowPointsOnMap.setText(bundle.getString("CadastreChangePointSurveyListForm.btnShowPointsOnMap.text")); // NOI18N
        btnShowPointsOnMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowPointsOnMapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMeanShift)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMeanShift, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblStandardDeviation)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStandardDeviation, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(optionUrban, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(optionRural)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                        .addComponent(lblAcceptableShift)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAcceptableShift, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmdLoadFromExternalSource)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addComponent(lblX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtX, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblY)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtY, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(cmdAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmdRemove))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnShowPointsOnMap, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMeanShift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMeanShift)
                    .addComponent(txtStandardDeviation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStandardDeviation)
                    .addComponent(optionUrban)
                    .addComponent(optionRural)
                    .addComponent(txtAcceptableShift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAcceptableShift))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdLoadFromExternalSource)
                    .addComponent(txtY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblY)
                    .addComponent(txtX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblX)
                    .addComponent(cmdAdd)
                    .addComponent(cmdRemove))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnShowPointsOnMap))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void cmdAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAddActionPerformed
// TODO add your handling code here:
    try {
        Double x = Double.valueOf(this.txtX.getText());
        Double y = Double.valueOf(this.txtY.getText());
        this.layer.addPoint(x, y);
    } catch (NumberFormatException ex) {
        Messaging.getInstance().show(GisMessage.CADASTRE_SURVEY_ADD_POINT);
    }
}//GEN-LAST:event_cmdAddActionPerformed

private void cmdRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRemoveActionPerformed
    int selectedRowIndex = this.table.getSelectedRow();
    if (selectedRowIndex > -1) {
        String fid = this.table.getModel().getValueAt(
                selectedRowIndex, this.layer.getFieldIndex(
                CadastreChangeNewSurveyPointLayer.LAYER_FIELD_FID)).toString();
        this.layer.removeFeature(fid);
        cmdRemove.setEnabled(false);
    }
}//GEN-LAST:event_cmdRemoveActionPerformed

private void optionUrbanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionUrbanActionPerformed
    this.txtAcceptableShift.setText(this.getAcceptanceShift(false).toString());
}//GEN-LAST:event_optionUrbanActionPerformed

private void optionRuralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionRuralActionPerformed
    this.txtAcceptableShift.setText(this.getAcceptanceShift(true).toString());
}//GEN-LAST:event_optionRuralActionPerformed

//<editor-fold defaultstate="collapse" desc="Addition by Kabindra for point import from text file.">
//------------------------------------------------------------------------------
    private FileFilter getTextFileFilter() {
        //prepare file filter.
        FileFilter filter = new FileFilter() {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    String filepathname = f.getAbsolutePath().toLowerCase();
                    if (filepathname.endsWith(".ssu")) {
                        return true;
                    }
//                    else if (filepathname.endsWith(".txt"))
//                        return true;
                }
                return false;
            }

            @Override
            public String getDescription() {
                //return "Text Files (*.txt|*.csv)";
                return "Text Files (*.ssu)";
            }
        };

        return filter;
    }

    private void readTextFileData(File iFile) {
        //fill data into table from text file.
        try {
            DefaultTableModel tblpoint = (DefaultTableModel) table.getModel();
            FileReader iReader = new FileReader(iFile);
            BufferedReader buff_reader = new BufferedReader((Reader) iReader);

            buff_reader.readLine();//read title line.
            DecimalFormat df = new DecimalFormat("0.000");//mm precision.
            String txtline = buff_reader.readLine();
            while (txtline != null && !txtline.isEmpty()) {
                String[] pt = txtline.split(",");
                if (pt != null && pt.length > 4) {//ignoring z and remarks field.
                    Object[] row = new Object[6];
                    row[0]= pt[0].toString();
                    for (int i=1;i<3;i++){
                        row[i]= Double.valueOf(pt[i].toString());
                    }
                    row[3]= true;//temporary values.
                    row[4]=false;
                    row[5]=0.0;
                     
                    tblpoint.addRow(row);
                }
                txtline = buff_reader.readLine();
            }
            buff_reader.close();
            table.setModel(tblpoint);
            table.repaint();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DefinePointListForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DefinePointListForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>
    
    private void importPoints() {
        JFileChooser fileOpen = new JFileChooser();
        fileOpen.setDialogTitle("Choose text file to load in table.");
        fileOpen.setVisible(true);
        FileFilter filter = getTextFileFilter();
        fileOpen.setFileFilter(filter);
         
        fileOpen.showOpenDialog(this);
        File iFile = fileOpen.getSelectedFile();
        if (iFile == null) {
            return;
        }
        //prepare table to input data.
        DefaultTableModel defTable = (DefaultTableModel) table.getModel();
        defTable.setRowCount(0);
        table.setModel(defTable);
        table.repaint();
        //fill table from text file.
        readTextFileData(iFile);
    }
//------------------------------------------------------------------------------
private void cmdLoadFromExternalSourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLoadFromExternalSourceActionPerformed
    importPoints();
}//GEN-LAST:event_cmdLoadFromExternalSourceActionPerformed

    private void btnShowPointsOnMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowPointsOnMapActionPerformed
        this.layer.setBlnAppend_table(false);//do not allow to append into the table.
        for (int i=0;i<table.getRowCount();i++){
            double x=ClsGeneral.getDoubleValue(table.getValueAt(i, 1).toString());
            double y=ClsGeneral.getDoubleValue(table.getValueAt(i,2).toString());
            this.layer.addPoint(x, y, false);
        }
        this.layer.setBlnAppend_table(true);//allow append in table.
        this.layer.getMapControl().refresh();
    }//GEN-LAST:event_btnShowPointsOnMapActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnShowPointsOnMap;
    private javax.swing.JButton cmdAdd;
    private javax.swing.JButton cmdLoadFromExternalSource;
    private javax.swing.JButton cmdRemove;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAcceptableShift;
    private javax.swing.JLabel lblMeanShift;
    private javax.swing.JLabel lblStandardDeviation;
    private javax.swing.JLabel lblX;
    private javax.swing.JLabel lblY;
    private javax.swing.JRadioButton optionRural;
    private javax.swing.JRadioButton optionUrban;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtAcceptableShift;
    private javax.swing.JTextField txtMeanShift;
    private javax.swing.JTextField txtStandardDeviation;
    private javax.swing.JTextField txtX;
    private javax.swing.JTextField txtY;
    private javax.swing.ButtonGroup urbanRural;
    // End of variables declaration//GEN-END:variables
}
