package netImproved;

import Jama.Matrix;

public class thetaNetwork {
	public static void main(String[] args)
	{
		thetaNetwork tn= new thetaNetwork();
		tn.thetaNetworkFramwork();
	}
	public thetaStorage[] thetaNetworkFramwork()
	{
		thetaStorage[] mat = new thetaStorage[generalInput.perceptronPerLayers.length];
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$thetaNetwork$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		for(int i=0;i<generalInput.perceptronPerLayers.length;i++)
		{
			mat[i] = new thetaStorage();
			Matrix A = Matrix.random(generalInput.perceptronPerLayers[i], 1);
			///A.print(9, 6);
			mat[i].setMat(A);
		}
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		
		return mat;
	}

}
