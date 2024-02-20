package Task_6;

public class SyncBlockCounter implements ICounter {
    private int count = 0;
    @Override
    public void increment() {
        synchronized(this) {
            count++;
        }
    }

    @Override
    public void decrement() {
        synchronized(this) {
            count--;
        }
    }

    @Override
    public int getValue() {
        return count;
    }
}
