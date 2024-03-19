package Task2.Multiplier.FjStripped;

import java.util.concurrent.RecursiveTask;

public class FjStrippedThread extends RecursiveTask<Integer> {
    private final int[] leftRow;

    private final int[] rightColumn;

    public FjStrippedThread(int[] leftRow, int[] rightColumn) {
        this.leftRow = leftRow;
        this.rightColumn = rightColumn;
    }

    @Override
    protected Integer compute() {
        int resultElement = 0;
        int multiplicationsAmount = leftRow.length;

        for (int i = 0; i < multiplicationsAmount; i++) {
            resultElement += leftRow[i] * rightColumn[i];
        }

        return resultElement;
    }
}
