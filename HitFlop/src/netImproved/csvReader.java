package netImproved;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import Jama.Matrix;

import java.util.Set;
import java.util.TreeSet;
/*#######################################################################################################
SPECIFICATIONS
1)CSVREADER AND PARSER IN 2D ARRAY====> public static String[][] fileContent(String pathToCSVFile)
2)COUNTS NUMBER OF ROWS IN FILE ====>public static int fileRowCount(String pathToCSVFile)
3)COUNTS NUMBER OF COLUMNS IN FILE ====>public static int fileColumnCount(String pathToCSVFile) 
4)CHECK DATA TYPE OF DATA IN CSV 2D ARRAY===>public static String isNumeric(String str)
5)GET DATA TYPE(ALPHA OR NUMERIC) FROM 2D ARRAY {USES ONLY FIRST ROW}====>public static String[] getDataType(String pathToCSVFile)
6)GET HEADER OF FILE ====> public static String[] getHeader(String pathToCSVFile)
7)CONVERT ALPHA TO NUMERIC ===>public static void alphaToNumeric()
8)CONVERT ALPHA TO NUMERIC CLASS=====>public static void alphaToNumericclass()
//#####################################################################################################
 */
public class csvReader {
	public BufferedReader br = null;
	public static String line = "";
	public static String cvsSplitBy = ",";
	public static int ROWS;
	public static int COLUMNS;
	static Set<String> contentSet = new HashSet<String>();
	static Set<String> classSet = new HashSet<String>();
	static HashMap<String,Integer> contentMap=new HashMap<String,Integer>();  
	static HashMap<String,Integer> classMap=new HashMap<String,Integer>();  
	static double[][] finalClassContent;
	static double[][] finalContent;


	public static void main(String[] args) throws IOException {

		csvReader obj = new csvReader();

		//System.out.println("Number of Rows in file ==> "+csvReader.fileRowCount(pathToCSVFile));
		//System.out.println("Number of Columns in file ==> "+csvReader.fileColumnCount(pathToCSVFile));
		ROWS=csvReader.fileRowCount(generalInput.pathToCSVFile);
		COLUMNS=csvReader.fileColumnCount(generalInput.pathToCSVFile);
		//csvReader.getDataType(pathToCSVFile);
		//csvReader.getHeader(pathToCSVFile);
		//String xyz[][]=csvReader.fileContent(pathToCSVFile);
		csvReader.alphaToNumeric();
		csvReader.alphaToNumericClass();


	}

