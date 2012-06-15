package org.sola.clients.swing.ui.security;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.sola.clients.beans.referencedata.DepartmentListBean;
import org.sola.clients.beans.referencedata.OfficeListBean;

/**
 * Allows to select department through the office selection
 */
public class DepartmentSelectionForm extends javax.swing.JDialog {

    public static final String DEPARTMENT_SELECTED_PROPERTY = "departmentSelected";

    /**
     * Form constructor
     */
    public DepartmentSelectionForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        customizeSelectButton();
        offices.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(OfficeListBean.SELECTED_OFFICE_PROPERTY)) {
                    loadDepartments();
                }
            }
        });
        departments.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(DepartmentListBean.SELECTED_DEPARTMENT_PROPERTY)) {
                    customizeSelectButton();
                }
            }
        });
        departments.getDepartments().clear();
    }

    private void loadDepartments() {
        if (offices.getSelectedOffice() != null) {
            departments.loadList(false, offices.getSelectedOffice().getCode());
        } else {
            departments.getDepartments().clear();
        }
    }

    private void fireSelectDepartment() {
        if (departments.getSelectedDepartment() != null) {
            firePropertyChange(DEPARTMENT_SELECTED_PROPERTY, null, departments.getSelectedDepartment());
        }
    }

    private void customizeSelectButton(){
        btnSelect.setEnabled(departments.getSelectedDepartment()!=null);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        departments = new org.sola.clients.beans.referencedata.DepartmentListBean();
        offices = new org.sola.clients.beans.referencedata.OfficeListBean();
        jToolBar1 = new javax.swing.JToolBar();
        btnSelect = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstOffices = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstDepartments = new javax.swing.JList();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/ui/security/Bundle"); // NOI18N
        setTitle(bundle.getString("DepartmentSelectionForm.title")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/confirm.png"))); // NOI18N
        btnSelect.setText(bundle.getString("DepartmentSelectionForm.btnSelect.text")); // NOI18N
        btnSelect.setFocusable(false);
        btnSelect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSelect);

        jPanel3.setLayout(new java.awt.GridLayout(1, 2, 15, 0));

        jLabel1.setText(bundle.getString("DepartmentSelectionForm.jLabel1.text")); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${officesFiltered}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, offices, eLProperty, lstOffices);
        jListBinding.setDetailBinding(org.jdesktop.beansbinding.ELProperty.create("${displayValue}"));
        bindingGroup.addBinding(jListBinding);
        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, offices, org.jdesktop.beansbinding.ELProperty.create("${selectedOffice}"), lstOffices, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(lstOffices);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel2);

        jLabel2.setText(bundle.getString("DepartmentSelectionForm.jLabel2.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${departmentsFiltered}");
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, departments, eLProperty, lstDepartments);
        jListBinding.setDetailBinding(org.jdesktop.beansbinding.ELProperty.create("${displayValue}"));
        bindingGroup.addBinding(jListBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, departments, org.jdesktop.beansbinding.ELProperty.create("${selectedDepartment}"), lstDepartments, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        lstDepartments.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstDepartmentsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lstDepartments);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        fireSelectDepartment();
    }//GEN-LAST:event_btnSelectActionPerformed

    private void lstDepartmentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstDepartmentsMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() > 1) {
            fireSelectDepartment();
        }
    }//GEN-LAST:event_lstDepartmentsMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSelect;
    private org.sola.clients.beans.referencedata.DepartmentListBean departments;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JList lstDepartments;
    private javax.swing.JList lstOffices;
    private org.sola.clients.beans.referencedata.OfficeListBean offices;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
