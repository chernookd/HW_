package edu.project3;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectingStatistics {

    private CollectingStatistics() {

    }

    private static final int NOT_FOUND_STATUS = 404;
    private static final int MAX_SIZE_OF_MAP = 3;

    public static Statistic statisticsFromLogsData(
        List<LogRecord> logRecordList, String path, String fromDateStr, String toDateStr) {
        if (logRecordList.isEmpty()) {
            return new Statistic(0, null,
                null, null, null, 0,
                null, null, null);
        }
        int countOfRequests = countOfRequest(logRecordList);
        List<Map.Entry<String, Integer>> theMostPopularRes = theMostPopularRes(logRecordList);
        List<Map.Entry<Integer, Integer>> theMostPopularAnswerCode = theMostPopularAnswerCode(logRecordList);
        List<Map.Entry<String, Integer>> errorsNotFound = logsGets404ErrorMostOften(logRecordList);
        List<Map.Entry<LocalDate, Integer>> busiestDays = busiestDays(logRecordList);
        long averageSizeOfServerAnswer = averageSizeOfServerAnswer(logRecordList);
        return new Statistic(countOfRequests, theMostPopularRes,
            theMostPopularAnswerCode, errorsNotFound, busiestDays,
            averageSizeOfServerAnswer, path, fromDateStr, toDateStr);
    }

    private static long averageSizeOfServerAnswer(List<LogRecord> logRecordList) {
        long sumOfAllServerAnswer = logRecordList.stream()
            .mapToLong(LogRecord::bodyBytesSent)
            .sum();
        return sumOfAllServerAnswer / countOfRequest(logRecordList);
    }

    private static List<Map.Entry<String, Integer>> theMostPopularRes(List<LogRecord> logRecordList) {
        Map<String, Integer> popularRes = new HashMap<>();
        for (int i = 0; i < logRecordList.size(); i++) {
            Integer value;
            if (popularRes.containsKey(truncateHeadAndGet(logRecordList.get(i).request()))) {
                value = popularRes.get(truncateHeadAndGet(logRecordList.get(i).request()));
                value++;
            } else {
                value = 1;
            }
            popularRes.put(truncateHeadAndGet(logRecordList.get(i).request()), value);
        }
        List<Map.Entry<String, Integer>> sortedPopularAnswerCode =
            popularRes.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());
        if (sortedPopularAnswerCode.size() > MAX_SIZE_OF_MAP) {
            return sortedPopularAnswerCode.subList(0, MAX_SIZE_OF_MAP);
        }
        return sortedPopularAnswerCode;
    }

    private static String truncateHeadAndGet(String logLine) {
        return logLine.replaceAll("(HEAD |GET )", "");
    }

    private static List<Map.Entry<Integer, Integer>> theMostPopularAnswerCode(List<LogRecord> logRecordList) {
        Map<Integer, Integer> popularAnswerCode = new HashMap<>();
        for (int i = 0; i < logRecordList.size(); i++) {
            Integer value;
            if (popularAnswerCode.containsKey(logRecordList.get(i).status())) {
                value = popularAnswerCode.get(logRecordList.get(i).status());
                value++;
            } else {
                value = 1;
            }
            popularAnswerCode.put(logRecordList.get(i).status(), value);
        }
        List<Map.Entry<Integer, Integer>> sortedPopularAnswerCode =
                new ArrayList<>(popularAnswerCode.entrySet());
        sortedPopularAnswerCode.sort(Comparator.comparingInt(Map.Entry::getValue));
        sortedPopularAnswerCode = sortedPopularAnswerCode.reversed();
        if (sortedPopularAnswerCode.size() > MAX_SIZE_OF_MAP) {
            return sortedPopularAnswerCode.subList(0, MAX_SIZE_OF_MAP);
        }
        return sortedPopularAnswerCode;
    }

    private static List<Map.Entry<String, Integer>> logsGets404ErrorMostOften(List<LogRecord> logRecordList) {
        Map<String, Integer> notFoundMap  = new HashMap<>();
        for (int i = 0; i < logRecordList.size(); i++) {
            if (logRecordList.get(i).status() != NOT_FOUND_STATUS) {
                continue;
            }
            Integer value;
            if (notFoundMap.containsKey(logRecordList.get(i).htppUserAgent())) {
                value = notFoundMap.get(logRecordList.get(i).htppUserAgent());
                value++;
            } else {
                value = 1;
            }
            notFoundMap.put(logRecordList.get(i).htppUserAgent(), value);
        }
        List<Map.Entry<String, Integer>> sortedNotFoundMap =
            notFoundMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());
        if (sortedNotFoundMap.size() > MAX_SIZE_OF_MAP) {
            return sortedNotFoundMap.subList(0, MAX_SIZE_OF_MAP);
        }
        return sortedNotFoundMap;
    }

    public static List<Map.Entry<LocalDate, Integer>> busiestDays(List<LogRecord> logRecordList) {
        Map<LocalDate, Integer> busiestDayMap = new HashMap<>();
        for (int i = 0; i < logRecordList.size(); i++) {
            Integer value = 1;
            LocalDate localDate = LocalDate.of(logRecordList.get(i).timeLocal().getYear(),
                logRecordList.get(i).timeLocal().getMonth(),
                logRecordList.get(i).timeLocal().getDayOfMonth());
            if (busiestDayMap.containsKey(localDate)) {
                value = busiestDayMap.get(localDate);
                value++;
            }
            busiestDayMap.put(localDate, value);
        }

        List<Map.Entry<LocalDate, Integer>> sotedbusiestDayMap = busiestDayMap.entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .collect(Collectors.toList()).reversed();
        if (sotedbusiestDayMap.size() > MAX_SIZE_OF_MAP) {
            return sotedbusiestDayMap.subList(0, MAX_SIZE_OF_MAP);
        }
        return sotedbusiestDayMap;
    }

    private static int countOfRequest(List<LogRecord> logRecordList) {
        return logRecordList.size();
    }

}
