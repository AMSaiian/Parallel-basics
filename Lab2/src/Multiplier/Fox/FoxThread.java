package Multiplier.Fox;

import java.util.concurrent.Callable;

public class FoxThread implements Callable<int[][]> {
    private final int[][] leftBlock;

    private final int[][] rightBlock;

    public FoxThread(int[][] leftBlock, int[][] rightBlock) {
        this.leftBlock = leftBlock;

        this.rightBlock = rightBlock;
    }

    @Override
    public int[][] call() {
        int blockSize = leftBlock.length;

        int[][] resultBlock = new int[blockSize][blockSize];

        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                for (int k = 0; k < blockSize; k++) {
                    resultBlock[i][j] += leftBlock[i][k] * rightBlock[k][j];
                }
            }
        }

        return resultBlock;
    }
}
