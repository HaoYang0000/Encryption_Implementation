import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


public class Parser {
	
	public static ArrayList<Client> readFile(String fileName ){
		
		//Creating list to store all the clients
		ArrayList<Client> list = new ArrayList<Client>();
		
		//Data path of all the clients
		//String fileName = "data/2015_Gaz_114CDs_national.txt";
		//String fileName = "data/data.txt";
		
		Scanner s = null;
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//Skip the first Line
		String line = s.nextLine();
		line = s.nextLine();
		line = s.nextLine();
		line = s.nextLine();
		line = s.nextLine();
		line = s.nextLine();
		
//		System.out.println("Read file and generating client...");
//		System.out.println("-----------------------------------");
//		System.out.println();

//		while(s.hasNext()) {
		int id=0;
		while(s.hasNext()){
			//int id =s.nextInt();
			//String name = s.next();
			
			//double temp = s.nextDouble();
			//temp = s.nextDouble();
			//temp = s.nextDouble();
			//temp = s.nextDouble();
			line = s.nextLine();
			String temp[] = line.split(",");
			double tempX = Double.parseDouble(temp[0]);
			double tempY = Double.parseDouble(temp[1]);
//			double tempX = s.nextDouble();
//			double tempY = s.nextDouble();
			//Client tempClient = new Client(id,name,x,y);
			Client tempClient = new Client(id,tempX,tempY);
			id++;
			
//			System.out.println("A new client is created...");
//			System.out.println("id: "+tempClient.getId());
//			System.out.println("state: "+tempClient.getName());
//			System.out.println("Latitude: "+tempX);
//			System.out.println("Longitude: "+tempY);
//			System.out.println("Easting: "+tempClient.getEasting());
//			System.out.println("Northing: "+tempClient.getNorthing());
//			System.out.println("Zone: " +tempClient.getLetterZone());
			
			list.add(tempClient);
//			System.out.println("-----------------------------------");
//			System.out.println();
		}
		s.close();
		
//		System.out.println("Finished read file");
//		System.out.println("-----------------------------------");
//		System.out.println();
//		
		return list;
	}
	
	
}


