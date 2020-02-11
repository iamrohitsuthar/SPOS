
public class MacroDefModel {
	private int positionalParams;
	private int keywordParams;
	private String macroDefPointer = null;
	private String keywordsDefPointer = null;
	
	public MacroDefModel(int positionalParams, int keywordParams) {
		this.positionalParams = positionalParams;
		this.keywordParams = keywordParams;
	}
	
	public MacroDefModel(int positionalParams, int keywordParams, String mPointer, String kPointer) {
		this.positionalParams = positionalParams;
		this.keywordParams = keywordParams;
		this.macroDefPointer = mPointer;
		this.keywordsDefPointer = kPointer;
	}
	
	public void setMacroDefPointer(String pointer) {
		this.macroDefPointer = pointer;
	}
	
	public void setKeywordsDefPointer(String pointer) {
		this.keywordsDefPointer = pointer;
	}
	
	public String getMacroDefPointer() {
		return macroDefPointer;
	}
	
	public String getKeywordsDefPointer() {
		return keywordsDefPointer;
	}
	
	public int getPositionalParams() {
		return positionalParams;
	}
	
	public int getKeywordParams() {
		return keywordParams;
	}
}
