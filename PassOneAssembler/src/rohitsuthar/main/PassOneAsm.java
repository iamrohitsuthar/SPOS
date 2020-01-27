package rohitsuthar.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import rohitsuthar.models.MnemonicsEntry;
import rohitsuthar.models.Symbol;
import rohitsuthar.others.Msg;

public class PassOneAsm {
		private String line;
		private ArrayList<LinkedHashMap<String, Integer>> literalTable;
		private LinkedHashMap<String, Symbol> symbolTable;
		private HashMap<String, MnemonicsEntry> mnemonics;
		private ArrayList<Integer> poolTable;
		private static BufferedReader bufferedReader = null;
		private int lineCount = 0;
		private MnemonicsEntry mnemonicsEntryItem;
		BufferedWriter icBufferedWriter = null;
		private int symbol_table_track = 0;
		private int literal_table_track = 0;
		private boolean isPresent = false;
		
		public PassOneAsm() {
			literalTable = new ArrayList<LinkedHashMap<String,Integer>>();
			symbolTable = new LinkedHashMap<String, Symbol>();
			poolTable = new ArrayList<Integer>();
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
				mnemonics.put(arr[0], new MnemonicsEntry(arr[1], arr[2]));
			}
			bufferedReader.close();
		}
		
		private void writeIntermediateCode(String data) throws IOException {
			icBufferedWriter.write(data);
			icBufferedWriter.flush();
		}
	    
	    private int getOperatorIndex(String data) {
	    	int index = -1;
	    	index = data.indexOf('+');
	    	if(index == -1)
	    		index = data.indexOf('-');
	    	
	    	return index;
	    	
	    }
		
	    private String format(int data) {
	    	String raw = String.valueOf(data);
	    	if(data <= 9)
	    		raw = "0"+String.valueOf(data);
	    	return raw;
	    }
	    
		public void parse(BufferedReader bufferedReader) {
			poolTable.add(0);
			try {
				while((line = bufferedReader.readLine()) != null) {
					String []arr = line.split("\t");
					if(!arr[0].isEmpty()) {
						if(!symbolTable.containsKey(arr[0])) {
							symbolTable.put(arr[0], new Symbol(symbol_table_track++, lineCount));
						}
					}
					if(mnemonics.get(arr[1]).isImperative()) {
						// IS Statements
						writeIntermediateCode(String.valueOf(lineCount));
						mnemonicsEntryItem = mnemonics.get(arr[1]);
						writeIntermediateCode("\t(" + mnemonicsEntryItem.getType() + "," + mnemonicsEntryItem.getCode() + ")");
						
						if(arr.length > 2) {
							// for stop condition
							if(mnemonics.containsKey(arr[2].substring(0, arr[2].length()-1))) {
								writeIntermediateCode("(" + mnemonics.get(arr[2].substring(0, arr[2].length()-1)).getCode() + ")");
							}
							else {
								if(!symbolTable.containsKey(arr[2])) {
									symbolTable.put(arr[2], new Symbol(symbol_table_track++, -1));
									writeIntermediateCode("(S," + format(symbolTable.get(arr[2]).getNumber()) + ")");
								}else {
									writeIntermediateCode("(S," + format(symbolTable.get(arr[2]).getNumber()) + ")");
								}
							}
						}

						if(arr.length == 4) {
							// fourth parameter is present
							// -1 means address is not allocated at this time
							if(arr[3].contains("=")) {
								//literal
								String data = arr[3];
								Iterator<LinkedHashMap<String, Integer>> iterator = literalTable.iterator();
								LinkedHashMap<String , Integer> linkedHashMap;
								int i = 0;
								while(iterator.hasNext()) {
									LinkedHashMap<String, Integer> hashMap = iterator.next();
									String key = hashMap.entrySet().iterator().next().getKey();
									Integer address = hashMap.entrySet().iterator().next().getValue();
									if(key.equals(data)) {
										if(address == -1) {
											isPresent = true;
											writeIntermediateCode("(L," + format(i) + ")");
										}
									}
									i++;
								}
								if(!isPresent) {
									linkedHashMap = new LinkedHashMap<String, Integer>();
									linkedHashMap.put(data, -1);
									writeIntermediateCode("(L," + format(literal_table_track) + ")");
									literalTable.add(literal_table_track++,linkedHashMap);
								}
								isPresent = false;
							}
							else {
								//symbol
								if(!symbolTable.containsKey(arr[3])) {
									symbolTable.put(arr[3], new Symbol(symbol_table_track++, -1));
									writeIntermediateCode("(S," + format(symbolTable.get(arr[3]).getNumber()) + ")");
								}else {
									writeIntermediateCode("(S," + format(symbolTable.get(arr[3]).getNumber()) + ")");
								}
							}
						}
						writeIntermediateCode("\n");
						lineCount++;
					}
					else if(mnemonics.get(arr[1]).isDeclarative()) {
						//DL Statements
						writeIntermediateCode(String.valueOf(lineCount));
						mnemonicsEntryItem = mnemonics.get(arr[1]);
						writeIntermediateCode("\t(" + mnemonicsEntryItem.getType() + "," + mnemonicsEntryItem.getCode() + ")");
						String constantData = arr[2];
						constantData = constantData.replace("\'", "");
						writeIntermediateCode("(C," + constantData + ")");
						writeIntermediateCode("\n");
						Symbol symbol = symbolTable.get(arr[0]);
						symbol.setAddress(lineCount);
						symbolTable.replace(arr[0], symbol);
						if(arr[1].equals("DS")) {
							lineCount += Integer.parseInt(arr[2]);
						}
						else {
							lineCount++;
						}
					}
					else {
						if(arr[1].equals("START")) {
							if(arr.length > 2) {
								lineCount = Integer.parseInt(arr[2]);
							}
							mnemonicsEntryItem = mnemonics.get(arr[1]);
							writeIntermediateCode("\t(" + mnemonicsEntryItem.getType() + "," + mnemonicsEntryItem.getCode() + ")");
							writeIntermediateCode("(C," + lineCount + ")");
							writeIntermediateCode("\n");
						}
						else if(arr[1].equals("EQU")) {
							String data = arr[2];
							int index = getOperatorIndex(data);			
							String code = data.substring(0, index);
							int num = Integer.parseInt(data.substring(index+1,data.length()));
							
							Symbol symbol = symbolTable.get(code);
							Symbol symbol1 = symbolTable.get(arr[0]);
							if(data.contains("+")) {
								symbol1.setAddress(symbol.getAddress() + num);
							}
							else if(data.contains("-")) {
								symbol1.setAddress(symbol.getAddress() - num);
							}
							writeIntermediateCode("\n");
						}
						else if(arr[1].equals("ORIGIN")) {
							String data = arr[2];
							int index = getOperatorIndex(data);
							
							mnemonicsEntryItem = mnemonics.get(arr[1]);
							writeIntermediateCode("\t(" + mnemonicsEntryItem.getType() + "," + mnemonicsEntryItem.getCode() + ")");
							
							String code = data.substring(0, index);
							int num = Integer.parseInt(data.substring(index+1,data.length()));
							if(data.contains("+")) {
								lineCount = (int) (symbolTable.get(code).getAddress() + num);
								writeIntermediateCode("(S," + format(symbolTable.get(code).getNumber()) + ")+"+num);
							}
							else if(data.contains("-")) {
								lineCount = (int) (symbolTable.get(code).getAddress() - num);
								writeIntermediateCode("(S," + format(symbolTable.get(code).getNumber()) + ")-"+num);
							}
							writeIntermediateCode("\n");
						}
						else if(arr[1].equals("END")) {
							mnemonicsEntryItem = mnemonics.get(arr[1]);
							writeIntermediateCode("\t(" + mnemonicsEntryItem.getType() + "," + mnemonicsEntryItem.getCode() + ")");
							writeIntermediateCode("\n");
							Iterator<LinkedHashMap<String, Integer>> iterator = literalTable.iterator();
							while(iterator.hasNext()) {
								LinkedHashMap<String, Integer> hashMap = iterator.next();
								String key = hashMap.entrySet().iterator().next().getKey();
								Integer address = hashMap.entrySet().iterator().next().getValue();
								if(address == -1) {
									String key1 = key;
									key1 = key1.replace("\'", "");
									key1 = key1.replace("=", "");
									writeIntermediateCode(String.valueOf(lineCount));
									writeIntermediateCode("\t(DL,01)(C,"+key1+")\n");
									hashMap.replace(key, lineCount);
									lineCount++;
								}
							}
							writeIntermediateCode("\n");
						}
						else if(arr[1].equals("LTORG")) {
							Iterator<LinkedHashMap<String, Integer>> iterator = literalTable.iterator();
							while(iterator.hasNext()) {
								LinkedHashMap<String, Integer> hashMap = iterator.next();
								String key = hashMap.entrySet().iterator().next().getKey();
								Integer address = hashMap.entrySet().iterator().next().getValue();
								if(address == -1) {
									String key1 = key;
									key1 = key1.replace("\'", "");
									key1 = key1.replace("=", "");
									writeIntermediateCode(String.valueOf(lineCount));
									writeIntermediateCode("\t(DL,01)(C,"+key1+")\n");
									hashMap.replace(key, lineCount);
									lineCount++;
								}
							}
							poolTable.add(literal_table_track);
						}
					}
							
				}
				writeSymbolTable();
				writeLiteralTable();
				writePoolTable();
				Msg.println("Done");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
		
	private void writeSymbolTable() {
		int i = 0;
		try {
			icBufferedWriter.close();
			icBufferedWriter = new BufferedWriter(new FileWriter("SYMTAB.txt"));
			for (Map.Entry<String, Symbol> entry : symbolTable.entrySet()) {
				Symbol symbol = entry.getValue();
				String key = entry.getKey();
			    icBufferedWriter.write(i+ " " + key + " " + symbol.getAddress());
			    icBufferedWriter.write("\n");
			    i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void writeLiteralTable() {
		try {
			int i = 0;
			icBufferedWriter.close();
			icBufferedWriter = new BufferedWriter(new FileWriter("LITTAB.txt"));
			Iterator<LinkedHashMap<String, Integer>> iterator = literalTable.iterator();
			while(iterator.hasNext()) {
				LinkedHashMap<String, Integer> hashMap = iterator.next();
				String key = hashMap.entrySet().iterator().next().getKey();
				Integer address = hashMap.entrySet().iterator().next().getValue();
				icBufferedWriter.write(i + " " + key + " " + address);
			    icBufferedWriter.write("\n");
			    i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writePoolTable() {
		try {
			icBufferedWriter.close();
			icBufferedWriter = new BufferedWriter(new FileWriter("POOLTAB.txt"));
			Iterator<Integer> poolIterator = poolTable.iterator();
			while(poolIterator.hasNext()) {
			    icBufferedWriter.write(String.valueOf(poolIterator.next()));
			    icBufferedWriter.write("\n");
			}
			icBufferedWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
