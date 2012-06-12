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
import org.sola.clients.beans.controls.SolaObservableList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;

/**
 *
 * @author KumarKhadka
 */
public class CadastreObjectListBean extends AbstractBindingListBean{
    public static final String SELECTED_CADASTRE = "selectedCadastreObjectBean";
    private CadastreObjectBean selectedCadastreObjectBean;
    SolaObservableList<CadastreObjectBean> cadastres; 

    public SolaObservableList<CadastreObjectBean> getCadastres() {
        if (cadastres == null) {
            cadastres = new SolaObservableList<CadastreObjectBean>();
        }
        return cadastres;
    }

    public void loadCadastreObjectList(String mapSheetCode) {
        TypeConverters.TransferObjectListToBeanList(WSManager.getInstance().getCadastreService().loadCadastreObjectList(mapSheetCode), CadastreObjectBean.class, (SolaObservableList) cadastres);
    }

    public CadastreObjectBean getSelectedCadastreObjectBean() {
        return selectedCadastreObjectBean;
    }

    public void setSelectedCadastreObjectBean(CadastreObjectBean selectedCadastreObjectBean) {
        CadastreObjectBean oldValue = this.selectedCadastreObjectBean;
        this.selectedCadastreObjectBean = selectedCadastreObjectBean;
        propertySupport.firePropertyChange(SELECTED_CADASTRE, oldValue, this.selectedCadastreObjectBean);
    }
    
}
