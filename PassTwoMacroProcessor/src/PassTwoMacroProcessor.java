import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

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
			//Msg.println("MNT read: "+line);
			String params[] = line.split(" ");
			MNT.put(params[1],new MacroDefModel(Integer.parseInt(params[2]), Integer.parseInt(params[3]), params[4], params[5]));
		}
		bufferedReader.close();
		
		//read KPDTAB
		bufferedReader = new BufferedReader(new FileReader("KPDTAB.txt"));
		while((line = bufferedReader.readLine()) != null) {
			//Msg.println("KPDTAB read: "+line);
			String params[] = line.split(" ");
			KPDTAB.put(params[1], params[2]);
		}
		bufferedReader.close();
		
		//read MDT
		bufferedReader = new BufferedReader(new FileReader("MDT.txt"));
		while((line = bufferedReader.readLine()) != null) {
			//Msg.println("MDT read: "+line);
			String params[] = line.split(" ");
			if(params.length > 2)
				MDT.add(params[1] + " " + params[2] + " " + params[3]);
			else
				MDT.add(params[1]);			
		}
		bufferedReader.close();
	}
	
	private String getFromHashMap(int index) {
		int temp = 1;
		String value = null;
		for ( Entry<String, String> e : KPDTAB.entrySet() ) {
		    value = e.getValue();
		    if(temp == index)
		    	break;
		    index++;
		}
		return value;
	}
	
	public void parse(BufferedReader bufferedReader) throws IOException {
		String prev = null;
		while((line = bufferedReader.readLine()) != null) {
			String parts[] = line.split(" ");
			if(MNT.containsKey(parts[0])) {
				//macro call
				APTAB.clear();
				
				int pp = MNT.get(parts[0]).getPositionalParams();
				int kp = MNT.get(parts[0]).getKeywordParams();
				int kpdtabPointer = -1;
				if(!MNT.get(parts[0]).getKeywordsDefPointer().equals("null"))
					kpdtabPointer = Integer.parseInt(MNT.get(parts[0]).getKeywordsDefPointer());
				
				int total = pp + kp;
				
				int positionalPar = 1;
				for(int i = 1 ; i <= pp ; i++) {
					String extract = parts[positionalPar++].replaceAll("[,]+", "");
					//Msg.println("APTAB: "+extract);
					APTAB.add(extract.trim());
				}
				int keywordPar = positionalPar;
				for(int j = 0 ; j < kp ; j++) {
					if((parts.length-1) >= keywordPar) {
						String extract = parts[keywordPar].replaceAll("[,]+", "");
						extract = extract.substring(extract.indexOf("=")+1);
						//Msg.println("APTAB No Default: "+extract);
						APTAB.add(extract.trim());
					}
					else {
						//default value
						String temp = getFromHashMap(kpdtabPointer+j);
						//Msg.println("APTAB Default value: "+ temp);
						APTAB.add(temp.trim());
					}
					keywordPar++;
				}
				int mdtPointer = Integer.parseInt(MNT.get(parts[0]).getMacroDefPointer());
//				if(prev != null && prev.equals("MEND"))
//					mdtPointer++;
				String ins = MDT.get(mdtPointer-1);
				prev = ins;
				Msg.println("HEL: "+ins);
				while(!ins.equals("MEND")) {
					Msg.println(ins);
					String params[] = ins.split(" ");
					bufferedWriter.write(params[0] + " ");
					for(int i = 1; i < params.length; i++) {
						String temp = params[i].replaceAll("[^0-9]", "");
						if(i == params.length - 1)
							bufferedWriter.write(APTAB.get(Integer.parseInt(temp)));
						else
							bufferedWriter.write(APTAB.get(Integer.parseInt(temp)) + ", ");
					}
					bufferedWriter.write("\n");
					ins = MDT.get(++mdtPointer-1);
					prev = ins;
					Msg.println("HEL1: "+ins);
				}
			}
			else {
				bufferedWriter.write(line + "\n");
			}
		}
		bufferedWriter.flush();
		bufferedWriter.close();
	}
}
