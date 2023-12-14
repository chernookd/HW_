package edu.project3;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogAnalyzer {

    private LogAnalyzer() {

    }

    private static final int MIN_QUANTITY_OF_ARGUMENTS = 2;
    private static final DateTimeFormatter FORMATTER_ARGS_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @SuppressWarnings("RegexpSinglelineJava")
    public static void start(String[] args) throws IllegalArgumentException {
        if (args.length == 0) {
            throw new IllegalArgumentException();
        }
        List<String> arrayListArgs = parseArgString(args);
        if (arrayListArgs.size() < MIN_QUANTITY_OF_ARGUMENTS) {
            throw new IllegalArgumentException();
        }
        try {
            analysis(arrayListArgs);
        } catch (Exception e) {
            System.out.println("Error");
            System.exit(1);
        }
    }

    private static void analysis(List<String> arrayListArgs)
        throws Exception {
        List<LogRecord> arrayListOfLogsData = null;
        String path = null;
        String to = null;
        String from = null;
        String format = null;
        for (int i = 0; i < arrayListArgs.size() - 1; i++) {
            switch (arrayListArgs.get(i)) {
                case "--path": {
                    path = arrayListArgs.get(i + 1);
                    break;
                }
                case "--from": {
                    from = arrayListArgs.get(i + 1);
                    break;
                }
                case "--to": {
                    to = arrayListArgs.get(i + 1);
                    break;
                }
                case "--format": {
                    format = arrayListArgs.get(i + 1);
                    break;
                }
                default: {
                    continue;
                }
            }
        }

        if (path != null && path.isEmpty()) {
            throw new IllegalArgumentException();
        }

        try {
            arrayListOfLogsData = scanLogs(path);
        } catch (Exception e) {
            throw new Exception();
        }
        arrayListOfLogsData = filterByData(arrayListOfLogsData, from, to);
        Statistic statistic = CollectingStatistics.statisticsFromLogsData(arrayListOfLogsData, path, from, to);
        if (format == null) {
            Printer.printInConsole(statistic);
        } else if (format.equals("adoc")) {
            Printer.printAdocFile(statistic);
        } else if (format.equals("markdown")) {
            Printer.printInMdFile(statistic);
        } else {
            Printer.printInConsole(statistic);
        }
    }

    @SuppressWarnings({"CyclomaticComplexity", "ReturnCount", "MagicNumber"})
    public static List<LogRecord> filterByData(List<LogRecord> arrayListOfLogsData, String from, String to) {

        if ((to == null || to.isEmpty()) && (from == null || from.isEmpty())) {
            return arrayListOfLogsData;
        } else if (to != null && !to.isEmpty() && (from == null || from.isEmpty())) {
            try {
                LocalDate toTime = LocalDate.parse(to, FORMATTER_ARGS_DATE);
                LocalDateTime toLDT = toTime.atTime(23, 59, 59);

                return arrayListOfLogsData.stream()
                    .filter(logRecord -> logRecord.timeLocal().isBefore(toLDT))
                    .toList();

            } catch (Exception e) {
                return arrayListOfLogsData;
            }

        } else if ((to == null || to.isEmpty()) && from != null && !from.isEmpty()) {
            try {
                LocalDate fromTime = LocalDate.parse(from, FORMATTER_ARGS_DATE);
                LocalDateTime fromLDT = fromTime.atTime(0, 0, 0);

                return arrayListOfLogsData.stream()
                    .filter(logRecord -> logRecord.timeLocal().isAfter(fromLDT))
                    .toList();
            } catch (Exception e) {
                return arrayListOfLogsData;
            }

        } else {
            try {
                LocalDate toTime = LocalDate.parse(to, FORMATTER_ARGS_DATE);
                LocalDate fromTime = LocalDate.parse(from, FORMATTER_ARGS_DATE);
                LocalDateTime toLDT = toTime.atTime(23, 59, 59);
                LocalDateTime fromLDT = fromTime.atTime(0, 0, 0);

                return arrayListOfLogsData.stream()
                    .filter(logRecord -> logRecord.timeLocal().isBefore(toLDT)
                        && logRecord.timeLocal().isAfter(fromLDT))
                    .toList();
            } catch (Exception e) {
                return arrayListOfLogsData;
            }
        }
    }


    public static List<LogRecord> scanLogs(String path) throws IOException, InterruptedException {
        List<LogRecord> arrayListOfLogsData = new ArrayList<>();
        if (path.startsWith("https://")) {
            arrayListOfLogsData = scanHttp(path);
        } else {
            arrayListOfLogsData = scanLogsFile(path);
        }
        return arrayListOfLogsData;
    }

    private static List<LogRecord> scanLogsFile(String path) throws FileNotFoundException {
        List<LogRecord> arrayListOfLogsData = new ArrayList<>();
        File file = new File(path);
        if (file.isDirectory()) {
            arrayListOfLogsData = scanDirectory(path);
            return arrayListOfLogsData;
        }

        try (Scanner scanner = new Scanner(file)) {
            int i = 0;
            while (scanner.hasNext()) {
                String logLine = scanner.nextLine();
                i++;
                arrayListOfLogsData.add(parseLogStringToLogRecord(logLine));
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
        return arrayListOfLogsData;
    }

    private static List<LogRecord> scanDirectory(String path) throws FileNotFoundException {
        List<List<LogRecord>> listOfArrayListOfLogsData = new ArrayList<>();
        List<LogRecord> arrayListOfLogsData = new ArrayList<>();

        File file = new File(path);
        FileFilter txtFilter = new TxtFileFilter() {
            @Override
            public boolean accept(File pathname) {
                return TxtFileFilter.super.accept(pathname);
            }
        };

        File[] arrFiles = file.listFiles(txtFilter);
        if (arrFiles == null || arrFiles.length == 0) {
            throw new IllegalArgumentException();
        }
        for (File f : arrFiles) {
            listOfArrayListOfLogsData.add(scanLogsFile(f.toPath().toString()));
        }
        for (List<LogRecord> list : listOfArrayListOfLogsData) {
            arrayListOfLogsData.addAll(list);
        }
        return arrayListOfLogsData;
    }

    private static List<LogRecord> scanHttp(String path) throws IOException, InterruptedException {
        List<LogRecord> arrayListOfLogsData;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
            .uri(URI.create(path))
            .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        String[] bodyStringArray = body.split("\n");

        arrayListOfLogsData = Arrays.stream(bodyStringArray)
            .map(LogAnalyzer::parseLogStringToLogRecord)
            .collect(Collectors.toList());
        client.close();
        return arrayListOfLogsData;
    }

    @SuppressWarnings("MagicNumber")
    public static LogRecord parseLogStringToLogRecord(String logString) {
        String regex = "^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"([^\"]+)\" (\\d{3})"
            + " (\\d+) \"([^\"]+)\" \"([^\"]+)\"$";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(logString);


        if (matcher.find()) {
            return new LogRecord(
                matcher.group(1),
                matcher.group(2),
                LocalDateTime.parse(matcher.group(4), formatter),
                matcher.group(5),
                Integer.parseInt(matcher.group(6)),
                Long.parseLong(matcher.group(7)),
                matcher.group(8),
                matcher.group(9)
            );
        }

        return null;
    }

    private static List<String> parseArgString(String[] arg) {
        return new ArrayList<>(Arrays.asList(arg));
    }
}
