package utilities;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Skarab
 */
public class ValidatedTF extends JTextField {

    public ValidatedTF(final Validator validator) {
        this(validator, "");
    }

    public ValidatedTF(final Validator validator, String init) {
        this(validator, init, 10);
    }

    public ValidatedTF(final Validator validator, String init, int cols) {
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