package netImproved;
import Jama.Matrix;

public class perceptronNetwork 
{
	public static void main(String[] args)
	{
		perceptronNetwork pf= new perceptronNetwork();
		pf.percepteronNetworkFramwork();
	}
	public perceptronStorage[] percepteronNetworkFramwork()
	{
		perceptronStorage[] mat = new perceptronStorage[generalInput.perceptronPerLayers.length];
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$perceptronNetwork$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		for(int i=0;i<generalInput.perceptronPerLayers.length;i++)
		{
			mat[i] = new perceptronStorage();
			Matrix A = Matrix.random(generalInput.perceptronPerLayers[i],1);
			//A.print(9, 6);
			mat[i].setMat(A);
		}
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		return mat;
	}
	
}
