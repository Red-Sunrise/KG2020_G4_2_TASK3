package Proskurin.models;

import Proskurin.points.Point;

public abstract class Polygon<P extends Point> {
    protected Line<P>[] lines;

    abstract Line<P>[] getLines();

    abstract P[] getPoints();

    public boolean isInPolygon(P point, P rayEnd) {
        boolean flag = false;
        for (Line<P> line : lines) {
            if (line.isCrossing(new Line<>(point, rayEnd))) {
                flag = !flag;
            }
        }
        return flag;
    }
}
