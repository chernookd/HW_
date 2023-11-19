package edu.project3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static edu.project3.LogAnalyzer.scanLogs;
import static edu.project3.LogAnalyzer.start;
import static org.assertj.core.api.Assertions.assertThat;

public class StatisticTest {


    private static final String[] ALL_ARGS_WITH_PATH = new String[2];
    @BeforeEach
    void before() {
        ALL_ARGS_WITH_PATH[0] = "--path";
        ALL_ARGS_WITH_PATH[1] = "logs.txt";
    }

    @Test
    public void CorrectStatsTest() throws IOException, InterruptedException {
        List<Map.Entry<String, Integer>> popularRes = new ArrayList<>();
        popularRes.add(new AbstractMap.SimpleEntry<>("/downloads/product_1 HTTP/1.1", 9));
        popularRes.add(new AbstractMap.SimpleEntry<>("/downloads/product_2 HTTP/1.1", 4));

        List<Map.Entry<Integer, Integer>> popularAnswerCodes = new ArrayList<>();
        popularAnswerCodes.add(new AbstractMap.SimpleEntry<>(304, 8));
        popularAnswerCodes.add(new AbstractMap.SimpleEntry<>(200, 3));
        popularAnswerCodes.add(new AbstractMap.SimpleEntry<>(404, 2));

        List<Map.Entry<String, Integer>> errorsNotFound = new ArrayList<>();
        errorsNotFound.add(new AbstractMap.SimpleEntry<>("b", 2));

        LocalDate localDate = LocalDate.of(2015, Month.MAY, 17);

        List<Map.Entry<LocalDate, Integer>> busiestDays = new ArrayList<>();
        busiestDays.add(new AbstractMap.SimpleEntry<>(localDate, 13));

        File logsFile = new File("logs.txt");

        start(ALL_ARGS_WITH_PATH);

        Statistic statistic = CollectingStatistics
            .statisticsFromLogsData(
                scanLogs(ALL_ARGS_WITH_PATH[1]), ALL_ARGS_WITH_PATH[1],
                null, null);

        assertThat(statistic.countOfRequests()).isEqualTo(13);
        assertThat(statistic.averageSizeOfServerAnswer()).isEqualTo(381);
        assertThat(statistic.theMostPopularRes()).isEqualTo(popularRes);
        assertThat(statistic.theMostPopularAnswerCode()).isEqualTo(popularAnswerCodes);
        assertThat(statistic.errorsNotFound()).isEqualTo(errorsNotFound);
        assertThat(statistic.busiestDays()).isEqualTo(busiestDays);
    }
}
