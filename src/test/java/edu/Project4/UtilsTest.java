package edu.Project4;

import edu.Project4.Render.FractalImageMultiThread;
import edu.Project4.Render.Pixel;
import edu.Project4.Utils.ImageFormat;
import edu.Project4.Utils.ImageUtils;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UtilsTest {

    @Test
    void saveFileTest() throws IOException {
        Pixel[][] data = new Pixel[100][100];
        ImageUtils.save(new FractalImageMultiThread(data, 100, 100),
            new File("./src/main/java/edu/Project4/Fractals/test"), ImageFormat.PNG);
        ImageUtils.save(new FractalImageMultiThread(data, 100, 100),
            new File("./src/main/java/edu/Project4/Fractals/test"), ImageFormat.BMP);
        ImageUtils.save(new FractalImageMultiThread(data, 100, 100),
            new File("./src/main/java/edu/Project4/Fractals/test"), ImageFormat.JPEG);
        File filePNG = new File("./src/main/java/edu/Project4/Fractals/test.PNG");
        File fileBMP = new File("./src/main/java/edu/Project4/Fractals/test.BMP");
        File fileJPEG = new File("./src/main/java/edu/Project4/Fractals/test.JPEG");


        assertThat(filePNG.getName()).isEqualTo("test.PNG");
        assertThat(fileBMP.getName()).isEqualTo("test.BMP");
        assertThat(fileJPEG.getName()).isEqualTo("test.JPEG");


        Files.deleteIfExists(filePNG.toPath());
        Files.deleteIfExists(fileBMP.toPath());
        Files.deleteIfExists(fileJPEG.toPath());
    }

}
