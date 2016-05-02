import java.math.BigInteger;
import java.util.Random;
import java.security.SecureRandom;

import paillierp.key.KeyGen;
import paillierp.key.PaillierKey;
import paillierp.key.PaillierPrivateKey;
import paillierp.Paillier ;
import paillierp.PaillierThreshold ;
import paillierp.PartialDecryption;
import paillierp.key.PaillierPrivateThresholdKey ;
import paillierp.zkp.DecryptionZKP ;

/******************************************************************************
 *
 * Author:        Hao Yang
 * Date:          04/29/2016
 * Description:   This program encrypt a information from position (x1,y1) and send it to (x2,y2).
 * 				  And would make sure the private information from (x1,y1) is secured. 
 *
 ******************************************************************************/

public class main {

	public static void main(String[] args) {
		/**
		 * Reference from version 1
		 * 
		 */

		//		// create random object
		//		Random random = new Random();
		//
		//		//Number of bit
		//		final int N = 10;
		//		//seed for generating random number
		//		long seed = random.nextLong();
		//
		//		/*
		//		 * @param s    Specifies the number of bits required for the prime factor 
		//		 *             of n.
		//		 * @param l    Number of decryption servers.
		//		 * @param w    Threshold number of decryption servers.  Must be
		//		 *             &le;&frac12;<code>l</code>
		//		 * @param seed Specifies the seed for the random number generator used.
		//		 */
		//		int s = 8;
		//		int l = 4;    
		//		int w = 3;
		//
		//		//Generate private keys for four private coordinate
		//		PaillierPrivateThresholdKey [] keys = KeyGen.PaillierThresholdKey (s,l,w,seed) ;
		//
		//		//Private Inputs
		//		PaillierThreshold x1 = new PaillierThreshold (keys[0]) ;
		//		PaillierThreshold y1 = new PaillierThreshold (keys[1]) ;
		//		PaillierThreshold x2 = new PaillierThreshold (keys[2]) ;
		//		PaillierThreshold y2 = new PaillierThreshold (keys[3]) ;
		//		
		//		//Alice encrypts a message and sends msg to Bob
		//		BigInteger msg =  new BigInteger(N,random);
		//		Paillier alice1 = new Paillier (keys[0].getPublicKey()) ;
		//		BigInteger Emsg = alice1.encrypt (msg) ;
		//		System.out.println("The message from Alice to Bob is :"+msg);
		//
		//		//Send one of Alice's pubic Key to Bob: aliceShare1 or aliceShare2
		//		DecryptionZKP aliceShare1 = x1.decryptProof (Emsg) ;
		//		DecryptionZKP aliceShare2 = y1.decryptProof (Emsg) ;
		//		DecryptionZKP bobShare1 = x2.decryptProof (Emsg) ;
		//		DecryptionZKP bobShare2 = y2.decryptProof (Emsg) ;
		//
		//		//Bob receive the message and decrypt
		//		BigInteger p2decrypt = x2.combineShares ( aliceShare1 , bobShare1 , bobShare2 ) ;
		//		if ( p2decrypt.equals (msg) ) {
		//			System.out.println ("Bob succeeds decrypting the message:"+msg) ;
		//		} else {
		//			System.out.println ("Bob fails decrypting the message:"+msg) ;
		//		}

		/*
		 * Number of Vector bit: N
		 *  We have two user here which are Alice (x) and Bob (y)
		 */
		
		final int N = 3;

		String x = generateRandomPriviteInput(N);
		System.out.println("X is : "+x);
		String y = generateRandomPriviteInput(N);
		System.out.println("Y is : "+y);

		//
		enctyption(x,y);

	}

