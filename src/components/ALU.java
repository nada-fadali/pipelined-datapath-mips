package components;

public class ALU {
	private int data1;
	private int data2;
	private String op;
	private int zero;
	private int result;

	public ALU() {

	}

	// getters & setters
	public int getData1() {
		return data1;
	}

	public void setData1(int data1) {
		this.data1 = data1;
	}

	public int getData2() {
		return data2;
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
			this.data1 = Integer.parseInt(Integer.toBinaryString(this.data1));
			this.data2 = Integer.parseInt(Integer.toBinaryString(this.data2));
			this.result = this.data1 & this.data2;
			break;
		case "or":
		case "ori":
			this.data1 = Integer.parseInt(Integer.toBinaryString(this.data1));
			this.data2 = Integer.parseInt(Integer.toBinaryString(this.data2));
			this.result = this.data1 | this.data2;
			break;
		case "nor":
			this.data1 = Integer.parseInt(Integer.toBinaryString(this.data1));
			this.data2 = Integer.parseInt(Integer.toBinaryString(this.data2));
			
			break;
		case "slt":
		case "sltu":
			this.result = (this.data1 < this.data2)? 1 : 0;
			break;
		case "sll":
			this.result = this.data1 << this.data2;
			break;
		case "srl":
			this.result = this.data1 >> this.data2;
			break;

		}

		return this.result;
	}

	public static void main(String[] args) {
		int x = 7;
		int y = 4;
		System.out.println(- ~(x | y));
	}

}
