package edu.project3;

import java.io.FileWriter;
import java.io.IOException;

public class Printer {

    private Printer() {

    }

    @SuppressWarnings("RegexpSinglelineJava")
    public static void printInConsole(Statistic statistic) {
        System.out.println(statistic.toMdString());
    }

    @SuppressWarnings("RegexpSinglelineJava")

    public static void printInMdFile(Statistic statistic) {
        String fileName = "logsInfo.md";
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(statistic.toMdString());
        } catch (IOException ignored) {
        }
    }

    @SuppressWarnings("RegexpSinglelineJava")
    public static void printAdocFile(Statistic statistic) {
        String fileName = "logsInfo.adoc";
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(statistic.toAdocString());
        } catch (IOException ignored) {
        }
    }

}
