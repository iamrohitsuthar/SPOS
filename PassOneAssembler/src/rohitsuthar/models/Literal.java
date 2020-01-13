package rohitsuthar.models;
/* 
   Literal class which is used to represent the Literal with
   literal no, name, and its address
*/
public class Literal {
	private int no;
	private long address;
	private String name;
	
	public Literal(int no, String name, long address) {
		this.no = no;
		this.name = name;
		this.address = address;
	}
	
	public void setAddress(long address) {
		this.address = address;
	}
	
	public int getNumber() {
		return no;
	}
	
	public long getAddress() {
		return address;
	}
	
	public String getName() {
		return name;
	}
}
