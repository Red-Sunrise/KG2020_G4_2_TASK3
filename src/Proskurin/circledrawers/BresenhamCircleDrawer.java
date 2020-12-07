package Proskurin.circledrawers;

import Proskurin.pixeldrawers.PixelDrawer;
import Proskurin.points.ScreenPoint;

import java.awt.*;

    public class BresenhamCircleDrawer implements CircleDrawer {
        PixelDrawer pd;

        public BresenhamCircleDrawer(PixelDrawer pd) {
            this.pd = pd;
        }

        @Override
        public void drawLine(ScreenPoint point, int a, int b, Color color) {
            int x0 = point.getX(), y0 = point.getY();

            int x = 0, y = b;
            int delta = 4 * b * b * ((x + 1) * (x + 1)) + a * a * ((2 * y - 1) * (2 * y - 1)) - 4 * a * a * b * b;
            while (a * a * (2 * y - 1) > 2 * b * b * (x + 1)) {
                putPixel(pd, x0, y0, x, y, color);
                if (delta < 0) {
                    x++;
                    delta += 4 * b * b * (2 * x + 3);
                } else {
                    x++;
                    delta = delta - 8 * a * a * (y - 1) + 4 * b * b * (2 * x + 3);
                    y--;
                }
            }
            delta = b * b * ((2 * x + 1) * (2 * x + 1)) + 4 * a * a * ((y + 1) * (y + 1)) - 4 * a * a * b * b;
            while (y + 1 != 0) {
                putPixel(pd, x0, y0, x, y, color);
                if (delta < 0) {
                    y--;
                    delta += 4 * a * a * (2 * y + 3);
                } else {
                    y--;
                    delta = delta - 8 * b * b * (x + 1) + 4 * a * a * (2 * y + 3);
                    x++;
                }
            }
        }

        private void putPixel(PixelDrawer pd, int x, int y, int dx, int dy, Color color) {
            pd.setPixel(x + dx, y + dy, color);
            pd.setPixel(x - dx, y + dy, color);
            pd.setPixel(x - dx, y - dy, color);
            pd.setPixel(x + dx, y - dy, color);
        }
    }
