package Multicast;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JFileChooser;

import com.google.gson.Gson;

public class RSAModule {

	//for better security !
	
	private Gson gson;
	private Cipher cipher;
	
	public RSAModule() {
		this.gson = new Gson();
		try {
			this.cipher = Cipher.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}
	
	//(this method is for the multicast server)
	//generates a pair of keys (one public, one private) and calls upon exportKeyToFile (only exports the PUBLIC key) | returns PRIVATE key only
	public PrivateKey generateKeys() throws NoSuchAlgorithmException, IOException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(2048); //key size in bits
		KeyPair pair = keyGen.generateKeyPair();
		
		//exporting public key
		exportKeyToFile(pair.getPublic());
		
		return pair.getPrivate();
	}
	
	//converts key to JSON file and opens the file explorer
	public void exportKeyToFile(PublicKey key) throws IOException {
			
		JFileChooser chooser = new JFileChooser();
			
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			try {
				FileWriter file = new FileWriter(chooser.getSelectedFile()+".json");
			    file.write(gson.toJson(key.getEncoded()));
			    file.close();
			} catch (IOException err) {
			    err.printStackTrace();
			}
		}
			
	}
		
	//requests JSON file to convert into a key it returns
	public PublicKey importKeyFromFile() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
	
		//select file
		JFileChooser chooser = new JFileChooser();
	    chooser.showOpenDialog(null);
	        
	    Path pathOfFile = Path.of(chooser.getSelectedFile().toString());
		String temporaryFileString = Files.readString(pathOfFile);
			
		//java.lang.reflect.Type type = new TypeToken<HashMap<Integer,ArrayList<ArrayList<Integer>>>>(){}.getType();
			
		byte[] key = gson.fromJson(temporaryFileString, byte[].class);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(key);
		
		return keyFactory.generatePublic(publicKeySpec);
	}
	
	//encrypts message with a given PUBLIC key and sends it as an array of bytes converted to a string
	public String encryptMessage(String str, PublicKey key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] bytes = cipher.doFinal(str.getBytes());
		return gson.toJson(bytes);
	}
	
	//decrypts message (array of bytes converted to a string) with a given PRIVATE key and returns it as a string
	public String decryptMessage(String str, PrivateKey key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] bytes = gson.fromJson(str, byte[].class);
		return new String(cipher.doFinal(bytes));
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
