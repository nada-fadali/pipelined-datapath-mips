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
			if (instructions.get(i).equalsIgnoreCase("lw")
					|| instructions.get(i).equalsIgnoreCase("sw")) {
				String[] tmp = instructions.get(i).split(" ");
				this.instruction_mem[i][0] = tmp[0]; // lw or sw
				this.instruction_mem[i][1] = tmp[1]; // r1

				tmp = tmp[2].split("(");
				this.instruction_mem[i][3] = tmp[0]; // offset
				this.instruction_mem[i][2] = tmp[1].substring(0,
						tmp[1].length() - 1); //r2

			} else
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
