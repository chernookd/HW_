package edu.hw6;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static edu.hw6.Task3.AbstractFilter.attributes;
import static edu.hw6.Task3.AbstractFilter.globMatches;
import static edu.hw6.Task3.AbstractFilter.largeThan;
import static edu.hw6.Task3.AbstractFilter.magicNumber;
import static edu.hw6.Task3.AbstractFilter.regexContains;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task3Test {
    private static File dir;

    @BeforeAll
    static void setup() throws IOException {
        dir = new File("D:\\newDir\\");
        dir.mkdir();
    }

    @Disabled
    @Test
    void Task3GlobMatchesTest() throws Exception {
        try {
            File file1 = new File("D:\\newDir\\test1.png");
            file1.createNewFile();
            File file2 = new File("D:\\newDir\\test2.png");
            file2.createNewFile();
            File file3 = new File("D:\\newDir\\test3.txt");
            file3.createNewFile();
            File file4 = new File("D:\\newDir\\test4.jpg");
            file4.createNewFile();
            File file5 = new File("D:\\newDir\\test5.txt");
            file5.createNewFile();
            File file6 = new File("D:\\newDir\\test6.png");
            file6.createNewFile();

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir.toPath(), globMatches("txt"))) {
                int numTxt = 0;
                for (Path entry : stream) {
                    assertThat(entry.getFileName().toString()).endsWith(".txt");
                    numTxt++;
                }
                assertThat(numTxt).isEqualTo(2);
            }
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir.toPath(), globMatches("jpg"))) {
                int numJpg = 0;
                for (Path entry : stream) {
                    assertThat(entry.getFileName().toString()).endsWith(".jpg");
                    numJpg++;
                }
                assertThat(numJpg).isEqualTo(1);
            }
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir.toPath(), globMatches("png"))) {
                int numPng = 0;
                for (Path entry : stream) {
                    assertThat(entry.getFileName().toString()).endsWith(".png");
                    numPng++;
                }
                assertThat(numPng).isEqualTo(3);
            }
            file1.delete();
            file2.delete();
            file3.delete();
            file4.delete();
            file5.delete();
            file6.delete();

        } catch (Exception e) {
            dir.delete();

        }
        dir.delete();
    }

    @Disabled
    @Test
    void TaskLargeThan3Test() throws IOException {
        try {
            File file1 = new File("D:\\newDir\\test1.txt");
            file1.createNewFile();
            File file2 = new File("D:\\newDir\\test2.txt");
            file2.createNewFile();
            File file3 = new File("D:\\newDir\\test3.txt");
            file2.createNewFile();
            Files.write(file1.toPath(), new byte[100]);
            Files.write(file2.toPath(), new byte[50]);
            Files.write(file3.toPath(), new byte[200]);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir.toPath(), largeThan(100))) {
                int count = 0;
                for (Path entry : stream) {
                    assertThat(Files.size(entry)).isGreaterThan(100);
                    count++;
                }

                assertThat(count).isEqualTo(1);
                file1.delete();
                file2.delete();
                file3.delete();
            }
        } catch (Exception e) {
            dir.delete();

        }
        dir.delete();
    }

    @Disabled
    @Test
    void Task3RegexContainsTest() throws IOException {
        try {
            File file1 = new File("D:\\newDir\\test12.txt");
            file1.createNewFile();
            File file2 = new File("D:\\newDir\\test.txt");
            file2.createNewFile();
            File file3 = new File("D:\\newDir\\test1.txt");
            file3.createNewFile();
            String regex = "test1.*";

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir.toPath(), regexContains(regex))) {
                int count = 0;
                Pattern pattern = Pattern.compile(regex);
                for (Path entry : stream) {
                    count++;
                    Matcher matcher = pattern.matcher(entry.toString());
                    assertTrue(matcher.find());
                }
                assertThat(count).isEqualTo(2);
                file1.delete();
                file2.delete();
                file3.delete();
            }
        } catch (Exception e) {
            dir.delete();
        }
        dir.delete();
    }

    @Disabled
    @Test
    void Task3AttributesTest() throws IOException {
        try {
            File file1 = new File("D:\\newDir\\test12.txt");
            file1.createNewFile();
            File file2 = new File("D:\\newDir\\test.txt");
            file2.createNewFile();
            File file3 = new File("D:\\newDir\\test1.txt");
            file3.createNewFile();

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir.toPath(), attributes(true, false))) {
                int count = 0;
                for (Path entry : stream) {
                    count++;
                }
                assertThat(count).isEqualTo(3);
                file1.delete();
                file2.delete();
                file3.delete();
            }
        } catch (Exception e) {
            dir.delete();
        }
        dir.delete();
    }

    @Disabled
    @Test
    void Task3MagicNumbersTest() throws IOException {
        try {
            byte[] magicNumberForJPG = {(byte) 255, (byte) 216};
            byte[] magicNumberForPNG = {(byte) 0x89, 'P', 'N', 'G'};
            Files.write(dir.toPath().resolve("test.jpg"), magicNumberForJPG);
            Files.write(dir.toPath().resolve("test.png"), magicNumberForPNG);

            DirectoryStream.Filter<Path> filter = magicNumber(magicNumberForPNG);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir.toPath(), filter)) {
                int countCorrectFiles = 0;
                for (Path entry : stream) {
                    ++countCorrectFiles;
                    assertThat(filter.accept(entry)).isTrue();
                }

                assertThat(countCorrectFiles).isEqualTo(1);
            }
        } catch (Throwable e) {
            Files.deleteIfExists((dir.toPath().resolve("test.jpg")));
            Files.deleteIfExists((dir.toPath().resolve("test.png")));
            Files.deleteIfExists((dir.toPath()));
        }

        Files.deleteIfExists((dir.toPath().resolve("test.jpg")));
        Files.deleteIfExists((dir.toPath().resolve("test.png")));
    }

    @Disabled
    @Test
    void Task3AndTest() throws IOException {
        try {
            byte[] magicNumberForJPG = {(byte) 255, (byte) 216};

            Path path1 = Files.createFile(dir.toPath().resolve("test1.txt"));
            Path path2 = Files.write(dir.toPath().resolve("photo.jpg"), magicNumberForJPG);
            Path path3 = Files.write(dir.toPath().resolve("test2.png"), magicNumberForJPG);

            DirectoryStream.Filter<Path> filter = magicNumber(magicNumberForJPG)
                .and(globMatches("jpg"))
                .and(regexContains("photo"))
                .and(attributes(true, false));

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir.toPath(), filter)) {
                int count = 0;
                for (Path entry : stream) {
                    count++;
                    assertThat(filter.accept(entry)).isTrue();
                }
                assertThat(count).isEqualTo(1);
            }
        } catch (Throwable e) {
            Files.deleteIfExists(dir.toPath().resolve("photo.jpg"));
            Files.deleteIfExists(dir.toPath().resolve("test2.png"));
            Files.deleteIfExists(dir.toPath().resolve("test1.txt"));
            Files.deleteIfExists(dir.toPath());
        }
        Files.deleteIfExists(dir.toPath().resolve("photo.jpg"));
        Files.deleteIfExists(dir.toPath().resolve("test2.png"));
        Files.deleteIfExists(dir.toPath().resolve("test1.txt"));
    }
    @AfterAll
    static void clear() throws IOException {
        dir.delete();
    }
}
