package edu.hw8;

import edu.hw8.Task1.MultithreadServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.net.Socket;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task1Test {
    private final static String PERSONALITIES_REQUEST = "личности\n";
    private final static String INSULTS_REQUEST = "оскорбления\n";
    private final static String SILLY_REQUEST = "глупый\n";
    private final static String INTELLIGENCE_REQUEST = "интеллект\n";
    private final static String PERSONALITIES_ANSWER = "Не переходи на личности там, где их нет";
    private final static String INSULTS_ANSWER
        = "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами";
    private final static String SILLY_ANSWER
        = "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.";
    private final static String INTELLIGENCE_ANSWER = "Чем ниже интеллект, тем громче оскорбления";
    static MultithreadServer server;
    @BeforeAll static void setup() {
        server = new MultithreadServer();
        server.start();
    }
    @Test
    void AllServerAnswersTest() throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 8000);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader answersReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {

            out.write(PERSONALITIES_REQUEST);
            out.flush();
            String serverAns = answersReader.readLine();
            assertThat(PERSONALITIES_ANSWER).isEqualTo(serverAns);

            out.write(INSULTS_REQUEST);
            out.flush();
            serverAns = answersReader.readLine();
            assertThat(INSULTS_ANSWER).isEqualTo(serverAns);

            out.write(SILLY_REQUEST);
            out.flush();
            serverAns = answersReader.readLine();
            assertThat(SILLY_ANSWER).isEqualTo(serverAns);

            out.write(INTELLIGENCE_REQUEST);
            out.flush();
            serverAns = answersReader.readLine();
            assertThat(INTELLIGENCE_ANSWER).isEqualTo(serverAns);

        } catch (Exception e) {

        } finally {
            socket.close();
            out.close();
            answersReader.close();
        }
    }

    @Test
    void manyClientsTest() throws InterruptedException, IOException {
        int maxConnexions = MultithreadServer.getCONNEXIONS().get();
        assertTrue(MultithreadServer.getSEMAPHORE()
            .tryAcquire(maxConnexions - 1));
        assertFalse(MultithreadServer.getSEMAPHORE()
            .tryAcquire(maxConnexions + 1));

        Thread.sleep(1000);

        for (int i = 0; i < 10; i++) {
            try {
                Socket sockets = new Socket("localhost", 8000);
            } catch (IOException e) {}
        }
        Thread.sleep(100);
        assertFalse(MultithreadServer.getSEMAPHORE().tryAcquire());
    }
    @AfterAll static void close() throws IOException, InterruptedException {
        MultithreadServer.stop();
    }
}
