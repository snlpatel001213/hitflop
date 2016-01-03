package netImproved;
import Jama.Matrix;

public class dummyWeightFramwork 
{
	public static void main(String[] args)
	{
		dummyWeightFramwork pf= new dummyWeightFramwork();
		pf.dummyWeightFramwork();
	}
	public dummyWeightStorage[] dummyWeightFramwork()
	{
		dummyWeightStorage[] mat = new dummyWeightStorage[generalInput.perceptronPerLayers.length];
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$Dummy weight Framwork$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		for(int i=1;i<generalInput.perceptronPerLayers.length;i++)
		{
			mat[i] = new dummyWeightStorage();
			Matrix A = Matrix.random(generalInput.perceptronPerLayers[i], generalInput.perceptronPerLayers[generalInput.perceptronPerLayers.length-1]);
			//A.print(9, 6);
			mat[i].setMat(A);
		}
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		return mat;
	}
	
}
