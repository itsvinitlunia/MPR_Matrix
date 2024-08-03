import java.util.*;
public class PartitionOfMatrix {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of rows: ");
        int rows = sc.nextInt();
        System.out.print("Enter number of columns: ");
        int columns = sc.nextInt();
        int P[][] = new int[rows][columns];
        System.out.println("Enter matrix elements:-");

        for(int i=0; i<rows; i++) {
            for(int j=0; j<columns; j++) {
                P[i][j] = sc.nextInt();
            }
        }
        System.out.println("Partitioned Matrices:-");
        for(int i=0; i<rows/2; i++) {
            //p1
            for(int j=0; j<columns/2; j++) {
                System.out.print(P[i][j]+" ");
            }
            System.out.print("  ");
            //p2
            for(int j=columns/2; j<columns; j++) {
                System.out.print(P[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
        
        for(int i=rows/2; i<rows; i++) {
            //p3
            for(int j=0; j<columns/2; j++) {
                System.out.print(P[i][j]+" ");
            }
            System.out.print("  ");

            //p4
            for(int j=columns/2; j<columns; j++) {
                System.out.print(P[i][j]+" ");
            }
            System.out.println();
        }
    }
}
