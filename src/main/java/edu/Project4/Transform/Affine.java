package edu.Project4.Transform;

import edu.Project4.Render.Point;
import java.util.Random;

public class Affine implements Transformation {
    private static final int RGB_BOUND = 255;
    private double a;
    private double b;
    private double c;
    private double d;
    private double e;
    private double f;
    private static Random random = new Random();
    int red = random.nextInt(RGB_BOUND);
    int green = random.nextInt(RGB_BOUND);
    int blue = random.nextInt(RGB_BOUND);

    public Affine() {
        createCoeff();
    }

    public Affine(int a, int b, int c, int d, int e, int f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }

    @SuppressWarnings("MagicNumber")
    private void createCoeff() {
        f = random.nextDouble() * 2 - 1;
        c = random.nextDouble() * 2 - 1;
        a = random.nextDouble();
        d = random.nextDouble();
        b = random.nextDouble();
        e = random.nextDouble();
        while ((b * b + e * e) > 1 && (a * a + d * d) > 1
            && (a * a + b * b + d * d + e * e) > (1 + (a * e - d * b) * (a * e - d * b))) {
            a = random.nextDouble();
            d = random.nextDouble();
            b = random.nextDouble();
            e = random.nextDouble();
        }
    }


    @Override
    public Point transform(Point point) {
        double x;
        double y;
        synchronized (this) {
            x =  (a * point.getX() + b * point.getY() + c);
            y =  (d * point.getX() + e * point.getY() + f);
        }
        return new Point(x, y);
    }


    public int r() {
        return red;
    }

    public int g() {
        return green;
    }

    public int b() {
        return blue;
    }

}
