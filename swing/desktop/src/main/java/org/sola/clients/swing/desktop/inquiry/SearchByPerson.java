/*
 * Copyright 2012 Food and Agriculture Organization of the United Nations (FAO).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sola.clients.swing.desktop.inquiry;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import org.sola.clients.beans.administrative.BaUnitAsPartyBean;
import org.sola.clients.beans.administrative.BaUnitBean;
import org.sola.clients.beans.cadastre.CadastreObjectBean;
import org.sola.clients.beans.party.PartyBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.desktop.party.PartyPanelForm;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.MainContentPanel;
import org.sola.clients.swing.ui.party.PartySearchPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author KumarKhadka
 */
public class SearchByPerson extends ContentPanel {

    private List<CadastreObjectBean> cadastreObjects;
  
    public List<CadastreObjectBean> getCadastreObjects() {
        return cadastreObjects;
    }

    public void setCadastreObjects(List<CadastreObjectBean> cadastreObjects) {
        List<CadastreObjectBean> oldValue=this.cadastreObjects;
        this.cadastreObjects = cadastreObjects;
        firePropertyChange("cadastreObjects", oldValue, this.cadastreObjects);
    }
 
    /**
     * Creates new form SearchByPerson
     */
    public SearchByPerson() {
        initComponents();
        partySearchPanel.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(PartySearchPanel.CREATE_NEW_PARTY_PROPERTY)
                        || evt.getPropertyName().equals(PartySearchPanel.EDIT_PARTY_PROPERTY)
                        || evt.getPropertyName().equals(PartySearchPanel.VIEW_PARTY_PROPERTY)
                        || evt.getPropertyName().equals(PartySearchPanel.SELECT_PARTY_PROPERTY)) {
                    handleSearchPanelEvents(evt);
                }
            }
        });
    }

    private void handleSearchPanelEvents(final PropertyChangeEvent evt) {
        SolaTask t = new SolaTask<Void, Void>() {

            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_PERSON));
                PartyPanelForm panel = null;

                if (evt.getPropertyName().equals(PartySearchPanel.CREATE_NEW_PARTY_PROPERTY)) {
                    panel = new PartyPanelForm(true, null, false, false);
                    panel.addPropertyChangeListener(new PropertyChangeListener() {

                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if (evt.getPropertyName().equals(PartyPanelForm.PARTY_SAVED)) {
                                ((PartyPanelForm) evt.getSource()).setParty(null);
                            }
                        }
                    });
                } else if (evt.getPropertyName().equals(PartySearchPanel.EDIT_PARTY_PROPERTY)) {
                    panel = new PartyPanelForm(true, (PartyBean) evt.getNewValue(), false, true);
                } else if (evt.getPropertyName().equals(PartySearchPanel.VIEW_PARTY_PROPERTY)) {
                    panel = new PartyPanelForm(true, (PartyBean) evt.getNewValue(), true, true);
                }else if (evt.getPropertyName().equals(PartySearchPanel.SELECT_PARTY_PROPERTY)) {                    
                    PartyBean partyBean = partySearchPanel.getParty();
                    getPartyBaUnitAsPartyList(partyBean);//                   
                }

                if (panel != null) {
                    getMainContentPanel().addPanel(panel, MainContentPanel.CARD_PERSON, true);
                }
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }
    
     public PartySearchPanel getPartySearchPanel() {
        return partySearchPanel;
    }
    
    private void getPartyBaUnitAsPartyList(PartyBean partyBean) {
        List<BaUnitAsPartyBean> baAsParty=new ArrayList<>();
           if(partyBean!=null){
               baAsParty=BaUnitAsPartyBean.getBaUnitAsPartyList(partyBean.getId());
           }
           getPartyRelatedBaUnits(baAsParty);
           
    }
     private void getPartyRelatedBaUnits(List<BaUnitAsPartyBean> baUnitAsPartyBeans){
         List<BaUnitBean> baUnitBeans=new ArrayList<>();
         for(BaUnitAsPartyBean baUnitAsPartyBean1 : baUnitAsPartyBeans){
             BaUnitBean baUnitBean=BaUnitBean.getBaUnitsById(baUnitAsPartyBean1.getBaUnitId());
             baUnitBeans.add(baUnitBean);
         }     
         getPartyRelatedCadastres(baUnitBeans);
     }
    
     private void getPartyRelatedCadastres(List<BaUnitBean> baUnitBeans){
         List<CadastreObjectBean> cadastreObjectBeans=new ArrayList<>();
         for(BaUnitBean baUnitBean : baUnitBeans){
             cadastreObjectBeans.addAll(baUnitBean.getCadastreObjectList());
         }
         setCadastreObjects(cadastreObjectBeans);          
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

        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        jPanel1 = new javax.swing.JPanel();
        partySearchPanel = new org.sola.clients.swing.ui.party.PartySearchPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setHeaderPanel(headerPanel1);

        headerPanel1.setTitleText("Search by Person");

        jPanel1.setLayout(new java.awt.GridLayout(2, 1, 0, 15));
        jPanel1.add(partySearchPanel);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Related Cadastre's details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 102, 102))); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cadastreObjects}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${parcelno}"));
        columnBinding.setColumnName("Parcel No");
        columnBinding.setColumnClass(Integer.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private org.sola.clients.swing.ui.party.PartySearchPanel partySearchPanel;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
