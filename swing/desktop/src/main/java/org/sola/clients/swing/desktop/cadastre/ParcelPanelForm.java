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

import org.sola.clients.beans.cadastre.CadastreObjectSummaryBean;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.cadastre.ParcelPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Holds instance of the {@link ParcelPanel} and allows to change parcel data.
 */
public class ParcelPanelForm extends ContentPanel {
    public static final String CADASTRE_OBJECT_SAVE = "cadastreObjectSaved";
    private boolean saveOnAction;
    private boolean closeOnAction;
    private CadastreObjectSummaryBean cadastreObject;
    
    private ParcelPanel createParcelPanel(){
        if(parcelPanel == null){
            parcelPanel = new ParcelPanel(cadastreObject);
        }
        return parcelPanel;
    }
    /**
     * Default constructor.
     */
    public ParcelPanelForm() {
        this(null, false, true);
    }
    
    /**
     * Form constructor with initial parameters.
     * @param cadastreObject {@link CadastreObjectSummaryBean} instance to show on the form.
     * @param saveOnAction Boolean flag, indicating whether to save cadastre object into DB or not.
     * @param closeOnAction Boolean flag, indicating whether to close form upon save button click.
     */
    public ParcelPanelForm(CadastreObjectSummaryBean cadastreObject, boolean saveOnAction, boolean closeOnAction){
        this.saveOnAction = saveOnAction;
        this.closeOnAction = closeOnAction;
        this.cadastreObject = cadastreObject;
        initComponents();
        postInit();
    }
    
    private void postInit(){
        if(closeOnAction){
            btnSave.setText(MessageUtility.getLocalizedMessageText(ClientMessage.GENERAL_LABELS_SAVE_AND_CLOSE));
        } else {
            btnSave.setText(MessageUtility.getLocalizedMessageText(ClientMessage.GENERAL_LABELS_SAVE));
        }
    }

    private void save(){
        if(parcelPanel.getCadastreObject().validate(true).size() < 1){
            if(saveOnAction){
                parcelPanel.getCadastreObject().saveCadastreObject();
            }
            firePropertyChange(CADASTRE_OBJECT_SAVE, null, parcelPanel.getCadastreObject());
            if(closeOnAction){
                close();
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        parcelPanel = createParcelPanel();

        setHeaderPanel(headerPanel1);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/cadastre/Bundle"); // NOI18N
        headerPanel1.setTitleText(bundle.getString("ParcelPanelForm.headerPanel1.titleText")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/save.png"))); // NOI18N
        btnSave.setText(bundle.getString("ParcelPanelForm.btnSave.text")); // NOI18N
        btnSave.setFocusable(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSave);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(parcelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1059, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(parcelPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 71, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        save();
    }//GEN-LAST:event_btnSaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JToolBar jToolBar1;
    private org.sola.clients.swing.ui.cadastre.ParcelPanel parcelPanel;
    // End of variables declaration//GEN-END:variables
}
