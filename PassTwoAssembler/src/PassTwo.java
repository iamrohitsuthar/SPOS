import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PassTwo {
	private int symbolIndexTrack = 0;
	private int literalIndexTrack = 0;
	private LinkedHashMap<String, Symbol> symbols;
	private LinkedHashMap<String, Literal> literals;
	private BufferedWriter icBufferedWriter = null;
	private static BufferedReader bufferedReader = null;
	private String line;
	
	public PassTwo() {
		symbols = new LinkedHashMap<>();
		literals = new LinkedHashMap<>();
		try {
			icBufferedWriter = new BufferedWriter(new FileWriter("MC.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeData(String data) throws IOException {
		icBufferedWriter.write(data);
		icBufferedWriter.flush();
	}
	
	private void getSymbolsData() throws IOException {
		bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("SYMTAB.txt")));
		while((line = bufferedReader.readLine()) != null) {
			String []arr = line.split(" ");
			symbols.put(zerosPad(String.valueOf(symbolIndexTrack++), 2),new Symbol(arr[1], Integer.parseInt(arr[2])));
		}
		bufferedReader.close();
	}
	
	private void getLiteralsData() throws IOException {
		bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("LITTAB.txt")));
		while((line = bufferedReader.readLine()) != null) {
			String []arr = line.split(" ");
			literals.put(zerosPad(String.valueOf(literalIndexTrack++), 2),new Literal(arr[1], Integer.parseInt(arr[2])));
		}
		bufferedReader.close();
	}
	
	public void parse(BufferedReader bufferedReader) {
		try {
			getSymbolsData();
			getLiteralsData();
//			for (Map.Entry<String, Symbol> entry : symbols.entrySet()) {
//			    String key = entry.getKey();
//			    Symbol value = entry.getValue();
//			    Msg.println(key);
//			    Msg.println(value.getName());
//			    Msg.println(value.getAddress());
//			}
			while((line = bufferedReader.readLine()) != null) {
				boolean isCheck = false;
				if(!line.equals("")) {
					String []arr = line.split("\t");
					if(!arr[0].isEmpty()) {
						writeData(arr[0]+") ");
					}
					String temp = arr[1];
					ArrayList<String> matchList = new ArrayList<String>();
					Pattern regex = Pattern.compile("\\((.*?)\\)");
					Matcher regexMatcher = regex.matcher(temp);
					while (regexMatcher.find()) {
					   matchList.add(regexMatcher.group(1));
					}
					
					String data = matchList.get(0);
					if(data.contains("AD,1")) {
						//start symbol - do nothing
					}
					else if(data.contains("DL")){
						if(!matchList.get(0).equals("DL,02")) {
							writeData("+00 0");
							String second = matchList.get(1);
							second = second.substring(second.indexOf(',')+1, second.length());
							writeData(" "+zerosPad(second,3));
						}
					}
					else if(data.contains("IS")) {
						data = data.substring(data.indexOf(',')+1, data.length());
						writeData("+" + data + " ");
						if(matchList.size() > 1) {
							
							String secondData = matchList.get(1);
							if(!secondData.contains(",")) {
								writeData(secondData + " ");
							}
							else {
								writeData("0 ");
								String raw = secondData;
								String type = raw.substring(0,raw.indexOf(','));
								String code = raw.substring(raw.indexOf(',')+1,raw.length());
								if(type.equals("S")) {
									//symbol
									writeData(" "+symbols.get(code).getAddress());
								}
								else {
									//literal
									writeData(" "+literals.get(code).getAddress());
								}
								isCheck = true;
							}
							
							if(!isCheck) {
								if(matchList.size() > 2) {
									String raw = matchList.get(2);
									String type = raw.substring(0,raw.indexOf(','));
									String code = raw.substring(raw.indexOf(',')+1,raw.length());
									if(type.equals("S")) {
										//symbol
										writeData(" "+symbols.get(code).getAddress());
									}
									else {
										//literal
										writeData(" "+literals.get(code).getAddress());
									}
								}
								else {
									writeData("000");
								}
							}
						}
						else {
							writeData("0 000");
						}
					}
				}
				writeData("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String zerosPad(String data,int length) {
	    while (data.length() < length) data = "0" + data;
	    return data;
	}
}
