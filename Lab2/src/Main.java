import Infrastructure.ParallelTestCase;
import Infrastructure.SequentialTestCase;

public class Main {
    public static void main(String[] args) {
        int[] matrixSizes = { 500 };
        int[] threadAmounts = { 4 };
        int printRowAmount = 5;

        for (int matrixSize : matrixSizes) {
            System.out.printf("%n%nMultipliers test with matrix size %d%n%n",
                    matrixSize);

            long sequentialTimeExecution =
                    new SequentialTestCase(matrixSize, printRowAmount, false)
                            .executeTest();

            for (int threadAmount : threadAmounts) {
                System.out.printf("%nParallel computation with %d threads%n%n",
                        threadAmount);

                new ParallelTestCase(threadAmount, sequentialTimeExecution, matrixSize, printRowAmount, false)
                        .executeTest();
            }
        }
    }
}