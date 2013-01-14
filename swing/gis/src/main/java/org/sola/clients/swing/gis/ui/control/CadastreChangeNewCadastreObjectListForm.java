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
package org.sola.clients.swing.gis.ui.control;

import com.vividsolutions.jts.geom.Geometry;
import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.jdesktop.observablecollections.ObservableList;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.beans.cadastre.CadastreObjectBean;
import org.sola.clients.swing.common.utils.Utils;
import org.sola.clients.swing.gis.PublicMethod;
import org.sola.clients.swing.gis.layer.CadastreChangeNewCadastreObjectLayer;
import org.sola.common.FrameUtility;
import org.sola.common.StringUtility;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * This form is used to display information about the new cadastre objects
 * during the cadastre change process.
 */
public class CadastreChangeNewCadastreObjectListForm extends javax.swing.JDialog {

    public static final String SELECTED_CADASTRE_OBJECT = "selectedCadastreObject";
    private CadastreChangeNewCadastreObjectLayer layer;
    private CadastreObjectBean selectedCadastreObject;

    /** Dialog constructor */
    public CadastreChangeNewCadastreObjectListForm(CadastreChangeNewCadastreObjectLayer cadastreObjectLayer) {
        super(FrameUtility.getTopFrame(), true);
        this.layer = cadastreObjectLayer;
        initComponents();
        postInit();
        Utils.positionFormCentrally(this);
    }

