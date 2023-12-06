package edu.hw6;

import edu.hw6.Task1.DiskMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

public class Task1Test {
    File testFile;
    DiskMap diskMap;
    public static final String STRING_PATH_TO_TEXT_FILE = "D:\\newFile.txt";
    @BeforeEach
    public void setup() throws IOException {
        testFile = new File(STRING_PATH_TO_TEXT_FILE);
        if(!testFile.exists()) {
            Files.createFile(Path.of(STRING_PATH_TO_TEXT_FILE));
            testFile = new File(STRING_PATH_TO_TEXT_FILE);
        }
        diskMap = new DiskMap(testFile.toPath());
        diskMap.put("1", "1");
        diskMap.put("2", "1");
        diskMap.put("3", "1");
        diskMap.put("4", "1");
        diskMap.put("5", "1");
    }
    @Test
    public void Task1WriteInFileTest() throws IOException {
        diskMap.write();
        BufferedReader reader = new BufferedReader(new FileReader(testFile));
        String str;
        int i = 1;
        while ((str = reader.readLine()) != null) {
            if(!str.equals(i + ":1")) {
                fail();
            }
            i++;
        }
        reader.close();
    }

    @Test
    public void Task1ReadFromFileTest() throws IOException {
        diskMap.write();
        diskMap.clear();
        diskMap.read();
        int i = 1;
        for (Map.Entry<String,String> entry : diskMap.entrySet()) {
            if(!entry.getValue().equals("1") || !entry.getKey().equals(String.valueOf(i))) {
                fail();
            }
            i++;
        }

    }
}
