package Task2.Multiplier;

import Task2.Multiplier.Infrastructure.ParallelTestCase;

public class Task2 {
    public static void main(String[] args) {
        int[] matrixSizes = { 1000, 1500, 2000 };
        int[] threadAmounts = { 12 };
        int printRowAmount = 5;

        for (int matrixSize : matrixSizes) {
            System.out.printf("%n%nMultipliers test with matrix size %d%n%n",
                    matrixSize);

            for (int threadAmount : threadAmounts) {
                System.out.printf("%nParallel computation with %d threads%n%n",
                        threadAmount);

                new ParallelTestCase(threadAmount, matrixSize, printRowAmount, false)
                        .executeTest();
            }
        }
    }
}
