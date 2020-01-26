package rohitsuthar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import rohitsuthar.main.PassOneAsm;

public class Main {
	private static final String FILE_NAME = "demo.asm";
	private static BufferedReader bufferedReader = null;
	
	public static void main(String[] args) {
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_NAME)));
			PassOneAsm passOneAsm = new PassOneAsm();
			passOneAsm.parse(bufferedReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
