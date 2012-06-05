/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geotools.swing.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.geotools.map.extended.layer.ExtendedLayer;

/**
 *
 * @author ShresthaKabin
 */
public class TreeNodePopupMenu implements ActionListener{
    private JPopupMenu propMenu=null;
    private ExtendedLayer mapLayer=null;

    public ExtendedLayer getMapLayer() {
        return mapLayer;
    }

    public void setMapLayer(ExtendedLayer mapLayer) {
        this.mapLayer = mapLayer;
    }

    public JPopupMenu getPropMenu() {
        if (propMenu==null){
            propMenu=getJPopupMenu();
        }
        return propMenu;
    }

    public void setPropMenu(JPopupMenu propMenu) {
        this.propMenu = propMenu;
    }
    
    public JPopupMenu getJPopupMenu(){
        JPopupMenu popupMenu=new JPopupMenu();
        
        JMenuItem attTable=new JMenuItem("Attribute Table");
        attTable.addActionListener(this);
        popupMenu.add(attTable);
        
        return popupMenu;
    }

    public void actionPerformed(ActionEvent e) {
        AttributeTableForm attTable=new AttributeTableForm(mapLayer);
        attTable.setVisible(true);
        attTable.setAlwaysOnTop(true);
    }
}
