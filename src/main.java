import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;




public class main {
	/**
	 * Class to stored expirement results, contains user number, running time and saved time of each instance.
	 * @author Howdy
	 *
	 */

	static class Result{
		public double runningTime=0;
		public int userNumber=0;
		public double savedTime=0;
		public int occurTime = 0;
		public double averageError = 0;
		Result(long endtime,long startTime, int userNumber){
			this.runningTime = (endtime-startTime)/1000000.0;
			this.userNumber = userNumber;
		}

	}

	static int counter = 0;
	static Result[] result ;

	/**
	 * main function for testing
	 * @author Hao Yang
	 */
	public static void main(String[] str) {

		//task2();

		//task3();

		task4();

		/**
		 * Use this method help to finish task 4
		 */
		//For test case 2
		//list.add(Parser.readFile("data/testcase/Data/001/Trajectory/"+fileName.get(i)).get(0));
		//computeDistance(list,i);
		//list = Parser.readFile("");
	}


	/**
	 * Task 2: compute distance between a fixed base to a client
	 */
	public static void task2(){
		//Reading files and generate Clients
		Scanner s = null;
		ArrayList<String> fileName = new ArrayList<String>();
		try {
			s = new Scanner(new File("data/fileName1.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(s.hasNext()){
			fileName.add(s.next());
		}

		result = new Result[fileName.size()];
		//List to store all the client
		ArrayList<Client> list = new ArrayList<Client>();
		//fixed base
		Client base = new Client(9999,44.008851,100.321485);
		for(int i=0;i<fileName.size();i++){
			list = Parser.readFile("data/testcase/Data/000/Trajectory/"+fileName.get(i));

			long totalTimeStart = System.nanoTime();
			counter=0;
			for(int j=0; j<list.size();j++){

				Client b = list.get(j);
				b.setId(j);
				long startTime = System.nanoTime();
				computeDistance(base,b);
				long endTime = System.nanoTime();

				//System.out.println("Computation time is = "+((endTime-startTime)/1000000.0)+"ms");
				//System.out.println();

			}

			long totalTimeEnd = System.nanoTime();
			System.out.println("---------------------------------");
			System.out.println("All tests are finished, Total computation time is "+((totalTimeEnd-totalTimeStart)/1000000.0)+"ms");
			System.out.println("The number of user is "+list.size());
			System.out.println("Average computation time is = "+((totalTimeEnd-totalTimeStart)/1000000.0/counter)+"ms");

			Result temp = new Result(totalTimeEnd,totalTimeStart,counter);
			result[i] = temp;

		}

		/**
		 * Sorting the result
		 */
		for(int i=0;i<result.length;i++){
			for(int j=i+1;j<result.length;j++){
				if(result[i].userNumber >= result[j].userNumber){
					Result temp = result[j];
					result[j] = result[i];
					result[i] = temp;
				}
			}
		}

		/**
		 * Print out results
		 */
		System.out.println("------------------");
		for(int i=0;i<result.length;i++){
			System.out.println(result[i].userNumber );
		}
		System.out.println("------------------");
		for(int i=0;i<result.length;i++){
			System.out.println(result[i].runningTime );
		}
	}

	/**
	 * Task 3: compute distance between mobile users
	 */
	public static void task3(){
		//Reading files and generate Clients
		Scanner s = null;
		ArrayList<String> fileName = new ArrayList<String>();
		try {
			s = new Scanner(new File("data/fileName2.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(s.hasNext()){
			fileName.add(s.next());
		}

		//List to store all the client
		ArrayList<Client> list = new ArrayList<Client>();

		for(int i=0;i<fileName.size();i++){
			list.add( Parser.readFile("data/testcase/Data/001/Trajectory/"+fileName.get(i)).get(0));
		}

		result = new Result[list.size()];

		for(int i=0;i<list.size();i++){
			long totalTimeStart = System.nanoTime();
			counter=0;
			for(int j=0;j<i;j++){
				Client a = list.get(i);
				a.setId(i);
				for(int k=j+1;k<i;k++){
					Client b = list.get(k);
					b.setId(k);
					long startTime = System.nanoTime();
					computeDistance(a,b);
					long endTime = System.nanoTime();
				}
				//System.out.println("Computation time is = "+((endTime-startTime)/1000000.0)+"ms");
				//System.out.println();
			}
			long totalTimeEnd = System.nanoTime();
			System.out.println("---------------------------------");
			System.out.println("All tests are finished, Total computation time is "+((totalTimeEnd-totalTimeStart)/1000000.0)+"ms");
			System.out.println("The number of pair is "+counter);
			System.out.println("Average computation time is = "+((totalTimeEnd-totalTimeStart)/1000000.0/counter)+"ms");

			Result temp = new Result(totalTimeEnd,totalTimeStart,counter);
			result[i] = temp;

		}

		/**
		 * Sorting the result
		 */
		for(int i=0;i<result.length;i++){
			for(int j=i+1;j<result.length;j++){
				if(result[i].userNumber >= result[j].userNumber){
					Result temp = result[j];
					result[j] = result[i];
					result[i] = temp;
				}
			}
		}

		/**
		 * Print out results
		 */
		System.out.println("------------------");
		for(int i=0;i<result.length;i++){
			System.out.println(result[i].userNumber );
		}
		System.out.println("------------------");
		for(int i=0;i<result.length;i++){
			System.out.println(result[i].runningTime );
		}
	}

	/**
	 * Task 4: Speed prediction 
	 */
	public static void task4(){
		//Reading files and generate Clients
		Scanner s = null;
		ArrayList<String> fileName = new ArrayList<String>();
		try {
			s = new Scanner(new File("data/fileName1.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(s.hasNext()){
			fileName.add(s.next());
		}

		result = new Result[fileName.size()];
		//List to store all the client
		ArrayList<Client> list = new ArrayList<Client>();

		//fixed base
		Client base = new Client(9999,44.008851,100.321485);
		for(int i=0;i<fileName.size();i++){
			list = Parser.readFile("data/testcase/Data/000/Trajectory/"+fileName.get(i));

			long totalTimeStart = System.nanoTime();
			counter=0;

			BigInteger holder = new BigInteger("0");

			ArrayList<Double> rangeList = new ArrayList<Double>();
			boolean predictMode = false;

			//Variables to help save the results
			double tempTime = 0;
			int occurTime = 0;
			double averageError = 0;
			for(int j=0; j<list.size();j++){
				long startTime = System.nanoTime();
				Client b = list.get(j);
				holder = computeDistance(base,b);
				rangeList.add(Utills.convertToDouble(holder));
				
				//!!Change j here to make different test
				if(j%5==0 && j>0){
					//!!Change STD here to make different test
					if(Utills.computeSTD(rangeList)>=10 && predictMode == true){
						System.out.println("Predict mode is off now. The STD is "+ Utills.computeSTD(rangeList));
						predictMode = false;
					}
					//!!Change STD here to make different test
					//Calculate speed and see if the predict mode is on
					else if(Utills.computeSTD(rangeList)<10){
						System.out.println("Predict mode is on now. The STD is "+ Utills.computeSTD(rangeList));
						predictMode = true;
					}
					if(predictMode == true){
						occurTime++;
						averageError = averageError + Math.abs(((rangeList.get(j-1)+Utills.predictDistance(rangeList)) - Utills.convertToDouble(holder)));
						System.out.println("The predict position is "+(rangeList.get(j-1)+Utills.predictDistance(rangeList))+ ", the real distance is "+holder);
					}
				}
				long endTime = System.nanoTime();
				tempTime = (endTime - startTime)/1000000.0;

			}

			long totalTimeEnd = System.nanoTime();
			System.out.println("---------------------------------");
			System.out.println("All tests are finished, Total computation time is "+((totalTimeEnd-totalTimeStart)/1000000.0)+"ms");
			System.out.println("The number of user is "+list.size());
			System.out.println("Average computation time is = "+((totalTimeEnd-totalTimeStart)/1000000.0/counter)+"ms");

			Result temp = new Result(totalTimeEnd,totalTimeStart,counter);
			temp.savedTime = tempTime;
			temp.occurTime = occurTime;
			temp.averageError = averageError/occurTime;
			result[i] = temp;

		}

		//		/**
		//		 * Sorting the result
		//		 */
		//		for(int i=0;i<result.length;i++){
		//			for(int j=i+1;j<result.length;j++){
		//				if(result[i].userNumber >= result[j].userNumber){
		//					Result temp = result[j];
		//					result[j] = result[i];
		//					result[i] = temp;
		//				}
		//			}
		//		}
		//
				/**
				 * Print out results
				 */
				System.out.println("------------------");
				for(int i=0;i<result.length;i++){
					if(result[i].occurTime != 0)
						System.out.println("In the "+(i+1)+" round, "+" saved "+result[i].savedTime
								+"ms, and the predict mode is on for "+ result[i].occurTime +" times, "
								+"the average error of prediction is "+ result[i].averageError+ "m.");
					}
//				System.out.println("------------------");
//				for(int i=0;i<result.length;i++){
//					System.out.println(result[i].runningTime );
//				}
	}

	/**
	 * Function to compute all the distances between Clients (Newest algorithm).
	 * @param list of Client
	 */
	public static BigInteger computeDistance(Client a, Client b){
		//Time stamp of computation 
		long start, end;
		start = System.nanoTime();

		//Random r for decryption
		BigInteger r1 = Utills.generateRandomBigInteger(7);

		//ALice: Encrypt and send to Bob
		BigInteger xMatrix[] = a.getXMatrix();
		BigInteger yMatrix[] = a.getYMatrix();
		for(int i=0;i<a.getXMatrix().length;i++){
			xMatrix[i] = a.getPublickKey().Encryption(xMatrix[i],r1);
		}
		for(int i=0;i<a.getYMatrix().length;i++){
			yMatrix[i] = a.getPublickKey().Encryption(yMatrix[i],r1);
		}	

		//Bob
		BigInteger x1Matrix[] = b.getXMatrix();
		BigInteger y1Matrix[] = b.getYMatrix();

		//Result set of W
		BigInteger resultX[][] = new BigInteger[7][7];
		BigInteger resultY[][] = new BigInteger[7][7];

		//Get w' and send to Alice
		for(int j=0;j<7;j++){
			for(int k=0;k<7;k++){
				resultX[j][k] = Utills.BigIntegerPow(xMatrix[j],x1Matrix[k]);
				resultY[j][k] = Utills.BigIntegerPow(yMatrix[j],y1Matrix[k]);
			}
		}

		//Alice compute the decryptd value and get the result
		BigInteger resultW = new BigInteger("0");

		//00*B1B2
		BigInteger temp = new BigInteger("0");
		temp = resultX[0][0].multiply(resultY[0][0]);
		temp = a.getPublickKey().Decryption(temp).multiply(new BigInteger(""+(long) Math.pow(10,12)));
		resultW = resultW.add(temp);

		//Numbers at the edge: 0*A2*B1B2'*A20*B2B2'.....
		int power = 11;
		for(int i=1;i<7;i++){
			temp = new BigInteger("0");
			temp = resultX[0][i].multiply(resultX[i][0]).multiply(resultY[0][i]).multiply(resultY[i][0]);
			temp = a.getPublickKey().Decryption(temp).multiply(new BigInteger(""+(long) Math.pow(10,power)));
			resultW = resultW.add(temp);
			power--;
		}

		//Numbers inside the matrix: B2B2'*A2A2'......
		power = 11;
		for(int i=1;i<7;i++){
			power = power - i;
			for(int j=1;j<7;j++){
				temp = new BigInteger("0");
				temp = resultX[i][j].multiply(resultY[i][j]);
				temp = a.getPublickKey().Decryption(temp).multiply(new BigInteger(""+(long) Math.pow(10,power)));
				resultW = resultW.add(temp);
				power--;
			}
			power = 11;
		}
		counter++;

		end = System.nanoTime();
		double ms = (end-start) / 1000000.0;
		//Compute distance between two client
		return Utills.computeDistance(a,b,resultW);

		//Method to check the real result
		//System.out.println("The result should be : "+ Utills.checkResult(a,b));


	}


}



//  Old code
//	
//	/**
//	 * Function to compute all the distances between Clients and counting time it takes at the same time
//	 * @param list of Client
//	 * @param publicKey
//	 * @param N
//	 */
//	public static void computeDistance(ArrayList<Client> list){
//
//		long start, end;
//
//
////		for(int i=0;i<list.size();i++){
//			//Fixed base
//			Client base = new Client(0000,44.008851,100.321485);
//			for(int j=0;j<list.size();j++){
//				
//				start = System.nanoTime();
//				BigInteger AX = new BigInteger(""+base.getXA());
//				BigInteger BX = new BigInteger(""+base.getXB());
//
//				BigInteger r1 = Utills.generateRandomBigInteger(7);
//
//				AX = base.getPublickKey().Encryption(AX,r1);
//				BX = base.getPublickKey().Encryption(BX,r1);
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
//				result1X = base.getPublickKey().Decryption(result1X).multiply(new BigInteger(""+1000000));
//				result2X = base.getPublickKey().Decryption(result2X).multiply(new BigInteger(""+1000));
//				result3X = base.getPublickKey().Decryption(result3X).multiply(new BigInteger(""+1000));
//				result4X = base.getPublickKey().Decryption(result4X);
//
//				BigInteger result_xx = result1X.add(result2X).add(result3X).add(result4X);
//
//				BigInteger AY = new BigInteger(""+base.getYA());
//				BigInteger BY = new BigInteger(""+base.getYB());
//
//
//				AY = base.getPublickKey().Encryption(BY,r1);
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
//				result1Y = base.getPublickKey().Decryption(result1Y).multiply(new BigInteger(""+1000000));
//				result2Y = base.getPublickKey().Decryption(result2Y).multiply(new BigInteger(""+1000));
//				result3Y = base.getPublickKey().Decryption(result3Y).multiply(new BigInteger(""+1000));
//				result4Y = base.getPublickKey().Decryption(result4Y);
//
//				BigInteger result_yy = result1Y.add(result2Y).add(result3Y).add(result4Y);
//
//				//Compute distance between two client
//				Utills.computeDistance(base,list.get(j),result_xx,result_yy);
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
//		//}
//	}
//
//	/**
//	 * Function to compute all the distances between Clients and counting time it takes at the same time
//	 * @param list of Client
//	 * @param publicKey
//	 * @param N
//	 */
//	public static void computeDistanceTest1(ArrayList<Client> list,int index){
//		long start, end;
//
//		//Fixed base
//		Client base = new Client(0000,44.008851,100.321485);
//
//		BigInteger r1 = Utills.generateRandomBigInteger(7);
//
//		BigInteger xMatrix[] = base.getXMatrix();
//		BigInteger yMatrix[] = base.getYMatrix();
//
//		//ALice: Encrypt and send to Bob
//		for(int i=0;i<base.getXMatrix().length;i++){
//			xMatrix[i] = base.getPublickKey().Encryption(xMatrix[i],r1);
//		}
//
//		for(int i=0;i<base.getYMatrix().length;i++){
//			yMatrix[i] = base.getPublickKey().Encryption(yMatrix[i],r1);
//		}
//
//		BigInteger holder = new BigInteger ("1");
//		BigInteger temp = new BigInteger ("1");
//		//Bob
//		Result aaa = new Result(0,0,0);
//		for(int i=0;i<list.size();i++){
//			
//			counter++;
//			start = System.nanoTime();
//
//			BigInteger x1Matrix[] = list.get(i).getXMatrix();
//			BigInteger y1Matrix[] = list.get(i).getYMatrix();
//
//			BigInteger resultX[][] = new BigInteger[6][6];
//			BigInteger result_xx = new BigInteger("0");
//
//			BigInteger resultY[][] = new BigInteger[7][7];
//			BigInteger result_yy = new BigInteger("0");
//
//			//Get w' and send to Alice
//
//			//Computation for easting
//			int power=10;
//			for(int j=0;j<6;j++){
//				power = power-j;
//				for(int k=0;k<6;k++){
//					resultX[j][k] = Utills.BigIntegerPow(xMatrix[j],x1Matrix[k]);
//					resultX[j][k] = base.getPublickKey().Decryption(resultX[j][k]);
//					resultX[j][k] = resultX[j][k].multiply(new BigInteger(""+(long) Math.pow(10,power)));
//					power--;
//					result_xx = result_xx.add(resultX[j][k]);
//				}
//				power=10;
//			}
//
//			//Computation for northing
//			power = 12;
//			for(int j=0;j<7;j++){
//				power = power-j;
//				for(int k=0;k<7;k++){
//					resultY[j][k] = Utills.BigIntegerPow(yMatrix[j],y1Matrix[k]);
//					resultY[j][k] = base.getPublickKey().Decryption(resultY[j][k]);
//					resultY[j][k] = resultY[j][k].multiply(new BigInteger(""+(long) Math.pow(10,power)));
//					power--;
//					result_yy = result_yy.add(resultY[j][k]);
//				}
//				power=12;
//			}
//			
//			//Compute distance between two client
//			list.get(i).setId(i+1);
//			if(i==0){
//				holder = Utills.computeDistance(list.get(i),base,result_xx,result_yy);
//				temp = holder;
//			}		
//			else{
//				holder = temp;
//				temp = Utills.computeDistance(list.get(i),base,result_xx,result_yy);
//				if(temp.subtract(holder).compareTo(new BigInteger("10"))==-1 && temp.subtract(holder).compareTo(new BigInteger("0"))==1){
////					System.out.println("yes!!!");
////					System.out.println(temp);
////					System.out.println(holder);
//					aaa.userNumber++;
//					
//					end = System.nanoTime();
//					double ms = (end-start) / 1000000.0;
//					//System.out.println("saved time is "+ms+"ms");
//					aaa.savedTime = (aaa.savedTime +  ms);
//				}
//				else if(temp.subtract(holder).compareTo(new BigInteger("-10"))==1 && temp.subtract(holder).compareTo(new BigInteger("0"))==-1){
//					//System.out.println("yes!!!");
//					//System.out.println(temp);
//					//System.out.println(holder);
//					aaa.userNumber++;
//					end = System.nanoTime();
//					double ms = (end-start) / 1000000.0;
//					//System.out.println("saved time is "+ms+"ms");
//					aaa.savedTime = (aaa.savedTime +  ms);
//				}
//			}
//			
//			
//
//			//Method to check the real result
//			//System.out.println("The result should be : "+ Utills.checkResult(list.get(i),base));
//
//			end = System.nanoTime();
//			double ms = (end-start) / 1000000.0;
//			//System.out.println("computation time is "+ms+"ms");
//			aaa.runningTime = aaa.runningTime+  ms;
//		}
//		result[index] = aaa;
//	}
//
//	public static void computeDistanceTest2(Client a, Client b){
//
//		long start, end;
//
//
//		BigInteger r1 = Utills.generateRandomBigInteger(7);
//
//		BigInteger xMatrix[] = a.getXMatrix();
//		BigInteger yMatrix[] = a.getYMatrix();
//		//ALice: Encrypt and send to Bob
//		for(int i=0;i<a.getXMatrix().length;i++){
//			xMatrix[i] = a.getPublickKey().Encryption(xMatrix[i],r1);
//		}
//
//		for(int i=0;i<a.getYMatrix().length;i++){
//			yMatrix[i] = a.getPublickKey().Encryption(yMatrix[i],r1);
//		}
//
//		counter++;
//		start = System.nanoTime();
//
//		BigInteger x1Matrix[] = b.getXMatrix();
//		BigInteger y1Matrix[] = b.getYMatrix();
//
//		BigInteger resultX[][] = new BigInteger[6][6];
//		BigInteger result_xx = new BigInteger("0");
//
//		BigInteger resultY[][] = new BigInteger[7][7];
//		BigInteger result_yy = new BigInteger("0");
//
//		//Get w' and send to Alice
//
//		//Computation for easting
//		int power=10;
//		for(int j=0;j<6;j++){
//			power = power-j;
//			for(int k=0;k<6;k++){
//				BigInteger temp =  new BigInteger("1");
//				resultX[j][k] = Utills.BigIntegerPow(xMatrix[j],x1Matrix[k]);
//				BigInteger sB = new BigInteger("0");
//				BigInteger r = Utills.generateRandomBigInteger(3);
//				temp = resultX[j][k].multiply(a.getPublickKey().Encryption(sB.negate(),r));
//				temp = a.getPublickKey().Decryption(temp);
//				temp = temp.multiply(new BigInteger(""+(long) Math.pow(10,power)));
//				power--;
//				result_xx = result_xx.add(temp);
//			}
//			power=10;
//		}
//
//		//Computing for northing
//		power = 12;
//		for(int j=0;j<7;j++){
//			power = power-j;
//			for(int k=0;k<7;k++){
//				BigInteger temp =  new BigInteger("1");
//				resultY[j][k] = Utills.BigIntegerPow(yMatrix[j],y1Matrix[k]);
//				BigInteger sB = new BigInteger("0");
//				BigInteger r = Utills.generateRandomBigInteger(3);
//				temp = resultY[j][k].multiply(a.getPublickKey().Encryption(sB.negate(),r));
//				temp = a.getPublickKey().Decryption(temp);
//				temp = temp.multiply(new BigInteger(""+(long) Math.pow(10,power)));
//				power--;
//				result_yy = result_yy.add(temp);
//			}
//			power=12;
//		}
//
//		//Compute distance between two client
//		Utills.computeDistance(a,b,result_xx,result_yy);
//
//		//Testing
//		//System.out.println("The result should be : "+ Utills.checkResult(a,b));
//
//		end = System.nanoTime();
//		double ms = (end-start) / 1000000.0;
//
//
//	}
//	/**
//	 * Function to compute all the distances between Clients and counting time it takes at the same time
//	 * @param list of Client
//	 * @param publicKey
//	 * @param N
//	 */
//	public static void computeDistance_4_3_digit(ArrayList<Client> list){
//
//		long start, end;
//
//		Client base = new Client(0000,44.008851,100.321485);
//
//		for(int i=0;i<list.size();i++){
//			counter++;
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
//			//System.out.println("The result should be : "+ Utills.checkResult(list.get(i),base));
//
//			end = System.nanoTime();
//			double ms = (end-start) / 1000000.0;
//
//
//		}
//	}







