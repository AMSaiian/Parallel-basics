package Task2.Multiplier.Stripped;

import java.util.concurrent.Callable;

public class StrippedThread implements Callable<Integer> {
    private final int[] leftRow;

    private final int[] rightColumn;

    public StrippedThread(int[] leftRow, int[] rightColumn) {
        this.leftRow = leftRow;
        this.rightColumn = rightColumn;
    }
    @Override
    public Integer call() {
        int resultElement = 0;
        int multiplicationsAmount = leftRow.length;

        for (int i = 0; i < multiplicationsAmount; i++) {
            resultElement += leftRow[i] * rightColumn[i];
        }

        return resultElement;
    }
}
