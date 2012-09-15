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
package org.sola.clients.swing.ui.source;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import org.sola.clients.beans.controls.SolaList;
import org.sola.clients.beans.security.SecurityBean;
import org.sola.clients.beans.source.SourceBean;
import org.sola.clients.beans.source.SourceListBean;
import org.sola.common.RolesConstants;

/**
 * Displays documents list. This panel could be used on different forms, where
 * documents list should be displayed and managed.
 * <p/>
 * {@link DoumentsPanel} is used to display list of documents.
 */
public class DocumentsManagementPanel extends javax.swing.JPanel {

    public static final String EVENT_NEW = "new";
    public static final String EVENT_ADD_FROM_APPLICATION = "addFromApplication";
    public static final String EVENT_SEARCH = "search";
    public static final String EVENT_VIEW = "view";
    public static final String EVENT_OPEN_ATTACHMENT = "openAttachment";
    public static final String EVENT_EDIT = "edit";
    public static final String EVENT_REMOVE = "remove";
    
    private SolaList<SourceBean> sourceList;
    private boolean allowEdit = true;

    /**
     * Creates new instance of {@link DocumentsPanel}.
     */
    private DocumentsPanel createDocumentsPanel() {
        DocumentsPanel panel;
        if (sourceList != null) {
            panel = new DocumentsPanel(sourceList);
        } else {
            panel = new DocumentsPanel();
        }

        panel.getSourceListBean().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(SourceListBean.SELECTED_SOURCE_PROPERTY)) {
                    customizeButtons();
                }
            }
        });
        return panel;
    }

    /**
     * Default constructor
     */
    public DocumentsManagementPanel() {
        this(null, false);
    }

    /**
     * This constructor is called to display predefined list of
     * {@link SourceBean}.
     *
     * @param sourceList The list of {@link SourceBean} to display.
     * @param applicationBean {ApplicationBean} is used to attach documents from
     * the application.
     * @param allowEdit Indicates whether it is allowed to do changes on the
     * list.
     */
    public DocumentsManagementPanel(SolaList<SourceBean> sourceList, boolean allowEdit) {
        this.sourceList = sourceList;
        this.allowEdit = allowEdit;
        initComponents();
        customizeButtons();
    }

    /** Enables or disables buttons, depending on the selection in the list. */
    private void customizeButtons() {
        boolean enabled = documentsPanel.getSourceListBean().getSelectedSource() !=null;
        boolean canSave = SecurityBean.isInRole(RolesConstants.SOURCE_SAVE) && allowEdit;
        
        btnNew.setEnabled(canSave);
        btnAddFromApplication.setEnabled(allowEdit);
        btnSearch.setEnabled(allowEdit);
        btnView.setEnabled(enabled);
        btnOpenAttachment.setEnabled(false);
        btnEdit.setEnabled(enabled && canSave);
        btnRemove.setEnabled(enabled && allowEdit);

        if (enabled) {
            if (documentsPanel.getSourceListBean().getSelectedSource().getArchiveDocumentId() != null 
                    && documentsPanel.getSourceListBean().getSelectedSource().getArchiveDocumentId().length() > 0) {
                btnOpenAttachment.setEnabled(true);
            }
        }
        
        menuNew.setEnabled(btnNew.isEnabled());
        menuAddFromApplication.setEnabled(btnAddFromApplication.isEnabled());
        menuSearch.setEnabled(btnSearch.isEnabled());
        menuView.setEnabled(btnView.isEnabled());
        menuOpenAttachment.setEnabled(btnOpenAttachment.isEnabled());
        menuEdit.setEnabled(btnEdit.isEnabled());
        menuRemove.setEnabled(btnRemove.isEnabled());
    }

    /**
     * Loads sources by the given list of IDs.
     */
    public final void loadSourcesByIds(List<String> sourceIds) {
        documentsPanel.loadSourcesByIds(sourceIds);
    }

    //<editor-fold defaultstate="collapsed" desc="Buttons visibility getters/setters">
    public boolean isAllowEdit() {
        return allowEdit;
    }
    
    public void setAllowEdit(boolean allowEdit) {
        this.allowEdit = allowEdit;
        customizeButtons();
    }
    
    public boolean isShowNewButton(){
        return btnNew.isVisible();
    }
    
    public void setShowNewButton(boolean show){
        btnNew.setVisible(show);
        showHideSeparators();
    }
    
    public boolean isShowAddFromApplicationButton(){
        return btnAddFromApplication.isVisible();
    }
    
    public void setShowAddFromApplicationButton(boolean show){
        btnAddFromApplication.setVisible(show);
        showHideSeparators();
    }
    
    public boolean isShowSearchButton(){
        return btnSearch.isVisible();
    }
    
    public void setShowSearchButton(boolean show){
        btnSearch.setVisible(show);
        showHideSeparators();
    }
    
    public boolean isShowViewButton(){
        return btnView.isVisible();
    }
    
    public void setShowViewButton(boolean show){
        btnView.setVisible(show);
        showHideSeparators();
    }
    
    public boolean isShowOpenAttachmentButton(){
        return btnOpenAttachment.isVisible();
    }
    
    public void setShowOpenAttachmentButton(boolean show){
        btnOpenAttachment.setVisible(show);
        showHideSeparators();
    }
    
    public boolean isShowEditButton(){
        return btnEdit.isVisible();
    }
    
    public void setShowEditButton(boolean show){
        btnEdit.setVisible(show);
        showHideSeparators();
    }
    
    public boolean isShowRemoveButton(){
        return btnRemove.isVisible();
    }
    
    public void setShowRemoveButton(boolean show){
        btnRemove.setVisible(show);
        showHideSeparators();
    }
    //</editor-fold>
    
    private void showHideSeparators(){
        // Separator 1
        if((!btnNew.isVisible() && !btnAddFromApplication.isVisible() && !btnSearch.isVisible()) ||
                (!btnOpenAttachment.isVisible() && !btnView.isVisible() && 
                !btnEdit.isVisible() && !btnRemove.isVisible())){
            separator1.setVisible(false);
        } else {
            separator1.setVisible(true);
        }
        separatorMenu1.setVisible(separator1.isVisible());
        
        // Separator 2
        if((!btnView.isVisible() && !btnOpenAttachment.isVisible()) || 
                (!btnEdit.isVisible() && !btnRemove.isVisible())){
            separator2.setVisible(false);
        } else {
            separator2.setVisible(true);
        }
        separatorMenu2.setVisible(separator2.isVisible());
    }
    
    /**
     * Returns the list of sources IDs.
     *
     * @param onlyFiltered Indicates whether to return IDs only from the
     * filtered list. If {@code false}, returns all IDs.
     */
    public final List<String> getSourceIds(boolean onlyFiltered) {
        return documentsPanel.getSourceIds(onlyFiltered);
    }

    /** Opens file attached to the selected source. */
    private void openAttachment() {
        documentsPanel.openAttachment();
        firePropertyChange(EVENT_OPEN_ATTACHMENT, false, true);
    }

    /** Removes selected document. */
    private void remove() {
        if(documentsPanel.removeSelectedDocument()){
            firePropertyChange(EVENT_REMOVE, false, true);
        }
    }

    /** Fires edit source event. */
    private void edit() {
        firePropertyChange(EVENT_EDIT, null, documentsPanel.getSourceListBean().getSelectedSource());
    }
    
    /** Fires search source event. */
    private void search() {
        firePropertyChange(EVENT_SEARCH, false, true);
    }
    
    /** Fires view source event. */
    private void view() {
        firePropertyChange(EVENT_VIEW, null, documentsPanel.getSourceListBean().getSelectedSource());
    }
    
    /** Fires add from application event. */
    private void addFromApplication() {
        firePropertyChange(EVENT_ADD_FROM_APPLICATION, false, true);
    }
    
    /** Fires new source event. */
    private void newSource() {
        firePropertyChange(EVENT_NEW, false, true);
    }
    
    /**
     * Adds new source into the list.
     */
    public void addDocument(SourceBean document) {
        documentsPanel.addDocument(document);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        documentsTablePopupMenu = new javax.swing.JPopupMenu();
        menuNew = new javax.swing.JMenuItem();
        menuAddFromApplication = new javax.swing.JMenuItem();
        menuSearch = new javax.swing.JMenuItem();
        separatorMenu1 = new javax.swing.JPopupMenu.Separator();
        menuView = new javax.swing.JMenuItem();
        menuOpenAttachment = new javax.swing.JMenuItem();
        separatorMenu2 = new javax.swing.JPopupMenu.Separator();
        menuEdit = new javax.swing.JMenuItem();
        menuRemove = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        btnNew = new javax.swing.JButton();
        btnAddFromApplication = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        separator1 = new javax.swing.JToolBar.Separator();
        btnView = new javax.swing.JButton();
        btnOpenAttachment = new javax.swing.JButton();
        separator2 = new javax.swing.JToolBar.Separator();
        btnEdit = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        documentsPanel = createDocumentsPanel();

        documentsTablePopupMenu.setName("documentsTablePopupMenu"); // NOI18N

        menuNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/ui/source/Bundle"); // NOI18N
        menuNew.setText(bundle.getString("DocumentsManagementPanel.menuNew.text")); // NOI18N
        menuNew.setName("menuNew"); // NOI18N
        menuNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNewActionPerformed(evt);
            }
        });
        documentsTablePopupMenu.add(menuNew);

        menuAddFromApplication.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/new.png"))); // NOI18N
        menuAddFromApplication.setText(bundle.getString("DocumentsManagementPanel.menuAddFromApplication.text")); // NOI18N
        menuAddFromApplication.setName(bundle.getString("DocumentsManagementPanel.menuAddFromApplication.name")); // NOI18N
        menuAddFromApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddFromApplicationActionPerformed(evt);
            }
        });
        documentsTablePopupMenu.add(menuAddFromApplication);

        menuSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/search.png"))); // NOI18N
        menuSearch.setText(bundle.getString("DocumentsManagementPanel.menuSearch.text")); // NOI18N
        menuSearch.setName(bundle.getString("DocumentsManagementPanel.menuSearch.name")); // NOI18N
        menuSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSearchActionPerformed(evt);
            }
        });
        documentsTablePopupMenu.add(menuSearch);

        separatorMenu1.setName(bundle.getString("DocumentsManagementPanel.separatorMenu1.name")); // NOI18N
        documentsTablePopupMenu.add(separatorMenu1);

        menuView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/view.png"))); // NOI18N
        menuView.setText(bundle.getString("DocumentsManagementPanel.menuView.text")); // NOI18N
        menuView.setName("menuView"); // NOI18N
        menuView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewActionPerformed(evt);
            }
        });
        documentsTablePopupMenu.add(menuView);

        menuOpenAttachment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/folder-open-document.png"))); // NOI18N
        menuOpenAttachment.setText(bundle.getString("DocumentsManagementPanel.menuOpenAttachment.text")); // NOI18N
        menuOpenAttachment.setName(bundle.getString("DocumentsManagementPanel.menuOpenAttachment.name")); // NOI18N
        menuOpenAttachment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOpenAttachmentActionPerformed(evt);
            }
        });
        documentsTablePopupMenu.add(menuOpenAttachment);

        separatorMenu2.setName(bundle.getString("DocumentsManagementPanel.separatorMenu2.name")); // NOI18N
        documentsTablePopupMenu.add(separatorMenu2);

        menuEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        menuEdit.setText(bundle.getString("DocumentsManagementPanel.menuEdit.text")); // NOI18N
        menuEdit.setName(bundle.getString("DocumentsManagementPanel.menuEdit.name")); // NOI18N
        menuEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditActionPerformed(evt);
            }
        });
        documentsTablePopupMenu.add(menuEdit);

        menuRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        menuRemove.setText(bundle.getString("DocumentsManagementPanel.menuRemove.text")); // NOI18N
        menuRemove.setName("menuRemove"); // NOI18N
        menuRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRemoveActionPerformed(evt);
            }
        });
        documentsTablePopupMenu.add(menuRemove);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        btnNew.setText(bundle.getString("DocumentsManagementPanel.btnNew.text")); // NOI18N
        btnNew.setFocusable(false);
        btnNew.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNew.setName("btnNew"); // NOI18N
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNew);

        btnAddFromApplication.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/new.png"))); // NOI18N
        btnAddFromApplication.setText(bundle.getString("DocumentsManagementPanel.btnAddFromApplication.text")); // NOI18N
        btnAddFromApplication.setFocusable(false);
        btnAddFromApplication.setName(bundle.getString("DocumentsManagementPanel.btnAddFromApplication.name")); // NOI18N
        btnAddFromApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFromApplicationActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAddFromApplication);

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/search.png"))); // NOI18N
        btnSearch.setText(bundle.getString("DocumentsManagementPanel.btnSearch.text")); // NOI18N
        btnSearch.setFocusable(false);
        btnSearch.setName(bundle.getString("DocumentsManagementPanel.btnSearch.name")); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSearch);

        separator1.setName(bundle.getString("DocumentsManagementPanel.separator1.name")); // NOI18N
        jToolBar1.add(separator1);

        btnView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/view.png"))); // NOI18N
        btnView.setText(bundle.getString("DocumentsManagementPanel.btnView.text")); // NOI18N
        btnView.setFocusable(false);
        btnView.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnView.setName("btnView"); // NOI18N
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });
        jToolBar1.add(btnView);

        btnOpenAttachment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/folder-open-document.png"))); // NOI18N
        btnOpenAttachment.setText(bundle.getString("DocumentsManagementPanel.btnOpenAttachment.text")); // NOI18N
        btnOpenAttachment.setFocusable(false);
        btnOpenAttachment.setName(bundle.getString("DocumentsManagementPanel.btnOpenAttachment.name")); // NOI18N
        btnOpenAttachment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenAttachmentActionPerformed(evt);
            }
        });
        jToolBar1.add(btnOpenAttachment);

        separator2.setName(bundle.getString("DocumentsManagementPanel.separator2.name")); // NOI18N
        jToolBar1.add(separator2);

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        btnEdit.setText(bundle.getString("DocumentsManagementPanel.btnEdit.text")); // NOI18N
        btnEdit.setFocusable(false);
        btnEdit.setName(bundle.getString("DocumentsManagementPanel.btnEdit.name")); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEdit);

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemove.setText(bundle.getString("DocumentsManagementPanel.btnRemove.text")); // NOI18N
        btnRemove.setFocusable(false);
        btnRemove.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRemove.setName("btnRemove"); // NOI18N
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRemove);

        documentsPanel.setName("documentsPanel"); // NOI18N
        documentsPanel.setPopupMenu(documentsTablePopupMenu);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jToolBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
            .add(documentsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(documentsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        newSource();
    }//GEN-LAST:event_btnNewActionPerformed

    private void menuNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNewActionPerformed
        newSource();
    }//GEN-LAST:event_menuNewActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        remove();
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void menuRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRemoveActionPerformed
        remove();
    }//GEN-LAST:event_menuRemoveActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        view();
    }//GEN-LAST:event_btnViewActionPerformed

    private void menuViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuViewActionPerformed
        view();
    }//GEN-LAST:event_menuViewActionPerformed

    private void btnAddFromApplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFromApplicationActionPerformed
        addFromApplication();
    }//GEN-LAST:event_btnAddFromApplicationActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        search();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnOpenAttachmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenAttachmentActionPerformed
        openAttachment();
    }//GEN-LAST:event_btnOpenAttachmentActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        edit();
    }//GEN-LAST:event_btnEditActionPerformed

    private void menuAddFromApplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAddFromApplicationActionPerformed
        addFromApplication();
    }//GEN-LAST:event_menuAddFromApplicationActionPerformed

    private void menuSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSearchActionPerformed
        search();
    }//GEN-LAST:event_menuSearchActionPerformed

    private void menuOpenAttachmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOpenAttachmentActionPerformed
        openAttachment();
    }//GEN-LAST:event_menuOpenAttachmentActionPerformed

    private void menuEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditActionPerformed
        edit();
    }//GEN-LAST:event_menuEditActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddFromApplication;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnOpenAttachment;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnView;
    private org.sola.clients.swing.ui.source.DocumentsPanel documentsPanel;
    private javax.swing.JPopupMenu documentsTablePopupMenu;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem menuAddFromApplication;
    private javax.swing.JMenuItem menuEdit;
    private javax.swing.JMenuItem menuNew;
    private javax.swing.JMenuItem menuOpenAttachment;
    private javax.swing.JMenuItem menuRemove;
    private javax.swing.JMenuItem menuSearch;
    private javax.swing.JMenuItem menuView;
    private javax.swing.JToolBar.Separator separator1;
    private javax.swing.JToolBar.Separator separator2;
    private javax.swing.JPopupMenu.Separator separatorMenu1;
    private javax.swing.JPopupMenu.Separator separatorMenu2;
    // End of variables declaration//GEN-END:variables

}
