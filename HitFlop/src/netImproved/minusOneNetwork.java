package netImproved;
import Jama.Matrix;

public class minusOneNetwork 
{
	public static void main(String[] args)
	{
		minusOneNetwork pf= new minusOneNetwork();
		pf.minusOneStorageFramwork();
	}
	public minusOneStorage[] minusOneStorageFramwork()
	{
		double dummyOne[][]=new double[1][1];
		dummyOne[0][0]=1;
		minusOneStorage[] mat = new minusOneStorage[generalInput.perceptronPerLayers.length];
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$Minus one Network$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		for(int i=0;i<generalInput.perceptronPerLayers.length;i++)
		{
			mat[i] = new minusOneStorage();
			Matrix A = new Matrix(generalInput.perceptronPerLayers[i], 1);
			for(int j=0;j<generalInput.perceptronPerLayers[i];j++)
			{
				A.set(j,0,-1);
			}
			//A.print(9, 6);
			mat[i].setMat(A);
		}
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		return mat;
	}

}
