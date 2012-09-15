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
package org.sola.clients.swing.common.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

/**
 * Various useful functions
 */
public class Utils {
    public static void positionFormCentrally(Window form){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = ((dim.width) / 2);
        int y = ((dim.height) / 2);
        form.setLocation(x - (form.getWidth() / 2), y - (form.getHeight() / 2));
    }
}
