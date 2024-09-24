import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatrixCalculator extends JFrame implements ActionListener {

    private JTextArea displayBox;
    private JComboBox<String> operationDropdown;
    private double[][] matrixA;
    private double[][] matrixB;

    public MatrixCalculator() {
        setTitle("Matrix Calculator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        displayBox = new JTextArea(10, 40);
        displayBox.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayBox);
        add(scrollPane, BorderLayout.NORTH);

        displayBox.append("Instructions:\n");
        displayBox.append("1. Choose an operation from the dropdown.\n");
        displayBox.append("2. Follow the prompts to input matrices or coefficients.\n\n");

        String[] operations = {"Matrix Multiplication", "Matrix Partitioning", "Determinant",
                               "Matrix Inversion", "Rank of Matrix", "Solve Linear Equations"};
        operationDropdown = new JComboBox<>(operations);
        operationDropdown.addActionListener(this);
        JPanel dropdownPanel = new JPanel();
        dropdownPanel.setLayout(new FlowLayout());
        dropdownPanel.add(new JLabel("Choose an operation: "));
        dropdownPanel.add(operationDropdown);
        add(dropdownPanel, BorderLayout.CENTER);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.setPreferredSize(new Dimension(80, 25));
        calculateButton.addActionListener(this);
        add(calculateButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JComboBox) {
            return;
        }

        String selectedOperation = (String) operationDropdown.getSelectedItem();

        switch (selectedOperation) {
            case "Matrix Multiplication":
                inputMatrix('A', true);
                inputMatrix('B', false);
                performMatrixMultiplication();
                break;

            case "Matrix Partitioning":
                inputPartitionedMatrix();
                break;

            case "Determinant":
                inputMatrix('A', true);
                performDeterminantCalculation();
                break;

            case "Matrix Inversion":
                inputMatrix('A', true);
                performMatrixInversion();
                break;

            case "Rank of Matrix":
                inputMatrix('A', true);
                performRankCalculation();
                break;

            case "Solve Linear Equations":
                inputLinearEquations();
                break;

            default:
                break;
        }
    }

    private void inputMatrix(char matrixLabel, boolean allowSizeInput) {
        try {
            String rowInput = JOptionPane.showInputDialog(this, "Enter the number of rows for Matrix " + matrixLabel + ":");
            String colInput = JOptionPane.showInputDialog(this, "Enter the number of columns for Matrix " + matrixLabel + ":");

            int rows = Integer.parseInt(rowInput);
            int cols = Integer.parseInt(colInput);

            double[][] matrix = new double[rows][cols];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    String element = JOptionPane.showInputDialog(this, "Enter element [" + i + "][" + j + "] for Matrix " + matrixLabel + ":");
                    matrix[i][j] = Double.parseDouble(element);
                }
            }

            if (matrixLabel == 'A') {
                matrixA = matrix;
                displayBox.append("Matrix A:\n");
            } else {
                matrixB = matrix;
                displayBox.append("Matrix B:\n");
            }

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    displayBox.append(matrix[i][j] + "\t");
                }
                displayBox.append("\n");
            }

        } catch (NumberFormatException ex) {
            displayBox.append("Invalid input. Please enter numeric values.\n");
        }
    }

    private void inputLinearEquations() {
        try {
            int n = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number of variables:"));

            double[][] coefficients = new double[n][n];
            double[] constants = new double[n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    coefficients[i][j] = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter coefficient a" + (i + 1) + (j + 1) + ":"));
                }
                constants[i] = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter constant c" + (i + 1) + ":"));
            }

            solveLinearEquations(coefficients, constants);

        } catch (NumberFormatException ex) {
            displayBox.append("Invalid input. Please enter numeric values.\n");
        }
    }

    private void performMatrixMultiplication() {
        if (matrixA == null || matrixB == null) {
            displayBox.append("Please provide both Matrix A and Matrix B for multiplication.\n");
            return;
        }

        int r1 = matrixA.length;
        int c1 = matrixA[0].length;
        int r2 = matrixB.length;
        int c2 = matrixB[0].length;

        if (c1 != r2) {
            displayBox.append("The matrices cannot be multiplied. Columns of Matrix A must match rows of Matrix B.\n");
            return;
        }

        double[][] res = new double[r1][c2];

        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                res[i][j] = 0;
                for (int k = 0; k < c1; k++) {
                    res[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        displayBox.append("The result of Matrix Multiplication is:\n");
        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                displayBox.append(res[i][j] + "\t");
            }
            displayBox.append("\n");
        }
    }

    private void performDeterminantCalculation() {
        if (matrixA == null) {
            displayBox.append("Please provide a Matrix A for determinant calculation.\n");
            return;
        }

        double determinant = calculateDeterminant(matrixA);
        displayBox.append("The determinant of the matrix is: " + determinant + "\n");
    }

    public static double calculateDeterminant(double[][] matrix) {
        int n = matrix.length;
        double determinant = 1;

        for (int i = 0; i < n; i++) {
            int pivotRow = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(matrix[j][i]) > Math.abs(matrix[pivotRow][i])) {
                    pivotRow = j;
                }
            }

            if (pivotRow != i) {
                double[] temp = matrix[i];
                matrix[i] = matrix[pivotRow];
                matrix[pivotRow] = temp;
                determinant *= -1;
            }

            if (matrix[i][i] == 0) {
                return 0;
            }

            for (int j = i + 1; j < n; j++) {
                double factor = matrix[j][i] / matrix[i][i];
                for (int k = i; k < n; k++) {
                    matrix[j][k] -= factor * matrix[i][k];
                }
            }

            determinant *= matrix[i][i];
        }

        return determinant;
    }

    private void performMatrixInversion() {
        if (matrixA == null) {
            displayBox.append("Please provide a Matrix A for inversion.\n");
            return;
        }

        int n = matrixA.length;

        if (n != matrixA[0].length) {
            displayBox.append("Matrix must be square for inversion.\n");
            return;
        }

        float[][] inv = invertMatrix(matrixA);
        if (inv == null) {
            displayBox.append("Matrix inversion not possible (determinant is zero).\n");
        } else {
            displayBox.append("The Inverse of the matrix is:\n");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    displayBox.append(String.format("%.2f\t", inv[i][j]));
                }
                displayBox.append("\n");
            }
        }
    }

    private void inputPartitionedMatrix() {
        int rows = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number of rows:"));
        int columns = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number of columns:"));

        int[][] matrix = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter element [" + i + "][" + j + "]:"));
            }
        }

        int rowPartitions = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number of row partitions:"));
        int colPartitions = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number of column partitions:"));

        displayBox.append("\nPartitioned Matrices:\n");
        partitionAndPrintMatrix(matrix, rowPartitions, colPartitions);
    }

    public void partitionAndPrintMatrix(int[][] matrix, int rowPartitions, int colPartitions) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int rowStep = (int) Math.ceil((double) rows / rowPartitions);
        int colStep = (int) Math.ceil((double) cols / colPartitions);

        int partitionCount = 1;

        for (int rp = 0; rp < rowPartitions; rp++) {
            int rowsInPartition = Math.min(rowStep, rows - rp * rowStep);
            if (rowsInPartition <= 0) break;

            for (int cp = 0; cp < colPartitions; cp++) {
                int colsInPartition = Math.min(colStep, cols - cp * colStep);
                if (colsInPartition <= 0) break;
                displayBox.append("Matrix " + partitionCount++ + " ");
                if (cp < colPartitions - 1) {
                    for (int k = 0; k < colStep * 5; k++) displayBox.append(" ");
                }
            }
            displayBox.append("\n");

            for (int i = rp * rowStep; i < rp * rowStep + rowsInPartition; i++) {
                for (int cp = 0; cp < colPartitions; cp++) {
                    int colsInPartition = Math.min(colStep, cols - cp * colStep);
                    if (colsInPartition <= 0) break;
                    for (int j = cp * colStep; j < cp * colStep + colsInPartition; j++) {
                        displayBox.append(String.format("%5d", matrix[i][j]));
                    }
                    if (cp < colPartitions - 1) displayBox.append("     ");
                }
                displayBox.append("\n");
            }
            displayBox.append("\n");
        }
    }

    private float[][] invertMatrix(double[][] A) {
        int n = A.length;
        float[][] inv = new float[n][n];
        double determinant = calculateDeterminant(A);

        if (determinant == 0) {
            return null;
        }

        double[][] adj = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double[][] temp = new double[n - 1][n - 1];
                cofactor(A, temp, i, j, n);
                adj[j][i] = ((i + j) % 2 == 0 ? 1 : -1) * calculateDeterminant(temp);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inv[i][j] = (float) (adj[i][j] / determinant);
            }
        }

        return inv;
    }

    private void cofactor(double[][] A, double[][] temp, int p, int q, int n) {
        int i = 0, j = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != p && col != q) {
                    temp[i][j++] = A[row][col];
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    private void performRankCalculation() {
        if (matrixA == null) {
            displayBox.append("Please provide a Matrix A for rank calculation.\n");
            return;
        }

        int rank = calculateRank(matrixA);
        displayBox.append("The rank of the matrix is: " + rank + "\n");
    }

    private int calculateRank(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        double[][] tempMatrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix[i], 0, tempMatrix[i], 0, cols);
        }

        int rank = 0;
        for (int row = 0; row < rows; row++) {
            int pivot = -1;
            for (int i = row; i < rows; i++) {
                if (Math.abs(tempMatrix[i][row]) > 1e-10) {
                    pivot = i;
                    break;
                }
            }

            if (pivot != -1) {
                double[] temp = tempMatrix[row];
                tempMatrix[row] = tempMatrix[pivot];
                tempMatrix[pivot] = temp;
                rank++;

                for (int i = row + 1; i < rows; i++) {
                    double factor = tempMatrix[i][row] / tempMatrix[row][row];
                    for (int j = row; j < cols; j++) {
                        tempMatrix[i][j] -= factor * tempMatrix[row][j];
                    }
                }
            }
        }
        return rank;
    }

    private void solveLinearEquations(double[][] coefficients, double[] constants) {
        int n = coefficients.length;

        // Perform Gaussian elimination
        for (int i = 0; i < n; i++) {
            int pivotRow = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(coefficients[j][i]) > Math.abs(coefficients[pivotRow][i])) {
                    pivotRow = j;
                }
            }

            // Swap rows
            if (pivotRow != i) {
                double[] temp = coefficients[i];
                coefficients[i] = coefficients[pivotRow];
                coefficients[pivotRow] = temp;
                double tempConst = constants[i];
                constants[i] = constants[pivotRow];
                constants[pivotRow] = tempConst;
            }

            // Make the pivot value 1 and eliminate the column below
            double pivotValue = coefficients[i][i];
            for (int j = i; j < n; j++) {
                coefficients[i][j] /= pivotValue;
            }
            constants[i] /= pivotValue;

            for (int j = i + 1; j < n; j++) {
                double factor = coefficients[j][i];
                for (int k = i; k < n; k++) {
                    coefficients[j][k] -= factor * coefficients[i][k];
                }
                constants[j] -= factor * constants[i];
            }
        }

        // Back substitution to find solutions
        double[] solutions = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            solutions[i] = constants[i];
            for (int j = i + 1; j < n; j++) {
                solutions[i] -= coefficients[i][j] * solutions[j];
            }
        }

        displayBox.append("The solution to the linear equations is:\n");
        for (int i = 0; i < n; i++) {
            displayBox.append("x" + (i + 1) + " = " + solutions[i] + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MatrixCalculator::new);
    }
}
