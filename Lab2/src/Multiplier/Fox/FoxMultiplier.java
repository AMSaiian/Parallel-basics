package Multiplier.Fox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

import Infrastructure.Helper;
import Infrastructure.Result;
import Multiplier.IMultiplier;

public class FoxMultiplier implements IMultiplier {
    private final int threadAmount;

    public FoxMultiplier(int threadAmount) {
        this.threadAmount = threadAmount;
    }

    @Override
    public Result Multiply(int[][] leftMatrix, int[][] rightMatrix) {
        long timeStart = System.currentTimeMillis();

        int blockSize = leftMatrix[0].length / 10;
        int leftRowSize = leftMatrix[0].length;
        int rightColumnSize = rightMatrix.length;

        if (leftRowSize != rightColumnSize)
            throw new IllegalArgumentException("Row size of left matrix must be equal column size of right matrix");

        int resultRowAmount = leftMatrix.length;
        int resultColumnAmount = rightMatrix[0].length;

        int[][] resultMatrix = new int[resultRowAmount][resultColumnAmount];

        int[][][][] leftBlocks = Helper.getMatrixBlocks(leftMatrix, blockSize);
        int[][][][] rightBlocks = Helper.getMatrixBlocks(rightMatrix, blockSize);

        int blocksInRowCount = leftBlocks[0].length;

        int[][][][] resultBlocks = new int[blocksInRowCount][blocksInRowCount][][];

        ExecutorService executor = Executors.newFixedThreadPool(this.threadAmount);

        ArrayList<Callable<int[][]>> threads = new ArrayList<>();
        List<Future<int[][]>> threadResults = Collections.emptyList();

        for (int i = 0; i < blocksInRowCount; i++) {
            for (int j = 0; j < blocksInRowCount; j++) {
                for (int k = 0; k < blocksInRowCount; k++) {
                    Callable<int[][]> thread = new FoxThread(leftBlocks[i][k], rightBlocks[k][j]);
                    threads.add(thread);
                }
            }
        }

        try {
            threadResults = executor.invokeAll(threads);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<int[][]> computationResult = threadResults.stream().map(x -> {
            try {
                return x.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).toList();

        for (int i = 0; i < threadResults.size() / blocksInRowCount; i++) {
            int startIndex = i * blocksInRowCount;

            List<int[][]> resultBlockParts = computationResult.subList(startIndex, startIndex + blocksInRowCount);
            resultBlocks[i / blocksInRowCount][i % blocksInRowCount] = Helper.sumMatrices(resultBlockParts);
        }

        executor.shutdown();

        Helper.setMatrixBlocks(resultMatrix, resultBlocks);

        long timeEnd = System.currentTimeMillis();
        long executionTime = timeEnd - timeStart;

        return new Result(resultMatrix, executionTime);
    }
}
