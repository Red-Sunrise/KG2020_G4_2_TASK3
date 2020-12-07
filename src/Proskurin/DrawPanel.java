package Proskurin;

import Proskurin.circledrawers.BresenhamCircleDrawer;
import Proskurin.circledrawers.CircleDrawer;
import Proskurin.linedrawers.BresenhamLineDrawer;
import Proskurin.linedrawers.LineDrawer;
import Proskurin.models.RealPolygon;
import Proskurin.models.Triangle;
import Proskurin.pixeldrawers.BufferedImagePixelDrawer;
import Proskurin.pixeldrawers.PixelDrawer;
import Proskurin.points.Point;
import Proskurin.points.RealPoint;
import Proskurin.points.ScreenPoint;
import Proskurin.utils.Logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class DrawPanel extends JPanel implements MouseMotionListener, MouseWheelListener, MouseListener {
    private ScreenConvertor sc;
    private BufferedImage img;
    private PixelDrawer pixelDrawer;
    private LineDrawer lineDrawer;
    private CircleDrawer circleDrawer;

    private RealPoint lastPoint = null;
    private Triangle<RealPoint> tr1, tr2;
    private Triangle<RealPoint>[] triangles;
    private List<RealPoint> Points;

    public DrawPanel() {
        super();
        sc = new ScreenConvertor(0, 0, 15, 7, 1500, 700);
        img = new BufferedImage(1500, 700, BufferedImage.TYPE_INT_RGB);

        tr1 = new Triangle<>(new RealPoint(1, 1), new RealPoint(5, 2), new RealPoint(3, 5));
        tr2 = new Triangle<>(new RealPoint(1, 6), new RealPoint(4.3, 2), new RealPoint(6, 6));
        triangles = new Triangle[] {tr1, tr2};
        Points = new ArrayList<>();

        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    @Override
    public void paint(Graphics gr) {
        img = new BufferedImage(1500, 700, BufferedImage.TYPE_INT_RGB);
        pixelDrawer = new BufferedImagePixelDrawer(img);
        lineDrawer = new BresenhamLineDrawer(pixelDrawer);
        circleDrawer = new BresenhamCircleDrawer(pixelDrawer);

        drawTriangle(tr1, Color.blue);
        drawTriangle(tr2, Color.blue);

        for (RealPoint point : Points) {
            circleDrawer.drawLine(sc.realToScreen(point), 20, 20, Color.green);
        }

        RealPolygon p = Logic.makePolygon(tr1, tr2);

        grFillPolygon(p);   // закраситть встроенным методом

        //polygonFiller.fillPolygon(pixelDrawer, p, sc, Color.GREEN);

        gr.drawImage(img, 0, 0, null);
    }

    private void grFillPolygon(RealPolygon p) {
        int n = p.getPoints().length;
        int[] px = new int[n];
        int[] py = new int[n];
        int i = 0;
        for (Point point : p.getPoints()) {
            ScreenPoint temp = sc.realToScreen((RealPoint) point);
            px[i] = temp.getX();
            py[i] = temp.getY();
            i++;
        }
        Graphics2D gr = (Graphics2D) img.getGraphics();
        gr.fillPolygon(px, py, n);
    }


    public void drawTriangle(Triangle<RealPoint> triangle, Color color) {
        lineDrawer.drawLine(sc.realToScreen(triangle.getP1()), sc.realToScreen(triangle.getP2()), color);
        lineDrawer.drawLine(sc.realToScreen(triangle.getP2()), sc.realToScreen(triangle.getP3()), color);
        lineDrawer.drawLine(sc.realToScreen(triangle.getP3()), sc.realToScreen(triangle.getP1()), color);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        RealPoint r = sc.screenToReal(new ScreenPoint(e.getX(), e.getY()));
        double dx = r.getX() - lastPoint.getX();
        double dy = r.getY() - lastPoint.getY();
        if (!Points.isEmpty()) {
            for (RealPoint point : Points) {
                point.setX(point.getX() + dx);
                point.setY(point.getY() + dy);
            }
        } else {
            sc.setX(sc.getX() - dx);
            sc.setY(sc.getY() - dy);
        }
        lastPoint = r;
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Points = new ArrayList<>();
        for (Triangle<RealPoint> triangle : triangles) {
            for (RealPoint point : triangle.getPoints()) {
                if (nearPoint(e, sc.realToScreen(point), 20)) {
                    Points.add(point);
                    repaint();
                    return;
                }
            }
        }
        for (Triangle<RealPoint> triangle : triangles) {
            if (triangle.isInPolygon(sc.screenToReal(new ScreenPoint(e.getX(), e.getY())), new ScreenPoint(e.getX(), 100000))) {
                Points.addAll(Arrays.asList(triangle.getPoints()));
            }
        }
        repaint();
    }

    private boolean nearPoint(MouseEvent e, ScreenPoint sp, int r) {
        return Math.pow((e.getX() - sp.getX()), 2) + Math.pow((e.getY() - sp.getY()), 2) <= Math.pow(r, 2);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        sc.setH(sc.getH() + e.getWheelRotation() * sc.getH() / sc.getW());
        sc.setW(sc.getW() + e.getWheelRotation());
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastPoint = sc.screenToReal(new ScreenPoint(e.getX(), e.getY()));
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}