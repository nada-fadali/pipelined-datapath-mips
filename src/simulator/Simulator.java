package simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Simulator {
	public static void main(String[] args) throws IOException {
		System.out.println("Please enter your code followed by 'endcode");
		System.out.println("Your code must be seperated by spaces only");
		System.out.println("Ex: lw $1 $2 2 -> = lw $1, 2($2)");
		System.out.println("Ex: add $1 $2 $3 -> = add $1, $2, $3");
		
		ArrayList<String> code = new ArrayList<String>();
		BufferedReader rdr = new BufferedReader(new InputStreamReader(System.in));
		String s = rdr.readLine();
		while(!s.equalsIgnoreCase("endcode")) {
			code.add(s);
			s = rdr.readLine();
		}
		@SuppressWarnings("unused")
		Program simulator = new Program(code);
	}
}
