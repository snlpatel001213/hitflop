package crawler;


import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class wikipediaTableReader {
	public static String[][] wikipediMainTableReader(String url, int table_number) throws IOException {
		
		/*
		 * To get movie url from main table containing consolidated information 
		 */
		
		ArrayList<String> downServers = new ArrayList<>();
		Document doc = Jsoup.connect(url).get();
		Element table = doc.getElementsByClass("wikitable").get(table_number);
		//System.out.println(table);
		Elements rows = table.select("tr");
		String[][] multD = new String[rows.size()][7];
		/////////////////////////////////////////////////////////////////
		for (int i = 0; i < rows.size(); i++)
		{ 
			//System.out.println("Printing totale number of rows  : "+rows.size()+"===="+ i);
			//first row is the col names so skip it.
			Element row = rows.get(i);
			Element row_th=rows.get(0);
			if(i==0)
			{
				for (int j = 0; j< row.select("th").size(); j++)
				{
					//System.out.println("column size =  "+rows.select("th").size()+"   ===   "+j);
					Element row_td=row.select("th").get(j);
					//System.out.println(">>"+row_td.toString());
					//System.out.println(b);
					multD[i][j]=row_td.text();
					multD[i][6]="URL";
				}

			}
			else
			{
				for (int j = 0; j< row.select("td").size(); j++)
				{
					//System.out.println("column size =  "+rows.select("th").size()+"   ===   "+j);
					Element row_td=row.select("td").get(j);
					String row_td_temp=row.select("td").get(1).getElementsByTag("a").attr("href");
					//System.out.println(">>"+row_td.toString());
					//System.out.println(b);
					multD[i][j]=row_td.text();
					multD[i][6]="https://en.wikipedia.org"+row_td_temp;
				}
			}
		}
		
		return multD;
	}
	public static int tableCounter(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements table = doc.getElementsByClass("wikitable");
		return table.size();
		
	}



}
