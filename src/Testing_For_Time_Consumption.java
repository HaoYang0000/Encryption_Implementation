import java.math.BigInteger;
import java.util.ArrayList;


public class Testing_For_Time_Consumption {

	public static void main(String[] str) {
		Paillier key = new Paillier(64,16);
		BigInteger x = new BigInteger("1234567");
		BigInteger y = new BigInteger("7654321");
		BigInteger encrypt_X = new BigInteger("1");
		BigInteger encrypt_Y = new BigInteger("1");
		BigInteger power_X3 = new BigInteger("1");
		BigInteger power_Y3 = new BigInteger("1");
		BigInteger power_X4 = new BigInteger("1");
		BigInteger power_Y4 = new BigInteger("1");
		BigInteger power_X5 = new BigInteger("1");
		BigInteger power_Y5 = new BigInteger("1");
		BigInteger power_X6 = new BigInteger("1");
		BigInteger power_Y6 = new BigInteger("1");
		BigInteger power_X7 = new BigInteger("1");
		BigInteger power_Y7 = new BigInteger("1");
		
		/*
		 * result[0] = Encryption
		 * result[1-5] = Power operation 
		 * result[6-10] = Decryption
		 */
		long result[] = new long[11];

		for(int i=0;i<10;i++){
			/**
			 * Old Algroithm
			 * Step 1: Encryption 
			 */
			long totalTimeStart = System.nanoTime();
			encrypt_X = key.Encryption(x);
			encrypt_Y = key.Encryption(y);
			long totalTimeEnd = System.nanoTime();
			System.out.println("computation time of encrypt 7 digit is "+((totalTimeEnd-totalTimeStart)/1000000.0)+"ms");
			result[0] = (long) (result[0] + (totalTimeEnd-totalTimeStart)/1000000.0);

			/**
			 * Old Algroithm
			 * Step 2: Power operation 
			 */
			totalTimeStart = System.nanoTime();
			power_X3 = Utills.BigIntegerPow(encrypt_X, new BigInteger("100"));
			power_Y3 = Utills.BigIntegerPow(encrypt_Y, new BigInteger("100"));
			totalTimeEnd = System.nanoTime();
			System.out.println("Power operation of 3 digit is "+((totalTimeEnd-totalTimeStart)/1000000.0)+"ms");
			result[1] = (long) (result[1] + (totalTimeEnd-totalTimeStart)/1000000.0);

			totalTimeStart = System.nanoTime();
			power_X4 = Utills.BigIntegerPow(encrypt_X, new BigInteger("1000"));
			power_Y4 = Utills.BigIntegerPow(encrypt_Y, new BigInteger("1000"));
			totalTimeEnd = System.nanoTime();
			System.out.println("Power operation of 4 digit is "+((totalTimeEnd-totalTimeStart)/1000000.0)+"ms");
			result[2] = (long) (result[2] + (totalTimeEnd-totalTimeStart)/1000000.0);

			totalTimeStart = System.nanoTime();
			power_X5 = Utills.BigIntegerPow(encrypt_X, new BigInteger("10000"));
			power_Y5 = Utills.BigIntegerPow(encrypt_Y, new BigInteger("10000"));
			totalTimeEnd = System.nanoTime();
			System.out.println("Power operation of 5 digit is "+((totalTimeEnd-totalTimeStart)/1000000.0)+"ms");
			result[3] = (long) (result[3] + (totalTimeEnd-totalTimeStart)/1000000.0);

			totalTimeStart = System.nanoTime();
			power_X6 = Utills.BigIntegerPow(encrypt_X, new BigInteger("100000"));
			power_Y6 = Utills.BigIntegerPow(encrypt_Y, new BigInteger("100000"));
			totalTimeEnd = System.nanoTime();
			System.out.println("Power operation of 6 digit is "+((totalTimeEnd-totalTimeStart)/1000000.0)+"ms");
			result[4] = (long) (result[4] + (totalTimeEnd-totalTimeStart)/1000000.0);

			totalTimeStart = System.nanoTime();
			power_X7 = Utills.BigIntegerPow(encrypt_X, new BigInteger("1000000"));
			power_Y7 = Utills.BigIntegerPow(encrypt_Y, new BigInteger("1000000"));
			totalTimeEnd = System.nanoTime();
			System.out.println("Power operation of 7 digit is "+((totalTimeEnd-totalTimeStart)/1000000.0)+"ms");
			result[5] = (long) (result[5] + (totalTimeEnd-totalTimeStart)/1000000.0);

			/**
			 * Old Algroithm
			 * Step 3: Decryption 
			 */
			totalTimeStart = System.nanoTime();
			key.Decryption(power_X3.multiply(power_Y3));
			totalTimeEnd = System.nanoTime();
			System.out.println("Decryption time of 3 digit is "+((totalTimeEnd-totalTimeStart)/1000000.0)+"ms");
			result[6] = (long) (result[6] + (totalTimeEnd-totalTimeStart)/1000000.0);

			totalTimeStart = System.nanoTime();
			key.Decryption(power_X4.multiply(power_Y4));
			totalTimeEnd = System.nanoTime();
			System.out.println("Decryption time of 4 digit is "+((totalTimeEnd-totalTimeStart)/1000000.0)+"ms");
			result[7] = (long) (result[7] + (totalTimeEnd-totalTimeStart)/1000000.0);

			totalTimeStart = System.nanoTime();
			key.Decryption(power_X5.multiply(power_Y5));
			totalTimeEnd = System.nanoTime();
			System.out.println("Decryption time of 5 digit is "+((totalTimeEnd-totalTimeStart)/1000000.0)+"ms");
			result[8] = (long) (result[8] + (totalTimeEnd-totalTimeStart)/1000000.0);

			totalTimeStart = System.nanoTime();
			key.Decryption(power_X6.multiply(power_Y6));
			totalTimeEnd = System.nanoTime();
			System.out.println("Decryption time of 6 digit is "+((totalTimeEnd-totalTimeStart)/1000000.0)+"ms");
			result[9] = (long) (result[9] + (totalTimeEnd-totalTimeStart)/1000000.0);

			totalTimeStart = System.nanoTime();
			key.Decryption(power_X7.multiply(power_Y7));
			totalTimeEnd = System.nanoTime();
			System.out.println("Decryption time of 7 digit is "+((totalTimeEnd-totalTimeStart)/1000000.0)+"ms");
			result[10] = (long) (result[10] + (totalTimeEnd-totalTimeStart)/1000000.0);
		}

		/**
		 * Find the average time of each part
		 */
		for(int i=0;i<10;i++){
			result[i] = result[i]/10;
		}
		System.out.println("Average computation time of encrypt 7 digit is " + result[0]+"ms");
		System.out.println("Average power operation of 3 digit is "+ result[1]+"ms");
		System.out.println("Average power operation of 4 digit is "+ result[2]+"ms");
		System.out.println("Average power operation of 5 digit is "+ result[3]+"ms");
		System.out.println("Average power operation of 6 digit is "+ result[4]+"ms");
		System.out.println("Average power operation of 7 digit is "+ result[5]+"ms");
		System.out.println("Average decryption time of 3 digit is "+ result[6]+"ms");
		System.out.println("Average decryption time of 4 digit is "+ result[7]+"ms");
		System.out.println("Average decryption time of 5 digit is "+ result[8]+"ms");
		System.out.println("Average decryption time of 6 digit is "+ result[9]+"ms");
		System.out.println("Average decryption time of 7 digit is "+ result[10]+"ms");

		/**
		 * New Algroithm
		 */
		double time = 0;
		Client a = new Client(1,44.008851,100.321485);
		Client b = new Client(1,20.008851,30.321485);
		for(int i=0;i<10;i++){
			long totalTimeStart = System.nanoTime();
			newAlgorithm(a,b);
			long totalTimeEnd = System.nanoTime();
			System.out.println("Running time is "+((totalTimeEnd-totalTimeStart)/1000000.0)+"ms");
			time = time + (totalTimeEnd-totalTimeStart)/1000000.0;
		}
		System.out.println(time/10);

	}

