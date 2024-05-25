package Multipliers.OneToOne;

import Infrastructure.Matrix;
import mpi.MPI;

public class Blocking {
    public static void multiply(int rank, 
                                int numberOfProcessors, 
                                int matrixSize,
                                Matrix left,
                                Matrix right, 
                                Matrix result) {
        int rowsPerProcess = matrixSize / numberOfProcessors;
        int extraRows = matrixSize % numberOfProcessors;

        if (rank == 0) {
            sendRowsToWorkers(numberOfProcessors, rowsPerProcess, extraRows, matrixSize, left);
        } else {
            receiveRowsFromMaster(rank, rowsPerProcess, numberOfProcessors, extraRows, matrixSize, left);
        }

        exchangeRightMatrix(rank, numberOfProcessors, right);

        computeMatrixMultiplication(rank,
                numberOfProcessors,
                rowsPerProcess,
                extraRows,
                matrixSize,
                left,
                right,
                result);

        if (rank == 0) {
            collectResultsFromWorkers(numberOfProcessors, rowsPerProcess, extraRows, matrixSize, result);
        } else {
            sendResultToMaster(rank, numberOfProcessors, rowsPerProcess, extraRows, matrixSize, result);
        }
    }

    private static void sendRowsToWorkers(int numberOfProcessors,
                                          int rowsPerProcess,
                                          int extraRows,
                                          int matrixSize,
                                          Matrix left) {
        for (int dest = 1; dest < numberOfProcessors; dest++) {
            int rowsToSend = (dest == numberOfProcessors - 1) ? rowsPerProcess + extraRows : rowsPerProcess;
            MPI.COMM_WORLD.Send(left.getFlatMatrix(),
                    dest * rowsPerProcess * matrixSize,
                    rowsToSend * matrixSize,
                    MPI.INT,
                    dest,
                    0
            );
        }
    }

    private static void receiveRowsFromMaster(int rank,
                                              int rowsPerProcess,
                                              int numberOfProcessors,
                                              int extraRows,
                                              int matrixSize,
                                              Matrix left) {
        int rowsForWorker = (rank == numberOfProcessors - 1) ? rowsPerProcess + extraRows : rowsPerProcess;
        MPI.COMM_WORLD.Recv(
                left.getFlatMatrix(),
                rank * rowsPerProcess * matrixSize,
                rowsForWorker * matrixSize,
                MPI.INT,
                0,
                0
        );
    }

    private static void exchangeRightMatrix(int rank,
                                            int numberOfProcessors,
                                            Matrix right) {
        if (rank == 0) {
            for (int dest = 1; dest < numberOfProcessors; dest++) {
                MPI.COMM_WORLD.Send(right.getFlatMatrix(),
                        0,
                        right.getFlatMatrix().length,
                        MPI.INT,
                        dest,
                        0
                );
            }
        } else {
            MPI.COMM_WORLD.Recv(right.getFlatMatrix(),
                    0,
                    right.getFlatMatrix().length,
                    MPI.INT,
                    0,
                    0
            );
        }
    }

    private static void computeMatrixMultiplication(int rank,
                                                    int numberOfProcessors,
                                                    int rowsPerProcess,
                                                    int extraRows,
                                                    int matrixSize,
                                                    Matrix left,
                                                    Matrix right,
                                                    Matrix result) {
        int workerExtraRows = 0;

        if (rank == numberOfProcessors - 1) {
            workerExtraRows = extraRows;
        }

        for (int i = rank * rowsPerProcess; i < (rank + 1) * rowsPerProcess + workerExtraRows; i++) {
            for (int j = 0; j < matrixSize; j++) {
                for (int k = 0; k < matrixSize; k++) {
                    result.incrementElementBy(i, j, left.getElement(i, k) * right.getElement(k, j));
                }
            }
        }
    }

    private static void collectResultsFromWorkers(int numberOfProcessors,
                                                  int rowsPerProcess,
                                                  int extraRows,
                                                  int matrixSize,
                                                  Matrix result) {
        for (int source = 1; source < numberOfProcessors - 1; source++) {
            MPI.COMM_WORLD.Recv(
                    result.getFlatMatrix(),
                    source * rowsPerProcess * matrixSize,
                    rowsPerProcess * matrixSize,
                    MPI.INT,
                    source,
                    0
            );
        }

        MPI.COMM_WORLD.Recv(
                result.getFlatMatrix(),
                (numberOfProcessors - 1) * rowsPerProcess * matrixSize,
                (rowsPerProcess + extraRows) * matrixSize,
                MPI.INT,
                (numberOfProcessors - 1),
                0
        );
    }

    private static void sendResultToMaster(int rank,
                                           int numberOfProcessors,
                                           int rowsPerProcess,
                                           int extraRows,
                                           int matrixSize,
                                           Matrix result) {
        int rowsForWorker = rowsPerProcess;

        if (rank == numberOfProcessors - 1) {
            rowsForWorker += extraRows;
        }

        MPI.COMM_WORLD.Send(result.getFlatMatrix(),
                rank * rowsPerProcess * matrixSize,
                rowsForWorker * matrixSize,
                MPI.INT,
                0,
                0
        );
    }
}
