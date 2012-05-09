/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.dlisdesktopclasses;

/**
 *
 * @author Dinesh
 */
public class ComboItem {
    int value;
    String label;

    public ComboItem(int id, String label){
        this.value = id;
        this.label = label;
    }

    public int getValue(){
        return value;
    }

    @Override
    public String toString(){
        return label;
    }
}
