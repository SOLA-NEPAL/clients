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
package org.sola.clients.beans.security;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.controls.SolaList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.referencedata.DepartmentBean;
import org.sola.clients.beans.validation.Localized;
import org.sola.common.StringUtility;
import org.sola.common.messaging.ClientMessage;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.EntityAction;
import org.sola.webservices.transferobjects.security.UserTO;

/** 
 * Represents <b>user</b> object in the domain model. 
 * Could be populated from the {@link UserTO} object.<br />
 * For more information on the properties of the <code>user</code> 
 * see data dictionary <b>System</b> schema.
 */
public class UserBean extends UserSummaryBean {

    public final static String USER_GROUPS_PROPERTY = "groups";
    public static final String USERNAME_PROPERTY = "userName";
    public static final String ACTIVE_PROPERTY = "active";
    public static final String DEPARTMENT_PROPERTY = "department";
    public static final String VDCS_PROPERTY = "vdcs";
    public static final String SELECTED_VDC_PROPERTY = "selectedVdc";
    
    @NotEmpty(message=ClientMessage.CHECK_NOTNULL_USERNAME, payload=Localized.class)
    private String userName;
    private boolean active;
    @Size(min=1, message=ClientMessage.CHECK_MIN_USERGROUP, payload=Localized.class)
    private SolaList<UserGroupBean> userGroups;
    private SolaList<RoleBean> roles;
    @NotNull(message=ClientMessage.CHECK_USER_DEPARTMENT_NOT_NULL, payload=Localized.class)
    private DepartmentBean department;
    private SolaList<UserVdcBean> vdcs;
    private UserVdcBean selectedVdc;
            
    public UserBean() {
        super();
        active = true;
        userGroups = new SolaList<UserGroupBean>();
        vdcs = new SolaList<UserVdcBean>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String value) {
        String oldValue = userName;
        userName = value;
        propertySupport.firePropertyChange(USERNAME_PROPERTY, oldValue, value);
    }

    @Override
    public String getFullUserName() {
        String fullName = super.getFullUserName();

        if (fullName == null || fullName.equals("") || fullName.equals("NO NAME")) {
            fullName = userName;
        }
        return fullName;
    }

    public SolaList<UserGroupBean> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(SolaList<UserGroupBean> userGroups) {
        this.userGroups = userGroups;
        propertySupport.firePropertyChange(USER_GROUPS_PROPERTY, null, this.userGroups);
    }

    public ObservableList<UserVdcBean> getFilteredVdcs() {
        return vdcs.getFilteredList();
    }
    
    public SolaList<UserVdcBean> getVdcs() {
        return vdcs;
    }

    public void setVdcs(SolaList<UserVdcBean> vdcs) {
        this.vdcs = vdcs;
        propertySupport.firePropertyChange(VDCS_PROPERTY, null, this.vdcs);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public DepartmentBean getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentBean department) {
        DepartmentBean oldValue = this.department;
        this.department = department;
        propertySupport.firePropertyChange(DEPARTMENT_PROPERTY, oldValue, this.department);
    }

    @Override
    public String getDepartmentCode(){
        if(department == null){
            return null;
        }
        return department.getCode();
    }

    public UserVdcBean getSelectedVdc() {
        return selectedVdc;
    }

    public void setSelectedVdc(UserVdcBean selectedVdc) {
        UserVdcBean oldValue = this.selectedVdc;
        this.selectedVdc = selectedVdc;
        propertySupport.firePropertyChange(SELECTED_VDC_PROPERTY, oldValue, this.selectedVdc);
    }
    
    // Methods
    
    /** Checks whether user belongs to one of the roles or not. */
    public boolean isInRole(String... roleNames) {
        if (roleNames != null && getRoles() != null) {
            for (String roleName : roleNames) {
                for (RoleBean role : getRoles()) {
                    if (roleName.equalsIgnoreCase(role.getCode())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** Returns list of roles, accessible to the user. */
    public SolaList<RoleBean> getRoles() {
        if(roles==null && WSManager.getInstance()!=null){
            roles =new SolaList<RoleBean>();
            TypeConverters.TransferObjectListToBeanList(
                    WSManager.getInstance().getAdminService().getCurrentUserRoles(),
                    RoleBean.class, (List)roles);
        }
        return roles;
    }

    /** Adds new {@link UserVdcBean} into the list of user's Vdcs. */
    public void addVdc(UserVdcBean newVdc){
        if(getVdcs()!=null && newVdc !=null){
            for(UserVdcBean currentVdc : getVdcs()){
                if(StringUtility.empty(currentVdc.getVdcCode()).equals(StringUtility.empty(newVdc.getVdcCode())) && 
                        StringUtility.empty(currentVdc.getWardNumber()).equals(StringUtility.empty(newVdc.getWardNumber()))){
                    if(currentVdc.getEntityAction()!=null && (currentVdc.getEntityAction() == EntityAction.DELETE 
                            || currentVdc.getEntityAction() == EntityAction.DISASSOCIATE)){
                        currentVdc.setEntityAction(null);
                    }
                    return;
                }
            }
            getVdcs().addAsNew(newVdc);
        }
    }
    
    /** Removes selected {@link UserVdcBean} */
    public void removeSelectedVdc(){
        if(selectedVdc!=null){
            getVdcs().safeRemove(selectedVdc, EntityAction.DELETE);
        }
    }
    
    /** Saves user. */
    public void save(){
        UserTO userTO = TypeConverters.BeanToTrasferObject(this, UserTO.class);
        userTO = WSManager.getInstance().getAdminService().saveUser(userTO);
        TypeConverters.TransferObjectToBean(userTO, UserBean.class, this);
    }
    
    /** Changes user password. */
    public boolean changePassword(String password){
        return changePassword(userName, password);
    }
    
    /** Changes user password. */
    public static boolean changePassword(String userName, String password){
        return WSManager.getInstance().getAdminService().changePassword(userName, password);
    }
    
    /** Changes current user password. */
    public static boolean changeCurrentPassword(String oldPassword, String newPassword){
        return WSManager.getInstance().getAdminService().changeCurrentUserPassword(oldPassword, newPassword);
    }
    
    /** Returns user by the given ID */
    public static UserBean getUser(String userName){
        return TypeConverters.TransferObjectToBean(WSManager.getInstance()
                .getAdminService().getUser(userName), UserBean.class, null);
    }
    
    /** Removes user */
    public static void removeUser(String userName){
        UserTO userTO = WSManager.getInstance().getAdminService().getUser(userName);
        userTO.setEntityAction(EntityAction.DELETE);
        WSManager.getInstance().getAdminService().saveUser(userTO);
    }
}
