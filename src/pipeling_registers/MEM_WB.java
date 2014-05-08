package pipeling_registers;

public class MEM_WB {
	private int read_Data;
	private int alu_Result;
	private int mux3Output;

	private int[] wb;

	public MEM_WB() {
		this.wb = new int[2];
	}

	// getters & setters
	public void setAlu_Result(int alu_Result) {
		this.alu_Result = alu_Result;
	}

	public int getAlu_Result() {
		return alu_Result;
	}

	public void setRead_Data(int read_Data) {
		this.read_Data = read_Data;
	}

	public int getRead_Data() {
		return read_Data;
	}

	public int getMux3Output() {
		return mux3Output;
	}

	public void setMux3Output(int mux3Output) {
		this.mux3Output = mux3Output;
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
