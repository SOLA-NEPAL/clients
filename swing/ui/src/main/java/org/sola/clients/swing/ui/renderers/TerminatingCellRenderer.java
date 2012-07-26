/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.ui.renderers;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Solovov
 */
public class TerminatingCellRenderer extends DefaultTableCellRenderer {

    private ImageIcon imageCancel;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);

        if (value != null) {
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);

            try {
                Boolean realValue = (Boolean) value;
                // Load images
                if (imageCancel == null) {
                    imageCancel = new ImageIcon(AttachedDocumentCellRenderer.class.getResource(
                            "/images/common/cancel.png"));
                }
                if(realValue){
                    label.setIcon(imageCancel);
                }else{
                    label.setIcon(null);
                }
            } catch (Exception ex) {
            }
        }

        label.setText(null);
        return this;
    }
}

