package org.sola.clients.swing.ui;

import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.JPanel;

/**
 * Used to create simple wizard forms. JPanel with CardLayout should be
 * provided.
 */
public final class WizardButtons extends javax.swing.JPanel {

    public static final String BUTTON_BEFORE_CLICK = "button_BeforeClick";
    public static final String BUTTON_AFTER_CLICK = "button_AfterClick";
    private JPanel wizardPanel;
    private ArrayList<WizardButtonsListener> wizardButtonsListeners;
    private ResourceBundle resourceBundle;

    /**
     * Default constructor
     */
    public WizardButtons() {
        this(null);
    }

    /**
     * Creates component and bind to the provided wizard panel.
     */
    public WizardButtons(JPanel wizardPanel) {
        this.wizardPanel = wizardPanel;
        wizardButtonsListeners = new ArrayList<WizardButtonsListener>();
        initComponents();
        resourceBundle = ResourceBundle.getBundle("org/sola/clients/swing/ui/Bundle");
        setWizardPanel(wizardPanel);
    }

    /**
     * Returns wizard instance.
     */
    public JPanel getWizardPanel() {
        return wizardPanel;
    }

    /**
     * Sets wizard panel to use.
     */
    public void setWizardPanel(JPanel wizardPanel) {
        this.wizardPanel = wizardPanel;
        customizeButtons();
    }

    private boolean checkWizard() {
        if (wizardPanel == null || !wizardPanel.getLayout().getClass().isAssignableFrom(CardLayout.class)
                || wizardPanel.getComponentCount() < 1) {
            return false;
        }
        return true;
    }

    private boolean isLastCard() {
        if (!checkWizard()) {
            return false;
        }
        for (int i = 0; i < wizardPanel.getComponentCount(); i++) {
            if (wizardPanel.getComponent(i).isShowing()) {
                if (i == wizardPanel.getComponentCount() - 1) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return wizardPanel.getComponentCount() == 1;
    }

    private boolean isAnyCardIsShown() {
        for (int i = 0; i < wizardPanel.getComponentCount(); i++) {
            if (wizardPanel.getComponent(i).isShowing()) {
                return true;
            }
        }
        return false;
    }

    private boolean isFirstCard() {
        if (!checkWizard()) {
            return false;
        }
        if (wizardPanel.getComponent(0).isShowing()) {
            return true;
        } else {
            return !isAnyCardIsShown();
        }
    }

    /**
     * Flips next screen.
     */
    public void showNext() {
        if (!isLastCard()) {
            ((CardLayout) wizardPanel.getLayout()).next(wizardPanel);
        }
    }

    /**
     * Flips previous screen.
     */
    public void showPrevious() {
        if (!isFirstCard()) {
            ((CardLayout) wizardPanel.getLayout()).previous(wizardPanel);
        }
    }

    /**
     * Returns enable state of the Next button.
     */
    public boolean isNextButtonEnabled() {
        return btnNext.isEnabled();
    }

    /**
     * Enabling/disabling Next button.
     */
    public void setNextButtonEnabled(boolean enabled) {
        btnNext.setEnabled(enabled);
    }

    /**
     * Returns enable state of the Back button.
     */
    public boolean isBackButtonEnabled() {
        return btnBack.isEnabled();
    }

    /**
     * Enabling/disabling Back button.
     */
    public void setBackButtonEnabled(boolean enabled) {
        btnBack.setEnabled(enabled);
    }

    /**
     * Adds WizardButtonsListener
     */
    public void addWizardButtonsListener(WizardButtonsListener listener) {
        if (!wizardButtonsListeners.contains(listener)) {
            wizardButtonsListeners.add(listener);
        }
    }

    /**
     * Removes WizardButtonsListener
     */
    public void removeWizardButtonsListener(WizardButtonsListener listener) {
        wizardButtonsListeners.remove(listener);
    }

    /**
     * Returns currently shown card.
     */
    public JPanel getCurrentCard() {
        if (!checkWizard()) {
            return null;
        }
        for (int i = 0; i < wizardPanel.getComponentCount(); i++) {
            if (wizardPanel.getComponent(i).isShowing()) {
                return (JPanel) wizardPanel.getComponent(i);
            }
        }
        return (JPanel) wizardPanel.getComponent(0);
    }

    private void customizeButtons() {
        if (checkWizard()) {
            if (isLastCard()) {
                btnNext.setText(resourceBundle.getString("WizardButtons.btnFinish.text"));
            } else {
                btnNext.setText(resourceBundle.getString("WizardButtons.btnNext.text"));
            }
            btnNext.setEnabled(true);
            btnBack.setEnabled(!isFirstCard());
        } else {
            btnNext.setEnabled(false);
            btnBack.setEnabled(false);
        }
    }

    private void buttonPressed(WizardButtonsEvent.BUTTON_PRESSED buttonPressed) {
        if (!checkWizard()) {
            return;
        }

        for (WizardButtonsListener listener : wizardButtonsListeners) {
            WizardButtonsEvent evt = new WizardButtonsEvent(getCurrentCard(), buttonPressed);
            listener.buttonPressed(evt);
            if (!evt.isStopProcessing()) {
                if (buttonPressed == WizardButtonsEvent.BUTTON_PRESSED.NEXT) {
                    ((CardLayout) wizardPanel.getLayout()).next(wizardPanel);
                } else if (buttonPressed == WizardButtonsEvent.BUTTON_PRESSED.BACK) {
                    ((CardLayout) wizardPanel.getLayout()).previous(wizardPanel);
                }
            }
        }
        customizeButtons();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnNext = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/ui/Bundle"); // NOI18N
        btnNext.setText(bundle.getString("WizardButtons.btnNext.text")); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnBack.setText(bundle.getString("WizardButtons.btnBack.text")); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 187, Short.MAX_VALUE)
                .addComponent(btnBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnNext)
                .addComponent(btnBack))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        buttonPressed(WizardButtonsEvent.BUTTON_PRESSED.BACK);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (isLastCard()) {
            buttonPressed(WizardButtonsEvent.BUTTON_PRESSED.FINISH);
        } else {
            buttonPressed(WizardButtonsEvent.BUTTON_PRESSED.NEXT);
        }
    }//GEN-LAST:event_btnNextActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnNext;
    // End of variables declaration//GEN-END:variables
}
