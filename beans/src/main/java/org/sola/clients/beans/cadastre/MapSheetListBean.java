/*
 * Copyright 2012 Food and Agriculture Organization of the United Nations (FAO).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sola.clients.beans.cadastre;

import java.util.ArrayList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.AbstractBindingListBean;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.EntityAction;

/**
 *
 * @author KumarKhadka
 */
public class MapSheetListBean extends AbstractBindingListBean {

    public static final String SELECTED_MAPSHEET = "selectedMapSheet";
    ObservableList<MapSheetBean> mapSheets;
    private MapSheetBean selectedMapSheet;
    
//    public MapSheetListBean()
//    {
//        mapSheets= ObservableCollections.observableList(new ArrayList<MapSheetBean>());
//    }
            

    public MapSheetBean getSelectedMapSheet() {
        return selectedMapSheet;
    }

    public void setSelectedMapSheet(MapSheetBean selectedMapSheet) {
        MapSheetBean oldValue = this.selectedMapSheet;
        this.selectedMapSheet = selectedMapSheet;
        propertySupport.firePropertyChange(SELECTED_MAPSHEET, oldValue, this.selectedMapSheet);
    }

    public ObservableList<MapSheetBean> getMapSheets() {
        if (mapSheets == null) {
            mapSheets = ObservableCollections.observableList(new ArrayList<MapSheetBean>());
        }
        return mapSheets;
    }

    public void loadMapSheetList() {
        TypeConverters.TransferObjectListToBeanList(WSManager.getInstance().getCadastreService().getMapSheetList(), MapSheetBean.class, (List) mapSheets);
    }
    
     public void loadMapSheetList(String mapSheetType) {
        TypeConverters.TransferObjectListToBeanList(WSManager.getInstance().getCadastreService().loadMapSheet(mapSheetType), MapSheetBean.class, (List) mapSheets);
    }

    /**
     * add new beans to the list.
     *
     * @param mapSheetBean mapsheetbean instance to be added.
     */
    public void addMapSheet(MapSheetBean mapSheetBean) {
        if (mapSheetBean == null) {
            return;
        }
        mapSheets.add(mapSheetBean);
    }
/**
 * remove selectedMapSheet from the list
 */
    
    public void removeSelected() {
        if (selectedMapSheet != null) {
            selectedMapSheet.setEntityAction(EntityAction.DELETE);
            selectedMapSheet.saveMapSheet();
             mapSheets.remove(selectedMapSheet);
        }
    }
}
