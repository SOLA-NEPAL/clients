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
package org.sola.clients.beans.cadastre;

import org.sola.clients.beans.AbstractBindingBean;

/**
 *
 * @author Kumar
 */
public class ParcelSearchResultListBean extends AbstractBindingBean{
//     public static final String SELECTED_APPLICATION_PROPERTY = "selectedApplication";
//    public static final String CHECKED_APPLICATION_PROPERTY = "checkedApplication";
//    private SolaObservableList<ApplicationSearchResultBean> applicationSearchResultsList;
//    private SolaObservableList<ApplicationSearchResultBean> applicationSearchResultsListUnfiltered;
//    private ApplicationSearchResultBean selectedApplication;
//    private PropertyChangeListener searchResultListener;
//    private boolean applicationChecked;
//
//    /**
//     * Creates object's instance and populates collection of {@link ApplicationSearchResultBean}.
//     */
//    public ApplicationSearchResultsListBean(List<ApplicationSearchResultBean> searchList) {
//        this();
//        if (searchList != null) {
//            for (ApplicationSearchResultBean searchResult : searchList) {
//                applicationSearchResultsList.add(searchResult);
//            }
//        }
//    }
//
//    /**
//     * Creates object's instance and initializes collection of {@link ApplicationSearchResultBean}.
//     */
//    public ApplicationSearchResultsListBean() {
//        applicationSearchResultsList = new SolaObservableList<ApplicationSearchResultBean>();
//        applicationSearchResultsListUnfiltered = new SolaObservableList<ApplicationSearchResultBean>();
//        searchResultListener = new PropertyChangeListener() {
//
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//                if (evt.getPropertyName().equals(ApplicationSearchResultBean.IS_SELECTED_PROPERTY)) {
//                    checkSearchResultChecked();
//                }
//            }
//        };
//
//        applicationSearchResultsList.addObservableListListener(new ObservableListListener() {
//
//            @Override
//            public void listElementsAdded(ObservableList list, int index, int length) {
//                addSearchResultListener(index, length);
//            }
//
//            @Override
//            public void listElementsRemoved(ObservableList list, int index, List oldElements) {
//                removeSearchResultListener(oldElements);
//            }
//
//            @Override
//            public void listElementReplaced(ObservableList list, int index, Object oldElement) {
//            }
//
//            @Override
//            public void listElementPropertyChanged(ObservableList list, int index) {
//            }
//        });
//    }
//
//    private void checkSearchResultChecked() {
//        for (ApplicationSearchResultBean searchResult : applicationSearchResultsList) {
//            if (searchResult.isChecked() && applicationChecked) {
//                return;
//            } else if (searchResult.isChecked() && !applicationChecked) {
//                setApplicationChecked(true);
//                return;
//            }
//        }
//        if (applicationChecked) {
//            setApplicationChecked(false);
//        }
//    }
//
//    private void addSearchResultListener(int index, int length) {
//        for (int i = index; i <= index + length - 1; i++) {
//            applicationSearchResultsList.get(i).addPropertyChangeListener(searchResultListener);
//        }
//        checkSearchResultChecked();
//    }
//
//    private void removeSearchResultListener(List<ApplicationSearchResultBean> oldElements) {
//        for (ApplicationSearchResultBean searchResult : oldElements) {
//            searchResult.removePropertyChangeListener(searchResultListener);
//        }
//        checkSearchResultChecked();
//    }
//
//    /**
//     * Returns checked list of search results
//     */
//    public SolaObservableList<ApplicationSearchResultBean> getCheckedApplications() {
//        SolaObservableList<ApplicationSearchResultBean> checkedApplications =
//                new SolaObservableList<ApplicationSearchResultBean>();
//        for (ApplicationSearchResultBean searchResult : applicationSearchResultsList) {
//            if (searchResult.isChecked()) {
//                checkedApplications.add(searchResult);
//            }
//        }
//        return checkedApplications;
//    }
//
//    /**
//     * Fills application search result list with assigned applications.
//     */
//    public void FillAssigned(boolean showOnlyMy) {
//        applicationSearchResultsList.clear();
//        applicationSearchResultsListUnfiltered.clear();
//        List<ApplicationSearchResultTO> assignedApplicationsTO =
//                WSManager.getInstance().getSearchService().getAssignedApplications();
//
//        TypeConverters.TransferObjectListToBeanList(assignedApplicationsTO,
//                ApplicationSearchResultBean.class, (List) getApplicationSearchResultsList());
//        for (ApplicationSearchResultBean searchResult : getApplicationSearchResultsList()) {
//            applicationSearchResultsListUnfiltered.add(searchResult);
//        }
//        showOnlyMyApplications(showOnlyMy);
//    }
//
//    public void showOnlyMyApplications(boolean showOnlyMy) {
//        if (showOnlyMy) {
//            String currentUserId = SecurityBean.getCurrentUser().getId();
//            Iterator it = applicationSearchResultsList.listIterator();
//            while(it.hasNext()){
//                ApplicationSearchResultBean searchResult = (ApplicationSearchResultBean)it.next();
//                if (!searchResult.getAssigneeId().equals(currentUserId)) {
//                    it.remove();
//                }
//            }
//        } else {
//            if (applicationSearchResultsListUnfiltered.size() > applicationSearchResultsList.size()) {
//                applicationSearchResultsList.clear();
//                for (ApplicationSearchResultBean searchResult : applicationSearchResultsListUnfiltered) {
//                    applicationSearchResultsList.add(searchResult);
//                }
//            }
//        }
//    }
//
//    /**
//     * Runs application search with a given search criteria.
//     */
//    public void searchApplications(ApplicationSearchParamsBean params) {
//        applicationSearchResultsList.clear();
//        ApplicationSearchParamsTO paramsTO = TypeConverters.BeanToTrasferObject(params,
//                ApplicationSearchParamsTO.class);
//
//        List<ApplicationSearchResultTO> searchApplicationsTO =
//                WSManager.getInstance().getSearchService().searchApplications(paramsTO);
//        TypeConverters.TransferObjectListToBeanList(searchApplicationsTO,
//                ApplicationSearchResultBean.class, (List) getApplicationSearchResultsList());
//    }
//
//    public ObservableList<ApplicationSearchResultBean> getApplicationSearchResultsList() {
//        return applicationSearchResultsList;
//    }
//
//    public ApplicationSearchResultBean getSelectedApplication() {
//        return selectedApplication;
//    }
//
//    public void setSelectedApplication(ApplicationSearchResultBean applicationSearchResultBean) {
//        selectedApplication = applicationSearchResultBean;
//        propertySupport.firePropertyChange(SELECTED_APPLICATION_PROPERTY, null, selectedApplication);
//    }
//
//    public boolean isApplicationChecked() {
//        return applicationChecked;
//    }
//
//    private void setApplicationChecked(boolean applicationChecked) {
//        boolean oldValue = this.applicationChecked;
//        this.applicationChecked = applicationChecked;
//        propertySupport.firePropertyChange(CHECKED_APPLICATION_PROPERTY, oldValue, this.applicationChecked);
//    }
//
//    public void removeSelectedResultFromList() {
//        if (selectedApplication != null) {
//            applicationSearchResultsList.remove(selectedApplication);
//        }
//    }
}
