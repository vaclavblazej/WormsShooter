package communication.frontend;

import communication.backend.utilities.SRegistrationForm;
import java.io.Serializable;

/**
 *
 * @author Václav Blažej
 */
public class SActionConnect implements Serializable {

    private SRegistrationForm form;

    public SActionConnect(SRegistrationForm form) {
        this.form = form;
    }

    public SRegistrationForm getForm() {
        return form;
    }
}
