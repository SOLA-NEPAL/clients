/**
 * ******************************************************************************************
 * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations
 * (FAO). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,this
 * list of conditions and the following disclaimer. 2. Redistributions in binary
 * form must reproduce the above copyright notice,this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. 3. Neither the name of FAO nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT,STRICT LIABILITY,OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * *********************************************************************************************
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.beans;

import java.util.ArrayList;
import java.util.List;
import org.sola.clients.beans.administrative.BaUnitSearchResultBean;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.validation.ValidationResultBean;
import org.sola.clients.swing.gis.data.PojoDataAccess;
import org.sola.clients.swing.gis.to.TransactionCadastreChangeExtraTO;
import org.sola.common.mapping.MappingManager;
import org.sola.webservices.transferobjects.transaction.TransactionCadastreChangeTO;
import org.sola.clients.beans.cadastre.CadastreObjectBean;
import org.sola.common.StringUtility;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.search.CadastreObjectSearchResultTO;

/**
 * Data bean representing a transaction of cadastre change.
 *
 * @author Elton Manoku
 */
public class TransactionCadastreChangeBean extends TransactionBean {

    private List<CadastreObjectBean> cadastreObjectList = new ArrayList<CadastreObjectBean>();
    private List<CadastreObjectTargetBean> cadastreObjectTargetList =
            new ArrayList<CadastreObjectTargetBean>();
    private List<SurveyPointBean> surveyPointList = new ArrayList<SurveyPointBean>();
    private List<CadastreObjectTargetRedefinitionBean> cadastreObjectNeighboursList =
            new ArrayList<CadastreObjectTargetRedefinitionBean>();

    public List<CadastreObjectTargetRedefinitionBean> getCadastreObjectNeighboursList() {
        return cadastreObjectNeighboursList;
    }

    public void setCadastreObjectNeighboursList(List<CadastreObjectTargetRedefinitionBean> cadastreObjectNeighboursList) {
        this.cadastreObjectNeighboursList = cadastreObjectNeighboursList;
    }

    /**
     * Gets list of new cadastre objects
     */
    public List<CadastreObjectBean> getCadastreObjectList() {
        return cadastreObjectList;
    }

    /**
     * Sets list of new cadastre objects
     */
    public void setCadastreObjectList(List<CadastreObjectBean> cadastreObjectList) {
        this.cadastreObjectList = cadastreObjectList;
    }

    /**
     * Gets list of target cadastre objects
     */
    public List<CadastreObjectTargetBean> getCadastreObjectTargetList() {
        return cadastreObjectTargetList;
    }

    /**
     * Sets list of target cadastre objects
     */
    public void setCadastreObjectTargetList(List<CadastreObjectTargetBean> cadastreObjectTargetList) {
        this.cadastreObjectTargetList = cadastreObjectTargetList;
    }

    /**
     * Sets list of target cadastre objects from the list of {@link CadastreObjectBean}s
     */
    public void setCadastreObjectTargetListByCoList(List<CadastreObjectBean> cadastreObjects) {
        cadastreObjectTargetList.clear();
        for (CadastreObjectBean cadastreObjectBean : cadastreObjects) {
            CadastreObjectTargetBean target = new CadastreObjectTargetBean();
            target.setDatasetId(cadastreObjectBean.getDatasetId());
            target.setCadastreObjectId(cadastreObjectBean.getId());
            cadastreObjectTargetList.add(target);
        }
    }

    /**
     * Sets list of target cadastre objects from the list of {@link BaUnitSearchResultBean}s
     */
    public void setCadastreObjectTargetListByBaUnits(List<BaUnitSearchResultBean> baUnits) {
        if (baUnits == null) {
            return;
        }

        ArrayList<String> ids = new ArrayList<String>();

        for (BaUnitSearchResultBean baUnit : baUnits) {
            ids.add(baUnit.getCadastreObjectId());
        }
        setCadastreObjectTargetListByIds(ids);
    }

    /**
     * Sets list of target cadastre objects by provided list of IDs.
     */
    public void setCadastreObjectTargetListByIds(List<String> ids) {
        if (ids == null) {
            return;
        }

        cadastreObjectTargetList.clear();
        List<CadastreObjectSearchResultTO> results = WSManager.getInstance().getSearchService().searchCadastreObecjtsByIds(ids);
        if (results != null) {
            for (CadastreObjectSearchResultTO searchResult : results) {
                CadastreObjectTargetBean target = new CadastreObjectTargetBean();
                target.setCadastreObjectId(searchResult.getId());
                target.setDatasetId(searchResult.getDatasetId());
                cadastreObjectTargetList.add(target);
            }
        }
    }

    /**
     * Checks the list of target cadastre objects to have dataset and be the same for all.
     */
    public boolean checkDatasetForCadastreObjectTargetList(){
        String datasetId = "";
        for (CadastreObjectTargetBean targetBean : cadastreObjectTargetList) {
            if(StringUtility.isEmpty(targetBean.getDatasetId())){
                return false;
            }
            if(!datasetId.isEmpty() && !targetBean.getDatasetId().equalsIgnoreCase(datasetId)){
                return false;
            }
            datasetId = targetBean.getDatasetId();
        }
        return true;
    }
    
    /** Returns Dataset ID from the target cadastre objects list. First value will be returned. */
    public String getDatasetIdFromCadastreObjectTargetList(){
        for (CadastreObjectTargetBean targetBean : cadastreObjectTargetList) {
            return targetBean.getDatasetId();
        }
        return null;
    }
    
    /**
     * Gets list of survey points added in transaction
     */
    public List<SurveyPointBean> getSurveyPointList() {
        return surveyPointList;
    }

    /**
     * Sets list of survey points added in transaction
     */
    public void setSurveyPointList(List<SurveyPointBean> surveyPointList) {
        this.surveyPointList = surveyPointList;
    }

    @Override
    public TransactionCadastreChangeTO getTO() {
        TransactionCadastreChangeExtraTO to = new TransactionCadastreChangeExtraTO();
        MappingManager.getMapper().map(this, to);
        return to;
    }

    @Override
    public List<ValidationResultBean> save() {
        return TypeConverters.TransferObjectListToBeanList(
                PojoDataAccess.getInstance().getCadastreService().saveTransactionCadastreChange(
                this.getTO()), ValidationResultBean.class, null);
    }

    @Override
    public boolean validate() {
        for (CadastreObjectBean cadastreObject : getCadastreObjectList()) {
            if (cadastreObject.getAddress() == null
                    || cadastreObject.getAddress().getVdcCode() == null
                    || cadastreObject.getAddress().getWardNo() == null
                    || cadastreObject.getMapSheetId() == null
                    || cadastreObject.getParcelno() == null) {
                MessageUtility.displayMessage(ClientMessage.CADASTRE_PARCEL_ADDRESS_IS_NULL);
                return false;
            }
        }
        return true;
    }

    /**
     * Returns {@link TransactionCadastreChangeBean} by provided application
     * service ID
     */
    public static TransactionCadastreChangeBean getTransaction(String serviceId) {
        return PojoDataAccess.getInstance().getTransactionCadastreChange(serviceId);
    }
}
