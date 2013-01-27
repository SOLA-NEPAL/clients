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
package org.sola.clients.swing.gis.tool;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.extended.layer.ExtendedLayerGraphics;
import org.geotools.swing.event.MapMouseEvent;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.geotools.swing.tool.extended.ExtendedTool;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.sola.clients.swing.gis.SelectedParcelInfo;
import org.sola.clients.swing.gis.data.PojoDataAccess;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author Shrestha_Kabin
 */
public class listSelectedCadastreObjects extends ExtendedTool {

    private FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();
    private String geometryAttributeName = "geom";
    public final static String NAME = "list parcels";
    private String toolTip = MessageUtility.getLocalizedMessage(
            GisMessage.LIST_PARCELS).getMessage();
    //main class to store the selection information.
    private SelectedParcelInfo parcelSelector;
    private CadastreChangeTargetCadastreObjectLayer targetParcelsLayer;

    public listSelectedCadastreObjects(PojoDataAccess dataAccess,
            CadastreTargetSegmentLayer targetPointLayer,
            CadastreChangeTargetCadastreObjectLayer targetParcelsLayer) {
        this.setToolName(NAME);
        this.setIconImage("resources/chooseParcel.png");
        this.setToolTip(toolTip);
        this.targetParcelsLayer = targetParcelsLayer;
        parcelSelector = new SelectedParcelInfo(dataAccess);
        try {
            parcelSelector.setTargetLayers(targetPointLayer, this.targetParcelsLayer.getNeighbourParcels());
        } catch (InitializeLayerException ex) {
            Logger.getLogger(listSelectedCadastreObjects.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The action of this tool. If a cadastre object is already selected it will
     * be unselected, otherwise it will be selected.
     *
     * @param ev
     */
    @Override
    public void onMouseClicked(MapMouseEvent ev) {
        boolean found = false;
        try {
            // Try to get parcels from new parcels
            if (targetParcelsLayer.getNewParcelsLayer().isVisible()) {
                found = selectFeatures(ev, targetParcelsLayer.getNewParcelsLayer());
            }
            if (!found) {
                // Try to get parcels from targetLayer
                if (targetParcelsLayer.isVisible()) {
                    selectFeatures(ev, targetParcelsLayer);
                }
            }
        } catch (InitializeLayerException ex) {
            Logger.getLogger(listSelectedCadastreObjects.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Selects all feature on the target layer.
     */
    public void selectLayerFeatures(ExtendedLayerGraphics layer) {
        parcelSelector.selectLayerFeatures(layer);
    }

    public boolean selectFeatures(MapMouseEvent ev, ExtendedLayerGraphics layer) {
        // Construct a 5x5 pixel rectangle centred on the mouse click position
        Point screenPos = ev.getPoint();
        Rectangle screenRect = new Rectangle(screenPos.x - 2, screenPos.y - 2, 5, 5);

        /*
         * Transform the screen rectangle into bounding box in the coordinate
         * reference system of our map context. Note: we are using a naive
         * method here but GeoTools also offers other, more accurate methods.
         */
        AffineTransform screenToWorld = layer.getMapControl().getScreenToWorldTransform();
        Rectangle2D worldRect = screenToWorld.createTransformedShape(screenRect).getBounds2D();
        ReferencedEnvelope bbox = new ReferencedEnvelope(
                worldRect, layer.getMapControl().getMapContent().getCoordinateReferenceSystem());

        /*
         * Create a Filter to select features that intersect with the bounding
         * box
         */
        Filter filter = ff.intersects(ff.property(geometryAttributeName), ff.literal(bbox));

        /*
         * Use the filter to identify the selected features
         */
        try {
            SimpleFeatureCollection selectedFeatures = layer.getFeatureSource().getFeatures(filter);
            SimpleFeatureIterator iter = selectedFeatures.features();
            try {
                while (iter.hasNext()) {
                    SimpleFeature feature = iter.next();
                    parcelSelector.selectParcel(feature.getID(), feature.getDefaultGeometry(), ev.isControlDown());
                    return true;
                }

            } finally {
                iter.close();
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
