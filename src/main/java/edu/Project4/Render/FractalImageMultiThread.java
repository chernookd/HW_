package edu.Project4.Render;

import edu.Project4.Transform.Affine;
import edu.Project4.Transform.Transformation;
import edu.Project4.Utils.GammaCorrection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FractalImageMultiThread implements FractalImage {
    private Pixel[][] data;
    private double theta2 = 0;

    private int width;
    private int height;
    private static final double XMIN = -1.777;
    private static final double XMAX = 1.777;
    private static final double YMIN = -1;
    private static final double YMAX = 1;
    private static final int OFFSET = 20;

    Lock lock = new ReentrantLock();

    //ThreadLocalRandom random = (ThreadLocalRandom) new ThreadLocalRandom();

    public FractalImageMultiThread(Pixel[][] data, int width, int height) {
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

    @SuppressWarnings({"ParameterAssignment", "LambdaBodyLength", "ParameterNumber"})
    public void create(FractalImage canvas, List<Transformation> noLine, List<Affine> affine, int samples,
        int symmetry, int stepCount, double gamma, double max) {
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        stepCount = stepCount + OFFSET;

        for (int num = 0; num < samples; num++) {
            int finalStepCount = stepCount;
            service.execute(() -> {

                double newX = ThreadLocalRandom.current().nextDouble() * (XMAX - XMIN) + XMIN;
                double newY = ThreadLocalRandom.current().nextDouble() * (YMAX - YMIN) + YMIN;

                for (int step = (-1 * OFFSET); step < finalStepCount; step++) {

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
                                int newR = affineTransformation.r();
                                int newG = affineTransformation.g();
                                int newB = affineTransformation.b();
                                    Pixel currentPixel = new Pixel(canvas.getData()[x][y].getR(),
                                        canvas.getData()[x][y].getB(), canvas.getData()[x][y].getG(),
                                        canvas.getData()[x][y].getHitCount(), canvas.getData()[x][y].getX(),
                                        canvas.getData()[x][y].getY());
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
                                    synchronized (canvas) {
                                        canvas.getData()[x][y] = currentPixel;
                                    }
                            }
                        }
                    }
                }
            });
        }
        service.shutdown();
        try {
            service.awaitTermination(1, TimeUnit.HOURS);
        } catch (Exception e) {

        }
        GammaCorrection gammaCorrection = new GammaCorrection();
        gammaCorrection.correction(width, height, canvas.getData(), max, gamma);
    }
}
