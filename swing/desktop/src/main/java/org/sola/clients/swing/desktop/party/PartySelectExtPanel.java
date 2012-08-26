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
package org.sola.clients.swing.desktop.party;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.sola.clients.beans.party.PartyBean;
import org.sola.clients.beans.party.PartySummaryBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.desktop.DesktopApplication;
import org.sola.clients.swing.ui.MainContentPanel;
import org.sola.clients.swing.ui.party.PartySearchPanel;
import org.sola.clients.swing.ui.party.PartySelectPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Extends {@link PartySelectPanel} to handle its events and show appropriate
 * forms.
 */
public class PartySelectExtPanel extends PartySelectPanel {

    public PartySelectExtPanel() {
        super();
        addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                switch (evt.getPropertyName()) {
                    case PartySelectPanel.SEARCH_PARTY:
                        openSearch();
                        break;
                    case PartySelectPanel.VIEW_PARTY:
                        openView((PartyBean) evt.getNewValue());
                        break;
                    case PartySelectPanel.EDIT_PARTY:
                        openCreateEdit((PartyBean) evt.getNewValue());
                        break;
                    case PartySelectPanel.REMOVE_PARTY:
                        setPartySummary(null);
                        break;
                    case PartySelectPanel.ADD_PARTY:
                        openCreateEdit(null);
                        break;
                }
            }
        });
    }

    private void openSearch() {
        SolaTask t = new SolaTask<Void, Void>() {

            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_PERSONSEARCH));
                final PersonSearchForm partySearchPanelForm = new PersonSearchForm();
                partySearchPanelForm.getPartySeachPanel().setShowSelectButton(true);
                partySearchPanelForm.getPartySeachPanel().addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (evt.getPropertyName().equals(PartySearchPanel.SELECT_PARTY_PROPERTY)) {
                            setPartySummary((PartySummaryBean) evt.getNewValue());
                            partySearchPanelForm.close();
                        }
                    }
                });
                DesktopApplication.getMainForm().getMainContentPanel().addPanel(partySearchPanelForm, MainContentPanel.CARD_SEARCH_PERSONS, true);
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }

    private void openView(PartyBean party) {
        DesktopApplication.getMainForm().getMainContentPanel().addPanel(new PartyPanelForm(false, party, true, true), MainContentPanel.CARD_PERSON, true);
    }

    private void openCreateEdit(PartyBean party) {
        PartyPanelForm form =new PartyPanelForm(true, party, false, true);
        form.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getPropertyName().equals(PartyPanelForm.PARTY_SAVED)){
                    setPartySummary((PartySummaryBean)evt.getNewValue());
                }
            }
        });
        DesktopApplication.getMainForm().getMainContentPanel().addPanel(form, MainContentPanel.CARD_PERSON, true);
    }
}
