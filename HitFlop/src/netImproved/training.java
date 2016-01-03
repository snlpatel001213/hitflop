package netImproved;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import Jama.Matrix;

public class training {
	static Matrix desiredOutput;
	static weightNetwork weightNet = new weightNetwork();
	static deltaWeightNetwork deltaWeightNet = new deltaWeightNetwork();
	static perceptronNetwork perceptronNet = new perceptronNetwork();
	static deltaNetwork deltaNet= new deltaNetwork();
	static thetaNetwork thetaNet = new thetaNetwork();
	static oneNetwork oneNet = new oneNetwork();
	static minusOneNetwork minusOneNet = new minusOneNetwork();
	static dummyWeightFramwork dummyWeight= new dummyWeightFramwork();
	static weightStorage[] weightStore = weightNet.weightNetworkFramwork();
	static deltaWeightStorage[] deltaWeightStore = deltaWeightNet.deltaWeightNetworkFramwork();
	static perceptronStorage[] perceptronStore=perceptronNet.percepteronNetworkFramwork();
	static deltaStorage[] deltaStore = deltaNet.deltaNetworkFramwork();
	static thetaStorage[] thetaStore = thetaNet.thetaNetworkFramwork();
	static oneStorage[] oneStore = oneNet.oneStorageFramwork();
	static minusOneStorage[] minusOneStore = minusOneNet.minusOneStorageFramwork();
	static dummyWeightStorage[] dummyWeightStore = dummyWeight.dummyWeightFramwork();
	static int numberOfClass=generalInput.perceptronPerLayers[generalInput.perceptronPerLayers.length-1];
	static Matrix classMatrix;
	static Matrix dummyMatrixOne;
	static Matrix dummyMatrixMinusOne;
	static Matrix alpha;
	static FileOutputStream fos = null;
	static int fileRowNumber = 0;
	static int layerNumber = 0;
	static int epoch = 50;

