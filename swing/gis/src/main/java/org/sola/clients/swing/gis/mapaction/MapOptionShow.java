/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.mapaction;

import java.util.List;
import org.geotools.swing.extended.Map;
import org.sola.clients.swing.gis.layer.CadastreObjectLayer;
import org.sola.clients.swing.gis.layer.ConstructionObjectLayer;
import org.sola.clients.swing.gis.ui.control.MapDisplayOptionForm;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author ShresthaKabin
 */
public class MapOptionShow extends ComponentShow{
    public final static String MAPACTION_NAME = "Show Map Display Options form";
    public MapDisplayOptionForm mapDisplayForm=null;
    
    private CadastreObjectLayer parcelLayer=null;
    private Map mapObj=null;
    private List<String> mapsheets=null;
    private ConstructionObjectLayer consLayer=null;

    public MapOptionShow(Map mapObj,CadastreObjectLayer parcelLayer,
               ConstructionObjectLayer consLayer, List<String> mapsheets) {
        super(mapObj, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.CADASTRE_DATASET_CHOOSE).getMessage(),
                "resources/MapOptions.png");
        
        this.mapObj=mapObj;
        this.parcelLayer=parcelLayer;
        this.mapsheets=mapsheets;
        this.consLayer=consLayer;
    }
    
    @Override
    public void onClick() {
        //Display segment list.
        if (mapDisplayForm==null){
            mapDisplayForm=new MapDisplayOptionForm(
                    mapObj,parcelLayer,consLayer,mapsheets);
        }
        mapDisplayForm.setVisible(true);
    }    
}
