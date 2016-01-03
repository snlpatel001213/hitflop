package netImproved;

import Jama.Matrix;

public class weightNetwork {
	public static void main(String[] args)
	{
		weightNetwork tn= new weightNetwork();
		weightStorage[]  mat=tn.weightNetworkFramwork();
		mat[1].getMat().print(9, 6);
		
	}
	public weightStorage[] weightNetworkFramwork()
	{
		weightStorage[] mat = new weightStorage[generalInput.perceptronPerLayers.length-1];
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$weightNetwork$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println(generalInput.perceptronPerLayers.length-1);
		for(int i=0;i<generalInput.perceptronPerLayers.length-1;i++)
		{
			mat[i] = new weightStorage();
			//System.out.println("Left Layer="+generalInput.perceptronPerLayers[i]+"\t Right Layer="+generalInput.perceptronPerLayers[i+1]);
			Matrix A = Matrix.random(generalInput.perceptronPerLayers[i],generalInput.perceptronPerLayers[i+1]);
			//A.print(9, 4);
			mat[i].setMat(A);
			
		}
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		return mat;

	}

}
