/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.mapaction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.geotools.swing.extended.Map;
import org.sola.clients.beans.cadastre.DatasetBean;
import org.sola.clients.swing.common.utils.Utils;
import org.sola.clients.swing.gis.ui.control.DatasetSelectionForm;
import org.sola.clients.swing.gis.ui.controlsbundle.SolaControlsBundle;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

public class DatasetSelectionButton extends ComponentShow{
    public final static String MAPACTION_NAME = "selectDataset";
    
    private SolaControlsBundle controlsBundle;
    
    public DatasetSelectionButton(Map mapObj, SolaControlsBundle controlsBundle) {
        super(mapObj, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.CADASTRE_DATASET_CHOOSE).getMessage(),
                "resources/datasets.png");
        this.controlsBundle = controlsBundle;
    }
    
    @Override
    public void onClick() {
        DatasetSelectionForm form = new DatasetSelectionForm(null, true);
        form.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getPropertyName().equals(DatasetSelectionForm.SELECTED_DATASET_PROPERTY)){
                    setDataset((DatasetBean)evt.getNewValue());
                }
            }
        });
        Utils.positionFormCentrally(form);
        form.setVisible(true);
    }    
    
    private void setDataset(DatasetBean dataset){
        if(dataset!=null && controlsBundle!=null){
            controlsBundle.setDataset(dataset);
        }
    }
}
