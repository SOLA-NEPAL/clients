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
package org.sola.clients.beans.security;

import java.util.Iterator;
import java.util.List;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.controls.ExtendedListFilter;
import org.sola.clients.beans.controls.SolaList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.search.UserSearchResultTO;

/**
 * Holds the list of {@link UserSearchResultBean} objects and used to populate
 * comboboxes or listboxes controls.
 */
public class UserSearchResultListBean extends AbstractBindingBean {

    private class FilterByDepartment implements ExtendedListFilter {

        private String departmentCode;

        public FilterByDepartment() {
        }

        @Override
        public boolean isAllowedByFilter(Object element) {
            if (element == null
                    || !element.getClass().isAssignableFrom(UserSearchResultBean.class)
                    || ((UserSearchResultBean) element).getDepartmentCode() == null) {
                return false;
            }

            if (departmentCode == null || departmentCode.isEmpty()) {
                return false;
            }

            if (((UserSearchResultBean) element).getDepartmentCode().equals(departmentCode)) {
                return true;
            } else {
                return false;
            }
        }

        public String getDepartmentCode() {
            return departmentCode;
        }

        public void setDepartmentCode(String departmentCode) {
            this.departmentCode = departmentCode;
        }
    }
    
    public static final String SELECTED_USER_PROPERTY = "selectedUser";
    private FilterByDepartment filterByDepartment;
    private SolaList<UserSearchResultBean> usersList;
    private UserSearchResultBean selectedUser;

    /**
     * Creates object instance and populates user's list with active users.
     */
    public UserSearchResultListBean() {
        super();
        usersList = new SolaList<UserSearchResultBean>();
        filterByDepartment = new FilterByDepartment();
    }

    public void filterByDepartment(String departmentCode) {
        if(usersList.getFilter() == null || usersList.getFilter() != filterByDepartment){
            usersList.setFilter(filterByDepartment);
        }
        filterByDepartment.setDepartmentCode(departmentCode);
        usersList.filter();
    }

    /**
     * Populates the list of users with active users.
     */
    public void loadMyOfficeUsers() {
        if (WSManager.getInstance().getSearchService() != null) {
            List<UserSearchResultTO> userListTO = WSManager.getInstance().getSearchService().getMyOfficeUsers();
            TypeConverters.TransferObjectListToBeanList(userListTO, UserSearchResultBean.class, (List) usersList);
        }
    }

    /**
     * Populates the list of users from current user department.
     */
    public void loadMyDepartmentUsers() {
        if (WSManager.getInstance().getSearchService() != null) {
            List<UserSearchResultTO> userListTO = WSManager.getInstance().getSearchService().getMyDepartmentUsers();
            TypeConverters.TransferObjectListToBeanList(userListTO, UserSearchResultBean.class, (List) usersList);
        }
    }

    /**
     * Populates the list of users from the given department who can assign
     * applications.
     *
     * @param departmentCode Code of department for which to load users with
     * Assign rights
     */
    public void loadUsersWithAssignRightByDepartment(String departmentCode) {
        if (WSManager.getInstance().getSearchService() != null) {
            List<UserSearchResultTO> userListTO = WSManager.getInstance().getSearchService().getUsersWithAssignRightByDepartment(departmentCode);
            TypeConverters.TransferObjectListToBeanList(userListTO,
                    UserSearchResultBean.class, (List) usersList);
        }
    }

    /**
     * Populates the list of users from the current user office who can assign
     * applications.
     */
    public void loadUsersWithAssignRightByOffice() {
        loadUsersWithAssignRightByOffice(SecurityBean.getCurrentUser().getDepartment().getOfficeCode());
    }
    
    /**
     * Populates the list of users from the given office who can assign
     * applications.
     *
     * @param officeCode Code of office for which to load users with Assign
     * rights
     */
    public void loadUsersWithAssignRightByOffice(String officeCode) {
        if (WSManager.getInstance().getSearchService() != null) {
            List<UserSearchResultTO> userListTO = WSManager.getInstance().getSearchService().getUsersWithAssignRightByOffice(officeCode);
            TypeConverters.TransferObjectListToBeanList(userListTO,
                    UserSearchResultBean.class, (List) usersList);
        }
    }

    public ObservableList<UserSearchResultBean> getUsers() {
        return usersList.getFilteredList();
    }

    public UserSearchResultBean getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserSearchResultBean value) {
        selectedUser = value;
        propertySupport.firePropertyChange(SELECTED_USER_PROPERTY, null, value);
    }

    /**
     * Searches for a given user id in the list of users and sets it as a
     * selected.
     */
    public void setSelectedUserById(String userId) {
        if (usersList != null) {
            for (Iterator<UserSearchResultBean> it = usersList.iterator(); it.hasNext();) {
                UserSearchResultBean user = it.next();
                if (user.getId().equals(userId)) {
                    selectedUser = user;
                    break;
                }
            }
        }
        propertySupport.firePropertyChange(SELECTED_USER_PROPERTY, null, selectedUser);
    }
}
