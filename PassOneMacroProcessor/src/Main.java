import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;


public class Main {
	private static final String FILE_NAME = "input.asm";
	private static BufferedReader bufferedReader = null;
	
	public static void main(String[] args) {
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_NAME)));
			PassOneMacroProcessor passOneMacroProcessor = new PassOneMacroProcessor();
			passOneMacroProcessor.parse(bufferedReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
