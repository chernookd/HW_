package edu.hw9;

import edu.hw9.Task2.Searcher;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task2Test {
    @Test
    public void testDirectoryWithMoreThan1000Files() {
        String fileName = "more1000";
        Path pathOfStartDirectory = Path.of("testDirToTestDirectoryWithMoreThan1000Files");
        List<File> directoryWithMoreThan1000Files = Searcher.getDirectoryWithMoreThan1000Files(pathOfStartDirectory);

        assertThat(directoryWithMoreThan1000Files.size()).isEqualTo(3);
        directoryWithMoreThan1000Files.stream()
            .forEach(file -> {assertTrue(file.getName().contains(fileName));});
    }


    @Test
    public void testFileSearcherWithFileSizePredicate() {
        Path path = Path.of("testDirToFileSearcherTest");
        String notEmptyFileName = "notEmpty";
        Predicate<File> predicate = (File f) -> {
            return f.length() > 1;
        };
        Predicate<File> predicate2 = (File f) -> {
            return f.length() == 0;
        };

        List<File> filesByPredicate = Searcher.getFilesByPredicate(path, predicate);

        assertThat(filesByPredicate.size()).isEqualTo(2);
        filesByPredicate.stream()
            .forEach(file -> {assertTrue(file.getName().contains(notEmptyFileName));});

        List<File> EmptyFilesByPredicate = Searcher.getFilesByPredicate(path, predicate2);

        assertThat(EmptyFilesByPredicate.size()).isEqualTo(8);
        EmptyFilesByPredicate.stream()
            .forEach(file -> {assertFalse(file.getName().contains(notEmptyFileName));});
    }

    @Test
    public void testFileSearcherWithExtensionPredicate() {
        Path path = Path.of("testDirToFileSearcherTest");
        String txtExtension = "txt";
        String pngExtension = "png";
        String texExtension = "tex";
        String unknownExtension = "";

        Predicate<File> txtPredicate = (File f) -> {
            return getExtension(f).equals(txtExtension);
        };
        Predicate<File> pngPredicate = (File f) -> {
            return getExtension(f).equals(pngExtension);
        };
        Predicate<File> texPredicate = (File f) -> {
            return getExtension(f).equals(texExtension);
        };
        Predicate<File> unknownPredicate = (File f) -> {
            return getExtension(f).equals(unknownExtension);
        };

        List<File> filesTxtPredicate = Searcher.getFilesByPredicate(path, txtPredicate);
        List<File> filesByPngPredicate = Searcher.getFilesByPredicate(path, pngPredicate);
        List<File> filesByTexPredicate = Searcher.getFilesByPredicate(path, texPredicate);
        List<File> filesByUnknownPredicate = Searcher.getFilesByPredicate(path, unknownPredicate);

        assertThat(filesTxtPredicate.size()).isEqualTo(5);
        assertThat(filesByPngPredicate.size()).isEqualTo(2);
        assertThat(filesByTexPredicate.size()).isEqualTo(3);
        assertThat(filesByUnknownPredicate.size()).isEqualTo(0);
    }

    public String getExtension(File file) {
        String fileName = file.getName();
        int indexOfDot = fileName.indexOf(".");
        return fileName.substring(indexOfDot + 1);
    }
}
