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
package org.sola.clients.swing.ui.source;

import java.awt.ComponentOrientation;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import org.sola.clients.beans.digitalarchive.DocumentBean;
import org.sola.clients.beans.digitalarchive.FileBinaryBean;
import org.sola.clients.beans.digitalarchive.FileInfoListBean;
import org.sola.clients.beans.source.SourceBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.ui.ImagePreview;
import org.sola.clients.swing.ui.renderers.FileNameCellRenderer;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;
import org.sola.services.boundary.wsclients.WSManager;

/**
 * This form provides browsing of local and remote folders for the scanned
 * images. Could be used to attach digital copies of documents.<br /> The
 * following bean is used to bind server side files list on the form -
 * {@link FileInfoListBean}.
 */
public class FileBrowserForm extends javax.swing.JDialog {

    /**
     * File browser action upon file attachment event.
     */
    public enum AttachAction {

        CLOSE_WINDOW, SHOW_MESSAGE
    }
    /**
     * Property name, used to rise property change event upon attached document
     * id change. This event is rised on attach button click.
     */
    public static final String ATTACHED_DOCUMENT = "AttachedDocumentId";
    private ResourceBundle formBundle = ResourceBundle.getBundle("org/sola/clients/swing/ui/source/Bundle");
    private AttachAction attachAction = AttachAction.CLOSE_WINDOW;

    public FileBrowserForm(java.awt.Frame parent, boolean modal, AttachAction attachAction) {
        super(parent, modal);
        this.attachAction = attachAction;
        initComponents();
        postInit();
    }

    private void postInit() {
        localFileChooser.setControlButtonsAreShown(false);
        localFileChooser.setAccessory(new ImagePreview(localFileChooser, 225, 300));
    }

    /**
     * Checks uploaded document bean, rises {@link #ATTACHED_DOCUMENT} property
     * change event. Closes the window or displays the message, depending on the {@link AttachAction}
     * value, passed to the form constructor.
     */
    private void fireAttachEvent(DocumentBean documentBean) {
        if (documentBean == null) {
            MessageUtility.displayMessage(ClientMessage.ARCHIVE_FAILED_TO_ATTACH_FILE,
                    new Object[]{serverFiles.getSelectedFileInfoBean().getName()});
        } else {
            this.firePropertyChange(ATTACHED_DOCUMENT, null, documentBean);

            if (attachAction == AttachAction.CLOSE_WINDOW) {
                this.dispose();
            }

            if (attachAction == AttachAction.SHOW_MESSAGE) {
                MessageUtility.displayMessage(ClientMessage.ARCHIVE_FILE_ADDED,
                        new Object[]{documentBean.getNr()});
            }
        }
    }

    private void attachLocalFile() {
        File selectedFile = localFileChooser.getSelectedFile();
        if (selectedFile != null) {
            SolaTask<Void, Void> task = new SolaTask<Void, Void>() {

                DocumentBean document = null;

                @Override
                protected Void doTask() {
                    setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.SOURCE_LOAD_DOC_ON_SERVER));
                    document = DocumentBean.createDocumentFromLocalFile(
                            localFileChooser.getSelectedFile());
                    return null;
                }

                @Override
                protected void taskDone() {
                    fireAttachEvent(document);
                }
            };
            TaskManager.getInstance().runTask(task);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        serverFiles = new org.sola.clients.beans.digitalarchive.FileInfoListBean();
        taskPanel1 = new org.sola.clients.swing.common.tasks.TaskPanel();
        jPanel1 = new javax.swing.JPanel();
        localFileChooser = new javax.swing.JFileChooser();
        jToolBar2 = new javax.swing.JToolBar();
        btnOpenLocal = new javax.swing.JButton();
        btnAttachLocal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/ui/source/Bundle"); // NOI18N
        setTitle(bundle.getString("FileBrowserForm.title_1")); // NOI18N
        setMinimumSize(new java.awt.Dimension(706, 432));
        setName("Form"); // NOI18N
        setResizable(false);

        taskPanel1.setName(bundle.getString("FileBrowserForm.taskPanel1.name")); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

        localFileChooser.setName("localFileChooser"); // NOI18N
        localFileChooser.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        localFileChooser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                localFileChooserMouseClicked(evt);
            }
        });

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setName("jToolBar2"); // NOI18N

        btnOpenLocal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/folder-open-document.png"))); // NOI18N
        btnOpenLocal.setText(bundle.getString("FileBrowserForm.btnOpenLocal.text")); // NOI18N
        btnOpenLocal.setFocusable(false);
        btnOpenLocal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnOpenLocal.setName("btnOpenLocal"); // NOI18N
        btnOpenLocal.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOpenLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenLocalActionPerformed(evt);
            }
        });
        jToolBar2.add(btnOpenLocal);

        btnAttachLocal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/attachment.png"))); // NOI18N
        btnAttachLocal.setText(bundle.getString("FileBrowserForm.btnAttachLocal.text")); // NOI18N
        btnAttachLocal.setFocusable(false);
        btnAttachLocal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAttachLocal.setName("btnAttachLocal"); // NOI18N
        btnAttachLocal.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAttachLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAttachLocalActionPerformed(evt);
            }
        });
        jToolBar2.add(btnAttachLocal);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(localFileChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(localFileChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(taskPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(taskPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Opens selected file from the local drive, by double click.
     */
    private void localFileChooserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_localFileChooserMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                Desktop dt = Desktop.getDesktop();
                dt.open(localFileChooser.getSelectedFile());
            } catch (IOException ex) {
                MessageUtility.displayMessage(ClientMessage.ERR_FAILED_OPEN_FILE,
                        new Object[]{localFileChooser.getSelectedFile().getName()});
            }
        }
    }//GEN-LAST:event_localFileChooserMouseClicked

    /**
     * Opens selected file from the local drive, by click on the open button.
     */
    private void btnOpenLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenLocalActionPerformed
        SolaTask t = new SolaTask<Void, Void>() {

            @Override
            public Void doTask() {
                try {
                    setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_DOCUMENT_OPENING));
                    Desktop dt = Desktop.getDesktop();
                    dt.open(localFileChooser.getSelectedFile());
                } catch (IOException ex) {
                    return null;
                }
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }//GEN-LAST:event_btnOpenLocalActionPerformed

    /**
     * Uploads selected file into the digital archive from local drive, gets {@link DocumentBean}
     * of uploaded file and calls method
     * {@link #fireAttachEvent(DocumentBean)} to rise attachment event.
     */
    private void btnAttachLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAttachLocalActionPerformed
        attachLocalFile();
    }//GEN-LAST:event_btnAttachLocalActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAttachLocal;
    private javax.swing.JButton btnOpenLocal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JFileChooser localFileChooser;
    private org.sola.clients.beans.digitalarchive.FileInfoListBean serverFiles;
    private org.sola.clients.swing.common.tasks.TaskPanel taskPanel1;
    // End of variables declaration//GEN-END:variables
}
