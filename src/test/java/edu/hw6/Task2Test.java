package edu.hw6;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static edu.hw6.Task2.Task2.cloneFile;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {

    File file;

    @BeforeEach
    void setup() throws IOException {
        file = new File("D:\\Tinkoff Bank Biggest Secret.txt");
        file.createNewFile();
    }

    @Disabled
    @Test
    void Task2CopyFileTest() throws IOException {
        Path copy = cloneFile(file.toPath());
        assert copy != null;
        assertThat(copy.getFileName().toString())
            .isEqualTo("Tinkoff Bank Biggest Secret — копия.txt");
        Files.deleteIfExists(copy);
    }

    @Disabled
    @Test
    void Task2ManyCopyFileTest() throws IOException {
        Path copy1 = cloneFile(file.toPath());
        Path copy2 = cloneFile(file.toPath());
        Path copy3 = cloneFile(file.toPath());

        assertThat(copy1.getFileName().toString())
            .isEqualTo("Tinkoff Bank Biggest Secret — копия.txt");
        assertThat(copy2.getFileName().toString())
            .isEqualTo("Tinkoff Bank Biggest Secret — копия (2).txt");
        assertThat(copy3.getFileName().toString())
            .isEqualTo("Tinkoff Bank Biggest Secret — копия (3).txt");

        Files.deleteIfExists(copy1);
        Files.deleteIfExists(copy2);
        Files.deleteIfExists(copy3);

    }
    @AfterEach void clear() throws IOException {
        Files.deleteIfExists(file.toPath());
    }
}
