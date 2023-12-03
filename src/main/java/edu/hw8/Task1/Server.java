package edu.hw8.Task1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server implements Runnable {
    private final Socket clientSocket;

    public Server(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;

    }

    static final List<String> ANSWERS = new CopyOnWriteArrayList<>(List.of(
        "Не переходи на личности там, где их нет",
        "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами",
        "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.",
        "Чем ниже интеллект, тем громче оскорбления"
    ));

    @Override
    public void run() {
        try {
            BufferedWriter bufferedWriter =
                new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String request;
            String serverAnswer = null;
            while ((request = bufferedReader.readLine()) != null) {
                for (int i = 0; i < ANSWERS.size(); i++) {
                    if (ANSWERS.get(i).contains(request)) {
                        serverAnswer = ANSWERS.get(i);
                        break;
                    }
                }
                bufferedWriter.write(serverAnswer + "\n");
                bufferedWriter.flush();
            }
        } catch (Exception e) {

        } finally {
            MultithreadServer.SEMAPHORE.release();
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
