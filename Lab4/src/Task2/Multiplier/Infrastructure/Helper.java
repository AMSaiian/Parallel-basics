package Task2.Multiplier.Infrastructure;

import java.util.List;
import java.util.Random;

public class Helper {
    public static int[] getMatrixRow(int[][] matrix, int index) {
        int rowAmount = matrix.length;
        int rowSize = matrix[0].length;

        if (index >= rowAmount)
            throw new ArrayIndexOutOfBoundsException("Provided index out of range");

        int[] row = new int[rowSize];

        System.arraycopy(matrix[index], 0, row, 0, rowSize);

        return row;
    }

    public static int[] getMatrixColumn(int[][] matrix, int index) {
        int columnAmount = matrix[0].length;
        int columnSize = matrix.length;

        if (index >= columnAmount)
            throw new ArrayIndexOutOfBoundsException("Provided index out of range");

        int[] column = new int[columnSize];

        for (int i = 0; i < columnSize; i++) {
            column[i] = matrix[i][index];
        }

        return column;
    }

    public static int[][] generateMatrixOfOnes(int rowsAmount, int columnsAmount) {
        int[][] result = new int[rowsAmount][columnsAmount];

        for (int i = 0; i < rowsAmount; i++) {
            for (int j = 0; j < columnsAmount; j++) {
                result[i][j] = 1;
            }
        }

        return result;
    }

    public static int[][] generateRandomMatrix(int rowsAmount, int columnsAmount) {
        int[][] result = new int[rowsAmount][columnsAmount];
        Random generator = new Random(1);

        for (int i = 0; i < rowsAmount; i++) {
            for (int j = 0; j < columnsAmount; j++) {
                result[i][j] = generator.nextInt(0, 10);
            }
        }

        return result;
    }

    public static void printMatrix(int[][] matrix, int rowAmount, int columnAmount) {
        for (int i = 0; i < rowAmount; i++) {
            for (int j = 0; j < columnAmount; j++) {
                System.out.print(matrix[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public static int[][][][] getMatrixBlocks(int[][] matrix, int blockSize)
    {
        int rowAmount = matrix.length;
        int columnAmount = rowAmount;

        int[][][][] blocks = new int[rowAmount / blockSize][columnAmount / blockSize][blockSize][blockSize];

        for (int i = 0; i < rowAmount / blockSize; i++) {
            for (int j = 0; j < columnAmount / blockSize; j++) {
                blocks[i][j] = getConcreteBlock(matrix, i, j, blockSize);
            }
        }

        return blocks;
    }

    public static void setMatrixBlocks(int[][] matrix, int[][][][] matrixBlocks) {
        int rowAmount = matrix.length;
        int columnAmount = rowAmount;
        int blockSize = matrixBlocks[0][0].length;

        for (int i = 0; i < rowAmount / blockSize; i++) {
            for (int j = 0; j < columnAmount / blockSize; j++) {
                setConcreteBlock(matrix, i, j, matrixBlocks[i][j]);
            }
        }
    }

    public static int[][] sumMatrices(List<int[][]> matrixCollection) {
        int rowAmount = matrixCollection.getFirst().length;
        int columnAmount = matrixCollection.getFirst()[0].length;

        int[][] resultMatrix = new int[rowAmount][columnAmount];

        int matrixAmount = matrixCollection.size();

        for (int[][] matrix : matrixCollection) {
            for (int i = 0; i < rowAmount; i++) {
                for (int j = 0; j < columnAmount; j++) {
                    resultMatrix[i][j] += matrix[i][j];
                }
            }
        }

        return resultMatrix;
    }

    private static int[][] getConcreteBlock(int[][] matrix, int iBlock, int jBlock, int blockSize)
    {
        int[][] block = new int[blockSize][blockSize];

        int blockRowStart = iBlock * blockSize;
        int blockColumnStart = jBlock * blockSize;

        for (int i = blockRowStart; i < blockRowStart + blockSize; i++) {
            for (int j = blockColumnStart; j < blockColumnStart + blockSize; j++) {
                block[i - blockRowStart][j - blockColumnStart] = matrix[i][j];
            }
        }

        return block;
    }

    private static void setConcreteBlock(int[][] matrix, int iBlock, int jBlock, int[][] block) {
        int blockSize = block.length;

        int blockRowStart = iBlock * blockSize;
        int blockColumnStart = jBlock * blockSize;

        for (int i = blockRowStart; i < blockRowStart + blockSize; i++) {
            for (int j = blockColumnStart; j < blockColumnStart + blockSize; j++) {
                matrix[i][j] = block[i - blockRowStart][j - blockColumnStart];
            }
        }
    }
}
