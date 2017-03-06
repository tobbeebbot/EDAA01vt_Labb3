package tree;

import fractal.Fractal;
import fractal.TurtleGraphics;
import mountain.Point;

import java.util.Random;

public class Tree extends Fractal {
    private int firstBranchLength;
    private Random rnd;
    public Tree(Point rootPos, int firstBranchLength) {
        this.firstBranchLength = firstBranchLength;
        rnd = new Random();
    }

    @Override
    public String getTitle() {
        return "Tree fractal";
    }

    @Override
    public void draw(TurtleGraphics g) {
        drawBranch(g, new Point(g.getWidth() / 2, g.getHeight() - 50), firstBranchLength, 90, order);
    }

    private void drawBranch(TurtleGraphics g, Point start, double length, double angle, int order) {
        //Rita grenen
        g.moveTo(start.getX(), start.getY());
        g.setPenWidth(length * 0.04);
        g.setDirection(angle);
        g.forward(length);

        if (order > 0) {
            Point end = new Point(g.getX(), g.getY());


            drawBranch(g, end, length * rand(0.6, 0.9), angle - rand(35, 45), order - 1);
            drawBranch(g, end, length * rand(0.6, 0.9), angle + rand(35, 45), order - 1);
        }
    }
    private double rand(double min, double max) {
        return rnd.nextDouble() * (max - min) + min;
    }
}
