package Task_6;

public class NonSyncCounter implements ICounter {
    private int counterValue = 0;
    @Override
    public void increment() {
        counterValue++;
    }

    @Override
    public void decrement() {
        counterValue--;
    }

    @Override
    public int getValue() {
        return counterValue;
    }
}
