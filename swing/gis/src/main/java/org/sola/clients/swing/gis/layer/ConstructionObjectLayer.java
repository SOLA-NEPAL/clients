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
import org.sola.webservices.transferobjects.cadastre.ConstructionObjectTO;

/**
 *
 * @author ShresthaKabin
 */
public class ConstructionObjectLayer extends ExtendedLayerGraphics{
    public static final String LAYER_FIELD_FID = "cid";
    public static final String LAYER_FIELD_PARCEL = "parcel_id";
    public static final String LAYER_FIELD_Cons_TYPE = "const_type";
    public static final String LAYER_FIELD_OFFICIAL_AREA = "official_area";
    
    private static final String LAYER_NAME = "Constructions";
    private static final String LAYER_STYLE_RESOURCE = "construction.xml";

    private static final String LAYER_ATTRIBUTE_DEFINITION = String.format("%s:\"\",%s:\"\",%s:\"\",%s:\"\"",
            LAYER_FIELD_FID, LAYER_FIELD_PARCEL, LAYER_FIELD_Cons_TYPE,LAYER_FIELD_OFFICIAL_AREA);
    /**
     * Gets the form that is responsible with handling other attributes of features
     * @return 
     */
    public ConstructionObjectLayer() throws InitializeLayerException {
        //create Target point layer.
        super(LAYER_NAME, Geometries.GEOMETRY, LAYER_STYLE_RESOURCE, LAYER_ATTRIBUTE_DEFINITION);
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
    
    public SimpleFeature addFeature(ConstructionObjectTO consStruc){
        HashMap<String, Object> fieldsWithValues = new HashMap<String, Object>();
        fieldsWithValues.put(
                LAYER_FIELD_FID,consStruc.getCid());
        fieldsWithValues.put(
                LAYER_FIELD_PARCEL, consStruc.getId());
        fieldsWithValues.put(
                LAYER_FIELD_Cons_TYPE, consStruc.getConstype());

        DecimalFormat df=new DecimalFormat("#0.00");
        WKBReader wkb_reader = new WKBReader();
        Geometry geom_poly=null;
        try {
            geom_poly = wkb_reader.read(consStruc.getGeomPolygon());
            fieldsWithValues.put(
                LAYER_FIELD_OFFICIAL_AREA, df.format(geom_poly.getArea()));
        } catch (ParseException ex) {
            Logger.getLogger(CadastreObjectLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.addFeature(consStruc.getId(), geom_poly, fieldsWithValues);
    }
}
