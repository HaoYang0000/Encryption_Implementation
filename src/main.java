import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;


public class main {
	static int counter = 0;

	/**
	 * main function for testing
	 * @author Hao Yang
	 */
	public static void main(String[] str) {

		System.out.println("---------------------------------");
		System.out.println("Generating Clients...");
		System.out.println();

		//Read the file names for the test
		Scanner s = null;
		ArrayList<String> fileName = new ArrayList<String>();
		try {
			//For test case 1
			s = new Scanner(new File("data/fileName1.txt"));
			//For test case 2
			//s = new Scanner(new File("data/fileName2.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(s.hasNext()){
			fileName.add(s.next());
		}

		//List to store all the client
		ArrayList<Client> list = new ArrayList<Client>();
		
		for(int i=0;i<fileName.size();i++){
			//For test case 1
			list = Parser.readFile("data/testcase/Data/000/Trajectory/"+fileName.get(i));
			
			//For test case 2
			//list.add(Parser.readFile("data/testcase/Data/002/Trajectory/"+fileName.get(i)).get(0));
		}

		//Initialize the counter, which used to keep the time of computation
		counter=0;
		long totalTimeStart = System.nanoTime();

		//Test case 1: which compute distance to a fixed position
		computeDistanceTest1(list);

		//Test case 2: which compute distance of all user
//		for(int i=0;i<list.size();i++){
//			//The first client is a
//			Client a = list.get(i);
//			a.setId(i);
//			//Second client is b
//			for(int j=0;j<list.size();j++){
//				if(i==j)
//					continue;
//				Client b = list.get(j);
//				b.setId(j);
//				long startTime = System.nanoTime();
//				computeDistanceTest2(a,b);
//				long endTime = System.nanoTime();
//
//				System.out.println("Computation time is = "+((endTime-startTime)/1000000.0)+"ms");
//				System.out.println();
//			}
//		}
		long totalTimeEnd = System.nanoTime();
		System.out.println("---------------------------------");
		System.out.println("All tests are finished, Total computation time is "+((totalTimeEnd-totalTimeStart)/1000000.0)+"ms");
		System.out.println("Total user number is "+fileName.size());
		System.out.println("The number of computation is "+counter);
		System.out.println("Average computation time is = "+((totalTimeEnd-totalTimeStart)/1000000.0/counter)+"ms");
	}

