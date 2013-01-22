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
package org.sola.clients.swing.gis.ui.controlsbundle;

import org.geotools.swing.extended.exception.InitializeLayerException;
import org.sola.clients.swing.gis.beans.TransactionCadastreChangeBean;
import org.sola.clients.swing.gis.data.PojoDataAccess;
import org.sola.clients.swing.gis.layer.CadastreChangeNewCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreChangeNewSurveyPointLayer;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.clients.swing.gis.mapaction.*;
import org.sola.clients.swing.gis.tool.*;
import org.sola.webservices.transferobjects.cadastre.CadastreObjectTO;

/**
 * A control bundle that is used for cadastre change process. The necessary
 * tools and layers are added in the bundle.
 *
 * @author Elton Manoku
 */
public final class ControlsBundleForCadastreChange extends ControlsBundleForTransaction {

    private TransactionCadastreChangeBean transactionBean;
    private String applicationNumber = "";
    private CadastreChangeTargetCadastreObjectLayer targetParcelsLayer;
    private CadastreTargetSegmentLayer targetSegmentLayer;
    private CadastreChangeNewCadastreObjectLayer newCadastreObjectLayer;
    private CadastreChangeNewSurveyPointLayer newPointsLayer;
    private listSelectedCadastreObjects listParcel;
    
    /**
     * Constructor. It sets up the bundle by adding layers and tools that are
     * relevant. Finally, it zooms in the interested zone. The interested zone
     * is defined in the following order: <br/> If bean has survey points it is
     * zoomed there, otherwise if baUnitId is present it is zoomed there else it
     * is zoomed in the application location.
     *
     * @param applicationNumber The application number that is used for
     * generating new property identifiers
     * @param transactionBean The transaction bean. If this is already populated
     * it means the transaction is being opened again for change.
     * @param baUnitId Id of the property that is defined in the application as
     * a target for this cadastre change.
     * @param applicationLocation Location of application that starts the
     * cadastre change
     */
    public ControlsBundleForCadastreChange(String applicationNumber,  TransactionCadastreChangeBean transactionBean) {
        this.applicationNumber = applicationNumber;
        this.transactionBean = transactionBean;
        if (this.transactionBean == null) {
            this.transactionBean = new TransactionCadastreChangeBean();
        }
        
        Setup(PojoDataAccess.getInstance(), transactionBean.getDatasetIdFromCadastreObjectTargetList()); 
        enableDatasetSelectionTool(false);
        targetParcelsLayer.zoomToLayer();
        listParcel.selectLayerFeatures(targetParcelsLayer);
     }

    @Override
    public TransactionCadastreChangeBean getTransactionBean() {
        transactionBean.setCadastreObjectList(this.newCadastreObjectLayer.getCadastreObjectList());
        transactionBean.setSurveyPointList(this.newPointsLayer.getSurveyPointList());
        transactionBean.setCadastreObjectTargetList(this.targetParcelsLayer.getCadastreObjectTargetList());
        return transactionBean;
    }
    
