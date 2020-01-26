package rohitsuthar.models;
/* 
	Symbol class which is used to represent the Symbol with
	Symbol no, name, and its address
*/
public class Symbol {
	private int number =  0;
	private long address;
	
	public Symbol(int number, long address) {
		this.number = number;
		this.address = address;
	}
	
	public void setAddress(long address) {
		this.address = address;
	}
	
	public int getNumber() {
		return number;
	}
	
	public long getAddress() {
		return address;
	}
}
