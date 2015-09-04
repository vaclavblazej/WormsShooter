package spacks.communication.utilities;

import java.io.Serializable;

/**
 *
 * @author Vaclav Blazej
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
