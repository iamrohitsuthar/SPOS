
public class Symbol {
	private String name;
	private int address;
	
	public Symbol(String name, int address) {
		this.name = name;
		this.address = address;
	}
	
	public int getAddress() {
		return address;
	}
	
	public String getName() {
		return name;
	}
}
