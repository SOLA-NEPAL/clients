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
package org.sola.clients.swing.common.converters;

import org.jdesktop.beansbinding.Converter;
import org.sola.common.NepaliIntegersConvertor;

/** Converts from integer into nepali date string and back.*/
public class FormattedNapaliDateConverter extends Converter<Integer, String> {

    @Override
    public String convertForward(Integer value) {
        if(value == null){
            return null;
        } 
        String stringValue = NepaliIntegersConvertor.getLocalizedValue(value.toString());
        
        if (stringValue.length()==8) {
            stringValue = stringValue.substring(0,4) + "/" + stringValue.substring(4,6) + "/" + stringValue.substring(6);
        } 
        return stringValue;
    }

    @Override
    public Integer convertReverse(String value) {
        Integer integerValue = null;
        
        if(value!=null){
            integerValue = NepaliIntegersConvertor.toInteger(value.replace("/", ""));
        }
        return integerValue;
    }
}
