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
package org.sola.clients.swing.desktop.party;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.sola.clients.beans.party.PartyBean;
import org.sola.clients.beans.party.PartySearchResultBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.party.PartySearchPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Holds {@link PartySearchPanel} component.
 */
public class PersonSearchForm extends ContentPanel {

    /**
     * Default constructor.
     */
    public PersonSearchForm() {
        initComponents();
        partySearchPanel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(PartySearchPanel.CREATE_NEW_PARTY_PROPERTY)
                        || evt.getPropertyName().equals(PartySearchPanel.EDIT_PARTY_PROPERTY)
                        || evt.getPropertyName().equals(PartySearchPanel.VIEW_PARTY_PROPERTY)) {
                    handleSearchPanelEvents(evt);
                }
            }
        });
    }

    private void handleSearchPanelEvents(final PropertyChangeEvent evt) {
        SolaTask t = new SolaTask<Void, Void>() {
            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_PERSON));
                PartyPanelForm panel = null;
                switch (evt.getPropertyName()) {
                    case PartySearchPanel.CREATE_NEW_PARTY_PROPERTY:
                        panel = new PartyPanelForm(true, null, false, false);
                        panel.addPropertyChangeListener(new PropertyChangeListener() {

                            @Override
                            public void propertyChange(PropertyChangeEvent evt) {
                                if (evt.getPropertyName().equals(PartyPanelForm.PARTY_SAVED)) {
                                    ((PartyPanelForm) evt.getSource()).setParty(null);
                                }
                            }
                        });
                        break;
                    case PartySearchPanel.EDIT_PARTY_PROPERTY:
                        setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_GETTING_PERSON));
                        panel = new PartyPanelForm(true, PartyBean.getParty(((PartySearchResultBean)evt.getNewValue()).getId()), false, true);
                        break;
                    case PartySearchPanel.VIEW_PARTY_PROPERTY:
                        setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_GETTING_PERSON));
                        panel = new PartyPanelForm(true, PartyBean.getParty(((PartySearchResultBean)evt.getNewValue()).getId()), true, true);
                        break;
                }
                if (panel != null) {
                    setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_PERSON));
                    openPersonPanel(panel);
                }
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }

    private void openPersonPanel(PartyPanelForm panel){
        getMainContentPanel().addPanel(panel, this.getId(), panel.getId(), true);
    }
    
    public PartySearchResultBean getSelectedPartySearchResultBean(){
        return this.getPartySearchPanel()
                .getPartySearchResuls().getSelectedPartySearchResult();
    }
    
    public PartySearchPanel getPartySearchPanel() {
        return partySearchPanel;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new org.sola.clients.swing.ui.HeaderPanel();
        partySearchPanel = new org.sola.clients.swing.ui.party.PartySearchPanel();

        setHeaderPanel(headerPanel);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/party/Bundle"); // NOI18N
        setHelpTopic(bundle.getString("PersonSearchForm.helpTopic")); // NOI18N

        headerPanel.setName("headerPanel"); // NOI18N
        headerPanel.setTitleText(bundle.getString("PersonSearchForm.headerPanel.titleText")); // NOI18N

        partySearchPanel.setName("partySearchPanel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(partySearchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(partySearchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sola.clients.swing.ui.HeaderPanel headerPanel;
    private org.sola.clients.swing.ui.party.PartySearchPanel partySearchPanel;
    // End of variables declaration//GEN-END:variables

}
