package Proskurin.models;

import Proskurin.points.Point;

public class RealPolygon<P extends Point> extends Polygon {
    private P[] points;

    public RealPolygon(P[] points) {
        this.points = points;
        this.lines = createLines();
    }

    private Line[] createLines() {
        lines = new Line[points.length];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new Line<>(points[i], points[i + 1 == lines.length ? 0 : i + 1]);
        }
        return lines;
    }

    public P[] getPoints() {
        return points;
    }

    public Line<P>[] getLines() {
        return lines;
    }
}
