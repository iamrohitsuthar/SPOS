package rohitsuthar.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import rohitsuthar.models.Literal;
import rohitsuthar.models.MnemonicsEntry;
import rohitsuthar.models.Symbol;
import rohitsuthar.others.Msg;

public class PassOneAsm {
		private String line;
		private Hashtable<String, Literal> literalTable;
		private LinkedHashMap<String, Symbol> symbolTable;
		private HashMap<String, MnemonicsEntry> mnemonics;
		private static BufferedReader bufferedReader = null;
		private int lineCount = 0;
		private MnemonicsEntry mnemonicsEntryItem;
		BufferedWriter icBufferedWriter = null;
		
		
		public PassOneAsm() {
			literalTable = new Hashtable<String, Literal>();
			symbolTable = new LinkedHashMap<String, Symbol>();
			mnemonics = new HashMap<String, MnemonicsEntry>();
			try {
				getMnemonicsData();
				icBufferedWriter = new BufferedWriter(new FileWriter("IC.txt"));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		protected void finalize() throws Throwable {
			super.finalize();
			icBufferedWriter.close();
		}
		
		private void getMnemonicsData() throws IOException {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("OPTAB.txt")));
			while((line = bufferedReader.readLine()) != null) {
				String []arr = line.split(" ");
				mnemonics.put(arr[0], new MnemonicsEntry(arr[1], Integer.parseInt(arr[2])));
			}
			bufferedReader.close();
		}
		
		private void writeIntermediateCode(String data) throws IOException {
			icBufferedWriter.write(data);
			icBufferedWriter.flush();
			Msg.println("Written Successfully");
		}
		
	    private static boolean isNumber(String s) 
	    { 
	        for (int i = 0; i < s.length(); i++) 
	        if (Character.isDigit(s.charAt(i))  
	            == false) 
	            return false; 
	  
	        return true; 
	    }
	    
	    private int getOperatorIndex(String data) {
	    	int index = -1;
	    	index = data.indexOf('+');
	    	if(index == -1)
	    		index = data.indexOf('-');
	    	
	    	return index;
	    	
	    }
		
		public void parse(BufferedReader bufferedReader) {
			try {
				while((line = bufferedReader.readLine()) != null) {
					String []arr = line.split("\t");
					writeIntermediateCode(line);
					
					if(!arr[0].isEmpty()) {
						if(!symbolTable.containsKey(arr[0])) {
							symbolTable.put(arr[0], new Symbol(arr[0], lineCount));
						}
					}
						
					
					if(mnemonics.get(arr[1]).isImperative()) {
						// IS Statements
						writeIntermediateCode("\t"+lineCount);
						mnemonicsEntryItem = mnemonics.get(arr[1]);
						writeIntermediateCode("\t(" + mnemonicsEntryItem.getType() + "," + mnemonicsEntryItem.getCode() + ")");
						
						if(mnemonics.containsKey(arr[2].substring(0, arr[2].length()-1))) {
							writeIntermediateCode("(" + mnemonics.get(arr[2].substring(0, arr[2].length()-1)).getCode() + ")");
						}
						else {
							if(!symbolTable.containsKey(arr[2])) {
								Msg.println(arr[2] + " there");
								symbolTable.put(arr[2], new Symbol(arr[2], -1));
								writeIntermediateCode("(S," + symbolTable.get(arr[2]).getNumber() + ")");
							}else {
								Msg.println(arr[2] + " here");
								writeIntermediateCode("(S," + symbolTable.get(arr[2]).getNumber() + ")");
							}
						}
						
						if(arr.length == 4) {
							// fourth parameter is present
							// -1 means address is not allocated at this time
							symbolTable.put(arr[3], new Symbol(arr[3], -1));
							writeIntermediateCode("(S," + symbolTable.get(arr[3]).getNumber() + ")");
						}
						writeIntermediateCode("\n");
						lineCount++;
					}
					else if(mnemonics.get(arr[1]).isDeclarative()) {
						//DL Statements
						writeIntermediateCode("\t"+lineCount);
						mnemonicsEntryItem = mnemonics.get(arr[1]);
						writeIntermediateCode("\t(" + mnemonicsEntryItem.getType() + "," + mnemonicsEntryItem.getCode() + ")");
						writeIntermediateCode("(C," + arr[2] + ")");
						writeIntermediateCode("\n");
						if(arr[1].equals("DS")) {
							lineCount += Integer.parseInt(arr[2]);
						}
						else {
							lineCount++;
						}
					}
					else {
						if(arr[1].equals("START")) {
							lineCount = Integer.parseInt(arr[2]);
							mnemonicsEntryItem = mnemonics.get(arr[1]);
							writeIntermediateCode("\t(" + mnemonicsEntryItem.getType() + "," + mnemonicsEntryItem.getCode() + ")");
							writeIntermediateCode("(C," + arr[2] + ")");
						}
						else if(arr[1].equals("EQU")) {
							String data = arr[2];
							int index = getOperatorIndex(data);			
							String code = data.substring(0, index);
							int num = Integer.parseInt(data.substring(index+1,data.length()));
							
							Symbol symbol = symbolTable.get(code);
							Msg.println("SYM1 " + arr[0] + " val: "+symbolTable.get(arr[0]).getNumber());
							if(data.contains("+")) {
								symbol.setAddress(symbol.getAddress() + num);
								Msg.println("SYM123 " + arr[0] + " val: "+ symbol.getNumber());
								Msg.println("SYM2 " + arr[0] + " val: "+symbolTable.get(arr[0]).getNumber());
								symbolTable.replace(arr[0], symbol);
								Msg.println("SYM3 " + arr[0] + " val: "+symbolTable.get(arr[0]).getNumber());
							}
							else if(data.contains("-")) {
								symbol.setAddress(symbol.getAddress() - num);
								symbolTable.replace(arr[0], symbol);
							}
							Msg.println("SYM " + arr[0] + " val: "+symbolTable.get(arr[0]).getNumber());
						}
						else if(arr[1].equals("ORIGIN")) {
							String data = arr[2];
							int index = getOperatorIndex(data);
							
							String code = data.substring(0, index);
							int num = Integer.parseInt(data.substring(index+1,data.length()));
							if(data.contains("+"))
								lineCount = (int) (symbolTable.get(code).getAddress() + num);
							else if(data.contains("-"))
								lineCount = (int) (symbolTable.get(code).getAddress() - num);
							Msg.println("Line count: "+lineCount);
							
						}
						writeIntermediateCode("\n");
					}
							
				}
				writeSymbolTable();
				writeLiteralTable();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
		
	private void writeSymbolTable() {
		try {
			icBufferedWriter = new BufferedWriter(new FileWriter("SYMTAB.txt"));
			for (Map.Entry<String, Symbol> entry : symbolTable.entrySet()) {
				Symbol symbol = entry.getValue();
			    icBufferedWriter.write(symbol.getNumber() + " " + symbol.getName() + " " + symbol.getAddress());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void writeLiteralTable() {
		try {
			icBufferedWriter = new BufferedWriter(new FileWriter("LITTAB.txt"));
			for (Map.Entry<String, Literal> entry : literalTable.entrySet()) {
				Literal literal = entry.getValue();
			    icBufferedWriter.write(literal.getNumber() + " " + literal.getName() + " " + literal.getAddress());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
