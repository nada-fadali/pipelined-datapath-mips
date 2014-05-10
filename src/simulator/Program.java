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
		
		//R FORMAT
		if (this.if_id.getInstruction()[0].equalsIgnoreCase("add")
				|| this.if_id.getInstruction()[0].equalsIgnoreCase("sub")
				|| this.if_id.getInstruction()[0].equalsIgnoreCase("sll")
				|| this.if_id.getInstruction()[0].equalsIgnoreCase("srl")
				|| this.if_id.getInstruction()[0].equalsIgnoreCase("and")
				|| this.if_id.getInstruction()[0].equalsIgnoreCase("or")
				|| this.if_id.getInstruction()[0].equalsIgnoreCase("nor")
				|| this.if_id.getInstruction()[0].equalsIgnoreCase("slt")
				|| this.if_id.getInstruction()[0].equalsIgnoreCase("sltu")) {

			// if the type of the instruction is an R type instruction:

			// part 1 of the stage
			this.reg_file.setRead_Reg1(this.if_id.getInstruction()[2]); // rs
			this.id_ex.setReadData1(this.reg_file.getRead_Data1());

			this.reg_file.setRead_Reg2(this.if_id.getInstruction()[3]); // rt
			this.id_ex.setReadData2(this.reg_file.getRead_Data2());

			this.id_ex.setI_instruction(-1);

			this.id_ex.setRt(this.reg_file.getRead_Data2());
			this.id_ex
					.setRd(Integer.parseInt(this.if_id.getInstruction()[1]
							.substring(1,
									this.if_id.getInstruction()[1].length() - 1))); //rd

			// part 2 of the stage
			this.id_ex.setNextPC(this.if_id.getNextPC());
			

			// control signals
			// wb
			this.id_ex.setRegWrite(1);
			this.id_ex.setMemToReg(0);
			// m
			this.id_ex.setMemRead(0);
			this.id_ex.setMemWrite(0);
			this.id_ex.setPCSrc(0); // check the explanation at the book
			// ex
			this.id_ex.setALUSrc(0);
			this.id_ex.setALUOp(10);
			this.id_ex.setRegDst(1);

		}
		
		//I FORMAT
		
		//J FORMAT

	}

	//nada
	private void exec() {

	}
	
	//enjy
	private void mem() {

	}
	
	//rawan
	private void wb() {

	}

	public static int mux(int v1, int v2, int s) {
		if (s == 0)
			return v1;
		return v2;
	}

	public static void main(String[] agrs) {

	}
}
