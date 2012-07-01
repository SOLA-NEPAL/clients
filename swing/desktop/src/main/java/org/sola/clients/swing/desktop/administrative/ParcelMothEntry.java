/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.desktop.administrative;

import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.sola.clients.beans.administrative.BaUnitBean;
import org.sola.clients.beans.administrative.LocBean;
import org.sola.clients.beans.administrative.MothBean;
import org.sola.clients.beans.cadastre.CadastreObjectBean;
import org.sola.clients.beans.controls.SolaObservableList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.party.PartyBean;
import org.sola.clients.beans.party.PartySearchResultBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.desktop.MainForm;
import org.sola.clients.swing.desktop.application.ApplicationPanel;
import org.sola.clients.swing.desktop.cadastre.Select_Parcel_Form;
import org.sola.clients.swing.desktop.party.PersonSearchForm;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.MainContentPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;
import org.sola.webservices.transferobjects.cadastre.CadastreObjectTO;

/**
 *
 * @author KumarKhadka
 */
public class ParcelMothEntry extends ContentPanel {

    public static final String LOC_SAVED = "LocSaved";
    private SolaObservableList<CadastreObjectBean> cadastreObjects;
    private SolaObservableList<PartyBean> parties;
    private CadastreObjectBean selectedCadastreObjectBean;
    private PartyBean selectedPartyBean;

    /**
     * Creates new form ParcelMothEntry
     */
    public ParcelMothEntry() {
        initComponents();

    }

    public ParcelMothEntry(MothBean mothBean) {
        this.mothBean = mothBean;
        initComponents();
    }

    private MothBean createMothBean() {
        if (this.mothBean == null) {
            mothBean = new MothBean();
        }
        return mothBean;
    }

    public CadastreObjectBean getSelectedCadastreObjectBean() {
        return selectedCadastreObjectBean;
    }

    public void setSelectedCadastreObjectBean(CadastreObjectBean selectedCadastreObjectBean) {
        CadastreObjectBean oldValue = this.selectedCadastreObjectBean;
        this.selectedCadastreObjectBean = selectedCadastreObjectBean;
        firePropertyChange("selectedCadastreObjectBean", oldValue, this.selectedCadastreObjectBean);
    }

    public PartyBean getSelectedPartyBean() {
        return selectedPartyBean;
    }

    public void setSelectedPartyBean(PartyBean selectedPartyBean) {
        PartyBean oldValue = this.selectedPartyBean;
        this.selectedPartyBean = selectedPartyBean;
        firePropertyChange("selectedPartyBean", oldValue, this.selectedPartyBean);
    }

    public SolaObservableList<PartyBean> getParties() {
        if (parties == null) {
            parties = new SolaObservableList<>();
        }
        return parties;
    }

    public void setParties(SolaObservableList<PartyBean> parties) {
        if (parties == null) {
            parties = new SolaObservableList<>();
        }
        this.parties = parties;
        firePropertyChange("parties", null, this.parties);
    }

    public SolaObservableList<CadastreObjectBean> getCadastreObjects() {
        if (cadastreObjects == null) {
            cadastreObjects = new SolaObservableList<>();
        }
        return cadastreObjects;
    }

    public void setCadastreObjects(SolaObservableList<CadastreObjectBean> cadastreObjects) {
        if (cadastreObjects == null) {
            cadastreObjects = new SolaObservableList<>();

        }
        this.cadastreObjects = cadastreObjects;
        firePropertyChange("cadastreObjects", null, this.cadastreObjects);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        locListBean = new org.sola.clients.beans.administrative.LocListBean();
        cadastreObjectBean = new org.sola.clients.beans.cadastre.CadastreObjectBean();
        locBean = new org.sola.clients.beans.administrative.LocBean();
        mapSheetListBean = new org.sola.clients.beans.cadastre.MapSheetListBean();
        cadastreObjectListBean = new org.sola.clients.beans.cadastre.CadastreObjectListBean();
        buttonGroup1 = new javax.swing.ButtonGroup();
        mothBean = createMothBean();
        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtVdc = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMothLuj = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtMothLujNo = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtPageNo = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnSearchNewParcel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnRemove1 = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        btnAddNewOwner = new javax.swing.JButton();
        btnSave1 = new javax.swing.JButton();
        btnRemove2 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setHeaderPanel(headerPanel1);

        headerPanel1.setTitleText("Parcel Shresta Entry");

        jPanel1.setLayout(new java.awt.GridLayout(1, 4, 30, 0));

        jLabel1.setText("VDC/M.P");

        txtVdc.setEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothBean, org.jdesktop.beansbinding.ELProperty.create("${vdc.displayValue}"), txtVdc, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtVdc, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtVdc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2);

