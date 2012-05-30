/**
 * ******************************************************************************************
 * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations (FAO).
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice,this list
 *       of conditions and the following disclaimer.
 *    2. Redistributions in binary form must reproduce the above copyright notice,this list
 *       of conditions and the following disclaimer in the documentation and/or other
 *       materials provided with the distribution.
 *    3. Neither the name of FAO nor the names of its contributors may be used to endorse or
 *       promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,STRICT LIABILITY,OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * *********************************************************************************************
 */
package org.sola.clients.swing.admin.security;

import java.util.ResourceBundle;
import org.sola.clients.beans.security.UserBean;
import org.sola.clients.swing.admin.VdcAssignment;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.MainContentPanel;
import org.sola.clients.swing.ui.security.UserPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Holds instance of {@link UserPanel}
 */
public class UserPanelForm extends ContentPanel {

    public static final String USER_SAVED_PROPERTY = "UserSaved";
    
    private boolean saveOnAction;
    private boolean closeOnSave;
    private boolean readOnly;
    private UserBean userBean;
    private ResourceBundle resourceBundle;
    
    /** Default constructor. */
    public UserPanelForm() {
        initComponents();       
    }
    
    /** 
     * Form constructor. 
     * @param userBean The user bean instance to show on the panel.
     * @param saveOnAction If <code>true</code>, user will be saved into database. 
     * If <code>false</code>, user will be validated and validation result returned as a value of 
     * {@link UserPanelForm.USER_SAVED_PROPERTY} property change event.
     * @param closeOnSave Indicates whether to close the form upon save action takes place.
     * @param readOnly Indicates whether to display provided {@link UserBean} in read only mode or not.
     */
    public UserPanelForm(UserBean userBean, boolean saveOnAction, boolean closeOnSave, boolean readOnly) {
        this.userBean = userBean;
        this.saveOnAction = saveOnAction;
        this.readOnly = readOnly;
        this.closeOnSave = closeOnSave;
        resourceBundle = ResourceBundle.getBundle("org/sola/clients/swing/admin/security/Bundle"); 
        
        initComponents();
        setUserBean(this.userBean);
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        customizePanel();
    }
    
    public boolean isCloseOnSave() {
        return closeOnSave;
    }

    public void setCloseOnSave(boolean closeOnSave) {
        this.closeOnSave = closeOnSave;
        customizePanel();
    }

    public UserBean getUserBean() {
        return userPanel.getUser();
    }

    public final void setUserBean(UserBean userBean) {
        this.userBean = userBean;
        userPanel.setUser(userBean);
        customizePanel();
    }

    public boolean isSaveOnAction() {
        return saveOnAction;
    }

    public void setSaveOnAction(boolean saveOnAction) {
        this.saveOnAction = saveOnAction;
    }
    
    private void customizePanel() {
        btnSave.setEnabled(!readOnly);
        userPanel.lockForm(readOnly);
        
        if (userBean != null) {
            headerPanel.setTitleText(String.format(
                    resourceBundle.getString("UserPanelForm.headerPanel.titleText.EditUser"), 
                    getUserBean().getUserName()));
        } else {
            headerPanel.setTitleText(resourceBundle.getString("UserPanelForm.headerPanel.titleText"));
        }

        if (closeOnSave) {
            btnSave.setText(MessageUtility.getLocalizedMessage(
                    ClientMessage.GENERAL_LABELS_SAVE_AND_CLOSE).getMessage());
        } else {
            btnSave.setText(MessageUtility.getLocalizedMessage(
                    ClientMessage.GENERAL_LABELS_SAVE).getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new org.sola.clients.swing.ui.HeaderPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnAsignVDC = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        userPanel = new org.sola.clients.swing.ui.security.UserPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cmbLMO = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();

        setCloseOnHide(true);
        setHeaderPanel(headerPanel);

        headerPanel.setName("headerPanel"); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/admin/security/Bundle"); // NOI18N
        headerPanel.setTitleText(bundle.getString("UserPanelForm.headerPanel.titleText")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        btnAsignVDC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/assign.png"))); // NOI18N
        btnAsignVDC.setText(bundle.getString("UserPanelForm.btnAsignVDC.text")); // NOI18N
        btnAsignVDC.setFocusable(false);
        btnAsignVDC.setName(bundle.getString("UserPanelForm.btnAsignVDC.name")); // NOI18N
        btnAsignVDC.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAsignVDC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignVDCActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAsignVDC);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/save.png"))); // NOI18N
        btnSave.setText(bundle.getString("UserPanelForm.btnSave.text")); // NOI18N
        btnSave.setFocusable(false);
        btnSave.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSave);

        userPanel.setName("userPanel"); // NOI18N

        jPanel4.setName(bundle.getString("UserPanelForm.jPanel4.name")); // NOI18N

        jPanel3.setName(bundle.getString("UserPanelForm.jPanel3.name")); // NOI18N
        jPanel3.setLayout(new java.awt.GridLayout(1, 2, 25, 0));

        jPanel2.setName(bundle.getString("UserPanelForm.jPanel2.name")); // NOI18N

        cmbLMO.setName(bundle.getString("UserPanelForm.cmbLMO.name")); // NOI18N

        jLabel2.setText(bundle.getString("UserPanelForm.jLabel2.text")); // NOI18N
        jLabel2.setName(bundle.getString("UserPanelForm.jLabel2.name")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(cmbLMO, 0, 603, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmbLMO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jPanel3.add(jPanel2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(userPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(userPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        boolean isSaved = false;
        
        if(saveOnAction){
            if(userPanel.saveUser(true)){
                MessageUtility.displayMessage(ClientMessage.ADMIN_USER_SAVED);
                isSaved = true;
            }
        } else {
            if(userPanel.validateUser(true)){
                isSaved = true;
            }
        }
        
        if(isSaved){
            firePropertyChange(USER_SAVED_PROPERTY, false, true);
            if(closeOnSave){
                close();
            } else {
                customizePanel();
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnAsignVDCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignVDCActionPerformed
        // TODO add your handling code here:
        VdcAssignment vdc=new VdcAssignment();
        getMainContentPanel().addPanel(vdc, MainContentPanel.CARD_ADMIN_VDC, true);        
        
    }//GEN-LAST:event_btnAsignVDCActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsignVDC;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cmbLMO;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JToolBar jToolBar1;
    private org.sola.clients.swing.ui.security.UserPanel userPanel;
    // End of variables declaration//GEN-END:variables
}
