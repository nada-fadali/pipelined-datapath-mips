package pipeling_registers;

public class EX_MEM {
	private int[] wb;
	private int[] m;
	private int adderOutput;
	private int zero;
	private int aluResult;
	private int readData2;
	private int mux3Output;
	private boolean state;
	
	//keeps track of the operation of the instruction
	private String op;

	public EX_MEM() {
		this.wb = new int[2];
		this.m = new int[3];
		this.state = false;

	}

	// getters & setters
	public boolean state(){
		return this.state;
	}
	public void setState(boolean s) {
		this. state = s;
	}
	
	public int getAdderOutput() {
		return adderOutput;
	}

	public void setAdderOutput(int adderOutput) {
		this.adderOutput = adderOutput;
	}

	public int getZero() {
		return zero;
	}

	public void setZero(int zero) {
		this.zero = zero;
	}

	public int getAluResult() {
		return aluResult;
	}

	public void setAluResult(int aluResult) {
		this.aluResult = aluResult;
	}

	public int getReadData2() {
		return readData2;
	}

	public void setReadData2(int readData2) {
		this.readData2 = readData2;
	}

	public int getMux3Output() {
		return mux3Output;
	}

	public void setMux3Output(int mux3Output) {
		this.mux3Output = mux3Output;
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

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}
	
	//public String printEx_Memcontents() {
	public String print() {
		String wbControlSignals = "	RegWrite: " + this.wb[0] + "		MemToReg: "
				+ this.wb[1];

		String mControlSignals = "MemRead: " + this.m[0] + "		MemWrite: "
				+ this.m[1] + "		Branch: " + this.m[2]; //pcsrc

		String allControlSignals = "EX/MEM contents:\n" + wbControlSignals
				+ "\n\n"
				+ "	" + mControlSignals + "\n	";

		String restOfContents = "\n	Extend: " + this.adderOutput
				+ "\n	Zero: " + this.zero + "		ALU result: " + this.aluResult
				+ "\n	Read Data2: " + this.readData2 + "\n	rd/rt register: "
				+ this.mux3Output;

		return (allControlSignals + restOfContents);
	}
}
