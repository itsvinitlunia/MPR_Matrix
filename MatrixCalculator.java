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

        displayBox = new JTextArea(10, 40); // Made the display box larger
        displayBox.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayBox);
        add(scrollPane, BorderLayout.NORTH);

        // Instructions for using the calculator
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
        calculateButton.setPreferredSize(new Dimension(80, 25)); // Smaller button size
        calculateButton.addActionListener(this);
        add(calculateButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JComboBox) {
            return; // Do nothing when dropdown is changed
        }

        String selectedOperation = (String) operationDropdown.getSelectedItem();

        switch (selectedOperation) {
            case "Matrix Multiplication":
                inputMatrix('A', true);
                inputMatrix('B', false);
                performMatrixMultiplication();
                break;

            case "Matrix Partitioning":
                displayBox.append("Matrix Partitioning logic not implemented yet.\n");
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
                    matrix[i][j] = Double.parseDouble(element); // Parse the element into the matrix
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
            double[][] coefficients = new double[2][3];

            // Input for the first equation
            coefficients[0][0] = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter coefficient a1 for the first equation (a1x + b1y = c1):"));
            coefficients[0][1] = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter coefficient b1 for the first equation:"));
            coefficients[0][2] = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter constant c1 for the first equation:"));

            // Input for the second equation
            coefficients[1][0] = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter coefficient a2 for the second equation (a2x + b2y = c2):"));
            coefficients[1][1] = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter coefficient b2 for the second equation:"));
            coefficients[1][2] = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter constant c2 for the second equation:"));

            matrixA = coefficients; // Store the coefficients in matrix A
            solveLinearEquations();

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

        // Display result matrix
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
            // Find pivot
            int pivotRow = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(matrix[j][i]) > Math.abs(matrix[pivotRow][i])) {
                    pivotRow = j;
                }
            }

            // Swap rows if necessary
            if (pivotRow != i) {
                double[] temp = matrix[i];
                matrix[i] = matrix[pivotRow];
                matrix[pivotRow] = temp;
                determinant *= -1; // Change sign of determinant
            }

            // Check if matrix is singular
            if (matrix[i][i] == 0) {
                return 0;
            }

            // Eliminate entries below pivot
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

        double[][] inv = invertMatrix(matrixA);
        if (inv == null) {
            displayBox.append("Matrix inversion not possible (determinant is zero).\n");
        } else {
            displayBox.append("The Inverse of the matrix is:\n");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    displayBox.append(inv[i][j] + "\t");
                }
                displayBox.append("\n");
            }
        }
    }

    private double[][] invertMatrix(double[][] A) {
        int n = A.length;
        double[][] augmented = new double[n][2 * n];

        // Create augmented matrix [A | I]
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented[i][j] = A[i][j];
            }
            augmented[i][i + n] = 1; // Identity matrix
        }

        // Perform row operations
        for (int i = 0; i < n; i++) {
            // Find pivot
            int pivotRow = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(augmented[j][i]) > Math.abs(augmented[pivotRow][i])) {
                    pivotRow = j;
                }
            }

            // Swap rows if necessary
            if (pivotRow != i) {
                double[] temp = augmented[i];
                augmented[i] = augmented[pivotRow];
                augmented[pivotRow] = temp;
            }

            // Check if matrix is singular
            if (augmented[i][i] == 0) {
                return null; // Not invertible
            }

            // Normalize the pivot row
            double pivot = augmented[i][i];
            for (int j = 0; j < 2 * n; j++) {
                augmented[i][j] /= pivot;
            }

            // Eliminate entries in other rows
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    double factor = augmented[j][i];
                    for (int k = 0; k < 2 * n; k++) {
                        augmented[j][k] -= factor * augmented[i][k];
                    }
                }
            }
        }

        // Extract the inverse matrix
        double[][] inverse = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse[i][j] = augmented[i][j + n];
            }
        }

        return inverse;
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
        int rowCount = matrix.length;
        int colCount = matrix[0].length;

        double[][] temp = new double[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            System.arraycopy(matrix[i], 0, temp[i], 0, colCount);
        }

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                if (temp[r][c] != 0) {
                    for (int i = r + 1; i < rowCount; i++) {
                        double factor = temp[i][c] / temp[r][c];
                        for (int j = 0; j < colCount; j++) {
                            temp[i][j] -= factor * temp[r][j];
                        }
                    }
                    break; // Move to the next row
                }
            }
        }

        int rank = 0;
        for (int r = 0; r < rowCount; r++) {
            boolean isNonZeroRow = false;
            for (int c = 0; c < colCount; c++) {
                if (temp[r][c] != 0) {
                    isNonZeroRow = true;
                    break;
                }
            }
            if (isNonZeroRow) {
                rank++;
            }
        }

        return rank;
    }

    private void solveLinearEquations() {
        if (matrixA == null) {
            displayBox.append("Please provide a valid matrix for solving linear equations.\n");
            return;
        }

        if (matrixA.length != 2 || matrixA[0].length != 3) {
            displayBox.append("Please provide a valid 2x2 matrix with coefficients and constants.\n");
            return;
        }

        double a1 = matrixA[0][0];
        double b1 = matrixA[0][1];
        double c1 = matrixA[0][2];
        double a2 = matrixA[1][0];
        double b2 = matrixA[1][1];
        double c2 = matrixA[1][2];

        double determinant = a1 * b2 - a2 * b1;

        if (determinant == 0) {
            displayBox.append("The equations have no unique solution (determinant is zero).\n");
        } else {
            double x = (c1 * b2 - c2 * b1) / determinant;
            double y = (a1 * c2 - a2 * c1) / determinant;

            displayBox.append("Solution:\n");
            displayBox.append("x = " + x + "\n");
            displayBox.append("y = " + y + "\n");
        }
    }

    public static void main(String[] args) {
        new MatrixCalculator();
    }
}
