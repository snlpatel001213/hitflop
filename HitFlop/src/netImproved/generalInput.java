package netImproved;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class generalInput {
	public static int[] perceptronPerLayers={4,10,8,6,3};
	public static double[][] learningRate=new double[1][1];
	public static int totalLayers=perceptronPerLayers.length+1;// all layer number including input and output layers;\
	public static String pathToCSVFile = "C:/Users/Decepticonn/workspace/MultiOutput/iris.csv";// specify path of file here
	static
	{
		learningRate[0][0]=0.5;
	}
	
}
