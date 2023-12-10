package edu.Project4.Render;

import edu.Project4.Transform.Affine;
import edu.Project4.Transform.Transformation;
import java.util.List;

public interface FractalImage {
    @SuppressWarnings("ParameterNumber")
    void create(FractalImage canvas, List<Transformation> noLine, List<Affine> affine,
        int samples, int symmetry, int stepCount, double max, double gamma);

    Pixel[][] getData();
}
