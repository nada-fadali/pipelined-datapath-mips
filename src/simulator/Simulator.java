package simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Simulator {
	public static void main(String[] args) throws IOException {
		
		ArrayList<String> code = new ArrayList<String>();
		ArrayList<String> data = new ArrayList<String>();
		String s = "";
		BufferedReader rdr = new BufferedReader(
				new InputStreamReader(System.in));
		
		
		System.out.println("Please enter the starting address");
		int stAdd = Integer.parseInt(rdr.readLine());
				
		
		System.out.println("Do you wish to store data in the memory? (y/n)");
		if(rdr.readLine().equalsIgnoreCase("y")){
			System.out.println("What are they? (address/data) on each line for each data followed by 'enddata'");
			s = rdr.readLine();
			while(!s.equalsIgnoreCase("enddata")){
				data.add(s);
				s = rdr.readLine();
			}
		}
		
		System.out.println("Please enter your code followed by 'endcode'");
		System.out.println("Your code must be seperated by spaces only");
		s = rdr.readLine();
		while (!s.equalsIgnoreCase("endcode")) {
			code.add(s);
			s = rdr.readLine();
		}
		
		
		
		Program simulator = new Program(code, data, stAdd);
		simulator.run();
	}
}
