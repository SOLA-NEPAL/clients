package org.sola.clients.swing.gis.ui.control;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.sola.clients.beans.cadastre.DatasetListBean;
import org.sola.clients.beans.referencedata.VdcListBean;
import org.sola.clients.swing.common.controls.autocomplete.Configurator;

public class DatasetSelectionForm extends javax.swing.JDialog {

    public static final String SELECTED_DATASET_PROPERTY = "selectedDataset";

    /**
     * Default form constructor
     */
    public DatasetSelectionForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        postInit();
    }

    private void postInit() {
        vdcs.loadListByOffice(false);
        cbxVdcs.setSelectedIndex(-1);
        Configurator.enableAutoCompletion(cbxVdcs);

        vdcs.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(VdcListBean.SELECTED_VDC_PROPERTY)) {
                    loadDatasets();
                }
            }
        });

        datasets.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(DatasetListBean.SELECTED_DATASET_PROPERTY)) {
                    customizeSelectionButton();
                }
            }
        });

        customizeSelectionButton();
    }

    private void loadDatasets() {
        if (vdcs.getSelectedVdc() != null) {
            datasets.loadDatasetsByVdc(vdcs.getSelectedVdc().getCode());
        }
    }

    private void customizeSelectionButton() {
        boolean enabled = datasets.getSelectedDataset() != null;
        btnSelect.setEnabled(enabled);
        menuSelect.setEnabled(enabled);
    }

    private void select() {
        if (datasets.getSelectedDataset() != null) {
            firePropertyChange(SELECTED_DATASET_PROPERTY, null, datasets.getSelectedDataset());
            this.setVisible(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        datasets = new org.sola.clients.beans.cadastre.DatasetListBean();
        popupDatasets = new javax.swing.JPopupMenu();
        menuSelect = new javax.swing.JMenuItem();
        vdcs = new org.sola.clients.beans.referencedata.VdcListBean();
        jToolBar1 = new javax.swing.JToolBar();
        btnSelect = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDatasets = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbxVdcs = new javax.swing.JComboBox();

        menuSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sola/clients/swing/gis/mapaction/resources/select.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/gis/ui/control/Bundle"); // NOI18N
        menuSelect.setText(bundle.getString("DatasetSelectionForm.menuSelect.text")); // NOI18N
        menuSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSelectActionPerformed(evt);
            }
        });
        popupDatasets.add(menuSelect);

        setTitle(bundle.getString("DatasetSelectionForm.title")); // NOI18N
        setResizable(false);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sola/clients/swing/gis/mapaction/resources/select.png"))); // NOI18N
        btnSelect.setText(bundle.getString("DatasetSelectionForm.btnSelect.text")); // NOI18N
        btnSelect.setFocusable(false);
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSelect);

        tableDatasets.setComponentPopupMenu(popupDatasets);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${datasets}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, datasets, eLProperty, tableDatasets);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${name}"));
        columnBinding.setColumnName("Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${srid}"));
        columnBinding.setColumnName("Srid");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, datasets, org.jdesktop.beansbinding.ELProperty.create("${selectedDataset}"), tableDatasets, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(tableDatasets);
        tableDatasets.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("DatasetSelectionForm.tableDatasets.columnModel.title0_1")); // NOI18N
        tableDatasets.getColumnModel().getColumn(1).setMaxWidth(100);
        tableDatasets.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("DatasetSelectionForm.tableDatasets.columnModel.title2_1")); // NOI18N

        jLabel1.setText(bundle.getString("DatasetSelectionForm.jLabel1.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${vdcs}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vdcs, eLProperty, cbxVdcs);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vdcs, org.jdesktop.beansbinding.ELProperty.create("${selectedVdc}"), cbxVdcs, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(cbxVdcs, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxVdcs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        select();
    }//GEN-LAST:event_btnSelectActionPerformed

    private void menuSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSelectActionPerformed
        select();
    }//GEN-LAST:event_menuSelectActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSelect;
    private javax.swing.JComboBox cbxVdcs;
    private org.sola.clients.beans.cadastre.DatasetListBean datasets;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem menuSelect;
    private javax.swing.JPopupMenu popupDatasets;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableDatasets;
    private org.sola.clients.beans.referencedata.VdcListBean vdcs;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
