//input matrix and print 
import java.util.*;
class Determinant
{
void Input(int A[][],int n)
{
    Scanner sc=new Scanner(System.in);
    System.out.println("Enter the Matrix: ");
    int i,j;
    for (i=0;i<n;i++)
    {
        for(j=0;j<n;j++)
        {
            A[i][j]=sc.nextInt();
        }
    }
}
void Cofactor(int A[][],int temp[][],int p,int q,int n)
{
    int i=0,j=0,row,col;
    for(row=0;row<n;row++)
    {
        for(col=0;col<n;col++)
        {
            if(row!=p && col!=q)
            {
                temp[i][j]=A[row][col];
                j++;
            }
            if(j==n-1)
            {
                j=0;
                i++;
            }
        }
    }

}   
int Deter(int A[][],int n)
{
    int d=0,sign=1,i;
    if (n == 1)
    return A[0][0];
    int temp[][]=new int[n-1][n-1];
    for(i=0;i<n;i++)
    {
        Cofactor(A,temp,0,i,n);
        d+=(sign)*A[0][i]*Deter(temp,n-1);
        sign=-(sign);
    }
    return d;
}
}

class MPR
{
    public static void main (String[] args)
    {
        Scanner sc=new Scanner(System.in);
        Determinant ob=new Determinant();
        int n;
        System.out.println("Enter the Size of matrix: ");
        n=sc.nextInt();
		int A[][] =new int[n][n];
        ob.Input(A,n);
        System.out.println("The Determinant of Matrix is: "+ ob.Deter(A,n));
    }
}