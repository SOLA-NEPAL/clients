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
package org.sola.clients.beans.cache;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.AbstractCodeBean;
import org.sola.clients.beans.AbstractIdBean;
import org.sola.clients.beans.cadastre.MapSheetBean;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.referencedata.*;
import org.sola.clients.beans.security.RoleBean;
import org.sola.clients.beans.system.LanguageBean;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;
import org.sola.services.boundary.wsclients.AbstractWSClient;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.AbstractTO;

public final class CacheManager {

    private static Cache cache = new Cache();
    private static final String LIST_POSTFIX = "_LIST";
    private static final String MAP_POSTFIX = "_MAP";
    /**
     * Cache key of the {@link RequestTypeBean} collection.
     */
    public static final String REQUEST_TYPES_KEY = RequestTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the code/displayValue map based on {@link RequestTypeBean}
     * collection.
     */
    public static final String REQUEST_TYPES_MAP_KEY = RequestTypeBean.class.getName() + MAP_POSTFIX;
    /**
     * Cache key of the {@link CommunicationTypeBean} collection.
     */
    public static final String COMMUNICATION_TYPES_KEY = CommunicationTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the code/displayValue map based on
     * {@link CommunicationTypeBean} collection.
     */
    public static final String COMMUNICATION_TYPES_MAP_KEY = CommunicationTypeBean.class.getName() + MAP_POSTFIX;
    /**
     * Cache key of the {@link GenderTypeBean} collection.
     */
    public static final String GENDER_TYPES_KEY = GenderTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the code/displayValue map based on {@link GenderTypeBean}
     * collection.
     */
    public static final String GENDER_TYPES_MAP_KEY = GenderTypeBean.class.getName() + MAP_POSTFIX;
    /**
     * Cache key of the {@link SourceTypeBean} collection.
     */
    public static final String SOURCE_TYPES_KEY = SourceTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the code/displayValue map based on {@link SourceTypeBean}
     * collection.
     */
    public static final String SOURCE_TYPES_MAP_KEY = SourceTypeBean.class.getName() + MAP_POSTFIX;
    /**
     * Cache key of the {@link ApplicationStatusTypeBean} collection.
     */
    public static final String APP_STATUS_TYPE_CODES_KEY = ApplicationStatusTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the code/displayValue map based on
     * {@link ApplicationStatusTypeBean} collection.
     */
    public static final String APP_STATUS_TYPE_CODES_MAP_KEY = ApplicationStatusTypeBean.class.getName() + MAP_POSTFIX;
    /**
     * Cache key of the {@link ApplicationActionTypeBean} collection.
     */
    public static final String APP_ACTION_TYPE_CODES_KEY = ApplicationActionTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link ServiceStatusTypeBean} collection.
     */
    public static final String SERVICE_STATUS_TYPE_CODES_KEY = ServiceStatusTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link ServiceActionTypeBean} collection.
     */
    public static final String SERVICE_ACTION_TYPE_CODES_KEY = ServiceActionTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link PartyTypeBean} collection.
     */
    public static final String PARTY_TYPE_CODES_KEY = PartyTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link PartyRoleTypeBean} collection.
     */
    public static final String PARTY_ROLE_TYPE_CODES_KEY = PartyRoleTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link IdTypeBean} collection.
     */
    public static final String ID_TYPE_CODES_KEY = IdTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link BaUnitTypeBean} collection.
     */
    public static final String BA_UNIT_TYPE_CODES_KEY = BaUnitTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link MortgageTypeBean} collection.
     */
    public static final String MORTGAGE_TYPE_CODES_KEY = MortgageTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link RRRGroupTypeBean} collection.
     */
    public static final String RRR_GROUP_TYPE_CODES_KEY = RrrGroupTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link RRRTypeBean} collection.
     */
    public static final String RRR_TYPE_CODES_KEY = RrrTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link RegistrationStatusTypeBean} collection.
     */
    public static final String REGISTRATION_STATUS_TYPE_CODES_KEY = RegistrationStatusTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link CadastreObjectTypeBean} collection.
     */
    public static final String CADASTRE_OBJECT_TYPE_CODES_KEY = CadastreObjectTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link ChangeStatusTypeBean} collection.
     */
    public static final String CHANGE_STATUS_TYPE_CODES_KEY = ChangeStatusTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link PartyRoleTypeBean} collection.
     */
    public static final String ROLE_TYPES_KEY = PartyRoleTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link RrrTypeActionBean} collection.
     */
    public static final String TYPE_ACTIONS_KEY = TypeActionBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link RoleBean} collection.
     */
    public static final String ROLES_KEY = RoleBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link LanguageBean} collection.
     */
    public static final String LANGUAGE_KEY = LanguageBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link RequestCategoryTypeBean} collection.
     */
    public static final String REQUEST_CATEGORY_TYPE_KEY = RequestCategoryTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link BrSeverityTypeBean} collection.
     */
    public static final String BR_SEVERITY_TYPE_KEY = BrSeverityTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link BrTechnicalTypeBean} collection.
     */
    public static final String BR_TECHNICAL_TYPE_KEY = BrTechnicalTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link BrValidationTargetTypeBean} collection.
     */
    public static final String BR_VALIDATION_TARGET_TYPE_KEY = BrValidationTargetTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link BaUnitRelTypeBean} collection.
     */
    public static final String BA_UNIT_REL_TYPE_KEY = BaUnitRelTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link DistrictBean} collection.
     */
    public static final String DISTRICT_KEY = DistrictBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link OfficeBean} collection.
     */
    public static final String OFFICE_KEY = OfficeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link VdcBean} collection.
     */
    public static final String VDC_KEY = VdcBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link MapSheetBean} collection.
     */
    public static final String MAP_SHEET_KEY = MapSheetBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link DepartmentBean} collection.
     */
    public static final String DEPARTMENT_KEY = DepartmentBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link RestrictionTypeBean} collection.
     */
    public static final String RESTRICTION_TYPE_KEY = RestrictionTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link RestrictionReasonBean} collection.
     */
    public static final String RESTRICTION_REASON_KEY = RestrictionReasonBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link RestrictionReleaseReasonBean} collection.
     */
    public static final String RESTRICTION_RELEASE_REASON_KEY = RestrictionReleaseReasonBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link RestrictionOfficeBean} collection.
     */
    public static final String RESTRICTION_OFFICE_KEY = RestrictionOfficeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link LandTypeBean} collection.
     */
    public static final String LAND_TYPES_KEY = LandTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link TenancyTypeBean} collection.
     */
    public static final String TENANT_TYPE_KEY = TenancyTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link OwnershipTypeBean} collection.
     */
    public static final String OWNER_TYPE_KEY = OwnerTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link OwnershipTypeBean} collection.
     */
    public static final String OWNERSHIP_TYPE_KEY = OwnershipTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link LandUseBean} collection.
     */
    public static final String LAND_USE_KEY = LandUseBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link LandClassBean} collection.
     */
    public static final String LAND_CLASS_KEY = LandClassBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link GuthiNameBean} collection.
     */
    public static final String GUTHI_NAME_KEY = GuthiNameBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link BuildingUnitTypeBean} collection.
     */
    /**
     * Cache key of the {@link LandClassBean} collection.
     */
    public static final String BUILDING_UNIT_TYPE_KEY = BuildingUnitTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link AreaUnitTypeBean} collection.
     */
    public static final String AREA_UNIT_TYPE_KEY = AreaUnitTypeBean.class.getName() + LIST_POSTFIX;
    /**
     * Cache key of the {@link FiscalYearBean} collection.
     */
    public static final String FISCAL_YEAR_KEY = FiscalYearBean.class.getName() + LIST_POSTFIX;
    private static final String CACHED_VDCS_KEY = "cachedVdcs" + LIST_POSTFIX;
    private static final String CACHED_MAP_SHEETS_KEY = "cachedMapSheets" + LIST_POSTFIX;
    private static final String GET_APPLICATION_STATUS_TYPES = "getApplicationStatusTypes";
    private static final String GET_SOURCE_TYPES = "getSourceTypes";
    private static final String GET_COMMUNICATION_TYPES = "getCommunicationTypes";
    private static final String GET_GENDER_TYPES = "getGenderTypes";
    private static final String GET_REQUEST_TYPES = "getRequestTypes";
    private static final String GET_APPLICATION_ACTION_TYPES = "getApplicationActionTypes";
    private static final String GET_SERVICE_ACTION_TYPES = "getServiceActionTypes";
    private static final String GET_SERVICE_STATUS_TYPES = "getServiceStatusTypes";
    private static final String GET_PARTY_TYPES = "getPartyTypes";
    private static final String GET_PARTY_ROLES = "getPartyRoles";
    private static final String GET_ID_TYPES = "getIdTypes";
    private static final String GET_BA_UNIT_TYPES = "getBaUnitTypes";
    private static final String GET_MORTGAGE_TYPES = "getMortgageTypes";
    private static final String GET_RRR_GROUP_TYPES = "getRrrGroupTypes";
    private static final String GET_RRR_TYPES = "getRrrTypes";
    private static final String GET_REGISTRATION_STATUS_TYPES = "getRegistrationStatusTypes";
    private static final String GET_CHANGE_STATUS_TYPES = "getChangeStatusTypes";
    private static final String GET_SOURCE_BA_UNIT_RELATION_TYPES = "getSourceBaUnitRelationTypes";
    private static final String GET_CADASTRE_OBJECT_TYPES = "getCadastreObjectTypes";
    private static final String GET_TYPE_ACTIONS = "getTypeActions";
    private static final String GET_ROLES = "getRoles";
    private static final String GET_LANGUAGES = "getLanguages";
    private static final String GET_REQUEST_CATEGORY_TYPES = "getRequestCategoryTypes";
    private static final String GET_BR_SEVERITY_TYPES = "getBrSeverityTypes";
    private static final String GET_BR_TECHNICAL_TYPES = "getBrTechnicalTypes";
    private static final String GET_BR_VALIDATION_TARGET_TYPES = "getBrValidationTargetTypes";
    private static final String GET_BA_UNIT_REL_TYPES = "getBaUnitRelTypes";
    private static final String GET_DISTRICTS = "getDistricts";
    private static final String GET_OFFICES = "getOffices";
    private static final String GET_MAPSHEETS = "getMapSheets";
    private static final String GET_VDCS = "getVdcs";
    private static final String GET_LAND_TYPES = "getLandTypes";
    private static final String GET_RESTRICTION_REASONS = "getRestrictionReasons";
    private static final String GET_RESTRICTION_RELEASE_REASONS = "getRestrictionReleaseReasons";
    private static final String GET_RESTRICTION_OFFICES = "getRestrictionOffices";
    private static final String GET_OWNER_TYPES = "getOwnerTypes";
    private static final String GET_OWNERSHIP_TYPES = "getOwnershipTypes";
    private static final String GET_TENANT_TYPES = "getTenantTypes";
    private static final String GET_LAND_USES = "getLandUses";
    private static final String GET_LAND_CLASSES = "getLandClasses";
    private static final String GET_GUTHI_NAMES = "getGuthiNames";
    private static final String GET_AREA_UNIT_TYPES = "getAreaUnitTypes";
    private static final String GET_BUILDING_UNIT_TYPES = "getBuildingUnitTypes";
    private static final String GET_FISCAL_YEARS = "getFiscalYears";

    public static List<DepartmentBean> getDepartments(String officeCode) {
        List<DepartmentBean> result = new ArrayList<DepartmentBean>();
        String key = DEPARTMENT_KEY + officeCode;

        if (cache.contains(key)) {
            result = (List<DepartmentBean>) cache.get(key);
        } else {
            TypeConverters.TransferObjectListToBeanList(
                    WSManager.getInstance().getReferenceDataService().getDepartments(officeCode),
                    DepartmentBean.class, (List) result);
            cache.put(key, result);
        }
        return result;
    }

    public static List<VdcBean> getVdcs(String districtCode) {
        List<VdcBean> result = new ArrayList<VdcBean>();
        String key = VDC_KEY + districtCode;

        if (cache.contains(key)) {
            result = (List<VdcBean>) cache.get(key);
        } else {
            if (WSManager.getInstance().getReferenceDataService() != null) {
                TypeConverters.TransferObjectListToBeanList(
                        WSManager.getInstance().getReferenceDataService().getVdcs(districtCode),
                        VdcBean.class, (List) result);
                cache.put(key, result);
                for (VdcBean vdc : result) {
                    getCachedVdcs().put(vdc.getCode(), vdc);
                }
            }
        }
        return result;
    }

    public static VdcBean getVdc(String vdcCode) {
        if (getCachedVdcs().containsKey(vdcCode)) {
            return getCachedVdcs().get(vdcCode);
        } else {
            VdcBean vdcBean = TypeConverters.TransferObjectToBean(
                    WSManager.getInstance().getReferenceDataService().getVdcByCode(vdcCode), VdcBean.class, null);
            if (vdcBean != null) {
                getVdcs(vdcBean.getDistrictCode());
            }
            return vdcBean;
        }
    }

    public static List<VdcBean> getVdcsByOffice() {
        if (OfficeBean.getCurrentOffice() != null) {
            return getVdcs(OfficeBean.getCurrentOffice().getDistrictCode());
        }
        return null;
    }

    private static HashMap<String, VdcBean> getCachedVdcs() {
        HashMap<String, VdcBean> vdcs;
        if (cache.contains(CACHED_VDCS_KEY)) {
            vdcs = (HashMap<String, VdcBean>) cache.get(CACHED_VDCS_KEY);
        } else {
            vdcs = new HashMap<String, VdcBean>();
            cache.put(CACHED_VDCS_KEY, vdcs);
        }
        return vdcs;
    }

    public static List<OfficeBean> getOffices() {
        return getCachedBeanList(OfficeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_OFFICES, OFFICE_KEY);
    }

    public static List<OfficeBean> getOffices(String districtCode) {
        List<OfficeBean> result = new ArrayList<OfficeBean>();
        String key = OFFICE_KEY + districtCode;

        if (cache.contains(key)) {
            result = (List<OfficeBean>) cache.get(key);
        } else {
            if (WSManager.getInstance().getReferenceDataService() != null) {
                TypeConverters.TransferObjectListToBeanList(
                        WSManager.getInstance().getReferenceDataService().getOfficesByDistrict(districtCode),
                        OfficeBean.class, (List) result);
                cache.put(key, result);
            }
        }
        return result;
    }

    public static List<DistrictBean> getDistricts() {
        return getCachedBeanList(DistrictBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_DISTRICTS, DISTRICT_KEY);
    }

    public static List<BrValidationTargetTypeBean> getBrValidationTargetTypes() {
        return getCachedBeanList(BrValidationTargetTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_BR_VALIDATION_TARGET_TYPES, BR_VALIDATION_TARGET_TYPE_KEY);
    }

    public static List<BaUnitRelTypeBean> getBaUnitRelTypes() {
        return getCachedBeanList(BaUnitRelTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_BA_UNIT_REL_TYPES, BA_UNIT_REL_TYPE_KEY);
    }

    public static List<BrTechnicalTypeBean> getBrTechnicalTypes() {
        return getCachedBeanList(BrTechnicalTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_BR_TECHNICAL_TYPES, BR_TECHNICAL_TYPE_KEY);
    }

    public static List<BrSeverityTypeBean> getBrSeverityTypes() {
        return getCachedBeanList(BrSeverityTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_BR_SEVERITY_TYPES, BR_SEVERITY_TYPE_KEY);
    }

    public static List<RequestCategoryTypeBean> getRequestCategoryTypes() {
        return getCachedBeanList(RequestCategoryTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_REQUEST_CATEGORY_TYPES, REQUEST_CATEGORY_TYPE_KEY);
    }

    public static List<LanguageBean> getLanguages() {
        return getCachedBeanList(LanguageBean.class,
                WSManager.getInstance().getAdminService(),
                GET_LANGUAGES, LANGUAGE_KEY);
    }

    public static List<RoleBean> getRoles() {
        return getCachedBeanList(RoleBean.class,
                WSManager.getInstance().getAdminService(),
                GET_ROLES, ROLES_KEY);
    }

    public static List<TypeActionBean> getTypeActions() {
        return getCachedBeanList(TypeActionBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_TYPE_ACTIONS, TYPE_ACTIONS_KEY);
    }

    public static List<ChangeStatusTypeBean> getChangeStatusTypes() {
        return getCachedBeanList(ChangeStatusTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_CHANGE_STATUS_TYPES, CHANGE_STATUS_TYPE_CODES_KEY);
    }

    public static List<CadastreObjectTypeBean> getCadastreObjectTypes() {
        return getCachedBeanList(CadastreObjectTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_CADASTRE_OBJECT_TYPES, CADASTRE_OBJECT_TYPE_CODES_KEY);
    }

    public static List<RegistrationStatusTypeBean> getRegistrationStatusTypes() {
        return getCachedBeanList(RegistrationStatusTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_REGISTRATION_STATUS_TYPES, REGISTRATION_STATUS_TYPE_CODES_KEY);
    }

    public static List<RrrTypeBean> getRrrTypes() {
        return getCachedBeanList(RrrTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_RRR_TYPES, RRR_TYPE_CODES_KEY);
    }

    public static List<RrrGroupTypeBean> getRrrGroupTypes() {
        return getCachedBeanList(RrrGroupTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_RRR_GROUP_TYPES, RRR_GROUP_TYPE_CODES_KEY);
    }

    public static List<MortgageTypeBean> getMortgageTypes() {
        return getCachedBeanList(MortgageTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_MORTGAGE_TYPES, MORTGAGE_TYPE_CODES_KEY);
    }

    public static List<BaUnitTypeBean> getBaUnitTypes() {
        return getCachedBeanList(BaUnitTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_BA_UNIT_TYPES, BA_UNIT_TYPE_CODES_KEY);
    }

    public static List<IdTypeBean> getIdTypes() {
        return getCachedBeanList(IdTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_ID_TYPES, ID_TYPE_CODES_KEY);
    }

    public static List<PartyTypeBean> getPartyTypes() {
        return getCachedBeanList(PartyTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_PARTY_TYPES, PARTY_TYPE_CODES_KEY);
    }

    public static List<PartyRoleTypeBean> getPartyRoles() {
        return getCachedBeanList(PartyRoleTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_PARTY_ROLES, PARTY_ROLE_TYPE_CODES_KEY);
    }

    public static List<ServiceStatusTypeBean> getAppServiceStatusTypes() {
        return getCachedBeanList(ServiceStatusTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_SERVICE_STATUS_TYPES, SERVICE_STATUS_TYPE_CODES_KEY);
    }

    public static List<ServiceActionTypeBean> getAppServiceActionTypes() {
        return getCachedBeanList(ServiceActionTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_SERVICE_ACTION_TYPES, SERVICE_ACTION_TYPE_CODES_KEY);
    }

    public static List<ApplicationActionTypeBean> getApplicationActionTypes() {
        return getCachedBeanList(ApplicationActionTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_APPLICATION_ACTION_TYPES, APP_ACTION_TYPE_CODES_KEY);
    }

    public static List<ApplicationStatusTypeBean> getApplicationStatusTypes() {
        return getCachedBeanList(ApplicationStatusTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_APPLICATION_STATUS_TYPES, APP_STATUS_TYPE_CODES_KEY);
    }

    public static Map getApplicationStatusTypesMap() {
        return getCachedMap(
                getCachedBeanList(ApplicationStatusTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_APPLICATION_STATUS_TYPES, APP_STATUS_TYPE_CODES_KEY),
                APP_STATUS_TYPE_CODES_MAP_KEY);
    }

    public static List<SourceTypeBean> getSourceTypes() {
        return getCachedBeanList(SourceTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_SOURCE_TYPES, SOURCE_TYPES_KEY);
    }

    public static Map getSourceTypesMap() {
        return getCachedMap(
                getCachedBeanList(SourceTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_SOURCE_TYPES, SOURCE_TYPES_KEY),
                SOURCE_TYPES_MAP_KEY);
    }

    public static List<CommunicationTypeBean> getCommunicationTypes() {
        return getCachedBeanList(CommunicationTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_COMMUNICATION_TYPES, COMMUNICATION_TYPES_KEY);
    }

    public static Map getCommunicationTypesMap() {
        return getCachedMap(
                getCachedBeanList(CommunicationTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_COMMUNICATION_TYPES, COMMUNICATION_TYPES_KEY),
                COMMUNICATION_TYPES_MAP_KEY);
    }

    public static List<GenderTypeBean> getGenderTypes() {
        return getCachedBeanList(GenderTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_GENDER_TYPES, GENDER_TYPES_KEY);
    }

    public static Map getGenderTypesMap() {
        return getCachedMap(
                getCachedBeanList(GenderTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_GENDER_TYPES, GENDER_TYPES_KEY),
                GENDER_TYPES_MAP_KEY);
    }

    public static List<RequestTypeBean> getRequestTypes() {
        return getCachedBeanList(RequestTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_REQUEST_TYPES, REQUEST_TYPES_KEY);
    }

    public static Map getRequestTypesMap() {
        return getCachedMap(
                getCachedBeanList(RequestTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_REQUEST_TYPES, REQUEST_TYPES_KEY),
                REQUEST_TYPES_MAP_KEY);
    }

    /**
     * Generic method to create cached list of the beans, representing reference
     * table data. The list holds full object, transfered from the server. If
     * the list already exists, it will be returned from the cache.
     *
     * @param beanClass Bean's class to create the list.
     * @param wsClient Web service client instance to extract data from.
     * @param methodName Method name of the web service client to get data.
     * @param key Unique key to find the list in the cache.
     * @return Returns cached map.
     */
    private static <T extends AbstractBindingBean, S extends AbstractTO, W extends AbstractWSClient> List<T> getCachedBeanList(
            Class<T> beanClass, W wsClient, String methodName, String key) {

        List<T> result = new ArrayList<T>();

        if (cache.contains(key)) {
            result = (List<T>) cache.get(key);
        } else {
            if (wsClient != null && methodName != null && !methodName.equals("")) {
                try {
                    List<S> toList;
                    toList = (List) wsClient.getClass().getMethod(methodName).invoke(wsClient);
                    TypeConverters.TransferObjectListToBeanList(toList, beanClass, (List) result);
                    cache.put(key, result);
                } catch (IllegalAccessException ex) {
                    MessageUtility.displayMessage(ClientMessage.GENERAL_UNEXPECTED,
                            new Object[]{ex.getLocalizedMessage()});
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(CacheManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    MessageUtility.displayMessage(ClientMessage.GENERAL_UNEXPECTED,
                            new Object[]{ex.getLocalizedMessage()});
                } catch (NoSuchMethodException ex) {
                    MessageUtility.displayMessage(ClientMessage.ERR_NO_SUCH_METHOD,
                            new Object[]{methodName, ex.getLocalizedMessage()});
                } catch (SecurityException ex) {
                    MessageUtility.displayMessage(ClientMessage.GENERAL_UNEXPECTED,
                            new Object[]{ex.getLocalizedMessage()});
                }
            }
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * Generic method to create cached map for the beans, representing reference
     * table data. The map holds code and value to display. If map already
     * exists, it will be returned from the cache.
     *
     * @param beanList The list of beans to cache.
     * @param key Unique key to find the map in the cache.
     * @return Returns cached map.
     */
    private static <T extends AbstractCodeBean> Map getCachedMap(
            List<T> beanList, String key) {
        Map map = new HashMap();

        if (cache.contains(key)) {
            map = (HashMap) cache.get(key);
        } else {
            for (Iterator<T> it = beanList.iterator(); it.hasNext();) {
                T bean = it.next();
                map.put(bean.getCode(), bean.getDisplayValue());
            }
            cache.put(key, map);
        }
        return Collections.unmodifiableMap(map);
    }

    /**
     * Generic method to find the bean by code in the given collection.
     *
     * @return Returns bean or null if it wasn't found.
     */
    public static <T extends AbstractCodeBean> T getBeanByCode(List<T> list, String code) {
        T result = null;
        for (Iterator<T> it = list.iterator(); it.hasNext();) {
            AbstractCodeBean bean = it.next();
            if (bean.getCode().equals(code)) {
                result = (T) bean;
                break;
            }
        }
        return result;
    }

    /**
     * Generic method to find the bean by ID in the given collection.
     *
     * @return Returns bean or null if it wasn't found.
     */
    public static <T extends AbstractIdBean> T getBeanById(List<T> list, String id) {
        T result = null;
        for (Iterator<T> it = list.iterator(); it.hasNext();) {
            AbstractIdBean bean = it.next();
            if (bean.getId().equals(id)) {
                result = (T) bean;
                break;
            }
        }
        return result;
    }

    /**
     * Adds object into the cache.
     */
    public static void add(String key, Object object) {
        cache.put(key, object);
    }

    /**
     * Returns object from the the cache.
     */
    public static Object get(String key) {
        return cache.get(key);
    }

    /**
     * Removes all data from the cache.
     */
    public static void clear() {
        // Clears all cache data
        cache.clear();
    }

    /**
     * Removes object from the cache by the given key value.
     */
    public static void remove(String key) {
        // Clears key 
        if (cache.contains(key)) {
            cache.remove(key);
        }
    }

    public static List<MapSheetBean> getMapSheets(String officeCode) {
        List<MapSheetBean> result = new ArrayList<MapSheetBean>();
        String key = MAP_SHEET_KEY + officeCode;

        if (cache.contains(key)) {
            result = (List<MapSheetBean>) cache.get(key);
        } else {
            if (WSManager.getInstance().getCadastreService() != null) {
                TypeConverters.TransferObjectListToBeanList(
                        WSManager.getInstance().getCadastreService().getMapSheetsByOffice(officeCode),
                        MapSheetBean.class, (List) result);
                cache.put(key, result);
                HashMap<String, MapSheetBean> mapSheets = getCachedMapSheets();
                for (MapSheetBean mapSheet : result) {
                    mapSheets.put(mapSheet.getId(), mapSheet);
                }
            }
        }
        return result;
    }

    public static MapSheetBean getMapSheet(String mapSheetId) {
        if (getCachedMapSheets().containsKey(mapSheetId)) {
            return getCachedMapSheets().get(mapSheetId);
        } else {
            MapSheetBean mapSheetBean = TypeConverters.TransferObjectToBean(
                    WSManager.getInstance().getCadastreService().getMapSheet(mapSheetId), MapSheetBean.class, null);
            if (mapSheetBean != null) {
                getMapSheets(mapSheetBean.getOfficeCode());
            }
            return mapSheetBean;
        }
    }

    public static List<MapSheetBean> getMapSheetsByCurrentOffice() {
        if (OfficeBean.getCurrentOffice() != null) {
            return getMapSheets(OfficeBean.getCurrentOffice().getDistrictCode());
        }
        return null;
    }

    private static HashMap<String, MapSheetBean> getCachedMapSheets() {
        HashMap<String, MapSheetBean> mapSheets;
        if (cache.contains(CACHED_MAP_SHEETS_KEY)) {
            mapSheets = (HashMap<String, MapSheetBean>) cache.get(CACHED_MAP_SHEETS_KEY);
        } else {
            mapSheets = new HashMap<String, MapSheetBean>();
            cache.put(CACHED_MAP_SHEETS_KEY, mapSheets);
        }
        return mapSheets;
    }

    public static List<RestrictionOfficeBean> getRestrictionOffices() {
        return getCachedBeanList(RestrictionOfficeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_RESTRICTION_OFFICES, RESTRICTION_OFFICE_KEY);
    }

    public static List<RestrictionReasonBean> getRestrictionReasons() {
        return getCachedBeanList(RestrictionReasonBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_RESTRICTION_REASONS, RESTRICTION_REASON_KEY);
    }

    public static List<RestrictionReleaseReasonBean> getRestrictionReleaseReasons() {
        return getCachedBeanList(RestrictionReleaseReasonBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_RESTRICTION_RELEASE_REASONS, RESTRICTION_RELEASE_REASON_KEY);
    }

    public static List<OwnerTypeBean> getOwnerTypes() {
        return getCachedBeanList(OwnerTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_OWNER_TYPES, OWNER_TYPE_KEY);
    }

    public static List<OwnershipTypeBean> getOwnershipTypes() {
        return getCachedBeanList(OwnershipTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_OWNERSHIP_TYPES, OWNERSHIP_TYPE_KEY);
    }

    public static List<TenancyTypeBean> getTenantTypes() {
        return getCachedBeanList(TenancyTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_TENANT_TYPES, TENANT_TYPE_KEY);
    }

    public static List<LandClassBean> getLandClasses() {
        return getCachedBeanList(LandClassBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_LAND_CLASSES, LAND_CLASS_KEY);
    }

    public static List<LandUseBean> getLandUses() {
        return getCachedBeanList(LandUseBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_LAND_USES, LAND_USE_KEY);
    }

    public static List<GuthiNameBean> getGuthiNames() {
        return getCachedBeanList(GuthiNameBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_GUTHI_NAMES, GUTHI_NAME_KEY);
    }

    public static List<LandTypeBean> getLandTypes() {
        return getCachedBeanList(LandTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_LAND_TYPES, LAND_TYPES_KEY);
    }

    public static List<AreaUnitTypeBean> getAreaUnitTypes() {
        return getCachedBeanList(AreaUnitTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_AREA_UNIT_TYPES, AREA_UNIT_TYPE_KEY);
    }

    public static List<BuildingUnitTypeBean> getBuildingUnitTypes() {
        return getCachedBeanList(BuildingUnitTypeBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_BUILDING_UNIT_TYPES, BUILDING_UNIT_TYPE_KEY);
    }

    public static List<FiscalYearBean> getFiscalYears() {
        return getCachedBeanList(FiscalYearBean.class,
                WSManager.getInstance().getReferenceDataService(),
                GET_FISCAL_YEARS, FISCAL_YEAR_KEY);
    }
}
