package edu.Project4.Transform;

import edu.Project4.Render.Point;

public class Sphere implements Transformation {
    @Override
    public Point transform(Point point) {
        double x = (point.getX() / (point.getX() * point.getX() + point.getY() * point.getY()));
        double y = (point.getY() / (point.getX() * point.getX() + point.getY() * point.getY()));
        return new Point(x, y);
    }
}
