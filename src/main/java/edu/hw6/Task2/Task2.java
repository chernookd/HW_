package edu.hw6.Task2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2 {

    private Task2() {

    }

    public static Path cloneFile(Path path) {
        String regex = "^(.*) — копия(?: \\(\\d+\\))?(\\.[^.]+)$";
        Pattern pattern = Pattern.compile("\\((\\d+)\\)[^\\(]*$");
        String regex2 = "[^\\d]";
        String regex3 = "(\\.[^.]+)$";
        String newFileName;
        boolean isStartFileInDir = false;

        List<File> allFilesEqualsStartFile = new ArrayList<>();
        File startFile = path.toFile();
        String fileName = startFile.getName();
        File parrentFile = startFile.getParentFile();
        File[] allFilesInParrentDir = parrentFile.listFiles();
        if (allFilesInParrentDir.length == 0) {
            return null;
        }
        for (File f : allFilesInParrentDir) {
            if (f.getName().equals(startFile.getName())) {
                isStartFileInDir = true;
                break;
            }
        }
        if (!isStartFileInDir) {
            return null;
        }

        for (File file1 : allFilesInParrentDir) {
            if (Pattern.matches(regex, file1.getName())) {
                allFilesEqualsStartFile.add(file1);
            }
        }
        allFilesEqualsStartFile.add(startFile);

        int numberOfClone = 0;

        for (File file1 : allFilesEqualsStartFile) {
            Matcher matcher = pattern.matcher(file1.getName());
            if (!file1.getName().contains("копия(") && file1.getName().contains("копия") && numberOfClone == 0) {
                numberOfClone = 1;
            }
            if (matcher.find()) {
                if (numberOfClone < Integer.parseInt(matcher.group().replaceAll(regex2, ""))) {
                    numberOfClone = Integer.parseInt(matcher.group().replaceAll(regex2, ""));
                }
            }
        }
        if (numberOfClone == 0) {
            newFileName = fileName
                .replaceAll(regex3, " — копия$1");
        } else {
            newFileName = fileName
                .replaceAll(regex3, " — копия (" + (numberOfClone + 1) + ")$1");
        }
        File newFile = new File(parrentFile.getAbsolutePath(), newFileName);
        try {
            if (newFile.createNewFile()) {
                copyFile(startFile, newFile);
            }
            return newFile.toPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("MagicNumber")
    public static void copyFile(File src, File dest) throws IOException {
        try (InputStream is = new FileInputStream(src);
             OutputStream os = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        }
    }
}
