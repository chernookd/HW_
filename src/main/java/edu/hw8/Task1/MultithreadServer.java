package edu.hw8.Task1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MultithreadServer {

    private static final AtomicInteger CONNEXIONS = new AtomicInteger(3);
    static final Semaphore SEMAPHORE = new Semaphore(3);
    private static ExecutorService executorService = Executors.newWorkStealingPool();
    private static final int PORT = 8000;

    public static Semaphore getSEMAPHORE() {
        return new Semaphore(SEMAPHORE.availablePermits());
    }

    public static AtomicInteger getCONNEXIONS() {
        return CONNEXIONS;
    }

    public void start() {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(PORT);
                while (true) {
                    try {
                        SEMAPHORE.acquire();
                        Socket clientSocket = serverSocket.accept();
                        executorService.submit(new Server(clientSocket));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static void stop() throws InterruptedException, IOException {
        executorService.shutdown();
        executorService.awaitTermination(2, TimeUnit.SECONDS);

    }
}

