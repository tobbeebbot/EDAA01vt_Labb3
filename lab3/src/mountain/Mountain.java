package mountain;

import fractal.Fractal;
import fractal.TurtleGraphics;

import java.util.ArrayList;

/**
 * Created by ha8040we-s on 01/02/17.
 */
public class Mountain extends Fractal {

    Point p1, p2, p3;
    double dev;
    ArrayList<Side> sides;

    public Mountain(double dev, Point p1, Point p2, Point p3){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.dev = dev;
        sides = new ArrayList<>();
    }

    @Override
    public String getTitle() {
        return "Mountain fractal";
    }

    @Override
    public void draw(TurtleGraphics g) {
        drawFractal(g, dev, order, p1, p2, p3);
    }

    /*
    fixa så att samm mittenpunkter inte beräknas två ggr. använd klassen side och arraylisten i klassen mountain
     */
    private void drawFractal(TurtleGraphics g, double dev, int order, Point A, Point B, Point C){
        if (order == 0){
            drawTriangle(g,A,B,C);
        } else {
            //Skapa sidor
            Side ab = new Side(A, B);
            Side bc = new Side(B, C);
            Side ac = new Side(C, A);

            //Mellanpunkter
            ab.createMiddlePoint(dev);
            bc.createMiddlePoint(dev);
            ac.createMiddlePoint(dev);

            //Rita fyra deltrianglar
            drawFractal(g, dev/2, order-1, A , ab.m, ac.m);
            drawFractal(g, dev/2, order-1, B, ab.m, bc.m);
            drawFractal(g, dev/2, order-1, C, bc.m, ac.m);
            drawFractal(g, dev/2, order-1, ab.m,bc.m, ac.m);
        }
    }

    private void drawTriangle(TurtleGraphics g, Point p1, Point p2, Point p3) {
        g.penUp();
        g.moveTo(p1.getX(), p1.getY());
        g.penDown();
        g.forwardTo(p2.getX(), p2.getY());
        g.forwardTo(p3.getX(), p3.getY());
        g.forwardTo(p1.getX(), p1.getY());
    }

    private class Side {

        Point a, m, c;

        public Side(Point a, Point c){
            this.a = a;
            this.c = c;
        }

        public void createMiddlePoint(double dev) {
            int i = sides.indexOf(this);
            if (i < 0) {    //Denna sidan fanns inte tidigare, så vi offsettar själva.
                double x = (a.getX() + c.getX()) / 2;
                double y = (a.getY() + c.getY()) / 2 + RandomUtilities.randFunc(dev);
                m = new Point(x, y);
                sides.add(this);
            } else {    //Sidan fanns sedan tidigare så vi hämtar samma offset.
                m = sides.get(i).m;
                sides.remove(i);
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Side) {
                Side that = (Side)obj;
                return (this.a.equals(that.a) && this.c.equals(that.c)) ||
                        (this.c.equals(that.a) && this.a.equals(that.c));
            }
            else {
                System.out.println("Problem in equals.");
                return false;
            }
        }


    }
}
