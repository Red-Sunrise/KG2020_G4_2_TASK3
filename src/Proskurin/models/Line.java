package Proskurin.models;

import Proskurin.points.Point;
import Proskurin.points.RealPoint;

public class Line<P extends Point> {
    private P p1, p2;
    private double a, b, c;

    public Line(P p1, P p2) {
        this.p1 = p1;
        this.p2 = p2;
        lineEquation();
    }

    private double vectorMult(double ax, double ay, double bx, double by) {
        return ax*by-bx*ay;
    }

    public boolean isCrossing(Line<P> line) {
        Point p3 = line.p1, p4 = line.p2;

        double p1x = p1.getX().doubleValue();
        double p2x = p2.getX().doubleValue();
        double p3x = p3.getX().doubleValue();
        double p4x = p4.getX().doubleValue();

        double p1y = p1.getY().doubleValue();
        double p2y = p2.getY().doubleValue();
        double p3y = p3.getY().doubleValue();
        double p4y = p4.getY().doubleValue();

        double v1 = vectorMult(p4x - p3x, p4y - p3y, p1x - p3x, p1y - p3y);
        double v2 = vectorMult(p4x - p3x, p4y - p3y, p2x - p3x, p2y - p3y);
        double v3 = vectorMult(p2x - p1x, p2y - p1y, p3x - p1x, p3y - p1y);
        double v4 = vectorMult(p2x - p1x, p2y - p1y, p4x - p1x, p4y - p1y);
        return (v1 * v2) < 0 && (v3 * v4) < 0;
    }

    public void lineEquation() {
        double p1x = p1.getX().doubleValue();
        double p2x = p2.getX().doubleValue();

        double p1y = p1.getY().doubleValue();
        double p2y = p2.getY().doubleValue();

        a = p2y - p1y;
        b = p1x - p2x;
        c = -p1x * (p2y - p1y) + p1y * (p2x - p1x);
    }

    public RealPoint crossingPoint(Line<P> line) {
        double a2 = line.getA();
        double b2 = line.getB();
        double c2 = line.getC();
        double d = a * b2 - b * a2;
        double dx = -c * b2 + b * c2;
        double dy = -a * c2 + c * a2;
        return new RealPoint((dx/d), (dy/d));
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public P getP1() {
        return p1;
    }

    public void setP1(P p1) {
        lineEquation();
        this.p1 = p1;
    }

    public P getP2() {
        return p2;
    }

    public void setP2(P p2) {
        lineEquation();
        this.p2 = p2;
    }
}
