import java.util.*;
public class Transpose {
   
    //Transpose Function
    static void getTranspose(int [][]A, int r, int c) {
        for(int i=0; i<c; i++) {
            for(int j=i; j<r; j++) {
                int temp = A[i][j];
                A[i][j] = A[j][i];
                A[j][i] = temp;
            }
        }
    }

    //Print function
    static void printMatrix(int [][]A, int r, int c) {
        for(int i=0; i<r; i++) {
            for(int j=0; j<c; j++) {
                System.out.print(A[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of rows: ");
        int rows = sc.nextInt();
        System.out.print("Enter number of columns: ");
        int columns = sc.nextInt();
        int [][]arr = new int[rows][columns];

        //Taking input 
        System.out.println("Enter matrix elements:-");
        for(int i=0; i<rows; i++) {
            for(int j=0; j<columns; j++) {
                arr[i][j] = sc.nextInt();
            }
        }

        //Taking transpose
        getTranspose(arr, rows, columns);

        //Printing transpose
        System.out.println("Transposed matrix:-");
        printMatrix(arr, rows, columns); 
    }
}