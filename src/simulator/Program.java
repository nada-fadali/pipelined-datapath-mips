package simulator;

import java.util.ArrayList;

import components.Instruction_Memory;

public class Program {
	//clock + PC
	private int clock;
	private int pc;
	
	//components
	private Instruction_Memory instruct_mem;
	
	//pipeling registers
	//...
	
	//contro signals
	//...
	
	public Program(ArrayList<String> instructions) {
		this.pc = 0;
		this.clock = 5 + instructions.size();
		this.instruct_mem = new Instruction_Memory(instructions);
	}
	
	public void run() {
		while(this.clock > 0) {
			//...
			this.clock--;
		}
		//print output
	}
	
	private void fetch(){
		
	}
	
	private void decode(){
		
	}
	
	private void exec() {
		
	}
	
	private void mem() {
		
	}
	
	private void wb() {
		
	}
	public Instruction_Memory getInstruct_mem() {
		return instruct_mem;
	}

	public void setInstruct_mem(Instruction_Memory instruct_mem) {
		this.instruct_mem = instruct_mem;
	}
}
