package components;

public class Registers_File {
	private int read_Reg1;
	private int read_Reg2;
	private int write_Reg;
	private int write_Data;
	private int read_Data1;
	private int read_Data2;
	private int regWrite; // control signals
	private int[] registers;

	public Registers_File() {
		this.registers = new int[33]; //Extra one added for $ra
	}

	/*
	 *  Getters & Setters
	 */
	public void setRead_Reg1(String reg) {
		//this.read_Reg1 = Integer.parseInt(reg.substring(1, reg.length() - 1));
		//^ that case handled in Instruction_Memory
		//System.out.println(reg);
		if(!reg.equals("0"))
			this.read_Reg1 = Integer.parseInt(reg);
	}

	public void setRead_Reg2(String reg) {
		//this.read_Reg2 = Integer.parseInt(reg.substring(1, reg.length() - 1));
		//^ that case handled in Instruction_Memory		
		if(!reg.equals("0"))
			this.read_Reg2 = Integer.parseInt(reg);
	}


	public void setWrite_Reg(int write_Reg) {
		this.write_Reg = write_Reg;
	}

	public void setWrite_Data(int data) {
		//this.write_Data = write_Data;
		if(this.regWrite == 1)
			this.registers[this.write_Reg] = data;
	}


	public int getRead_Data1() {
		return this.registers[this.read_Reg1];
	}


	public int getRead_Data2() {
		return this.registers[this.read_Data2];
	}


	public int getRegWrite() {
		return regWrite;
	}

	public void setRegWrite(int regWrite) {
		this.regWrite = regWrite;
	}
	
	public int getRa(){
		return this.registers[32];
	}
	
	public void setRa(int value){
		this.registers[32] = value;
	}
	
	public String print(){
		String s = "Register File Content:\n";
		for(int i = 0; i < this.registers.length-1; i++){
			s += "	Reg #" + i + ": " + this.registers[i] + "/" + Integer.toHexString(this.registers[i]) + "\n";
		}
		s += "	Reg #Ra: " + this.registers[32] + "/" + Integer.toHexString(this.registers[32]);
		return s;
	}
}
