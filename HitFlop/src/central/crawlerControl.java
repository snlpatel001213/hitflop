package central;
import crawler.*;
import java.io.IOException;

import crawler.*;
public class crawlerControl {

	public static void main(String[] args) throws IOException {
		String url = "https://en.wikipedia.org/wiki/List_of_Bollywood_films_of_2016";
		for(int table_number=0;table_number<crawler.wikipediaTableReader.tableCounter(url);table_number++)
		{
			String[][] multD=crawler.wikipediaTableReader.wikipediMainTableReader(url, table_number);

			for(int m = 0; m<multD.length; m++)
			{
				for(int n = 0; n<multD[0].length; n++)
				{
					System.out.print("\t"+multD[m][n] +"");
				}
				System.out.println();
			}
			System.out.println("========================================================================");
		}
	}



}
