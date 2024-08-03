import java.util.Scanner;
class Transd 
{
    void input(int A[][], int n)
     {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Matrix: ");
        for (int i = 0; i < n; i++) 
        {
            for (int j = 0; j < n; j++) 
            {
                A[i][j] = sc.nextInt();
            }
        }
    }

    void cofactor(int A[][], int temp[][], int p, int q, int n)
     {
        int i = 0, j = 0;
        for (int row = 0; row < n; row++) 
        {
            for (int col = 0; col < n; col++)
             {
                if (row != p && col != q)
                 {
                    temp[i][j++] = A[row][col];
                    if (j == n - 1) 
                    {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    float deter(int A[][], int n) 
    {
        if (n == 1)
            return A[0][0];
        
        float d = 0;
        int temp[][] = new int[n-1][n-1];
        int sign = 1;
        for (int i = 0; i < n; i++)
         {
            cofactor(A, temp, 0, i, n);
            d += sign * A[0][i] * deter(temp, n - 1);
            sign = -sign;
        }
        return d;
    }

    void adjoint(int A[][], int adj[][], int n)
     {
        if (n == 1) 
        {
            adj[0][0] = 1;
            return;
        }
        int sign = 1;
        int temp[][] = new int[n][n];

        for (int i = 0; i < n; i++) 
        {
            for (int j = 0; j < n; j++)
             {
                cofactor(A, temp, i, j, n);
                sign = ((i + j) % 2 == 0) ? 1 : -1;
                adj[j][i] = sign * (int)deter(temp, n - 1);
            }
        }
    }

}

class Inverse 
{
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        Transd ob = new Transd();
        System.out.println("Enter the Size of matrix: ");
        int n = sc.nextInt();
        int A[][] = new int[n][n];
        ob.input(A, n);
        float d;
        d=ob.deter(A,n);
        float inv[][] = new float[n][n];

        if (d==0) 
            System.out.println("Inverse not possible."); 
        else
        {
            int adj[][] = new int[n][n];
            ob.adjoint(A, adj, n);

        for (int i = 0; i < n; i++)
         {
            for (int j = 0; j < n; j++)
             {
                inv[i][j] = adj[i][j] / d;
            }
        }    
            System.out.println("The Inverse of matrix is: ");
            for (int i = 0; i < n; i++)
             {
                for (int j = 0; j < n; j++) 
                {
                    System.out.printf("%.2f  ", inv[i][j]);
                }
                System.out.println();
            }
        }
    }
}
