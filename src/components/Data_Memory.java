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
	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public int getWrite_Data() {
		return write_Data;
	}

	public void setWrite_Data(int write_Data) {
		this.write_Data = write_Data;
	}

	public int getRead_Data() {
		return read_Data;
	}

	public void setRead_Data(int read_Data) {
		this.read_Data = read_Data;
	}

	public int getMemWrite() {
		return memWrite;
	}

	public void setMemWrite(int memWrite) {
		this.memWrite = memWrite;
	}

	public int getMemRead() {
		return memRead;
	}

	public void setMemRead(int memRead) {
		this.memRead = memRead;
	}

	public int getData(int index) {
		return this.memory.get(index);
	}

	public void setData(int index, int value) {
		this.memory.set(index, value);
	}
}
