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
package org.sola.clients.swing.gis.layer;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.geotools.feature.CollectionEvent;
import org.geotools.feature.CollectionListener;
import org.geotools.geometry.jts.Geometries;
import org.geotools.map.extended.layer.ExtendedLayerEditor;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.beans.cadastre.CadastreObjectBean;
import org.sola.clients.swing.gis.Messaging;
import org.sola.clients.swing.gis.beans.CadastreObjectTargetBean;
import org.sola.common.messaging.GisMessage;

/**
 * Layer that has the features of the new cadastre objects during the cadastre
 * change
 *
 * @author Elton Manoku
 */
public class CadastreChangeNewCadastreObjectLayer extends ExtendedLayerEditor {

    public static final String LAYER_FIELD_FID = "fid";
    public static final String LAYER_FIELD_FIRST_PART = "first_part";
    public static final String LAYER_FIELD_LAST_PART = "last_part";
    public static final String LAYER_FIELD_OFFICIAL_AREA = "official_area";
    public static final String LAYER_NAME = "New Parcels";
    public static final String LAYER_FIELD_MAP_SHEET = "map_sheet";
    public static final String LAYER_FIELD_PARCEL_TYPE = "parcel_typecode";
    public static final String LAYER_FIELD_SELECTED = "is_selected";
    private static final String LAYER_STYLE_RESOURCE = "parcel_new.xml";
    private static final String LAYER_ATTRIBUTE_DEFINITION = String.format("%s:\"\",%s:\"\",%s:\"\",%s:\"\",%s:\"\",%s:0",
            LAYER_FIELD_FIRST_PART, LAYER_FIELD_LAST_PART, LAYER_FIELD_OFFICIAL_AREA,
            LAYER_FIELD_MAP_SHEET, LAYER_FIELD_PARCEL_TYPE, LAYER_FIELD_SELECTED);
    private ObservableList<CadastreObjectBean> cadastreObjectList = ObservableCollections.observableList(new ArrayList<CadastreObjectBean>());
    private Integer firstPartGenerator = 1;
    private Integer fidGenerator = 1;
    private static final String LAST_PART_FORMAT = "SP %s";
    private String lastPart = "";
    private boolean updateCadastreObjectsList = true;
    private CadastreChangeTargetCadastreObjectLayer targetParcelsLayer;

    /**
     * Constructor for the layer.
     *
     * @param applicationNumber The application number of the service that
     * starts the transaction where the layer is used. This number is used in
     * the definition of new parcel number
     * @throws InitializeLayerException
     */
    public CadastreChangeNewCadastreObjectLayer(String applicationNumber,
            CadastreChangeTargetCadastreObjectLayer targetParcelsLayer)
            throws InitializeLayerException {
        super(LAYER_NAME, Geometries.POLYGON, LAYER_STYLE_RESOURCE, LAYER_ATTRIBUTE_DEFINITION);
        this.targetParcelsLayer = targetParcelsLayer;
        lastPart = String.format(LAST_PART_FORMAT, applicationNumber);
        postInit();
    }

    /**
     * It initializes event handlers and form hosting
     */
    private void postInit() {
        this.getFeatureCollection().addListener(new CollectionListener() {

            @Override
            public void collectionChanged(CollectionEvent ce) {
                featureCollectionChanged(ce);
            }
        });
    }

    /**
     * Gets the list of new cadastre objects
     *
     * @return
     */
    public ObservableList<CadastreObjectBean> getCadastreObjectList() {
        return cadastreObjectList;
    }

    /**
     * Sets the list of new cadastre objects. This is used if the transaction is
     * read from the server.
     *
     * @param cadastreObjectList
     */
    public void setCadastreObjectList(List<CadastreObjectBean> cadastreObjectList) {
        this.cadastreObjectList.clear();
        if (!cadastreObjectList.isEmpty()) {
            try {
                updateCadastreObjectsList = false;
                for (CadastreObjectBean cadastreObjectBean : cadastreObjectList) {
                    HashMap<String, Object> fieldsWithValues = new HashMap<String, Object>();
                    fieldsWithValues.put(LAYER_FIELD_FIRST_PART, cadastreObjectBean.getNameFirstPart());
                    fieldsWithValues.put(LAYER_FIELD_LAST_PART, cadastreObjectBean.getNameLastPart());
                    fieldsWithValues.put(LAYER_FIELD_MAP_SHEET, cadastreObjectBean.getMapSheetId());
                    fieldsWithValues.put(LAYER_FIELD_OFFICIAL_AREA, cadastreObjectBean.getOfficialAreaFormatted());
                    fieldsWithValues.put(LAYER_FIELD_SELECTED, "0");

                    this.addFeature(cadastreObjectBean.getId(), cadastreObjectBean.getGeomPolygon(), fieldsWithValues, false);
                    this.cadastreObjectList.add(cadastreObjectBean);
                }

                updateCadastreObjectsList = true;
            } catch (ParseException ex) {
                Messaging.getInstance().show(
                        GisMessage.CADASTRE_CHANGE_ERROR_ADDINGNEWCADASTREOBJECT_IN_START);
                org.sola.common.logging.LogUtility.log(
                        GisMessage.CADASTRE_CHANGE_ERROR_ADDINGNEWCADASTREOBJECT_IN_START, ex);
            }
        }
    }

