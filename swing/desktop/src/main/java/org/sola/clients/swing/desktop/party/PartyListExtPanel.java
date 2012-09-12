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
import org.sola.clients.beans.controls.SolaList;
import org.sola.clients.beans.party.PartyBean;
import org.sola.clients.beans.party.PartySummaryBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.desktop.DesktopApplication;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.party.PartyListPanel;
import org.sola.clients.swing.ui.party.PartySearchPanel;
import org.sola.clients.swing.ui.party.PartySelectPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;
import org.sola.webservices.transferobjects.EntityAction;

/**
 * Extends {@link PartyListPanel} to handle buttons events and open appropriate forms.
 */
public class PartyListExtPanel extends PartyListPanel {
    public PartyListExtPanel(){
        this(null);
    }
    
    public PartyListExtPanel(SolaList<PartySummaryBean> partyList){
        super(partyList);
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
                final PersonSearchForm form = new PersonSearchForm();
                form.getPartySeachPanel().setShowSelectButton(true);
                form.getPartySeachPanel().addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (evt.getPropertyName().equals(PartySearchPanel.SELECT_PARTY_PROPERTY)) {
                            PartySummaryBean selectedParty = (PartySummaryBean) evt.getNewValue();
                            if(getPersonList().contains(selectedParty)){
                                PartySummaryBean existingParty = getPersonList().get(getPersonList().indexOf(selectedParty));
                                if(existingParty.getEntityAction()!=null && existingParty.getEntityAction() == EntityAction.DISASSOCIATE){
                                    existingParty.setEntityAction(null);
                                    getPersonList().filter();
                                }
                            } else {
                                getPersonList().addAsNew(selectedParty);
                            }
                            form.close();
                        }
                    }
                });
                openContentPanel(form);
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }

    private void openContentPanel(ContentPanel form){
        DesktopApplication.getMainForm().getMainContentPanel().addPanel(form, this, form.getId(), true);
    }
    
    private void openView(PartyBean party) {
        openContentPanel(new PartyPanelForm(false, party, true, true));
    }

    private void openCreateEdit(PartyBean party) {
        final boolean isNew = party==null;
        PartyPanelForm form =new PartyPanelForm(true, party, false, true);
        form.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getPropertyName().equals(PartyPanelForm.PARTY_SAVED)){
                    PartySummaryBean savedParty = (PartySummaryBean)evt.getNewValue();
                    if(isNew){
                        getPersonList().addAsNew(savedParty);
                    } else {
                        getPersonList().set(getPersonList().indexOf(savedParty), savedParty);
                    }
                }
            }
        });
        openContentPanel(form);
    }
}
