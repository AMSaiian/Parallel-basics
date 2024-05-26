package Multipliers.OneToOne;

import Infrastructure.Matrix;
import mpi.MPI;
import mpi.Request;

public class NonBlocking {
    public static void multiply(int rank,
                                int numberOfProcessors,
                                int matrixSize,
                                Matrix left,
                                Matrix right,
                                Matrix result) {
        if (rank <= numberOfProcessors - 1) {
            int rowsPerProcess = matrixSize / numberOfProcessors;
            int extraRows = matrixSize % numberOfProcessors;
            Request[] requests = new Request[numberOfProcessors * 2];

            if (rank == 0) {
                sendMatricesToWorkers(requests, numberOfProcessors, rowsPerProcess, extraRows, matrixSize, left);
            } else {
                receiveMatrixFromMaster(requests, rank, numberOfProcessors, rowsPerProcess, extraRows, matrixSize, left);
            }

            exchangeMatrixBetweenProcesses(requests, rank, numberOfProcessors, matrixSize, right);

            Request.Waitall(requests);

            computeMatrixMultiplication(rank, numberOfProcessors, rowsPerProcess, extraRows, matrixSize, left, right, result);

            if (rank == 0) {
                collectResultsFromWorkers(requests, numberOfProcessors, rowsPerProcess, extraRows, matrixSize, result);
            } else {
                sendResultToMaster(requests, rank, numberOfProcessors, rowsPerProcess, extraRows, matrixSize, result);
            }

            Request.Waitall(requests);
        }
    }

    private static void sendMatricesToWorkers(Request[] requests,
                                              int numberOfProcessors,
                                              int rowsPerProcess,
                                              int extraRows,
                                              int matrixSize,
                                              Matrix left) {
        for (int dest = 1; dest < numberOfProcessors; dest++) {
            int rowsToSend = (dest == numberOfProcessors - 1) ? rowsPerProcess + extraRows : rowsPerProcess;
            requests[dest - 1] = MPI.COMM_WORLD.Isend(left.getFlatMatrix(),
                    dest * rowsPerProcess * matrixSize,
                    rowsToSend * matrixSize,
                    MPI.INT,
                    dest,
                    0);
        }
    }

    private static void receiveMatrixFromMaster(Request[] requests,
                                                int rank,
                                                int numberOfProcessors,
                                                int rowsPerProcess,
                                                int extraRows,
                                                int matrixSize,
                                                Matrix left) {
        int rowsForWorker = (rank == numberOfProcessors - 1) ? rowsPerProcess + extraRows : rowsPerProcess;
        requests[rank - 1] = MPI.COMM_WORLD.Irecv(
                left.getFlatMatrix(),
                rank * rowsPerProcess * matrixSize,
                rowsForWorker * matrixSize,
                MPI.INT,
                0,
                0);
    }

    private static void exchangeMatrixBetweenProcesses(Request[] requests,
                                                       int rank,
                                                       int numberOfProcessors,
                                                       int matrixSize,
                                                       Matrix right) {
        if (rank == 0) {
            for (int dest = 1; dest < numberOfProcessors; dest++) {
                requests[numberOfProcessors - 1 + dest] = MPI.COMM_WORLD.Isend(right.getFlatMatrix(),
                        0,
                        right.getFlatMatrix().length,
                        MPI.INT,
                        dest,
                        0);
            }
        } else {
            requests[numberOfProcessors - 1 + rank] = MPI.COMM_WORLD.Irecv(right.getFlatMatrix(),
                    0,
                    right.getFlatMatrix().length,
                    MPI.INT,
                    0,
                    0);
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
        int startRow = rank * rowsPerProcess;
        int endRow = (rank == numberOfProcessors - 1) ? startRow + rowsPerProcess + extraRows : startRow + rowsPerProcess;

        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < matrixSize; j++) {
                for (int k = 0; k < matrixSize; k++) {
                    result.incrementElementBy(i, j, left.getElement(i, k) * right.getElement(k, j));
                }
            }
        }
    }

    private static void collectResultsFromWorkers(Request[] requests,
                                                  int numberOfProcessors,
                                                  int rowsPerProcess,
                                                  int extraRows,
                                                  int matrixSize,
                                                  Matrix result) {
        for (int source = 1; source < numberOfProcessors; source++) {
            int rowsToReceive = (source == numberOfProcessors - 1) ? rowsPerProcess + extraRows : rowsPerProcess;
            requests[source - 1] = MPI.COMM_WORLD.Irecv(
                    result.getFlatMatrix(),
                    source * rowsPerProcess * matrixSize,
                    rowsToReceive * matrixSize,
                    MPI.INT,
                    source,
                    0);
        }
    }

    private static void sendResultToMaster(Request[] requests,
                                           int rank,
                                           int numberOfProcessors,
                                           int rowsPerProcess,
                                           int extraRows,
                                           int matrixSize,
                                           Matrix result) {
        int rowsForWorker = (rank == numberOfProcessors - 1) ? rowsPerProcess + extraRows : rowsPerProcess;
        requests[rank - 1] = MPI.COMM_WORLD.Isend(result.getFlatMatrix(),
                rank * rowsPerProcess * matrixSize,
                rowsForWorker * matrixSize,
                MPI.INT,
                0,
                0);
    }
}
