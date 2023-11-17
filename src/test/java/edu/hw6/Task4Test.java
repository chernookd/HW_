package edu.hw6;

import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static edu.hw6.Task4.Task4.composition;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
     @Test
    void Task4CompositionTest() throws IOException {
        Path path = Path.of("file.txt");
        Files.deleteIfExists(path);
        Files.createFile(path);
        composition(path);

        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(path)))) {
            String line = br.readLine();
            assert line != null;
            assertThat(line).isEqualTo("Programming is learned by writing programs. ― Brian Kernighan");
            assertThat(br.readLine()).isEqualTo(null);
        }

        Files.deleteIfExists(path);
    }
}
