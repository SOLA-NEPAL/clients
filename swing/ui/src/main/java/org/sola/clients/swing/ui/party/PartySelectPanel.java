package org.sola.clients.swing.ui.party;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.sola.clients.beans.party.PartyBean;
import org.sola.clients.beans.party.PartySummaryBean;
import org.sola.clients.beans.security.SecurityBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.common.RolesConstants;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;
import org.sola.webservices.transferobjects.EntityAction;

/**
 * Shows provided {@link PartySummaryBean} data and allows to trigger search, edit, view and remove events.
 */
public class PartySelectPanel extends javax.swing.JPanel {

    public static final String SEARCH_PARTY = "searchParty";
    public static final String VIEW_PARTY = "viewParty";
    public static final String EDIT_PARTY = "editParty";
    public static final String REMOVE_PARTY = "removeParty";
    public static final String ADD_PARTY = "addParty";
    private PartySummaryBean partySummary;
    private PartySummaryBean displayPartySummary;
    private PartySummaryListener partySummaryListener;
    private boolean readOnly = false;

    /** Listens to partySummary events to update bound displayPartySummary. */
    private class PartySummaryListener implements PropertyChangeListener {
        public PartySummaryListener(){
        }
        
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if(evt.getPropertyName().equals(PartySummaryBean.ENTITY_ACTION_PROPERTY)){
                setupDisplayParty();
            }
        }
        
    }
    
    /**
     * Default panel constructor
     */
    public PartySelectPanel() {
        this(null);
    }

    /**
     * Panel constructor with initial {@link PartySummaryBean} parameter.
     */
    public PartySelectPanel(PartySummaryBean partySummary) {
        if (partySummary == null) {
            this.partySummary = new PartySummaryBean();
            this.partySummary.setEntityAction(EntityAction.DISASSOCIATE);
        } else {
            this.partySummary = partySummary;
        }
        partySummaryListener = new PartySummaryListener();
        this.partySummary.addPropertyChangeListener(partySummaryListener);
        displayPartySummary = this.partySummary;
        initComponents();
        customizeButtons();
    }

    private void customizeButtons(){
        boolean enabled = partySummary!=null && partySummary.getEntityAction() != EntityAction.DISASSOCIATE;
        boolean hasPartySaveRole = SecurityBean.isInRole(RolesConstants.PARTY_SAVE);
        btnEdit.setEnabled(enabled && !readOnly && hasPartySaveRole);
        btnView.setEnabled(enabled);
        btnRemove.setEnabled(enabled && !readOnly);
        btnSearch.setEnabled(!readOnly);
        btnAdd.setEnabled(!readOnly && hasPartySaveRole);
    }

    public PartySummaryBean getDisplayPartySummary() {
        return displayPartySummary;
    }
    
    public PartySummaryBean getPartySummary() {
        return partySummary;
    }

    public void setPartySummary(PartySummaryBean partySummary) {
        if (partySummary == null) {
            this.partySummary = new PartySummaryBean();
            this.partySummary.setEntityAction(EntityAction.DISASSOCIATE);
            this.partySummary.addPropertyChangeListener(partySummaryListener);
        } else {
            this.partySummary.removePropertyChangeListener(partySummaryListener);
            this.partySummary = partySummary;
            this.partySummary.addPropertyChangeListener(partySummaryListener);
        }
        setupDisplayParty();
        firePropertyChange("partySummary", null, this.partySummary);
    }

    private void setupDisplayParty(){
        if(partySummary == null || partySummary.getEntityAction() == EntityAction.DISASSOCIATE){
            displayPartySummary = new PartySummaryBean();
        } else {
            displayPartySummary = partySummary;
        }
        firePropertyChange("displayPartySummary", null, this.displayPartySummary);
        customizeButtons();
    }
    
    public boolean isShowSearchButton() {
        return btnSearch.isVisible();
    }

    public void setShowSearchButton(boolean show) {
        btnSearch.setVisible(show);
    }

    public boolean isShowViewButton() {
        return btnView.isVisible();
    }

    public void setShowViewButton(boolean show) {
        btnView.setVisible(show);
    }

    public boolean isShowEditButton() {
        return btnEdit.isVisible();
    }

    public void setShowEditButton(boolean show) {
        btnEdit.setVisible(show);
    }

    public boolean isShowRemoveButton() {
        return btnRemove.isVisible();
    }

    public void setShowRemoveButton(boolean show) {
        btnRemove.setVisible(show);
    }

    public boolean isShowAddButton() {
        return btnAdd.isVisible();
    }

    public void setShowAddButton(boolean show) {
        btnAdd.setVisible(show);
    }
    
    public boolean isReadOnly(){
        return readOnly;
    }
    
    public void setReadOnly(boolean readOnly){
        this.readOnly = readOnly;
        customizeButtons();
    }
    
    private void fireGetParty(final String evtName) {
        SolaTask<PartyBean, Void> task = new SolaTask<PartyBean, Void>() {

            @Override
            protected PartyBean doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_PERSON_GETTING));
                return PartyBean.getParty(getPartySummary().getId());
            }

            @Override
            protected void taskDone() {
                if(get()==null){
                    MessageUtility.displayMessage(ClientMessage.PARTY_NOT_FOUND);
                } else {
                    firePropertyChange(evtName, null, get());
                }
            }
        };
        TaskManager.getInstance().runTask(task);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jToolBar1 = new javax.swing.JToolBar();
        btnSearch = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtFullName = new javax.swing.JTextField();

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/search.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/ui/party/Bundle"); // NOI18N
        btnSearch.setText(bundle.getString("PartySelectPanel.btnSearch.text")); // NOI18N
        btnSearch.setFocusable(false);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSearch);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        btnAdd.setText(bundle.getString("PartySelectPanel.btnAdd.text")); // NOI18N
        btnAdd.setFocusable(false);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAdd);

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        btnEdit.setText(bundle.getString("PartySelectPanel.btnEdit.text")); // NOI18N
        btnEdit.setFocusable(false);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEdit);

        btnView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/view.png"))); // NOI18N
        btnView.setText(bundle.getString("PartySelectPanel.btnView.text")); // NOI18N
        btnView.setFocusable(false);
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });
        jToolBar1.add(btnView);

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemove.setText(bundle.getString("PartySelectPanel.btnRemove.text")); // NOI18N
        btnRemove.setFocusable(false);
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRemove);

        jLabel1.setText(bundle.getString("PartySelectPanel.jLabel1.text")); // NOI18N

        txtFullName.setBackground(new java.awt.Color(255, 255, 255));
        txtFullName.setEditable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${displayPartySummary.fullName}"), txtFullName, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtFullName)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        firePropertyChange(SEARCH_PARTY, false, true);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        firePropertyChange(REMOVE_PARTY, null, getPartySummary());
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        fireGetParty(EDIT_PARTY);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        fireGetParty(VIEW_PARTY);
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        firePropertyChange(ADD_PARTY, false, true);
    }//GEN-LAST:event_btnAddActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnView;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txtFullName;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
