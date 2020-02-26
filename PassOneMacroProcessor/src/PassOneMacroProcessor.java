import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;


public class PassOneMacroProcessor {
	private String line = null;
	private BufferedWriter bufferedWriter = null;
	private LinkedHashMap<String,String> KPDTAB;
	private ArrayList<String> PNTAB;
	private LinkedHashMap<String, MacroDefModel> MNT;
	private int keywordTablePointer = 1;
	private int MacroTablePointer = 1;
	private StringBuilder MDT;
	
	public PassOneMacroProcessor() throws IOException {
		PNTAB = new ArrayList<String>();
		KPDTAB = new LinkedHashMap<String, String>();
		MNT = new LinkedHashMap<String, MacroDefModel>();
		MDT = new StringBuilder();
		bufferedWriter = new BufferedWriter(new FileWriter("input_new.asm"));
	}
	
	public StringBuilder getMDT() {
		return MDT;
	}
	
	public HashMap<String, MacroDefModel> getMNT() {
		return MNT;
	}
	
	public HashMap<String,String> getKPDTAB() {
		return KPDTAB;
	}
	
	private void writeFile(String data) throws IOException {
		bufferedWriter.write(data);
		bufferedWriter.flush();
	}

	public void parse(BufferedReader bufferedReader) {
		int ppCount = 0;
		int kpCount = 0;
		try {
			//read from file until we get the "START"
			while((line = bufferedReader.readLine()) != null) {
					if(line.equals("MACRO")) {
						String params[] = bufferedReader.readLine().split(" ");
						for (int i = 1; i < params.length ; i++) {
							String string  = params[i];
							if(string.contains("=")) {
								kpCount++;
								String key = string.substring(string.indexOf("&")+1, string.indexOf("="));
								if(string.indexOf("=") < string.length() - 1) {
									//default value is present
									String value;
									if(string.contains(","))
										value = string.substring(string.indexOf("=")+1,string.indexOf(","));
									else
										value = string.substring(string.indexOf("=")+1);
									KPDTAB.put(key, value);
								}
								else {
									KPDTAB.put(key, null);
								}
								keywordTablePointer++;
								PNTAB.add(key.trim());
							}
							else {
								ppCount++;
								String extract = string.replaceAll("[&,]+", "");
								PNTAB.add(extract);
							}
						}
						
						MNT.put(params[0], new MacroDefModel(ppCount, kpCount));
						if(kpCount > 0)
							MNT.get(params[0]).setKeywordsDefPointer(String.valueOf(keywordTablePointer - kpCount));
						else
							MNT.get(params[0]).setKeywordsDefPointer(null);
						MNT.get(params[0]).setMacroDefPointer(String.valueOf(MacroTablePointer));
						
						// Generating MDT
						String macroBodyInstructions = bufferedReader.readLine();
						while(!macroBodyInstructions.equals("MEND")) {
							String splittedInstructions[] = macroBodyInstructions.split(" ");
							MDT.append(MacroTablePointer + " " + splittedInstructions[0]);
							for(int i = 1 ; i < splittedInstructions.length ; i++) {
								if(splittedInstructions[i].contains("=")) {
									MDT.append(" "+splittedInstructions[i]);
								}
								else {
									String extract = splittedInstructions[i].replaceAll("[^a-zA-Z_]+", "");
									if(i < splittedInstructions.length - 1 )
										MDT.append(" (P,"+PNTAB.indexOf(extract.trim()) + "),");
									else
										MDT.append(" (P,"+PNTAB.indexOf(extract.trim()) + ")");
								}
								
							}
							MDT.append("\n");
							MacroTablePointer++;
							macroBodyInstructions = bufferedReader.readLine();
						}
						MDT.append(MacroTablePointer + " " + "MEND" + "\n");
						MacroTablePointer++;
						if(macroBodyInstructions.equals("MEND")) {
							PNTAB.clear();
							ppCount = 0;
							kpCount = 0;
						}
				}
				else {
					// write remaining file content after START symbol into new file
					writeFile(line + "\n");
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
