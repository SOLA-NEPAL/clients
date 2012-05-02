/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis;

import java.util.LinkedHashMap;
import javax.swing.JCheckBox;
import javax.swing.tree.DefaultTreeModel;
import org.geotools.map.extended.layer.ExtendedLayer;
import org.geotools.swing.control.extended.TocLayerNode;
import org.geotools.swing.extended.Map;

/**
 *
 * @author ShresthaKabin
 */
public class PublicMethod {

    //Make all layer off excepts target layers.
    public static void maplayerOnOff(Map mapObj, boolean showOtherLayers) {
        //Set layer tick status in TOC.
        DefaultTreeModel treeModel = (DefaultTreeModel) mapObj.getToc().getTreeModel();
        Object parent = treeModel.getRoot();
        //Assuming the layer name is "target" (not the title)
        for (int i = 0; i < treeModel.getChildCount(parent); i++) {
            TocLayerNode tocNode = (TocLayerNode) treeModel.getChild(parent, i);
            
            ExtendedLayer layer=tocNode.getLayer();
            String layerName= layer.getLayerName();
            if (layerName.contains("Target")) continue;
            
            //Based on the situation trigger the tree node click events.
            boolean isLayerVisible=layer.isVisible();
            if (showOtherLayers){
                if (!isLayerVisible) mapObj.getToc().changeNodeSwitch(tocNode);
            }
            else{
                if (isLayerVisible) mapObj.getToc().changeNodeSwitch(tocNode);
            }
        }
    }

    public static void Tmp_maplayerOnOff(Map mapObj, boolean showOtherLayers) {
        LinkedHashMap<String, ExtendedLayer> lays = mapObj.getSolaLayers();
        //Set visibility status of map layers.
        for (ExtendedLayer lay : lays.values()) {
            //System.out.println(lay.getTitle());
            if (lay.getTitle().contains("Target")) {
                continue;
            }
            lay.setVisible(showOtherLayers);
            //lay.setShowInToc(showTargetOnly);
        }
        //Set layer tick status in TOC.
        DefaultTreeModel treeModel = (DefaultTreeModel) mapObj.getToc().getTreeModel();
        Object parent = treeModel.getRoot();
        for (int i = 0; i < treeModel.getChildCount(parent); i++) {
            TocLayerNode node = (TocLayerNode) treeModel.getChild(parent, i);
            //System.out.println(node.toString()); //Print title for the node.
            if (node.toString().contains("Target")) {
                continue;
            }
            JCheckBox checkbox = node.getVisualisationComponent();
            checkbox.setSelected(showOtherLayers);
        }
        mapObj.getToc().repaint();
        mapObj.refresh();
    }
}
