package Infrastructure;

public class Matrix {
    private final int[] flatMatrix;
    private final int rowAmount;
    private final int colAmount;

    public Matrix(int rowAmount, int colAmount) {
        this.flatMatrix = new int[rowAmount * colAmount];
        this.rowAmount = rowAmount;
        this.colAmount = colAmount;
    }

    public int getRowAmount() {
        return rowAmount;
    }

    public int getColAmount() {
        return colAmount;
    }

    public int[] getFlatMatrix() {
        return flatMatrix;
    }

    public int getElement(int row, int col) {
        if (row < 0 || row >= rowAmount) {
            throw new ArrayIndexOutOfBoundsException("Row index out of range");
        }

        if (col < 0 || col >= colAmount) {
            throw new ArrayIndexOutOfBoundsException("Column index out of range");
        }

        return flatMatrix[row * colAmount + col];
    }

    public void setElement(int row, int col, int value) {
        if (row < 0 || row >= rowAmount) {
            throw new ArrayIndexOutOfBoundsException("Row index out of range");
        }

        if (col < 0 || col >= colAmount) {
            throw new ArrayIndexOutOfBoundsException("Column index out of range");
        }

        flatMatrix[row * colAmount + col] = value;
    }

    public void incrementElementBy(int row, int col, int value) {
        if (row < 0 || row >= rowAmount) {
            throw new ArrayIndexOutOfBoundsException("Row index out of range");
        }

        if (col < 0 || col >= colAmount) {
            throw new ArrayIndexOutOfBoundsException("Column index out of range");
        }

        flatMatrix[row * colAmount + col] += value;
    }
}
