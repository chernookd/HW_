package edu.project3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Month;
import java.util.List;
import static edu.project3.LogAnalyzer.scanLogs;
import static edu.project3.LogAnalyzer.start;
import static org.junit.jupiter.api.Assertions.*;

public class TestOfWholeProgram {

    private static final String[] ARGS_HTTPS_FROM_TO_MARKDOWN = new String[8];
    private static final String[] ALL_ARGS_WITH_HTTPS_FROM_TO_ADOC = new String[8];
    private static final String[] ALL_ARGS_WITH_DIR_FROM_TO_MARKDOWN = new String[8];

    @BeforeEach
    void before() {
        ARGS_HTTPS_FROM_TO_MARKDOWN[0] = "--path";
        ARGS_HTTPS_FROM_TO_MARKDOWN[1] =
            "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";
        ARGS_HTTPS_FROM_TO_MARKDOWN[2] = "--from";
        ARGS_HTTPS_FROM_TO_MARKDOWN[3] = "2000-05-20";
        ARGS_HTTPS_FROM_TO_MARKDOWN[4] = "--to";
        ARGS_HTTPS_FROM_TO_MARKDOWN[5] = "2025-05-20";
        ARGS_HTTPS_FROM_TO_MARKDOWN[6] = "--format";
        ARGS_HTTPS_FROM_TO_MARKDOWN[7] = "markdown";

        ALL_ARGS_WITH_HTTPS_FROM_TO_ADOC[0] = "--path";
        ALL_ARGS_WITH_HTTPS_FROM_TO_ADOC[1] = "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";
        ALL_ARGS_WITH_HTTPS_FROM_TO_ADOC[2] = "--from";
        ALL_ARGS_WITH_HTTPS_FROM_TO_ADOC[3] = "2000-05-20";
        ALL_ARGS_WITH_HTTPS_FROM_TO_ADOC[4] = "--to";
        ALL_ARGS_WITH_HTTPS_FROM_TO_ADOC[5] = "2025-05-20";
        ALL_ARGS_WITH_HTTPS_FROM_TO_ADOC[6] = "--format";
        ALL_ARGS_WITH_HTTPS_FROM_TO_ADOC[7] = "adoc";

        ALL_ARGS_WITH_DIR_FROM_TO_MARKDOWN[0] = "--path";
        ALL_ARGS_WITH_DIR_FROM_TO_MARKDOWN[1] = "log";
        ALL_ARGS_WITH_DIR_FROM_TO_MARKDOWN[2] = "--from";
        ALL_ARGS_WITH_DIR_FROM_TO_MARKDOWN[3] = "2015-05-20";
        ALL_ARGS_WITH_DIR_FROM_TO_MARKDOWN[4] = "--to";
        ALL_ARGS_WITH_DIR_FROM_TO_MARKDOWN[5] = "2015-05-21";
        ALL_ARGS_WITH_DIR_FROM_TO_MARKDOWN[6] = "--format";
        ALL_ARGS_WITH_DIR_FROM_TO_MARKDOWN[7] = "adoc";

    }



    @Test
    public void RunningTheProgramWithAllArgumentsFormatMDTest() throws IOException, InterruptedException {
        File mdFile = new File("logsInfo.md");
        try {

            assertFalse(mdFile.exists());

            start(ARGS_HTTPS_FROM_TO_MARKDOWN);

            mdFile = new File("logsInfo.md");

            assertTrue(mdFile.exists());

            Statistic statistic = CollectingStatistics
                .statisticsFromLogsData(
                    scanLogs(ARGS_HTTPS_FROM_TO_MARKDOWN[1]), ARGS_HTTPS_FROM_TO_MARKDOWN[1],
                    ARGS_HTTPS_FROM_TO_MARKDOWN[3], ARGS_HTTPS_FROM_TO_MARKDOWN[5]
                );

            assertEquals(readFile(mdFile.toPath().toString(), StandardCharsets.UTF_8), statistic.toMdString());

            mdFile.delete();
        } catch (Exception e) {
            mdFile.delete();
            fail();
        }
    }

    @Test
    public void RunningTheProgramWithAllArgumentsFormatADOCTest() throws IOException, InterruptedException {
        File adocFile = new File("logsInfo.adoc");
        try {
            assertFalse(adocFile.exists());

            start(ALL_ARGS_WITH_HTTPS_FROM_TO_ADOC);

            adocFile = new File("logsInfo.adoc");

            assertTrue(adocFile.exists());

            Statistic statistic = CollectingStatistics
                .statisticsFromLogsData(
                    scanLogs(ALL_ARGS_WITH_HTTPS_FROM_TO_ADOC[1]), ALL_ARGS_WITH_HTTPS_FROM_TO_ADOC[1],
                    ALL_ARGS_WITH_HTTPS_FROM_TO_ADOC[3], ALL_ARGS_WITH_HTTPS_FROM_TO_ADOC[5]
                );

            assertEquals(readFile(adocFile.toPath().toString(), StandardCharsets.UTF_8), statistic.toAdocString());

            adocFile.delete();
        } catch (Exception e) {
            adocFile.delete();
            fail();
        }
    }

    @Test
    public void RunningTheProgramWithAllArgumentsDateTest() throws IOException, InterruptedException {
        File logsFile = new File("logsInfo.adoc");

        try {
            start(ALL_ARGS_WITH_DIR_FROM_TO_MARKDOWN);

            assertTrue(logsFile.exists());

            Statistic statistic = CollectingStatistics
                .statisticsFromLogsData(
                    scanLogs(ALL_ARGS_WITH_DIR_FROM_TO_MARKDOWN[1]), ALL_ARGS_WITH_DIR_FROM_TO_MARKDOWN[1],
                    ALL_ARGS_WITH_DIR_FROM_TO_MARKDOWN[3], ALL_ARGS_WITH_DIR_FROM_TO_MARKDOWN[5]
                );

            List<LogRecord> logRecordList = LogAnalyzer.scanLogs(ALL_ARGS_WITH_DIR_FROM_TO_MARKDOWN[1]);
            logRecordList = LogAnalyzer
                .filterByData(
                    logRecordList,
                    ALL_ARGS_WITH_DIR_FROM_TO_MARKDOWN[3],
                    ALL_ARGS_WITH_DIR_FROM_TO_MARKDOWN[5]
                );

            for (LogRecord logRecord : logRecordList) {
                assertEquals(2015, logRecord.timeLocal().getYear());
                assertSame(logRecord.timeLocal().getMonth(), Month.MAY);
                assertTrue(logRecord.timeLocal().getDayOfMonth() >= 20
                    && logRecord.timeLocal().getDayOfMonth() <= 21);
                logsFile.delete();
            }
        } catch (Exception e) {
            logsFile.delete();
            fail();
        }
    }

    public static String readFile(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
