package org.sola.clients.swing.ui.administrative;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.naming.directory.SearchResult;
import org.sola.clients.beans.administrative.LocDetailsBean;
import org.sola.clients.beans.administrative.LocSearchParamsBean;
import org.sola.clients.beans.administrative.LocSearchResultListBean;
import org.sola.clients.reports.ReportManager;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.ui.renderers.TableCellTextAreaRenderer;
import org.sola.clients.swing.ui.reports.ReportViewerForm;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Shows LOC search results
 */
public class LocSearchResultsPanel extends javax.swing.JPanel {

    /**
     * Default constructor
     */
    public LocSearchResultsPanel() {
        initComponents();
        postInit();
    }

    private void postInit() {
        locSearchResults.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(LocSearchResultListBean.SELECTED_RESULT_PROPERTY)) {
                    customizePrintButton();
                }
            }
        });
        customizePrintButton();
    }

    private void customizePrintButton() {
        boolean enabled = locSearchResults.getSeletedResult() != null;
        btnPrint.setEnabled(enabled);
        menuPrint.setEnabled(enabled);
    }

    public void searchLocs(final LocSearchParamsBean searchParams) {
        SolaTask<Void, Void> task = new SolaTask<Void, Void>() {

            @Override
            protected Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_LOC_SEARCHING));
                locSearchResults.search(searchParams);
                return null;
            }

            @Override
            protected void taskDone() {
                if (locSearchResults.getSearchResults().size() < 1) {
                    MessageUtility.displayMessage(ClientMessage.SEARCH_NO_RESULTS);
                } else if (locSearchResults.getSearchResults().size() > 99) {
                    MessageUtility.displayMessage(ClientMessage.SEARCH_TOO_MANY_RESULTS, new String[]{"100"});
                }
            }
        };
        TaskManager.getInstance().runTask(task);
    }

    private void print() {
        if (locSearchResults.getSeletedResult() != null) {
            SolaTask<Void, Void> task = new SolaTask<Void, Void>() {

                @Override
                protected Void doTask() {
                    setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_GETTING_LOC));
                    ReportViewerForm.showLocReport(LocDetailsBean.loadLocDetails(locSearchResults.getSeletedResult().getId(), "np"));
                    return null;
                }
            };
            TaskManager.getInstance().runTask(task);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        popupLocs = new javax.swing.JPopupMenu();
        menuPrint = new javax.swing.JMenuItem();
        locSearchResults = new org.sola.clients.beans.administrative.LocSearchResultListBean();
        jToolBar1 = new javax.swing.JToolBar();
        btnPrint = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableWithDefaultStyles1 = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();

        menuPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/print.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/ui/administrative/Bundle"); // NOI18N
        menuPrint.setText(bundle.getString("LocSearchResultsPanel.menuPrint.text")); // NOI18N
        menuPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPrintActionPerformed(evt);
            }
        });
        popupLocs.add(menuPrint);

        setName(bundle.getString("LocSearchResultsPanel.name")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName(bundle.getString("LocSearchResultsPanel.jToolBar1.name")); // NOI18N

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/print.png"))); // NOI18N
        btnPrint.setText(bundle.getString("LocSearchResultsPanel.btnPrint.text")); // NOI18N
        btnPrint.setFocusable(false);
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPrint);

        jScrollPane1.setName(bundle.getString("LocSearchResultsPanel.jScrollPane1.name")); // NOI18N

        jTableWithDefaultStyles1.setComponentPopupMenu(popupLocs);
        jTableWithDefaultStyles1.setName(bundle.getString("LocSearchResultsPanel.jTableWithDefaultStyles1.name")); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${searchResults}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, locSearchResults, eLProperty, jTableWithDefaultStyles1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mothNumber}"));
        columnBinding.setColumnName("Moth Number");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mothType.mothTypeName}"));
        columnBinding.setColumnName("Moth Type.moth Type Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${panaNumber}"));
        columnBinding.setColumnName("Pana Number");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${tmpPanaNumber}"));
        columnBinding.setColumnName("Tmp Pana Number");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${owners}"));
        columnBinding.setColumnName("Owners");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, locSearchResults, org.jdesktop.beansbinding.ELProperty.create("${seletedResult}"), jTableWithDefaultStyles1, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTableWithDefaultStyles1);
        jTableWithDefaultStyles1.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("LocSearchResultsPanel.jTableWithDefaultStyles1.columnModel.title0_1")); // NOI18N
        jTableWithDefaultStyles1.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("LocSearchResultsPanel.jTableWithDefaultStyles1.columnModel.title1_1")); // NOI18N
        jTableWithDefaultStyles1.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("LocSearchResultsPanel.jTableWithDefaultStyles1.columnModel.title2_1")); // NOI18N
        jTableWithDefaultStyles1.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("LocSearchResultsPanel.jTableWithDefaultStyles1.columnModel.title3_1")); // NOI18N
        jTableWithDefaultStyles1.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("LocSearchResultsPanel.jTableWithDefaultStyles1.columnModel.title4")); // NOI18N
        jTableWithDefaultStyles1.getColumnModel().getColumn(4).setCellRenderer(new TableCellTextAreaRenderer());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        print();
    }//GEN-LAST:event_btnPrintActionPerformed

    private void menuPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPrintActionPerformed
        print();
    }//GEN-LAST:event_menuPrintActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrint;
    private javax.swing.JScrollPane jScrollPane1;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles jTableWithDefaultStyles1;
    private javax.swing.JToolBar jToolBar1;
    private org.sola.clients.beans.administrative.LocSearchResultListBean locSearchResults;
    private javax.swing.JMenuItem menuPrint;
    private javax.swing.JPopupMenu popupLocs;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
