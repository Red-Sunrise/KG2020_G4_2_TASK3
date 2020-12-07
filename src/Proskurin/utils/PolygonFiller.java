package Proskurin.utils;


import Proskurin.ScreenConvertor;
import Proskurin.models.RealPolygon;
import Proskurin.pixeldrawers.PixelDrawer;
import Proskurin.points.Point;
import Proskurin.points.RealPoint;
import Proskurin.points.ScreenPoint;

import java.awt.*;

public class PolygonFiller<P extends Point> {
    public void fillPolygon(PixelDrawer pixelDrawer, RealPolygon<P> polygon, ScreenConvertor sc, Color color) {
        if (polygon.getPoints().length == 0) {
            return;
        }

        RealPolygon<ScreenPoint> convPolygon = new RealPolygon<>(getScreenPoints(polygon, sc));

        int minX = 1000000, minY = 10000000, maxX = 0, maxY = 0;
        for (ScreenPoint point : convPolygon.getPoints()) {
            minX = Math.min(minX, (int) Math.floor(point.getX()));
            minY = Math.min(minY, (int) Math.floor(point.getY()));
            maxX = Math.max(maxX, (int) Math.ceil(point.getX()));
            maxY = Math.max(maxY, (int) Math.ceil(point.getY()));
        }

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                if (convPolygon.isInPolygon(new ScreenPoint(x, y), new ScreenPoint(x,  1000000))) {
                    pixelDrawer.setPixel(x, y, color);
                }
            }
        }
    }

    private ScreenPoint[] getScreenPoints(RealPolygon<P> polygon, ScreenConvertor sc) {
        ScreenPoint[] convPolygonPoints = new ScreenPoint[polygon.getPoints().length];
        for (int i = 0; i < convPolygonPoints.length; i++) {
            convPolygonPoints[i] = sc.realToScreen((RealPoint) polygon.getPoints()[i]);
        }
        return convPolygonPoints;
    }
}
