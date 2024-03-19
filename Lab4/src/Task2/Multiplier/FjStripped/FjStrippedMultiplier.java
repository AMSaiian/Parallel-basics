package Task2.Multiplier.FjStripped;

import Task2.Multiplier.IMultiplier;
import Task2.Multiplier.Infrastructure.Helper;
import Task2.Multiplier.Infrastructure.Result;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class FjStrippedMultiplier implements IMultiplier {
    private final int threadAmount;

    public FjStrippedMultiplier(int threadAmount) {
        this.threadAmount = threadAmount;
    }
    @Override
    public Result Multiply(int[][] leftMatrix, int[][] rightMatrix)  {
        long timeStart = System.currentTimeMillis();

        int leftRowSize = leftMatrix[0].length;
        int rightColumnSize = rightMatrix.length;

        if (leftRowSize != rightColumnSize)
            throw new IllegalArgumentException("Row size of left matrix must be equal column size of right matrix");

        int resultRowAmount = leftMatrix.length;
        int resultColumnAmount = rightMatrix[0].length;

        int[][] leftRows = new int[leftMatrix.length][leftMatrix[0].length];
        for (int i = 0; i < leftMatrix.length; i++) {
            leftRows[i] = Helper.getMatrixRow(leftMatrix, i);
        }

        int[][] rightColumns = new int[rightMatrix[0].length][rightMatrix.length];
        for (int i = 0; i < rightMatrix[0].length; i++) {
            rightColumns[i] = Helper.getMatrixColumn(rightMatrix, i);
        }

        int[][] resultMatrix = new int[resultRowAmount][resultColumnAmount];

        ForkJoinPool executor = new ForkJoinPool(threadAmount);

        ArrayList<FjStrippedThread> threads = new ArrayList<>(leftRows.length);

        for (int i = 0; i < leftRows.length; i++) {
            for (int j = 0; j < rightColumns.length; j++) {
                int[] row = leftRows[i];
                int[] column = rightColumns[j];

                FjStrippedThread thread = new FjStrippedThread(row, column);
                threads.add(thread);
                thread.fork();
            }
        }

        for (int i = 0; i < threads.size(); i++) {
            try {
                resultMatrix[i / resultRowAmount][i % resultRowAmount] = threads.get(i).join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        long timeEnd = System.currentTimeMillis();
        long executionTime = timeEnd - timeStart;

        return new Result(resultMatrix, executionTime);
    }
}
