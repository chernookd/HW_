package edu.Project4.Render;

import edu.Project4.Transform.Affine;
import edu.Project4.Transform.Transformation;
import edu.Project4.Utils.GammaCorrection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FractalImageSingleThread implements FractalImage {
    private Pixel[][] data;
    private int width;
    private int height;
    private static final double XMIN = -1.777;
    private static final double XMAX = 1.777;
    private static final double YMIN = -1;
    private static final double YMAX = 1;
    private static final int OFFSET = 20;

    Random random = new Random();

    public FractalImageSingleThread(Pixel[][] data, int width, int height) {
        this.data = data;
        this.width = width;
        this.height = height;
        createData();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Pixel[][] getData() {
        return data;
    }

    private void createData() {
        data = new Pixel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = new Pixel(0, 0, 0, 0, i, j);
            }
        }
    }

    @SuppressWarnings({"ParameterAssignment", "ParameterNumber"})
    public void create(FractalImage canvas, List<Transformation> noLine,
        List<Affine> affine, int samples, int symmetry, int stepCount, double max, double gamma) {
        stepCount = stepCount + OFFSET;
        for (int num = 0; num < samples; num++) {

            double newX = random.nextDouble() * (XMAX - XMIN) + XMIN;
            double newY = random.nextDouble() * (YMAX - YMIN) + YMIN;
            double theta2 = 0;


            for (short step = (-1 * OFFSET); step < stepCount; step++) {

                int randomAffineTransformIndex = ThreadLocalRandom.current().nextInt(affine.size());
                Affine affineTransformation = affine.get(randomAffineTransformIndex);
                int randomNoLinearTransformIndex = ThreadLocalRandom.current().nextInt(noLine.size());
                Transformation noLineTransformation = noLine.get(randomNoLinearTransformIndex);

                Point point = new Point(newX, newY);

                point = affineTransformation.transform(point);
                point = noLineTransformation.transform(point);

                newX = point.getX();
                newY = point.getY();
                for (int i = 0; i < symmetry; theta2 += Math.PI * 2 / symmetry, i++) {
                    newX = (point.getX() * Math.cos(theta2) - point.getY() * Math.sin(theta2));
                    newY = (point.getX() * Math.sin(theta2) + point.getY() * Math.cos(theta2));
                    if (step >= 0 && newX >= XMIN && newX <= XMAX && newY >= YMIN && newY <= YMAX) {
                        int x = width - ((int) (((XMAX - newX) / (XMAX - XMIN)) * width));
                        int y = height - ((int) (((YMAX - newY) / (YMAX - YMIN)) * height));

                        if (x < width && y < height) {
                            Pixel currentPixel = canvas.getData()[x][y];
                            int newR = affineTransformation.r();
                            int newG = affineTransformation.g();
                            int newB = affineTransformation.b();
                            if (currentPixel.getHitCount() == 0) {
                                currentPixel.setR(newR);
                                currentPixel.setG(newG);
                                currentPixel.setB(newB);
                            } else {
                                currentPixel.setR((currentPixel.getR() + newR) / 2);
                                currentPixel.setG((currentPixel.getG() + newG) / 2);
                                currentPixel.setB((currentPixel.getB() + newB) / 2);
                            }

                            currentPixel.setHitCount(currentPixel.getHitCount() + 1);
                        }
                    }
                }
            }
        }
        GammaCorrection gammaCorrection = new GammaCorrection();
        gammaCorrection.correction(width, height, canvas.getData(), max, gamma);
    }
}

