package rohitsuthar.models;

public class MnemonicsEntry {
	private String type;
	private int code;
	
	public MnemonicsEntry(String type, int code) {
		this.type = type;
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getType() {
		return type;
	}
	
	public boolean isDeclarative() {
		return type.equals("DL");
	}
	
	public boolean isImperative() {
		return type.equals("IS");
	}
}
