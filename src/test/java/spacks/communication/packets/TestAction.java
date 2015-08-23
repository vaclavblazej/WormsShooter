package spacks.communication.packets;

import spacks.communication.utilities.SAction;

/**
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public class TestAction implements SAction {

    public final String ident;

    public TestAction(String ident) {
        this.ident = ident;
    }

    public String getIdent(){
        return ident;
    }

    @Override
    public void perform() {
    }
}
