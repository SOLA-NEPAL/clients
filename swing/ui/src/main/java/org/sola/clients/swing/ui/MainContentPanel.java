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
package org.sola.clients.swing.ui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JPanel;

/**
 * Displays different panels on the Main form.
 */
public class MainContentPanel extends javax.swing.JPanel {

    public final static String CARD_TEST = "testForm";
    public final static String CARD_DASHBOARD = "dashboard";
    public final static String CARD_SEARCH_BY_LAND_OWNER = "searchByPerson";
    public final static String CARD_SEARCH_BY_MOTH_PANA = "searchByMothPana";
    public final static String CARD_SEARCH_BY_MOTH_PANA_PARCEL = "searchByMothPanaParcel";
    public final static String CARD_SEARCH_BY_PARCEL_NO = "searchByParcelNo";
    public final static String CARD_MOTH_SHRESTA_ENTRY = "mothShrestaEntry";
    public final static String CARD_OWNER_ENTRY = "landOwnerEntry";
    public final static String CARD_PARCEL_SEARCH = "parcelSearch";
    public final static String CARD_PARCEL_ENTRY = "parcelsEntry";
    public final static String CARD_ADD_NEW_OWNER = "addNewOwner";
    public final static String CARD_ADMIN_VDC = "vdcAssignmnet";
    public final static String CARD_SEARCH_PERSONS = "searchPersons";
    public final static String CARD_PERSON = "person";
    public final static String CARD_APPSEARCH = "appsearch";
    public final static String CARD_BAUNIT_SEARCH = "baunitsearch";
    public final static String CARD_DOCUMENT_SEARCH = "documentsearch";
    public final static String CARD_APPASSIGNMENT = "appassignment";
    public final static String CARD_APPTRANSFER = "appTransfer";
    public final static String CARD_APPDETAILS = "appdetails";
    public final static String CARD_MAP = "map";
    public final static String CARD_APPLICATION = "application";
    public final static String CARD_NEW_PROPERTY_WIZARD = "newPropertyWizard";
    public final static String CARD_BAUNIT_SELECT_PANEL = "baUnitSelectPanel";
    public final static String CARD_PROPERTY_PANEL = "propertyPanel";
    public final static String CARD_CADASTRECHANGE = "cadastreChange";
    public final static String CARD_MORTGAGE = "mortgagePanel";
    public final static String CARD_SIMPLE_RIGHT = "simpleRightPanel";
    public final static String CARD_SIMPLE_RESTRICTIONS = "simpleRestrictionsPanel";
    public final static String CARD_OWNERSHIP = "ownershipPanel";
    public final static String CARD_OWNERSHIP_SHARE = "ownershipSharePanel";
    public final static String CARD_TRANSACTIONED_DOCUMENT = "transactionedDocumentPanel";
    public final static String CARD_ADMIN_REFDATA_MANAGE = "refDataManagementPanel";
    public final static String CARD_ADMIN_REFDATA = "refDataPanel";
    public final static String CARD_ADMIN_REFDATA_REQUEST_TYPE = "refDataRequestTypePanel";
    public final static String CARD_ADMIN_RRR_TYPE = "refDataRrrTypePanel";
    public final static String CARD_ADMIN_ROLES_MANAGE = "rolesManagementPanel";
    public final static String CARD_ADMIN_ROLE = "rolePanel";
    public final static String CARD_ADMIN_GROUP_MANAGE = "groupManagementPanel";
    public final static String CARD_ADMIN_GROUP = "groupPanel";
    public final static String CARD_ADMIN_USER_MANAGE = "usersManagementPanel";
    public final static String CARD_ADMIN_USER = "userPanel";
    public final static String CARD_ADMIN_USER_PASSWORD = "userPasswordPanel";
    public final static String CARD_ADMIN_BR_MANAGE = "brManagementPanel";
    public final static String CARD_ADMIN_BR = "brPanel";
    public final static String CARD_SOURCE = "sourcePanel";
    public final static String CARD_ADMIN_CALENDAR = "calendarPanel";
    public final static String CARD_OFFICES = "offices";
    public final static String CARD_OFFICE = "office";
    public final static String CARD_DEPARTMENTS = "departmetns";
    public final static String CARD_DEPARTMENT = "department";
    public final static String CARD_VDC = "vdc";
    public final static String CARD_VDCS = "vdcs";
    private HashMap<String, ContentPanel> cards;
    private ArrayList<String> cardsIndex;
    private PropertyChangeListener panelListener;

