package netImproved;

import Jama.Matrix;

public class deltaWeightNetwork {
	public static void main(String[] args)
	{
		deltaWeightNetwork tn= new deltaWeightNetwork();
		deltaWeightStorage[]  mat=tn.deltaWeightNetworkFramwork();
		mat[1].getMat().print(9, 6);
		
	}
	public deltaWeightStorage[] deltaWeightNetworkFramwork()
	{
		deltaWeightStorage[] mat = new deltaWeightStorage[generalInput.perceptronPerLayers.length-1];
		//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ deltaweightNetwork $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		//System.out.println(generalInput.perceptronPerLayers.length-1);
		for(int i=0;i<generalInput.perceptronPerLayers.length-1;i++)
		{
			mat[i] = new deltaWeightStorage();
			System.out.println("Left Layer="+generalInput.perceptronPerLayers[i]+"\t Right Layer="+generalInput.perceptronPerLayers[i+1]);
			Matrix A = Matrix.random(generalInput.perceptronPerLayers[i],generalInput.perceptronPerLayers[i+1]);
			//A.print(9, 4);
			mat[i].setMat(A);
			
		}
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		return mat;

	}

}
