package edu.hw9.Task2;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

class RecursiveDirectorySearcher extends RecursiveTask<List<File>> {
    private final static int FILES_IN_DIRECTORY = 1000;
    private final Path startDirectory;

    RecursiveDirectorySearcher(Path directory) {
        this.startDirectory = directory;
    }

    @Override
    protected List<File> compute() {
        List<File> directoryWithMoreThan1000Files = new ArrayList<>();
        List<RecursiveDirectorySearcher> tasks = new ArrayList<>();
        File[] filesInDirectory = startDirectory.toFile().listFiles();

        if (filesInDirectory != null) {
            for (File file : filesInDirectory) {
                if (file.isDirectory()) {
                    RecursiveDirectorySearcher task = new RecursiveDirectorySearcher(file.toPath());
                    task.fork();
                    tasks.add(task);
                }
            }

            for (RecursiveDirectorySearcher task : tasks) {
                directoryWithMoreThan1000Files.addAll(task.join());
            }

            if (filesInDirectory.length > FILES_IN_DIRECTORY) {
                directoryWithMoreThan1000Files.add(startDirectory.toFile());
            }
        }

        return directoryWithMoreThan1000Files;
    }
}