    /**
     * It adds a feature of the cadastre object. The fid is not taken into
     * account. It is always regenerated in the ordinal number. This is to
     * assure the uniqueness and to have it also user friendly.
     *
     * @param fid
     * @param geom
     * @param fieldsWithValues
     * @return
     */
    @Override
    public SimpleFeature addFeature(String fid, Geometry geom, HashMap<String, Object> fieldsWithValues) {
        return addFeature(fid, geom, fieldsWithValues, true);
    }

    @Override
    public SimpleFeature addFeature(String fid, Geometry geom, HashMap<String, Object> fieldsWithValues, boolean refreshmap) {
        
        if (fid == null) {
            fid = fidGenerator.toString();
        }
        fidGenerator++;

        DecimalFormat df = new DecimalFormat("0.00");
        if (fieldsWithValues == null) {
            fieldsWithValues = new HashMap<String, Object>();
            fieldsWithValues.put(LAYER_FIELD_FIRST_PART, firstPartGenerator.toString());
            fieldsWithValues.put(LAYER_FIELD_LAST_PART, lastPart);
            fieldsWithValues.put(LAYER_FIELD_SELECTED, 0);
            fieldsWithValues.put(LAYER_FIELD_MAP_SHEET, 0);
            fieldsWithValues.put(LAYER_FIELD_PARCEL_TYPE, 0);
            fieldsWithValues.put(LAYER_FIELD_OFFICIAL_AREA, df.format(geom.getArea()));
        }
        SimpleFeature addedFeature = super.addFeature(fid, geom, fieldsWithValues, refreshmap);
        firstPartGenerator++;
        return addedFeature;
    }

    /**
     * It handles the changes in the collection of features
     *
     * @param ev
     */
    private void featureCollectionChanged(CollectionEvent ev) {
        if (ev.getFeatures() == null || !updateCadastreObjectsList) {
            return;
        }
        if (ev.getEventType() == CollectionEvent.FEATURES_ADDED) {
            for (SimpleFeature feature : ev.getFeatures()) {
                this.getCadastreObjectList().add(newBean(feature));
            }
        } else if (ev.getEventType() == CollectionEvent.FEATURES_REMOVED) {
            for (SimpleFeature feature : ev.getFeatures()) {
                CadastreObjectBean found = getBean(feature);
                if (found != null) {
                    this.getCadastreObjectList().remove(found);
                }
            }
        } else if (ev.getEventType() == CollectionEvent.FEATURES_CHANGED) {
            for (SimpleFeature feature : ev.getFeatures()) {
                CadastreObjectBean found = getBean(feature);
                if (found != null) {
                    changeBean(found, feature, false);
                }
            }
        }
    }

    /**
     * Gets a new bean from a feature
     *
     * @param feature
     * @return
     */
    private CadastreObjectBean newBean(SimpleFeature feature) {
        CadastreObjectBean coBean = new CadastreObjectBean();
        changeBean(coBean, feature, true);
        return coBean;
    }

    /**
     * Changes an existing bean from its correspondent feature
     *
     * @param coBean
     * @param feature
     */
    private void changeBean(CadastreObjectBean coBean, SimpleFeature feature, boolean isNew) {
        // Assign properties from the target parcel
        if(isNew && targetParcelsLayer!=null && targetParcelsLayer.getCadastreObjectTargetList()!=null && 
                targetParcelsLayer.getCadastreObjectTargetList().size() > 0){
            CadastreObjectTargetBean targetBean = targetParcelsLayer.getCadastreObjectTargetList().get(0);
            CadastreObjectBean coTargetBean = CadastreObjectBean.getParcel(targetBean.getCadastreObjectId());
            if(coTargetBean!=null){
                coBean.copyFromObject(coTargetBean);
                coBean.resetPropertiesForNewParcel();
            }
        }
        
        coBean.setId(feature.getID());
        coBean.setDatasetId(getMapControl().getDatasetId());
        coBean.setNameFirstPart(feature.getAttribute(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_FIRST_PART).toString());
        coBean.setNameLastPart(feature.getAttribute(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_LAST_PART).toString());
        coBean.setMapSheetId(feature.getAttribute(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_MAP_SHEET).toString());
        coBean.setOfficialArea(new BigDecimal(feature.getAttribute(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_OFFICIAL_AREA).toString()));
        coBean.setGeomPolygon(wkbWriter.write((Geometry) feature.getDefaultGeometry()));
    }

    /**
     * Gets the corresponding bean of a feature
     *
     * @param feature
     * @return
     */
    private CadastreObjectBean getBean(SimpleFeature feature) {
        CadastreObjectBean coBean = new CadastreObjectBean();
        coBean.setId(feature.getID());
        int foundIndex = this.getCadastreObjectList().indexOf(coBean);
        if (foundIndex > -1) {
            coBean = this.getCadastreObjectList().get(foundIndex);
        } else {
            coBean = null;
        }
        return coBean;
    }
}
