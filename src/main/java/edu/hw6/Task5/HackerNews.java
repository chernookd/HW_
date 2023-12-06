package edu.hw6.Task5;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HackerNews {

    private HackerNews() {

    }

    public static HttpClient client = HttpClient.newHttpClient();

    public static long[] hackerNewsTopStories() {
        try {
            List<Long> idOfTopStories = new ArrayList<>();
            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://hacker-news.firebaseio.com/v0/topstories.json"))
             .build();
            HttpResponse<String> response = null;

            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(responseBody);
            while (matcher.find()) {
                idOfTopStories.add(Long.parseLong(matcher.group()));
            }
            return idOfTopStories.stream().mapToLong(Long::longValue).toArray();
        } catch (IOException | InterruptedException e) {
            return new long[0];
        }
    }

    public static String news(long id) {
        try {
            URI uri = URI.create(String.format("https://hacker-news.firebaseio.com/v0/item/%d.json", id));
            HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String regex = "\"title\": ?\"((.|(\"(.*?)\"))*?)\",";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(response.body());
            if (matcher.find()) {
                return matcher.group(1);
            }
        } catch (IOException | InterruptedException ignored) {
            return "";
        }
        return "";
    }

}
