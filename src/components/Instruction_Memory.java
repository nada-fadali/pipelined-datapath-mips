package components;

import java.util.ArrayList;

public class Instruction_Memory {
	private int address;
	private String[] instruction;
	private String[][] instruction_mem;

	public Instruction_Memory(ArrayList<String> instructions) {
		this.address = 0;
		this.instruction = null;
		for (int i = 0; i < instructions.size(); i++) {
			this.instruction_mem[i] = instructions.get(i).split(" ");
		}
	}

	public String[] getInstruction() {
		return instruction;
	}

	public void setInstruction(String[] instruction) {
		this.instruction = instruction;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}
}
