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
package org.sola.clients.swing.desktop;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JFrame;
import org.sola.clients.swing.common.LafManager;
import org.sola.clients.swing.common.LocalizationManager;
import org.sola.clients.swing.common.utils.Utils;
import org.sola.clients.swing.ui.DesktopClientExceptionHandler;
import org.sola.clients.swing.ui.security.LoginForm;
import org.sola.clients.swing.ui.security.LoginPanel;

/**
 * The main class of the application.
 */
public class DesktopApplication {

    private static MainForm mainForm;

    public static MainForm getMainForm() {
        return mainForm;
    }

    /**
     * Main method to run the application.
     *
     * @param args Array of input parameters.
     */
    public static void main(String[] args) {
        // Show splash screen
        SplashForm splash = new SplashForm();
        Utils.positionFormCentrally(splash);
        splash.setVisible(true);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        splash.setVisible(false);
        splash.dispose();

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Thread.setDefaultUncaughtExceptionHandler(new DesktopClientExceptionHandler());
                LocalizationManager.loadLanguage(DesktopApplication.class);
//                LogUtility.initialize(DesktopApplication.class);
//                try {
//                    UIManager.setLookAndFeel(
//                            UIManager.getSystemLookAndFeelClassName());
//                } catch (Exception ex){ }
                LafManager.getInstance().setProperties("green");

                final LoginForm loginForm = new LoginForm(DesktopApplication.class);
                loginForm.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (evt.getPropertyName().equals(LoginPanel.LOGIN_RESULT)) {
                            if (((Boolean) evt.getNewValue())) {
                                loginForm.dispose();
                                mainForm = new MainForm();
                                mainForm.setLocation(0, 0);
                                mainForm.setExtendedState(JFrame.MAXIMIZED_BOTH);
                                mainForm.setVisible(true);
                            }
                        }
                    }
                });
                Utils.positionFormCentrally(loginForm);
                loginForm.setVisible(true);
            }
        });
    }
}
