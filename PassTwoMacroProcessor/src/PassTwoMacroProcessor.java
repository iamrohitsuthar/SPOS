import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class PassTwoMacroProcessor {
	private BufferedWriter bufferedWriter = null;
	private BufferedReader bufferedReader = null;
	private LinkedHashMap<String,String> KPDTAB;
	private ArrayList<String> APTAB;
	private LinkedHashMap<String, MacroDefModel> MNT;
	private ArrayList<String> MDT;
	private String line = null;
	private static String OUTPUT_FILE_NAME = "Output.txt";
	
	public PassTwoMacroProcessor() throws IOException {
		APTAB = new ArrayList<String>();
		MDT = new ArrayList<>();
		KPDTAB = new LinkedHashMap<String, String>();
		MNT = new LinkedHashMap<String, MacroDefModel>();
		bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
		
		//read MNT
		bufferedReader = new BufferedReader(new FileReader("MNT.txt"));
		while((line = bufferedReader.readLine()) != null) {
			Msg.println("MNT read: "+line);
			String params[] = line.split(" ");
			MNT.put(params[1],new MacroDefModel(Integer.parseInt(params[2]), Integer.parseInt(params[3]), params[4], params[5]));
		}
		bufferedReader.close();
		
		//read KPDTAB
		bufferedReader = new BufferedReader(new FileReader("KPDTAB.txt"));
		while((line = bufferedReader.readLine()) != null) {
			Msg.println("KPDTAB read: "+line);
			String params[] = line.split(" ");
			KPDTAB.put(params[1], params[2]);
		}
		bufferedReader.close();
		
		//read MDT
		bufferedReader = new BufferedReader(new FileReader("MDT.txt"));
		while((line = bufferedReader.readLine()) != null) {
			Msg.println("MDT read: "+line);
			String params[] = line.split(" ");
			if(params.length > 2)
				MDT.add(params[1] + " " + params[2] + " " + params[3]);
			else
				MDT.add(params[1]);			
		}
		bufferedReader.close();
		
	}
	
	public void parse(BufferedReader bufferedReader) throws IOException {
		while((line = bufferedReader.readLine()) != null) {
			String parts[] = line.split(" ");
			if(MNT.containsKey(parts[0])) {
				//macro call
				int pp = MNT.get(parts[0]).getPositionalParams();
				int kp = MNT.get(parts[0]).getKeywordParams();
				
				int positionalPar = 1;
				for(int i = 1 ; i <= pp ; i++) {
					String extract = parts[positionalPar++].replaceAll("[,]+", "");
					Msg.println("APTAB: "+extract);
					APTAB.add(extract.trim());
				}
				int keywordPar = positionalPar;
				for(int j = 1 ; j <= kp ; j++) {
					String extract = parts[positionalPar++].replaceAll("[,]+", "");
					Msg.println("APTAB: "+extract);
					APTAB.add(extract.trim());
				}
				
			}
			else {
				bufferedWriter.write(line + "\n");
			}
		}
	}
}
