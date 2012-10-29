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
package org.sola.clients.swing.desktop.source;

import org.sola.clients.beans.source.SourceBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.source.DocumentPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Shows document properties and allows create/save operations.
 */
public class DocumentForm extends ContentPanel {

    private SourceBean document;
    public static final String DOCUMENT_SAVED = "documentSaved";
    private boolean saveOnAction;
    private boolean closeOnAction;
    private boolean allowEditing;

    private DocumentPanel createDocumentPanel() {
        if (documentPanel == null) {
            documentPanel = new DocumentPanel(document, allowEditing);
        }
        return documentPanel;
    }

    /**
     * Default form constructor
     */
    public DocumentForm() {
        this(null, false, true, false);
    }

    /**
     * Form constructor with document parameter
     *
     * @param document Document to edit.
     */
    public DocumentForm(SourceBean document, boolean saveOnAction, boolean closeOnAction, boolean allowEditing) {
        this.document = document;
        this.saveOnAction = saveOnAction;
        this.closeOnAction = closeOnAction;
        this.allowEditing = allowEditing;
        initComponents();
        postInit();
    }

    private void postInit() {
        btnSave.setEnabled(allowEditing);
        if (document == null) {
            headerPanel.setTitleText(
                    MessageUtility.getLocalizedMessageText(ClientMessage.GENERAL_LABELS_DOCUMENT)
                    + " - " + MessageUtility.getLocalizedMessageText(ClientMessage.GENERAL_LABELS_NEW));
        } else {
            headerPanel.setTitleText(
                    MessageUtility.getLocalizedMessageText(ClientMessage.GENERAL_LABELS_DOCUMENT)
                    + " - #" + document.getLaNr());
        }
        if (closeOnAction) {
            btnSave.setText(MessageUtility.getLocalizedMessageText(ClientMessage.GENERAL_LABELS_SAVE_AND_CLOSE));
        } else {
            btnSave.setText(MessageUtility.getLocalizedMessageText(ClientMessage.GENERAL_LABELS_SAVE));
        }
    }

    private void save() {
        if (saveOnAction) {
            SolaTask t = new SolaTask<Boolean, Boolean>() {

                @Override
                public Boolean doTask() {
                    setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_DOCUMENT_SAVING));
                    return documentPanel.save();
                }

                @Override
                protected void taskDone() {
                    if (get() == Boolean.TRUE) {
                        fireDocumentSave();
                    }
                }
            };
            TaskManager.getInstance().runTask(t);
        } else {
            fireDocumentSave();
        }

    }

    private void fireDocumentSave() {
        firePropertyChange(DOCUMENT_SAVED, null, documentPanel.getSource());
        if (closeOnAction) {
            close();
        } else {
            MessageUtility.displayMessage(ClientMessage.SOURCE_SAVED);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new org.sola.clients.swing.ui.HeaderPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        documentPanel = createDocumentPanel();

        setHeaderPanel(headerPanel);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/source/Bundle"); // NOI18N
        headerPanel.setTitleText(bundle.getString("DocumentForm.headerPanel.titleText")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/save.png"))); // NOI18N
        btnSave.setText(bundle.getString("DocumentForm.btnSave.text")); // NOI18N
        btnSave.setFocusable(false);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
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
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(documentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(documentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        save();
    }//GEN-LAST:event_btnSaveActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private org.sola.clients.swing.ui.source.DocumentPanel documentPanel;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