	/**
	 * Function to protect data privacy 
	 * @param x
	 * @param y
	 */
	public static void enctyption(String x,String y){
		
		/* 1.Start phase: 
		 * Generate a private and public key pair
		 * Send public key to Bob
		 */

		//Number of bit: N
		final int N = 3;
		// create random object
		Random random = new Random();
		//seed for generating random number
		long seed = random.nextLong();

		/*
		 * @param s    Specifies the number of bits required for the prime factor 
		 *             of n.
		 * @param l    Number of decryption servers.
		 * @param d    Threshold number of decryption servers.  Must be
		 *             &le;&frac12;<code>l</code>
		 * @param seed Specifies the seed for the random number generator used.
		 */

		int s = 3;
		int l = 2;    
		int d = 3;

		//Generate private keys for four private coordinate
		PaillierPrivateThresholdKey [] keys = KeyGen.PaillierThresholdKey(s,l,d,seed);
		PaillierPrivateKey key1 = KeyGen.PaillierKey(s,seed);

		
		//PaillierThreshold Alice = new PaillierThreshold (key1) ;

		//Send public key to Bob:
		Paillier AlicePublicKey = new Paillier(key1);
		//PaillierKey publicKey = Alice.getPublicKey();
		
		/* 2. Alice does for i ∈ {1, . . . , N}
		 * Generate a random new string ri
		 * Send ci = Encpk(xi; ri) to Bob.
		 */
		BigInteger c [] = new BigInteger[N];
		BigInteger r = new BigInteger(N, random);
		System.out.println("The new string ri is "+r);

		for(int i=0;i<N;i++){
			BigInteger temp = new BigInteger(""+x.charAt(i));
			c[i] = AlicePublicKey.encrypt(temp);
			System.out.println("x"+i+" is "+temp+", C"+i+" = :"+c[i]);
		}
		
		System.out.println("result of 0 is :"+AlicePublicKey.encrypt(new BigInteger("0")));
		
		BigInteger a = AlicePublicKey.encrypt(r);
		System.out.println("CHecking result encrypt: "+r +"   "+a);
		AlicePublicKey.setDecryption(key1);
		System.out.println("CHecking result encrypt: "+AlicePublicKey.decrypt(a));
		//Send c to Bob

		/* 3. Bob does:
		 * Set w
		 * Generate a random plaintext sB and a random nonce r'
		 * Send w' = w · Encpk(−sB; r') to Alice.
		 */
		//Bob's private Inputs: y
		PaillierThreshold Bob = new PaillierThreshold (keys[1]) ;

		BigInteger w = new BigInteger("1");
		//Random plaintext sB:
		BigInteger sB = new BigInteger(N, random);
		System.out.println("Sb is :"+sB);
		//r': 
		BigInteger r1 = new BigInteger(N, random);
		//Set w;
		for(int i=0;i<N;i++){
			if(y.charAt(i) == '1'){
				w = w.multiply(c[i]);
			}
		}
		
		System.out.println("The value of w is "+w);
		System.out.println("The value of pailler key NS is "+AlicePublicKey.getPublicKey().getNS());
		//Send w' to Alice: w

		/* 4. Alice does: Compute sA = Decsk(w') = x · y − sB
		 */
		//AlicePublicKey.setDecryption(AlicePublicKey.getPrivateKey());
		BigInteger temp0 = AlicePublicKey.encrypt(new BigInteger("0"));
		w = w.multiply(temp0);
		System.out.println("After Dec(0) value = "+temp0);
//		for(int i =0;i<N;i++){
		System.out.println("W' value = "+w);
			//PartialDecryption sA = Alice.decrypt(w);
			//System.out.println("Alice get Decsk(w'): "+sA.getDecryptedValue());
//		}

		computeScalarProduct(x,y);
	}

	/**
	 * Function to generate a random string vector with n bits
	 * @param n
	 * @return
	 */
	public static String generateRandomPriviteInput(int n)
	{
		String letters = "01";
		Random random = new Random();
		String r = "";
		for (int i=0; i<n; i++)
		{
			int index = (int)(random.nextDouble()*letters.length());
			r += letters.substring(index, index+1);
		}
		return r;
	}
	
	/**
	 * Function to generate scalar product, take two string vector as variables
	 * @param x
	 * @param y
	 * @return
	 */
	public static String computeScalarProduct(String x, String y)
	{
		String result = "";
		for(int i=0;i<x.length();i++){
			if(x.charAt(i) == '1' && y.charAt(i) == '1'){
				result = result + "1";
			}
			else{
				result = result + "0";
			}
		}
		
		System.out.println("The result of X:"+x+" and Y:"+y+" is "+result);
		return result;
	}

}
