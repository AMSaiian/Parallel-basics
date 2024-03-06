package Infrastructure;

import Multiplier.IMultiplier;
import Multiplier.Sequential.SequentialMultiplier;

public class SequentialTestCase {
    private final int matrixSize;

    private final int printAmount;

    private final int[][] leftMatrix;

    private final int[][] rightMatrix;

    public SequentialTestCase(int matrixSize, int printAmount, boolean isRandom) {
        this.matrixSize = matrixSize;
        this.printAmount = printAmount;

        if (isRandom) {
            leftMatrix = Helper.generateRandomMatrix(this.matrixSize, this.matrixSize);
            rightMatrix = Helper.generateRandomMatrix(this.matrixSize, this.matrixSize);
        } else {
            leftMatrix = Helper.generateMatrixOfOnes(this.matrixSize, this.matrixSize);
            rightMatrix = Helper.generateMatrixOfOnes(this.matrixSize, this.matrixSize);
        }
    }

    public long executeTest() {
        Result executionResult = new SequentialMultiplier().Multiply(leftMatrix, rightMatrix);

        System.out.printf("Sequential multiplier result(first %d rows):%n%n",
                printAmount);

        Helper.printMatrix(executionResult.value(), printAmount, printAmount);

        System.out.printf("%nExecution time in milliseconds = %d%n",
                executionResult.executionTime());

        return executionResult.executionTime();
    }
}
