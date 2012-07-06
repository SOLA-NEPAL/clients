/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.ui.control;

import com.vividsolutions.jts.geom.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.geotools.data.FeatureReader;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.swing.extended.Map;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.swing.gis.*;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.clients.swing.gis.tool.listSelectedCadastreObjects;
/**
 *
 * @author ShresthaKabin
 */
public class ImportLinesFromFile extends javax.swing.JDialog {
    //Store for old data collection.
    private CadastreChangeTargetCadastreObjectLayer prevTargetParcelsLayer = null;

    private CadastreTargetSegmentLayer segmentLayer = null;
    private CadastreChangeTargetCadastreObjectLayer targetParcelsLayer = null;
    
    private LocatePointPanel locatePointPanel;
    private JToolBar jTool;
    /**
     * Creates new form DefinePointListForm
     */
    public ImportLinesFromFile(CadastreTargetSegmentLayer segmentLayer,
            CadastreChangeTargetCadastreObjectLayer targetParcelsLayer
            ,JToolBar jTool) throws InitializeLayerException {
        initComponents();
        //set table dimesion.
        TableColumnModel cModel= tblPoints.getColumnModel();
        cModel.getColumn(0).setWidth(30);
        tblPoints.setColumnModel(cModel);
        tblPoints.repaint();
        
        this.jTool=jTool;
        otherInitializations(segmentLayer,targetParcelsLayer);
    }

