package Task2.Multiplier.Infrastructure;

import Task2.Multiplier.FjStripped.FjStrippedMultiplier;
import Task2.Multiplier.IMultiplier;
import Task2.Multiplier.Stripped.StrippedMultiplier;

import java.util.HashMap;
import java.util.Map;

public class ParallelTestCase {
    private final int threadAmount;

    private final int matrixSize;

    private final int printAmount;

    private final int[][] leftMatrix;

    private final int[][] rightMatrix;

    public ParallelTestCase(int threadAmount,
                            int matrixSize,
                            int printAmount,
                            boolean isRandom) {
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
        multipliers.put("Fj stripped", new FjStrippedMultiplier(threadAmount));

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

        float fjStrippedSpeedUp = (float)timeResults.get("Stripped") / timeResults.get("Fj stripped");

        System.out.printf("Fork join stripped speed up upon default thread pool = %.2f%n", fjStrippedSpeedUp);
    }
}
