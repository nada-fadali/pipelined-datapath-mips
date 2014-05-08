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
		this.registers = new int[32];
	}

	// getters & setters
	public void setRead_Reg1(String reg) {
		this.read_Reg1 = Integer.parseInt(reg.substring(1, reg.length() - 1));
	}

	/*public int getRead_Reg1() {
		return read_Reg1;
	}*/

	public void setRead_Reg2(String reg) {
		this.read_Reg2 = Integer.parseInt(reg.substring(1, reg.length() - 1));
	}

	/*public int getRead_Reg2() {
		return read_Reg2;
	}*/

	public void setWrite_Reg(int write_Reg) {
		this.write_Reg = write_Reg;
	}

	public int getWrite_Reg() {
		return write_Reg;
	}

	public void setWrite_Data(int write_Data) {
		this.write_Data = write_Data;
	}

	public int getWrite_Data() {
		return write_Data;
	}

	public int getRead_Data1() {
		return this.registers[this.read_Reg1];
	}

	/*public void setRead_Data1(int read_Data1) {
		this.read_Data1 = read_Data1;
	}*/

	public int getRead_Data2() {
		return this.registers[this.read_Data2];
	}

	/*public void setRead_Data2(int read_Data2) {
		this.read_Data2 = read_Data2;
	}*/

	public int[] getRegisters() {
		return registers;
	}

	public void setRegisters(int[] registers) {
		this.registers = registers;
	}

	public int getRegWrite() {
		return regWrite;
	}

	public void setRegWrite(int regWrite) {
		this.regWrite = regWrite;
	}
}
