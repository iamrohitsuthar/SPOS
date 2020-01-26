package rohitsuthar.models;

public class MnemonicsEntry {
	private String type;
	private String code;
	
	public MnemonicsEntry(String type, String code) {
		this.type = type;
		this.code = code;
	}
	
	public String getCode() {
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
