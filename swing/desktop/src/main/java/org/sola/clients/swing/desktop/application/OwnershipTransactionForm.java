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
package org.sola.clients.swing.desktop.application;

/**
 *
 * @author Solovov
 */
public class OwnershipTransactionForm extends javax.swing.JPanel {

    /**
     * Creates new form OwnershipTransactionForm
     */
    public OwnershipTransactionForm() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel1 = new org.sola.clients.swing.ui.HeaderPanel();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableWithDefaultStyles1 = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        groupPanel1 = new org.sola.clients.swing.ui.GroupPanel();
        jPanel2 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableWithDefaultStyles2 = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        groupPanel2 = new org.sola.clients.swing.ui.GroupPanel();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/application/Bundle"); // NOI18N
        headerPanel1.setTitleText(bundle.getString("OwnershipTransactionForm.headerPanel1.titleText")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        jButton1.setText(bundle.getString("OwnershipTransactionForm.jButton1.text")); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        jButton2.setText(bundle.getString("OwnershipTransactionForm.jButton2.text")); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton2);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/change-share.png"))); // NOI18N
        jButton3.setText(bundle.getString("OwnershipTransactionForm.jButton3.text")); // NOI18N
        jButton3.setFocusable(false);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton3);

        jTableWithDefaultStyles1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1-03-01", "342", "1003", "234",  new Boolean(true)},
                {"2-44-12", "234", "", null, null},
                {"4-05-12", "456", "1233", "43",  new Boolean(true)}
            },
            new String [] {
                "Name First Part", "Name Last Part", "Moth", "Loc", "Exists"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableWithDefaultStyles1);

        groupPanel1.setTitleText(bundle.getString("OwnershipTransactionForm.groupPanel1.titleText")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
            .addComponent(groupPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(groupPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        );

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/view.png"))); // NOI18N
        jButton5.setText(bundle.getString("OwnershipTransactionForm.jButton5.text")); // NOI18N
        jButton5.setFocusable(false);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton5);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/pencil.png"))); // NOI18N
        jButton4.setText(bundle.getString("OwnershipTransactionForm.jButton4.text")); // NOI18N
        jButton4.setFocusable(false);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton4);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        jButton6.setText(bundle.getString("OwnershipTransactionForm.jButton6.text")); // NOI18N
        jButton6.setFocusable(false);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton6);

        jTableWithDefaultStyles2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1-03-01", "342", "1003", "234", "Pending", "Ownership change"},
                {"2-44-12", "234", "2344", "111", "Current", "Rights change"},
                {"4-05-12", "456", "1233", "43", "Pending", "Termination"}
            },
            new String [] {
                "Name First Part", "Name Last Part", "Moth", "Loc", "Status", "Reason"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableWithDefaultStyles2);

        groupPanel2.setTitleText(bundle.getString("OwnershipTransactionForm.groupPanel2.titleText")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(groupPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(groupPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sola.clients.swing.ui.GroupPanel groupPanel1;
    private org.sola.clients.swing.ui.GroupPanel groupPanel2;
    private org.sola.clients.swing.ui.HeaderPanel headerPanel1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles jTableWithDefaultStyles1;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles jTableWithDefaultStyles2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    // End of variables declaration//GEN-END:variables
}
