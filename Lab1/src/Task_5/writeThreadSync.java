package Task_5;

public class writeThreadSync extends Thread {
    private char symbol;
    private Syncronizer syncronizer;
    private boolean controlValue;

    public writeThreadSync(char symbol, Syncronizer syncronizer, boolean controlValue) {
        this.symbol = symbol;
        this.syncronizer = syncronizer;
        this.controlValue = controlValue;
    }

    @Override
    public void run() {
        try {
            while (!syncronizer.isStop()) {
                syncronizer.printSymbol(controlValue, symbol);
            }
        } catch (InterruptedException ex) {
        }
    }
}
