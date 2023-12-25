package edu.hw7.Task1;

public class IncThread extends Thread {

    @Override
    public void run() {
        Counter.counter.addAndGet(1);
    }
}
