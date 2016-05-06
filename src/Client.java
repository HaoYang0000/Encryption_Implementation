import java.math.BigInteger;


public class Client {
	
	private BigInteger x;
	private BigInteger y;
	private BigInteger ciX;
	private BigInteger ciY;
	private String name;
	private Paillier privateKey;
	private Paillier publickKey;

	/**
	 * Constructors
	 * 
	 */
	public Client(){
	}
	
	/**
	 * Generate a client with random position, bit of number is n 
	 * @param n
	 */
	public Client(int n){
		this.x = Utills.generateRandomBigInteger(n);
		this.y = Utills.generateRandomBigInteger(n);
	}
	
	/**
	 * Generate a client with a fixed position 
	 * @param x
	 * @param y
	 */
	public Client(BigInteger x, BigInteger y){
		this.x = x;
		this.y = y;
	}

	/**
	 * Generate a client with a fixed position and a name
	 * @param x
	 * @param y
	 * @param name
	 */
	public Client(BigInteger x, BigInteger y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.privateKey = new Paillier(64,16);
		this.publickKey = new Paillier(64,16);
	}


	/**
	 * Setters and getters
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigInteger getX() {
		return x;
	}

	public void setX(BigInteger x) {
		this.x = x;
	}

	public BigInteger getY() {
		return y;
	}

	public void setY(BigInteger y) {
		this.y = y;
	}
	
	//Get value of x*y
	public BigInteger getXY(){
		return x.multiply(y);
	}
	
	//Get value of x^2+y^2
	public BigInteger getXS_YS(){
		return x.multiply(x).add(y.multiply(y));
	}
	
	//Set encrypted value of Enc(x;r) and Enc(y;r)
	public void setCi(int numberOfBit){
		BigInteger r = Utills.generateRandomBigInteger(numberOfBit);
		this.ciX = publickKey.Encryption(x,r);
		this.ciY = publickKey.Encryption(y,r);
	}
	
	//Get Enc(x;r)
	public BigInteger getCiX(){
		return this.ciX;
	}
	
	//Get Enc(y;r)
	public BigInteger getCiY(){
		return this.ciY;
	}
	
	public Paillier getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(Paillier privateKey) {
		this.privateKey = privateKey;
	}

	public void setCiX(BigInteger ciX) {
		this.ciX = ciX;
	}

	public void setCiY(BigInteger ciY) {
		this.ciY = ciY;
	}
	

	public Paillier getPublickKey() {
		return publickKey;
	}

	public void setPublickKey(Paillier publickKey) {
		this.publickKey = publickKey;
	}
	
}
