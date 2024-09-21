import java.util.*;

public class CustomPartitionOfMatrix {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter number of rows: ");
        int rows = sc.nextInt();
        System.out.print("Enter number of columns: ");
        int columns = sc.nextInt();
        
        int[][] matrix = new int[rows][columns];
        
        System.out.println("Enter matrix elements:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        
        System.out.print("Enter number of row partitions: ");
        int rowPartitions = sc.nextInt();
        System.out.print("Enter number of column partitions: ");
        int colPartitions = sc.nextInt();
        
        System.out.println("\nPartitioned Matrices:");
        partitionAndPrintMatrix(matrix, rowPartitions, colPartitions);
        
    }
    
    public static void partitionAndPrintMatrix(int[][] matrix, int rowPartitions, int colPartitions) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        int rowStep = (int) Math.ceil((double) rows / rowPartitions);
        int colStep = (int) Math.ceil((double) cols / colPartitions);
        
        int partitionCount = 1;
        
        for (int rp = 0; rp < rowPartitions; rp++) {
            // Calculate actual number of rows in this partition
            int rowsInPartition = Math.min(rowStep, rows - rp * rowStep);
            if (rowsInPartition <= 0) break;  // No more rows left
            
            // Print partition labels
            for (int cp = 0; cp < colPartitions; cp++) {
                int colsInPartition = Math.min(colStep, cols - cp * colStep);
                if (colsInPartition <= 0) break;  // No more columns left
                System.out.printf("Matrix %-4d", partitionCount++);
                if (cp < colPartitions - 1) {
                    for (int k = 0; k < colStep * 5; k++) System.out.print(" ");
                }
            }
            System.out.println("\n");
            
            // Print matrix elements
            for (int i = rp * rowStep; i < rp * rowStep + rowsInPartition; i++) {
                for (int cp = 0; cp < colPartitions; cp++) {
                    int colsInPartition = Math.min(colStep, cols - cp * colStep);
                    if (colsInPartition <= 0) break;  // No more columns left
                    for (int j = cp * colStep; j < cp * colStep + colsInPartition; j++) {
                        System.out.printf("%5d", matrix[i][j]);
                    }
                    if (cp < colPartitions - 1) System.out.print("     ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}