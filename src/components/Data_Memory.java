package components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Data_Memory {
	private int address;
	private int write_Data;
	private int read_Data;
	private HashMap<Integer, Integer> memory;

	// control signals
	private int memWrite;
	private int memRead;

	public Data_Memory() {
		this.memory = new HashMap<Integer, Integer>();
	}

	// getters & setters
	public void setAddress(int address) {
		this.address = address;
	}

	public void setWrite_Data(int data) {
		if (this.memWrite == 1) 
			this.memory.put(address, data);
	}

	public void setMemWrite(int memWrite) {
		this.memWrite = memWrite;
	}

	public void setMemRead(int memRead) {
		this.memRead = memRead;
	}

	public int getRead_Data() {
		if (this.memRead == 1)
			if (this.memory.get(this.address) != null)
				return this.memory.get(this.address);
			
		return -1;
	}
	
	// input data from the user
	public void setData(ArrayList<String> data) {
		for(int i = 0; i < data.size(); i++){
			String[] s = data.get(i).split("/");
			this.memory.put(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
		}
	}

	public String print() {
		String s = "Data Memory Content:\n";
		for(Map.Entry<Integer, Integer> entry: memory.entrySet()){
			s += "	Location #" + entry.getKey() + ": " + entry.getValue() + "/"
					+ Integer.toHexString(entry.getValue()) + "\n";
		}
		return s;
	}

	
}
