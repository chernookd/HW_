package edu.Project4.Render;

import edu.Project4.Transform.Affine;
import edu.Project4.Transform.Disk;
import edu.Project4.Transform.Heart;
import edu.Project4.Transform.Polar;
import edu.Project4.Transform.Sinus;
import edu.Project4.Transform.Sphere;
import edu.Project4.Transform.Transformation;
import edu.Project4.Utils.ImageFormat;
import edu.Project4.Utils.ImageUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class FractalViewer extends JFrame {
    private static final int XRES = 1920;
    private static final int YRES = 1080;
    private FractalImageMultiThread fractalImageSingleThread;

    public FractalViewer(FractalImageMultiThread fractalImageSingleThread) {
        this.fractalImageSingleThread = fractalImageSingleThread;
        setSize(fractalImageSingleThread.getWidth(), fractalImageSingleThread.getHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < fractalImageSingleThread.getData().length; i++) {
            for (int j = 0; j < fractalImageSingleThread.getData()[i].length; j++) {
                g.setColor(new Color(
                    fractalImageSingleThread.getData()[i][j].getR(), fractalImageSingleThread.getData()[i][j].getG(),
                    fractalImageSingleThread.getData()[i][j].getB()));
                g.fillRect(fractalImageSingleThread.getData()[i][j].getX(), fractalImageSingleThread
                    .getData()[i][j].getY(), 1, 1);
            }
        }
    }

    @SuppressWarnings({"UncommentedMain", "MagicNumber"})
    public static void main(String[] args) throws IOException {
        FractalImageMultiThread fractalImageSingleThread = new FractalImageMultiThread(new Pixel[XRES][YRES],
            XRES, YRES);

        List<Affine> affineTransformationList = new ArrayList<>(List.of(new Affine(), new Affine(),
            new Affine(), new Affine(), new Affine()));
        List<Transformation> noLinearTransformationList = new ArrayList<>(List.of(
            new Sphere(),
            new Sinus(),
            new Disk(),
            new Heart(),
            new Polar()
            ));

        fractalImageSingleThread.create(fractalImageSingleThread, noLinearTransformationList,
            affineTransformationList, 200_000, 6, 30, 0.8, 0);


        new FractalViewer(fractalImageSingleThread);
        ImageUtils.save(fractalImageSingleThread,
            new File("./src/main/java/edu/Project4/Fractals/im8"), ImageFormat.PNG);
    }
}
