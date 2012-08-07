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

import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.administrative.LocSearchByMothParamsTO;
import org.sola.webservices.transferobjects.administrative.LocTO;
import org.sola.webservices.transferobjects.administrative.LocWithMothTO;

public class LocWithMothBean extends LocBean {

    public static final String MOTH_PROPERTY = "moth";
    private MothBasicBean moth;

    public LocWithMothBean() {
        super();
    }

    public MothBasicBean getMoth() {
        return moth;
    }

    public void setMoth(MothBasicBean moth) {
        MothBasicBean oldValue = this.moth;
        this.moth = moth;
        propertySupport.firePropertyChange(MOTH_PROPERTY, oldValue, moth);
    }

    public static LocWithMothBean createLoc(MothBasicBean moth, String pageNumber) {
        LocBean loc = new LocBean(moth, pageNumber);
        LocTO locTO = TypeConverters.BeanToTrasferObject(loc, LocTO.class);
        locTO = WSManager.getInstance().getAdministrative().saveLoc(locTO);
        LocWithMothBean locWithMoth = null;

        if (locTO != null) {
            locWithMoth = TypeConverters.TransferObjectToBean(
                    WSManager.getInstance().getAdministrative().getLocWithMoth(locTO.getId()),
                    LocWithMothBean.class, null);
        }
        return locWithMoth;
    }

    public static LocWithMothBean searchLocByMothAndPage(MothBasicBean moth, String pageNumber) {
        LocSearchByMothParamsBean searchParams = new LocSearchByMothParamsBean();
        searchParams.setMoth(moth);
        searchParams.setPageNumber(pageNumber);
        LocSearchByMothParamsTO searchParamsTO = TypeConverters.BeanToTrasferObject(searchParams, LocSearchByMothParamsTO.class);
        LocWithMothTO locTO = WSManager.getInstance().getAdministrative().getLocByPageNoAndMoth(searchParamsTO);
        return TypeConverters.TransferObjectToBean(locTO, LocWithMothBean.class, null);
    }
}
