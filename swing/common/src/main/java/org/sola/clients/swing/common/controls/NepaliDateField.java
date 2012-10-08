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

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import org.sola.clients.swing.common.converters.FormattersFactory;

/**
 * Allows entering dates in the format of
 * <code>AAAA/AA/AA</code>, using arabic and nepali numbers.
 */
public class NepaliDateField extends JFormattedTextField {

    MaskFormatter mf;

    public NepaliDateField() {
        super();
        this.setFormatterFactory(FormattersFactory.getInstance().getNepaliDateFormatterFactory());
    }
}