    /**
     * Default constructor.
     */
    public MainContentPanel() {
        cardsIndex = new ArrayList<String>();
        cards = new HashMap<String, ContentPanel>();
        panelListener = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                handlePanelPropertyChanges(evt);
            }
        };
        initComponents();

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                handleKeyPress(e);
                return false;
            }
        });
    }

    private void handleKeyPress(KeyEvent e) {
        // Catch F1 key press
        if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_F1) {
            ContentPanel panel = getTopCard();
            if (panel != null) {
                panel.showHelp();
            }
        }
        getTopCard();
    }

    /**
     * Listens to the panel property changes to trap close button click.
     */
    private void handlePanelPropertyChanges(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(HeaderPanel.CLOSE_BUTTON_CLICKED)) {
            closePanel((ContentPanel) evt.getSource());
        }
    }

    /**
     * Checks if panel already opened.
     *
     * @param panelClass Class of the panel to search for.
     */
    public boolean isPanelOpened(String cardName) {
        return cards.containsKey(cardName);
    }

    /**
     * Adds panel into cards panels collection.
     *
     * @param panel Panel object to add into the cards collection.
     * @param caller Component called addPanel method. It is used to search for
     * the parent {@link ContentPanel}. If caller itself is {@link ContentPanel}
     * instance, than parentId will be picked up.
     * @param cardName Name of the card to assign to the added panel.
     * @param showPanel Indicates whether to show added panel.
     */
    public void addPanel(ContentPanel panel, Component caller, String cardName, boolean showPanel) {
        String parentId = panel.getParentId();
        if (caller != null) {
            if (ContentPanel.class.isAssignableFrom(caller.getClass())) {
                parentId = ((ContentPanel) caller).getParentId();
            } else {
                while (caller.getParent() != null) {
                    caller = caller.getParent();
                    if (ContentPanel.class.isAssignableFrom(caller.getClass())) {
                        parentId = ((ContentPanel) caller).getId();
                        break;
                    }
                }
            }
        }
        addPanel(panel, parentId, cardName, showPanel);
    }

    /**
     * Adds panel into cards panels collection.
     *
     * @param panel Panel object to add into the cards collection.
     * @param cardName Name of the card to assign to the added panel.
     * @param showPanel Indicates whether to show added panel.
     */
    public void addPanel(ContentPanel panel, String cardName, boolean showPanel) {
        addPanel(panel, panel.getParentId(), cardName, showPanel);
    }

    /**
     * Adds panel into cards panels collection.
     *
     * @param panel Panel object to add into the cards collection.
     * @param parentId Parent panel ID. It will be assigned to the panel and
     * used for closing.
     * @param cardName Name of the card to assign to the added panel.
     * @param showPanel Indicates whether to show added panel.
     */
    public void addPanel(ContentPanel panel, String parentId, String cardName, boolean showPanel) {
        if (isPanelOpened(cardName)) {
            getPanel(cardName).removePropertyChangeListener(panelListener);
            closePanel(cardName);
        }

        panel.setParentId(parentId);
        addCard(panel, cardName);

        panel.addPropertyChangeListener(panelListener);
        pnlContent.add(panel, cardName);
        panel.setMainContentPanel(this);
        panel.panelAdded();

        if (showPanel) {
            showPanel(cardName);
        }
    }

    private void addCard(ContentPanel panel, String cardName) {
        cards.put(cardName, panel);
        if (!cardsIndex.contains(cardName)) {
            cardsIndex.add(cardName);
        }
    }

    /**
     * Adds panel into cards panels collection.
     *
     * @param panel Panel object to add into the cards collection.
     * @param cardName Name of the card to assign to the added panel.
     */
    public void addPanel(ContentPanel panel, String cardName) {
        addPanel(panel, cardName, false);
    }

    /**
     * Returns card/panel by the given card name.
     *
     * @param cardName Name of the card to search by.
     */
    public ContentPanel getPanel(String cardName) {
        return cards.get(cardName);
    }

    /**
     * Closes panel by component object.
     *
     * @param panel Panel object to remove from the cards collection.
     */
    public void closePanel(ContentPanel panel) {
        if (cards.containsValue(panel)) {
            Set<Entry<String, ContentPanel>> tab = cards.entrySet();
            for (Iterator<Entry<String, ContentPanel>> it = tab.iterator(); it.hasNext();) {
                Entry<String, ContentPanel> entry = it.next();
                if (entry.getValue().equals(panel)) {
                    closePanel(entry.getKey());
                    break;
                }
            }
        }
    }

    /**
     * Closes panel by card name.
     *
     * @param cardName Name of the card to close.
     */
    public void closePanel(String cardName) {
        if (cards.containsKey(cardName)) {

            // Check for child panels and close them
            ArrayList<String> cardNames = new ArrayList<String>();
            findChildPanelNames(cardNames, cardName);

            Set<Entry<String, ContentPanel>> tab = cards.entrySet();
            Iterator<Entry<String, ContentPanel>> it = tab.iterator();
            while (it.hasNext()) {
                Entry<String, ContentPanel> entry = it.next();
                for (String tmpCardName : cardNames) {
                    if (entry.getKey().equals(tmpCardName)) {
                        pnlContent.remove(entry.getValue());
                        it.remove();
                        cardsIndex.remove(tmpCardName);
                        break;
                    }
                }
            }
            showLastCard();
        }
    }

    /**
     * Populates Array of strings with child panel card names.
     */
    private void findChildPanelNames(ArrayList<String> cardNames, String cardName) {
        if (cards.containsKey(cardName)) {
            ContentPanel panel = cards.get(cardName);

            Set<Entry<String, ContentPanel>> tab = cards.entrySet();
            Iterator<Entry<String, ContentPanel>> it = tab.iterator();
            while (it.hasNext()) {
                Entry<String, ContentPanel> entry = it.next();
                if (entry.getValue().getParentId() != null
                        && !entry.getValue().getId().equals(panel.getId())
                        && entry.getValue().getParentId().equals(panel.getId())) {
                    findChildPanelNames(cardNames, entry.getKey());
                }
            }
            cardNames.add(cardName);
        }
    }

    /**
     * Obsoleted function. Might be removed from the future release.
     */
    private void closeAutoCollapsiblePanels() {
        Iterator<Entry<String, ContentPanel>> it = cards.entrySet().iterator();
        ArrayList<String> keys = new ArrayList<String>();

        while (it.hasNext()) {
            Entry<String, ContentPanel> entry = it.next();
            if (ContentPanel.class.isAssignableFrom(entry.getValue().getClass())) {
                if (((ContentPanel) entry.getValue()).isCloseOnHide() && !entry.getValue().isVisible()) {
                    keys.add(entry.getKey());
                }
            }
        }

        for (String key : keys) {
            closePanel(key);
        }
    }

    /**
     * Closes child panels.
     */
    private void closeChildPanels(String cardName) {
        if (cards.containsKey(cardName)) {
            ContentPanel panel = cards.get(cardName);
            Iterator<Entry<String, ContentPanel>> it = cards.entrySet().iterator();
            ArrayList<String> cardNames = new ArrayList<String>();

            while (it.hasNext()) {
                Entry<String, ContentPanel> entry = it.next();
                if (entry.getValue().getParentId() != null
                        && !entry.getValue().getId().equals(panel.getId())
                        && entry.getValue().getParentId().equals(panel.getId())) {
                    cardNames.add(entry.getKey());
                }
            }

            for (String key : cardNames) {
                closePanel(key);
            }
        }
    }

    private ContentPanel getTopCard() {
        if (cardsIndex.size() > 0) {
            return cards.get(cardsIndex.get(cardsIndex.size() - 1));
        }
        return null;
    }

    private void showLastCard() {
        if (cardsIndex.size() > 0) {
            ((CardLayout) pnlContent.getLayout()).show(pnlContent, cardsIndex.get(cardsIndex.size() - 1));
        }
    }

    /**
     * Shows panel by the given card name.
     *
     * @param cardName Name of the card to search by.
     */
    public void showPanel(String cardName) {
        if (!cards.containsKey(cardName)) {
            return;
        }

        ((CardLayout) pnlContent.getLayout()).show(pnlContent, cardName);

        // move panel on top
        int cardIndx = cardsIndex.indexOf(cardName);
        if (cardIndx < cardsIndex.size() - 1) {
            closeChildPanels(cardName);
            cardsIndex.remove(cardIndx);
            cardsIndex.add(0, cardName);
        }

        // close autoclosable panels
        closeAutoCollapsiblePanels();

        if (ContentPanel.class.isAssignableFrom(cards.get(cardName).getClass())) {
            ((ContentPanel) cards.get(cardName)).panelShown();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlContent = new javax.swing.JPanel();
        dummyPanel = new javax.swing.JPanel();

        setName("Form"); // NOI18N

        pnlContent.setName("pnlContent"); // NOI18N
        pnlContent.setLayout(new java.awt.CardLayout());

        dummyPanel.setName("dummyPanel"); // NOI18N

        javax.swing.GroupLayout dummyPanelLayout = new javax.swing.GroupLayout(dummyPanel);
        dummyPanel.setLayout(dummyPanelLayout);
        dummyPanelLayout.setHorizontalGroup(
            dummyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 336, Short.MAX_VALUE)
        );
        dummyPanelLayout.setVerticalGroup(
            dummyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 223, Short.MAX_VALUE)
        );

        pnlContent.add(dummyPanel, "card2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel dummyPanel;
    private javax.swing.JPanel pnlContent;
    // End of variables declaration//GEN-END:variables
}
