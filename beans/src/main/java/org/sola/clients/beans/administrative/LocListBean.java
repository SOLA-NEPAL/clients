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

/**
 *
 * @author KumarKhadka
 */
public class LocListBean extends AbstractBindingBean {

    ObservableList<LocBean> locs;

    public ObservableList<LocBean> getLocs() {
        if (locs == null) {
            locs = ObservableCollections.observableList(new ArrayList<LocBean>());
        }
        return locs;
    }

    public LocBean getLoc(MothBean mothBean, int panaNo) {
        List<LocBean> locList = mothBean.getLocList();
        for (LocBean locBean : locList) {
            if (locBean.getPanaNo() == panaNo) {
                return locBean;
            }
        }
        return null;
    }
}
