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
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.search.RestrictionInfoParamsTO;

public class RestrictionInfoListBean extends AbstractBindingBean {
    private List<RestrictionInfoBean> restrictionInfoList;
    
    public RestrictionInfoListBean(){
        super();
        restrictionInfoList = new ArrayList<RestrictionInfoBean>();
    }

    public List<RestrictionInfoBean> getRestrictionInfoList() {
        return restrictionInfoList;
    }
    
    public void search(String refNumber, String refDate, String langCode){
        RestrictionInfoParamsBean params = new RestrictionInfoParamsBean();
        params.setLangCode(langCode);
        params.setRefDate(refDate);
        params.setRefNumber(refNumber);
        search(params);
    }
    
    public void search(RestrictionInfoParamsBean params){
        restrictionInfoList.clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchRestrictionInfo(
                TypeConverters.BeanToTrasferObject(params, RestrictionInfoParamsTO.class)), 
                RestrictionInfoBean.class, (List)restrictionInfoList);
    }
}
