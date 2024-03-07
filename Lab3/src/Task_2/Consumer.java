package Task_2;

import java.util.Random;

public class Consumer implements Runnable {
    private Drop drop;
    private static int DONE_MESSAGE = -1;

    public Consumer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        Random random = new Random();
        for (int message = drop.take();
             ! (message == DONE_MESSAGE);
             message = drop.take()) {
            System.out.format("MESSAGE RECEIVED: %d%n", message);
            try {
                Thread.sleep(random.nextInt(50));
            } catch (InterruptedException e) {}
        }
    }
}