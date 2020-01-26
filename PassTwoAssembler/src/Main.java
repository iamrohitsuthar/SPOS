import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class Main {
	private static final String FILE_NAME = "IC.txt";
	private static BufferedReader bufferedReader = null;
	
	public static void main(String[] args) {
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_NAME)));
			PassTwo passTwo = new PassTwo();
			passTwo.parse(bufferedReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
