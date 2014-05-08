package simulator;

import java.util.ArrayList;
import pipeling_registers.*;
import components.*;

public class Program {
	// clock + PC
	private int clock;
	private int pc;

	// components
	private Instruction_Memory instruct_mem;
	private Registers_File reg_file;
	private ALU alu;
	private Data_Memory data_memory;

	// Pipeline registers
	private IF_ID if_id;
	private ID_EX id_ex;
	private EX_MEM ex_mem;
	private MEM_WB mem_wb;

	// control signals
	private int pcsrc;

	public Program(ArrayList<String> instructions) {
		this.pc = 0;
		this.clock = 5 + instructions.size();
		this.instruct_mem = new Instruction_Memory(instructions);
	}

	public void run() {
		while (this.clock > 0) {
			// ...
			this.clock--;
		}
		// print output
	}

	private void fetch() {
		this.pc = mux(if_id.getNextPC(), ex_mem.getAdderOutput(), this.pcsrc);

		this.if_id.setInstruction(this.instruct_mem.getInstruction(this.pc));

		this.if_id.setNextPC(this.pc++);
	}

	private void decode() {
		/*
		 * switch(){ case "add": case "addi": case "sub": case "and": case
		 * "andi": case "ori": case "or": case "nor": case "slt": case "sltu":
		 * 
		 * }
		 */
		if (this.if_id.getInstruction()[0].equalsIgnoreCase("beq")
				|| this.if_id.getInstruction()[0].equalsIgnoreCase("bne")
				|| this.if_id.getInstruction()[0].equalsIgnoreCase("j")
				|| this.if_id.getInstruction()[0].equalsIgnoreCase("jal")
				|| this.if_id.getInstruction()[0].equalsIgnoreCase("jr")
				|| this.if_id.getInstruction()[0].equalsIgnoreCase("lw")
				|| this.if_id.getInstruction()[0].equalsIgnoreCase("sw")) {

		}
	}

	private void exec() {

	}

	private void mem() {

	}

	private void wb() {

	}

	private static int mux(int v1, int v2, int s) {
		if (s == 0)
			return v1;
		return v2;
	}
}
