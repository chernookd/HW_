package edu.hw7;

import edu.hw7.Task3.Database;
import edu.hw7.Task3.Person;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3_5Test {

    @Test
    void task3_5DatabaseAddDeleteTest() throws InterruptedException {
        Database database = new Database();
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        ExecutorService executorServiceSecond = Executors.newFixedThreadPool(6);

        for (int i = 1; i < 100; i++) {
            int finalI = i;
            executorService.submit(() -> {
                database.add(new Person(finalI, String.valueOf(finalI),  String.valueOf(finalI),
                    String.valueOf(finalI)));
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        for (int i = 1; i < 100; i++) {
            assertThat(database.findByAddress(String.valueOf(i)).size()).isEqualTo(1);
            assertThat(database.findByName(String.valueOf(i)).size()).isEqualTo(1);
            assertThat(database.findByPhone(String.valueOf(i)).size()).isEqualTo(1);

            assertThat(database.findByAddress(String.valueOf(i))).isEqualTo(new ArrayList<>(List.of(new Person(i,
                String.valueOf(i), String.valueOf(i) ,String.valueOf(i)))));
            assertThat(database.findByName(String.valueOf(i))).isEqualTo(new ArrayList<>(List.of(new Person(i,
                String.valueOf(i), String.valueOf(i) ,String.valueOf(i)))));
            assertThat(database.findByPhone(String.valueOf(i))).isEqualTo(new ArrayList<>(List.of(new Person(i,
                String.valueOf(i), String.valueOf(i) ,String.valueOf(i)))));
        }

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executorServiceSecond.submit(() -> {
                database.delete(finalI);
            });
        }

        executorServiceSecond.shutdown();
        executorServiceSecond.awaitTermination(1, TimeUnit.MINUTES);


        for (int i = 0; i < 100; i++) {
            assertThat(database.findByAddress(String.valueOf(i)).size()).isEqualTo(0);
            assertThat(database.findByName(String.valueOf(i)).size()).isEqualTo(0);
            assertThat(database.findByPhone(String.valueOf(i)).size()).isEqualTo(0);
        }
    }

}
