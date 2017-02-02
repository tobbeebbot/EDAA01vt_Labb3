package mountain;

import fractal.Fractal;
import fractal.TurtleGraphics;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

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
        //drawTriangle(g, p1, p2, p3);
    }

    /*
    fixa så att samm mittenpunkter inte beräknas två ggr. använd klassen side och arraylisten i klassen mountain
     */
    private void drawFractal(TurtleGraphics g, double dev, int order, Point a, Point b, Point c){

        if (order == 0){
            drawTriangle(g,a,b,c);
        } else {
            int index;
            Side ab;
            Side bc;
            Side ca;

            //beräkna mellanpunkter
            //lägg in i hjälp funktion
            index = sides.indexOf(new Side(a,b));
            if(index >=0) {
                ab = sides.get(index);
                sides.remove(index);
            } else{
                ab = new Side(a,b);
                ab.createMiddlePoint(dev);
            }
            System.out.println(ab.toString());

            index = sides.indexOf(new Side(b,c));
            if(index >=0) {
                bc = sides.get(index);
                sides.remove(index);
            } else{
                bc = new Side(b,c);
                bc.createMiddlePoint(dev);
            }
            System.out.println(bc.toString());

            index = sides.indexOf(new Side(c,a));
            if(index >=0) {
                ca = sides.get(index);
                sides.remove(index);
            } else{
                ca = new Side(c,a);
                ca.createMiddlePoint(dev);
            }
            System.out.println(ca.toString());


/*            //förskjut y-pos
            ab.move(ab.getX(), ab.getY() + RandomUtilities.randFunc(dev));
            bc.move(bc.getX(), bc.getY() + RandomUtilities.randFunc(dev));
            ca.move(ca.getX(), ca.getY() + RandomUtilities.randFunc(dev));*/

            //Rita fyra deltrianglar
            drawFractal(g, dev/2, order-1,ab.b ,b, bc.b);
            drawFractal(g, dev/2, order-1, a, ab.b, ca.b);
            drawFractal(g, dev/2, order-1,ab.b ,bc.b, ca.b);
            drawFractal(g, dev/2, order-1,ca.b ,bc.b, c);
        }
    }

    private Point getMiddlePoint(Point p1, Point p2){
        double x = (p1.getX() + p2.getX())/2;
        double y = (p1.getY() + p2.getY())/2;
        return new Point(x, y);
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

        Point a,b,c;

        public Side(Point a, Point c){
            this.a = a;
            this.c = c;
        }

        public void createMiddlePoint(double dev){
            double x = (a.getX() + a.getX())/2;
            double y = (c.getY() + c.getY())/2 + RandomUtilities.randFunc(dev);
            b = new Point(x, y);
        }

        //@Override
        public boolean equals(Side that) {
            return this.a.equals(that.a) && this.c.equals(that.c);
        }

        @Override
        public String toString() {
            return "Side{" + a.getX() + ", " + a.getY() +"; " + b.getX() + ", " + b.getY() + "; " + c.getX() + ", " + c.getY() + "}";
        }
    }
}