	/**
	 * Function to compute all the distances between Clients and counting time it takes at the same time
	 * @param list of Client
	 * @param publicKey
	 * @param N
	 */
	public static void computeDistanceTest1(ArrayList<Client> list){

		long start, end;

		//Fixed base
		Client base = new Client(0000,44.008851,100.321485);

		BigInteger r1 = Utills.generateRandomBigInteger(7);

		BigInteger xMatrix[] = base.getXMatrix();
		BigInteger yMatrix[] = base.getYMatrix();

		//ALice: Encrypt and send to Bob
		for(int i=0;i<base.getXMatrix().length;i++){
			xMatrix[i] = base.getPublickKey().Encryption(xMatrix[i],r1);
		}

		for(int i=0;i<base.getYMatrix().length;i++){
			yMatrix[i] = base.getPublickKey().Encryption(yMatrix[i],r1);
		}

		//Bob
		for(int i=0;i<list.size();i++){
			counter++;
			start = System.nanoTime();

			BigInteger x1Matrix[] = list.get(i).getXMatrix();
			BigInteger y1Matrix[] = list.get(i).getYMatrix();

			BigInteger resultX[][] = new BigInteger[6][6];
			BigInteger result_xx = new BigInteger("0");

			BigInteger resultY[][] = new BigInteger[7][7];
			BigInteger result_yy = new BigInteger("0");
			
			//Get w' and send to Alice

			//Computation for easting
			int power=10;
			for(int j=0;j<6;j++){
				power = power-j;
				for(int k=0;k<6;k++){
					//BigInteger temp =  new BigInteger("1");
					//BigInteger sB = new BigInteger("0");
					//BigInteger r = Utills.generateRandomBigInteger(3);
					//temp = resultX[j][k].multiply(base.getPublickKey().Encryption(sB.negate(),r));
					//temp = base.getPublickKey().Decryption(temp);
					//temp = temp.multiply(new BigInteger(""+(long) Math.pow(10,power)));
					
					//Compute W
					resultX[j][k] = Utills.BigIntegerPow(xMatrix[j],x1Matrix[k]);
					resultX[j][k] = base.getPublickKey().Decryption(resultX[j][k]);
					resultX[j][k] = resultX[j][k].multiply(new BigInteger(""+(long) Math.pow(10,power)));
					power--;
					result_xx = result_xx.add(resultX[j][k]);
				}
				power=10;
			}

			//Computation for northing
			power = 12;
			for(int j=0;j<7;j++){
				power = power-j;
				for(int k=0;k<7;k++){
					//BigInteger temp =  new BigInteger("1");
//					BigInteger sB = new BigInteger("0");
//					BigInteger r = Utills.generateRandomBigInteger(3);
//					temp = resultY[j][k].multiply(base.getPublickKey().Encryption(sB.negate(),r));
//					//temp = base.getPublickKey().Decryption(temp);
					//temp = temp.multiply(new BigInteger(""+(long) Math.pow(10,power)));
					
					//Compute W 
					resultY[j][k] = Utills.BigIntegerPow(yMatrix[j],y1Matrix[k]);
					resultY[j][k] = base.getPublickKey().Decryption(resultY[j][k]);
					resultY[j][k] = resultY[j][k].multiply(new BigInteger(""+(long) Math.pow(10,power)));
					power--;
					result_yy = result_yy.add(resultY[j][k]);
				}
				power=12;
			}

			//Compute distance between two client
			Utills.computeDistance(list.get(i),base,result_xx,result_yy);

			//Method to check the real result
			System.out.println("The result should be : "+ Utills.checkResult(list.get(i),base));

			end = System.nanoTime();
			double ms = (end-start) / 1000000.0;


		}
	}

