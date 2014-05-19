package pipeling_registers;

public class IF_ID {
	private int nextPC;
	private String[] instruction;

	public IF_ID() {
		// this.nextPC = 5;
		this.instruction = null;
	}

	public int getNextPC() {
		return nextPC;
	}

	public void setNextPC(int nextPC) {
		this.nextPC = nextPC;
	}

	public String[] getInstruction() {
		return instruction;
	}

	public void setInstruction(String[] instruction) {
		this.instruction = instruction;
	}

	// method that returns the operation of the instruction
	public String getOp() {
		return this.instruction[0];
	}

	// public String printIf_IDcontents(){
	public String print() {
		String s = "";
		for (int i = 0; i < this.instruction.length; i++)
			s += this.instruction[i] + "|";
		return ("IF/ID contents:\n"
				+ "	NextPc = " + this.nextPC + "\n"
						+ "	instruction: " + s);
	}
}
