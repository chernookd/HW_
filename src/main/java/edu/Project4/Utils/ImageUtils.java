package edu.Project4.Utils;

import edu.Project4.Render.FractalImageMultiThread;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class ImageUtils {
    private ImageUtils() {}

    public static void save(FractalImageMultiThread image, File filename, ImageFormat format) throws IOException {


        BufferedImage bufferedImage =
            new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < image.getData().length; i++) {
            for (int j = 0; j < image.getData()[i].length; j++) {
                Color color =
                    new Color(image.getData()[i][j].getR(), image.getData()[i][j].getG(), image.getData()[i][j].getB());
                bufferedImage.setRGB(
                    image.getData()[i][j].getX(),
                    image.getData()[i][j].getY(),
                    color.getRGB()
                );
            }
        }

        File file = new File(filename + "." + format.name());
        ImageIO.write(bufferedImage, format.name(), file);
    }
}
