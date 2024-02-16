package Task_5;

public class writeThread extends Thread {
    private char symbol;
    private static int SYMBOL_AMOUNT = 100;
    private static int ROW_AMOUNT = 100;

    public writeThread(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public void run() {
        for (int i = 0; i < ROW_AMOUNT; i++) {
            for (int j = 0; j < SYMBOL_AMOUNT; j++) {
                System.out.print(symbol);
            }
            System.out.println();
        }
    }
}