	public static void computeDistanceTest2(Client a, Client b){

		long start, end;


		BigInteger r1 = Utills.generateRandomBigInteger(7);

		BigInteger xMatrix[] = a.getXMatrix();
		BigInteger yMatrix[] = a.getYMatrix();
		//ALice: Encrypt and send to Bob
		for(int i=0;i<a.getXMatrix().length;i++){
			xMatrix[i] = a.getPublickKey().Encryption(xMatrix[i],r1);
		}

		for(int i=0;i<a.getYMatrix().length;i++){
			yMatrix[i] = a.getPublickKey().Encryption(yMatrix[i],r1);
		}

		counter++;
		start = System.nanoTime();

		BigInteger x1Matrix[] = b.getXMatrix();
		BigInteger y1Matrix[] = b.getYMatrix();

		BigInteger resultX[][] = new BigInteger[6][6];
		BigInteger result_xx = new BigInteger("0");

		BigInteger resultY[][] = new BigInteger[7][7];
		BigInteger result_yy = new BigInteger("0");

		//Get w' and send to Alice

		//Computation for easting
		int power=10;
		for(int j=0;j<6;j++){
			power = power-j;
			for(int k=0;k<6;k++){
				BigInteger temp =  new BigInteger("1");
				resultX[j][k] = Utills.BigIntegerPow(xMatrix[j],x1Matrix[k]);
				BigInteger sB = new BigInteger("0");
				BigInteger r = Utills.generateRandomBigInteger(3);
				temp = resultX[j][k].multiply(a.getPublickKey().Encryption(sB.negate(),r));
				temp = a.getPublickKey().Decryption(temp);
				temp = temp.multiply(new BigInteger(""+(long) Math.pow(10,power)));
				power--;
				result_xx = result_xx.add(temp);
			}
			power=10;
		}

		//Computing for northing
		power = 12;
		for(int j=0;j<7;j++){
			power = power-j;
			for(int k=0;k<7;k++){
				BigInteger temp =  new BigInteger("1");
				resultY[j][k] = Utills.BigIntegerPow(yMatrix[j],y1Matrix[k]);
				BigInteger sB = new BigInteger("0");
				BigInteger r = Utills.generateRandomBigInteger(3);
				temp = resultY[j][k].multiply(a.getPublickKey().Encryption(sB.negate(),r));
				temp = a.getPublickKey().Decryption(temp);
				temp = temp.multiply(new BigInteger(""+(long) Math.pow(10,power)));
				power--;
				result_yy = result_yy.add(temp);
			}
			power=12;
		}

		//Compute distance between two client
		Utills.computeDistance(a,b,result_xx,result_yy);

		//Testing
		System.out.println("The result should be : "+ Utills.checkResult(a,b));

		end = System.nanoTime();
		double ms = (end-start) / 1000000.0;


	}


//  Old code
	
//	/**
//	 * Function to compute all the distances between Clients and counting time it takes at the same time
//	 * @param list of Client
//	 * @param publicKey
//	 * @param N
//	 */
//	public static void computeDistance(ArrayList<Client> list, int n){
//
//		long start, end;
//
//
//		for(int i=0;i<n;i++){
//			for(int j=i+1;j<n;j++){
//				//Not necessary to count their own distance, so skip
//				if(i==j)
//					continue;
//
//				start = System.nanoTime();
//				BigInteger AX = new BigInteger(""+list.get(i).getXA());
//				BigInteger BX = new BigInteger(""+list.get(i).getXB());
//
//				BigInteger r1 = Utills.generateRandomBigInteger(7);
//
//				AX = list.get(i).getPublickKey().Encryption(AX,r1);
//				BX = list.get(i).getPublickKey().Encryption(BX,r1);
//
//
//
//				BigInteger A1X = new BigInteger(""+list.get(j).getXA());
//				BigInteger B1X = new BigInteger(""+list.get(j).getXB());
//
//
//
//				BigInteger result1X = Utills.BigIntegerPow(AX,A1X);
//				BigInteger result2X = Utills.BigIntegerPow(AX,B1X);
//				BigInteger result3X = Utills.BigIntegerPow(BX,A1X);
//				BigInteger result4X = Utills.BigIntegerPow(BX,B1X);
//
//				result1X = list.get(i).getPublickKey().Decryption(result1X).multiply(new BigInteger(""+1000000));
//				result2X = list.get(i).getPublickKey().Decryption(result2X).multiply(new BigInteger(""+1000));
//				result3X = list.get(i).getPublickKey().Decryption(result3X).multiply(new BigInteger(""+1000));
//				result4X = list.get(i).getPublickKey().Decryption(result4X);
//
//				BigInteger result_xx = result1X.add(result2X).add(result3X).add(result4X);
//
//				BigInteger AY = new BigInteger(""+list.get(i).getYA());
//				BigInteger BY = new BigInteger(""+list.get(i).getYB());
//
//
//				AY = list.get(i).getPublickKey().Encryption(AY,r1);
//				BY = list.get(i).getPublickKey().Encryption(BY,r1);
//
//
//
//				BigInteger A1Y = new BigInteger(""+list.get(j).getYA());
//				BigInteger B1Y = new BigInteger(""+list.get(j).getYB());
//
//
//
//				BigInteger result1Y = Utills.BigIntegerPow(AY,A1Y);
//				BigInteger result2Y = Utills.BigIntegerPow(AY,B1Y);
//				BigInteger result3Y = Utills.BigIntegerPow(BY,A1Y);
//				BigInteger result4Y = Utills.BigIntegerPow(BY,B1Y);
//
//				result1Y = list.get(i).getPublickKey().Decryption(result1Y).multiply(new BigInteger(""+1000000));
//				result2Y = list.get(i).getPublickKey().Decryption(result2Y).multiply(new BigInteger(""+1000));
//				result3Y = list.get(i).getPublickKey().Decryption(result3Y).multiply(new BigInteger(""+1000));
//				result4Y = list.get(i).getPublickKey().Decryption(result4Y);
//
//				BigInteger result_yy = result1Y.add(result2Y).add(result3Y).add(result4Y);
//
//				//Compute distance between two client
//				Utills.computeDistance(list.get(i),list.get(j),result_xx,result_yy);
//
//				counter++;
//
//				//Testing
//				//System.out.println("The result should be : "+ Utills.checkResult(list.get(i),list.get(j)));
//
//				end = System.nanoTime();
//				double ms = (end-start) / 1000000.0;
//
//
//			}
//		}
//	}
//
//	/**
//	 * Function to compute all the distances between Clients and counting time it takes at the same time
//	 * @param list of Client
//	 * @param publicKey
//	 * @param N
//	 */
//	public static void computeDistanceCase1(ArrayList<Client> list){
//
//		long start, end;
//
//		Client base = new Client(0000,44.008851,100.321485);
//
//		for(int i=0;i<list.size();i++){
//			start = System.nanoTime();
//			BigInteger AX = new BigInteger(""+list.get(i).getXA());
//			BigInteger BX = new BigInteger(""+list.get(i).getXB());
//
//			BigInteger r1 = Utills.generateRandomBigInteger(7);
//
//			AX = list.get(i).getPublickKey().Encryption(AX,r1);
//			BX = list.get(i).getPublickKey().Encryption(BX,r1);
//
//
//
//			BigInteger A1X = new BigInteger(""+base.getXA());
//			BigInteger B1X = new BigInteger(""+base.getXB());
//
//
//
//			BigInteger result1X = Utills.BigIntegerPow(AX,A1X);
//			BigInteger result2X = Utills.BigIntegerPow(AX,B1X);
//			BigInteger result3X = Utills.BigIntegerPow(BX,A1X);
//			BigInteger result4X = Utills.BigIntegerPow(BX,B1X);
//
//			result1X = list.get(i).getPublickKey().Decryption(result1X).multiply(new BigInteger(""+1000000));
//			result2X = list.get(i).getPublickKey().Decryption(result2X).multiply(new BigInteger(""+1000));
//			result3X = list.get(i).getPublickKey().Decryption(result3X).multiply(new BigInteger(""+1000));
//			result4X = list.get(i).getPublickKey().Decryption(result4X);
//
//			BigInteger result_xx = result1X.add(result2X).add(result3X).add(result4X);
//
//			BigInteger AY = new BigInteger(""+list.get(i).getYA());
//			BigInteger BY = new BigInteger(""+list.get(i).getYB());
//
//
//			AY = list.get(i).getPublickKey().Encryption(AY,r1);
//			BY = list.get(i).getPublickKey().Encryption(BY,r1);
//
//
//
//			BigInteger A1Y = new BigInteger(""+base.getYA());
//			BigInteger B1Y = new BigInteger(""+base.getYB());
//
//
//
//			BigInteger result1Y = Utills.BigIntegerPow(AY,A1Y);
//			BigInteger result2Y = Utills.BigIntegerPow(AY,B1Y);
//			BigInteger result3Y = Utills.BigIntegerPow(BY,A1Y);
//			BigInteger result4Y = Utills.BigIntegerPow(BY,B1Y);
//
//			result1Y = list.get(i).getPublickKey().Decryption(result1Y).multiply(new BigInteger(""+1000000));
//			result2Y = list.get(i).getPublickKey().Decryption(result2Y).multiply(new BigInteger(""+1000));
//			result3Y = list.get(i).getPublickKey().Decryption(result3Y).multiply(new BigInteger(""+1000));
//			result4Y = list.get(i).getPublickKey().Decryption(result4Y);
//
//			BigInteger result_yy = result1Y.add(result2Y).add(result3Y).add(result4Y);
//
//			//Compute distance between two client
//			Utills.computeDistance(list.get(i),base,result_xx,result_yy);
//
//			//Testing
//			System.out.println("The result should be : "+ Utills.checkResult(list.get(i),base));
//
//			end = System.nanoTime();
//			double ms = (end-start) / 1000000.0;
//
//
//		}
//	}




}



