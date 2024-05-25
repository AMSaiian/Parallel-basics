import Infrastructure.Helper;
import Infrastructure.Matrix;
import Multipliers.OneToOne.Blocking;
import Multipliers.OneToOne.NonBlocking;
import Multipliers.Sequential;
import mpi.MPI;

public class Main {
    public static void main(String[] args) {
        final int matrixSize = 1000;
        final int numberOfProcessors = 12;
        final MultiplierType type = MultiplierType.NonBlocking;
        final boolean isShowResult = true;
        final int showRowsAmount = 4;
        final int showColsAmount = 4;

        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();

        Matrix left = new Matrix(matrixSize, matrixSize);
        Matrix right = new Matrix(matrixSize, matrixSize);
        Matrix result = new Matrix(matrixSize, matrixSize);

        if (rank == 0) {
            Helper.setMatrixByOnes(left);
            Helper.setMatrixByOnes(right);
        }

        long startTime = System.currentTimeMillis();
        switch (type) {
            case Blocking -> Blocking.multiply(rank, numberOfProcessors, matrixSize, left, right, result);
            case NonBlocking -> NonBlocking.multiply(rank, numberOfProcessors, matrixSize, left, right, result);
            case Sequential -> Sequential.multiply(rank, matrixSize, left, right, result);
        }
        long elapsedTime = System.currentTimeMillis() - startTime;

        if (rank == 0) {
            System.out.println(elapsedTime);
            if (isShowResult) {
                Helper.printMatrix(result, showRowsAmount, showColsAmount);
            }
        }

        MPI.Finalize();
    }
}