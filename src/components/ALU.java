package components;

public class ALU {
	private int data1;
	private int data2;
	private String op;
	private int zero;
	private int result;

	public ALU() {

	}

	public void setData1(int data1) {
		this.data1 = data1;
	}

	public void setData2(int data2) {
		this.data2 = data2;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public int getZero() {
		return zero;
	}

	public void setZero(int zero) {
		this.zero = zero;
	}

	// runs alu based on the operation
	public int run(String op) {
		switch (op) {
		case "add":
		case "addi":
			this.result = this.data1 + this.data2;
			break;
		case "sub":
			this.result = this.data1 - this.data2;
			break;
		case "and":
		case "andi":
			this.result = this.data1 & this.data2;
			break;
		case "or":
		case "ori":
			this.result = this.data1 | this.data2;
			break;
		case "nor":
			this.result = -~(this.data1 | this.data2);
			break;
		case "slt":
		case "sltu":
			this.result = (this.data1 < this.data2) ? 1 : 0;
			break;
		case "sll":
			this.result = this.data1 << this.data2;
			break;
		case "srl":
			this.result = this.data1 >> this.data2;
			break;
		case "beq":
			this.zero = (this.data1 == this.data2) ? 1 : 0;
			break;
		case "bne":
			this.zero = (this.data1 != this.data2) ? 1 : 0;
			break;
		case "lw":
		case "sw":
			this.result = this.data1 + this.data2;
			break;
		}

		return this.result;
	}

}
