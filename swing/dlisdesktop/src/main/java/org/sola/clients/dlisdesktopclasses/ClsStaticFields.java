/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.dlisdesktopclasses;

import java.util.*;

/**
 *
 * @author KumarKhadka
 */
public class ClsStaticFields {

    public static String selectedZoneName = "Gandaki";
    public static int selectedZoneCode = 0101;
    public static String selectedDistrictName = "Kaski";
    public static int selectedDistrictCode = 001;
    public static String selectedLMOName = "Land Management office Kaski";
    public static int selectedLMOCode = 001;
    public static List<ComboItem> selectedVdc = new ArrayList();
    public static int seletedVdcCode;

    //for temp use
    public static List<ComboItem> getSeletedVdc() {
        selectedVdc.removeAll(selectedVdc);
        selectedVdc.add(new ComboItem(1, "Rupakot"));
        selectedVdc.add(new ComboItem(2, "Kathmandu"));
        return selectedVdc;
    }

    //for temp use
    public static int getSeletedVdcCode(String vdcName) {
        if (vdcName.equals("Rupakot")) {
            return 001;
        } else {
            return 002;
        }

    }

    //If has table/map is used
    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        Set<T> keys = new HashSet<T>();
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
//    For real use
//    public static List<String> getSeletedVdcName() {
//        return seletedVdcName;
//    }
//
//    public static void setSeletedVdcName(List<String> seletedVdcName) {
//        for(String s:seletedVdcName){
//            seletedVdcName.add(s);
//        }     
//        ClsStaticFields.seletedVdcName = seletedVdcName;
//    }
}
