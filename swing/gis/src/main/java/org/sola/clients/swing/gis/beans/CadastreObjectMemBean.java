/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.beans;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ShresthaKabin
 */
public class CadastreObjectMemBean {
    private List<CadastreObjectBean> cadastreObjectList = new ArrayList<CadastreObjectBean>();

    public List<CadastreObjectBean> getCadastreObjectList() {
        return cadastreObjectList;
    }

    public void setCadastreObjectList(List<CadastreObjectBean> cadastreObjectList) {
        this.cadastreObjectList = cadastreObjectList;
    }
}
