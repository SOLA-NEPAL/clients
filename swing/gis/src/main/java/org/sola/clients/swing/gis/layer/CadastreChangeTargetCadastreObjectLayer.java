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
package org.sola.clients.swing.gis.layer;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.CollectionEvent;
import org.geotools.feature.CollectionListener;
import org.geotools.geometry.jts.Geometries;
import org.geotools.map.extended.layer.ExtendedLayerGraphics;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.swing.gis.Messaging;
import org.sola.clients.swing.gis.beans.CadastreObjectTargetBean;
import org.sola.clients.swing.gis.beans.CadastreObjectTargetRedefinitionBean;
import org.sola.clients.swing.gis.data.PojoDataAccess;
import org.sola.common.messaging.GisMessage;
import org.sola.webservices.transferobjects.search.CadastreObjectSearchResultTO;

/**
 *
 * Layer of the target cadastre objects that is used during the cadastre change.
 * It maintains a list of cadastre objects being targeted for change.
 * 
 * @author Elton Manoku
 */
public class CadastreChangeTargetCadastreObjectLayer extends ExtendedLayerGraphics {
    public static final String LAYER_FIELD_FID="fid";
    public static final String LAYER_FIELD_AREA="shape_area";
    public static final String LAYER_FIELD_TARGET="target";
    private static final String LAYER_ATTRIBUTE_DEFINITION =
            String.format("%s:\"\",%s:\"\",%s:0",
                            LAYER_FIELD_FID,LAYER_FIELD_AREA,LAYER_FIELD_TARGET);
    
    private static final String LAYER_NAME = "Target Parcels";
    private static final String LAYER_STYLE_RESOURCE = "parcel_target.xml";
    private List<CadastreObjectTargetBean> cadastreObjectTargetList =
            new ArrayList<CadastreObjectTargetBean>();
    
    //for storing touching parcel by target parcel.
    private TargetNeighbourParcelLayer neigbhour_parcels=null;
    private CadastreChangeNewCadastreObjectLayer newParcelsLayer=null;

    public CadastreChangeNewCadastreObjectLayer getNewParcelsLayer() throws InitializeLayerException {
        return newParcelsLayer;
    }

    public void setNewParcelsLayer(CadastreChangeNewCadastreObjectLayer newParcelsLayer) {
        this.newParcelsLayer = newParcelsLayer;
    }

    public TargetNeighbourParcelLayer getNeighbourParcels() throws InitializeLayerException {
        if (neigbhour_parcels==null)
            neigbhour_parcels=new TargetNeighbourParcelLayer();
        return neigbhour_parcels;
    }

    public void setNeighbour_parcels(TargetNeighbourParcelLayer neigbhour_parcels) {
        this.neigbhour_parcels = neigbhour_parcels;
    }

    
    /**
     * Gets the list of target objects
     * @return 
     */
    public List<CadastreObjectTargetRedefinitionBean> getCadastreNeighboursList() {
        List<CadastreObjectTargetRedefinitionBean> targetList =
                new ArrayList<CadastreObjectTargetRedefinitionBean>();
        SimpleFeature feature = null;
        SimpleFeatureIterator iterator = (SimpleFeatureIterator) neigbhour_parcels.getFeatureCollection().features();
        while (iterator.hasNext()) {
            feature = iterator.next();
            CadastreObjectTargetRedefinitionBean targetBean = new CadastreObjectTargetRedefinitionBean();
            targetBean.setCadastreObjectId(feature.getID());
            targetBean.setGeomPolygon(wkbWriter.write((Geometry) feature.getDefaultGeometry()));
            targetList.add(targetBean);
        }
        iterator.close();
        return targetList;
    }
    
    /**
     * Constructor
     * 
     * @throws InitializeLayerException 
     */
    public CadastreChangeTargetCadastreObjectLayer() throws InitializeLayerException {
        super(LAYER_NAME, Geometries.POLYGON, LAYER_STYLE_RESOURCE,LAYER_ATTRIBUTE_DEFINITION);

        this.getFeatureCollection().addListener(new CollectionListener() {

            @Override
            public void collectionChanged(CollectionEvent ce) {
                featureCollectionChanged(ce);
            }
        });
    }

    /**
     * Gets list of cadastre object targets
     * @return 
     */
    public List<CadastreObjectTargetBean> getCadastreObjectTargetList() {
        return cadastreObjectTargetList;
    }

