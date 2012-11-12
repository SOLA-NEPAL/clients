package org.geotools.swing.control;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;
import org.geotools.swing.extended.Map;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.referencedata.VdcBean;
import org.sola.common.StringUtility;

/**
 * Shows dataset and VDC name
 */
public class DatasetNameItem extends JPanel {

    private Map mapPane;
    private PropertyChangeListener mapListener;

    public DatasetNameItem() {
        this(null);
    }

    public DatasetNameItem(Map mapPane) {
        this.mapPane = mapPane;
        initComponents();
        postInit();
    }

    private void postInit() {
        setBackground(new Color(224, 224, 224));
        mapListener = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(Map.DATASET_CHANGED_PROPERTY)) {
                    setDatasetLabel();
                }
            }
        };

        bindMapListener();
    }

    private void bindMapListener() {
        if (mapPane != null) {
            mapPane.addPropertyChangeListener(mapListener);
        }
        setDatasetLabel();
    }

    public void setMapPane(Map mapPane) {
        if (this.mapPane!=null) {
            this.mapPane.removePropertyChangeListener(mapListener);
        }
        this.mapPane = mapPane;
        bindMapListener();
    }

    private void setDatasetLabel() {
        if (mapPane != null && mapPane.getDataset() != null) {
            String lblText = StringUtility.empty(mapPane.getDataset().getName());
            if (mapPane.getDataset().getVdcCode() != null) {
                VdcBean vdc = CacheManager.getVdc(mapPane.getDataset().getVdcCode());
                if(vdc!=null){
                    lblText = lblText + ", " + StringUtility.empty(vdc.getDisplayValue());
                }
            }
            lblDatasetName.setText(lblText);
        } else {
            lblDatasetName.setText("");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblDatasetName = new javax.swing.JLabel();

        lblDatasetName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/geotools/swing/control/Bundle"); // NOI18N
        lblDatasetName.setText(bundle.getString("DatasetNameItem.lblDatasetName.text")); // NOI18N
        lblDatasetName.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblDatasetName, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDatasetName, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblDatasetName;
    // End of variables declaration//GEN-END:variables
}
