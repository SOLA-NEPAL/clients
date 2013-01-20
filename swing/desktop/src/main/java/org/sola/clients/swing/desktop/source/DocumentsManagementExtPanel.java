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
package org.sola.clients.swing.desktop.source;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.sola.clients.beans.application.ApplicationBean;
import org.sola.clients.beans.controls.SolaList;
import org.sola.clients.beans.source.SourceBean;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.common.utils.Utils;
import org.sola.clients.swing.desktop.DesktopApplication;
import org.sola.clients.swing.ui.application.ApplicationDocumentsForm;
import org.sola.clients.swing.ui.source.DocumentSearchPanel;
import org.sola.clients.swing.ui.source.DocumentsManagementPanel;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Extends {@link DocumentsManagementPanel} to handle buttons events and open
 * appropriate forms.
 */
public class DocumentsManagementExtPanel extends DocumentsManagementPanel {

    private boolean saveOnAction = false;
    private boolean closeOnAction = true;
    private ApplicationBean applicationBean;
    
    public DocumentsManagementExtPanel(){
        super();
    }
    
    public DocumentsManagementExtPanel(SolaList<SourceBean> sourceList, 
            ApplicationBean applicationBean, boolean allowEdit) {
        
        super(sourceList, allowEdit);
        this.applicationBean = applicationBean;
        
        addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                switch (evt.getPropertyName()) {
                    case DocumentsManagementPanel.EVENT_ADD_FROM_APPLICATION:
                        addFromApplication();
                        break;
                    case DocumentsManagementPanel.EVENT_EDIT:
                        openSourceForm((SourceBean)evt.getNewValue(), true);
                        break;
                    case DocumentsManagementPanel.EVENT_NEW:
                        openSourceForm(null, true);
                        break;
                    case DocumentsManagementPanel.EVENT_SEARCH:
                        openSearch();
                        break;
                    case DocumentsManagementPanel.EVENT_VIEW:
                        openSourceForm((SourceBean)evt.getNewValue(), false);
                        break;
                }
            }
        });
    }

    public boolean isCloseOnAction() {
        return closeOnAction;
    }

    public void setCloseOnAction(boolean closeOnAction) {
        this.closeOnAction = closeOnAction;
    }

    public boolean isSaveOnAction() {
        return saveOnAction;
    }

    public void setSaveOnAction(boolean saveOnAction) {
        this.saveOnAction = saveOnAction;
    }

    private void openSearch(){
        SolaTask t = new SolaTask<Void, Void>() {

            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_DOCUMENTSEARCH));
                DocumentSearchForm form = new DocumentSearchForm(true, true);
                form.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (evt.getPropertyName().equals(DocumentSearchPanel.SELECT_SOURCE)) {
                            addDocument((SourceBean) evt.getNewValue());
                        }
                    }
                });
                DesktopApplication.getMainForm().getMainContentPanel().addPanel(form, getThis(), form.getId(), true);
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }
    
    private void addFromApplication() {
        if(applicationBean == null){
            return;
        }
        
        ApplicationDocumentsForm form = new ApplicationDocumentsForm(applicationBean, null, true);
        Utils.positionFormCentrally(form);
        form.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getPropertyName().equals(ApplicationDocumentsForm.SELECTED_SOURCE)){
                    addDocument((SourceBean)evt.getNewValue());
                }
            }
        });
        form.setVisible(true);
    }

    private void openSourceForm(final SourceBean source, final boolean allowEditing) {
        SolaTask t = new SolaTask<Void, Void>() {

            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_SOURCE_FORM));
                SourceBean sourceToOpen=null;
                if (source != null) {
                    sourceToOpen = source.copy();
                }
                DocumentForm form = new DocumentForm(sourceToOpen, saveOnAction, closeOnAction, allowEditing);
                form.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (evt.getPropertyName().equals(DocumentForm.DOCUMENT_SAVED)) {
                            addDocument((SourceBean) evt.getNewValue());
                        }
                    }
                });
                DesktopApplication.getMainForm().getMainContentPanel().addPanel(form, getThis(), form.getId(), true);
                return null;
            }
        };
        TaskManager.getInstance().runTask(t);
    }
    
    private DocumentsManagementExtPanel getThis(){
        return this;
    }
}
