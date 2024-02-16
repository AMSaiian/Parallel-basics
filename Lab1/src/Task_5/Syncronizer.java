package Task_5;

public class Syncronizer {
    private static int SYMBOLS_IN_LINE = 100;
    private static int LINES = 100;
    private int writtenSymbols = 0;
    private int writtenLines = 0;
    private volatile boolean access = true;
    private volatile boolean stop = false;

    public Syncronizer() {
    }

    public boolean getAccess() {
        return access;
    }

    public boolean isStop() {
        return stop;
    }

    public synchronized void printSymbol(boolean control, char s) throws InterruptedException {
        while (getAccess() != control && !isStop()) {
            wait();
        }

        if (isStop()) {
            notifyAll();
            return;
        }

        System.out.print(s);
        access = !access;
        writtenSymbols++;

        if (writtenSymbols == 100) {
            writtenSymbols = 0;
            System.out.println();
            writtenLines++;
        }

        if (writtenLines == 100) {
            stop = true;
        }

        notifyAll();
    }
}
