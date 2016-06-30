import java.math.BigInteger;
import java.util.Random;


public class Utills {
	/**
	 * Function to generate a random vector with n bits
	 * @param n
	 * @return
	 */
	public static BigInteger generateRandomPriviteInput(int n)
	{
		String letters = "01";
		Random random = new Random();
		String r = "";
		for (int i=0; i<n; i++)
		{
			int index = (int)(random.nextDouble()*letters.length());
			r += letters.substring(index, index+1);
		}
		return new BigInteger(r);
	}

	/**
	 * Function to generate a random BigInteger with n bits
	 * @param n
	 * @return
	 */
	public static BigInteger generateRandomBigInteger(int n)
	{
		String letters = "0123456789";
		Random random = new Random();
		String r = "";
		for (int i=0; i<n; i++)
		{
			int index = (int)(random.nextDouble()*letters.length());
			r += letters.substring(index, index+1);
		}
		return new BigInteger(r);
	}

	/**
	 * Function to compute Power of BigIntger, base BigInteger
	 * @param base
	 * @param exponent
	 * @return
	 */
	public static BigInteger BigIntegerPow(BigInteger base, BigInteger exponent) {
		BigInteger result = BigInteger.ONE;
		while (exponent.signum() > 0) {
			if (exponent.testBit(0)) result = result.multiply(base);
			base = base.multiply(base);
			exponent = exponent.shiftRight(1);
		}
		return result;
	}
	
	public static BigInteger computeDistance(Client a, Client b,BigInteger x1x2, BigInteger y1y2){
		BigInteger result = sqrt(a.getXS_YS().add(b.getXS_YS()).subtract(x1x2.multiply(new BigInteger("2"))).subtract(y1y2.multiply(new BigInteger("2"))));
		System.out.println("Distance between : user "+a.getId()+" and user "+b.getId()+" is " +result + "m");
		return result;
	}
	
//	public static BigInteger computeShortDistance(Client a, Client b,BigInteger x1x2, BigInteger y1y2){
//		//System.out.println(x1x2+ " " +y1y2+"  "+a.getShortXS_YS());
//		BigInteger result = sqrt(a.getShortXS_YS().add(b.getShortXS_YS()).subtract(x1x2.multiply(new BigInteger("2"))).subtract(y1y2.multiply(new BigInteger("2"))));
//		System.out.println("Distance between : "+a.getName()+" and "+b.getName()+" is " +result);
//		return result;
//	}

	public static BigInteger sqrt(BigInteger n) {
		BigInteger a = BigInteger.ONE;
		BigInteger b = new BigInteger(n.shiftRight(5).add(new BigInteger("8")).toString());
		while(b.compareTo(a) >= 0) {
			BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
			if(mid.multiply(mid).compareTo(n) > 0) b = mid.subtract(BigInteger.ONE);
			else a = mid.add(BigInteger.ONE);
		}
		return a.subtract(BigInteger.ONE);
	}
	
	public static BigInteger setW_New(Paillier publickKey,BigInteger ci,BigInteger input,int N){
		BigInteger w = Utills.BigIntegerPow(ci,input);
		//System.out.println("w is :"+w);
		//Random plaintext sB:
		BigInteger sB = new BigInteger("0");
		//System.out.println("Sb is :"+sB);
		//Random nonce r': 
		BigInteger r1 = Utills.generateRandomBigInteger(N);
		return w.multiply(publickKey.Encryption(sB.negate(),r1));
	}

	public static BigInteger setW(Paillier publickKey,BigInteger ci,BigInteger input,int N){
		BigInteger w = Utills.BigIntegerPow(ci,input);
		//System.out.println("w is :"+w);
		//Random plaintext sB:
		BigInteger sB = new BigInteger("0");
		//System.out.println("Sb is :"+sB);
		//Random nonce r': 
		BigInteger r1 = Utills.generateRandomBigInteger(N);
		return w.multiply(publickKey.Encryption(sB.negate(),r1));
	}

	public static double checkResult(Client clientA, Client clientB) {
		double x1_x2 = Math.pow((clientA.getNorthing()-clientB.getNorthing()),2);
		double y1_y2 = Math.pow((clientA.getEasting()-clientB.getEasting()),2);
		
		
		return Math.sqrt(x1_x2+y1_y2);
	}
	
	public static double convertToDouble(BigInteger bigInteger){
		String temp = bigInteger.toString();
		double result = Double.parseDouble(temp);
		return result;
	}
	
	public static int cutDigit(double number,int n){
		int count=0;
		double temp = number;
		while(number>1){
			number = number/10;
			count++;
		}
		if(count>n){
			temp = temp / Math.pow(10,count-n);
			return (int) ( temp * Math.pow(10,count-n));
		}
		else
			return (int) temp;
	}
	
	
	public static BigInteger cutNumber(BigInteger number,int dataLength){
		int newNumber = (int) convertToDouble(number);
		int temp = (int) ( (newNumber % Math.pow(10,dataLength)) );
		
		//System.out.println("new number is "+temp);
		return new BigInteger(""+ temp);
	}
	
	public static int sameDigit(double number1, double number2){
		int count = 0;
		
		return count;
	}
}