	public static void newAlgorithm(Client a, Client b){
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

		//Bob
		start = System.nanoTime();

		BigInteger x1Matrix[] = b.getXMatrix();
		BigInteger y1Matrix[] = b.getYMatrix();

		BigInteger resultX[][] = new BigInteger[6][6];
		BigInteger result_xx = new BigInteger("0");

		BigInteger resultY[][] = new BigInteger[7][7];
		BigInteger result_yy = new BigInteger("0");

		//Get w' and send to Alice to decrypt

		//Computation for easting
		int power=10;
		for(int j=0;j<6;j++){
			power = power-j;
			for(int k=0;k<6;k++){
				resultX[j][k] = Utills.BigIntegerPow(xMatrix[j],x1Matrix[k]);
				resultX[j][k] = a.getPublickKey().Decryption(resultX[j][k]);
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
				resultY[j][k] = Utills.BigIntegerPow(yMatrix[j],y1Matrix[k]);
				resultY[j][k] = a.getPublickKey().Decryption(resultY[j][k]);
				resultY[j][k] = resultY[j][k].multiply(new BigInteger(""+(long) Math.pow(10,power)));
				power--;
				result_yy = result_yy.add(resultY[j][k]);
			}
			power=12;
		}
		//Compute distance between two client
		Utills.computeDistance(b,a,result_xx,result_yy);

		//Method to check the real result
		//System.out.println("The result should be : "+ Utills.checkResult(b,a));

		end = System.nanoTime();
		double ms = (end-start) / 1000000.0;
		System.out.println("computation time is "+ms+"ms");


	}
}