        jLabel2.setText("Moth/Luj");

        txtMothLuj.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothBean, org.jdesktop.beansbinding.ELProperty.create("${mothLuj}"), txtMothLuj, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtMothLuj, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtMothLuj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);

        jLabel3.setText("Moth/Luj No.");

        txtMothLujNo.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mothBean, org.jdesktop.beansbinding.ELProperty.create("${mothlujNumber}"), txtMothLujNo, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(txtMothLujNo, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtMothLujNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4);

        jLabel4.setText("Page No.");

        txtPageNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPageNoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txtPageNo, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtPageNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Parcel List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Aharoni", 0, 18), new java.awt.Color(0, 102, 51))); // NOI18N

        jTable1.setColumnSelectionAllowed(true);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cadastreObjects}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mapSheet.mapNumber}"));
        columnBinding.setColumnName("Map No");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${parcelno}"));
        columnBinding.setColumnName("");
        columnBinding.setColumnClass(Integer.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${selectedCadastreObjectBean}"), jTable1, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                .addContainerGap())
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnSearchNewParcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/search.png"))); // NOI18N
        btnSearchNewParcel.setText("Search Parcel");
        btnSearchNewParcel.setFocusable(false);
        btnSearchNewParcel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSearchNewParcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchNewParcelActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSearchNewParcel);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/save.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.setFocusable(false);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSave);

        btnRemove1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemove1.setText("Remove");
        btnRemove1.setFocusable(false);
        btnRemove1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemove1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemove1ActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRemove1);

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        btnAddNewOwner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/search.png"))); // NOI18N
        btnAddNewOwner.setText("Search Owner");
        btnAddNewOwner.setFocusable(false);
        btnAddNewOwner.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddNewOwner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewOwnerActionPerformed(evt);
            }
        });
        jToolBar2.add(btnAddNewOwner);

        btnSave1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/save.png"))); // NOI18N
        btnSave1.setText("Save");
        btnSave1.setFocusable(false);
        btnSave1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave1ActionPerformed(evt);
            }
        });
        jToolBar2.add(btnSave1);

        btnRemove2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemove2.setText("Remove");
        btnRemove2.setFocusable(false);
        btnRemove2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemove2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemove2ActionPerformed(evt);
            }
        });
        jToolBar2.add(btnRemove2);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Owner List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Aharoni", 0, 18), new java.awt.Color(0, 102, 51))); // NOI18N

        jTable2.setColumnSelectionAllowed(true);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${parties}");
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jTable2);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${name}"));
        columnBinding.setColumnName("First Name");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${lastName}"));
        columnBinding.setColumnName("Last Name");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${selectedPartyBean}"), jTable2, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(jTable2);
        jTable2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPageNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPageNoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
             locOperation();
        }       
    }//GEN-LAST:event_txtPageNoKeyPressed

    private void locOperation() {
        cadastreObjects.clear();
        parties.clear();
        if ("".equals(txtPageNo.getText())) {
            locBean = null;
            return;
        }
        locBean = locListBean.getLoc(mothBean, Integer.parseInt(txtPageNo.getText().toString()));
        //if pana no is not exist
        if (locBean == null) {
            if (newPanaCreation()) {
                locBean = new LocBean();
                locBean.setMothId(mothBean.getId());
                locBean.setPanaNo(Integer.parseInt(txtPageNo.getText()));
                if(locBean.saveLoc()){
                    JOptionPane.showMessageDialog(null, "Page Created");
                }
                refreshMoth(locBean);
            } else {
                return;
            }
        }
        //get the baUnits included in selected loc            
        baUnitOperation(locBean.getBaUnits());
    }

    private void refreshMoth(LocBean locBean) {
        mothBean.getLocList().add(locBean);
    }

    private void baUnitOperation(SolaObservableList<BaUnitBean> baUnits) {
        BaUnitBean baUnit;
        if (baUnits.size() > 0) {
            baUnit = baUnits.get(0);
            baUnitFinalOperation(baUnit);
        } else {
            //create baUnit
            baUnit = createBaUnit();
            locBean.getBaUnits().add(baUnit);
            baUnitFinalOperation(baUnit);
        }

    }

    private BaUnitBean createBaUnit() {
        BaUnitBean baUnit = new BaUnitBean();
        baUnit.setLocId(locBean.getId());
        baUnit.setTypeCode("administrativeUnit");
        baUnit.setName("TestBaunit");
        baUnit.setNameFirstpart("TestBaunit");
        baUnit.setNameLastpart("TestBaunit");
        baUnit.setStatusCode("current");
        baUnit.saveBaUnit(null);
        return baUnit;
    }

    private void baUnitFinalOperation(BaUnitBean baUnitBean) {
        SolaObservableList<CadastreObjectBean> cadObjLst = new SolaObservableList<>();
        SolaObservableList<PartyBean> partyList = new SolaObservableList<>();
        for (CadastreObjectBean cadBean : baUnitBean.getCadastreObjectList()) {
            cadObjLst.add(cadBean);
        }
        for (PartyBean partyBean : baUnitBean.getParties()) {
            partyList.add(partyBean);
        }
        cadastreObjectOperation(cadObjLst);
        partiesOperation(partyList);
    }

    private void cadastreObjectOperation(SolaObservableList<CadastreObjectBean> cadastreObjectBeans) {
        if (cadastreObjects.size() > 0) {
            for (CadastreObjectBean cad : cadastreObjectBeans) {
                if (!cadastreObjects.contains(cad)) {
                    cadastreObjects.add(cad);
                }
            }
            refreshCadastresInLoc();
            return;
        }
        setCadastreObjects(cadastreObjectBeans);
        refreshCadastresInLoc();
    }

    private void refreshCadastresInLoc() {
        for (CadastreObjectBean cad : cadastreObjects) {
            if (!locBean.getBaUnits().get(0).getCadastreObjectList().contains(cad)) {
                locBean.getBaUnits().get(0).getCadastreObjectList().add(cad);
            }
        }

    }

    private void partiesOperation(SolaObservableList<PartyBean> partyList) {
        if (parties.size() > 0) {
            for (PartyBean pty : partyList) {
                if (!parties.contains(pty)) {
                    parties.add(pty);
                }
            }
            refreshPartiesInLoc();
            return;
        }
        setParties(partyList);
        refreshPartiesInLoc();
    }

    private void refreshPartiesInLoc() {
        for (PartyBean pty : parties) {
            if (!locBean.getBaUnits().get(0).getParties().contains(pty)) {
                locBean.getBaUnits().get(0).getParties().add(pty);
            }
        }
    }

    private boolean newPanaCreation() {
        if ((JOptionPane.showConfirmDialog(this, "There is no any page " + txtPageNo.getText() + ". Do you like to create a new page", "Page Not Found Error", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.YES_OPTION)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean validateLoc(boolean showMessage) {
        return locBean.validate(showMessage).size() < 1;
    }

    public boolean saveLoc() {
        if (validateLoc(true)) {
            return locBean.saveLoc();
        } else {
            return false;
        }
    }

    private void saveLoc(final boolean allowClose) {
        SolaTask<Boolean, Boolean> t = new SolaTask<Boolean, Boolean>() {

            @Override
            public Boolean doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_SAVING));
                return saveLoc();
            }

            @Override
            public void taskDone() {
                if (get() != null && get()) {
                    firePropertyChange(LOC_SAVED, false, true);
                    if (allowClose) {
                        close();
                    } else {
                        MessageUtility.displayMessage(ClientMessage.LOC_SAVED);
                        MainForm.saveBeanState(locBean);
                    }
                }
            }
        };
        TaskManager.getInstance().runTask(t);
    }

    private void btnSearchNewParcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchNewParcelActionPerformed
        // TODO add your handling code here:
        if(locBean.getPanaNo()==0){
            JOptionPane.showMessageDialog(null, "No Page is specified");
            return;
        }
        Select_Parcel_Form parcelSearchForm = new Select_Parcel_Form();
        //Event delegate passing to the child JPanel.
        Class[] cls = new Class[]{CadastreObjectTO.class};
        Class workingForm = this.getClass();
        Method taskCompletion = null;
        try {
            taskCompletion = workingForm.getMethod("refreshCadastreDetails", cls);
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(CadastreObjectTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        parcelSearchForm.set_SearchCompletedTriggers(taskCompletion, this);
        displayForm(parcelSearchForm);
    }//GEN-LAST:event_btnSearchNewParcelActionPerformed

    private void displayForm(Select_Parcel_Form parcelSearchForm) {
        if (!getMainContentPanel().isPanelOpened(MainContentPanel.CARD_PARCEL_SEARCH)) {
            getMainContentPanel().addPanel(parcelSearchForm, MainContentPanel.CARD_PARCEL_SEARCH);
        }
        getMainContentPanel().showPanel(MainContentPanel.CARD_PARCEL_SEARCH);
    }

    //Invokes this method by btnAddPointActionPerformed event of LocatePointPanel.
    public void refreshCadastreDetails(CadastreObjectTO cadastreTO) {
        //do as required.
        if (cadastreTO == null) {
            return;
        }

        CadastreObjectBean cadastreBean = TypeConverters.TransferObjectToBean(
                cadastreTO, CadastreObjectBean.class, null);
        SolaObservableList<CadastreObjectBean> cadObjLst = new SolaObservableList<>();
        cadObjLst.add(cadastreBean);
        cadastreObjectOperation(cadObjLst);
    }

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if(locBean.getPanaNo()==0){
            JOptionPane.showMessageDialog(null, "No Page is specified");
            return;
        }
        saveLoc(false);
        refreshMoth(locBean);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnSave1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave1ActionPerformed
        // TODO add your handling code here:
        if(locBean.getPanaNo()==0){
            JOptionPane.showMessageDialog(null, "No Page is specified");
            return;
        }
        saveLoc(false);
        refreshMoth(locBean);
    }//GEN-LAST:event_btnSave1ActionPerformed

    //Invokes this method by btnAddPointActionPerformed event of LocatePointPanel.
    public void refreshPartyDetails(PartySearchResultBean partySummary) {
        if (partySummary == null) {
            return;
        }
        PartyBean party = partySummary.getPartyBean();
        SolaObservableList<PartyBean> partyList = new SolaObservableList<>();
        partyList.add(party);
        partiesOperation(partyList);
    }

    private void btnAddNewOwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewOwnerActionPerformed
        // TODO add your handling code here:
        if(locBean.getPanaNo()==0){
            JOptionPane.showMessageDialog(null, "No Page is specified");
            return;
        }
        PersonSearchForm partySearchForm = new PersonSearchForm();
        //Event delegate passing to the child JPanel.
        Class[] cls = new Class[]{PartySearchResultBean.class};
        Class workingForm = this.getClass();
        Method taskCompletion = null;
        try {
            taskCompletion = workingForm.getMethod("refreshPartyDetails", cls);
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(ApplicationPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        partySearchForm.setTaskCompleted_Triggering(taskCompletion, this);
        partySearchForm.setSize(700, 800);
        partySearchForm.setVisible(true);
        partySearchForm.setTitle("Details of the applicant");
        partySearchForm.setAlwaysOnTop(true);
    }//GEN-LAST:event_btnAddNewOwnerActionPerformed

    private void btnRemove1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemove1ActionPerformed
        // TODO add your handling code here:
        if(cadastreObjects.size()==0){
            JOptionPane.showMessageDialog(null, "No List is specified");
            return;
        }
        if (removeConfirmation()) {
            CadastreObjectBean cadBean = getSelectedCadastreObjectBean();
            cadastreObjects.remove(cadBean);
            if (locBean.getBaUnits().get(0).getCadastreObjectList().size() > 0) {
                locBean.getBaUnits().get(0).getCadastreObjectList().remove(cadBean);
            }
        } else {
        }
    }//GEN-LAST:event_btnRemove1ActionPerformed

    private boolean removeConfirmation() {
        if ((JOptionPane.showConfirmDialog(this, "Are you sure to remove this content from list ?", "Remove Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.YES_OPTION)) {
            return true;
        } else {
            return false;
        }

    }
    private void btnRemove2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemove2ActionPerformed
        // TODO add your handling code here:
        if(parties.size()==0){
            JOptionPane.showMessageDialog(null, "No List is specified");
            return;
        }
        if (removeConfirmation()) {
            PartyBean selectedBean = getSelectedPartyBean();
            parties.remove(selectedBean);
            if (locBean.getBaUnits().get(0).getParties().size() > 0) {
                locBean.getBaUnits().get(0).getParties().remove(selectedBean);
            }
        } else {
        }

    }//GEN-LAST:event_btnRemove2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNewOwner;
    private javax.swing.JButton btnRemove1;
    private javax.swing.JButton btnRemove2;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSave1;
    private javax.swing.JButton btnSearchNewParcel;
    private javax.swing.ButtonGroup buttonGroup1;
    private org.sola.clients.beans.cadastre.CadastreObjectBean cadastreObjectBean;
    private org.sola.clients.beans.cadastre.CadastreObjectListBean cadastreObjectListBean;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private org.sola.clients.beans.administrative.LocBean locBean;
    private org.sola.clients.beans.administrative.LocListBean locListBean;
    private org.sola.clients.beans.cadastre.MapSheetListBean mapSheetListBean;
    private org.sola.clients.beans.administrative.MothBean mothBean;
    private javax.swing.JTextField txtMothLuj;
    private javax.swing.JTextField txtMothLujNo;
    private javax.swing.JTextField txtPageNo;
    private javax.swing.JTextField txtVdc;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
