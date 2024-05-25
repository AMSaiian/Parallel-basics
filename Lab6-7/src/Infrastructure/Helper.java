package Infrastructure;

public class Helper {
    public static void printMatrix(Matrix matrix, int rowsAmount, int columnAmount) {
        if (rowsAmount < 0 || rowsAmount > matrix.getRowAmount()) {
            throw new IndexOutOfBoundsException();
        }

        if (columnAmount < 0 || columnAmount > matrix.getColAmount()) {
            throw new IndexOutOfBoundsException();
        }

        for (int i = 0; i < rowsAmount; i++) {
            for (int j = 0; j < columnAmount; j++) {
                System.out.print(matrix.getElement(i, j) + "  ");
            }
            System.out.println();
        }
    }

    public static void setMatrixByOnes(Matrix matrix) {
        for (int i = 0; i < matrix.getRowAmount(); i++) {
            for (int j = 0; j < matrix.getColAmount(); j++) {
                matrix.setElement(i, j, 1);
            }
        }
    }
}
