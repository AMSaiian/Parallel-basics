package Multipliers.OneToMany;

import Infrastructure.Matrix;
import mpi.MPI;

public class OneToMany {
    public static void multiply(int rank,
                                int numberOfProcessors,
                                int matrixSize,
                                Matrix left,
                                Matrix right,
                                Matrix result) {

        int rowsPerProcess = matrixSize / numberOfProcessors;
        int extraRows = matrixSize % numberOfProcessors;
        int[] elementsPerProcessors = new int[numberOfProcessors];
        int[] offsets = new int[numberOfProcessors];

        Matrix localLeftRows = distributeRows(
                rank,
                numberOfProcessors,
                rowsPerProcess,
                extraRows,
                matrixSize,
                left,
                right,
                elementsPerProcessors,
                offsets
        );

        Matrix localResult = computeMatrixMultiplication(localLeftRows, right, matrixSize);
        gatherResult(localResult, result, elementsPerProcessors, offsets);
    }

    private static Matrix distributeRows(
            int rank,
            int numberOfProcessors,
            int rowsPerProcess,
            int extraRows,
            int matrixSize,
            Matrix left,
            Matrix right,
            int[] elementsPerProcessors,
            int[] offsets) {

        for (int i = 0; i < numberOfProcessors - 1; i++) {
            elementsPerProcessors[i] = rowsPerProcess * matrixSize;
            offsets[i] = i * rowsPerProcess * matrixSize;
        }
        elementsPerProcessors[numberOfProcessors - 1] = (rowsPerProcess + extraRows) * matrixSize;
        offsets[numberOfProcessors - 1] = (numberOfProcessors - 2) * rowsPerProcess * matrixSize;

        Matrix localLeftRows;
        if (rank == numberOfProcessors - 1) {
            localLeftRows = new Matrix(rowsPerProcess + extraRows, matrixSize);
        } else {
          localLeftRows = new Matrix(rowsPerProcess, matrixSize);
        }


        MPI.COMM_WORLD.Scatterv(
                left.getFlatMatrix(),
                0,
                elementsPerProcessors,
                offsets,
                MPI.INT,
                localLeftRows.getFlatMatrix(),
                0,
                elementsPerProcessors[rank],
                MPI.INT,
                0
        );

        MPI.COMM_WORLD.Bcast(
                right.getFlatMatrix(),
                0,
                right.getFlatMatrix().length,
                MPI.INT,
                0
        );

        return localLeftRows;
    }

    private static Matrix computeMatrixMultiplication(Matrix localLeft,
                                                      Matrix right,
                                                      int matrixSize) {
        Matrix localResult = new Matrix(localLeft.getRowAmount(), localLeft.getColAmount());

        for (int i = 0; i < localLeft.getRowAmount(); i++) {
            for (int j = 0; j < right.getColAmount(); j++) {
                for (int k = 0; k < matrixSize; k++) {
                    localResult.incrementElementBy(i, j, localLeft.getElement(i, k) * right.getElement(k, j));
                }
            }
        }

        return localResult;
    }

    private static void gatherResult(Matrix localResult,
                                     Matrix result,
                                     int[] rowsPerProcessors,
                                     int[] offsets) {

        MPI.COMM_WORLD.Gatherv(
                localResult.getFlatMatrix(),
                0,
                localResult.getFlatMatrix().length,
                MPI.INT,
                result.getFlatMatrix(),
                0,
                rowsPerProcessors,
                offsets,
                MPI.INT,
                0);
    }
}
