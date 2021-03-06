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
package org.sola.clients.swing.ui.renderers;

import java.awt.Component;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.sola.clients.swing.common.controls.TableRowResizer;

/**
 * Renders table cell containing List object of the list.
 */
public class TableCellListRenderer extends JTextArea implements TableCellRenderer {

    private final DefaultTableCellRenderer adaptee = new DefaultTableCellRenderer();
    private String[] methods;

    public TableCellListRenderer(String... methods) {
        this.methods = methods;
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        adaptee.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setForeground(adaptee.getForeground());
        setBackground(adaptee.getBackground());
        setBorder(adaptee.getBorder());
        setFont(adaptee.getFont());
        setText(adaptee.getText());

        try {
            if (value != null && Iterable.class.isAssignableFrom(value.getClass())) {
                String out = "";
                List list = (List) value;

                for (Object object : list) {
                    String record = "";
                    if (out.length() > 0) {
                        out += "\n";
                    }
                    if (methods == null || methods.length < 1) {
                        record = object.toString();
                    } else {
                        for (String methodName : methods) {
                            Method method = object.getClass().getMethod(methodName);
                            Object stringValue = method.invoke(object);
                            if (stringValue != null) {
                                record += stringValue.toString() + " ";
                            }
                        }
                    }
                    out += String.format("- %s;", record.trim());
                }

                setText(out);
                setToolTipText(out);

            } else {
                setText("");
                setToolTipText("");
            }

            TableColumnModel columnModel = table.getColumnModel();
            setSize(columnModel.getColumn(column).getWidth(), 100000);
            int prefHeight = (int) getPreferredSize().getHeight();

            if(prefHeight > table.getRowHeight(row)){
                TableRowResizer resizer = null;
                if(table.getMouseListeners().length > 0){
                    for (int i = 0; i < table.getMouseListeners().length; i++) {
                        if(table.getMouseListeners()[i] instanceof TableRowResizer){
                            resizer = (TableRowResizer)table.getMouseListeners()[i];
                        }
                    }
                }
                
                if(resizer==null || !resizer.isResizing()){
                    table.setRowHeight(row, prefHeight);
                }
            }

        } catch (Exception e) {
            setText("");
            setToolTipText("");
        }

        return this;
    }
}