    //partially by Kabindra.
    @Override
    protected void addLayers() throws InitializeLayerException {
        super.addLayers(); 
        
        //add vertical bar.//By Kabindra
        this.getMap().addMapAction(new BlankTool(true),this.getToolbar(), true);
        
        //segment new layer.//By Kabindra
        this.targetSegmentLayer = new CadastreTargetSegmentLayer();
        this.getMap().addLayer(this.targetSegmentLayer);
        this.getMap().addLayer(this.targetSegmentLayer.getSegmentLayer());
        
        this.targetParcelsLayer = new CadastreChangeTargetCadastreObjectLayer();
        this.newCadastreObjectLayer = new CadastreChangeNewCadastreObjectLayer(applicationNumber, targetParcelsLayer);
        this.newCadastreObjectLayer.setCadastreObjectList(this.transactionBean.getCadastreObjectList());
        this.getMap().addLayer(newCadastreObjectLayer);
        
        this.newPointsLayer = new CadastreChangeNewSurveyPointLayer(this.newCadastreObjectLayer);
        this.getMap().addLayer(newPointsLayer);
        this.newPointsLayer.setSurveyPointList(this.transactionBean.getSurveyPointList());
        
        this.targetParcelsLayer.setNew_parcels(this.newCadastreObjectLayer);//get reference of the newparcel layer object.
        this.targetParcelsLayer.setCadastreObjectTargetList(transactionBean.getCadastreObjectTargetList());
        this.getMap().addLayer(targetParcelsLayer);
        
        //selection affected parcels.//By Kabindra
        this.targetParcelsLayer.getNeighbourParcels().setVisible(true);
        this.getMap().addLayer(this.targetParcelsLayer.getNeighbourParcels());
    }

   
    @Override
    protected void addToolsAndCommands() {
        genericSOLA_Tools();
        //------------------------
        //add vertical bar.
        this.getMap().addMapAction(new BlankTool(true),this.getToolbar(), true);
        this.getMap().addMapAction(new ZoomPreviousTool(this.getMap()), this.getToolbar(), true);
        this.getMap().addMapAction(new SearchParcelFormShow(this.getPojoDataAccess(),
                this.getMap(),targetSegmentLayer, targetParcelsLayer), this.getToolbar(), true);
        //new tool for parcel selection.
        listParcel = new listSelectedCadastreObjects(this.getPojoDataAccess(), targetSegmentLayer,targetParcelsLayer);
        this.getMap().addTool(listParcel, this.getToolbar(), true,listSelectedCadastreObjects.NAME);
        //add deselect tool.
        this.getMap().addMapAction(new DeselectALL(this.getMap(),targetSegmentLayer,targetParcelsLayer), this.getToolbar(), true);
        //add toolbar for the one point and Area method show forms.
        this.getMap().addMapAction(new CadastreOnePointAreaFormShow(this.getMap(), targetSegmentLayer,targetParcelsLayer
                    ,this.getToolbar()), this.getToolbar(), true);
        //add toolbar for the multiple join show forms.
        this.getMap().addMapAction(new CadastreTwoPointFormShow(this.getMap(),  targetSegmentLayer,targetParcelsLayer,
                 this.getToolbar()),this.getToolbar(), true);
        //add toolbar for offset method.
        this.getMap().addMapAction(new MultiOffestFormShow(this.getMap(), targetSegmentLayer,targetParcelsLayer,
                 this.getToolbar()),this.getToolbar(), true);
        //add toolbar for parcel merging.
        this.getMap().addMapAction(new MergeParcelFormShow(this.getMap(),targetSegmentLayer,targetParcelsLayer), this.getToolbar(), true);  
        //add toolbar for equal area splitting method.
        this.getMap().addMapAction(new EqualAreaMethodFormShow(this.getMap(),targetSegmentLayer,targetParcelsLayer,
                 this.getToolbar()), this.getToolbar(), true);
        //add toolbar for one side, direction and area method splitting.
        this.getMap().addMapAction(new OneSideDirectionAreaShow(this.getMap(),targetSegmentLayer,targetParcelsLayer,
                 this.getToolbar()),this.getToolbar(), true);
        //add toolbar for import lines from line,shape and dxf file.
        this.getMap().addMapAction(new ImportShapeFileShow(this.getMap(), targetSegmentLayer,targetParcelsLayer,
                 this.getToolbar()),this.getToolbar(), true);
    }
    
    private void genericSOLA_Tools() {
        CadastreChangeSelectParcelTool selectParcelTool = new CadastreChangeSelectParcelTool(this.getPojoDataAccess());
        selectParcelTool.setTargetParcelsLayer(targetParcelsLayer);
        this.getMap().addTool(selectParcelTool, this.getToolbar(), true);
        
        this.getMap().addMapAction(
                new CadastreChangePointSurveyListFormShow(
                this.getMap(), this.newPointsLayer.getHostForm()),
                this.getToolbar(), true);
        
        CadastreChangeNodeTool nodelinkingTool = new CadastreChangeNodeTool(newPointsLayer);
        nodelinkingTool.getTargetSnappingLayers().add(this.targetParcelsLayer);
        this.getMap().addTool(nodelinkingTool, this.getToolbar(), true);

        CadastreChangeNewParcelTool newParcelTool = new CadastreChangeNewParcelTool(this.newCadastreObjectLayer);
        newParcelTool.getTargetSnappingLayers().add(newPointsLayer);
        this.getMap().addTool(newParcelTool, this.getToolbar(), true);

        this.getMap().addMapAction(new CadastreChangeNewCadastreObjectListFormShow(
                this.getMap(), this.newCadastreObjectLayer), this.getToolbar(), true);

        CadastreBoundarySelectTool cadastreBoundarySelectTool =
                new CadastreBoundarySelectTool(
                this.cadastreBoundaryPointLayer,
                this.newCadastreObjectLayer,
                this.newCadastreObjectLayer.getVerticesLayer());
        this.getMap().addTool(cadastreBoundarySelectTool, this.getToolbar(), true);
        super.addToolsAndCommands();
        this.cadastreBoundaryEditTool.setTargetLayer(this.newCadastreObjectLayer);
        this.cadastreBoundaryEditTool.getTargetSnappingLayers().add(this.targetParcelsLayer);
    }

    //uncomment all lines to restore default tools of generic sola.
    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        this.getMap().getMapActionByName(CadastreChangeSelectParcelTool.NAME).setEnabled(!readOnly);
        this.getMap().getMapActionByName(
                CadastreChangePointSurveyListFormShow.MAPACTION_NAME).setEnabled(!readOnly);
        this.getMap().getMapActionByName(CadastreChangeNodeTool.NAME).setEnabled(!readOnly);
        this.getMap().getMapActionByName(CadastreChangeNewParcelTool.NAME).setEnabled(!readOnly);
        this.getMap().getMapActionByName(
                CadastreChangeNewCadastreObjectListFormShow.MAPACTION_NAME).setEnabled(!readOnly);
    }

    @Override
    public void show_Selected_Parcel_onMap(CadastreObjectTO selected_parcel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
