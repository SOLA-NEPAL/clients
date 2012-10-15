package org.sola.clients.swing.common.controls;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import org.sola.common.NepaliIntegersConvertor;

/**
 *
 * @author Dinesh
 */
public class JTextBoxWithAutoLocalizationOfNumber extends JTextField {

    public String getTextInEnglish() {
        return NepaliIntegersConvertor.toStringInteger(this.getText());
        //return textInEnglish;
    }

    public String getTextInNepali() {
        return NepaliIntegersConvertor.toNepaliInteger(this.getText());
    }
    //This method uses DoubleDocument to handle the textfield 

    @Override
    protected Document createDefaultModel() {
        return new DoubleDocument();
    }

    static class DoubleDocument extends PlainDocument {
        //insertString Method uses the information from the text field to know what is in it and whethor not to except it

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            //if str does is null do nothing
            if (str == null) {
                return;
            }           
            str = NepaliIntegersConvertor.getLocalizedValue(str);
            super.insertString(offs, str, a);
        }
    }
}
