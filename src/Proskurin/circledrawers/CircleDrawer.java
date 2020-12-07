package Proskurin.circledrawers;

import Proskurin.points.ScreenPoint;

import java.awt.*;

public interface CircleDrawer {
    void drawLine(ScreenPoint point, int a, int b, Color color);
}
