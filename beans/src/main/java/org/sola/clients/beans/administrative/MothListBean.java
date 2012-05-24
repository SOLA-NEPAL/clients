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
package org.sola.clients.beans.administrative;

import java.util.ArrayList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;

/**
 *
 * @author KumarKhadka
 */
public class MothListBean extends AbstractBindingBean {

    public static final String SELECTED_MOTH = "selectedMoth";
    private MothBean selectedMoth;
    ObservableList<MothBean> moths;

    public ObservableList<MothBean> getMoths() {
        if (moths == null) {
            moths = ObservableCollections.observableList(new ArrayList<MothBean>());
        }
        return moths;
    }

    public void loadMothList(String vdcCode, String mothLuj) {
        TypeConverters.TransferObjectListToBeanList(WSManager.getInstance().getAdministrative().getMoths(vdcCode, mothLuj), MothBean.class, (List) moths);
    }

    public MothBean getSelectedMoth() {
        return selectedMoth;
    }

    public void setSelectedMoth(MothBean selectedMoth) {
        MothBean oldValue = this.selectedMoth;
        this.selectedMoth = selectedMoth;
        propertySupport.firePropertyChange(SELECTED_MOTH, oldValue, this.selectedMoth);
    }
}
