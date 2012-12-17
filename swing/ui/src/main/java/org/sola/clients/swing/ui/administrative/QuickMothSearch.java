/**
 * ******************************************************************************************
 * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations (FAO).
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice,this list
 *       of conditions and the following disclaimer.
 *    2. Redistributions in binary form must reproduce the above copyright notice,this list
 *       of conditions and the following disclaimer in the documentation and/or other
 *       materials provided with the distribution.
 *    3. Neither the name of FAO nor the names of its contributors may be used to endorse or
 *       promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,STRICT LIABILITY,OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * *********************************************************************************************
 */
package org.sola.clients.swing.ui.administrative;

import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;
import org.sola.clients.beans.administrative.MothListBean;
import org.sola.clients.beans.administrative.MothSearchParamsBean;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.swing.common.controls.FreeTextSearch;
import org.sola.services.boundary.wsclients.AdministrativeClient;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.administrative.MothSearchParamsTO;

/**
 *
 * @author Kumar
 */

/**
 * Quick search control for Moth
 */
public class QuickMothSearch extends FreeTextSearch {
    private AdministrativeClient administrativeClient;
    private MothSearchParamsBean searchParams;
    
    public QuickMothSearch(){
        super();
        searchParams = new MothSearchParamsBean();
    }

    public void setSearchParams(MothSearchParamsBean searchParams) {
        if(searchParams==null){
            searchParams=new MothSearchParamsBean();
        }
        this.searchParams = searchParams;
    }
    
    @Override
    public void onNewSearchString(String searchString, DefaultListModel listModel) {
        if (this.administrativeClient == null) {
            this.administrativeClient = WSManager.getInstance().getAdministrative();
        }
        
        List<MothListBean> searchResult = new LinkedList<MothListBean>();
        MothSearchParamsTO params = TypeConverters.BeanToTrasferObject(
                searchParams, MothSearchParamsTO.class);
        params.setMothLuj("M");
        params.setVdcCode("27014");
        TypeConverters.TransferObjectListToBeanList(administrativeClient.searchMoths(params), 
                MothListBean.class, (List)searchResult);
        
        listModel.clear();
        
        for (MothListBean moth : searchResult) {
            listModel.addElement(moth);
        }
    }

    public MothSearchParamsBean getSearchParams() {
        return searchParams;
    }
}
