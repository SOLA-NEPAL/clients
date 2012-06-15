package org.sola.clients.swing.ui.referencedata;

import org.sola.clients.beans.referencedata.OfficeBean;
import org.sola.webservices.transferobjects.referencedata.OfficeTO;

/**
 * Shows office data
 */
public class OfficePanel extends javax.swing.JPanel {

    private OfficeBean officeBean;

    /**
     * Default constructor
     */
    public OfficePanel() {
        initComponents();
    }

    /**
     * Panel constructor with {@link OfficeBean} parameter
     * @param officeBean The instance of OfficeBean to show on the panel
     */
    public OfficePanel(OfficeBean officeBean) {
        initComponents();
        setupOfficeBean(officeBean);
    }
    
    public OfficeBean getOfficeBean() {
        return officeBean;
    }

    public void setOfficeBean(OfficeBean officeBean) {
        this.officeBean = officeBean;
        setupOfficeBean(officeBean);
    }

    /**
     * Setup reference data bean object, used to bind data on the form.
     */
    private void setupOfficeBean(OfficeBean officeBean) {
        txtCode.setEnabled(officeBean == null);

        if (officeBean != null) {
            this.officeBean = officeBean;
        } else {
            this.officeBean = new OfficeBean();
        }

        descriptionValues.loadLocalizedValues(this.officeBean.getDescription());
        displayValues.loadLocalizedValues(this.officeBean.getDisplayValue());
        firePropertyChange("officeBean", null, this.officeBean);
    }

    /** Validates office object. */
    public boolean validateOffice(boolean showMessage){
        return officeBean.validate(showMessage).size()<1;
    }

    /** Calls saving procedure of reference data object. */
    public boolean save(boolean showMessage){
        officeBean.setDisplayValue(displayValues.buildMultilingualString());
        officeBean.setDescription(descriptionValues.buildMultilingualString());
        if(validateOffice(showMessage)){
            OfficeBean.saveRefData(officeBean, OfficeTO.class);
            return true;
        }else {
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        districts = new org.sola.clients.beans.referencedata.DistrictListBean();
        displayValues = new org.sola.clients.beans.system.LocalizedValuesListBean();
        descriptionValues = new org.sola.clients.beans.system.LocalizedValuesListBean();
        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbxDistrict = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableOfficeNames = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDdescriptions = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();

        jPanel6.setLayout(new java.awt.GridLayout(1, 3, 15, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/ui/referencedata/Bundle"); // NOI18N
        jLabel1.setText(bundle.getString("OfficePanel.jLabel1.text")); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${officeBean.code}"), txtCode, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 123, Short.MAX_VALUE))
            .addComponent(txtCode)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel1);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel2.setText(bundle.getString("OfficePanel.jLabel2.text")); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${districts}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, districts, eLProperty, cbxDistrict);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${officeBean.district}"), cbxDistrict, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 115, Short.MAX_VALUE))
            .addComponent(cbxDistrict, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel2);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel3.setText(bundle.getString("OfficePanel.jLabel3.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${officeBean.status}"), txtStatus, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 117, Short.MAX_VALUE))
            .addComponent(txtStatus)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel3);

        jPanel7.setLayout(new java.awt.GridLayout(2, 0, 0, 15));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/red_asterisk.gif"))); // NOI18N
        jLabel4.setText(bundle.getString("OfficePanel.jLabel4.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${localizedValues}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, displayValues, eLProperty, tableOfficeNames);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${language.displayValue}"));
        columnBinding.setColumnName("Language.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${localizedValue}"));
        columnBinding.setColumnName("Localized Value");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(tableOfficeNames);
        tableOfficeNames.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("OfficePanel.tableOfficeNames.columnModel.title0_1")); // NOI18N
        tableOfficeNames.getColumnModel().getColumn(1).setResizable(false);
        tableOfficeNames.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("OfficePanel.tableOfficeNames.columnModel.title1_1")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel4);

        jLabel5.setText(bundle.getString("OfficePanel.jLabel5.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${localizedValues}");
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, descriptionValues, eLProperty, tableDdescriptions);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${language.displayValue}"));
        columnBinding.setColumnName("Language.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${localizedValue}"));
        columnBinding.setColumnName("Localized Value");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane2.setViewportView(tableDdescriptions);
        tableDdescriptions.getColumnModel().getColumn(0).setMinWidth(150);
        tableDdescriptions.getColumnModel().getColumn(0).setMaxWidth(200);
        tableDdescriptions.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("OfficePanel.tableDdescriptions.columnModel.title0_1")); // NOI18N
        tableDdescriptions.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("OfficePanel.tableDdescriptions.columnModel.title1_1")); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbxDistrict;
    private org.sola.clients.beans.system.LocalizedValuesListBean descriptionValues;
    private org.sola.clients.beans.system.LocalizedValuesListBean displayValues;
    private org.sola.clients.beans.referencedata.DistrictListBean districts;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableDdescriptions;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableOfficeNames;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtStatus;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
