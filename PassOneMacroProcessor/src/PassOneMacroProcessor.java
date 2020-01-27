import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class PassOneMacroProcessor {
	private String line = null;
	private BufferedWriter bufferedWriter = null;
	
	public PassOneMacroProcessor() {
		
	}
	
	public void parse(BufferedReader bufferedReader) {
		try {
			while((line = bufferedReader.readLine()) != null) {
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
