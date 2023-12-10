package edu.Project4.Transform;

import edu.Project4.Render.Point;

public class Disk implements Transformation {
    @Override
    public Point transform(Point point) {
        double x = ((1 / Math.PI) * Math.atan(point.getY()) / point.getX()
            * Math.sin(Math.PI * Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY())));
        double y = ((1 / Math.PI) * Math.atan(point.getY()) / point.getX()
            * Math.cos(Math.PI * Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY())));
        return new Point(x, y);
    }
}
