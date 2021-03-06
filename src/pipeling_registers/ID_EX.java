package pipeling_registers;

public class ID_EX {
	private int nextPC;
	private int readData1;
	private int readData2;
	private int extend; //was i_Instuction
	private int rt;
	private int rd;

	private int[] wb;
	private int[] m;
	private int[] ex;
	
	private boolean state;
	
	//keeps track of the operation of the instruction
	private String op;

	public ID_EX() {
		this.wb = new int[2];
		this.m = new int[3];
		this.ex = new int[3];
		this.state = false;
	}

	// getters & setters
	public boolean state(){
		return this.state;
	}
	
	public void setState(boolean s){
		this.state = s;
	}
	
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

	public int getExtend() {
		return extend;
	}

	public void setExtend(int extend) {
		this.extend = extend;
	}

	// ex array
	public int getALUSrc() {
		return this.ex[0];
	}

	public int getALUOp() {
		return this.ex[1];
	}

	public int getRegDst() {
		return this.ex[2];
	}

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

	public int getRt() {
		return rt;
	}

	public void setRt(int rt) {
		this.rt = rt;
	}

	public int getRd() {
		return rd;
	}

	public void setRd(int rd) {
		this.rd = rd;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}
	
	//public String printId_Excontents() {
	public String print(){
		String wbControlSignals = "	RegWrite: " + this.wb[0] + "		MemToReg: "
				+ this.wb[1];

		String mControlSignals = "MemRead: " + this.m[0] + "		MemWrite: "
				+ this.m[1] + "		Branch: " + this.m[2];

		String exControlSignals = "ALUSrc: " + this.ex[0] + "		ALUOp: "
				+ this.ex[1] + "		RegDst: " + this.ex[2];

		String allControlSignals = "ID/Ex contents:\n " + wbControlSignals
				+ "\n	" + mControlSignals + "\n	" + exControlSignals;

		String restOfContents = "\n\n	NextPc: " + this.nextPC + "\n	Read Data1: "
				+ this.readData1 + "		Read Data2: " + this.readData2
				+ "\n	Extend: " + this.getExtend() + "\n	rt: "
				+ this.getRt() + "		rd: " + this.getRd();

		return (allControlSignals + restOfContents);
	}
	
	// **
	public String getContent(){
		return  "" + nextPC + " " + readData1 + " " + readData2 + " " + extend + " " 
				+ rt + " " + rd +" "
				+ wb[0] + "" + wb[1] + " "
				+ m[0] + "" + m[1] + "" + m[2] + " "
				+ ex[0] + "" + ex[1] + "" +ex[2];
		
	}

}
