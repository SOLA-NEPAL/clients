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
package org.sola.clients.swing.common.converters;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.text.*;
import org.sola.common.NepaliIntegersConvertor;

/**
 * Provides formatters for {@link JFormattedTextField}
 */
public class FormattersFactory {

    private DefaultFormatterFactory decimalFormatterFactory;
    private DefaultFormatterFactory integerFormatterFactory;
    private DefaultFormatterFactory shortFormatterFactory;
    private DefaultFormatterFactory dateFormatterFactory;

    private FormattersFactory() {
    }

    public static FormattersFactory getInstance() {
        return FormattersFactoryHolder.INSTANCE;
    }

    private static class FormattersFactoryHolder {

        private static final FormattersFactory INSTANCE = new FormattersFactory();
    }

    public DefaultFormatterFactory getDecimalFormatterFactory() {
        if (decimalFormatterFactory == null) {
            DefaultFormatter fmt = new NumberFormatter(new DecimalFormat("#.####"));
            fmt.setValueClass(BigDecimal.class);
            decimalFormatterFactory = new DefaultFormatterFactory(fmt, fmt, fmt);
        }
        return decimalFormatterFactory;
    }

    public DefaultFormatterFactory getIntegerFormatterFactory() {
        if (integerFormatterFactory == null) {
            DefaultFormatter fmt = new NumberFormatter(NumberFormat.getIntegerInstance());
            fmt.setValueClass(Integer.class);
            integerFormatterFactory = new DefaultFormatterFactory(fmt, fmt, fmt);
        }
        return integerFormatterFactory;
    }

    public DefaultFormatterFactory getShortFormatterFactory() {
        if (shortFormatterFactory == null) {
            DefaultFormatter fmt = new NumberFormatter();
            fmt.setValueClass(Short.class);
            shortFormatterFactory = new DefaultFormatterFactory(fmt, fmt, fmt);
        }
        return shortFormatterFactory;
    }

    public DefaultFormatterFactory getDateFormatterFactory() {
        DateFormatter displayFormat = new DateFormatter(DateFormat.getDateInstance(DateFormat.MEDIUM));
        DateFormatter editFormat = new DateFormatter(DateFormat.getDateInstance(DateFormat.SHORT)) {
            // Accept null or emtpy string values entered by the user as null.

            @Override
            public Object stringToValue(String userInput) throws ParseException {
                Object result = null;
                if (userInput != null && !userInput.trim().isEmpty()) {
                    result = super.stringToValue(userInput);
                }
                return result;
            }
        };

        return new DefaultFormatterFactory(displayFormat, displayFormat, editFormat);
    }

    public DefaultFormatterFactory getNepaliDateFormatterFactory() {
        MaskFormatter mf;
        mf = new MaskFormatter() {

            @Override
            public Object stringToValue(String userInput) throws ParseException {
                Object result = null;
                if (userInput != null && !userInput.trim().isEmpty()) {
                    String tmp = userInput.replace("_", "").replace("/", "");
                    if (!tmp.isEmpty()) {
                        result = super.stringToValue(NepaliIntegersConvertor.toStringInteger(userInput, false));
                    }
                }
                return result;
            }

            @Override
            public String valueToString(Object value) throws ParseException {
                String valueString = value == null ? null : value.toString();
                return super.valueToString(NepaliIntegersConvertor.toNepaliInteger(valueString, false));
            }
        };

        mf.setValidCharacters("1234567890१२३४५६७८९०");

        try {
            mf.setMask("AAAA/AA/AA");
        } catch (ParseException ex) {
            Logger.getLogger(FormattersFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        mf.setPlaceholderCharacter('_');
        mf.setValueContainsLiteralCharacters(false);
        mf.setCommitsOnValidEdit(true);
        return new DefaultFormatterFactory(mf, mf, mf);
    }

    public DefaultFormatterFactory getIntegerStringFormatterFactory() {
        MaskFormatter mf = new MaskFormatter() {

            @Override
            public Object stringToValue(String userInput) throws ParseException {
                Object result = null;
                if (userInput != null && !userInput.trim().isEmpty()) {
                    result = super.stringToValue(NepaliIntegersConvertor.toStringInteger(userInput, false));
                }
                return result;
            }

            @Override
            public String valueToString(Object value) throws ParseException {
                String valueString = value == null ? null : value.toString();
                return super.valueToString(NepaliIntegersConvertor.toStringInteger(valueString, false));
            }
        };

        mf.setValidCharacters("1234567890१२३४५६७८९०");
        
        try {
            mf.setMask("AAAAAAAAAA");
        } catch (ParseException ex) {
            Logger.getLogger(FormattersFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        mf.setValueContainsLiteralCharacters(false);
        mf.setCommitsOnValidEdit(true);
        return new DefaultFormatterFactory(mf, mf, mf);
    }
}
