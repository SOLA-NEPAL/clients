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
package org.sola.clients.swing.desktop.source;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.sola.clients.beans.source.SourceBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Allows to search documents and print out digital copies.
 */
public class DocumentSearchForm extends ContentPanel {

    /**
     * Default constructor.
     */
    public DocumentSearchForm() {
        initComponents();
        //postInit();
    }

    public DocumentSearchForm(boolean showSelectButton, boolean showEditButton) {
        initComponents();
        postInit(showSelectButton, showEditButton);
    }

    private void postInit(boolean showSelectButton, boolean showEditButton) {
        documentSearchPanel.setShowEditButton(showEditButton);
        documentSearchPanel.setShowSelectButton(showSelectButton);
        documentSearchPanel.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                switch (evt.getPropertyName()) {
                    case org.sola.clients.swing.ui.source.DocumentSearchPanel.EDIT_SOURCE:
                        openSourceForm(evt, true);
                        break;
                    case org.sola.clients.swing.ui.source.DocumentSearchPanel.VIEW_SOURCE:
                        openSourceForm(evt, false);
                        break;
                }
                if (evt.getPropertyName().equals(org.sola.clients.swing.ui.source.DocumentSearchPanel.SELECT_SOURCE)) {
                    close();
                }
                firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
            }
        });
    }

    private void openSourceForm(PropertyChangeEvent evt, final boolean allowEditing) {

        final SourceBean source = (SourceBean) evt.getNewValue();

        SolaTask t = new SolaTask<Void, Void>() {

            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_DOCUMENT_FORM_OPENING));
                DocumentForm form = new DocumentForm(source, true, true, allowEditing);
                form.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (evt.getPropertyName().equals(DocumentForm.DOCUMENT_SAVED)) {
                            documentSearchPanel.searchDocuments();
                        }
                    }
                });
                getMainContentPanel().addPanel(form, getThis().getId(), form.getId(), true);
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }

    private DocumentSearchForm getThis() {
        return this;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        documentSearchPanel = new org.sola.clients.swing.ui.source.DocumentSearchPanel();

        setHeaderPanel(headerPanel1);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/source/Bundle"); // NOI18N
        setHelpTopic(bundle.getString("DocumentSearchForm.helpTopic")); // NOI18N
        setName("Form"); // NOI18N

        headerPanel1.setName("headerPanel1"); // NOI18N
        headerPanel1.setTitleText(bundle.getString("DocumentSearchForm.headerPanel1.titleText")); // NOI18N

        jScrollPane1.setBorder(null);
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        documentSearchPanel.setName(bundle.getString("DocumentSearchForm.documentSearchPanel.name")); // NOI18N
        documentSearchPanel.setShowEditButton(false);
        documentSearchPanel.setShowSelectButton(false);
        jScrollPane1.setViewportView(documentSearchPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sola.clients.swing.ui.source.DocumentSearchPanel documentSearchPanel;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
