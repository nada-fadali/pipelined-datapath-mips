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
	
	// =============== Branching Case ================
	//	number of instructions
	private int instructCount;
	private boolean dontFetch;
	private int bc;
	
	/*
	 * Constructor
	 */
	public Program(ArrayList<String> instructions, ArrayList<String> data, int address) {
		this.pc = -1;
		this.clock = 0;
		
		// =============== Branching Case ================
		this.instructCount = instructions.size();
		this.dontFetch = false;
		this.bc = 0;
	

		this.stAdd = address;

		// initialize component
		this.instruct_mem = new Instruction_Memory(instructions);
		this.reg_file = new Registers_File();
		this.alu = new ALU();
		this.data_memory = new Data_Memory();
		
		if(data.size() > 0)
			this.data_memory.setData(data);

		// initialize registers
		this.if_id = new IF_ID();
		this.id_ex = new ID_EX();
		this.ex_mem = new EX_MEM();
		this.mem_wb = new MEM_WB();
	}

	public void run() {
		
		while(true){
			System.out.println("START OF SIMULATION\n\n");
			System.out.println("Clock cycle #" + (this.clock+1) );
			
			if(this.mem_wb.state() && this.pc != this.instructCount)
				this.wb();
			else
				break; //end simulation
			
			if(this.ex_mem.state())
				this.mem();
			
			if(this.id_ex.state())
				this.exec();
		
			if(this.if_id.state())
				this.decode();
			
			if(this.pc <= this.instructCount && !dontFetch || bc == 5)
				this.fetch();
			
			
			// print control signals that not part of the pipeline registers
			System.out.println("Control Signals:\n"
					+ "	PCSrc: " + this.pcsrc + "\n");
			
			this.clock++;
			System.out.println("End of clock cycle #" + (this.clock+1));
			System.out.println("-------------------------------------\n\n");
		}
		
		
		
		System.out.println("START OF SIMULATION\n\n");
		System.out.println("Clock cycle #" + (this.clock+1) );
		// print control signals that not part of the pipeline registers
		System.out.println("Control Signals:\n"
				+ "	PCSrc: " + this.pcsrc + "\n");
		System.out.println("End of clock cycle #" + (this.clock+1));
		System.out.println("-------------------------------------\n\n");
	
			
		//print register files
		System.out.println(this.reg_file.print() + "\n###################\n");
		// memory
		System.out.println(this.data_memory.print() + "\n###################\n");
		// total number of cycles
		System.out.println("Total Number of Clock cycles: " + (this.clock) + "\n###################\n\n");
		System.out.println("END OF SIMULATION");

	}

	private void fetch() {

		this.pc = mux(pc + 1, ex_mem.getAdderOutput(), this.pcsrc);

		// fetch line
		this.if_id.setInstruction(this.instruct_mem.getInstruction(this.pc));

		this.if_id.setNextPC(this.pc + 1);

		System.out.println(this.if_id.print());
		System.out.println("________________________\n");
		
		// =============== Branching Case ================
		if(this.if_id.getInstruction()[0].equals("bne") || this.if_id.getInstruction()[0].equals("beq")){
			dontFetch = true;
			bc++;
		}
		
		this.id_ex.setState(true);

	}

	private void decode() {
		// transfer the op from if_id to id_ex
		this.id_ex.setOp(this.if_id.getOp());

		// passing nextPc doesn't depend on the instruction
		this.id_ex.setNextPC(this.if_id.getNextPC());

		String[] tmp = this.if_id.getInstruction();
		//System.out.println(tmp[0]);
		// System.out.println(this.if_id.getInstruction());

		// R FORMAT
		if (tmp[0].equalsIgnoreCase("add")
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
			// System.out.println(tmp[2]);
			this.reg_file.setRead_Reg1(tmp[2]); // rs
			this.id_ex.setReadData1(this.reg_file.getRead_Data1());

			this.reg_file.setRead_Reg2(tmp[3]); // rt
			this.id_ex.setReadData2(this.reg_file.getRead_Data2());
			
			this.id_ex.setExtend(-1);

			this.id_ex.setRt(-1);
			this.id_ex.setRd(Integer.parseInt(tmp[1])); // rd

			// part 2 of the stage
			// this.id_ex.setNextPC(this.if_id.getNextPC());

			// control signals
			// wb
			this.id_ex.setRegWrite(1);
			this.id_ex.setMemToReg(1);
			// m
			this.id_ex.setMemRead(0);
			this.id_ex.setMemWrite(0);
			this.id_ex.setPCSrc(0);
			// ex
			this.id_ex.setALUSrc(0);
			this.id_ex.setALUOp(10);
			this.id_ex.setRegDst(1);
		}
		// if the type of the instruction is an I type instruction:
		else if (tmp[0].equalsIgnoreCase("addi")
				|| this.if_id.getInstruction()[0].equalsIgnoreCase("sll")
				|| this.if_id.getInstruction()[0].equalsIgnoreCase("srl")
				|| this.if_id.getInstruction()[0].equalsIgnoreCase("andi")
				|| this.if_id.getInstruction()[0].equalsIgnoreCase("ori")) {

			// wb
			this.id_ex.setRegWrite(1);
			this.id_ex.setMemToReg(1);
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

			this.reg_file.setRead_Reg1(tmp[2]); // rs
			this.id_ex.setReadData1(this.reg_file.getRead_Data1());

			this.id_ex.setReadData2(-1);

			// this.reg_file.setRead_Reg2(tmp[3]); // immediate
			// value
			// this.id_ex.setExtend(this.reg_file.getRead_Data2());
			this.id_ex.setExtend(Integer.parseInt(tmp[3]));

			this.id_ex.setRt(Integer.parseInt((tmp[1]))); // rt

			this.id_ex.setRd(-1);

		}

		else if (tmp[0].equalsIgnoreCase("beq")
				|| tmp[0].equalsIgnoreCase("bne")) {
			// wb
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

			this.reg_file.setRead_Reg1(tmp[1]); // rs
			this.id_ex.setReadData1(this.reg_file.getRead_Data1());

			this.reg_file.setRead_Reg2(tmp[2]); // rt
			this.id_ex.setReadData2(this.reg_file.getRead_Data2());

			// LABEL ADDRESS
			// update: HANDELED
			this.id_ex.setExtend(Integer.parseInt(tmp[3]));

			this.id_ex.setRt(-1);
			this.id_ex.setRd(-1);
			
			// =============== Branching Case ================
			this.dontFetch = true;
			bc++;
		}

		else if (tmp[0].equalsIgnoreCase("lw")) {
			//w
			this.id_ex.setRegWrite(1);
			this.id_ex.setMemToReg(0);
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

			this.reg_file.setRead_Reg1(tmp[2]); // rs
			this.id_ex.setReadData1(this.reg_file.getRead_Data1());

			this.id_ex.setReadData2(-1);

			this.id_ex.setExtend(Integer.parseInt(tmp[3])); // offset

			this.id_ex.setRt(Integer.parseInt((tmp[1]))); // rt

			this.id_ex.setRd(-1);
		}

		else if (tmp[0].equalsIgnoreCase("sw")) {

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

			this.reg_file.setRead_Reg1(tmp[2]); // reg has the address to be added to offset
			this.id_ex.setReadData1(this.reg_file.getRead_Data1());
			
			this.reg_file.setRead_Reg2(tmp[1]);	//reg has the actuall data to be written in the memory
			this.id_ex.setReadData2(this.reg_file.getRead_Data2());

			this.id_ex.setExtend(Integer.parseInt(tmp[3])); // offset
			
			
			this.id_ex.setRt(-1); // rt
			this.id_ex.setRd(-1);
		}

		// J FORMAT
		else if (tmp[0].equalsIgnoreCase("j") || tmp[0].equalsIgnoreCase("jal")
				|| tmp[0].equalsIgnoreCase("jr")) {
			/* common assignments */
			this.id_ex.setReadData1(-1);
			this.id_ex.setReadData2(-1);

			this.id_ex.setRt(-1);
			this.id_ex.setRd(-1);

			// controls
			// same as branch
			// wb
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
			/* end of common assignments */

			switch (tmp[0]) {
			case "j":
				this.id_ex.setExtend(Integer.parseInt(tmp[1]));
				break;
			case "jal":
				this.id_ex.setExtend(Integer.parseInt(tmp[1]));
				this.reg_file.setRa(this.id_ex.getNextPC());
				break;
			case "jr":
				this.id_ex.setExtend(this.reg_file.getRa());
				break;
			}
			
			// =============== Branching Case ================
			this.dontFetch = true;
			bc++;
		}

		System.out.println(this.id_ex.print());
		System.out.println("________________________\n");
		
		this.id_ex.setState(true);

	}

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
		// this.ex_mem.setAdderOutput(this.id_ex.getNextPC() + 3);
		// update: case handeled
		this.ex_mem.setAdderOutput(this.id_ex.getExtend());
		

		// set alu data1 by read_data1 from reg_file
		this.alu.setData1(this.id_ex.getReadData1());
		

		// set alu data2 by output from mux between
		// reg_file read_data2
		// Immediate value/i_instruction
		// selector: AluSrc
		this.alu.setData2(mux(this.id_ex.getReadData2(),
				this.id_ex.getExtend(), this.id_ex.getALUSrc()));

		// alu control
		// calculate the operation and pass it to alu
		// run alu
		this.ex_mem.setAluResult(this.alu.run(this.id_ex.getOp()));
		

		// mux 3
		this.ex_mem.setMux3Output(mux(this.id_ex.getRt(), this.id_ex.getRd(),
				this.id_ex.getRegDst()));
		
		//	write data to the memory
		this.ex_mem.setReadData2(this.id_ex.getReadData2());
		
		// zero signal
		this.ex_mem.setZero(this.alu.getZero());

		System.out.println(this.ex_mem.print());
		System.out.println("________________________\n");
		
		// =============== Branching Case ================
		this.ex_mem.setState(true);

		//if(this.id_ex.getOp().equals("beq") || this.id_ex.getOp().equals("bne"))
		if(this.ex_mem.getPCSrc() == 1){
			this.if_id.setState(false);
			bc++;
		}
		

	}

	private void mem() {
		
		// wb
		this.mem_wb.setRegWrite(this.ex_mem.getRegWrite());
		this.mem_wb.setMemToReg(this.ex_mem.getMemToReg());
		
		this.data_memory.setMemRead(this.id_ex.getMemRead()); //control signal
		this.data_memory.setMemWrite(this.id_ex.getMemWrite());	//control signal;
		this.data_memory.setAddress(this.ex_mem.getAluResult());
		this.data_memory.setWrite_Data(this.ex_mem.getReadData2());
		
		this.mem_wb.setMux3Output(this.ex_mem.getMux3Output());
		this.mem_wb.setAlu_Result(this.ex_mem.getAluResult());
		this.mem_wb.setRead_Data(this.data_memory.getRead_Data());

		this.pcsrc = this.alu.getZero() & this.ex_mem.getPCSrc();

		System.out.println(this.mem_wb.print());
		System.out.println("________________________\n");
		
		// =============== Branching Case ================
		if(this.ex_mem.getPCSrc() == 1){
			this.ex_mem.setState(false);
			bc++;
		}
		
		this.mem_wb.setState(true);
	}

	private void wb() {
		this.reg_file.setRegWrite(this.mem_wb.getRegWrite());
		this.reg_file.setWrite_Reg(this.mem_wb.getMux3Output());
		this.reg_file.setWrite_Data(mux(this.mem_wb.getRead_Data(),
				this.mem_wb.getAlu_Result(), this.mem_wb.getMemToReg()));

		System.out.println("________________________\n");
		
		// =============== Branching Case ================
		if(this.pcsrc == 1)
			bc++;
		else
			bc = 0;
		

	}

	/*
	 * MUX method public static parameters: first value, second value, selector
	 */
	public static int mux(int v1, int v2, int s) {
		if (s == 0)
			return v1;
		return v2;
	}

}
