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
package org.sola.clients.swing.desktop.administrative;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.sola.clients.beans.administrative.BaUnitSearchResultBean;
import org.sola.clients.beans.party.PartySearchResultBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.desktop.DesktopApplication;
import org.sola.clients.swing.desktop.party.PersonSearchForm;
import org.sola.clients.swing.ui.administrative.BaUnitSearchPanel;
import org.sola.clients.swing.ui.party.PartySearchPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Extends {@link BaUnitSearchPanel} to handle events and open appropriate forms.
 */
public class BaUnitSearchExtPanel extends BaUnitSearchPanel {
   
    public BaUnitSearchExtPanel(){
        super();
        addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                switch (evt.getPropertyName()) {
                    case BaUnitSearchPanel.OPEN_SELECTED_BAUNIT:
                        openPropertyForm((BaUnitSearchResultBean)evt.getNewValue());
                        break;
                    case BaUnitSearchPanel.BROWSE_RIGHTHOLDER:
                        openPersonSearchForm();
                        break;
                }
            }
        });
    }
    
    private void openPropertyForm(final BaUnitSearchResultBean baUnitSearchResult) {
        SolaTask t = new SolaTask<Void, Void>() {
            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_PROPERTY));
                PropertyPanel propertyPanel = new PropertyPanel(baUnitSearchResult.getId());
                DesktopApplication.getMainForm().getMainContentPanel()
                        .addPanel(propertyPanel, getThis(), propertyPanel.getId(), true);
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }
    
    private void openPersonSearchForm() {
        SolaTask t = new SolaTask<Void, Void>() {
            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_PERSONSEARCH));
                final PersonSearchForm form = new PersonSearchForm();
                form.getPartySearchPanel().setShowAddButton(false);
                form.getPartySearchPanel().setShowEditButton(false);
                form.getPartySearchPanel().setShowRemoveButton(false);
                form.getPartySearchPanel().addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (evt.getPropertyName().equals(PartySearchPanel.SELECT_PARTY_PROPERTY)) {
                            PartySearchResultBean party = (PartySearchResultBean)evt.getNewValue();
                            baUnitSearchParams.setRightHolderId(party.getId());
                            baUnitSearchParams.setRightHolderName(party.getFullName());
                            form.close();
                        }
                    }
                });
                DesktopApplication.getMainForm().getMainContentPanel().addPanel(form, getThis(), form.getId(), true);
                
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }
    
    private BaUnitSearchExtPanel getThis(){
        return this;
    }
}
