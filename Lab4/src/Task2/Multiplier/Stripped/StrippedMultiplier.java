package Task2.Multiplier.Stripped;

import Task2.Multiplier.IMultiplier;
import Task2.Multiplier.Infrastructure.Helper;
import Task2.Multiplier.Infrastructure.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StrippedMultiplier implements IMultiplier {
    private final int threadAmount;

    public StrippedMultiplier(int threadAmount) {
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

        ExecutorService executor = Executors.newFixedThreadPool(this.threadAmount);

        ArrayList<Callable<Integer>> threads = new ArrayList<>(leftRows.length);
        List<Future<Integer>> threadResults = Collections.emptyList();

        for (int i = 0; i < leftRows.length; i++) {
            for (int j = 0; j < rightColumns.length; j++) {
                int[] row = leftRows[i];
                int[] column = rightColumns[j];

                Callable<Integer> thread = new StrippedThread(row, column);

                threads.add(thread);
            }
        }

        try {
            threadResults = executor.invokeAll(threads);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < threadResults.size(); i++) {
            try {
                resultMatrix[i / resultRowAmount][i % resultRowAmount] = threadResults.get(i).get();
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
