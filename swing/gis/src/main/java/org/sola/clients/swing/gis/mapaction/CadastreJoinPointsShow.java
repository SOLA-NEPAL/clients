/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.mapaction;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.geotools.swing.extended.Map;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.sola.clients.swing.gis.PublicMethod;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.clients.swing.gis.ui.control.JoinPointMethodForm;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author Shrestha_Kabin
 */
public class CadastreJoinPointsShow extends ComponentShow{
    public final static String MAPACTION_NAME = "Join Point Form Show";
    public JoinPointMethodForm pointListForm=null;
    
    private CadastreTargetSegmentLayer segmentLayer=null;
    private CadastreChangeTargetCadastreObjectLayer targetParcelLayer=null;
    private Map mapObj=null;

    public CadastreJoinPointsShow(Map mapObj, 
                CadastreTargetSegmentLayer segmentLayer, 
                        CadastreChangeTargetCadastreObjectLayer targetParcelLayer) {
        super(mapObj, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.CADASTRE_JOIN_POINT_SHOW).getMessage(),
                "resources/JoinPoints.png");
        
        this.mapObj=mapObj;
        this.pointListForm= (JoinPointMethodForm) pointListForm;
        this.targetParcelLayer=targetParcelLayer;
        this.segmentLayer=segmentLayer;
    }
    
    @Override
    public void onClick() {
        int parcel_count=PublicMethod.count_Parcels_Selected(targetParcelLayer);
        if (parcel_count<1){
            JOptionPane.showMessageDialog(null, "Select the concerned parcel and proceed again.");
            return;
        }
        if (parcel_count>1){
            JOptionPane.showMessageDialog(null, "Only one parcel is allowed to select for split action.");
            return;
        }
        //Make all layers off except the target layers.
        PublicMethod.maplayerOnOff(mapObj, false);
        
        try {
            //Display segment list.
            if (pointListForm==null)
                pointListForm=new JoinPointMethodForm(segmentLayer, targetParcelLayer);
   
            pointListForm.setVisible(true);
            pointListForm.showPointListInTable();
            //Display segment list.
            pointListForm.getLocatePointPanel().reload_Data();
        } catch (InitializeLayerException ex) {
            Logger.getLogger(CadastreJoinPointsShow.class.getName()).log(Level.SEVERE, null, ex);
        }catch (NoSuchMethodException ex) {
            Logger.getLogger(CadastreJoinPointsShow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
