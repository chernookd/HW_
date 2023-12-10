package edu.Project4.Render;

@SuppressWarnings("EqualsHashCode")
public class Pixel {
    public double normal = 0;
    private int r;
    private int g;
    private int b;
    private int hitCount;
    private Point point;
    private int x;
    private int y;

    public int getHitCount() {
        return hitCount;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setB(int b) {
        this.b = b;
    }

    public Pixel(int r, int g, int b, int hitCount, int x, int y) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.hitCount = hitCount;
        this.x = x;
        this.y = y;
        //this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Pixel pixel = (Pixel) o;
        return this.point.equals(pixel.point);

    }

    public void setHitCount(int newHitCount) {
        hitCount = newHitCount;
    }
}
