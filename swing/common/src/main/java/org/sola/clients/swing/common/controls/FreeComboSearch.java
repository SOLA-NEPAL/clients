/*
 * Copyright 2013 Food and Agriculture Organization of the United Nations (FAO).
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
package org.sola.clients.swing.common.controls;

import javax.swing.JComboBox;

/**
 *
 * @author Kumar
 */
public class FreeComboSearch extends JComboBox {
   
}












///**
// * ******************************************************************************************
// * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations
// * (FAO). All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without
// * modification, are permitted provided that the following conditions are met:
// *
// * 1. Redistributions of source code must retain the above copyright notice,this
// * list of conditions and the following disclaimer. 2. Redistributions in binary
// * form must reproduce the above copyright notice,this list of conditions and
// * the following disclaimer in the documentation and/or other materials provided
// * with the distribution. 3. Neither the name of FAO nor the names of its
// * contributors may be used to endorse or promote products derived from this
// * software without specific prior written permission.
// *
// * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT OF
// * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// * CONTRACT,STRICT LIABILITY,OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
// * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// * POSSIBILITY OF SUCH DAMAGE.
// * *********************************************************************************************
// */
///*
// * To change this template, choose Tools | Templates and open the template in the editor.
// */
//package org.sola.clients.swing.common.controls;
//
//import java.awt.Component;
//import java.awt.Container;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import javax.swing.DefaultListModel;
//import javax.swing.JList;
//import javax.swing.JScrollPane;
//import javax.swing.JTextField;
//import javax.swing.event.ListSelectionEvent;
//import org.sola.clients.swing.common.LafManager;
//
///**
// *
// * @author Elton Manoku
// */
//public class FreeTextSearch extends JTextField {
//
//    private JList list = null;
//    private JScrollPane listScroll = null;
//    private Integer listHeight = 100;
//    private Object selectedElement = null;
//    private Integer minimalSearchStringLength = 1;
//    private String searchString = "";
//    private boolean listIsHosted = false;
//    private boolean hideListIfNotNeeded = true;
//    private boolean refreshTextInSelection = false;
//
//    public FreeTextSearch() {
//        this.addKeyListener(
//                new java.awt.event.KeyAdapter() {
//
//                    @Override
//                    public void keyReleased(java.awt.event.KeyEvent evt) {
//                        processInput(evt);
//                    }
//                });
//
//        this.list = new JList(new DefaultListModel());
//        this.list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
//        LafManager.getInstance().setListProperties(list);
//        this.list.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
//
//            @Override
//            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
//                listValueChanged(evt);
//            }
//        });
//        this.list.addKeyListener(new java.awt.event.KeyAdapter() {
//
//            @Override
//            public void keyPressed(java.awt.event.KeyEvent evt) {
//                onListKeyPressed(evt);
//            }
//        });
//        this.list.addMouseListener(new MouseAdapter() {
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                onMouseClicked(e);
//            }
//        });
//    }
//
//    private void processInput(java.awt.event.KeyEvent evt) {
//        JTextField tmp = (JTextField) evt.getSource();
//        LafManager.getInstance().setTxtProperties(tmp);
//
//        String tmpSearchString = tmp.getText();
//        if (tmpSearchString.length() < this.minimalSearchStringLength
//                && this.listScroll != null) {
//            if (this.hideListIfNotNeeded) {
//                this.listScroll.setVisible(false);
//            }
//            return;
//        }
//
//
//        if (listScroll == null) {
//            listScroll = new JScrollPane(list);
//            listScroll.setVisible(false);
//        } else {
//            listScroll.setViewportView(list);
//        }
//
//        if (!listScroll.isVisible()) {
//            listScroll.setSize(getSize().width, listHeight);
//
//            getParent().add(listScroll);
//            getParent().setComponentZOrder(listScroll, 0);
//
//            listScroll.setLocation(getLocation().x, getLocation().y + getSize().height + 2);
//        }
//
//        if (evt.getKeyCode() == 40) {
//            list.requestFocusInWindow();
//            return;
//        }
//
//        if (!this.searchString.equals(tmpSearchString)) {
//            DefaultListModel model = (DefaultListModel) this.list.getModel();
//            this.onNewSearchString(tmpSearchString, model);
//            this.listScroll.setVisible(true);
//        }
//        this.searchString = tmpSearchString;
//    }
//
//    private Container getTopParent() {
//        Container cont = this.getParent();
//        while (cont.getParent() != null) {
//            cont = cont.getParent();
//        }
//        return cont;
//    }
//
//    private void listValueChanged(ListSelectionEvent e) {
//        if (e.getValueIsAdjusting() == false) {
//
//            if (list.getSelectedIndex() == -1) {
//                this.selectedElement = null;
//
//            } else {
//                this.setSelectedElement(list.getSelectedValue());
//            }
//        }
//    }
//
//    private void onListKeyPressed(java.awt.event.KeyEvent evt) {
//        //Enter is pressed
//        if (evt.getKeyCode() == 10) {
//            this.confirmSelection();
//        }
//    }
//
//    private void onMouseClicked(MouseEvent e) {
//        if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
//            this.confirmSelection();
//        }
//    }
//
//    private void confirmSelection() {
//        if (this.getSelectedElement() == null) {
//            return;
//        }
//        if (this.hideListIfNotNeeded) {
//            if (listScroll.isVisible()) {
//                listScroll.setVisible(false);
//                getParent().remove(listScroll);
//            }
//        }
//        this.onSelectionConfirmed();
//    }
//
//    public void onSelectionConfirmed() {
//        System.out.println("Selected object:" + this.getSelectedElement());
//    }
//
//    public void onNewSearchString(String searchString, DefaultListModel listModel) {
//        listModel.addElement(searchString);
//    }
//
//    public Object getSelectedElement() {
//        return this.selectedElement;
//    }
//
//    public void setSelectedElement(Object element) {
//        this.selectedElement = element;
//        if (this.refreshTextInSelection) {
//            this.setText(this.selectedElement.toString());
//        }
//    }
//
//    public Integer getListHeight() {
//        return listHeight;
//    }
//
//    public void setListHeight(Integer listHeight) {
//        this.listHeight = listHeight;
//    }
//
//    public Integer getMinimalSearchStringLength() {
//        return minimalSearchStringLength;
//    }
//
//    public void setMinimalSearchStringLength(Integer minimalSearchStringLength) {
//        this.minimalSearchStringLength = minimalSearchStringLength;
//    }
//
//    public JScrollPane getListScroll() {
//        return listScroll;
//    }
//
//    public void setListScroll(JScrollPane listScroll) {
//        this.listScroll = listScroll;
//    }
//
//    public boolean isHideListIfNotNeeded() {
//        return hideListIfNotNeeded;
//    }
//
//    public void setHideListIfNotNeeded(boolean hideListIfNotNeeded) {
//        this.hideListIfNotNeeded = hideListIfNotNeeded;
//    }
//
//    public boolean isRefreshTextInSelection() {
//        return refreshTextInSelection;
//    }
//
//    public void setRefreshTextInSelection(boolean refreshTextInSelection) {
//        this.refreshTextInSelection = refreshTextInSelection;
//    }
//}
