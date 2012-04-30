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
package org.sola.clients.beans.system;

import java.util.ArrayList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.controls.SolaList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.admin.NepaliMonthTO;
import org.sola.webservices.transferobjects.EntityAction;

/**
 *
 * @author KumarKhadka
 */
public class NepaliMonthListBean extends AbstractBindingBean{
     public static final String SELECTED_MONTH = "selectedMonth";
    SolaList<NepaliMonthBean> months;
    NepaliMonthBean selectedMonth;
    public NepaliMonthListBean(){
        super();
    }

    public SolaList<NepaliMonthBean> getMonths() {
        if(months==null){
            months=new SolaList<NepaliMonthBean>();
        }
        return months;
    }
    public ObservableList<NepaliMonthBean> getFilteredMonth(){
        return getMonths().getFilteredList();
    }
    public NepaliMonthBean getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(NepaliMonthBean selectedMonth) {
        NepaliMonthBean oldValue=this.selectedMonth;
        this.selectedMonth = selectedMonth;
        propertySupport.firePropertyChange(SELECTED_MONTH, oldValue, this.selectedMonth);
    }
    
    
    public void loadMonthList(int nepYear){
        TypeConverters.TransferObjectListToBeanList(WSManager.getInstance().getAdminService().getNepaliMonths(nepYear), NepaliMonthBean.class, (List)months) ;
    }
    
    public void saveNepaliMonth(){
        List<NepaliMonthTO> nepaliTO = new ArrayList<NepaliMonthTO>();
        TypeConverters.BeanListToTransferObjectList((List)months, nepaliTO, NepaliMonthTO.class);
        TypeConverters.TransferObjectListToBeanList(WSManager.getInstance().getAdminService().saveNepaliMonth(nepaliTO), NepaliMonthBean.class, (List)months);
    }
    
    public void addNepaliMonth(NepaliMonthBean nepMonth){
        getMonths().addAsNew(nepMonth);
    }
    
    public void deleteSelectedMonth(){
        if(selectedMonth!=null){
            getMonths().safeRemove(selectedMonth, EntityAction.DELETE);
        }
    }
}
