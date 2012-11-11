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
package org.sola.clients.swing.common.controls;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.event.MouseInputAdapter;

public class TableRowResizer extends MouseInputAdapter{ 
    public static Cursor resizeCursor = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR); 
 
    private boolean resizing = false;
    private int mouseYOffset, resizingRow; 
    private Cursor otherCursor = resizeCursor; 
    private JTable table; 
 
    public TableRowResizer(JTable table){ 
        this.table = table; 
        table.addMouseListener(this); 
        table.addMouseMotionListener(this); 
    } 
 
    private int getResizingRow(Point p){ 
        return getResizingRow(p, table.rowAtPoint(p)); 
    } 
 
    private int getResizingRow(Point p, int row){ 
        if(row == -1){ 
            return -1; 
        } 
        int col = table.columnAtPoint(p); 
        if(col==-1) 
            return -1; 
        Rectangle r = table.getCellRect(row, col, true); 
        r.grow(0, -3); 
        if(r.contains(p)) 
            return -1; 
 
        int midPoint = r.y + r.height / 2; 
        int rowIndex = (p.y < midPoint) ? row - 1 : row; 
 
        return rowIndex; 
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        resizing = false;
    }
 
    @Override
    public void mousePressed(MouseEvent e){ 
        resizing = true;
        Point p = e.getPoint(); 
 
        resizingRow = getResizingRow(p); 
        mouseYOffset = p.y - table.getRowHeight(resizingRow); 
    } 
 
    private void swapCursor(){ 
        Cursor tmp = table.getCursor(); 
        table.setCursor(otherCursor); 
        otherCursor = tmp; 
    } 
 
    @Override
    public void mouseMoved(MouseEvent e){ 
        if((getResizingRow(e.getPoint())>=0) 
           != (table.getCursor() == resizeCursor)){ 
            swapCursor(); 
        } 
    } 
 
    @Override
    public void mouseDragged(MouseEvent e){ 
        int mouseY = e.getY(); 
 
        if(resizingRow >= 0){ 
            int newHeight = mouseY - mouseYOffset;
            if(newHeight > 5){
                table.setRowHeight(resizingRow, newHeight); 
            }
        } 
    }

    public boolean isResizing() {
        return resizing;
    }
} 