    /**
     * Sets list of cadastre object targets. It is called when the transaction is read from the 
     * server.
     * 
     * @param objectList 
     */
    public void setCadastreObjectTargetList(List<CadastreObjectTargetBean> objectList) {
        if (objectList.size() > 0) {
            List<String> ids = new ArrayList<String>();
            for (CadastreObjectTargetBean bean : objectList) {
                ids.add(bean.getCadastreObjectId());
            }
            
            List<CadastreObjectSearchResultTO> targetObjectList = this.getDataAccess().getSearchService().searchCadastreObecjtsByIds(ids);
            
            try {
                for (CadastreObjectSearchResultTO targetCOTO : targetObjectList) {
                    addFeature(targetCOTO.getId(), targetCOTO.getTheGeom(), null);
                }
            } catch (ParseException ex) {
                Messaging.getInstance().show(GisMessage.CADASTRE_CHANGE_ERROR_ADDTARGET_IN_START);
                org.sola.common.logging.LogUtility.log(
                        GisMessage.CADASTRE_CHANGE_ERROR_ADDTARGET_IN_START, ex);
            }
        }
    }

    /**
     * It handles the changes in the collection of features
     * @param ev 
     */
    private void featureCollectionChanged(CollectionEvent ev) {
        if (ev.getFeatures() == null) {
            return;
        }
        if (ev.getEventType() == CollectionEvent.FEATURES_ADDED) {
            for (SimpleFeature feature : ev.getFeatures()) {
                this.getCadastreObjectTargetList().add(this.newBean(feature));
            }
        } else if (ev.getEventType() == CollectionEvent.FEATURES_REMOVED) {
            for (SimpleFeature feature : ev.getFeatures()) {
                CadastreObjectTargetBean found = this.getBean(feature);
                if (found != null) {
                    this.getCadastreObjectTargetList().remove(found);
                }
            }
        }
    }

    /**
     * Changes an existing bean from its correspondent feature
     * @param targetBean
     * @param feature 
     */
    private void changeBean(CadastreObjectTargetBean targetBean, SimpleFeature feature) {
        targetBean.setCadastreObjectId(feature.getID());
    }

    /**
     * Gets a new bean from a feature
     * @param feature
     * @return 
     */
    private CadastreObjectTargetBean newBean(SimpleFeature feature) {
        CadastreObjectTargetBean bean = new CadastreObjectTargetBean();
        this.changeBean(bean, feature);
        return bean;
    }

    /**
     * Gets the corresponding bean of a feature
     * 
     * @param feature
     * @return 
     */
    private CadastreObjectTargetBean getBean(SimpleFeature feature) {
        CadastreObjectTargetBean bean = new CadastreObjectTargetBean();
        bean.setCadastreObjectId(feature.getID());
        int foundIndex = this.getCadastreObjectTargetList().indexOf(bean);
        if (foundIndex > -1) {
            bean = this.getCadastreObjectTargetList().get(foundIndex);
        } else {
            bean = null;
        }
        return bean;
    }
    
    /**
     * Gets a reference to the data access
     * @return 
     */
    public PojoDataAccess getDataAccess() {
        return PojoDataAccess.getInstance();
    }
    
    //<editor-fold defaultstate="collapsed" desc="CommentedBy Kabindra updating parcels.">
//    public void updateStatus_TargetParcel(){
//        String cmd="update cadastre.cadastre_object set ";
//        cmd += " status_code='historic'";
//        
//        //get feature collection.
//        SimpleFeatureCollection polys=this.getFeatureCollection();
//        SimpleFeatureIterator geom_iter=polys.features();
//        while (geom_iter.hasNext()){
//            SimpleFeature fea=geom_iter.next();
//            String fid=fea.getID();
//            String sql=cmd + " where id='" + fid + "'";
//            //execute normal query.
//            this.getDataAccess().getCadastreService().executeQuery(sql);
//        }
//    }
//    
//    public void updateGeometry_TouchingParcels(int srid) 
//                    throws InitializeLayerException{
//        //get feature collection.
//        SimpleFeatureCollection polys=this.getAffected_parcels().getFeatureCollection();
//        String geomfld=PublicMethod.theGeomFieldName(polys);
//        if (geomfld.isEmpty()) return;
//        
//        //create instance of binary writer.
//        WKBWriter wkb_writer=new WKBWriter();
//        
//        String cmd="update cadastre.cadastre_object set ";
//        SimpleFeatureIterator polyIterator=polys.features();
//        while (polyIterator.hasNext()){
//            SimpleFeature fea=polyIterator.next();
//            String fid=fea.getID();
//            Geometry thegeom=(Geometry)fea.getAttribute(geomfld);
//            String geom= WKBWriter.toHex(wkb_writer.write(thegeom));
//            String sql=cmd + " geom_polygon=";
//            sql += "(select st_geomfromwkb(('" + geom + "'::geometry)," + srid + "))";
//            sql+= " where id='" + fid + "'";
//            //execute normal query.
//            this.getDataAccess().getCadastreService().executeQuery(sql);
//        }
//    }
    //</editor-fold>
}
