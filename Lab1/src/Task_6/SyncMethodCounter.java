package Task_6;

public class SyncMethodCounter implements ICounter {
    private int counterValue = 0;
    @Override
    public synchronized void increment() {
        counterValue++;
    }

    @Override
    public synchronized void decrement() {
        counterValue--;
    }

    @Override
    public int getValue() {
        return counterValue;
    }
}
