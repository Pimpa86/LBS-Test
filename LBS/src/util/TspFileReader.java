package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class TspFileReader {
	
	public Point[] readFile(){
		
		Point[] ret = new Point[51];
		try {
			FileReader in = new FileReader("F:\\eil51.txt");
			
			BufferedReader bufRead = new BufferedReader(in);
			
			String line;
			int count=0;

			line = bufRead.readLine();
			count++;
			
			while(line!=null){
				String[] splitLine = line.split(" ");
				System.out.println(count+": "+splitLine[1]+", "+ splitLine[0]);
				Point p = new Point(new Integer(splitLine[1]),new Integer(splitLine[2]));
				ret[count-1]=p;
				line = bufRead.readLine();
				count++;
			}
			bufRead.close();

		} catch (
Exception e) {
			
			e.printStackTrace();
		}
		
		return ret;
	}
}
