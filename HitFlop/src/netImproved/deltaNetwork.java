package netImproved;

import Jama.Matrix;

public class deltaNetwork {
	public static void main(String[] args)
	{
		deltaNetwork tn= new deltaNetwork();
		tn.deltaNetworkFramwork();
	}
	public deltaStorage[] deltaNetworkFramwork()
	{
		deltaStorage[] mat = new deltaStorage[generalInput.perceptronPerLayers.length];
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$deltaNetwork$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		for(int i=0;i<generalInput.perceptronPerLayers.length;i++)
		{
			mat[i] = new deltaStorage();
			Matrix A = Matrix.random(generalInput.perceptronPerLayers[i], 1);
			//A.print(9, 6);
			mat[i].setMat(A);
		}
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		return mat;
	}

}
