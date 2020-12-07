package Proskurin.utils;

import Proskurin.models.Line;
import Proskurin.models.RealPolygon;
import Proskurin.models.Triangle;
import Proskurin.points.RealPoint;

import java.util.ArrayList;
import java.util.List;

public class Logic {
    public static RealPolygon<RealPoint> makePolygon(Triangle<RealPoint> tr1, Triangle<RealPoint> tr2) {
        List<RealPoint> crossPoints = new ArrayList<>(); // находим точки пересечения треугольников
        for (Line<RealPoint> line1 : tr1.getLines()) {
            for (Line<RealPoint> line2 : tr2.getLines()) {
                if (line1.isCrossing(line2)) {
                    line1.lineEquation();
                    line2.lineEquation();
                    crossPoints.add(line1.crossingPoint(line2));
                }
            }
        }

        for (RealPoint point : tr2.getPoints()) { // находим точки второго треугольника лежащие в первом
            if (tr1.isInPolygon(point, new RealPoint(point.getX(), 100000))) {
                crossPoints.add(point);
            }
        }

        for (RealPoint point : tr1.getPoints()) { // находим точки первого треугольника лежащие во втором
            if (tr2.isInPolygon(point, new RealPoint(point.getX(), 100000))) {
                crossPoints.add(point);
            }
        }

        if (crossPoints.isEmpty()) {
            return new RealPolygon<>(new RealPoint[0]);
        }
        return new RealPolygon<>(normalizePolygon(crossPoints));
    }

    private static RealPoint[] normalizePolygon(List<RealPoint> points) {
        RealPoint upperPoint = points.get(0);
        for (RealPoint point : points) {
            if (point.getY() < upperPoint.getY()) {
                upperPoint = point;
            }
        }
        points.remove(upperPoint);
        RealPoint nextPoint = null;

        RealPoint[] resPointArr = new RealPoint[points.size() + 1];
        resPointArr[0] = upperPoint;
        int i = 1;

        while (points.size() > 1){
            double angle = 361;
            for (RealPoint point : points) {
                double dy = point.getY() - upperPoint.getY();
                double dx = point.getX() - upperPoint.getX();
                double tempAngle = Math.atan(dy / dx);
                if (dy < 0 && dx < 0) {
                    tempAngle += Math.PI;
                }
                if (dy < 0 && dx > 0) {
                    tempAngle = 2*Math.PI - tempAngle;
                }
                if (dy > 0 && dx < 0) {
                    tempAngle = Math.PI + tempAngle;
                }
                if (tempAngle < angle) {
                    angle = tempAngle;
                    nextPoint = point;
                }
            }
            points.remove(nextPoint);
            resPointArr[i] = nextPoint;
            i++;
            upperPoint = nextPoint;
        }
        resPointArr[i] = points.get(0);
        for (RealPoint realPoint : resPointArr) {
            System.out.println("x = " + realPoint.getX() + "  y = " + realPoint.getY());
        }
        System.out.println();
        return resPointArr;
    }
}
