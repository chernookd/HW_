package edu.Project4.Transform;

import edu.Project4.Render.Point;

public class Polar implements Transformation {
    @Override
    public Point transform(Point point) {
        double x = ((Math.atan(point.getY()) / point.getX()) / Math.PI);
        double y = Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY()) - 1;
        return new Point(x, y);

    }
}
