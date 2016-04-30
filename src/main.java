import java.math.BigInteger;
import java.util.Random;
import paillierp.key.KeyGen;
import paillierp.Paillier ;
import paillierp.PaillierThreshold ;
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
		
		// create random object
		Random random = new Random();

		//Number of bit
		final int N = 10;
		//seed for generating random number
		long seed = random.nextLong();

		/*
		 * @param s    Specifies the number of bits required for the prime factor 
		 *             of n.
		 * @param l    Number of decryption servers.
		 * @param w    Threshold number of decryption servers.  Must be
		 *             &le;&frac12;<code>l</code>
		 * @param seed Specifies the seed for the random number generator used.
		 */
		int s = 8;
		int l = 4;    
		int w = 3;

		//Generate private keys for four private coordinate
		PaillierPrivateThresholdKey [] keys = KeyGen.PaillierThresholdKey (s,l,w,seed) ;

		//Private Inputs
		PaillierThreshold x1 = new PaillierThreshold (keys[0]) ;
		PaillierThreshold y1 = new PaillierThreshold (keys[1]) ;
		PaillierThreshold x2 = new PaillierThreshold (keys[2]) ;
		PaillierThreshold y2 = new PaillierThreshold (keys[3]) ;
		
		//Alice encrypts a message and sends msg to Bob
		BigInteger msg =  new BigInteger(N,random);
		Paillier alice1 = new Paillier (keys[0].getPublicKey()) ;
		BigInteger Emsg = alice1.encrypt (msg) ;
		System.out.println("The message from Alice to Bob is :"+msg);

		//Send one of Alice's pubic Key to Bob: aliceShare1 or aliceShare2
		DecryptionZKP aliceShare1 = x1.decryptProof (Emsg) ;
		DecryptionZKP aliceShare2 = y1.decryptProof (Emsg) ;
		DecryptionZKP bobShare1 = x2.decryptProof (Emsg) ;
		DecryptionZKP bobShare2 = y2.decryptProof (Emsg) ;

		//Bob receive the message and decrypt
		BigInteger p2decrypt = x2.combineShares ( aliceShare1 , bobShare1 , bobShare2 ) ;
		if ( p2decrypt.equals (msg) ) {
			System.out.println ("Bob succeeds decrypting the message:"+msg) ;
		} else {
			System.out.println ("Bob fails decrypting the message:"+msg) ;
		}

	}
}
