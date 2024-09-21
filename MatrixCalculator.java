import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatrixCalculator extends JFrame implements ActionListener {

    private JTextArea displayBox;
    private JComboBox<String> matrixDropdown;
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
        displayBox.append("1. Choose a matrix from the dropdown.\n");
        displayBox.append("2. Click an operation button to perform calculations.\n");
        displayBox.append("3. For custom matrices, input values as prompted.\n\n");

        String[] matrixOptions = {"Custom Matrix", "2x2 Identity Matrix", "3x3 Identity Matrix", "4x4 Identity Matrix"};
        matrixDropdown = new JComboBox<>(matrixOptions);
        matrixDropdown.addActionListener(this);
        JPanel dropdownPanel = new JPanel();
        dropdownPanel.setLayout(new FlowLayout());
        dropdownPanel.add(new JLabel("Choose a matrix: "));
        dropdownPanel.add(matrixDropdown);
        add(dropdownPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3));

        String[] operations = {"Matrix Multiplication", "Matrix Partitioning", "Determinant",
                               "Matrix Inversion", "Rank of Matrix", "Solve Linear Equations"};
        for (String operation : operations) {
            JButton button = new JButton(operation);
            button.setPreferredSize(new Dimension(60, 25)); // Smaller button size
            button.addActionListener(this);
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (e.getSource() == matrixDropdown) {
            String selectedMatrix = (String) matrixDropdown.getSelectedItem();
            if (selectedMatrix.equals("Custom Matrix")) {
                inputCustomMatrix('A');
            } else {
                displayBox.append("Using preset: " + selectedMatrix + "\n");
                initializePresetMatrix(selectedMatrix);
            }
        }

        switch (actionCommand) {
            case "Matrix Multiplication":
                displayBox.append("Input Matrix A:\n");
                inputCustomMatrix('A');
                displayBox.append("Input Matrix B:\n");
                inputCustomMatrix('B');
                performMatrixMultiplication();
                break;

            case "Matrix Partitioning":
                displayBox.append("Matrix Partitioning logic not implemented yet.\n");
                break;

            case "Determinant":
                displayBox.append("Calculating Determinant...\n");
                performDeterminantCalculation();
                break;

            case "Matrix Inversion":
                displayBox.append("Performing Matrix Inversion...\n");
                performMatrixInversion();
                break;

            case "Rank of Matrix":
                displayBox.append("Calculating Rank of Matrix...\n");
                performRankCalculation();
                break;

            case "Solve Linear Equations":
                displayBox.append("Solving Linear Equations...\n");
                solveLinearEquations();
                break;

            default:
                break;
        }
    }

    private void inputCustomMatrix(char matrixLabel) {
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

    private void initializePresetMatrix(String selectedMatrix) {
        switch (selectedMatrix) {
            case "2x2 Identity Matrix":
                matrixB = new double[][]{{1, 0}, {0, 1}};
                break;

            case "3x3 Identity Matrix":
                matrixB = new double[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
                break;

            case "4x4 Identity Matrix":
                matrixB = new double[][]{{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
                break;

            default:
                displayBox.append("No preset matrix available.\n");
                return;
        }

        displayBox.append("Matrix B:\n");
        for (int i = 0; i < matrixB.length; i++) {
            for (int j = 0; j < matrixB[i].length; j++) {
                displayBox.append(matrixB[i][j] + "\t");
            }
            displayBox.append("\n");
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
        double[][] A = new double[n][n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(matrixA[i], 0, A[i], 0, n);
        }

        double d = calculateDeterminant(A);
        double[][] inv = new double[n][n];

        if (d == 0) {
            displayBox.append("Inverse not possible.\n");
        } else {
            double[][] adj = new double[n][n];
            calculateAdjoint(A, adj, n);

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    inv[i][j] = adj[i][j] / d;
                }
            }

            displayBox.append("The Inverse of the matrix is:\n");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    displayBox.append(String.format("%.2f\t", inv[i][j]));
                }
                displayBox.append("\n");
            }
        }
    }

    private void calculateAdjoint(double[][] A, double[][] adj, int n) {
        if (n == 1) {
            adj[0][0] = 1;
            return;
        }

        int sign = 1;
        double[][] temp = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cofactor(A, temp, i, j, n);
                sign = ((i + j) % 2 == 0) ? 1 : -1;
                adj[j][i] = sign * calculateDeterminant(temp);
            }
        }
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
            displayBox.append("Please provide a 2x2 coefficient matrix for solving linear equations.\n");
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
