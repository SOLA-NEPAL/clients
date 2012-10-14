/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.ui.renderers;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.sola.common.DateUtility;
import org.sola.common.NepaliIntegersConvertor;

/**
 * Formats table cell, containing nepali date. Displays date in nepali numbers
 * using yyyy/mm/dd format.
 */
public class NepaliDateCellRenderer extends DefaultTableCellRenderer {

    public NepaliDateCellRenderer() {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value != null) {
            label.setText(DateUtility.toFormattedNepaliDate(value.toString()));
            return this;
        }

        return this;
    }
}
