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

import com.vividsolutions.jts.geom.Geometry;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geometry.DirectPosition2D;

import org.geotools.swing.event.MapMouseEvent;
import org.geotools.swing.tool.extended.ExtendedTool;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.swing.gis.SelectedParcelInfo;
import org.sola.clients.swing.gis.data.PojoDataAccess;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;
import org.sola.webservices.transferobjects.cadastre.CadastreObjectTO;
/**
 *
 * @author Shrestha_Kabin
 */
public class listSelectedCadastreObjects extends ExtendedTool {

    public final static String NAME = "list parcels";
    private String toolTip = MessageUtility.getLocalizedMessage(
            GisMessage.LIST_PARCELS).getMessage();
    
    //main class to store the selection information.
    private SelectedParcelInfo parcel_selected;
    
    public listSelectedCadastreObjects(PojoDataAccess dataAccess, 
            CadastreTargetSegmentLayer targetPointLayer,
            CadastreChangeTargetCadastreObjectLayer targetParcelsLayer) {
        this.setToolName(NAME);
        this.setIconImage("resources/chooseParcel.png");
        this.setToolTip(toolTip);
        parcel_selected= new SelectedParcelInfo(dataAccess);
        parcel_selected.setTargetLayers(targetPointLayer, targetParcelsLayer);
    }

    /**
     * The action of this tool. If a cadastre object is already selected it will
     * be unselected, otherwise it will be selected.
     *
     * @param ev
     */
    @Override
    public void onMouseClicked(MapMouseEvent ev) {
        DirectPosition2D pos = ev.getWorldPos();
        CadastreObjectTO cadastreObject = this.parcel_selected.getDataAccess().
                getCadastreService().getCadastreObjectByPoint(
                pos.x, pos.y, this.getMapControl().getSrid());
        
        parcel_selected.display_Selected_Parcel(cadastreObject.getId(), cadastreObject.getGeomPolygon() ,ev.isControlDown());
    }
    
    /** Selects all feature on the target layer. */
    public void selectTargetLayerFeatures(){
        parcel_selected.selectTargetLayerFeatures();
    }
}
