package components;

import java.util.ArrayList;

public class Instruction_Memory {
	private int address;
	private String[] instruction;
	private String[][] instruction_mem;
	private ArrayList<String> labels;

	// private int instructionsNumber;

	public Instruction_Memory(ArrayList<String> instructions) {
		this.instruction_mem = new String[20][4];
		// this.instructionsNumber = instructions.size();
		// System.out.println(this.instructionsNumber);

		for (int i = 0; i < instructions.size(); i++) {
			this.intializeInstruction(instructions.get(i), i);
		}
	}

	/*
	 * public int getInstructionsNumber(){ return this.instructionsNumber; }
	 */

	/*
	 * Helper Method
	 */
	private void intializeInstruction(String instruction, int i) {

		// split incoming instruction
		String[] tmp = instruction.split(" ");

		// check on position 0
		switch (tmp[0]) {
		/* M */
		case "lw":
		case "sw":
			
			this.instruction_mem[i][0] = tmp[0]; // lw or sw
			this.instruction_mem[i][1] = tmp[1].substring(1); // r1

			tmp = tmp[2].split("\\(");
			this.instruction_mem[i][3] = tmp[0]; // offset
			this.instruction_mem[i][2] = tmp[1].substring(1, tmp[1].length()-1); // r2
			break;
		/* R */
		case "add":
		case "sub":
		case "and":
		case "or":
		case "nor":
		case "sltu":
		case "slt":
			this.instruction_mem[i][0] = tmp[0]; // add or sub or ...
			this.instruction_mem[i][1] = tmp[1].substring(1); // rd
			this.instruction_mem[i][2] = tmp[2].substring(1); // rs
			this.instruction_mem[i][3] = tmp[3].substring(1);
			break;
		/* I */
		case "addi":
		case "andi":
		case "ori":
		case "sll":
		case "srl":
			this.instruction_mem[i][0] = tmp[0]; // addi or andi or ....
			this.instruction_mem[i][1] = tmp[1].substring(1); // rt
			this.instruction_mem[i][2] = tmp[2].substring(1); // rs
			this.instruction_mem[i][3] = tmp[3]; // value
			break;
		/* B */
		case "beq":
		case "bne":
			this.instruction_mem[i][0] = tmp[0]; // beq or bne
			this.instruction_mem[i][1] = tmp[1].substring(1); // rs
			this.instruction_mem[i][2] = tmp[2].substring(1); // rt
			/*
			 * fetch label address and put it in here
			 */
			this.instruction_mem[i][3] = this.getLabelAddress(tmp[3]);
			break;
		/* J */
		case "j":
		case "jal":
			this.instruction_mem[i][0] = tmp[0]; // j or jal
			/*
			 * fetch label address and put it in here
			 */
			this.instruction_mem[i][1] = this.getLabelAddress(tmp[3]);
			break;
		case "jr":
			this.instruction_mem[i] = tmp;
			break;
		/* Label case */
		default:
			this.labels.add(tmp[0] + "|" + i); // adds label and it's index in
			this.intializeInstruction(tmp[1], i);
		}

	}

	/*
	 * Helper Method
	 */
	private String getLabelAddress(String label) {
		for (int i = 0; i < this.labels.size(); i++) {
			String[] tmp = this.labels.get(i).split("|");
			if (tmp[0].equals(label))
				return tmp[1];
		}
		return "0";
	}

	/*
	 * Getters & Setters
	 */
	public String[] getInstruction(int index) {
		return this.instruction_mem[index];
	}

	public void setAddress(int address) {
		this.address = address;
	}

}
