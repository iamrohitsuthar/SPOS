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

import rohitsuthar.models.Literal;
import rohitsuthar.models.MnemonicsEntry;
import rohitsuthar.models.Symbol;
import rohitsuthar.others.Msg;

public class PassOneAsm {
		private String line;
		private Hashtable<String, Literal> literalTable;
		private Hashtable<String, Symbol> symbolTable;
		private HashMap<String, MnemonicsEntry> mnemonics;
		private static BufferedReader bufferedReader = null;
		private int lineCount = 0;
		private MnemonicsEntry mnemonicsEntryItem;
		BufferedWriter icBufferedWriter = null;
		
		
		public PassOneAsm() {
			literalTable = new Hashtable<String, Literal>();
			symbolTable = new Hashtable<String, Symbol>();
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
		
		public void parse(BufferedReader bufferedReader) {
			try {
				while((line = bufferedReader.readLine()) != null) {
					String []arr = line.split("\t");
					writeIntermediateCode(line);
					
					if(!arr[0].isEmpty())
						symbolTable.put(arr[0], new Symbol(arr[0], lineCount));
					
					if(mnemonics.get(arr[1]).isImperative()) {
						// IS Statements
						writeIntermediateCode("\t"+lineCount);
						mnemonicsEntryItem = mnemonics.get(arr[1]);
						writeIntermediateCode("\t(" + mnemonicsEntryItem.getType() + "," + mnemonicsEntryItem.getCode() + ")");
						if(mnemonics.containsKey(arr[2].substring(0, arr[2].length()-1))) {
							writeIntermediateCode("(" + mnemonics.get(arr[2].substring(0, arr[2].length()-1)).getCode() + ")");
						}
						if(arr.length == 4) {
							// fourth parameter is present
							// -1 means address is not allocated at this time
							symbolTable.put(arr[3], new Symbol(arr[3], -1));
							writeIntermediateCode("(S," + symbolTable.get(arr[3]).getNo() + ")");
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
						lineCount++;
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
							String code = data.substring(0, data.indexOf('+'));
							int num = Integer.parseInt(data.substring(data.indexOf('+')+1,data.length()));
							symbolTable.replace(arr[0], new Symbol(arr[0], symbolTable.get(code).getAddress() + num));
							lineCount++;
						}
						writeIntermediateCode("\n");
					}
							
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
