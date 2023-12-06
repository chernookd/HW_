package edu.hw9.Task2;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

class RecursiveFileSearcher extends RecursiveTask<List<File>> {
    private final Path startDirectory;
    private final Predicate<File> predicate;

    RecursiveFileSearcher(Path directory, Predicate<File> predicate) {
        this.startDirectory = directory;
        this.predicate = predicate;
    }

    @Override
    protected List<File> compute() {
        List<File> correctFiles = new ArrayList<>();
        List<RecursiveFileSearcher> tasks = new ArrayList<>();
        File[] filesInDirectory = startDirectory.toFile().listFiles();

        if (filesInDirectory != null) {
            for (File file : filesInDirectory) {
                if (file.isDirectory()) {
                    RecursiveFileSearcher task = new RecursiveFileSearcher(file.toPath(), predicate);
                    task.fork();
                    tasks.add(task);
                } else if (predicate.test(file)) {
                    correctFiles.add(file);
                }
            }

            for (RecursiveFileSearcher task : tasks) {
                correctFiles.addAll(task.join());
            }
        }

        return correctFiles;
    }
}
