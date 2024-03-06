package Multiplier.Sequential;

import Infrastructure.Result;
import Multiplier.IMultiplier;

public class SequentialMultiplier implements IMultiplier {
    @Override
    public Result Multiply(int[][] leftMatrix, int[][] rightMatrix) {
        long timeStart = System.currentTimeMillis();

        int leftRowSize = leftMatrix[0].length;
        int rightColumnSize = rightMatrix.length;

        if (leftRowSize != rightColumnSize)
            throw new IllegalArgumentException("Row size of left matrix must be equal column size of right matrix");

        int resultRowAmount = leftMatrix.length;
        int resultColumnAmount = rightMatrix[0].length;

        int[][] resultMatrix = new int[resultRowAmount][resultColumnAmount];

        for (int i = 0; i < resultRowAmount; i++) {
            for (int j = 0; j < resultColumnAmount; j++) {
                for (int k = 0; k < leftRowSize; k++) {
                    resultMatrix[i][j] += leftMatrix[i][k] * rightMatrix[k][j];
                }
            }
        }

        long timeEnd = System.currentTimeMillis();
        long executionTime = timeEnd - timeStart;

        return new Result(resultMatrix, executionTime);
    }
}
