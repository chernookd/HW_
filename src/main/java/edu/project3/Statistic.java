package edu.project3;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Statistic(
    int countOfRequests,
    List<Map.Entry<String, Integer>> theMostPopularRes,
    List<Map.Entry<Integer, Integer>> theMostPopularAnswerCode,
    List<Map.Entry<String, Integer>> errorsNotFound,
    List<Map.Entry<LocalDate, Integer>> busiestDays,
    long averageSizeOfServerAnswer,
    String path,
    String to,
    String from
) {

@SuppressWarnings("MultipleStringLiterals")
    public String toMdString() {
        StringBuilder sb = new StringBuilder();
        sb.append("# Статистика логов\n\n");
        sb.append("#### Общая информация\n");
        sb.append(String.format("|        Метрика        |     Значение |\n"));
        sb.append(String.format("|:---------------------:|-------------:|\n"));
        sb.append(String.format("|  %-20s |  %s |\n", "Файл(-ы)", path + "   "));
        sb.append(String.format("|    Начальная дата     |   " + from + "| \n"));
        sb.append(String.format("|     Конечная дата     |    "  +  to + "|\n"));
        sb.append(String.format("|  Количество запросов  |   %,d        |\n", countOfRequests));
        sb.append(String.format("| Средний размер ответа |   %,d        |\n\n", averageSizeOfServerAnswer));

        sb.append("#### Запрашиваемые ресурсы\n");
        sb.append(String.format("|     Ресурс      | Количество |\n"));
        sb.append(String.format("|:-------------------------:|--------:|\n"));
        for (Map.Entry<String, Integer> entry : theMostPopularRes) {
            sb.append(String.format("|  %-15s  |   %,d |\n", entry.getKey(), entry.getValue()));
        }
        sb.append("\n");

        sb.append("#### Самые популярные коды ответа\n");
        sb.append(String.format("| Код |          Имя          | Количество |\n"));
        sb.append(String.format("|:----:|:-----------------------:|-------:|\n"));
        for (Map.Entry<Integer, Integer> entry : theMostPopularAnswerCode) {
            sb.append(String.format("|  %-3d |  %-21s |   %,d |\n", entry.getKey(),
                getAnswerCodeName(entry.getKey()), entry.getValue()));
        }


        sb.append("#### Ошибки 404\n");
        sb.append(String.format("| htppUserAgent |Количество|\n"));
        sb.append(String.format("|:-------------:|:------------------------:|\n"));
        for (Map.Entry<String, Integer> entry : errorsNotFound) {
            sb.append(String.format("|  %-21s |  %,d  |\n", entry.getKey(), entry.getValue()));
        }

        sb.append("#### Самые нагруженные дни\n");
        sb.append(String.format("| День |Количество|\n"));
        sb.append(String.format("|:--------------:|:-----------------------:|\n"));
        for (Map.Entry<LocalDate, Integer> entry : busiestDays) {
            sb.append(String.format("|  %-21s |  %,d  |\n", entry.getKey().toString(), entry.getValue()));
        }

        return sb.toString();
    }

    private String shortenHttps(String path) {
        if (path.startsWith("https://")) {
            String regex = "(^https://)([^//]+)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(path);
            matcher.find();
            return matcher.group();
        }
        return path;
    }

    @SuppressWarnings("MultipleStringLiterals")
    public String toAdocString() {
        StringBuilder sb = new StringBuilder();


        sb.append("==== Общая информация\n");
        sb.append("|===\n");
        sb.append("|Метрика|Значение\n");
        sb.append(String.format("|Файл(-ы)      |%s\n", path));
        sb.append(String.format("|Начальная дата|%s\n", from));
        sb.append(String.format("|Конечная дата |%s\n", to));
        sb.append(String.format("|Количество запросов|%d\n", countOfRequests));
        sb.append(String.format("|Средний размер ответа|%d\n\n", averageSizeOfServerAnswer));
        sb.append("|===\n");

        sb.append("==== Запрашиваемые ресурсы\n");
        sb.append("|===\n");
        sb.append("|Ресурс|Количество\n");
        for (Map.Entry<String, Integer> entry : theMostPopularRes) {
            sb.append(String.format("|%s|%d\n", entry.getKey(), entry.getValue()));
        }
        sb.append("\n");
        sb.append("|===\n");


        sb.append("==== Коды ответа\n");
        sb.append("|===\n");
        sb.append("|Код|Имя|Количество\n");
        for (Map.Entry<Integer, Integer> entry : theMostPopularAnswerCode) {
            sb.append(String.format("|%d|%s|%d\n", entry.getKey(), getAnswerCodeName(entry.getKey()),
                entry.getValue()));
        }
        sb.append("|===\n");

        sb.append("==== Ошибки 404\n");
        sb.append("|===\n");
        sb.append("|htppUserAgent|Количество\n");
        for (Map.Entry<String, Integer> entry : errorsNotFound) {
            sb.append(String.format("|%s|%d\n", entry.getKey(), entry.getValue()));
        }
        sb.append("|===\n");

        sb.append("==== Нагруженные дни\n");
        sb.append("|===\n");
        sb.append("|День|Количество\n");
        for (Map.Entry<LocalDate, Integer> entry : busiestDays) {
            sb.append(String.format("|%s|%d\n", entry.getKey(), entry.getValue()));
        }
        sb.append("|===\n");

        return sb.toString();
    }

    static final int FIRST_NOT_FOUND_CODES = 400;
    static final int FIRST_INTERNAL_SERVER_ERROR_CODES = 500;
    static final int FIRST_MULTIPLE_CHOICES = 300;


    private String getAnswerCodeName(Integer key) {
        if (key < FIRST_MULTIPLE_CHOICES) {
            return "OK";
        } else if (key < FIRST_NOT_FOUND_CODES && key > FIRST_MULTIPLE_CHOICES) {
            return "Multiple choices";
        } else if (key < FIRST_INTERNAL_SERVER_ERROR_CODES && key >  FIRST_NOT_FOUND_CODES) {
            return "Not found";
        } else {
            return "Internal Server Error";
        }
    }

}
