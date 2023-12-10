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
            affineTransformationList, 100000, 4, 10, 0, 2);
        long endTime = System.nanoTime();
        long singleThreadTime = endTime - startTime;

        startTime = System.nanoTime();
        fractalImageMultiThread.create(fractalImageSingleThread,noLinearTransformationList,
            affineTransformationList, 100000, 4, 10, 0 ,2);
        endTime = System.nanoTime();
        long multiThreadTime = endTime - startTime;

        System.out.println("multiThreadTime = " + multiThreadTime + " singleThreadTime " + singleThreadTime);
    }
}


