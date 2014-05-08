package pipeling_registers;

public class ID_EX {
	private int nextPC;
	private int readData1;
	private int readData2;
	private int i_instruction;
	//private int rt;
	//private int rd;

	private int[] wb;
	private int[] m;
	private int[] ex;

	public ID_EX() {
		this.wb = new int[2];
		this.m = new int[3];
		this.ex = new int[3];
	}

	// getters & setters
	public int getNextPC() {
		return nextPC;
	}

	public void setNextPC(int nextPC) {
		this.nextPC = nextPC;
	}

	public int getReadData1() {
		return readData1;
	}

	public void setReadData1(int readData1) {
		this.readData1 = readData1;
	}

	public int getReadData2() {
		return readData2;
	}

	public void setReadData2(int readData2) {
		this.readData2 = readData2;
	}

	public int getI_instruction() {
		return i_instruction;
	}

	public void setI_instruction(int i_instruction) {
		this.i_instruction = i_instruction;
	}

	// ex array
	public int getALUSrc() {
		return this.ex[0];
	}

	public int getALUOp() {
		return this.ex[1];
	}

	/*public int getRegDst() {
		return this.ex[2];
	}*/

	public void setALUSrc(int ALUSrc) {
		this.ex[0] = ALUSrc;
	}

	public void setALUOp(int ALUOp) {
		this.ex[1] = ALUOp;
	}

	public void setRegDst(int RegDst) {
		this.ex[2] = RegDst;
	}

	// m array
	public int getMemRead() {
		return this.m[0];
	}

	public int getMemWrite() {
		return this.m[1];
	}

	public int getPCSrc() {
		return this.m[2];
	}

	public void setMemRead(int MemRead) {
		this.m[0] = MemRead;
	}

	public void setMemWrite(int MemWrite) {
		this.m[1] = MemWrite;
	}

	public void setPCSrc(int PCSrc) {
		this.m[2] = PCSrc;
	}

	// wb array
	public int getRegWrite() {
		return this.wb[0];
	}

	public int getMemToReg() {
		return this.wb[1];
	}

	public void setRegWrite(int RegWrite) {
		this.wb[0] = RegWrite;
	}

	public void setMemToReg(int MemToReg) {
		this.wb[1] = MemToReg;
	}

}
