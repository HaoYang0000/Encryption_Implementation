import java.math.BigInteger;


public class Client {
	
	private BigInteger x;
	private BigInteger y;
	private int Id;
	private String name;
	private Paillier privateKey;
	private Paillier publickKey;
	private double northing;
	private double easting;
	private String letterZone;

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
		Deg2UTM converter = new Deg2UTM(Utills.convertToDouble(x),Utills.convertToDouble(y));	
		this.easting = converter.getEasting();
		this.northing = converter.getNorthing();
		this.letterZone = converter.getLetter()+" "+converter.getZone();
			
	}
	
	/**
	 * Generate a client with a fixed position 
	 * @param x
	 * @param y
	 */
	public Client(BigInteger x, BigInteger y){
		this.x = x;
		this.y = y;
		Deg2UTM converter = new Deg2UTM(Utills.convertToDouble(x),Utills.convertToDouble(y));	
		this.easting = converter.getEasting();
		this.northing = converter.getNorthing();
		this.letterZone = converter.getLetter()+" "+converter.getZone();
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
		Deg2UTM converter = new Deg2UTM(Utills.convertToDouble(x),Utills.convertToDouble(y));	
		this.easting = converter.getEasting();
		this.northing = converter.getNorthing();
		this.letterZone = converter.getLetter()+" "+converter.getZone();
		this.privateKey = new Paillier(64,16);
		this.publickKey = new Paillier(64,16);
	}
	
	/**
	 * Generate a client with a fixed position and a name (Old one)
	 * @param x
	 * @param y
	 * @param name
	 */
	public Client(int Id, String name,BigInteger x, BigInteger y) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.Id = Id;
		this.privateKey = new Paillier(64,16);
		this.publickKey = new Paillier(64,16);
		Deg2UTM converter = new Deg2UTM(Utills.convertToDouble(x),Utills.convertToDouble(y));	
		this.easting = converter.getEasting();
		this.northing = converter.getNorthing();
		this.letterZone = converter.getLetter()+" "+converter.getZone();
	}
	
	/**
	 * Generate a client with a fixed position and a name(UTM)
	 * @param Id
	 * @param name
	 * @param x
	 * @param y
	 */
	public Client(int Id, String name,double latitude, double longitude,int n) {
		this.name = name;
		this.Id = Id;
		this.privateKey = new Paillier(64,16);
		this.publickKey = new Paillier(64,16);
		Deg2UTM converter = new Deg2UTM(latitude,longitude);	
		this.easting = Math.round(converter.getEasting());
		this.northing = Math.round(converter.getNorthing());
		this.letterZone = converter.getLetter()+" "+converter.getZone();
		//this.x = new BigInteger(""+ (int) (Utills.cutDigit(this.easting,n)));
		//this.y = new BigInteger(""+ (int) (Utills.cutDigit(this.northing,n)));
		this.x = new BigInteger(""+ (int) this.easting);
		this.y = new BigInteger(""+ (int) this.northing);

	}
	
	/**
	 * Generate a client with a fixed position
	 * @param Id
	 * @param name
	 * @param x
	 * @param y
	 */
	public Client(int Id, double latitude, double longitude) {
		this.Id = Id;
		this.privateKey = new Paillier(64,16);
		this.publickKey = new Paillier(64,16);
		Deg2UTM converter = new Deg2UTM(latitude,longitude);	
		this.easting = Math.round(converter.getEasting());
		this.northing = Math.round(converter.getNorthing());
		this.letterZone = converter.getLetter()+" "+converter.getZone();
		//this.x = new BigInteger(""+ (int) (Utills.cutDigit(this.easting,n)));
		//this.y = new BigInteger(""+ (int) (Utills.cutDigit(this.northing,n)));
		this.x = new BigInteger(""+ (int) this.easting);
		this.y = new BigInteger(""+ (int) this.northing);
		

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

	public BigInteger getX(int numberOfBit) {
		//System.out.println("X is : "+x.divide(new BigInteger(""+(int) Math.pow(10,6-numberOfBit))));
		return x.divide(new BigInteger(""+(int) Math.pow(10,6-numberOfBit)));
	}
	
	public BigInteger getX() {
		//System.out.println("X is : "+x.divide(new BigInteger(""+(int) Math.pow(10,6-numberOfBit))));
		return x;
	}

	public void setX(BigInteger x) {
		this.x = x;
	}

	public BigInteger getY(int numberOfBit) {
		//System.out.println("Y is : "+y.divide(new BigInteger(""+(int) Math.pow(10,6-numberOfBit))));
		
		return y.divide(new BigInteger(""+(int) Math.pow(10,6-numberOfBit)));
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
	
	public BigInteger getShortXS_YS() {
		BigInteger tempX = Utills.cutNumber(x,2);
		BigInteger tempY = Utills.cutNumber(y,2);
		return tempX.multiply(tempX).add(tempY.multiply(tempY));
	}
	
	//Get encrypted value of Enc(x;r) and Enc(y;r) with N digit
	public BigInteger getCiX(int numberOfBit){
		BigInteger r = Utills.generateRandomBigInteger(numberOfBit);
		//System.out.println("Cix is :" +x+ x.divide(new BigInteger(""+(int) Math.pow(10,6-numberOfBit))));
		BigInteger ciX = publickKey.Encryption(x.divide(new BigInteger(""+(int) Math.pow(10,6-numberOfBit))),r);
		
		return ciX;
	}
	
	public BigInteger getCiY(int numberOfBit){
		BigInteger r = Utills.generateRandomBigInteger(numberOfBit);
		//System.out.println("CiY is :" +y+ y.divide(new BigInteger(""+(int) Math.pow(10,6-numberOfBit))));
		
		BigInteger ciY = publickKey.Encryption(y.divide(new BigInteger(""+(int) Math.pow(10,6-numberOfBit))),r);
		return ciY;
	}
	
	public Paillier getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(Paillier privateKey) {
		this.privateKey = privateKey;
	}
	
	public Paillier getPublickKey() {
		return publickKey;
	}

	public void setPublickKey(Paillier publickKey) {
		this.publickKey = publickKey;
	}
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}
	
	
	public double getNorthing() {
		return northing;
	}

	public void setNorthing(double northing) {
		this.northing = northing;
	}

	public double getEasting() {
		return easting;
	}

	public void setEasting(double easting) {
		this.easting = easting;
	}

	public String getLetterZone() {
		return letterZone;
	}

	public void setLetterZone(String letterZone) {
		this.letterZone = letterZone;
	}
	
	public int getXA(){
		return (int) this.easting/1000;
	}
	
	public int getXB(){
		return (int) this.easting%1000;
	}
	
	public int getYA(){
		return (int) this.northing/1000;
	}
	
	public int getYB(){
		return (int) this.northing%1000;
	}
	
	public BigInteger[] getXMatrix(){
		BigInteger matrix[] = new BigInteger [6];
		int temp = (int) this.easting;
		int index=0;
		for(int i=100000;i>=1;i=i/10){
			matrix[index] = new BigInteger("" + temp/i);
			index++;
			temp = temp%i;
		}
		return matrix;
	}
	
	public BigInteger[] getYMatrix(){
		BigInteger matrix[] = new BigInteger [7];
		int temp = (int) this.northing;
		int index=0;
		for(int i=1000000;i>=1;i=i/10){
			matrix[index] = new BigInteger("" + temp/i);
			index++;
			temp = temp%i;
		}
		return matrix;
	}

	
}
