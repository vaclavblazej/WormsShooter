package utilities.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import server.Performable;
import utilities.AbstractView;

/**
 *
 * @author Skarab
 */
public class Packet implements Serializable, Performable {

    private Action action;
    private int count;
    private int id;
    private ArrayList<Object> information;

    public Packet() {
        this(null, 0);
    }

    public Packet(Action action, int id) {
        this.action = action;
        this.id = id;
        this.information = new ArrayList<>();
    }

    public Packet setCount(int count) {
        this.count = count;
        return this;
    }

    public Packet setId(int id) {
        this.id = id;
        return this;
    }

    public Action getAction() {
        return action;
    }

    public int getCount() {
        return count;
    }

    public int getId() {
        return id;
    }

    public void addInfo(Object object) {
        information.add(object);
    }

    public Object get(int index) {
        return information.get(index);
    }

    @Override
    public void perform(Packet packet, AbstractView view) {
    }

    @Override
    public void performServer(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
    }

    @Override
    public void performClient(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
    }
}
