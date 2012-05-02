/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.mapaction;

/**
 *
 * @author ShresthaKabin
 */
public class BlankTool extends ComponentShow{
    public final static String MAPACTION_NAME = "";

    public BlankTool() {
        super(null, null, MAPACTION_NAME, "","");// "resources/VerticalBar.png");
    }
    
    public BlankTool(boolean verticalbar) {
        super(null, null, MAPACTION_NAME, "","resources/VerticalBar.png");
    }
    
    @Override
    public void onClick() {
        //Blank command.
    }
}
