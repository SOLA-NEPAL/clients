/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.desktop.cadastre;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import org.sola.clients.beans.cadastre.CadastreObjectBean;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.swing.gis.data.PojoDataAccess;
import org.sola.webservices.transferobjects.cadastre.CadastreObjectTO;

/**
 *
 * @author ShresthaKabin
 */
public class Select_Parcel_Form extends javax.swing.JDialog {
    //temporary variable.
    private CadastreObjectTO cadastreObject=null;
    private Geometry the_Polygon=null;
    
    private Method search_Completed_Trigger=null;
    private Object method_holder_object=null;
    
    public void set_SearchCompletedTriggers(Method search_completed, Object method_holder){
        this.search_Completed_Trigger=search_completed;
        this.method_holder_object=method_holder;
    }
    /**
     * Creates new form SelectParcelForm
     */
    public Select_Parcel_Form() {
        super();
        initComponents();
        //initialize variables.
        set_SearchCompletionTrigger();
    }

    private void set_SearchCompletionTrigger(){
        Class[] cls=new Class[]{CadastreObjectBean.class,
            String.class,String.class,String.class};
        Class workingForm=this.getClass();
        Method taskCompletion=null;
        try {
            taskCompletion = workingForm.getMethod("refresh_Cadastre_Object_Searching", cls);
        } catch (Exception ex) {
            Logger.getLogger(Select_Parcel_Form.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        parcelSearchPanel.set_SearchCompletedTriggers(taskCompletion, this);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstParcelInfo = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        btnOK = new javax.swing.JButton();
        parcelSearchPanel = new org.sola.clients.swing.ui.cadastre.CadastreObjectPanel();
        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane1.setViewportView(lstParcelInfo);

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel1.setText("Details of the selected Parcel:");

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        parcelSearchPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        headerPanel1.setTitleText("Select Parcel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(parcelSearchPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(18, 18, 18)
                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(parcelSearchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                    .addComponent(btnOK))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void refresh_Cadastre_Object_Searching(CadastreObjectBean parcel,
            String ddc, String vdc, String wardno){
        if (parcel==null) return;
        
        cadastreObject= TypeConverters.BeanToTrasferObject(parcel, CadastreObjectTO.class);
        //display details of the cadastre object found.
        DefaultListModel def_model= new DefaultListModel();
        def_model.clear();
        def_model.addElement("Parcel Number: " + String.valueOf(parcel.getParcelno()));
        def_model.addElement("District: " + ddc);
        def_model.addElement("VDC: " + vdc);
        def_model.addElement("Ward Number: " + wardno);
        //get the geometry object.
        WKBReader wkb_reader = new WKBReader();
        DecimalFormat df=new DecimalFormat("0.000");
        try {
            the_Polygon = wkb_reader.read(cadastreObject.getGeomPolygon());
        } catch (ParseException ex) {
            Logger.getLogger(Select_Parcel_Form.class.getName()).log(Level.SEVERE, null, ex);
        }
        def_model.addElement("Parcel Area: " + df.format(the_Polygon.getArea())+ " sqr. meters");
        def_model.addElement("Parcel Key: " +
                    parcel.getNameFirstpart() + "-" + parcel.getNameLastpart());
        lstParcelInfo.setModel(def_model);
    }
    
    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        //By Kabindra
        try {
            search_Completed_Trigger.invoke(method_holder_object,
                new Object[]{cadastreObject});  
        } catch (Exception e) {
        }
        this.dispose();
    }//GEN-LAST:event_btnOKActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOK;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lstParcelInfo;
    private org.sola.clients.swing.ui.cadastre.CadastreObjectPanel parcelSearchPanel;
    // End of variables declaration//GEN-END:variables
}
