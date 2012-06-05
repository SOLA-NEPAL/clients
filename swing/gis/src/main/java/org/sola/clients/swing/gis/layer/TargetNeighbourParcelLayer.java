/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.layer;

//import com.vividsolutions.jts.geom.Geometry;
import org.geotools.geometry.jts.Geometries;
import org.geotools.map.extended.layer.ExtendedLayerGraphics;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.sola.clients.swing.gis.data.PojoDataAccess;

/**
 *
 * @author Shrestha_Kabin
 */
public class TargetNeighbourParcelLayer  extends ExtendedLayerGraphics {
    //For point data.
    public static final String POINT_LAYER_FIELD_LABEL = "label";
    
    private static final String LAYER_NAME = "Target Neighbours";
    private static final String LAYER_STYLE_RESOURCE = "affected_parcels.xml";
    
    //For segment data.
    public static final String LAYER_FIELD_FID = "sid";
    public static final String LAYER_FIELD_PARCEL_ID="parcel_id";

    private static final String LAYER_ATTRIBUTE_DEFINITION = String.format("%s:\"\",%s:0",
            POINT_LAYER_FIELD_LABEL, LAYER_FIELD_PARCEL_ID);
    /**
     * Gets the form that is responsible with handling other attributes of features
     * @return 
     */
    public TargetNeighbourParcelLayer() throws InitializeLayerException {
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
}
