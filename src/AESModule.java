import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;

import com.google.gson.Gson;

public class AESModule {
	
	private Gson gson;
	private Cipher cipher;
	
	public AESModule() {
		this.gson = new Gson();
		
		try {
			this.cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}
	
	//generates a random key and calls upon exportKeyToFile
	public Key generateKey() throws NoSuchAlgorithmException, IOException {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		Key key = keyGen.generateKey();
		
		exportKeyToFile(key);
		
		return key;
	}
	
	//converts key to JSON file and opens the file explorer
	public void exportKeyToFile(Key key) throws IOException {
		
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
	public Key importKeyFromFile() throws IOException {
		
		//select file
		JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        
        Path pathOfFile = Path.of(chooser.getSelectedFile().toString());
		String temporaryFileString = Files.readString(pathOfFile);
		
		byte[] key = gson.fromJson(temporaryFileString, byte[].class);
		
		return new SecretKeySpec(key,"AES");
	}
	
	//encrypts message with a given key and sends it as an array of bytes converted to a string
	public String encryptMessage(String str, Key key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] bytes = cipher.doFinal(str.getBytes());
		return gson.toJson(bytes);
	}
	
	//decrypts message (array of bytes converted to a string) with a given key and returns it as a string
	public String decryptMessage(String str, Key key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] bytes = gson.fromJson(str, byte[].class);
		return new String(cipher.doFinal(bytes));
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		/*AESModule aes = new AESModule();
		Key key = aes.generateKey();*/
		//byte[] key = aes.importKeyFromFile();
		//SecretKey k = new SecretKeySpec(key,"AES");
		
		//crypting / decrypting test
		/*String init = "Hello world !";
		String cryptedInitString = aes.encryptMessageString(init, key);
		System.out.println("init : "+init+" | crypted string : "+cryptedInitString);
		System.out.println("decrypted through string : "+aes.decryptMessage(cryptedInitString, key));*/
	}

}
