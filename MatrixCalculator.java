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
            double[][] coefficients = new double[2][3];

            coefficients[0][0] = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter coefficient a1 for the first equation (a1x + b1y = c1):"));
            coefficients[0][1] = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter coefficient b1 for the first equation:"));
            coefficients[0][2] = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter constant c1 for the first equation:"));

            coefficients[1][0] = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter coefficient a2 for the second equation (a2x + b2y = c2):"));
            coefficients[1][1] = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter coefficient b2 for the second equation:"));
            coefficients[1][2] = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter constant c2 for the second equation:"));

            matrixA = coefficients;
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

    private float[][] invertMatrix(double[][] A) {
        int n = A.length;
        float[][] inv = new float[n][n];
        double determinant = calculateDeterminant(A);

        if (determinant == 0) {
            return null; // Not invertible
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
        displayBox.append("Rank calculation logic not implemented yet.\n");
    }

    private void solveLinearEquations() {
        // Placeholder for linear equations solving logic
        displayBox.append("Solve linear equations logic not implemented yet.\n");
    }

    public static void main(String[] args) {
        new MatrixCalculator();
    }
}
