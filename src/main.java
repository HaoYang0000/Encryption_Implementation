import java.math.BigInteger;
import java.util.ArrayList;


public class main {


	/**
	 * main function for testing
	 * @author Hao Yang
	 */
	public static void main(String[] str) {

		//Number of bit 
		//After some testing, 2 is working fine, 3 would be takes longer, 4 would takes almost 1 miutes
		final int N = 4;

		//List to store all the client
		ArrayList<Client> list = new ArrayList();

		//Cloud public key
		Paillier cloud = new Paillier();
		
		System.out.println("---------------------------------");
		System.out.println("Generating Clients...");
		System.out.println();

		//Generate Alice's private input: 
		BigInteger x1 = Utills.generateRandomBigInteger(N);
		BigInteger y1 = Utills.generateRandomBigInteger(N);
		System.out.println("Alice's private input is : x: "+x1+", y: "+y1);

		//Alice
		Client Alice = new Client(x1,y1,"Alice");
		//Encrypt Alice's private input, using a Paillier
		Alice.setCi(cloud,N);

		list.add(Alice);

		//Generate Bob's private input: 
		BigInteger x2 = Utills.generateRandomBigInteger(N);
		BigInteger y2 = Utills.generateRandomBigInteger(N);
		System.out.println("Bob's private input is : x: "+x2+", y: "+y2);

		//Bob
		Client Bob = new Client(x2,y2,"Bob");
		//Encrypt Bob's private input, using a Paillier
		Bob.setCi(cloud,N);

		list.add(Bob);

		//Generate Charles's private input: 
		BigInteger x3 = Utills.generateRandomBigInteger(N);
		BigInteger y3 = Utills.generateRandomBigInteger(N);
		System.out.println("Charles's private input is : x: "+x3+", y: "+y3);

		//Charles
		Client Charles = new Client(x3,y3,"Charles");
		//Encrypt Charles's private input, using a Paillier
		Charles.setCi(cloud,N);

		list.add(Charles);

		//Generate David's private input: 
		BigInteger x4 = Utills.generateRandomBigInteger(N);
		BigInteger y4 = Utills.generateRandomBigInteger(N);
		System.out.println("David's private input is : x: "+x4+", y: "+y4);

		//David
		Client David = new Client(x4,y4,"David");
		//Encrypt Alice's private input, using a Paillier
		David.setCi(cloud,N);

		list.add(David);
		
		System.out.println();
		System.out.println("---------------------------------");
		System.out.println("Computing Distances...");
		System.out.println();
		//Computing distances between clients
		computeDistance(list,cloud,N);


	}

	/**
	 * Function to compute all the distances between Clients and counting time it takes at the same time
	 * @param list of Client
	 * @param publicKey
	 * @param N
	 */
	public static void computeDistance(ArrayList<Client> list, Paillier publicKey,int N){
		
		long start, end;
		
		
		for(int i=0;i<list.size();i++){
			for(int j=0;j<list.size();j++){
				//Not necessary to count their own distance, so skip
				if(i==j)
					continue;
				
				start = System.nanoTime();
				//Setting x1x2, take public key, two entrypted value and number of bits n as input
				BigInteger result_xx = Utills.setW(publicKey,list.get(i).getCiX(),list.get(j).getX(),N);
				//Setting y1y2, two entrypted value and number of bits n as input
				BigInteger result_yy = Utills.setW(publicKey,list.get(i).getCiY(),list.get(j).getY(),N);
				//Compute distance between two client
				Utills.computeDistance(list.get(i),list.get(j),publicKey.Decryption(result_xx),publicKey.Decryption(result_yy));
				
				end = System.nanoTime();
				
				double ms = (end-start) / 1000000.0;
				System.out.println("execution time is = "+ms+"ms");
				System.out.println();
				
			}
		}
	}


}
