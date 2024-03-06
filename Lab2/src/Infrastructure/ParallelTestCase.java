package Infrastructure;

import Multiplier.Fox.FoxMultiplier;
import Multiplier.IMultiplier;
import Multiplier.Stripped.StrippedMultiplier;

import java.util.*;

public class ParallelTestCase {
    private final int threadAmount;

    private final int matrixSize;

    private final int printAmount;

    private final float sequentialTimeExecution;

    private final int[][] leftMatrix;

    private final int[][] rightMatrix;

    public ParallelTestCase(int threadAmount,
                            long sequentialTimeExecution,
                            int matrixSize,
                            int printAmount,
                            boolean isRandom) {
        this.sequentialTimeExecution = sequentialTimeExecution;
        this.threadAmount = threadAmount;
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

    public void executeTest() {
        Map<String, IMultiplier> multipliers = new HashMap<>();
        Map<String, Long> timeResults = new HashMap<>();

        multipliers.put("Stripped", new StrippedMultiplier(threadAmount));
        multipliers.put("Fox", new FoxMultiplier(threadAmount));

        for (Map.Entry<String, IMultiplier> multiplierEntry : multipliers.entrySet()) {
            String multiplierType = multiplierEntry.getKey();
            IMultiplier multiplier = multiplierEntry.getValue();

            Result executionResult = multiplier.Multiply(leftMatrix, rightMatrix);

            timeResults.put(multiplierType, executionResult.executionTime());

            System.out.printf("%s multiplier result(first %d rows):%n%n",
                    multiplierType, printAmount);

            Helper.printMatrix(executionResult.value(), printAmount, printAmount);

            System.out.printf("%nExecution time in milliseconds = %d%n%n",
                    executionResult.executionTime());
        }

        float strippedSpeedUp = sequentialTimeExecution / timeResults.get("Stripped");
        float foxSpeedUp = sequentialTimeExecution / timeResults.get("Fox");

        System.out.printf("Stripped speed up = %.2f%n", strippedSpeedUp);
        System.out.printf("Fox speed up = %.2f%n", foxSpeedUp);
    }
}
