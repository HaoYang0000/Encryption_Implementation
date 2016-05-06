import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;


public class Parser {
	
	public static String readFile(){
		
		//UTM2Deg pos =new UTM2Deg("35 R 312915.84 4451481.33");
		
		String fileName = "data/";
		Scanner s = null;
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String line = "";

		while(s.hasNext()) {
			line = s.nextLine();
			
		}
		s.close();
		
		return null;
	}
	
}


