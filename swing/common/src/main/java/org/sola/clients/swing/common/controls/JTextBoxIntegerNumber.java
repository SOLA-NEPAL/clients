package org.sola.clients.swing.common.controls;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import org.sola.common.NepaliIntegersConvertor;

/**
 * Controls user input allowing only arabic integer numbers.
 */
public class JTextBoxIntegerNumber extends JTextField {

    private class IntegerFiler extends DocumentFilter {

        public IntegerFiler() {
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string,
                AttributeSet attr) throws BadLocationException {
            fb.insertString(offset, NepaliIntegersConvertor.toStringInteger(string), attr);
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset,
                int length, String text, AttributeSet attrs) throws BadLocationException {
            super.replace(fb, offset, length, NepaliIntegersConvertor.toStringInteger(text), attrs);
        }
    }

    public JTextBoxIntegerNumber() {
        super();
        ((AbstractDocument) getDocument()).setDocumentFilter(new IntegerFiler());
    }
}
