package components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Instruction_Memory {
	private int address;
	private String[] instruction;
	private HashMap<Integer, String[]> instruction_mem;
	private ArrayList<String> labels;

	public Instruction_Memory(ArrayList<String> instructions, int address) {
		this.instruction_mem = new HashMap<Integer, String[]>();
		this.labels = new ArrayList<String>();

		for (int i = 0; i < instructions.size(); i++) {
			this.intializeInstruction(instructions.get(i), address++);
		}

	}

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
			String[] tmp2 = tmp[2].split("\\(");

			String[] v = { tmp[0], // lw or sw
					tmp[1].substring(1), // r1
					tmp2[1].substring(1, tmp2[1].length() - 1), // r2
					tmp2[0] // offset
			};

			this.instruction_mem.put(i, v);
			break;
		/* R */
		case "add":
		case "sub":
		case "and":
		case "or":
		case "nor":
		case "sltu":
		case "slt":
			String[] v1 = { tmp[0], // add or sub or ...
					tmp[1].substring(1), // rd
					tmp[2].substring(1), // rs
					tmp[3].substring(1) // rt
			};
			this.instruction_mem.put(i, v1);
			break;
		/* I */
		case "addi":
		case "andi":
		case "ori":
		case "sll":
		case "srl":
			String[] v2 = { tmp[0], // addi or andi or ....
					tmp[1].substring(1), // rt
					tmp[2].substring(1), // rs
					tmp[3] // value
			};
			this.instruction_mem.put(i, v2);
			break;
		/* B */
		case "beq":
		case "bne":
			String[] v3 = { tmp[0], // beq or bne
					tmp[1].substring(1), // rs
					tmp[2].substring(1), // rt
					this.getLabelAddress(tmp[3]) // label address
			};
			this.instruction_mem.put(i, v3);
			break;
		/* J */
		case "j":
		case "jal":
			String[] v4 = { tmp[0], // j or jal
					this.getLabelAddress(tmp[1]) // label address
			};
			this.instruction_mem.put(i, v4);
			break;
		case "jr":
			this.instruction_mem.put(i, tmp);
			break;
		/* Label case */
		default: {
			this.labels.add(tmp[0] + " " + (i)); // adds label and it's index in
			String s = "";
			for (int k = 1; k < tmp.length; k++)
				s += tmp[k] + " ";
			this.intializeInstruction(s, i);
		}
		}

	}

	/*
	 * Helper Method
	 */
	private String getLabelAddress(String label) {
		for (int i = 0; i < this.labels.size(); i++) {
			String[] tmp = this.labels.get(i).split(" ");
			if (tmp[0].equals(label))
				return tmp[1];
		}
		return "0";
	}

	/*
	 * 
	 * Getters & Setters
	 */
	public String[] getInstruction(int index) {
		return this.instruction_mem.get(index);
	}

	public void setAddress(int address) {
		this.address = address;
	}

}
