package edu.hw8.Task2;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class FixedThreadPool implements ThreadPool {

    private final Thread[] threads;

    private final Queue<Runnable> tasks;


    private FixedThreadPool(int numOfThreads) throws InterruptedException {
        threads = new Thread[numOfThreads];
        tasks = new LinkedBlockingQueue<>();

        for (int i = 0; i < numOfThreads; i++) {
            threads[i] = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    Runnable currentTask = null;

                    synchronized (tasks) {
                        while (tasks.isEmpty()) {
                            try {
                                tasks.wait();
                            } catch (InterruptedException e) {
                                return;
                            }
                        }
                        currentTask = tasks.poll();

                        if (currentTask != null) {
                            currentTask.run();
                        }
                    }
                }
            });
        }
    }

    public static FixedThreadPool create(int numOfThreads) throws InterruptedException {
        return new FixedThreadPool(numOfThreads);
    }

    @Override
    public void start() {
        int i = 0;
        while (!tasks.isEmpty() && i < threads.length) {
            if (threads[i] != null) {
                threads[i].start();
            }
            i++;
        }
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (tasks) {
            tasks.offer(runnable);
            tasks.notify();
        }
    }

    @Override
    public void close() throws Exception {
        for (Thread thread : threads) {
            if (thread != null) {
                thread.join();
            }
        }
    }

    public boolean stop() {
        synchronized (tasks) {
            if (tasks.isEmpty()) {
                Arrays.stream(threads).forEach(Thread::interrupt);
                return true;
            } else {
                return false;
            }
        }
    }
}
