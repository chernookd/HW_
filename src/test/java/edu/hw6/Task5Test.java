package edu.hw6;

import edu.hw6.Task5.HackerNews;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.stream.Stream;
import static edu.hw6.Task5.HackerNews.hackerNewsTopStories;
import static edu.hw6.Task5.HackerNews.news;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task5Test {

    private static Stream<Arguments> sourseForNewsTest() {
        return Stream.of(
            Arguments.of(38255455, "Inequalities, convergence, and continuity as \\\"special deals\\\""),
            Arguments.of(1, "Y Combinator"),
            Arguments.of(2, "A Student's Guide to Startups"),
            Arguments.of(38230494, "Master Foo and the Unix Zealot")
            );
    }

    @ParameterizedTest
    @MethodSource("sourseForNewsTest")
    void Task5NewsTest(long id, String correctTitle) {
        HackerNews.client = HttpClient.newHttpClient();
        String title = news(id);
        assertThat(title).isEqualTo(correctTitle);
    }

    @Test
    void Task5TopNewsTest() {
        long[] topNewsID = hackerNewsTopStories();
        assertThat(isContains(38285482, topNewsID)).isTrue();
        assertThat(isContains(38282166, topNewsID)).isTrue();
        assertThat(isContains(38286163, topNewsID)).isTrue();
        assertThat(isContains(38285228, topNewsID)).isTrue();
        assertThat(isContains(38304109, topNewsID)).isTrue();
        assertThat(isContains(38280472, topNewsID )).isTrue();
        assertThat(isContains(38273948, topNewsID )).isTrue();
        System.out.println(news(700));

        System.out.println(news(700));

    }

    private static boolean isContains(long id, long[] arr) {
        return Arrays.stream(arr)
            .anyMatch(e -> e == id);
    }


}
