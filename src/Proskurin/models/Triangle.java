package Proskurin.models;

import Proskurin.points.Point;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Triangle<P extends Point> extends Polygon {
    private P p1, p2, p3;

    public Triangle(P p1, P p2, P p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.lines = createLines();
    }

    private Line[] createLines() {
        lines = new Line[3];
        lines[0] = new Line<>(p1, p2);
        lines[1] = new Line<>(p2, p3);
        lines[2] = new Line<>(p3, p1);
        return lines;
    }

    public P getP1() {
        return p1;
    }

    public void setP1(P p1) {
        this.p1 = p1;
    }

    public P getP2() {
        return p2;
    }

    public void setP2(P p2) {
        this.p2 = p2;
    }

    public P getP3() {
        return p3;
    }

    public void setP3(P p3) {
        this.p3 = p3;
    }

    public P[] getPoints() {
        P[] arr = (P[]) Array.newInstance(p1.getClass(), 3);
        arr[0] = p1;
        arr[1] = p2;
        arr[2] = p3;
        return arr;
    }

    public Line<P>[] getLines() {
        return lines;
    }
}
