import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Word {
	
	private static ArrayList<String> getBizList(String filePath){
		
		  ArrayList<String> List = null;
		  BufferedReader br = null;

		  if ( !(filePath==null)) {
		   List = new ArrayList<String>();
		   try {
		    br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
		    String s;

		    while ((s = br.readLine()) != null) {
		     List.add(s);
		    }
		    br.close();
		   } catch (IOException e) {
		    System.err.println(e);
		   }finally{
		    try { if( br != null ) { br.close(); } } catch(Exception ex) { }
		   }
		  
		  }

		  return List;
		 
		 }
		 public static String[] mood =  {"Positive","Negative","Study","Exercise"};
	public static String getResult(ArrayList<String> totalTweets) {
		
		String[] name=new String[4];
		name[0] = ".\\data\\Positive.txt";
		name[1] = ".\\data\\Negative.txt";
		name[2] = ".\\data\\Study.txt";
		name[3] = ".\\data\\Exercise.txt";
		int flag = -1;
		double max = 0;
		for(int i = 0; i<4; i++) {
		double tmp = 0;
			for(String s:getBizList(name[i])) {
				tmp += TFIDFCalculator.tfIdf(totalTweets.get(0),totalTweets,s);
			}
			System.out.println(mood[i]+" : "+tmp);
			if (tmp>max){ max = tmp; flag = i;}
			tmp=0;
		}
		switch(flag){
			case 0:return "Positive";
			case 1:return "Negative";
			case 2:return "Study";
			case 3:return "Exercise";
			default: return "Error";
		}
	}
	


}