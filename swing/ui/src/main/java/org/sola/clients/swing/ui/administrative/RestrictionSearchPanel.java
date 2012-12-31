package org.sola.clients.swing.ui.administrative;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.sola.clients.beans.administrative.RestrictionInfoListBean;
import org.sola.clients.beans.administrative.RestrictionSearchResultListBean;
import org.sola.clients.beans.security.SecurityBean;
import org.sola.clients.reports.ReportManager;
import org.sola.clients.swing.common.controls.autocomplete.Configurator;
import org.sola.clients.swing.common.converters.FormattersFactory;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.ui.renderers.NepaliDateCellRenderer;
import org.sola.clients.swing.ui.renderers.TableCellTextAreaRenderer;
import org.sola.clients.swing.ui.reports.ReportViewerForm;
import org.sola.common.RolesConstants;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Allows to search restrictions by different criteria
 */
public class RestrictionSearchPanel extends javax.swing.JPanel {

    /**
     * Default constructor
     */
    public RestrictionSearchPanel() {
        initComponents();
        postInit();
    }

    private void postInit() {
        vdcs.loadListByOffice(true);
        restrictionReasons.loadList(true);
        mapSheets.loadList(true);
        Configurator.enableAutoCompletion(cbxMapSheets); 
        cbxMapSheets.setSelectedIndex(-1);
        
        clearCombos();
        restrictionSearchResults.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(RestrictionSearchResultListBean.SELECTED_RESULT_PROPERTY)) {
                    customizePrintButtons();
                }
            }
        });
        customizePrintButtons();
    }

    private void customizePrintButtons() {
        btnPrintLetter.setEnabled(restrictionSearchResults.getSelectedResult() != null &&
                SecurityBean.isInRole(RolesConstants.ADMINISTRATIVE_RESTRICTION_PRINT));
        btnPrintList.setEnabled(restrictionSearchResults.getSearchResults().size() > 0);
        menuPrintLetter.setEnabled(btnPrintLetter.isEnabled());
        menuPrintList.setEnabled(btnPrintList.isEnabled());
    }

    private void search() {
        SolaTask<Void, Void> task = new SolaTask<Void, Void>() {
            @Override
            protected Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_RESTRICTIONS_SEARCHING));
                restrictionSearchResults.search(restrictionSearchParams);
                return null;
            }
            
            @Override
            protected void taskDone(){
                if (restrictionSearchResults.getSearchResults().size() > 100) {
                    MessageUtility.displayMessage(ClientMessage.SEARCH_TOO_MANY_RESULTS, new Object[]{"100"});
                } else if (restrictionSearchResults.getSearchResults().size() < 1) {
                    MessageUtility.displayMessage(ClientMessage.SEARCH_NO_RESULTS);
                }
                lblSearchResults.setText("(" + Integer.toString(restrictionSearchResults.getSearchResults().size()) + ")");
                customizePrintButtons();
            }
        };
        TaskManager.getInstance().runTask(task);
    }

    private void clear() {
        restrictionSearchResults.getSearchResults().clear();
        lblSearchResults.setText("(0)");
        restrictionSearchParams.clear();
        clearCombos();
        customizePrintButtons();
    }

    private void clearCombos() {
        if (mapSheets.getMapSheets().size() > 0) {
            cbxMapSheets.setSelectedIndex(0);
        }
        if (restrictionReasons.getRestrictionReasons().size() > 0) {
            cbxRestReasons.setSelectedIndex(0);
        }
        if (vdcs.getVdcs().size() > 0) {
            cbxVdcs.setSelectedIndex(0);
        }
    }
    
    private void printRestrictionLetter(){
        if (restrictionSearchResults.getSelectedResult() == null) {
            return;
        }
        RestrictionInfoListBean restriction = new RestrictionInfoListBean();
        restriction.search(restrictionSearchResults.getSelectedResult().getReferenceNr(), 
                restrictionSearchResults.getSelectedResult().getReferenceDate(), "np");
        ReportViewerForm.showReport(ReportManager.getRestrictionLetterReport(
                restriction.getRestrictionInfoList()));
    }
    
    private void printRestrictionsList(){
        if (restrictionSearchResults.getSearchResults().size()<1) {
            return;
        }
        ReportViewerForm.showReport(ReportManager.getRestrictionsListReport(
                restrictionSearchResults.getSearchResults()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        restrictionSearchParams = new org.sola.clients.beans.administrative.RestrictionSearchParamsBean();
        restrictionSearchResults = new org.sola.clients.beans.administrative.RestrictionSearchResultListBean();
        vdcs = new org.sola.clients.beans.referencedata.VdcListBean();
        restrictionReasons = new org.sola.clients.beans.referencedata.RestrictionReasonListBean();
        mapSheets = new org.sola.clients.beans.cadastre.MapSheetListBean();
        popupRestrictions = new javax.swing.JPopupMenu();
        menuPrintList = new javax.swing.JMenuItem();
        menuPrintLetter = new javax.swing.JMenuItem();
        jPanel20 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbxVdcs = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cbxMapSheets = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtWard = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtParcelNumber = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtBundleNumber = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtBundlePage = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cbxRestReasons = new javax.swing.JComboBox();
        jPanel10 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtRestrOffice = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtRefNumber = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtSerialNumber = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtRefDateFrom = new org.sola.clients.swing.common.controls.NepaliDateField();
        jPanel16 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtRefDateTo = new org.sola.clients.swing.common.controls.NepaliDateField();
        jPanel12 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtOwnerName = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtOwnerLastName = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtRegNumber = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtRegDateFrom = new org.sola.clients.swing.common.controls.NepaliDateField();
        jPanel18 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtRegDateTo = new org.sola.clients.swing.common.controls.NepaliDateField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtPriceFrom = new javax.swing.JFormattedTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtPriceTo = new javax.swing.JFormattedTextField();
        jPanel21 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        btnClear = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        btnPrintList = new javax.swing.JButton();
        btnPrintLetter = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel21 = new javax.swing.JLabel();
        lblSearchResults = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSearchResults = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();

        menuPrintList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/print.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/ui/administrative/Bundle"); // NOI18N
        menuPrintList.setText(bundle.getString("RestrictionSearchPanel.menuPrintList.text")); // NOI18N
        menuPrintList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPrintListActionPerformed(evt);
            }
        });
        popupRestrictions.add(menuPrintList);

        menuPrintLetter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/print.png"))); // NOI18N
        menuPrintLetter.setText(bundle.getString("RestrictionSearchPanel.menuPrintLetter.text")); // NOI18N
        menuPrintLetter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPrintLetterActionPerformed(evt);
            }
        });
        popupRestrictions.add(menuPrintLetter);

        jPanel20.setLayout(new java.awt.GridLayout(4, 5, 15, 5));

        jLabel1.setText(bundle.getString("RestrictionSearchPanel.jLabel1.text")); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${vdcs}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vdcs, eLProperty, cbxVdcs);
        bindingGroup.addBinding(jComboBoxBinding);
        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${vdc}"), cbxVdcs, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(cbxVdcs, 0, 136, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxVdcs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel1);

        jLabel4.setText(bundle.getString("RestrictionSearchPanel.jLabel4.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${mapSheets}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mapSheets, eLProperty, cbxMapSheets);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${mapSheet}"), cbxMapSheets, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(cbxMapSheets, 0, 136, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxMapSheets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel4);

        jLabel5.setText(bundle.getString("RestrictionSearchPanel.jLabel5.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${wardNo}"), txtWard, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtWard, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtWard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel5);

        jLabel6.setText(bundle.getString("RestrictionSearchPanel.jLabel6.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${parcelNo}"), txtParcelNumber, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtParcelNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtParcelNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel6);

        jLabel7.setText(bundle.getString("RestrictionSearchPanel.jLabel7.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${bundleNo}"), txtBundleNumber, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtBundleNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBundleNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel7);

        jLabel8.setText(bundle.getString("RestrictionSearchPanel.jLabel8.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${bundlePageNo}"), txtBundlePage, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 38, Short.MAX_VALUE))
            .addComponent(txtBundlePage)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBundlePage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel8);

        jLabel9.setText(bundle.getString("RestrictionSearchPanel.jLabel9.text")); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${restrictionReasons}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionReasons, eLProperty, cbxRestReasons);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${restrictionReason}"), cbxRestReasons, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(0, 49, Short.MAX_VALUE))
            .addComponent(cbxRestReasons, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxRestReasons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel9);

        jLabel10.setText(bundle.getString("RestrictionSearchPanel.jLabel10.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${restrtictionOfficeName}"), txtRestrOffice, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(0, 20, Short.MAX_VALUE))
            .addComponent(txtRestrOffice)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRestrOffice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel10);

        jLabel11.setText(bundle.getString("RestrictionSearchPanel.jLabel11.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${referenceNo}"), txtRefNumber, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtRefNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRefNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel11);

        jLabel14.setText(bundle.getString("RestrictionSearchPanel.jLabel14.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${serialNo}"), txtSerialNumber, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel14)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtSerialNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSerialNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel14);

        jLabel2.setText(bundle.getString("RestrictionSearchPanel.jLabel2.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${refDateFrom}"), txtRefDateFrom, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 57, Short.MAX_VALUE))
            .addComponent(txtRefDateFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRefDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel2);

        jLabel16.setText(bundle.getString("RestrictionSearchPanel.jLabel16.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${refDateTo}"), txtRefDateTo, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jLabel16)
                .addGap(0, 69, Short.MAX_VALUE))
            .addComponent(txtRefDateTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRefDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel16);

        jLabel12.setText(bundle.getString("RestrictionSearchPanel.jLabel12.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${ownerName}"), txtOwnerName, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtOwnerName, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOwnerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel12);

        jLabel13.setText(bundle.getString("RestrictionSearchPanel.jLabel13.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${ownerLastName}"), txtOwnerLastName, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addGap(0, 55, Short.MAX_VALUE))
            .addComponent(txtOwnerLastName)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOwnerLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel13);

        jLabel15.setText(bundle.getString("RestrictionSearchPanel.jLabel15.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${regNumber}"), txtRegNumber, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel15)
                .addGap(0, 39, Short.MAX_VALUE))
            .addComponent(txtRegNumber)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRegNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel15);

        jLabel17.setText(bundle.getString("RestrictionSearchPanel.jLabel17.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${regDateFrom}"), txtRegDateFrom, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17)
            .addComponent(txtRegDateFrom, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRegDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel17);

        jLabel18.setText(bundle.getString("RestrictionSearchPanel.jLabel18.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${regDateTo}"), txtRegDateTo, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18)
            .addComponent(txtRegDateTo, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRegDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel18);

        jLabel3.setText(bundle.getString("RestrictionSearchPanel.jLabel3.text")); // NOI18N

        txtPriceFrom.setFormatterFactory(FormattersFactory.getInstance().getDecimalFormatterFactory());
        txtPriceFrom.setText(bundle.getString("RestrictionSearchPanel.txtPriceFrom.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${priceFrom}"), txtPriceFrom, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 80, Short.MAX_VALUE))
            .addComponent(txtPriceFrom)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPriceFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel3);

        jLabel19.setText(bundle.getString("RestrictionSearchPanel.jLabel19.text")); // NOI18N

        txtPriceTo.setFormatterFactory(FormattersFactory.getInstance().getDecimalFormatterFactory());
        txtPriceTo.setText(bundle.getString("RestrictionSearchPanel.txtPriceTo.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchParams, org.jdesktop.beansbinding.ELProperty.create("${priceTo}"), txtPriceTo, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel19)
                .addGap(0, 92, Short.MAX_VALUE))
            .addComponent(txtPriceTo)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPriceTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel19);

        jLabel20.setText(bundle.getString("RestrictionSearchPanel.jLabel20.text")); // NOI18N

        btnClear.setText(bundle.getString("RestrictionSearchPanel.btnClear.text")); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnSearch.setText(bundle.getString("RestrictionSearchPanel.btnSearch.text")); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(btnClear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel21Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnClear, btnSearch});

        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear)
                    .addComponent(btnSearch))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel21);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnPrintList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/print.png"))); // NOI18N
        btnPrintList.setText(bundle.getString("RestrictionSearchPanel.btnPrintList.text")); // NOI18N
        btnPrintList.setFocusable(false);
        btnPrintList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintListActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPrintList);

        btnPrintLetter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/print.png"))); // NOI18N
        btnPrintLetter.setText(bundle.getString("RestrictionSearchPanel.btnPrintLetter.text")); // NOI18N
        btnPrintLetter.setFocusable(false);
        btnPrintLetter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintLetterActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPrintLetter);
        jToolBar1.add(jSeparator1);

        jLabel21.setText(bundle.getString("RestrictionSearchPanel.jLabel21.text")); // NOI18N
        jToolBar1.add(jLabel21);

        lblSearchResults.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSearchResults.setText(bundle.getString("RestrictionSearchPanel.lblSearchResults.text")); // NOI18N
        jToolBar1.add(lblSearchResults);

        tableSearchResults.setComponentPopupMenu(popupRestrictions);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${searchResults}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchResults, eLProperty, tableSearchResults);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${vdcName}"));
        columnBinding.setColumnName("Vdc Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mapNumber}"));
        columnBinding.setColumnName("Map Number");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${wardNo}"));
        columnBinding.setColumnName("Ward No");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${parcelNo}"));
        columnBinding.setColumnName("Parcel No");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${owners}"));
        columnBinding.setColumnName("Owners");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${bundleNumber}"));
        columnBinding.setColumnName("Bundle Number");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${bundlePageNo}"));
        columnBinding.setColumnName("Bundle Page No");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${registrationNumber}"));
        columnBinding.setColumnName("Registration Number");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${registrationDate}"));
        columnBinding.setColumnName("Registration Date");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${restrictionReason.displayValue}"));
        columnBinding.setColumnName("Restriction Reason.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${restrictionOfficeName}"));
        columnBinding.setColumnName("Restriction Office Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${sourceType.displayValue}"));
        columnBinding.setColumnName("Source Type.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${referenceNr}"));
        columnBinding.setColumnName("Reference Nr");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${referenceDate}"));
        columnBinding.setColumnName("Reference Date");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${price}"));
        columnBinding.setColumnName("Price");
        columnBinding.setColumnClass(java.math.BigDecimal.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${serialNumber}"));
        columnBinding.setColumnName("Serial Number");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${notationText}"));
        columnBinding.setColumnName("Notation Text");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, restrictionSearchResults, org.jdesktop.beansbinding.ELProperty.create("${selectedResult}"), tableSearchResults, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(tableSearchResults);
        tableSearchResults.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("RestrictionSearchPanel.tableSearchResults.columnModel.title0_1")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("RestrictionSearchPanel.tableSearchResults.columnModel.title1_1")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("RestrictionSearchPanel.tableSearchResults.columnModel.title2_1")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("RestrictionSearchPanel.tableSearchResults.columnModel.title3_1")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(4).setMinWidth(100);
        tableSearchResults.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("RestrictionSearchPanel.tableSearchResults.columnModel.title4")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(4).setCellRenderer(new org.sola.clients.swing.ui.renderers.CellDelimitedListRenderer("; "));
        tableSearchResults.getColumnModel().getColumn(5).setHeaderValue(bundle.getString("RestrictionSearchPanel.tableSearchResults.columnModel.title5")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(6).setHeaderValue(bundle.getString("RestrictionSearchPanel.tableSearchResults.columnModel.title6")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(7).setHeaderValue(bundle.getString("RestrictionSearchPanel.tableSearchResults.columnModel.title7")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(8).setHeaderValue(bundle.getString("RestrictionSearchPanel.tableSearchResults.columnModel.title8")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(8).setCellRenderer(new NepaliDateCellRenderer());
        tableSearchResults.getColumnModel().getColumn(9).setHeaderValue(bundle.getString("RestrictionSearchPanel.tableSearchResults.columnModel.title9")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(10).setHeaderValue(bundle.getString("RestrictionSearchPanel.tableSearchResults.columnModel.title10")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(11).setHeaderValue(bundle.getString("RestrictionSearchPanel.tableSearchResults.columnModel.title11")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(12).setHeaderValue(bundle.getString("RestrictionSearchPanel.tableSearchResults.columnModel.title12")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(13).setHeaderValue(bundle.getString("RestrictionSearchPanel.tableSearchResults.columnModel.title13")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(13).setCellRenderer(new NepaliDateCellRenderer());
        tableSearchResults.getColumnModel().getColumn(14).setHeaderValue(bundle.getString("RestrictionSearchPanel.tableSearchResults.columnModel.title14")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(15).setHeaderValue(bundle.getString("RestrictionSearchPanel.tableSearchResults.columnModel.title15")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(16).setHeaderValue(bundle.getString("RestrictionSearchPanel.tableSearchResults.columnModel.title16")); // NOI18N
        tableSearchResults.getColumnModel().getColumn(16).setCellRenderer(new TableCellTextAreaRenderer());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        search();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnPrintLetterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintLetterActionPerformed
        printRestrictionLetter();
    }//GEN-LAST:event_btnPrintLetterActionPerformed

    private void menuPrintLetterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPrintLetterActionPerformed
        printRestrictionLetter();
    }//GEN-LAST:event_menuPrintLetterActionPerformed

    private void btnPrintListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintListActionPerformed
        printRestrictionsList();
    }//GEN-LAST:event_btnPrintListActionPerformed

    private void menuPrintListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPrintListActionPerformed
        printRestrictionsList();
    }//GEN-LAST:event_menuPrintListActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnPrintLetter;
    private javax.swing.JButton btnPrintList;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox cbxMapSheets;
    private javax.swing.JComboBox cbxRestReasons;
    private javax.swing.JComboBox cbxVdcs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblSearchResults;
    private org.sola.clients.beans.cadastre.MapSheetListBean mapSheets;
    private javax.swing.JMenuItem menuPrintLetter;
    private javax.swing.JMenuItem menuPrintList;
    private javax.swing.JPopupMenu popupRestrictions;
    private org.sola.clients.beans.referencedata.RestrictionReasonListBean restrictionReasons;
    private org.sola.clients.beans.administrative.RestrictionSearchParamsBean restrictionSearchParams;
    private org.sola.clients.beans.administrative.RestrictionSearchResultListBean restrictionSearchResults;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableSearchResults;
    private javax.swing.JTextField txtBundleNumber;
    private javax.swing.JTextField txtBundlePage;
    private javax.swing.JTextField txtOwnerLastName;
    private javax.swing.JTextField txtOwnerName;
    private javax.swing.JTextField txtParcelNumber;
    private javax.swing.JFormattedTextField txtPriceFrom;
    private javax.swing.JFormattedTextField txtPriceTo;
    private org.sola.clients.swing.common.controls.NepaliDateField txtRefDateFrom;
    private org.sola.clients.swing.common.controls.NepaliDateField txtRefDateTo;
    private javax.swing.JTextField txtRefNumber;
    private org.sola.clients.swing.common.controls.NepaliDateField txtRegDateFrom;
    private org.sola.clients.swing.common.controls.NepaliDateField txtRegDateTo;
    private javax.swing.JTextField txtRegNumber;
    private javax.swing.JTextField txtRestrOffice;
    private javax.swing.JTextField txtSerialNumber;
    private javax.swing.JTextField txtWard;
    private org.sola.clients.beans.referencedata.VdcListBean vdcs;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
