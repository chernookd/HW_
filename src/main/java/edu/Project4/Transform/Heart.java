package edu.Project4.Transform;

import edu.Project4.Render.Point;

public class Heart implements Transformation {
    @Override
    public Point transform(Point point) {
        double x = Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY())
            * Math.sin((point.getX() * point.getX() + point.getY() * point.getY())
            * Math.atan(point.getY()) / point.getX());
        double y = Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY())
            * Math.cos((point.getX() * point.getX() + point.getY() * point.getY())
            * Math.atan(point.getY()) / point.getX());
        return new Point(x, y);

    }
}