	public static void main(String[] args) throws IOException 
	{
		LayerWiseController();
		fineTuniningController();

	}
	public static void LayerWiseController() throws IOException
	{
		alpha = new Matrix(generalInput.learningRate);
		classMatrix=csvReader.alphaToNumericClass();
		classMatrix=classMatrix.transpose();

		for(layerNumber=0;layerNumber<generalInput.perceptronPerLayers.length-1;layerNumber++)
		{
			for(int epoch_counter=0;epoch_counter<epoch;epoch_counter++)// defined 100 epoch per layer 
			{
				for(fileRowNumber=1;fileRowNumber<csvReader.fileRowCount(generalInput.pathToCSVFile);fileRowNumber++)// 0th  row in file is empty
				{
					insertingDataIntoInputLayer(fileRowNumber, perceptronStore);
					layerWiseTraining(fileRowNumber, layerNumber,epoch_counter);
					backPropagation(fileRowNumber,layerNumber,epoch_counter);
				}
			}
		}

	}
	public static void insertingDataIntoInputLayer(int fileRowNumber, perceptronStorage[] perceptronStore) throws IOException
	{
		//// row 0 in filecontent is all zerossssssssss so dont use it
		//// this function is meant to set file row  contents in the input layer of perceptron  
		Matrix fileContent = csvReader.alphaToNumeric();
		Matrix temp = fileContent.getMatrix(fileRowNumber,fileRowNumber, 0, generalInput.perceptronPerLayers[0]-1);
		temp = temp.transpose();
		//temp.print(9, 5);
		perceptronStore[0].setMat(temp);
		System.out.println("************Following data is added to Input layer*****");
		perceptronStore[0].getMat().print(9, 6);
		System.out.println("********************************************************");

	}
	public static void layerWiseTraining(int fileRowNumber,int layerNumber,int epoch_counter)
	{
		//################################Multipling Inputfrom i to j################################//
		System.out.println("weightStore[layerNumber]");
		weightStore[layerNumber].getMat().print(9, 6);
		System.out.println("perceptronStore[layerNumber]");
		perceptronStore[layerNumber].getMat().print(9, 6);
		System.out.println("weightStore Multiplied By PerceptronStore for layer ij");
		Matrix weightStoreMultipliedByPerceptronStoreij=weightStore[layerNumber].getMat().transpose().times(perceptronStore[layerNumber].getMat());
		weightStoreMultipliedByPerceptronStoreij.print(9, 6);
		///REMOVING THETA FROM THE weightStoreMultipliedByperceptronStoreij EXIWI-THETA
		weightStoreMultipliedByPerceptronStoreij.minusEquals(thetaStore[layerNumber+1].getMat());
		System.out.println("theta removed from weightStore Multiplied By PerceptronStore for layer ij");
		weightStoreMultipliedByPerceptronStoreij.print(9, 6);
		//############################################################################################################//		
		//############################################################################################################//
		///APPLING SIGMOID FUNCTION
		double[][] weightStoreMultipliedByPerceptronStoreijArray=new double[weightStoreMultipliedByPerceptronStoreij.getRowDimension()][1];
		double[][] resultj=new double[weightStoreMultipliedByPerceptronStoreij.getRowDimension()][1];
		weightStoreMultipliedByPerceptronStoreijArray=weightStoreMultipliedByPerceptronStoreij.getArray();
		for(int i=0;i<weightStoreMultipliedByPerceptronStoreij.getRowDimension();i++)
		{
			//System.out.println("input >>"+weightStoreMultipliedByPerceptronStoreijArray[i][0]);	
			resultj[i][0]=1/(1 + Math.exp(-1*weightStoreMultipliedByPerceptronStoreijArray[i][0]));
			System.out.println(">"+resultj[i][0]);
		}
		Matrix perceptronAfterSigmoidj=new Matrix(resultj);
		////Storing to RL
		System.out.println("SETTING MATRIX  TO j layer perceptron :- perceptronStore[layerNumber+1].setMat");
		perceptronStore[layerNumber+1].setMat(perceptronAfterSigmoidj);


		//############################################################################################################//
		//############################################################################################################//
		//############################################################################################################//
		//################################Multipling Inputfrom  j to dummy OUTPUT(o)################################//
		System.out.println("Weights between j and dummy output dummyWeightStore[layerNumber+1].getMat()");
		dummyWeightStore[layerNumber+1].getMat().print(9, 6);
		Matrix weightStoreMultipliedByperceptronStorejo=dummyWeightStore[layerNumber+1].getMat().transpose().times(perceptronStore[layerNumber+1].getMat());
		System.out.println("DUMMY OUTPUT OF OUTPUT LAYER");
		weightStoreMultipliedByperceptronStorejo.print(9, 6);
		///removing theta from output(o)
		System.out.println("DUMMY OUTPUT OF OUTPUT LAYER MINUS ");
		//dummyElements.dummy.print(9, 6);
		//dummyElements.dummy.getMatrix(0, numberOfClass-1, layerNumber+1, layerNumber+1).print(9, 6);
		weightStoreMultipliedByperceptronStorejo.minusEquals(dummyElements.dummyTheta.getMatrix(0, numberOfClass-1, layerNumber+1, layerNumber+1));
		weightStoreMultipliedByperceptronStorejo.print(9, 6);
		//############################################################################################################//
		//############################################################################################################//
		///APPLING SIGMOID FUNCTION
		double[][] weightStoreMultipliedByPerceptronStorejoArray=new double[weightStoreMultipliedByperceptronStorejo.getRowDimension()][1];
		double[][] resulto=new double[weightStoreMultipliedByperceptronStorejo.getRowDimension()][1];
		weightStoreMultipliedByPerceptronStorejoArray=weightStoreMultipliedByperceptronStorejo.getArray();
		for(int i=0;i<weightStoreMultipliedByperceptronStorejo.getRowDimension();i++)
		{
			//System.out.println("input >>"+weightStoreMultipliedByPerceptronStorejoArray[i][0]);	
			resulto[i][0]=1/(1 + Math.exp(-1*weightStoreMultipliedByPerceptronStorejoArray[i][0]));
			System.out.println(">"+resulto[i][0]);
		}
		Matrix perceptronAfterSigmoido=new Matrix(resulto);
		
		
		if(epoch_counter==epoch-1&&layerNumber==generalInput.perceptronPerLayers.length-2)
		{
			writeToFile_Output(perceptronAfterSigmoido);
		}


		

		////Storing to RL
		System.out.println("SETTING MATRIX  TO o layer perceptron :- perceptronStore[layerNumber+1].setMat");
		dummyElements.dummyOutput.setMatrix(0, numberOfClass-1, layerNumber+1, layerNumber+1,perceptronAfterSigmoido);
		//############################################################################################################//	
	}
	public static void backPropagation(int fileRowNumber,int layerNumber,int epoch_counter)
	{
		//##############################################  FINDING ERROR  ######################################//	
		System.out.println("\n\n##########################  BACK PROPAGATION  ########################");	
		System.out.println("##############################   BACKPROPAGATION BETWEEN J AN O  #########################//");	
		System.out.println("Desired Output is :- ");
		classMatrix.getMatrix(0, numberOfClass-1, fileRowNumber,fileRowNumber).print(9, 6);
		Matrix actualDummyerror=classMatrix.getMatrix(0, numberOfClass-1, fileRowNumber,fileRowNumber).minus(dummyElements.dummyOutput.getMatrix(0, numberOfClass-1, layerNumber+1, layerNumber+1));
		System.out.println("Error is");
		actualDummyerror.print(9, 6);
		if(epoch_counter==epoch-1&&layerNumber==generalInput.perceptronPerLayers.length-2)
		{
			writeToFile_error(actualDummyerror);
		}
		/// Setting Error
		dummyElements.dummyError.setMatrix(0, numberOfClass-1, layerNumber+1, layerNumber+1,actualDummyerror);

		//############################################## Setting Delta Error ######################################//	
		Matrix oneMinusOutput=dummyElements.DummyOneMatrix.minus(dummyElements.dummyOutput.getMatrix(0, numberOfClass-1, layerNumber+1, layerNumber+1));
		System.out.println("oneMinusOutput :-");
		oneMinusOutput.print(9, 6);
		Matrix oneMinusOutputMultipledbyOutput=dummyElements.dummyOutput.getMatrix(0, numberOfClass-1, layerNumber+1, layerNumber+1).arrayTimes(oneMinusOutput);
		System.out.println("oneMinus Output Multipled by Output :-");
		oneMinusOutputMultipledbyOutput.print(9, 6);
		System.out.println("Dummy delta : - oneMinus Output Multipled byOutput Multiplied By Error");
		Matrix oneMinusOutputMultipledbyOutputMultipliedByError=oneMinusOutputMultipledbyOutput.arrayTimes(dummyElements.dummyError.getMatrix(0, numberOfClass-1, layerNumber+1, layerNumber+1));
		oneMinusOutputMultipledbyOutputMultipliedByError.print(9, 6);
		dummyElements.dummyDelta.setMatrix(0, numberOfClass-1, layerNumber+1, layerNumber+1, oneMinusOutputMultipledbyOutputMultipliedByError);

		//############################################## Setting THETA Error ######################################//	
		//dummyElements.dummyTheta.getMatrix(0, numberOfClass-1, layerNumber+1, layerNumber+1).print(9, 6); 
		dummyElements.dummyTheta.setMatrix(0, numberOfClass-1, layerNumber+1, layerNumber+1,dummyElements.DummyMinusOneMatrix.times(alpha).arrayTimes(dummyElements.dummyDelta.getMatrix(0, numberOfClass-1, layerNumber+1, layerNumber+1)));
		//############################################################################################################//	
		//dummyElements.dummyTheta.getMatrix(0, numberOfClass-1, layerNumber+1, layerNumber+1).print(9, 6); 

		//##############################################   UPDATING WEIGHTS  #############################################//	

		/*
		 * Dummy delta : - oneMinus Output Multipled byOutput Multiplied By Error
			   0.003924
			  -0.040360
		  	  -0.058846

		  OUTPUT AT LAYER J
		  	>0.9896151858580589
			>0.9668237285391841
			>0.9873251838513304
			>0.9915402307498027
			>0.9827615538349439
			>0.983241631809484
		OUTPUT WILL BE
		 	0.00388    0.00379    0.00387    0.00389    0.00386    0.00386
		   -0.03994   -0.03902   -0.03985   -0.04002   -0.03966   -0.03968
		   -0.05823   -0.05689   -0.05810   -0.05835   -0.05783   -0.05786

		 *NOW MULTIPLY IT WITH ALPHA
		 */
		System.out.println("ALPHA*dummy Delta o Multiplied by Perceptron J");
		Matrix dummyDeltaoMultipliedbyPerceptronJ=dummyElements.dummyDelta.getMatrix(0, numberOfClass-1, layerNumber+1, layerNumber+1).times(alpha).times(perceptronStore[layerNumber+1].getMat().transpose());
		dummyDeltaoMultipliedbyPerceptronJ=dummyDeltaoMultipliedbyPerceptronJ.transpose();
		dummyDeltaoMultipliedbyPerceptronJ.print(9, 6);
		//dummyWeightStore[layerNumber+1].getMat().print(9, 6);
		//############################################################################################################//	
		dummyWeightStore[layerNumber+1].getMat().plusEquals(dummyDeltaoMultipliedbyPerceptronJ);
		System.out.println("Weights Updated :-");
		dummyWeightStore[layerNumber+1].getMat().print(9, 6);


		System.out.println("##############################   BACKPROPAGATION BETWEEN I AND J #########################//");	

		//##############################################   FINDING DELTA FOR LAYER J  #############################################//	
		System.out.println("weight between layer numbr and dummy layer");
		dummyWeightStore[layerNumber+1].getMat().print(9, 6);
		System.out.println("dummy delta o layer ");
		dummyElements.dummyDelta.getMatrix(0, numberOfClass-1, layerNumber+1, layerNumber+1).print(9, 6);
		Matrix weightjoMultipliedbyDummyDeltao=dummyElements.dummyDelta.getMatrix(0, numberOfClass-1, layerNumber+1, layerNumber+1).transpose().times(dummyWeightStore[layerNumber+1].getMat().transpose());
		weightjoMultipliedbyDummyDeltao=weightjoMultipliedbyDummyDeltao.transpose();
		weightjoMultipliedbyDummyDeltao.print(9, 6);
		System.out.println("perceptronStore[layerNumber+1].getMat() :-");
		perceptronStore[layerNumber+1].getMat().print(9, 6);
		Matrix perceptronjTimesOneMinusPercepteronj=perceptronStore[layerNumber+1].getMat().arrayTimes(oneStore[layerNumber+1].getMat().minus(perceptronStore[layerNumber+1].getMat()));
		deltaStore[layerNumber+1].setMat(weightjoMultipliedbyDummyDeltao.arrayTimes(perceptronjTimesOneMinusPercepteronj));
		System.out.println("DELTA STORE AT LAYER J");
		deltaStore[layerNumber+1].getMat().print(9, 6);
		//##############################################  UPDATING WEIGHTS BETWEEN I AND J  #############################################//	
		Matrix perceptronStoreiMultipliedbyDeltaStorejMultipledAlpha=perceptronStore[layerNumber].getMat().times(alpha).times(deltaStore[layerNumber+1].getMat().transpose());
		System.out.println("UPDATE TO BE ADDED TO WEIGHT BETWEEN I AND J ");
		perceptronStoreiMultipliedbyDeltaStorejMultipledAlpha.print(9, 6);
		Matrix tempweightij=weightStore[layerNumber].getMat().plus(perceptronStoreiMultipliedbyDeltaStorejMultipledAlpha);
		weightStore[layerNumber].setMat(tempweightij);
		System.out.println("WEIGHT BETWEEN I AND J UPDATED");
		weightStore[layerNumber].getMat().print(9, 6);
	}
	public static void fineTuniningController() throws IOException
	{
		alpha = new Matrix(generalInput.learningRate);
		classMatrix=csvReader.alphaToNumericClass();
		classMatrix=classMatrix.transpose();
		
		
		for(int epoch_counter=0;epoch_counter<epoch;epoch_counter++)// defined 100 epoch per layer 
		{
			for(fileRowNumber=1;fileRowNumber<csvReader.fileRowCount(generalInput.pathToCSVFile);fileRowNumber++)// 0th  row in file is empty
			{
				insertingDataIntoInputLayer(fileRowNumber, perceptronStore);
				fineTuniningForward(epoch_counter);
				fineTuniningBackpropagation(fileRowNumber, epoch_counter);
			}
		}
	}
	public static void fineTuniningForward(int epoch_counter) throws IOException
	{
		for(int layerNumber=0;layerNumber<generalInput.perceptronPerLayers.length-1;layerNumber++)
		{
			System.out.println("weightStore[layerNumber]");
			weightStore[layerNumber].getMat().print(9, 6);
			System.out.println("perceptronStore[layerNumber]");
			perceptronStore[layerNumber].getMat().print(9, 6);
			System.out.println("weightStore Multiplied By PerceptronStore for layer ij");
			Matrix weightStoreMultipliedByPerceptronStoreij=weightStore[layerNumber].getMat().transpose().times(perceptronStore[layerNumber].getMat());
			weightStoreMultipliedByPerceptronStoreij.print(9, 6);
			///REMOVING THETA FROM THE weightStoreMultipliedByperceptronStoreij EXIWI-THETA
			weightStoreMultipliedByPerceptronStoreij.minusEquals(thetaStore[layerNumber+1].getMat());
			System.out.println("theta removed from weightStore Multiplied By PerceptronStore for layer ij");
			weightStoreMultipliedByPerceptronStoreij.print(9, 6);
			//############################################################################################################//		
			//############################################################################################################//
			///APPLING SIGMOID FUNCTION
			double[][] weightStoreMultipliedByPerceptronStoreijArray=new double[weightStoreMultipliedByPerceptronStoreij.getRowDimension()][1];
			double[][] resultj=new double[weightStoreMultipliedByPerceptronStoreij.getRowDimension()][1];
			weightStoreMultipliedByPerceptronStoreijArray=weightStoreMultipliedByPerceptronStoreij.getArray();
			for(int i=0;i<weightStoreMultipliedByPerceptronStoreij.getRowDimension();i++)
			{
				//System.out.println("input >>"+weightStoreMultipliedByPerceptronStoreijArray[i][0]);	
				resultj[i][0]=1/(1 + Math.exp(-1*weightStoreMultipliedByPerceptronStoreijArray[i][0]));
				System.out.println(">"+resultj[i][0]);
			}
			Matrix perceptronAfterSigmoidj=new Matrix(resultj);
			////Storing to RL
			System.out.println("SETTING MATRIX  TO j layer perceptron :- perceptronStore[layerNumber+1].setMat");
			perceptronStore[layerNumber+1].setMat(perceptronAfterSigmoidj);
			
			if(epoch_counter==epoch-1&&layerNumber==generalInput.perceptronPerLayers.length-2)
			{
				writeToFile_Output(perceptronAfterSigmoidj);
			}

		}

	}
	public static void fineTuniningBackpropagation(int fileRowNumber,int epoch_counter) throws IOException// file row number to find error
	{

		// generalInput.perceptronPerLayers.length-1 == last layer 
		//#########################BACKPROPAGATION IN OUTPUT LAYER#############################
		System.out.println("Desired Output is :- ");
		classMatrix.getMatrix(0, numberOfClass-1, fileRowNumber,fileRowNumber).print(9, 6);
		System.out.println("actual output is :- ");
		perceptronStore[ generalInput.perceptronPerLayers.length-1].getMat().print(9, 6);
		System.out.println("Error is");
		Matrix outputError=classMatrix.getMatrix(0, numberOfClass-1, fileRowNumber,fileRowNumber).minus(perceptronStore[ generalInput.perceptronPerLayers.length-1].getMat());
		outputError.print(9, 6);
		
		if(epoch_counter==epoch-1&&layerNumber==generalInput.perceptronPerLayers.length-2)
		{
			writeToFile_error(outputError);
		}
		
		//FINDING DELTA AT OUTPUT LAYER
		Matrix outputDelta=perceptronStore[ generalInput.perceptronPerLayers.length-1].getMat().arrayTimes(oneStore[generalInput.perceptronPerLayers.length-1].getMat().minus(perceptronStore[ generalInput.perceptronPerLayers.length-1].getMat())).arrayTimes(outputError);
		System.out.println("Output layer Delta is :-");
		outputDelta.print(9, 6);
		deltaStore[generalInput.perceptronPerLayers.length-1].setMat(outputDelta);
		//WEIGHT UPDATE FOR OUTER LAYER
		Matrix weightUpdate=perceptronStore[generalInput.perceptronPerLayers.length-2].getMat().times(alpha).times(deltaStore[generalInput.perceptronPerLayers.length-1].getMat().transpose());//deltaStore[generalInput.perceptronPerLayers.length-1].getMat()
		weightStore[generalInput.perceptronPerLayers.length-2].getMat().plusEquals(weightUpdate);
		System.out.println("Output layer and immiduate inner layer weight update :-");
		weightStore[generalInput.perceptronPerLayers.length-2].getMat().print(9, 6);
		//theta update for outer layer 
		System.out.println("theta updated :-");
		thetaStore[generalInput.perceptronPerLayers.length-1].setMat(minusOneStore[generalInput.perceptronPerLayers.length-1].getMat().times(alpha).arrayTimes(deltaStore[generalInput.perceptronPerLayers.length-1].getMat()));
		thetaStore[generalInput.perceptronPerLayers.length-1].getMat().print(9, 6);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ BACK PROPAGATION LOOP BEGINS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		for(int hiddenLayerNumber=generalInput.perceptronPerLayers.length-2;hiddenLayerNumber>0;hiddenLayerNumber--)
		{
			System.out.println("###################Targetting hidden layer (scale from 0) :- "+hiddenLayerNumber+"####################");
			//FINDING DELTA for the current layer
			System.out.println("perceptronStore[hiddenLayerNumber]");
			perceptronStore[hiddenLayerNumber].getMat().print(9, 6);
			System.out.println("(1-perceptronStore[hiddenLayerNumber])*perceptronStore[hiddenLayerNumber]");
			Matrix perceptrononeMultipliedbyOneminusPerceptron=oneStore[hiddenLayerNumber].getMat().minus(perceptronStore[hiddenLayerNumber].getMat()).arrayTimes(perceptronStore[hiddenLayerNumber].getMat());
			perceptrononeMultipliedbyOneminusPerceptron.print(9, 6);
			System.out.println("Delta of hiddenLayerNumber+1 (next layer) :-");
			deltaStore[hiddenLayerNumber+1].getMat().print(9, 6);
			Matrix deltaOfNextLayerMultipliedbyWeightBetwwen=deltaStore[hiddenLayerNumber+1].getMat().transpose().times(weightStore[hiddenLayerNumber].getMat().transpose());
			deltaOfNextLayerMultipliedbyWeightBetwwen=deltaOfNextLayerMultipliedbyWeightBetwwen.transpose();
			System.out.println("delta of nextlayer multiplied to weight between next and current layer:-");
			deltaOfNextLayerMultipliedbyWeightBetwwen.print(9, 6);
			System.out.println("Settinng update to hiddenLayerNumber(Current layer ) delta");
			deltaStore[hiddenLayerNumber].setMat(deltaOfNextLayerMultipliedbyWeightBetwwen.arrayTimes(perceptrononeMultipliedbyOneminusPerceptron));
			deltaStore[hiddenLayerNumber].getMat().print(9, 6);
			//updating weights   ηδAinλ
			System.out.println("ηδAinλ :-");
			Matrix X = perceptronStore[hiddenLayerNumber-1].getMat().times(alpha).times(deltaStore[hiddenLayerNumber].getMat().transpose());
			X.print(9, 6);
			weightStore[hiddenLayerNumber-1].setMat(weightStore[hiddenLayerNumber-1].getMat().plus(X));
			//updating theta
			thetaStore[hiddenLayerNumber].setMat(deltaStore[hiddenLayerNumber].getMat().times(alpha).arrayTimes(minusOneStore[hiddenLayerNumber].getMat()));

		}
	}

	public static void writeToFile_Output(Matrix finalOutputForDummyLayer) {

		double[][] d=finalOutputForDummyLayer.getArray();
		System.out.println(d);
		try
		{

			PrintStream p = new PrintStream(new BufferedOutputStream(new FileOutputStream("output.txt", true)));
			p.println(d[0][0]+"\t"+d[1][0]+"\t"+d[2][0]);
			p.close();

		}
		catch (IOException e)
		{
			System.out.println("IOException : " + e);
		}

	}
	public static void writeToFile_error(Matrix error) {

		double[][] d=error.getArray();
		System.out.println(d);
		try
		{

			PrintStream p = new PrintStream(new BufferedOutputStream(new FileOutputStream("error.txt", true)));
			p.println(d[0][0]+"\t"+d[1][0]+"\t"+d[2][0]);
			p.close();

		}
		catch (IOException e)
		{
			System.out.println("IOException : " + e);
		}

	}




}
