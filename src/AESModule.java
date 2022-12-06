import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;

import com.google.gson.Gson;

//documentation https://howtodoinjava.com/java/java-security/java-aes-encryption-example/
// https://www.baeldung.com/java-secure-aes-key

//cipher : https://jenkov.com/tutorials/java-cryptography/cipher.html

public class AESModule {
	
	private Gson gson;
	
	public AESModule() {
		this.gson = new Gson();
	}
	
	//generates a random key and calls upon exportKeyToFile
	public void generateKey() throws NoSuchAlgorithmException, IOException {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		Key key = keyGen.generateKey();
		
		exportKeyToFile(key);
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
		
		/*FileWriter file = new FileWriter(chooser.getSelectedFile()+".json");
        file.write(gson.toJson(key.getEncoded()));
        file.close();*/
		
	}
	
	//requests JSON file to convert into a key it returns
	public byte[] importKeyFromFile() throws IOException {
		
		//select file
		JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        
        Path pathOfFile = Path.of(chooser.getSelectedFile().toString());
		String temporaryFileString = Files.readString(pathOfFile);
		
		//java.lang.reflect.Type type = new TypeToken<HashMap<Integer,ArrayList<ArrayList<Integer>>>>(){}.getType();
		
		return gson.fromJson(temporaryFileString, byte[].class);
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		AESModule aes = new AESModule();
		//aes.generateKey(); -> ok
		byte[] key = aes.importKeyFromFile();
		SecretKey k = new SecretKeySpec(key,"AES");
	}

}
