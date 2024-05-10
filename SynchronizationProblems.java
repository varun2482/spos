public class SynchronizationProblems {
    public static void main(String[] args) {
        BoundedBuffer buffer = new BoundedBuffer();

        // Producer-Consumer (bounded-buffer) problem
        Thread producerConsumerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    int data = (int) (Math.random() * 100);
                    buffer.produce(data);
                    Thread.sleep((long) (Math.random() * 1000));
                    buffer.consume();
                    Thread.sleep((long) (Math.random() * 1000));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Dining Philosophers problem
        Thread diningPhilosophersThread = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    Philosopher philosopher = new Philosopher(buffer, i);
                    philosopher.start();
                    philosopher.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Readers-Writers problem
        Thread readersWritersThread = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    Reader reader = new Reader(buffer, i);
                    reader.start();
                    reader.join();
                    Writer writer = new Writer(buffer, i);
                    writer.start();
                    writer.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Start threads for each problem separately
        producerConsumerThread.start();
        try {
            producerConsumerThread.join(); // Wait for bounded-buffer problem to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        diningPhilosophersThread.start();
        try {
            diningPhilosophersThread.join(); // Wait for dining philosophers problem to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        readersWritersThread.start();
        try {
            readersWritersThread.join(); // Wait for readers-writers problem to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
