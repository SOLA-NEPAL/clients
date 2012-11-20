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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.observablecollections.ObservableListListener;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.controls.SolaObservableList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.EntityAction;
import org.sola.webservices.transferobjects.administrative.LocSearchByMothParamsTO;

/**
 *
 * @author Kumar
 */
public class LocSearchByMothParamsListBean extends AbstractBindingBean {

    public static final String SELECTED_RESULT_PROPERTY = "selectedResult";
    public static final String CHECKED_LOCS_PROPERTY = "locChecked";
    SolaObservableList<LocWithMothBean> searchResults;
    LocWithMothBean selectedResult;
    private PropertyChangeListener searchResultListener;
    private boolean locChecked;

    /**
     * Creates object's instance and populates collection of
     * {@link LocWithMothBean}.
     */
    public LocSearchByMothParamsListBean(List<LocWithMothBean> searchList) {
        this();
        if (searchList != null) {
            for (LocWithMothBean srchlst : searchList) {
                searchResults.add(srchlst);
            }
        }
    }

    public LocSearchByMothParamsListBean() {
        super();
        searchResults = new SolaObservableList<LocWithMothBean>();
        searchResultListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(LocWithMothBean.IS_CHECKED_PROPERTY)) {
                    checkSearchResultChecked();
                }
            }
        };

        searchResults.addObservableListListener(new ObservableListListener() {
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

    private void addSearchResultListener(int index, int length) {
        for (int i = index; i <= index + length - 1; i++) {
            searchResults.get(i).addPropertyChangeListener(searchResultListener);
        }
        checkSearchResultChecked();
    }

    private void removeSearchResultListener(List<LocWithMothBean> oldElements) {
        for (LocWithMothBean searchResult : oldElements) {
            searchResult.removePropertyChangeListener(searchResultListener);
        }
        checkSearchResultChecked();
    }

    public SolaObservableList<LocWithMothBean> getSearchResults() {
        return searchResults;
    }

    public LocWithMothBean getSelectedResult() {
        return selectedResult;
    }

    public void setSelectedResult(LocWithMothBean selectedResult) {
        LocWithMothBean oldValue = this.selectedResult;
        this.selectedResult = selectedResult;
        propertySupport.firePropertyChange(SELECTED_RESULT_PROPERTY, oldValue, this.selectedResult);
    }

    public void search(LocSearchByMothParamsBean searchParams) {
        searchResults.clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getAdministrative().getLocListByPageNoAndMoth(
                TypeConverters.BeanToTrasferObject(searchParams, LocSearchByMothParamsTO.class)),
                LocWithMothBean.class, (List) searchResults);
    }

    public void removeSelected() {
        if (getCheckedLocs() != null) {
            List<LocWithMothBean> locs = getCheckedLocs();
            for (LocWithMothBean loc : locs) {
                loc.setEntityAction(EntityAction.DELETE);
                loc.saveLoc();
                searchResults.remove(loc);
            }

        }
    }

    public boolean isLocChecked() {
        return locChecked;
    }

    public void setLocChecked(boolean locChecked) {
        boolean oldValue = this.locChecked;
        this.locChecked = locChecked;
        propertySupport.firePropertyChange(CHECKED_LOCS_PROPERTY, oldValue, this.locChecked);
    }

    private void checkSearchResultChecked() {
        for (LocWithMothBean rslts : searchResults) {
            if (rslts.isChecked() && locChecked) {
                return;
            } else if (rslts.isChecked() && !locChecked) {
                setLocChecked(true);
                return;
            }
        }
        if (locChecked) {
            setLocChecked(false);
        }
    }

    /**
     * Returns checked list of search results
     */
    public SolaObservableList<LocWithMothBean> getCheckedLocs() {
        SolaObservableList<LocWithMothBean> checkedLocs =
                new SolaObservableList<LocWithMothBean>();
        for (LocWithMothBean rslts : searchResults) {
            if (rslts.isChecked()) {
                checkedLocs.add(rslts);
            }
        }
        return checkedLocs;
    }
}
