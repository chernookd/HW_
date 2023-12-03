package edu.hw8.Task2;

@SuppressWarnings("UncommentedMain")
public class FibonacciCalculateDemonstration {

    private FibonacciCalculateDemonstration() {
    }

    @SuppressWarnings({"MagicNumber", "RegexpSinglelineJava"})
    public static void main(String[] args) throws Exception {
        FixedThreadPool threadPool = FixedThreadPool.create(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < 31; ++i) {
            int finalI = i;
            threadPool.execute(() -> System.out.println("fibonacci number " + finalI + " " + fibonacci(finalI)));
        }
        threadPool.start();
        threadPool.stop();
    }

    private static int fibonacci(int n) {
        int past = 0;
        int current = 1;
        if (n <= 1) {
            return n;
        }
        for (int i = 2; i <= n; i++) {
            int sum = past + current;
            past = current;
            current = sum;

        }
        return current;
    }
}
