package rohitsuthar.models;
/* 
	Symbol class which is used to represent the Symbol with
	Symbol no, name, and its address
*/
public class Symbol {
	private static int no = 0;
	private long address;
	private String name;
	
	public Symbol(String name, long address) {
		no++;
		this.name = name;
		this.address = address;
	}
	
	public void setAddress(long address) {
		this.address = address;
	}
	
	public int getNo() {
		return no;
	}
	
	public long getAddress() {
		return address;
	}
	
	public String getName() {
		return name;
	}
}
