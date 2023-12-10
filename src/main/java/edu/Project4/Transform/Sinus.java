package edu.Project4.Transform;

import edu.Project4.Render.Point;

public class Sinus implements Transformation {
    @Override
    public Point transform(Point point) {
        return new Point(Math.sin(point.getX()), Math.sin(point.getY()));
    }

}
