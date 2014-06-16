package fr.miage.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.Security;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public final class Cryptography {
	/*
	private static final Cryptography cryptographie;
	private String salt = null;
	private Properties prop = new Properties();
	private static final String ALGO = "AES";
	static{
		Security.addProvider(new BouncyCastleProvider());
		cryptographie = new Cryptography();
    }
	
	private Cryptography() {
		try {
			InputStream inputStream  = getClass().getClassLoader().getResourceAsStream("pathworld_local.properties");
			prop.load(inputStream);
			String salt = prop.getProperty("salt");
			this.salt = salt;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Cryptography getInstance(){
		return cryptographie;
	}
	
	public String getSalt(){
		return salt;
	}
	
	public String calculateRFC2104HMAC(String data, String key) throws java.security.SignatureException {
		String result;
		try {
			// get an hmac_sha1 key from the raw key bytes
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(),"HmacSHA1");

			// get an hmac_sha1 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);

			// compute the hmac on input data bytes
			byte[] rawHmac = mac.doFinal(data.getBytes());

			// base64-encode the hmac
			result = DatatypeConverter.printBase64Binary(rawHmac);

		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : "
					+ e.getMessage());
		}
		return result;
	}

	public String encryptWithAES(String pwd) {
		byte[] cipherText = null;
	    try {
	    	Key privateKey = getKey();
	    	// get an RSA cipher object and print the provider
	    	final Cipher cipher = Cipher.getInstance(ALGO);
	    	// encrypt the plain text using the public key
	    	cipher.init(Cipher.ENCRYPT_MODE, privateKey);
	    	cipherText = cipher.doFinal(pwd.getBytes());	      
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return new String(cipherText);
	}
	
	public String decryptWithAES(String pwd) {
		byte[] decryptedText = null;
	    try {
	    	Key privateKey = getKey();
	    	// get an RSA cipher object and print the provider
	    	final Cipher cipher = Cipher.getInstance(ALGO);
	    	// encrypt the plain text using the public key
	    	cipher.init(Cipher.DECRYPT_MODE, privateKey);
	    	decryptedText = cipher.doFinal(pwd.getBytes());
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return new String(decryptedText);
	}
	
	private Key getKey(){
		byte[] privateKey;
		try {
			privateKey = Cryptography.getInstance().getSalt().getBytes("UTF-8");
			privateKey = Arrays.copyOf(privateKey, 16); // use only first 128 bit
			Key key = new SecretKeySpec(privateKey, ALGO); 
			return key;
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}*/
}
