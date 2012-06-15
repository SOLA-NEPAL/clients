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
import org.sola.clients.beans.controls.SolaCodeList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;

/**
 *
 * @author KumarKhadka
 */
public class MapSheetListBean extends AbstractBindingListBean {

    ObservableList<MapSheetBean> mapSheets;

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
}
