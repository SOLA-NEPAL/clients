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
package org.sola.clients.beans.referencedata;

import java.util.List;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.AbstractBindingListBean;
import org.sola.clients.beans.AbstractCodeBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.controls.SolaCodeList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.EntityAction;
import org.sola.webservices.transferobjects.referencedata.DepartmentTO;

/**
 * Holds list of {@link DepartmentBean} objects.
 */
public class DepartmentListBean extends AbstractBindingListBean {

    public static final String SELECTED_DEPARTMENT_PROPERTY = "selectedDepartment";
    private SolaCodeList<DepartmentBean> departments;
    private DepartmentBean selectedDepartment;

    /**
     * Default constructor.
     */
    public DepartmentListBean() {
        this(false);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public DepartmentListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param excludedCodes Codes, which should be skipped while filtering.
     */
    public DepartmentListBean(boolean createDummy, String... excludedCodes) {
        super();
        departments = new SolaCodeList<DepartmentBean>(excludedCodes);
        loadList(createDummy);
    }

    /**
     * Loads list of {@link DepartmentBean}, related to the current office.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public final void loadList(boolean createDummy) {
        if (WSManager.getInstance().getAdminService() != null) {
            loadList(createDummy, OfficeBean.getCurrentOffice().getCode());
        }
    }

    /**
     * Loads list of {@link DepartmentBean}.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param officeCode Office code which departments to load
     */
    public final void loadList(boolean createDummy, String officeCode) {
        if (WSManager.getInstance().getReferenceDataService() != null) {
            loadCodeList(DepartmentBean.class, departments,
                    CacheManager.getDepartments(officeCode), createDummy);
        }
    }

    public final void loadUntranslatedList(String officeCode) {
        departments.clear();
        if(officeCode!=null && !officeCode.isEmpty()){
            TypeConverters.TransferObjectListToBeanList(
            WSManager.getInstance().getReferenceDataService().getDepartments(officeCode, null),
            DepartmentBean.class, (List)departments);
        }
    }
    
    public void setExcludedCodes(String... codes) {
        departments.setExcludedCodes(codes);
    }

    public ObservableList<DepartmentBean> getDepartments() {
        return departments;
    }

    public ObservableList<DepartmentBean> getDepartmentsFiltered() {
        return departments.getFilteredList();
    }
    
    public DepartmentBean getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(DepartmentBean selectedDepartment) {
        DepartmentBean oldValue = this.selectedDepartment;
        this.selectedDepartment = selectedDepartment;
        propertySupport.firePropertyChange(SELECTED_DEPARTMENT_PROPERTY, oldValue, this.selectedDepartment);
    }
    
    public void setSelectedDepartment(String deparmentCode) {
        if(deparmentCode == null || deparmentCode.isEmpty()){
            return;
        }
        
        for(DepartmentBean department : getDepartments()){
            if(department.getCode().equals(deparmentCode)){
                setSelectedDepartment(department);
                return;
            }
        }
    }
    
     public void removeSelectedDepartment(){
        if(selectedDepartment!=null){
            selectedDepartment.setEntityAction(EntityAction.DELETE);
            AbstractCodeBean.saveRefData(selectedDepartment, DepartmentTO.class);
        }
    }
}
