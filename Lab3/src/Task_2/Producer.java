package Task_2;

import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;
    private int messageAmount;
    private static int DONE_MESSAGE = -1;

    public Producer(Drop drop, int messageAmount) {
        this.drop = drop;
        this.messageAmount = messageAmount;
    }

    public void run() {
        int[] importantInfo = generateData(messageAmount);
        Random random = new Random();

        for (int i = 0; i < importantInfo.length; i++) {
            drop.put(importantInfo[i]);
            try {
                Thread.sleep(random.nextInt(50));
            } catch (InterruptedException e) {}
        }
        drop.put(-1);
    }

    private int[] generateData(int arraySize) {
        int[] data = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            data[i] = i;
        }
        return data;
    }
}