    private void postInit() {
        this.addPropertyChangeListener(SELECTED_CADASTRE_OBJECT, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                setupListButtons();
            }
        });
        setupListButtons();
    }

    private void setupListButtons() {
        boolean enabled = selectedCadastreObject != null;
        btnShowOnMap.setEnabled(enabled);
        btnEdit.setEnabled(enabled);
        btnRemove.setEnabled(enabled);
        menuShowOnMap.setEnabled(btnShowOnMap.isEnabled());
        menuEdit.setEnabled(btnEdit.isEnabled());
        menuRemove.setEnabled(btnRemove.isEnabled());
    }

    public ObservableList<CadastreObjectBean> getCadastreObjects() {
        return layer.getCadastreObjectList();
    }

    public CadastreObjectBean getSelectedCadastreObject() {
        return selectedCadastreObject;
    }

    public void setSelectedCadastreObject(CadastreObjectBean selectedCadastreObject) {
        CadastreObjectBean oldValue = this.selectedCadastreObject;
        this.selectedCadastreObject = selectedCadastreObject;
        firePropertyChange(SELECTED_CADASTRE_OBJECT, oldValue, this.selectedCadastreObject);
    }

    private void showOnMap() {
        if (selectedCadastreObject == null) {
            return;
        }

        SimpleFeature featureToSelect = layer.getFeatureCollection().getFeature(selectedCadastreObject.getId());
        if (featureToSelect != null) {
            String geomfld = PublicMethod.theGeomFieldName(this.layer.getFeatureCollection());
            Geometry geomToSelect = (Geometry) featureToSelect.getAttribute(geomfld);

            if (geomToSelect != null) {
                // Remove selection from features if there are any
                clearMapSelection(false);
                featureToSelect.setAttribute(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_SELECTED, "1");

                ReferencedEnvelope envelope = JTS.toEnvelope(geomToSelect);
                double expand_by = envelope.getHeight() * 0.5;//expand 50 % of height
                envelope.expandBy(expand_by);
                this.layer.getMapControl().setDisplayArea(envelope);
                this.layer.getMapControl().refresh(false);
            }
        }
    }

    private void clearMapSelection(boolean refresh) {
        SimpleFeatureIterator feaIter = layer.getFeatureCollection().features();
        while (feaIter.hasNext()) {
            SimpleFeature feature = feaIter.next();
            feature.setAttribute(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_SELECTED, "0");
        }
        if(refresh){
            this.layer.getMapControl().refresh(false);
        }
    }

    private void edit() {
        if (selectedCadastreObject == null) {
            return;
        }

        CadastreObjectBean tmp = selectedCadastreObject.copy();
        parcelPanel.setCadastreObject(tmp);
        switchPanels(true);
    }

    private void switchPanels(boolean showParcel) {
        if (showParcel) {
            ((CardLayout) pnlCards.getLayout()).show(pnlCards, "PARCEL");
        } else {
            ((CardLayout) pnlCards.getLayout()).show(pnlCards, "LIST");
        }
    }

    private void remove() {
        if (selectedCadastreObject != null && selectedCadastreObject.getId() != null) {
            if (MessageUtility.displayMessage(GisMessage.PARCEL_CONFIRM_REMOVAL) == MessageUtility.BUTTON_ONE) {
                this.layer.removeFeature(selectedCadastreObject.getId());
            }
        }
    }

    private void saveChanges() {
        if (parcelPanel.getCadastreObject().validate(true).size() > 0) {
            return;
        }

        parcelPanel.getCadastreObject().generateNameFirstLastPart();
        CadastreObjectBean newObj = selectedCadastreObject.copy();
        newObj.copyFromObject(parcelPanel.getCadastreObject());
        getCadastreObjects().set(getCadastreObjects().indexOf(selectedCadastreObject), newObj);
        tableCadastreObjectsList.clearSelection();
        switchPanels(false);
        updateFeature(newObj);
    }

    /**
     * Updates feature attributes by provided {@link CadastreObjectBean}
     *
     * @param cadastreObjectBean Cadastre object used to update feature
     */
    private void updateFeature(CadastreObjectBean cadastreObjectBean) {
        if (cadastreObjectBean == null || cadastreObjectBean.getId() == null) {
            return;
        }

        SimpleFeature feature = layer.getFeatureCollection().getFeature(cadastreObjectBean.getId());
        String nameFPart = StringUtility.empty(cadastreObjectBean.getNameFirstPart());
        String nameLPart = StringUtility.empty(cadastreObjectBean.getNameLastPart());
        String officialArea = StringUtility.empty(cadastreObjectBean.getOfficialAreaFormatted());
        String mapSheet = "";
        if (cadastreObjectBean.getMapSheet() != null) {
            mapSheet = StringUtility.empty(cadastreObjectBean.getMapSheet().getMapNumber());
        }

        boolean hasChanges = false;

        if (!feature.getAttribute(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_FIRST_PART).equals(nameFPart)) {
            feature.setAttribute(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_FIRST_PART, nameFPart);
            hasChanges = true;
        }

        if (!feature.getAttribute(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_LAST_PART).equals(nameLPart)) {
            feature.setAttribute(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_LAST_PART, nameLPart);
            hasChanges = true;
        }

        if (!feature.getAttribute(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_OFFICIAL_AREA).equals(officialArea)) {
            feature.setAttribute(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_OFFICIAL_AREA, officialArea);
            hasChanges = true;
        }

        if (!feature.getAttribute(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_MAP_SHEET).equals(mapSheet)) {
            feature.setAttribute(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_MAP_SHEET, mapSheet);
            hasChanges = true;
        }

        if (hasChanges) {
            layer.getMapControl().refresh();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        urbanRural = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        popupCadastreObjectsList = new javax.swing.JPopupMenu();
        menuShowOnMap = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenuItem();
        menuRemove = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuClearMapSelection = new javax.swing.JMenuItem();
        pnlCards = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnShowOnMap = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnClearSelectionOnMap = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCadastreObjectsList = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        jPanel1 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        parcelPanel = new org.sola.clients.swing.ui.cadastre.ParcelPanel();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/gis/ui/control/Bundle"); // NOI18N
        jButton1.setText(bundle.getString("CadastreChangeNewCadastreObjectListForm.jButton1.text")); // NOI18N

        menuShowOnMap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/network.png"))); // NOI18N
        menuShowOnMap.setText(bundle.getString("CadastreChangeNewCadastreObjectListForm.menuShowOnMap.text")); // NOI18N
        menuShowOnMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuShowOnMapActionPerformed(evt);
            }
        });
        popupCadastreObjectsList.add(menuShowOnMap);

        menuEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        menuEdit.setText(bundle.getString("CadastreChangeNewCadastreObjectListForm.menuEdit.text")); // NOI18N
        menuEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditActionPerformed(evt);
            }
        });
        popupCadastreObjectsList.add(menuEdit);

        menuRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        menuRemove.setText(bundle.getString("CadastreChangeNewCadastreObjectListForm.menuRemove.text")); // NOI18N
        menuRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRemoveActionPerformed(evt);
            }
        });
        popupCadastreObjectsList.add(menuRemove);
        popupCadastreObjectsList.add(jSeparator2);

        menuClearMapSelection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/broom.png"))); // NOI18N
        menuClearMapSelection.setText(bundle.getString("CadastreChangeNewCadastreObjectListForm.menuClearMapSelection.text")); // NOI18N
        menuClearMapSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuClearMapSelectionActionPerformed(evt);
            }
        });
        popupCadastreObjectsList.add(menuClearMapSelection);

        setTitle(bundle.getString("CadastreChangeNewCadastreObjectListForm.title")); // NOI18N

        pnlCards.setLayout(new java.awt.CardLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnShowOnMap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/network.png"))); // NOI18N
        btnShowOnMap.setText(bundle.getString("CadastreChangeNewCadastreObjectListForm.btnShowOnMap.text")); // NOI18N
        btnShowOnMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowOnMapActionPerformed(evt);
            }
        });
        jToolBar1.add(btnShowOnMap);

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        btnEdit.setText(bundle.getString("CadastreChangeNewCadastreObjectListForm.btnEdit.text")); // NOI18N
        btnEdit.setFocusable(false);
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEdit);

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemove.setText(bundle.getString("CadastreChangeNewCadastreObjectListForm.btnRemove.text")); // NOI18N
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRemove);
        jToolBar1.add(jSeparator1);

        btnClearSelectionOnMap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/broom.png"))); // NOI18N
        btnClearSelectionOnMap.setText(bundle.getString("CadastreChangeNewCadastreObjectListForm.btnClearSelectionOnMap.text")); // NOI18N
        btnClearSelectionOnMap.setFocusable(false);
        btnClearSelectionOnMap.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClearSelectionOnMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearSelectionOnMapActionPerformed(evt);
            }
        });
        jToolBar1.add(btnClearSelectionOnMap);

        tableCadastreObjectsList.setComponentPopupMenu(popupCadastreObjectsList);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cadastreObjects}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, tableCadastreObjectsList);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${propertyIdCode}"));
        columnBinding.setColumnName("Property Id Code");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${address.vdcBean.displayValue}"));
        columnBinding.setColumnName("Address.vdc Bean.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${address.wardNo}"));
        columnBinding.setColumnName("Address.ward No");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mapSheet.mapNumber}"));
        columnBinding.setColumnName("Map Sheet.map Number");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${parcelno}"));
        columnBinding.setColumnName("Parcelno");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${officialAreaFormatted}"));
        columnBinding.setColumnName("Official Area Formatted");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${selectedCadastreObject}"), tableCadastreObjectsList, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(tableCadastreObjectsList);
        tableCadastreObjectsList.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("CadastreChangeNewCadastreObjectListForm.tableCadastreObjectsList.columnModel.title0")); // NOI18N
        tableCadastreObjectsList.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("CadastreChangeNewCadastreObjectListForm.tableCadastreObjectsList.columnModel.title1")); // NOI18N
        tableCadastreObjectsList.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("CadastreChangeNewCadastreObjectListForm.tableCadastreObjectsList.columnModel.title2")); // NOI18N
        tableCadastreObjectsList.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("CadastreChangeNewCadastreObjectListForm.tableCadastreObjectsList.columnModel.title3")); // NOI18N
        tableCadastreObjectsList.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("CadastreChangeNewCadastreObjectListForm.tableCadastreObjectsList.columnModel.title4")); // NOI18N
        tableCadastreObjectsList.getColumnModel().getColumn(5).setHeaderValue(bundle.getString("CadastreChangeNewCadastreObjectListForm.tableCadastreObjectsList.columnModel.title5")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
        );

        pnlCards.add(jPanel2, "LIST");

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/save.png"))); // NOI18N
        btnSave.setText(bundle.getString("CadastreChangeNewCadastreObjectListForm.btnSave.text")); // NOI18N
        btnSave.setFocusable(false);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar2.add(btnSave);

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/revert.png"))); // NOI18N
        btnCancel.setText(bundle.getString("CadastreChangeNewCadastreObjectListForm.btnCancel.text")); // NOI18N
        btnCancel.setFocusable(false);
        btnCancel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jToolBar2.add(btnCancel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(parcelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(parcelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE))
        );

        pnlCards.add(jPanel1, "PARCEL");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlCards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlCards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
    remove();
}//GEN-LAST:event_btnRemoveActionPerformed

    private void btnShowOnMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowOnMapActionPerformed
        showOnMap();
    }//GEN-LAST:event_btnShowOnMapActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        edit();
    }//GEN-LAST:event_btnEditActionPerformed

    private void menuShowOnMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuShowOnMapActionPerformed
        showOnMap();
    }//GEN-LAST:event_menuShowOnMapActionPerformed

    private void menuEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditActionPerformed
        edit();
    }//GEN-LAST:event_menuEditActionPerformed

    private void menuRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRemoveActionPerformed
        remove();
    }//GEN-LAST:event_menuRemoveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        switchPanels(false);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveChanges();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnClearSelectionOnMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSelectionOnMapActionPerformed
        clearMapSelection(true);
    }//GEN-LAST:event_btnClearSelectionOnMapActionPerformed

    private void menuClearMapSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuClearMapSelectionActionPerformed
        clearMapSelection(true);
    }//GEN-LAST:event_menuClearMapSelectionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClearSelectionOnMap;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnShowOnMap;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JMenuItem menuClearMapSelection;
    private javax.swing.JMenuItem menuEdit;
    private javax.swing.JMenuItem menuRemove;
    private javax.swing.JMenuItem menuShowOnMap;
    private org.sola.clients.swing.ui.cadastre.ParcelPanel parcelPanel;
    private javax.swing.JPanel pnlCards;
    private javax.swing.JPopupMenu popupCadastreObjectsList;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tableCadastreObjectsList;
    private javax.swing.ButtonGroup urbanRural;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
