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
package org.sola.clients.swing.admin.referencedata;

import java.util.ResourceBundle;
import org.sola.clients.beans.referencedata.OfficeBean;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Allows to create and save office object
 */
public class OfficePanelForm extends ContentPanel {

    private boolean closeOnSave;
    private OfficeBean officeBean;
    private ResourceBundle resourceBundle;

    /**
     * Creates new form OfficePanelForm
     */
    public OfficePanelForm() {
        initComponents();
    }

    /**
     * Form constructor.
     *
     * @param officeBean The instance of office object to show on the panel.
     * @param closeOnSave Indicates whether to close the form upon save action
     * takes place.
     */
    public OfficePanelForm(OfficeBean officeBean, boolean closeOnSave) {
        this.officeBean = officeBean;
        this.closeOnSave = closeOnSave;
        resourceBundle = ResourceBundle.getBundle("org/sola/clients/swing/admin/referencedata/Bundle");

        initComponents();
        setOfficeBean(this.officeBean);
    }

    public boolean isCloseOnSave() {
        return closeOnSave;
    }

    public void setCloseOnSave(boolean closeOnSave) {
        this.closeOnSave = closeOnSave;
        customizePanel();
    }

    public OfficeBean getOfficeBean() {
        return officePanel.getOfficeBean();
    }

    public final void setOfficeBean(OfficeBean officeBean) {
        this.officeBean = officeBean;
        officePanel.setOfficeBean(officeBean);
        customizePanel();
    }

    private void customizePanel() {
        if (officeBean != null) {
            headerPanel.setTitleText(String.format(
                    resourceBundle.getString("OfficePanelForm.headerPanel.titleText"),
                    officeBean.getTranslatedDisplayValue()));
        } else {
            headerPanel.setTitleText(String.format(resourceBundle.getString("OfficePanelForm.headerPanel.titleText"),
                    MessageUtility.getLocalizedMessageText(ClientMessage.GENERAL_LABELS_NEW)));
        }

        if (closeOnSave) {
            btnSave.setText(MessageUtility.getLocalizedMessage(
                    ClientMessage.GENERAL_LABELS_SAVE_AND_CLOSE).getMessage());
        } else {
            btnSave.setText(MessageUtility.getLocalizedMessage(
                    ClientMessage.GENERAL_LABELS_SAVE).getMessage());
        }
    }

    private void save() {
        boolean isSaved = false;

        if (officePanel.save(true)) {
            MessageUtility.displayMessage(ClientMessage.ADMIN_OFFICE_SAVED,
                    new String[]{officePanel.getOfficeBean().getTranslatedDisplayValue()});
            isSaved = true;
        }

        if (isSaved) {
            firePropertyChange(ReferenceDataPanelForm.REFDATA_SAVED_PROPERTY, false, true);
            if (closeOnSave) {
                close();
            } else {
                customizePanel();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new org.sola.clients.swing.ui.HeaderPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        officePanel = new org.sola.clients.swing.ui.referencedata.OfficePanel();

        setHeaderPanel(headerPanel);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/admin/referencedata/Bundle"); // NOI18N
        headerPanel.setTitleText(bundle.getString("OfficePanelForm.headerPanel.titleText")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/save.png"))); // NOI18N
        btnSave.setText(bundle.getString("OfficePanelForm.btnSave.text")); // NOI18N
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
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(officePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(officePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        save();
    }//GEN-LAST:event_btnSaveActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel;
    private javax.swing.JToolBar jToolBar1;
    private org.sola.clients.swing.ui.referencedata.OfficePanel officePanel;
    // End of variables declaration//GEN-END:variables
}
