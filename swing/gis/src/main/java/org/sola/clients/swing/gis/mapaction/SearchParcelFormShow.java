/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.mapaction;

import org.geotools.swing.extended.Map;
import org.sola.clients.swing.gis.data.PojoDataAccess;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.clients.swing.gis.ui.control.SelectParcelForm;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author ShresthaKabin
 */
public class SearchParcelFormShow extends ComponentShow{
    public final static String MAPACTION_NAME = "Search Parcel Form";
    public SelectParcelForm searchForm=null;
    
    private CadastreTargetSegmentLayer pointsLayer = null;
    private CadastreChangeTargetCadastreObjectLayer targetParcelsLayer = null;
    private PojoDataAccess dataAccess=null;
    
    public SearchParcelFormShow(PojoDataAccess dataAccess, Map mapObj,
            CadastreTargetSegmentLayer pointsLayer,
                    CadastreChangeTargetCadastreObjectLayer targetParcelsLayer ) {
        super(mapObj, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.CADASTRE_SEARCH_PARCEL).getMessage(),
                "resources/searchParcel.png");
        
        this.dataAccess=dataAccess;
        this.pointsLayer=pointsLayer;
        this.targetParcelsLayer=targetParcelsLayer;
    }
    
    @Override
    public void onClick() {
        try {
            //Display segment list.
            if (searchForm==null)
                searchForm=new SelectParcelForm(
                        dataAccess,pointsLayer,targetParcelsLayer);

            searchForm.setVisible(true);
        }
        catch (Exception ex){}
    }
}
