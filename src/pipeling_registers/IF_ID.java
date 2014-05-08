package pipeling_registers;

public class IF_ID {
	private int nextPC;
	private String[] instruction;

	public IF_ID() {
		this.nextPC = 0;
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
}
