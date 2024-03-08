package Task_3;

public class Mark {
    private int value = 0;

    public synchronized void addMark(int value) {
        this.value += value;
    }

    public synchronized int getValue() {
        return value;
    }
}
