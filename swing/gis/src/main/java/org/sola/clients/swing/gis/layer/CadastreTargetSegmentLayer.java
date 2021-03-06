/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.layer;

//import com.vividsolutions.jts.geom.Geometry;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JToolBar;
import org.geotools.geometry.jts.Geometries;
import org.geotools.map.extended.layer.ExtendedLayerGraphics;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.sola.clients.swing.gis.AreaObject;
import org.sola.clients.swing.gis.data.PojoDataAccess;
import org.sola.clients.swing.gis.ui.control.*;

/**
 *
 * @author Shrestha_Kabin
 */
public class CadastreTargetSegmentLayer  extends ExtendedLayerGraphics {
    //For point data.
    public static final String POINT_LAYER_FIELD_LABEL = "label";
    public static final String LAYER_FIELD_IS_POINT_SELECTED="point_selected";
    
    private static final String LAYER_NAME = "Target points";
    private static final String LAYER_STYLE_RESOURCE = "segmentPoints.xml";
    
    //For segment data.
    public static final String LAYER_FIELD_FID = "sid";
    public static final String LAYER_FIELD_SHAPE_LEN = "shape_length";
    public static final String LAYER_FIELD_PARCEL_ID="parcel_id";
    public static final String LAYER_FIELD_SELECTED = "is_selected";
    public static final String LAYER_FIELD_NEW_SEGMENT="is_newLine";
    
    private static final String LAYER_ATTRIBUTE_DEFINITION_POINT = String.format("%s:\"\",%s:0,%s:0",
            POINT_LAYER_FIELD_LABEL,LAYER_FIELD_IS_POINT_SELECTED, LAYER_FIELD_PARCEL_ID);
    
    public static final String LAYER_ATTRIBUTE_DEFINITION = String.format("%s:\"\",%s:\"\",%s:0,%s:0,%s:0",
            LAYER_FIELD_FID, LAYER_FIELD_SHAPE_LEN, LAYER_FIELD_PARCEL_ID,LAYER_FIELD_SELECTED,LAYER_FIELD_NEW_SEGMENT);   
    
    public static final String LAYER_SEGMENT_NAME = "Target Segments";
    public static final String LAYER_SEGMENT_STYLE_RESOURCE = "segmentnew.xml";
    
    private ExtendedLayerGraphics segmentLayer;
    //Declare form component to interact with this layer.
    private Component hostForm = null;
    
    //Store list of area to display in the parcel splitting form.
    private List<AreaObject> polyAreaList=new ArrayList<AreaObject>();

    public List<AreaObject> getPolyAreaList() {
        return polyAreaList;
    }

    public void setPolyArea(List<AreaObject> polyAreaList) {
        this.polyAreaList = polyAreaList;
    }
    
    public ExtendedLayerGraphics getSegmentLayer() {
        return segmentLayer;
    }

    public void setSegmentLayer(ExtendedLayerGraphics segmentLayer) {
        this.segmentLayer = segmentLayer;
    }

    public CadastreTargetSegmentLayer() throws InitializeLayerException {
        //create Target point layer.
        super(LAYER_NAME, Geometries.POINT, LAYER_STYLE_RESOURCE, LAYER_ATTRIBUTE_DEFINITION_POINT);
        this.setShowInToc(true);
        //Create Target segment.
        this.segmentLayer = new ExtendedLayerGraphics(
                LAYER_SEGMENT_NAME, Geometries.LINESTRING, LAYER_SEGMENT_STYLE_RESOURCE,LAYER_ATTRIBUTE_DEFINITION);
        this.getMapLayers().addAll(this.segmentLayer.getMapLayers());
    }

    /**
     * Reset the selected boundary
     */
    public void clearSelection() {
        this.removeFeatures();
        this.segmentLayer.removeFeatures();
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
