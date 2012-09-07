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

import org.sola.clients.beans.AbstractBindingListBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.controls.SolaObservableList;
import org.sola.clients.beans.referencedata.OfficeBean;
import org.sola.webservices.transferobjects.EntityAction;

/**
 * Holds list of {@link MapSheetBean} objects.
 */
public class MapSheetListBean extends AbstractBindingListBean {

    public static final String SELECTED_MAPSHEET = "selectedMapSheet";
    SolaObservableList<MapSheetBean> mapSheets;
    private MapSheetBean selectedMapSheet;

    public MapSheetListBean() {
        mapSheets = new SolaObservableList<MapSheetBean>();
    }

    public MapSheetBean getSelectedMapSheet() {
        return selectedMapSheet;
    }

    public void setSelectedMapSheet(MapSheetBean selectedMapSheet) {
        MapSheetBean oldValue = this.selectedMapSheet;
        this.selectedMapSheet = selectedMapSheet;
        propertySupport.firePropertyChange(SELECTED_MAPSHEET, oldValue, this.selectedMapSheet);
    }

    public void updateSelectedMapSheet(MapSheetBean newMapSheet) {
        if (selectedMapSheet != null && newMapSheet != null && mapSheets.contains(selectedMapSheet)) {
            mapSheets.set(mapSheets.indexOf(selectedMapSheet), newMapSheet);
        }
    }

    public SolaObservableList<MapSheetBean> getMapSheets() {
        return mapSheets;
    }

    /**
     * Loads list of {@link MapSheetBean} by the current office ID.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public final void loadList(boolean createDummy) {
        if(OfficeBean.getCurrentOffice()!=null){
            loadList(OfficeBean.getCurrentOffice().getCode(), createDummy);
        }
    }

    /**
     * Loads list of {@link MapSheetBean} by provided office ID.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param officeId Office ID, for which to load map sheets.
     */
    public final void loadList(String officeId, boolean createDummy) {
        mapSheets.clear();
        mapSheets.addAll(CacheManager.getMapSheets(officeId));
        if (createDummy) {
            MapSheetBean dummy = new MapSheetBean();
            dummy.setId(null);
            dummy.setMapNumber(" ");
            dummy.setEntityAction(EntityAction.DISASSOCIATE);
            mapSheets.add(0, dummy);
        }
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