	public static String[][] fileContent(String pathToCSVFile) throws IOException {
		//System.out.println("Number of Rows in file (func) ==> "+ROWS);
		//System.out.println("Number of Columns in file (func)==> "+COLUMNS);
		String header[] = new String[csvReader.fileColumnCount(generalInput.pathToCSVFile)];
		String[][] dataMatrix = new String[csvReader.fileRowCount(generalInput.pathToCSVFile)][csvReader.fileColumnCount(generalInput.pathToCSVFile)];
		BufferedReader reader = new BufferedReader(new FileReader(generalInput.pathToCSVFile));
		int lines=0,h=0;
		String [] strArray= new String[csvReader.fileRowCount(generalInput.pathToCSVFile)];
		while (lines<csvReader.fileRowCount(generalInput.pathToCSVFile))
		{

			line = reader.readLine();
			//System.out.println(line);
			line=line.trim();
			strArray[lines]  = line;
			lines=lines+1;
		}

		for(int i=0;i<dataMatrix.length;i++)//rows
		{
			String[] tempStrArray = strArray[i].split(",");

			for(int j=0;j<dataMatrix[0].length;j++)//columns
			{

				//System.out.println("dataMatrix[0].length   i  j="+dataMatrix.length+" "+i +" "+j+" ");
				dataMatrix[i][j]=tempStrArray[j];
				//System.out.print(dataMatrix[i][j] +"=");
			}//System.out.println();
		}
		return dataMatrix;
	}
	/////////////////////////GETTING NUMBER OF LINES IN FILE/////////////////////////////////////////////////////////////
	public static int fileRowCount(String pathToCSVFile) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(generalInput.pathToCSVFile));
		int lines = 0;
		while (reader.readLine() != null) lines++;
		reader.close();
		return  lines;
	}
	public static int fileColumnCount(String pathToCSVFile) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(generalInput.pathToCSVFile));
		line = reader.readLine();
		String[] cells = line.split(cvsSplitBy);
		return  cells.length;
	}
	public static String isNumeric(String str)  
	{  
		try  
		{  
			double d = Double.parseDouble(str);  
		}  
		catch(NumberFormatException nfe)  
		{  
			return "Alpha";  
		}  
		return "Numeric";  
	}
	public static String[] getDataType(String pathToCSVFile) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(generalInput.pathToCSVFile));
		String headerline = reader.readLine();
		line = reader.readLine();
		String[] cells = line.split(cvsSplitBy);
		String b[] = new String[line.length()];
		for(int i=0;i<cells.length;i++)
		{
			b[i] =csvReader.isNumeric(cells[i]);
			//System.out.print(cells[i]+"=>"+b[i]+"\t");
		}
		return  b;

	}
	public static String[] getHeader(String pathToCSVFile) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(pathToCSVFile));
		String line = reader.readLine();
		String[] cells = line.split(cvsSplitBy);
		String b[] = new String[line.length()];

		System.out.println("\n###PRINTING HEADER###");
		for(int i=0;i<cells.length;i++)
		{
			b[i] = cells[i];
			//System.out.print(cells[i]+"\t");
		}
		System.out.println("\n###PRINTING HEADER COMPLETED###\n");
		return  b;
	}
	//	public static boolean checkDuplicateHeader() throws IOException
	//	{
	//		String b[]=csvReader.getHeader(pathToCSVFile);
	//		HashMap<String,Integer> h = new HashMap<String,Integer>();
	//		for(int i=0; i<b.length; i++)
	//		{
	//            if(h.containsKey(b[i])){
	//                h.put(b[i], h.get(b[i]) + 1);
	//            }
	//            else {
	//                h.put(b[i], 1);
	//            }
	//            
	//		}
	//		return false;  
	//	}
	public static Matrix alphaToNumeric() throws IOException
	{
		String[][] filecontent=csvReader.fileContent(generalInput.pathToCSVFile);
		//////////////////////Making Replica//////////////////////
		finalContent=new double[filecontent.length][filecontent[0].length-1];
		double[][] transposefinalContent = new double[filecontent[0].length-1][filecontent.length-1];
		///////////////////////////////////////////////////////////
		String[] dataType=csvReader.getDataType(generalInput.pathToCSVFile);
		for(int i=0;i<filecontent[0].length-1;i++)
		{
			//System.out.println(dataType[0].length());
			if(dataType[i]=="Alpha")
			{
				for(int j=0;j<filecontent.length;j++)
				{

					if(j>0)
					{
						//System.out.print(filecontent[j][i]+"\t");
						contentSet.add(filecontent[j][i]);

					}
				}
			}//System.out.println();
		}
		int count=1;
		for(Object object : contentSet) {
			String element = (String) object;
			//System.out.println(element);
			////////////////////////////////Mapping Part///////////////////////////////////////
			contentMap.put(element, count++);
		}
		System.out.println("##################Printing Data Variable Type####################");
		for(Entry<String, Integer> m:contentMap.entrySet()){  
			System.out.println(m.getKey()+"=====>"+m.getValue());  
		}  
		System.out.println("################################################################");

		//////////////////////////Replacement//////////////////////////////////////////////////

		for(int i=0;i<filecontent[0].length-1;i++)
		{
			//System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
			//System.out.println(filecontent[0].length);
			if(dataType[i]=="Alpha")
			{
				for(int j=0;j<filecontent.length-1;j++)
				{
					//System.out.print(filecontent.length+" "+filecontent[j][i]+"\t");
					for(Entry<String, Integer> m:contentMap.entrySet())
					{  
						if(filecontent[j][i].equals(m.getKey()))
						{
							finalContent[j][i]=(int)m.getValue();
							//System.out.print(finalContent[j][i]+"\t");
						}
					}
				}//System.out.println();
			}
			//Numeric
			if(dataType[i]=="Numeric")
			{
				for(int j=1;j<filecontent.length-1;j++)
				{
					//System.out.print(filecontent.length+" "+filecontent[j][i]+"\t");
					
							finalContent[j][i]=Double.parseDouble(filecontent[j][i]);
							//System.out.print(finalContent[j][i]+"\t");
					
				}//System.out.println();
			}
			
		}


		//transpose of class content

				//System.out.println("##################PRINTING ALPHATONUMERI CONTENT#######################");
		Matrix A = new Matrix(finalContent);
		//		A.print(9, 4);
		//		
		//		
		//		System.out.println("#######################################################################");
		//		System.out.println("Number of ROWS="+A.getRowDimension()+"\t Number of COLS="+A.getColumnDimension());
		return A;


	}

	public static Matrix alphaToNumericClass() throws IOException
	{
		String[][] filecontent=csvReader.fileContent(generalInput.pathToCSVFile);
		//////////////////////Making Replica//////////////////////
		finalClassContent=new double[filecontent.length][1];
		///////////////////////////////////////////////////////////
		String[] dataType=csvReader.getDataType(generalInput.pathToCSVFile);
		//System.out.println(filecontent[0][filecontent[0].length-1]);
		if(dataType[filecontent[0].length-1]=="Alpha")
		{
			for(int j=0;j<filecontent.length;j++)
			{
				if(j>0)
				{
					//System.out.print(filecontent[j][filecontent[0].length-1]+"\t");
					classSet.add(filecontent[j][filecontent[0].length-1]);
				}
			}
		}//System.out.println();
		System.out.println("\n###################Printing Class Types#########################");
		int count=1;
		for(Object object : classSet) {
			String element = (String) object;
			////////////////////////////////Mapping Part///////////////////////////////////////
			classMap.put(element, count);
			System.out.println(element +"======>"+count);
			count++;
		}
		//System.out.println("########################################################################");

		//////////////////////////Replacement//////////////////////////////////////////////////
		if(dataType[filecontent[0].length-1]=="Alpha")
		{
			for(int j=0;j<filecontent.length;j++)
			{

				for(Entry<String, Integer> m:classMap.entrySet())
				{  
					if(filecontent[j][filecontent[0].length-1].equals(m.getKey()))
					{
						finalClassContent[j][0]=(int)m.getValue();
						//System.out.print(finalClassContent[j][filecontent[0].length-1]+"\t");
					}
				}
			}
		}//System.out.println();

		//taking transpose
		System.out.println("##################PRINTING ALPHATONUMERIC CLASS CONTENT#######################");
		for(int j=0;j<filecontent.length;j++)
		{

			for(Entry<String, Integer> m:classMap.entrySet())
			{  
				if(filecontent[j][filecontent[0].length-1].equals(m.getKey()))
				{
					finalClassContent[j][0]=(int)m.getValue();
					//System.out.println(finalClassContent[j][0]=(int)m.getValue());
				}
			}
		}
		//System.out.println("########################################################################");
		System.out.println("Number of ROWS="+finalClassContent.length);
		
		double classMatrix[][]= new double[finalClassContent.length][count-1];
		for(int e=0;e<filecontent.length;e++)
		{
			for(int f=0;f<count-1;f++)
			{
				if(finalClassContent[e][0]==f+1)
				{
					classMatrix[e][f]=1;
				}
				
			}
		}	
		Matrix temp = new Matrix(classMatrix);
		//temp.print(9, 6);
		return temp;
	}

}






