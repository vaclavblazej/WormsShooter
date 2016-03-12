package cz.spacks.worms.model.structures;

import java.io.Serializable;
import java.util.HashMap;

public class Map2D<E> implements Serializable {

    private HashMap<Integer, HashMap<Integer, E>> map;

    public Map2D() {
        map = new HashMap<>();
    }

    public boolean put(int x, int y, E elem) {
        if (map.containsKey(x)) {
            if (map.get(x).containsKey(y)) {
                return false;
            } else {
                map.get(x).put(y, elem);
            }
        } else {
            final HashMap<Integer, E> value = new HashMap<>();
            value.put(y, elem);
            map.put(x, value);
        }
        return true;
    }

    public E get(int x, int y) {
        final HashMap<Integer, E> ires = map.get(x);
        return (ires == null) ? null : ires.get(y);
    }

    public E remove(int x, int y) {
        final HashMap<Integer, E> res = map.get(x);
        if (res != null) {
            final E e = res.get(y);
            if (e != null) {
                res.remove(y);
                return e;
            }
        }
        return null;
    }

}
