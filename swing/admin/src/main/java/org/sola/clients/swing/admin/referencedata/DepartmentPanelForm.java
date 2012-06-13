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
import org.sola.clients.beans.referencedata.DepartmentBean;
import org.sola.clients.beans.referencedata.OfficeBean;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.referencedata.DepartmentPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Shows {@link DepartmentPanel} and allows to change department object data.
 */
public class DepartmentPanelForm extends ContentPanel {

    private boolean closeOnSave;
    private DepartmentBean departmentBean;
    private ResourceBundle resourceBundle;
    
    /**
     * Default constructor
     */
    public DepartmentPanelForm() {
        initComponents();
    }

    /**
     * Form constructor.
     *
     * @param officeBean The instance of office object to show on the panel.
     * @param closeOnSave Indicates whether to close the form upon save action
     * takes place.
     */
    public DepartmentPanelForm(DepartmentBean departmentBean, boolean closeOnSave) {
        this.departmentBean = departmentBean;
        this.closeOnSave = closeOnSave;
        resourceBundle = ResourceBundle.getBundle("org/sola/clients/swing/admin/referencedata/Bundle");

        initComponents();
        setDepartmentBean(this.departmentBean);
    }
    
    public boolean isCloseOnSave() {
        return closeOnSave;
    }

    public void setCloseOnSave(boolean closeOnSave) {
        this.closeOnSave = closeOnSave;
        customizePanel();
    }

    public DepartmentBean getDepartmentBean() {
        return departmentPanel.getDepartmentBean();
    }

    public final void setDepartmentBean(DepartmentBean departmentBean) {
        this.departmentBean = departmentBean;
        departmentPanel.setDepartmentBean(departmentBean);
        customizePanel();
    }

    private void customizePanel() {
        if (departmentBean != null) {
            headerPanel.setTitleText(String.format(
                    resourceBundle.getString("DepartmentPanelForm.headerPanel.titleText"),
                    departmentBean.getTranslatedDisplayValue()));
        } else {
            headerPanel.setTitleText(String.format(resourceBundle.getString("DepartmentPanelForm.headerPanel.titleText"),
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

        if (departmentPanel.save(true)) {
            MessageUtility.displayMessage(ClientMessage.ADMIN_DEPARTMENT_SAVED,
                    new String[]{departmentPanel.getDepartmentBean().getTranslatedDisplayValue()});
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
        departmentPanel = new org.sola.clients.swing.ui.referencedata.DepartmentPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();

        setHeaderPanel(headerPanel);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/admin/referencedata/Bundle"); // NOI18N
        headerPanel.setTitleText(bundle.getString("DepartmentPanelForm.headerPanel.titleText")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/save.png"))); // NOI18N
        btnSave.setText(bundle.getString("DepartmentPanelForm.btnSave.text")); // NOI18N
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
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(departmentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(departmentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        save();
    }//GEN-LAST:event_btnSaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private org.sola.clients.swing.ui.referencedata.DepartmentPanel departmentPanel;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
