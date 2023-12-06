package edu.hw9.Task2;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Predicate;

public class Searcher {

    private Searcher() {

    }

    public static List<File> getDirectoryWithMoreThan1000Files(Path startPath) {

        RecursiveDirectorySearcher searcher = new RecursiveDirectorySearcher(startPath);
        ForkJoinPool.commonPool().invoke(searcher);
        return searcher.join();
    }

    public static List<File> getFilesByPredicate(Path startPath, Predicate<File> predicate) {
        RecursiveFileSearcher searcher = new RecursiveFileSearcher(startPath, predicate);
        ForkJoinPool.commonPool().invoke(searcher);
        return searcher.join();
    }
}
