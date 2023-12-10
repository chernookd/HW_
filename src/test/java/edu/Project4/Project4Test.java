package edu.Project4;

import edu.Project4.Render.FractalImageMultiThread;
import edu.Project4.Render.FractalImageSingleThread;
import edu.Project4.Render.Pixel;
import edu.Project4.Transform.Affine;
import edu.Project4.Transform.Heart;
import edu.Project4.Transform.Transformation;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Project4Test {

    @Test
    void speedTest() {
        FractalImageSingleThread fractalImageSingleThread = new FractalImageSingleThread(new Pixel[1920][1080],
        1920, 1080);

        FractalImageMultiThread fractalImageMultiThread = new FractalImageMultiThread(new Pixel[1920][1080],
            1920, 1080);

        List<Affine> affineTransformationList = new ArrayList<>(List.of(new Affine()));
        List<Transformation> noLinearTransformationList = new ArrayList<>(List.of(
            new Heart()
        ));

        long startTime = System.nanoTime();
        fractalImageSingleThread.create(fractalImageSingleThread,noLinearTransformationList,
            affineTransformationList, 1000000, 4, 10, 0, 2);
        long endTime = System.nanoTime();
        long singleThreadTime = endTime - startTime;

        startTime = System.nanoTime();
        fractalImageMultiThread.create(fractalImageSingleThread,noLinearTransformationList,
            affineTransformationList, 1000000, 4, 10, 0 ,2);
        endTime = System.nanoTime();
        long multiThreadTime = endTime - startTime;

        assertTrue(multiThreadTime <= singleThreadTime);
    }
}

   /* @Test
    void fractalGeneration_MultiThread_Test() {
        //Arrange
        FractalConfig config = new FractalConfig(20000, 1000, 5, "sphere");
        FractalMultiThread fst = new FractalMultiThread(1920, 1080, config);
        Pixel[][] pixels = new Pixel[1920][1080];

        //Act
        Pixel[][] result = fst.generate(5);

        //Assert
        assertThat(Arrays.deepEquals(pixels, result)).isFalse();
    }

    @Test
    void gammaCorrection_Test() {
        //Arrange
        Pixel[][] pixels = new Pixel[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                pixels[x][y] = new Pixel();
                pixels[x][y].red = 100;
                pixels[x][y].green = 150;
                pixels[x][y].blue = 200;
                pixels[x][y].counter = x + y;
            }
        }

        //Act
        GammaCorrection gammaCorrection = new GammaCorrection(pixels);
        pixels = gammaCorrection.run();

        //Assert
        assertThat(pixels[0][0].red).isEqualTo(0);
        assertThat(pixels[1][1].green).isEqualTo(78);
        assertThat(pixels[2][2].blue).isEqualTo(143);
    }

    @Test
    void nonLinearTransformation_Test() {
        //Arrange
        Point point = new Point(1, 1);

        //Act
        NonLinear nonLinear = new NonLinear(point, "sin");
        Point pointSin = nonLinear.run();

        //Assert
        assertThat(point.x()).isNotEqualTo(pointSin.x());
        assertThat(point.y()).isNotEqualTo(pointSin.y());
    }

    @Test
    void affineTransformation_Test() {
        //Arrange
        Point point = new Point(1, 1);

        //Act
        Affine affine = new Affine();
        Point pointAffine = affine.transform(point.x(), point.y());

        //Assert
        assertThat(point.x()).isNotEqualTo(pointAffine.x());
        assertThat(point.y()).isNotEqualTo(pointAffine.y());
    }

    @Test
    void symmetry_Test() {
        //Arrange
        Point point = new Point(1, 1);

        //Act
        Symmetry symmetry = new Symmetry(point.x(), point.y());
        Point pointSymmetry = symmetry.rotate();

        //Assert
        assertThat(point.x()).isNotEqualTo(pointSymmetry.x());
        assertThat(point.y()).isNotEqualTo(pointSymmetry.y());
    }
}*/
