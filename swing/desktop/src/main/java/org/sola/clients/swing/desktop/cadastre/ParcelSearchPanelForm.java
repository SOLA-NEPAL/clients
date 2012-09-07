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
package org.sola.clients.swing.desktop.cadastre;

import org.sola.clients.beans.cadastre.CadastreObjectSearchResultBean;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.cadastre.ParcelSearchPanel;

/**
 *
 * @author Kumar
 */
public class ParcelSearchPanelForm extends ContentPanel {

    public static final String SELECT_PARCEL_PROPERTY = "selectParcel";

    /**
     * Creates new form ParcelSearchPanelForm
     */
    public ParcelSearchPanelForm() {
        initComponents();
    }

    public CadastreObjectSearchResultBean getSelectedParcelSearchResultBean() {
        return this.getParcelSearchPanel()
                .getParcelSearchResuls().getSelectedCadastreObjectSearchResult();
    }

    public ParcelSearchPanel getParcelSearchPanel() {
        return parcelSearchPanel;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        parcelSearchPanel = new org.sola.clients.swing.ui.cadastre.ParcelSearchPanel();

        setHeaderPanel(headerPanel1);

        headerPanel1.setTitleText("Parcel Search");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(parcelSearchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(parcelSearchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private org.sola.clients.swing.ui.cadastre.ParcelSearchPanel parcelSearchPanel;
    // End of variables declaration//GEN-END:variables
}
