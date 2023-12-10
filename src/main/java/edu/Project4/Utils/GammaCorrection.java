package edu.Project4.Utils;

import edu.Project4.Render.Pixel;
import static java.lang.Math.log10;
import static java.lang.Math.pow;

public class GammaCorrection {

    public Pixel[][] correction(int xRes, int yRes, Pixel[][] pixels, double max, double gamma) {
        double currentMax = max;
        for (int row = 0; row < xRes; row++) {
            for (int col = 0; col < yRes; col++) {
                if (pixels[row][col].getHitCount() != 0) {
                    pixels[row][col].normal = log10(pixels[row][col].getHitCount());
                    if (pixels[row][col].normal > currentMax) {
                        currentMax = pixels[row][col].normal;
                    }
                }
            }
        }
        for (int row = 0; row < xRes; row++) {
            for (int col = 0; col < yRes; col++) {
                pixels[row][col].normal /= currentMax;
                pixels[row][col].setR((int) (pixels[row][col].getR() * pow(pixels[row][col].normal, (1.0 / gamma))));
                pixels[row][col].setG((int) (pixels[row][col].getG() * pow(pixels[row][col].normal, (1.0 / gamma))));
                pixels[row][col].setB((int) (pixels[row][col].getB() * pow(pixels[row][col].normal, (1.0 / gamma))));
            }
        }
        return pixels;
    }
}
