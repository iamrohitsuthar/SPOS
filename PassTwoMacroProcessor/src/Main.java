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
import java.util.Map;


public class Main {
	private static final String FILE_NAME = "input_new.asm";
	private static BufferedReader bufferedReader = null;
	private static BufferedWriter bufferedWriter = null;
	
	public static void main(String[] args) {
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_NAME)));
			PassTwoMacroProcessor passTwoMacroProcessor = null;
			try {
				passTwoMacroProcessor = new PassTwoMacroProcessor();
				passTwoMacroProcessor.parse(bufferedReader);
				//Main.writePNTAB(passOneMacroProcessor.getPNTAB());
//				Main.writeKPDTAB(passOneMacroProcessor.getKPDTAB());
//				Main.writeMNT(passOneMacroProcessor.getMNT());
//				Main.writeMDT(passOneMacroProcessor.getMDT());
			} catch (IOException e) {
				e.printStackTrace();
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
//	private static void writePNTAB(ArrayList<String> arrayList) throws IOException {
//		bufferedWriter = new BufferedWriter(new FileWriter("PNTAB.txt"));
//		int i = 1;
//		Iterator<String> iterator = arrayList.iterator();
//		while(iterator.hasNext()) {
//			bufferedWriter.write(i++ + " " + iterator.next() + "\n");
//		}
//		bufferedWriter.close();
//	}
	
//	private static void writeKPDTAB(HashMap<String,String> hashmap) throws IOException {
//		bufferedWriter = new BufferedWriter(new FileWriter("KPDTAB.txt"));
//		int i = 1;
//		for (Map.Entry<String, String> entry : hashmap.entrySet()) {
//		    bufferedWriter.write(i++ + " " + entry.getKey() + " " + entry.getValue() + "\n");
//		}
//		bufferedWriter.close();
//	}
//	
//	private static void writeMDT(StringBuilder stringBuilder) throws IOException {
//		bufferedWriter = new BufferedWriter(new FileWriter("MDT.txt"));
//		bufferedWriter.write(stringBuilder.toString());
//		bufferedWriter.close();
//	}
//	
//	private static void writeMNT(HashMap<String,MacroDefModel> hashmap) throws IOException {
//		bufferedWriter = new BufferedWriter(new FileWriter("MNT.txt"));
//		int i = 1;
//		for (Map.Entry<String, MacroDefModel> entry : hashmap.entrySet()) {
//			MacroDefModel macroDefModel = entry.getValue();
//		    bufferedWriter.write(i++ + " " + entry.getKey() + " " + macroDefModel.getPositionalParams() + " " +
//			macroDefModel.getKeywordParams() + " " + macroDefModel.getMacroDefPointer() + " " + macroDefModel.getKeywordsDefPointer() +"\n");
//		}
//		bufferedWriter.close();
//	}
}
