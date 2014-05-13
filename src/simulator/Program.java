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
	private int pcsrc; // branch/jump signal

	// input starting address from the user
	// See description -> Simulator Inputs Sections
	private int stAdd;

	/*
	 * Constructor
	 */
	public Program(ArrayList<String> instructions, int stAdd) {
		this.pc = 0;
		this.clock = 5 + instructions.size();
		this.stAdd = stAdd;

		// initialize component
		this.instruct_mem = new Instruction_Memory(instructions);
		this.reg_file = new Registers_File();
		this.alu = new ALU();
		this.data_memory = new Data_Memory();

		// initialize registers
		this.if_id = new IF_ID();
		this.id_ex = new ID_EX();
		this.ex_mem = new EX_MEM();
		this.mem_wb = new MEM_WB();
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

		// transfer the op from if_id to id_ex
		this.id_ex.setOp(this.if_id.getOp());

		// passing nextPc doesn't depend on the instruction
		this.id_ex.setNextPC(this.if_id.getNextPC());

		// R FORMAT
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

			this.id_ex.setExtend(-1);

			this.id_ex.setRt(this.reg_file.getRead_Data2());
			this.id_ex
					.setRd(Integer.parseInt(this.if_id.getInstruction()[1]
							.substring(1,
									this.if_id.getInstruction()[1].length() - 1))); // rd

			// part 2 of the stage
			// this.id_ex.setNextPC(this.if_id.getNextPC());

			// control signals
			// wb
			this.id_ex.setRegWrite(1);
			this.id_ex.setMemToReg(0);
			// m
			this.id_ex.setMemRead(0);
			this.id_ex.setMemWrite(0);
			this.id_ex.setPCSrc(0);
			// ex
			this.id_ex.setALUSrc(0);
			this.id_ex.setALUOp(10);
			this.id_ex.setRegDst(1);

			// if the type of the instruction is an I type instruction:

			if (this.if_id.getInstruction()[0].equalsIgnoreCase("addi")
					|| this.if_id.getInstruction()[0].equalsIgnoreCase("sll")
					|| this.if_id.getInstruction()[0].equalsIgnoreCase("srl")
					|| this.if_id.getInstruction()[0].equalsIgnoreCase("andi")
					|| this.if_id.getInstruction()[0].equalsIgnoreCase("ori")) {

				// wb
				this.id_ex.setRegWrite(1);
				this.id_ex.setMemToReg(0);
				// m
				this.id_ex.setMemRead(0);
				this.id_ex.setMemWrite(0);
				this.id_ex.setPCSrc(0); // check the explanation at the book
				// ex
				this.id_ex.setALUSrc(1); // check mux2,as if we are saying
											// branch= 0
				this.id_ex.setALUOp(10);
				this.id_ex.setRegDst(0); // check mux3 in the diagram

				// this.id_ex.setNextPC(this.if_id.getNextPC());

				this.reg_file.setRead_Reg1(this.if_id.getInstruction()[2]); // rs
				this.id_ex.setReadData1(this.reg_file.getRead_Data1());

				this.id_ex.setReadData2(-1);

				this.reg_file.setRead_Reg2(this.if_id.getInstruction()[3]); // immediate
																			// value
				this.id_ex.setExtend(this.reg_file.getRead_Data2());

				this.id_ex
						.setRt(Integer.parseInt((this.if_id.getInstruction()[1])
								.substring(1, (this.if_id.getInstruction()[1])
										.length() - 1))); // rt

				this.id_ex.setRd(-1);

			}

			if (this.if_id.getInstruction()[0].equalsIgnoreCase("beq")
					|| this.if_id.getInstruction()[0].equalsIgnoreCase("bne")) {

				this.id_ex.setRegWrite(0);
				this.id_ex.setMemToReg(0); // it should be x
				// m
				this.id_ex.setMemRead(0);
				this.id_ex.setMemWrite(0);
				this.id_ex.setPCSrc(1); // equivalent to branch is equal to 1
				// ex
				this.id_ex.setALUSrc(0); // check mux2,as if we are saying
											// branch= 0
				this.id_ex.setALUOp(01);
				this.id_ex.setRegDst(0); // it should be x

				// this.id_ex.setNextPC(this.if_id.getNextPC());

				this.reg_file.setRead_Reg1(this.if_id.getInstruction()[1]);
				this.id_ex.setReadData1(this.reg_file.getRead_Data1());

				this.reg_file.setRead_Reg2(this.if_id.getInstruction()[2]);
				this.id_ex.setReadData2(this.reg_file.getRead_Data2());

				// LABEL ADDRESS
				// CASE NEED TO BE HANDLED
				// this.id_ex.setExtend(Integer.parseInt((this.if_id
				// .getInstruction()[3]).substring(1,
				// (this.if_id.getInstruction()[3]).length() - 1)));

				this.id_ex.setRt(-1);
				this.id_ex.setRd(-1);
			}

			if (this.if_id.getInstruction()[0].equalsIgnoreCase("lw")) {

				this.id_ex.setRegWrite(1);
				this.id_ex.setMemToReg(1);
				// m
				this.id_ex.setMemRead(1);
				this.id_ex.setMemWrite(0);
				this.id_ex.setPCSrc(0);
				// ex
				this.id_ex.setALUSrc(1);
				this.id_ex.setALUOp(00);
				this.id_ex.setRegDst(0);

				// this.id_ex.setNextPC(this.if_id.getNextPC());

				// this.reg_file.setRead_Reg1((this.if_id.getInstruction()[2]).substring(beginIndex,
				// endIndex));
				this.reg_file.setRead_Reg1(this.if_id.getInstruction()[1]); // rs
				this.id_ex.setReadData1(this.reg_file.getRead_Data1());

				this.id_ex.setReadData2(-1);

				this.id_ex.setExtend(Integer.parseInt(this.if_id
						.getInstruction()[3])); // offset

				this.id_ex
						.setRt(Integer.parseInt((this.if_id.getInstruction()[2])
								.substring(1, (this.if_id.getInstruction()[2])
										.length() - 1))); // rt

				this.id_ex.setRd(-1);
			}

			if (this.if_id.getInstruction()[0].equalsIgnoreCase("sw")) {

				this.id_ex.setRegWrite(0);
				this.id_ex.setMemToReg(-1);
				// m
				this.id_ex.setMemRead(0);
				this.id_ex.setMemWrite(1);
				this.id_ex.setPCSrc(0);
				// ex
				this.id_ex.setALUSrc(1);
				this.id_ex.setALUOp(00);
				this.id_ex.setRegDst(0);

				// this.id_ex.setNextPC(this.if_id.getNextPC());

				this.reg_file.setRead_Reg1(this.if_id.getInstruction()[2]); // rs
				this.id_ex.setReadData1(this.reg_file.getRead_Data1());

				this.id_ex.setReadData2(-1);

				this.id_ex.setExtend(Integer.parseInt(this.if_id
						.getInstruction()[3])); // offset

				this.id_ex
						.setRt(Integer.parseInt((this.if_id.getInstruction()[1])
								.substring(1, (this.if_id.getInstruction()[1])
										.length() - 1))); // rt

				this.id_ex.setRd(-1);
			}

		}

		// J FORMAT

	}

	// nada
	private void exec() {
		// set the control signals
		// get them from id_ex register
		// wb
		this.ex_mem.setRegWrite(this.id_ex.getRegWrite());
		this.ex_mem.setMemToReg(this.id_ex.getMemToReg());
		// m
		this.ex_mem.setMemRead(this.id_ex.getMemRead());
		this.ex_mem.setMemWrite(this.id_ex.getMemWrite());
		this.ex_mem.setPCSrc(this.id_ex.getPCSrc());

		// add nextPC from id_ex register to the label address for jump & branch
		// add it to the adder value in the ex_mem register
		// ~~~MUST HANDEL LABEL CASE~~~
		this.ex_mem.setAdderOutput(this.id_ex.getNextPC() + 3); // 3 is bogus
																// must be
			 													// replaced with
																// label address

		// set alu data1 by read_data1 from reg_file
		this.alu.setData1(this.id_ex.getReadData1());

		// set alu data2 by output from mux between
		// reg_file read_data2
		// Immediate value/i_instruction
		// selector: AluSrc
		this.alu.setData2(mux(this.reg_file.getRead_Data2(),
				this.id_ex.getExtend(), this.id_ex.getALUSrc()));

		// alu control
		// calculate the operation and pass it to alu
		// run alu
		this.alu.run(this.id_ex.getOp());

		// mux 3
		this.ex_mem.setMux3Output(mux(this.id_ex.getRt(), this.id_ex.getRd(),
				this.id_ex.getRegDst()));

		// zero signal
		this.ex_mem.setZero(this.alu.getZero());
	}

	// enjy
	private void mem() {

	}

	// rawan
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
