package simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Simulator {
	public static void main(String[] args) throws IOException {
		//System.out.println("Please enter the starting address");
		ArrayList<String> code = new ArrayList<String>();
		BufferedReader rdr = new BufferedReader(
				new InputStreamReader(System.in));
		//int stAdd = Integer.parseInt(rdr.readLine());
				
		
		System.out.println("Please enter your code followed by 'endcode");
		System.out.println("Your code must be seperated by spaces only");
		String s = rdr.readLine();
		while (!s.equalsIgnoreCase("endcode")) {
			code.add(s);
			s = rdr.readLine();
		}
		
		
		
		Program simulator = new Program(code);
		simulator.run();
	}
}
