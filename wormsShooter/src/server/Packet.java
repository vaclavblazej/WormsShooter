package server;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import utilities.communication.Action;

/**
 *
 * @author Skarab
 */
public class Packet implements Serializable {

    private static final double PRECISION = 1000;
    private Action action;
    private int count;
    private int id;
    private ArrayList<Point> points;
    private ArrayList<Point> doublePoints;
    private boolean bool;

    public Packet(Action action, int id) {
        this.action = action;
        this.id = id;
        this.points = new ArrayList<>();
        this.doublePoints = new ArrayList<>();
    }

    public void setCount(int count) {
        this.count = count;
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

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public boolean getBool() {
        return bool;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public Point getPoint(int index) {
        return points.get(index);
    }

    public void addDoublePoint(Point.Double point) {
        // Point.Double does not serialize properly
        Point p = new Point((int) (point.x * PRECISION), (int) (point.y * PRECISION));
        doublePoints.add(p);
    }

    public Point.Double getDoublePoint(int index) {
        Point g = doublePoints.get(index);
        Point.Double p = new Point.Double(g.x / PRECISION, g.y / PRECISION);
        return p;
    }
}
