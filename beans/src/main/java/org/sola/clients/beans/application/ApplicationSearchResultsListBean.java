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
package org.sola.clients.beans.application;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.List;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.observablecollections.ObservableListListener;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.controls.SolaObservableList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.security.SecurityBean;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.search.ApplicationSearchParamsTO;
import org.sola.webservices.transferobjects.search.ApplicationSearchResultTO;

/**
 * Contains methods to search applications and get the list of assigned and
 * unassigned applications.
 */
public class ApplicationSearchResultsListBean extends AbstractBindingBean {

    public static final String SELECTED_APPLICATION_PROPERTY = "selectedApplication";
    public static final String CHECKED_APPLICATION_PROPERTY = "checkedApplication";
    private SolaObservableList<ApplicationSearchResultBean> applicationSearchResultsList;
    private SolaObservableList<ApplicationSearchResultBean> applicationSearchResultsListUnfiltered;
    private ApplicationSearchResultBean selectedApplication;
    private PropertyChangeListener searchResultListener;
    private boolean applicationChecked;

    /**
     * Creates object's instance and populates collection of {@link ApplicationSearchResultBean}.
     */
    public ApplicationSearchResultsListBean(List<ApplicationSearchResultBean> searchList) {
        this();
        if (searchList != null) {
            for (ApplicationSearchResultBean searchResult : searchList) {
                applicationSearchResultsList.add(searchResult);
            }
        }
    }

    /**
     * Creates object's instance and initializes collection of {@link ApplicationSearchResultBean}.
     */
    public ApplicationSearchResultsListBean() {
        applicationSearchResultsList = new SolaObservableList<ApplicationSearchResultBean>();
        applicationSearchResultsListUnfiltered = new SolaObservableList<ApplicationSearchResultBean>();
        searchResultListener = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(ApplicationSearchResultBean.IS_SELECTED_PROPERTY)) {
                    checkSearchResultChecked();
                }
            }
        };

        applicationSearchResultsList.addObservableListListener(new ObservableListListener() {

            @Override
            public void listElementsAdded(ObservableList list, int index, int length) {
                addSearchResultListener(index, length);
            }

            @Override
            public void listElementsRemoved(ObservableList list, int index, List oldElements) {
                removeSearchResultListener(oldElements);
            }

            @Override
            public void listElementReplaced(ObservableList list, int index, Object oldElement) {
            }

            @Override
            public void listElementPropertyChanged(ObservableList list, int index) {
            }
        });
    }

    private void checkSearchResultChecked() {
        for (ApplicationSearchResultBean searchResult : applicationSearchResultsList) {
            if (searchResult.isChecked() && applicationChecked) {
                return;
            } else if (searchResult.isChecked() && !applicationChecked) {
                setApplicationChecked(true);
                return;
            }
        }
        if (applicationChecked) {
            setApplicationChecked(false);
        }
    }

    private void addSearchResultListener(int index, int length) {
        for (int i = index; i <= index + length - 1; i++) {
            applicationSearchResultsList.get(i).addPropertyChangeListener(searchResultListener);
        }
        checkSearchResultChecked();
    }

    private void removeSearchResultListener(List<ApplicationSearchResultBean> oldElements) {
        for (ApplicationSearchResultBean searchResult : oldElements) {
            searchResult.removePropertyChangeListener(searchResultListener);
        }
        checkSearchResultChecked();
    }

    /**
     * Returns checked list of search results
     */
    public SolaObservableList<ApplicationSearchResultBean> getCheckedApplications() {
        SolaObservableList<ApplicationSearchResultBean> checkedApplications =
                new SolaObservableList<ApplicationSearchResultBean>();
        for (ApplicationSearchResultBean searchResult : applicationSearchResultsList) {
            if (searchResult.isChecked()) {
                checkedApplications.add(searchResult);
            }
        }
        return checkedApplications;
    }

    /**
     * Fills application search result list with assigned applications.
     */
    public void FillAssigned(boolean showOnlyMy) {
        applicationSearchResultsList.clear();
        applicationSearchResultsListUnfiltered.clear();
        List<ApplicationSearchResultTO> assignedApplicationsTO =
                WSManager.getInstance().getSearchService().getAssignedApplications();

        TypeConverters.TransferObjectListToBeanList(assignedApplicationsTO,
                ApplicationSearchResultBean.class, (List) getApplicationSearchResultsList());
        for (ApplicationSearchResultBean searchResult : getApplicationSearchResultsList()) {
            applicationSearchResultsListUnfiltered.add(searchResult);
        }
        showOnlyMyApplications(showOnlyMy);
    }

    public void showOnlyMyApplications(boolean showOnlyMy) {
        if (showOnlyMy) {
            String currentUserId = SecurityBean.getCurrentUser().getId();
            Iterator it = applicationSearchResultsList.listIterator();
            while(it.hasNext()){
                ApplicationSearchResultBean searchResult = (ApplicationSearchResultBean)it.next();
                if (!searchResult.getAssigneeId().equals(currentUserId)) {
                    it.remove();
                }
            }
        } else {
            if (applicationSearchResultsListUnfiltered.size() > applicationSearchResultsList.size()) {
                applicationSearchResultsList.clear();
                for (ApplicationSearchResultBean searchResult : applicationSearchResultsListUnfiltered) {
                    applicationSearchResultsList.add(searchResult);
                }
            }
        }
    }

    /**
     * Runs application search with a given search criteria.
     */
    public void searchApplications(ApplicationSearchParamsBean params) {
        applicationSearchResultsList.clear();
        ApplicationSearchParamsTO paramsTO = TypeConverters.BeanToTrasferObject(params,
                ApplicationSearchParamsTO.class);

        List<ApplicationSearchResultTO> searchApplicationsTO =
                WSManager.getInstance().getSearchService().searchApplications(paramsTO);
        TypeConverters.TransferObjectListToBeanList(searchApplicationsTO,
                ApplicationSearchResultBean.class, (List) getApplicationSearchResultsList());
    }

    public ObservableList<ApplicationSearchResultBean> getApplicationSearchResultsList() {
        return applicationSearchResultsList;
    }

    public ApplicationSearchResultBean getSelectedApplication() {
        return selectedApplication;
    }

    public void setSelectedApplication(ApplicationSearchResultBean applicationSearchResultBean) {
        selectedApplication = applicationSearchResultBean;
        propertySupport.firePropertyChange(SELECTED_APPLICATION_PROPERTY, null, selectedApplication);
    }

    public boolean isApplicationChecked() {
        return applicationChecked;
    }

    private void setApplicationChecked(boolean applicationChecked) {
        boolean oldValue = this.applicationChecked;
        this.applicationChecked = applicationChecked;
        propertySupport.firePropertyChange(CHECKED_APPLICATION_PROPERTY, oldValue, this.applicationChecked);
    }

    public void removeSelectedResultFromList() {
        if (selectedApplication != null) {
            applicationSearchResultsList.remove(selectedApplication);
        }
    }
}
