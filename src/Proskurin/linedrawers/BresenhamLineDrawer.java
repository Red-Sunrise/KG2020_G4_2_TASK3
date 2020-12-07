package Proskurin.linedrawers;

import Proskurin.pixeldrawers.PixelDrawer;
import Proskurin.points.ScreenPoint;

import java.awt.*;


public class BresenhamLineDrawer implements LineDrawer {
    PixelDrawer pd;

    public BresenhamLineDrawer(PixelDrawer pd) {
        this.pd = pd;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2, Color color) {
        int x1 = p1.getX();
        int x2 = p2.getX();
        int y1 = p1.getY();
        int y2 = p2.getY();

        int dx = (x2 - x1 > 0 ? 1 : -1);
        int dy = (y2 - y1 > 0 ? 1 : -1);

        int deltaX = Math.abs(x2 - x1);
        int deltaY = Math.abs(y2 - y1);

        int x = x1, y = y1;

        if (deltaX >= deltaY) {
            int e = 2 * deltaY - deltaX;
            for (int i = 0; i < deltaX; i++) {
                pd.setPixel(x, y, color);
                if (e >= 0) {
                    x += dx;
                    y += dy;
                    e += 2 * (deltaY - deltaX);
                } else {
                    e += 2 * deltaY;
                    x += dx;
                }
            }
        } else {
            int e = 2 * deltaX - deltaY;
            for (int i = 0; i < deltaY; i++) {
                pd.setPixel(x, y, color);
                if (e >= 0) {
                    x += dx;
                    y += dy;
                    e += 2 * (deltaX - deltaY);
                } else {
                    e += 2 * deltaX;
                    y += dy;
                }
            }
        }
    }
    }
