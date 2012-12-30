/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.sola.clients.beans.system.AreaBean;
import org.sola.clients.swing.common.converters.FormattersFactory;
import org.sola.common.AreaConversion;

public class AreaControl extends ContentPanel {

    private class UnitTypeListener implements PropertyChangeListener {

        public UnitTypeListener() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals(AreaBean.UNIT_TYPE)) {
                customizeTextFields();
            }
        }
    }
    
    private AreaBean area;
    private UnitTypeListener unitTypeListener = new UnitTypeListener();

    /**
     * Creates new form AreaControl
     */
    public AreaControl() {
        area = new AreaBean();
        initComponents();
        cmbLocalUnit.setSelectedIndex(-1);
        area.addPropertyChangeListener(unitTypeListener);
        customizeTextFields();
    }

    private void customizeTextFields() {
        boolean enabled = area.getUnitType() != null;
        txtAreaInLocalUnit.setEnabled(enabled);
        txtAreaInSqMeter.setEnabled(enabled);
    }

    public AreaBean getArea() {
        return area;
    }

    public void setArea(AreaBean area) {
        AreaBean oldValue = this.area;
        if(this.area!=null){
            this.area.removePropertyChangeListener(unitTypeListener);
        }
        
        if (area == null) {
            this.area = new AreaBean();
        } else {
            this.area = area;
        }
        this.area.addPropertyChangeListener(unitTypeListener);
        firePropertyChange("area", oldValue, this.area);
        customizeTextFields();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        areaUnitTypes = new org.sola.clients.beans.referencedata.AreaUnitTypeListBean();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbLocalUnit = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtAreaInLocalUnit = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtAreaInSqMeter = new javax.swing.JFormattedTextField();

        setLayout(new java.awt.GridLayout(1, 3, 15, 0));

        jLabel1.setText("Area units");

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${areaUnitTypes}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, areaUnitTypes, eLProperty, cmbLocalUnit);
        bindingGroup.addBinding(jComboBoxBinding);
        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${area.unitType}"), cmbLocalUnit, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 19, Short.MAX_VALUE))
            .addComponent(cmbLocalUnit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbLocalUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel1);

        jLabel2.setText("Area in units");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${area.areaInLocalUnit}"), txtAreaInLocalUnit, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtAreaInLocalUnit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAreaInLocalUnitFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 8, Short.MAX_VALUE))
            .addComponent(txtAreaInLocalUnit)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAreaInLocalUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        add(jPanel2);

        jLabel3.setText("Area in sq. m.");

        txtAreaInSqMeter.setFormatterFactory(FormattersFactory.getInstance().getDecimalFormatterFactory());

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${area.areaInSqMt}"), txtAreaInSqMeter, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 1, Short.MAX_VALUE))
            .addComponent(txtAreaInSqMeter)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAreaInSqMeter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        add(jPanel3);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAreaInLocalUnitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAreaInLocalUnitFocusLost
        String areaCode = null;
        if (area.getUnitType() != null) {
            areaCode = area.getUnitType().getCode();
        }
        if (!AreaConversion.checkArea(txtAreaInLocalUnit.getText(), areaCode)) {
            txtAreaInLocalUnit.setText(AreaConversion.getDefaultArea(areaCode));
        }
    }//GEN-LAST:event_txtAreaInLocalUnitFocusLost
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sola.clients.beans.referencedata.AreaUnitTypeListBean areaUnitTypes;
    private javax.swing.JComboBox cmbLocalUnit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtAreaInLocalUnit;
    private javax.swing.JFormattedTextField txtAreaInSqMeter;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
