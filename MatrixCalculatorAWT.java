import java.awt.*;
import java.awt.event.*;

public class MatrixCalculatorAWT extends Frame implements ActionListener {

    private TextArea display;
    private Choice operationDropdown;
    private double[][] matA;
    private double[][] matB;

    public MatrixCalculatorAWT() {
        setTitle("Matrix Calculator");
        setSize(600, 400);
        setLayout(new BorderLayout());

        display = new TextArea(10, 40);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        display.append("Instructions:\n");
        display.append("1. Choose an operation from the dropdown.\n");
        display.append("2. Follow the prompts to input matrices or coefficients.\n\n");

        operationDropdown = new Choice();
        operationDropdown.add("Multiply");
        operationDropdown.add("Partition");
        operationDropdown.add("Determinant");
        operationDropdown.add("Invert");
        operationDropdown.add("Rank");
        operationDropdown.add("Solve");
        operationDropdown.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                // No specific action for item change.
            }
        });
        
        Panel dropdownPanel = new Panel(new FlowLayout());
        dropdownPanel.add(new Label("Choose an operation: "));
        dropdownPanel.add(operationDropdown);
        add(dropdownPanel, BorderLayout.CENTER);

        Button calculateButton = new Button("Calculate");
        calculateButton.setPreferredSize(new Dimension(80, 25));
        calculateButton.addActionListener(this);
        add(calculateButton, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String operation = operationDropdown.getSelectedItem();

        switch (operation) {
            case "Multiply":
                inputMatrix('A', true);
                inputMatrix('B', false);
                multiply();
                break;

            case "Partition":
                partition();
                break;

            case "Determinant":
                inputMatrix('A', true);
                calculateDeterminant();
                break;

            case "Invert":
                inputMatrix('A', true);
                invert();
                break;

            case "Rank":
                inputMatrix('A', true);
                calculateRank();
                break;

            case "Solve":
                solve();
                break;

            default:
                break;
        }
    }

    private void inputMatrix(char label, boolean allowSizeInput) {
        try {
            String rowsInput = getInputDialog("Enter rows for Matrix " + label + ":");
            String colsInput = getInputDialog("Enter cols for Matrix " + label + ":");

            int rows = Integer.parseInt(rowsInput);
            int cols = Integer.parseInt(colsInput);

            double[][] matrix = new double[rows][cols];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    String element = getInputDialog("Enter element [" + i + "][" + j + "] for Matrix " + label + ":");
                    matrix[i][j] = Double.parseDouble(element);
                }
            }

            if (label == 'A') {
                matA = matrix;
                display.append("Matrix A:\n");
            } else {
                matB = matrix;
                display.append("Matrix B:\n");
            }

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    display.append(matrix[i][j] + "\t");
                }
                display.append("\n");
            }

        } catch (NumberFormatException ex) {
            display.append("Invalid input. Please enter numeric values.\n");
        }
    }

    private String getInputDialog(String message) {
        return (String) new InputDialog(this, message).getValue();
    }

    private void solve() {
        try {
            int a1 = Integer.parseInt(getInputDialog("Enter a1:"));
            int b1 = Integer.parseInt(getInputDialog("Enter b1:"));
            int c1 = Integer.parseInt(getInputDialog("Enter c1:"));
            int a2 = Integer.parseInt(getInputDialog("Enter a2:"));
            int b2 = Integer.parseInt(getInputDialog("Enter b2:"));
            int c2 = Integer.parseInt(getInputDialog("Enter c2:"));

            int det = a1 * b2 - a2 * b1;

            if (det == 0) {
                display.append("The equations have no unique solution (determinant is zero).\n");
            } else {
                int x = (c1 * b2 - c2 * b1) / det;
                int y = (a1 * c2 - a2 * c1) / det;

                display.append("Solution:\n");
                display.append("x = " + x + "\n");
                display.append("y = " + y + "\n");
            }

        } catch (NumberFormatException ex) {
            display.append("Invalid input. Please enter numeric values.\n");
        }
    }

    private void multiply() {
        if (matA == null || matB == null) {
            display.append("Both Matrix A and Matrix B are required for multiplication.\n");
            return;
        }

        int r1 = matA.length;
        int c1 = matA[0].length;
        int r2 = matB.length;
        int c2 = matB[0].length;

        if (c1 != r2) {
            display.append("Matrices cannot be multiplied. Columns of Matrix A must match rows of Matrix B.\n");
            return;
        }

        double[][] result = new double[r1][c2];

        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                result[i][j] = 0;
                for (int k = 0; k < c1; k++) {
                    result[i][j] += matA[i][k] * matB[k][j];
                }
            }
        }

        display.append("Result of Multiplication:\n");
        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                display.append(result[i][j] + "\t");
            }
            display.append("\n");
        }
    }

    private void calculateDeterminant() {
        if (matA == null) {
            display.append("Matrix A is required for determinant calculation.\n");
            return;
        }

        double det = findDeterminant(matA);
        display.append("Determinant is: " + det + "\n");
    }

    public static double findDeterminant(double[][] matrix) {
        int n = matrix.length;
        double det = 1;

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
                det *= -1;
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

            det *= matrix[i][i];
        }

        return det;
    }

    private void invert() {
        if (matA == null) {
            display.append("Matrix A is required for inversion.\n");
            return;
        }

        int n = matA.length;

        if (n != matA[0].length) {
            display.append("Matrix must be square for inversion.\n");
            return;
        }

        double[][] inv = calculateInverse(matA);
        if (inv == null) {
            display.append("Matrix inversion not possible (determinant is zero).\n");
        } else {
            display.append("Inverse of the matrix:\n");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    display.append(String.format("%.2f\t", inv[i][j]));
                }
                display.append("\n");
            }
        }
    }

    private double[][] calculateInverse(double[][] mat) {
        int n = mat.length;
        double[][] temp = new double[n][n];
        double[][] inv = new double[n][n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(mat[i], 0, temp[i], 0, n);
        }

        for (int i = 0; i < n; i++) {
            inv[i][i] = 1;
        }

        for (int i = 0; i < n; i++) {
            double diag = temp[i][i];
            for (int j = 0; j < n; j++) {
                temp[i][j] /= diag;
                inv[i][j] /= diag;
            }

            for (int j = 0; j < n; j++) {
                if (j != i) {
                    double factor = temp[j][i];
                    for (int k = 0; k < n; k++) {
                        temp[j][k] -= factor * temp[i][k];
                        inv[j][k] -= factor * inv[i][k];
                    }
                }
            }
        }
        return inv;
    }

    private void partition() {
        int rows = Integer.parseInt(getInputDialog("Enter number of rows:"));
        int cols = Integer.parseInt(getInputDialog("Enter number of columns:"));

        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Integer.parseInt(getInputDialog("Enter element [" + i + "][" + j + "]:"));
            }
        }

        display.append("Matrix:\n");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                display.append(matrix[i][j] + "\t");
            }
            display.append("\n");
        }

        int partitionIndex = rows / 2;
        display.append("Partitioned Matrices:\n");
        for (int i = 0; i < partitionIndex; i++) {
            for (int j = 0; j < cols; j++) {
                display.append(matrix[i][j] + "\t");
            }
            display.append("\n");
        }

        display.append("Second Part:\n");
        for (int i = partitionIndex; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                display.append(matrix[i][j] + "\t");
            }
            display.append("\n");
        }
    }

    private void calculateRank() {
        double[][] matrix = matA;
        int rank = findRank(matrix);
        display.append("Rank of the matrix is: " + rank + "\n");
    }

    private int findRank(double[][] matrix) {
        int rowCount = matrix.length;
        int colCount = matrix[0].length;
        int rank = Math.min(rowCount, colCount);
        double[][] temp = new double[rowCount][colCount];

        for (int i = 0; i < rowCount; i++) {
            System.arraycopy(matrix[i], 0, temp[i], 0, colCount);
        }

        for (int row = 0; row < rank; row++) {
            for (int col = 0; col < colCount; col++) {
                if (temp[row][col] != 0) {
                    for (int i = 0; i < rowCount; i++) {
                        if (i != row) {
                            double factor = temp[i][col] / temp[row][col];
                            for (int j = 0; j < colCount; j++) {
                                temp[i][j] -= factor * temp[row][j];
                            }
                        }
                    }
                    break;
                }
            }
        }

        for (int i = rank - 1; i >= 0; i--) {
            boolean zeroRow = true;
            for (int j = 0; j < colCount; j++) {
                if (temp[i][j] != 0) {
                    zeroRow = false;
                    break;
                }
            }
            if (zeroRow) {
                rank--;
            }
        }
        return rank;
    }

    public static void main(String[] args) {
        new MatrixCalculatorAWT();
    }

    // AWT InputDialog class to simulate JOptionPane in AWT
    class InputDialog extends Dialog {
        private TextField inputField;
        private Button okButton;
        private String value = "";

        public InputDialog(Frame parent, String message) {
            super(parent, "Input", true);
            setLayout(new BorderLayout());

            add(new Label(message), BorderLayout.NORTH);

            inputField = new TextField(10);
            add(inputField, BorderLayout.CENTER);

            okButton = new Button("OK");
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    value = inputField.getText();
                    dispose();
                }
            });

            Panel buttonPanel = new Panel();
            buttonPanel.add(okButton);
            add(buttonPanel, BorderLayout.SOUTH);

            setSize(300, 150);
            setVisible(true);
        }

        public String getValue() {
            return value;
        }
    }
}
