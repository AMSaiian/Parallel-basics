package Multipliers;

import Infrastructure.Matrix;

public class Sequential {
    public static void multiply(int rank,
                           int matrixSize,
                           Matrix left,
                           Matrix right,
                           Matrix result) {
        if (rank == 0) {
            for (int i = 0; i < matrixSize; i++) {
                for (int j = 0; j < matrixSize; j++) {
                    for (int k = 0; k < matrixSize; k++) {
                        result.incrementElementBy(i, j, left.getElement(i, k) * right.getElement(k, j));
                    }
                }
            }
        }
    }
}
