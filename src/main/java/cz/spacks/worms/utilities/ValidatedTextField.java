package cz.spacks.worms.utilities;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 */
public class ValidatedTextField extends JTextField {

    public ValidatedTextField(final Validator validator) {
        this(validator, "");
    }

    public ValidatedTextField(final Validator validator, String init) {
        this(validator, init, 10);
    }

    public ValidatedTextField(final Validator validator, String init, int cols) {
        super(init, cols);
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                validator.validateDialog();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                validator.validateDialog();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                validator.validateDialog();
            }
        });
    }
}