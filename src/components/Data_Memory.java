package components;

import java.util.ArrayList;

public class Data_Memory {
	private int address;
	private int write_Data;
	private int read_Data;
	private ArrayList<Integer> memory;

	// control signals
	private int memWrite;
	private int memRead;

	public Data_Memory() {
		this.memory = new ArrayList<Integer>();
	}

	// getters & setters
	public void setAddress(int address) {
		this.address = address;
	}
	
	public void setWrite_Data(int data) {
		if(this.memWrite == 1)
			this.memory.set(this.address, data);
	}
	
	public void setMemWrite(int memWrite) {
		this.memWrite = memWrite;
	}
	
	public void setMemRead(int memRead) {
		this.memRead = memRead;
	}
	
	public int getRead_Data() {
		if(this.memRead == 1)
			return this.memory.get(this.address);
		else
			return -1;
	}
	
	public void setData(ArrayList<String> data){
		
	}
	
	public String print(){
		String s  = "Data Memory Content:\n";
		for(int i = 0; i < this.memory.size(); i++){
			s += "	Location #" + i + ": " + this.memory.get(i) + "/" + Integer.toHexString(this.memory.get(i)) + "\n";
		}
		return s;
	}
	
}
