package netImproved;

import java.io.IOException;

import Jama.Matrix;

public class dummyElements {
	static double[][] dummy= new double[generalInput.perceptronPerLayers.length][generalInput.perceptronPerLayers[generalInput.perceptronPerLayers.length-1]];
	static Matrix dummyPerceptron=  Matrix.random(generalInput.perceptronPerLayers[generalInput.perceptronPerLayers.length-1], dummy.length);
	static Matrix dummyTheta=  Matrix.random(generalInput.perceptronPerLayers[generalInput.perceptronPerLayers.length-1], dummy.length);
	static Matrix dummyDelta=  Matrix.random(generalInput.perceptronPerLayers[generalInput.perceptronPerLayers.length-1], dummy.length);
	static Matrix dummyError=  Matrix.random(generalInput.perceptronPerLayers[generalInput.perceptronPerLayers.length-1], dummy.length);
	static Matrix dummyOutput=  Matrix.random(generalInput.perceptronPerLayers[generalInput.perceptronPerLayers.length-1], dummy.length);
	static Matrix DummyOneMatrix;
	static Matrix DummyMinusOneMatrix;
	static double[][] tempDummyOneMatrix=new double[generalInput.perceptronPerLayers[generalInput.perceptronPerLayers.length-1]][1];
	static double[][] tempMinusDummyOneMatrix=new double[generalInput.perceptronPerLayers[generalInput.perceptronPerLayers.length-1]][1];
	static
	{
		for(int i=0;i<generalInput.perceptronPerLayers[generalInput.perceptronPerLayers.length-1];i++)
		{
			tempDummyOneMatrix[i][0]=1;
		}
		DummyOneMatrix=new Matrix(tempDummyOneMatrix);
		for(int i=0;i<generalInput.perceptronPerLayers[generalInput.perceptronPerLayers.length-1];i++)
		{
			tempMinusDummyOneMatrix[i][0]=-1;
		}
		DummyMinusOneMatrix=new Matrix(tempMinusDummyOneMatrix);
	}
	
	public static void main(String[] args) throws IOException 
	{	
		dummyPerceptron.print(9, 6);
		dummyPerceptron.getMatrix(0, 0, 0, 0).print(9, 6); // accessing perticular element 
		dummyTheta.getMatrix(0, 0, 0, 0).print(9, 6);
		dummyTheta.print(9, 6);

	}
}