    private void otherInitializations(CadastreTargetSegmentLayer segmentLayer, CadastreChangeTargetCadastreObjectLayer targetParcelsLayer) throws InitializeLayerException {
        this.setTitle("Defining Point List for Parcel Splitting form.");
        this.setAlwaysOnTop(true);
        //Initialize other variables.
        this.segmentLayer = segmentLayer;
        this.targetParcelsLayer = targetParcelsLayer;

        locatePointPanel=new LocatePointPanel(segmentLayer);
        locatePointPanel.reset_OldCollectionVariable(segmentLayer);
        //set parcel id.
        SimpleFeatureCollection feacol=targetParcelsLayer.getFeatureCollection();
        SimpleFeatureIterator feaIter=feacol.features();
        SimpleFeature fea= feaIter.next();
        String parcel_id=fea.getID().toString();
        locatePointPanel.setParcelID(parcel_id);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPoints = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnOpenShapeFile = new javax.swing.JButton();
        btnSaveTextFile = new javax.swing.JButton();
        btnUndoSplit = new javax.swing.JButton();
        btnAddRow = new javax.swing.JButton();
        btnImportLines = new javax.swing.JButton();
        btnOK = new javax.swing.JButton();
        btnDxfImport = new javax.swing.JButton();
        btnRefreshMap = new javax.swing.JButton();
        btnShowInMap = new javax.swing.JButton();
        btnCreatePolygon = new javax.swing.JButton();
        btnImportPoints = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tblPoints.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.N.", "X1-Cor", "Y1-Cor", "X2-Cor", "Y2-Cor"
            }
        ));
        tblPoints.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblPoints);
        tblPoints.getColumnModel().getColumn(0).setResizable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        btnOpenShapeFile.setText("Import Shape File");
        btnOpenShapeFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenShapeFileActionPerformed(evt);
            }
        });

        btnSaveTextFile.setText("Save to Text File");
        btnSaveTextFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveTextFileActionPerformed(evt);
            }
        });

        btnUndoSplit.setText("Undo Split");
        btnUndoSplit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUndoSplitActionPerformed(evt);
            }
        });

        btnAddRow.setText("Add Row");
        btnAddRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRowActionPerformed(evt);
            }
        });

        btnImportLines.setText("Import Line File");
        btnImportLines.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportLinesActionPerformed(evt);
            }
        });

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnDxfImport.setText("Import DXF file");
        btnDxfImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDxfImportActionPerformed(evt);
            }
        });

        btnRefreshMap.setText("Refresh Map");
        btnRefreshMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshMapActionPerformed(evt);
            }
        });

        btnShowInMap.setText("Show Lines in Map");
        btnShowInMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowInMapActionPerformed(evt);
            }
        });

        btnCreatePolygon.setText("Create Polygons");
        btnCreatePolygon.setEnabled(false);
        btnCreatePolygon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreatePolygonActionPerformed(evt);
            }
        });

        btnImportPoints.setText("Import Point File");
        btnImportPoints.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportPointsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUndoSplit, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCreatePolygon, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSaveTextFile, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImportLines, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddRow, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDxfImport, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnShowInMap)
                    .addComponent(btnRefreshMap, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOpenShapeFile, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImportPoints, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddRow, btnCreatePolygon, btnDxfImport, btnImportLines, btnOK, btnOpenShapeFile, btnRefreshMap, btnSaveTextFile, btnShowInMap, btnUndoSplit});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddRow)
                .addGap(32, 32, 32)
                .addComponent(btnImportPoints)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImportLines)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDxfImport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOpenShapeFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveTextFile)
                .addGap(39, 39, 39)
                .addComponent(btnRefreshMap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUndoSplit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnShowInMap)
                .addGap(51, 51, 51)
                .addComponent(btnCreatePolygon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOK)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private FileFilter getTextFileFilter(final String ext, final String desc) {
        //prepare file filter.
        FileFilter filter=new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory())
                {
                    return true;
                }
                else
                {
                    String filepathname = f.getAbsolutePath().toLowerCase();
                    if (filepathname.endsWith(ext)) //".sli"
                        return true;
//                    else if (filepathname.endsWith(".txt"))
//                        return true;
                }
                return false;
            }
            @Override
            public String getDescription() {
               //return "Text Files (*.txt|*.csv)";
                return desc;//"Text Files (*.sli)";
            }
        };

        return filter;
    }
    
    private void btnImportLinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportLinesActionPerformed
        JFileChooser fileOpen=new JFileChooser();
        fileOpen.setDialogTitle("Choose point text file to load into table.");
        fileOpen.setVisible(true);
        FileFilter filter=getTextFileFilter(".sli","Text Files (*.sli)");
        fileOpen.setFileFilter(filter);
        //FileNameExtensionFilter extFilter=new FileNameExtensionFilter("Text Files", "*.txt|*.csv");
        fileOpen.showOpenDialog(this);
        File iFile=fileOpen.getSelectedFile();
        if (iFile==null) return;
        //prepare table to input data.
        DefaultTableModel defTable=(DefaultTableModel)tblPoints.getModel();
        defTable.setRowCount(0);
        tblPoints.setModel(defTable);
        tblPoints.repaint();
        //fill table from text file.
        if (!readTextFileData(iFile)){
            JOptionPane.showMessageDialog(this, "Wrong file format, please check it.");
        }
    }

    private boolean readTextFileData(File iFile) {
        //fill data into table from text file.
        try {
            DefaultTableModel table=(DefaultTableModel)tblPoints.getModel();
            table.setRowCount(0);//remove all rows.
            
            FileReader iReader=new FileReader(iFile);
            BufferedReader buff_reader=new BufferedReader((Reader)iReader);
            
            String txtline = buff_reader.readLine();
            int i=1;
            DecimalFormat df=new DecimalFormat("0.000");//mm precision.
            while (txtline!=null && !txtline.isEmpty()){
                String[] pt=txtline.split(",");
                if (pt.length<4){
                    return false;
                }
                if (pt!=null && pt.length>1){
                    Object[] row=new Object[]{i++,
                        df.format(Double.parseDouble(pt[0])),
                             df.format(Double.parseDouble(pt[1])),
                                df.format(Double.parseDouble(pt[2])),
                                    df.format(Double.parseDouble(pt[3]))};
                    table.addRow(row);
                }
                txtline = buff_reader.readLine();
            }
            buff_reader.close();
            tblPoints.setModel(table);
            tblPoints.repaint();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImportLinesFromFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImportLinesFromFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }//GEN-LAST:event_btnImportLinesActionPerformed

    private void btnAddRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRowActionPerformed
        DefaultTableModel tblmodel=(DefaultTableModel)tblPoints.getModel();
        int rowcount=tblmodel.getRowCount()+1;
        tblmodel.setRowCount(rowcount);
        tblPoints.setModel(tblmodel);
        tblPoints.repaint();
    }//GEN-LAST:event_btnAddRowActionPerformed

    private void saveTextFileData(File oFile) throws IOException{
        boolean isNewFile=oFile.createNewFile();
        if (!isNewFile){
            JOptionPane.showMessageDialog(this, "Could not create the new file.");
            return;
        }
        
        FileWriter oWriter=new FileWriter(oFile);
        BufferedWriter buff_writer=new BufferedWriter((Writer)oWriter);
         
        for (int i=0;i<tblPoints.getRowCount();i++){
            String[] itms=new String[4];
            itms[0]=tblPoints.getValueAt(i, 1).toString();//x-cordinate.
            itms[1]=tblPoints.getValueAt(i, 2).toString();//y-cordinate.
            itms[2]=tblPoints.getValueAt(i, 3).toString();//x-cordinate.
            itms[3]=tblPoints.getValueAt(i, 4).toString();//y-cordinate.
            if (itms[0].trim().isEmpty()) continue;
            
            String txtline=itms[0] + "," + itms[1];
            buff_writer.write(txtline);
            buff_writer.newLine();
        }
        buff_writer.close();
    }
    
    private void btnSaveTextFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTextFileActionPerformed
        JFileChooser fileSave=new JFileChooser();
        fileSave.setDialogTitle("Give text file name to save table data.");
        fileSave.setVisible(true);
        FileFilter filter=getTextFileFilter(".sli","Text Files (*.sli)");
        fileSave.setFileFilter(filter);
        //FileNameExtensionFilter extFilter=new FileNameExtensionFilter("Text Files", "*.txt|*.csv");
        fileSave.showSaveDialog(this);
        File oFile=fileSave.getSelectedFile();
        if (oFile==null) return;
        try {
            //fill table from text file.
            saveTextFileData(oFile);
        } catch (IOException ex) {
            Logger.getLogger(ImportLinesFromFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSaveTextFileActionPerformed

    private void append_Points_to_Layer(int i, GeometryFactory geomFactory,int j) throws NumberFormatException {
        if (tblPoints.getValueAt(i, 1)==null) return;
        
        double x= Double.parseDouble(tblPoints.getValueAt(i, j + 1).toString());//x-cordinate.
        double y= Double.parseDouble(tblPoints.getValueAt(i, j + 2).toString());//y-cordinate.
        Coordinate co=new Coordinate(x, y);
        Point pt=geomFactory.createPoint(co);
        locatePointPanel.addPointInPointCollection(pt);
    }
    
    private void btnUndoSplitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUndoSplitActionPerformed
        locatePointPanel.getPreviousData();
        //copy data from old collection to current collection.
        PublicMethod.exchangeParcelCollection(prevTargetParcelsLayer, targetParcelsLayer);
        btnCreatePolygon.setEnabled(false);
        //refresh map.
        targetParcelsLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnUndoSplitActionPerformed
    
    private void btnCreatePolygonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreatePolygonActionPerformed
        //Generate new line collection.
        NodedLineStringGenerator lineGenerator=new NodedLineStringGenerator(segmentLayer, locatePointPanel);
        lineGenerator.generateNodedSegments();
        
        Polygonization.formPolygon(segmentLayer, targetParcelsLayer);
        targetParcelsLayer.getMapControl().refresh();
        btnCreatePolygon.setEnabled(false);
    }//GEN-LAST:event_btnCreatePolygonActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
         try {
            //Store data for undo action.
            locatePointPanel.reset_OldCollectionVariable(segmentLayer);
            //store data to old collection.
            prevTargetParcelsLayer= new CadastreChangeTargetCadastreObjectLayer();
            PublicMethod.exchangeParcelCollection(targetParcelsLayer,prevTargetParcelsLayer);
        } catch (InitializeLayerException ex) {
            Logger.getLogger(OnePointAreaMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Map mapObj=segmentLayer.getMapControl();
        PublicMethod.maplayerOnOff(mapObj, true);
        PublicMethod.enable_disable_Select_Tool(jTool,
                    listSelectedCadastreObjects.NAME, true);
    }//GEN-LAST:event_formWindowClosing

    private void btnRefreshMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshMapActionPerformed
        targetParcelsLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnRefreshMapActionPerformed
    
    private boolean read_ShapeFile(File iFile){
        try {
            DecimalFormat df= new DecimalFormat("0.000");
            int j=1;
            DefaultTableModel table=(DefaultTableModel)tblPoints.getModel();
            table.setRowCount(0);//remove all rows.
            //iterate through feature collection.
            FileDataStore store = FileDataStoreFinder.getDataStore(iFile);
            FeatureReader fea_reader=store.getFeatureReader();
            String featureTypeName=fea_reader.getFeatureType().getGeometryDescriptor().getType().getName().toString().toUpperCase();
            if (!featureTypeName.equals("MULTILINESTRING") || featureTypeName.equals("LINESTRING")){
                return false;
            }
            String geomfield=fea_reader.getFeatureType().getGeometryDescriptor().getLocalName();
            while (fea_reader.hasNext()){
                SimpleFeature fea=(SimpleFeature)fea_reader.next();
                //find geometry
                Geometry seg=(Geometry)fea.getAttribute(geomfield);//first attribute or the_geom column as geometry.
                Coordinate[] cors=seg.getCoordinates();
                for (int i=1;i<cors.length;i++){
                    Object[] row=new Object[]{j++, 
                            df.format(cors[i-1].x), df.format(cors[i-1].y), 
                                  df.format(cors[i].x), df.format(cors[i].y)};
                    table.addRow(row);
                }
            }
            tblPoints.setModel(table);
            tblPoints.repaint();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(ImportLinesFromFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    private void btnOpenShapeFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenShapeFileActionPerformed
        JFileChooser fileOpen=new JFileChooser();
        fileOpen.setDialogTitle("Choose shape file to load into table.");
        fileOpen.setVisible(true);
        FileFilter filter=getTextFileFilter(".shp","Shape Files (*.shp)");
        fileOpen.setFileFilter(filter); 
        fileOpen.showOpenDialog(this);
        File iFile=fileOpen.getSelectedFile();
        if (iFile==null) return;
         
        if (!read_ShapeFile(iFile)){
            JOptionPane.showMessageDialog(this, "Selected shape file not with correct line geometry. please check it.");
        }
    }//GEN-LAST:event_btnOpenShapeFileActionPerformed

    private void btnDxfImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDxfImportActionPerformed
        JFileChooser fileOpen=new JFileChooser();
        fileOpen.setDialogTitle("Choose dxf file to load into table.");
        fileOpen.setVisible(true);
        FileFilter filter=getTextFileFilter(".dxf","DXF Files (*.dxf)");
        fileOpen.setFileFilter(filter);
        fileOpen.showOpenDialog(this);
        File iFile=fileOpen.getSelectedFile();
        if (iFile==null) return;
        try {
            List<LineString> segs= Dxf_Line_Reader.read_lines(iFile);
            if (segs.size()<1){
                JOptionPane.showMessageDialog(this, "Selected dxf file does not have line geometry. please check it.");
                return;
            }
            DecimalFormat df= new DecimalFormat("0.000");
            int j=1;
            //load data into table.
            DefaultTableModel table=(DefaultTableModel)tblPoints.getModel();
            table.setRowCount(0);//remove all rows.
            
            for (LineString seg:segs){
                double x1=seg.getStartPoint().getX();
                double y1=seg.getStartPoint().getY();
                double x2=seg.getEndPoint().getX();
                double y2=seg.getEndPoint().getY();
                Object[] row=new Object[]{j++, 
                            df.format(x1), df.format(y1), 
                                  df.format(x2), df.format(y2)};
                table.addRow(row);
            }
            tblPoints.setModel(table);
            tblPoints.repaint();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImportLinesFromFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImportLinesFromFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDxfImportActionPerformed

    private void btnShowInMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowInMapActionPerformed
        GeometryFactory geomFactory=new GeometryFactory();
        //append points into collection.
        for (int i=0;i<tblPoints.getRowCount();i++){
            append_Points_to_Layer(i, geomFactory,0);
        }
        //last point.
        int n=tblPoints.getRowCount()-1;
        append_Points_to_Layer(n, geomFactory,2);
        
        //append segments into collection.
        LineString[] segs=new LineString[tblPoints.getRowCount()];
        for (int i=0;i<tblPoints.getRowCount();i++){
            if (tblPoints.getValueAt(i, 1)==null ) continue;

            double x= ClsGeneral.getDoubleValue(tblPoints.getValueAt(i, 1).toString());//x-cordinate.
            double y= ClsGeneral.getDoubleValue(tblPoints.getValueAt(i, 2).toString());//y-cordinate.
            double x1= ClsGeneral.getDoubleValue(tblPoints.getValueAt(i, 3).toString());//x-cordinate.
            double y1= ClsGeneral.getDoubleValue(tblPoints.getValueAt(i, 4).toString());//y-cordinate.
            if (x==x1 &&  y==y1) continue;
            Coordinate[] co=new Coordinate[]{new Coordinate(x, y),new Coordinate(x1,y1)};
            segs[i]=geomFactory.createLineString(co);
        }
        
        byte is_newLine=1;
        for (LineString seg:segs){
            locatePointPanel.appendNewSegment(seg, is_newLine);
        }
        //refresh map.
        targetParcelsLayer.getMapControl().refresh();
        btnCreatePolygon.setEnabled(true);
    }//GEN-LAST:event_btnShowInMapActionPerformed

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        PublicMethod.deselect_All(segmentLayer);
        targetParcelsLayer.getMapControl().refresh();
        this.dispose();
    }//GEN-LAST:event_btnOKActionPerformed

    private void btnImportPointsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportPointsActionPerformed
        JFileChooser fileOpen = new JFileChooser();
        fileOpen.setDialogTitle("Choose text file to load in table.");
        fileOpen.setVisible(true);
        FileFilter filter = getTextFileFilter(".spo","Text Files (*.spo)");
        fileOpen.setFileFilter(filter);
        //FileNameExtensionFilter extFilter=new FileNameExtensionFilter("Text Files", "*.txt|*.csv");
        fileOpen.showOpenDialog(this);
        File iFile = fileOpen.getSelectedFile();
        if (iFile == null) {
            return;
        }
        //prepare table to input data.
        DefaultTableModel defTable = (DefaultTableModel) tblPoints.getModel();
        defTable.setRowCount(0);
        tblPoints.setModel(defTable);
        tblPoints.repaint();
        //fill table from text file.
        if (!readPointTextFileData(iFile)) {
            JOptionPane.showConfirmDialog(this, "File format is not ok, please check it.");
        }
    }

    private boolean readPointTextFileData(File iFile) {
        //fill data into table from text file.
        try {
            DefaultTableModel table = (DefaultTableModel) tblPoints.getModel();
            FileReader iReader = new FileReader(iFile);
            BufferedReader buff_reader = new BufferedReader((Reader) iReader);

            String txtline = buff_reader.readLine();
            DecimalFormat df = new DecimalFormat("0.000");//mm precision.
            //read data.
            List<Coordinate> cors=new ArrayList<Coordinate>();
            while (txtline != null && !txtline.isEmpty()) {
                String[] pt = txtline.split(",");
                if (pt.length < 2) {
                    return false;
                }
                if (pt != null && pt.length > 1) {
                    double x= ClsGeneral.getDoubleValue(pt[0]);//x-cordinate.
                    double y= ClsGeneral.getDoubleValue(pt[1]);//y-cordinate.
                    Coordinate co=new Coordinate(x,y);
                    cors.add(co);
                }
                txtline = buff_reader.readLine();
            }
            if (cors.size()<2) return false;
            //display into table.
            for (int i=1;i<cors.size();i++){
                double x1=cors.get(i-1).x;
                double y1=cors.get(i-1).y;
                double x2=cors.get(i).x;
                double y2=cors.get(i).y;
                Object[] row=new Object[]{i, 
                            df.format(x1), df.format(y1), 
                                  df.format(x2), df.format(y2)};
                table.addRow(row);
            }
            buff_reader.close();
            tblPoints.setModel(table);
            tblPoints.repaint();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImportLinesFromFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImportLinesFromFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }//GEN-LAST:event_btnImportPointsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddRow;
    private javax.swing.JButton btnCreatePolygon;
    private javax.swing.JButton btnDxfImport;
    private javax.swing.JButton btnImportLines;
    private javax.swing.JButton btnImportPoints;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnOpenShapeFile;
    private javax.swing.JButton btnRefreshMap;
    private javax.swing.JButton btnSaveTextFile;
    private javax.swing.JButton btnShowInMap;
    private javax.swing.JButton btnUndoSplit;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPoints;
    // End of variables declaration//GEN-END:variables
}
