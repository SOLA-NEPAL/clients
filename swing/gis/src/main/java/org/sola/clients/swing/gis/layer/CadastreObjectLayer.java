/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.layer;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geotools.geometry.jts.Geometries;
import org.geotools.map.extended.layer.ExtendedLayerGraphics;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.swing.gis.data.PojoDataAccess;
import org.sola.webservices.transferobjects.cadastre.CadastreObjectTO;

/**
 *
 * @author ShresthaKabin
 */
public class CadastreObjectLayer extends ExtendedLayerGraphics{
     //For point data.
    public static final String LAYER_FIELD_FID = "fid";
    public static final String  LAYER_FIELD_FIRST_PART = "first_part";
    public static final String  LAYER_FIELD_LAST_PART = "last_part";
    public static final String LAYER_FIELD_MAP_SHEET = "map_sheet";
    public static final String LAYER_FIELD_PARCEL_TYPE = "parcel_type";
    public static final String LAYER_FIELD_OFFICIAL_AREA = "official_area";
    
    private static final String LAYER_NAME = "Parcels";
    private static final String LAYER_STYLE_RESOURCE = "parcel.xml";

    private static final String LAYER_ATTRIBUTE_DEFINITION = String.format("%s:\"\",%s:\"\",%s:\"\",%s:\"\",%s:\"\"",
            LAYER_FIELD_FIRST_PART, LAYER_FIELD_LAST_PART, LAYER_FIELD_OFFICIAL_AREA,
            LAYER_FIELD_MAP_SHEET,LAYER_FIELD_PARCEL_TYPE);
    /**
     * Gets the form that is responsible with handling other attributes of features
     * @return 
     */
    public CadastreObjectLayer() throws InitializeLayerException {
        //create Target point layer.
        super(LAYER_NAME, Geometries.POLYGON, LAYER_STYLE_RESOURCE, LAYER_ATTRIBUTE_DEFINITION);
        this.setShowInToc(true);
    }

    /**
     * Reset the selected boundary
     */
    public void clearSelection() {
        this.removeFeatures();
    }
    
    /**
     * Gets the field index in the table of a certain field
     * @param fieldName
     * @return 
     */
    public int getFieldIndex(String fieldName) {
        if (fieldName.equals(LAYER_FIELD_FID)) {
            return 0;
        }
        return -1;
    }
    
    /**
     * Gets a reference to the data access
     * @return 
     */
    public PojoDataAccess getDataAccess() {
        return PojoDataAccess.getInstance();
    }
    
    public SimpleFeature addFeature(CadastreObjectTO parcel){
        HashMap<String, Object> fieldsWithValues = new HashMap<String, Object>();
        fieldsWithValues.put(
                LAYER_FIELD_FIRST_PART,parcel.getNameFirstpart());
        fieldsWithValues.put(
                LAYER_FIELD_LAST_PART, parcel.getNameLastpart());
        fieldsWithValues.put(
                LAYER_FIELD_MAP_SHEET, parcel.getMapSheetCode());
        fieldsWithValues.put(
                LAYER_FIELD_PARCEL_TYPE, parcel.getParcelType());

        DecimalFormat df=new DecimalFormat("#0.00");
        WKBReader wkb_reader = new WKBReader();
        Geometry geom_poly=null;
        try {
            geom_poly = wkb_reader.read(parcel.getGeomPolygon());
            fieldsWithValues.put(
                LAYER_FIELD_OFFICIAL_AREA, df.format(geom_poly.getArea()));
        } catch (ParseException ex) {
            Logger.getLogger(CadastreObjectLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.addFeature(parcel.getId(), geom_poly, fieldsWithValues);
    }
}
