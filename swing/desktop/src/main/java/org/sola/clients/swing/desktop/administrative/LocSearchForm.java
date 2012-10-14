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
import org.sola.clients.beans.administrative.BaUnitSearchResultListBean;
import org.sola.clients.beans.administrative.LocSearchParamsBean;
import org.sola.clients.beans.administrative.LocWithMothBean;
import org.sola.clients.beans.party.PartySearchResultBean;
import org.sola.clients.beans.party.PartySearchResultListBean;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.administrative.LocSearchCreatePanel;

/**
 * Form to search LOC by different ways.
 */
public class LocSearchForm extends ContentPanel {

    /**
     * Default form constructor.
     */
    public LocSearchForm() {
        initComponents();
        postInit();
    }

    private void postInit(){
        baUnitSearchPanel.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getPropertyName().equals(BaUnitSearchResultListBean.SELECTED_BAUNIT_SEARCH_RESULT_PROPERTY)){
                    String baUnitId = null;
                    if(evt.getNewValue()!=null){
                        baUnitId = ((BaUnitSearchResultBean)evt.getNewValue()).getId();
                    }
                    search(baUnitId, null, null);
                }
            }
        });
        
        partySearchPanel.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getPropertyName().equals(PartySearchResultListBean.SELECTED_PARTY_SEARCH_RESULT)){
                    String partyId = null;
                    if(evt.getNewValue()!=null){
                        partyId = ((PartySearchResultBean)evt.getNewValue()).getId();
                    }
                    search(null, partyId, null);
                }
            }
        });
        
        locSearchCreatePanel.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getPropertyName().equals(LocSearchCreatePanel.LOC_FOUND)){
                    search(null, null, ((LocWithMothBean)evt.getNewValue()).getId());
                }
            }
        });
    }
    
    private void search(String baUnitId, String partyId, String locId){
        LocSearchParamsBean searchParams = new LocSearchParamsBean();
        searchParams.setBaUnitId(baUnitId);
        searchParams.setPartyId(partyId);
        searchParams.setLocId(locId);
        if(baUnitId!=null){
            locSearchResultsByProperty.searchLocs(searchParams);
        }
        if(partyId!=null){
            locSearchResultsByParty.searchLocs(searchParams);
        }
        if(locId!=null){
            locSearchResultsByMoth.searchLocs(searchParams);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        baUnitSearchPanel = new org.sola.clients.swing.desktop.administrative.BaUnitSearchExtPanel();
        locSearchResultsByProperty = new org.sola.clients.swing.ui.administrative.LocSearchResultsPanel();
        jPanel2 = new javax.swing.JPanel();
        partySearchPanel = new org.sola.clients.swing.ui.party.PartySearchPanel();
        locSearchResultsByParty = new org.sola.clients.swing.ui.administrative.LocSearchResultsPanel();
        jPanel3 = new javax.swing.JPanel();
        locSearchResultsByMoth = new org.sola.clients.swing.ui.administrative.LocSearchResultsPanel();
        locSearchCreatePanel = new org.sola.clients.swing.ui.administrative.LocSearchCreatePanel();

        setHeaderPanel(headerPanel1);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/administrative/Bundle"); // NOI18N
        headerPanel1.setTitleText(bundle.getString("LocSearchForm.headerPanel1.titleText")); // NOI18N

        baUnitSearchPanel.setShowOpenButton(false);
        baUnitSearchPanel.setShowSelectButton(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(baUnitSearchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
                    .addComponent(locSearchResultsByProperty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(baUnitSearchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(locSearchResultsByProperty, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(bundle.getString("LocSearchForm.jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        partySearchPanel.setShowAddButton(false);
        partySearchPanel.setShowEditButton(false);
        partySearchPanel.setShowRemoveButton(false);
        partySearchPanel.setShowSelectButton(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(partySearchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
                    .addComponent(locSearchResultsByParty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(partySearchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(locSearchResultsByParty, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(bundle.getString("LocSearchForm.jPanel2.TabConstraints.tabTitle"), jPanel2); // NOI18N

        locSearchCreatePanel.setCreateNewIfNotFound(false);
        locSearchCreatePanel.setShowToolbar(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(locSearchResultsByMoth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(locSearchCreatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(locSearchCreatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(locSearchResultsByMoth, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(bundle.getString("LocSearchForm.jPanel3.TabConstraints.tabTitle"), jPanel3); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sola.clients.swing.desktop.administrative.BaUnitSearchExtPanel baUnitSearchPanel;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.sola.clients.swing.ui.administrative.LocSearchCreatePanel locSearchCreatePanel;
    private org.sola.clients.swing.ui.administrative.LocSearchResultsPanel locSearchResultsByMoth;
    private org.sola.clients.swing.ui.administrative.LocSearchResultsPanel locSearchResultsByParty;
    private org.sola.clients.swing.ui.administrative.LocSearchResultsPanel locSearchResultsByProperty;
    private org.sola.clients.swing.ui.party.PartySearchPanel partySearchPanel;
    // End of variables declaration//GEN-END:variables
}
