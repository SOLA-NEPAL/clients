/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.ui;

import javax.swing.JPanel;

/**
 * Used to pass parameters to the {@link WizardButtonsListener}
 */
public class WizardButtonsEvent {

    public enum BUTTON_PRESSED {
        NEXT, BACK, FINISH
    };
    
    private JPanel currentCard;
    private boolean stopProcessing;
    private BUTTON_PRESSED buttonPressed;

    public WizardButtonsEvent(JPanel currentCard, BUTTON_PRESSED buttonPressed) {
        this.currentCard = currentCard;
        this.buttonPressed = buttonPressed;
    }

    public JPanel getCurrentCard() {
        return currentCard;
    }

    public BUTTON_PRESSED getButtonPressed() {
        return buttonPressed;
    }

    public boolean isStopProcessing() {
        return stopProcessing;
    }

    public void setStopProcessing(boolean stopProcessing) {
        this.stopProcessing = stopProcessing;
    }
}
