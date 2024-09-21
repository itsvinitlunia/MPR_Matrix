import java.util.Scanner;

public class DeterminantCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the size of the square matrix: ");
        int n = scanner.nextInt();

        double[][] matrix = new double[n][n];

        System.out.println("Enter the elements of the matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }

        double determinant = calculateDeterminant(matrix);
        System.out.println("The determinant of the matrix is: " + determinant);

        scanner.close();
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
}