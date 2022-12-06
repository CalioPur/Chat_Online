import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFileChooser;

//import com.google.gson.Gson;

//documentation https://howtodoinjava.com/java/java-security/java-aes-encryption-example/
// https://www.baeldung.com/java-secure-aes-key

public class AESModule {
	
	//generates a random key and calls upon the exportKeyToFile
	public void generateKey() {
		
	}
	
	//converts key to JSON file and opens the file explorer
	public void exportKeyToFile() {
		
	}
	
	//requests JSON file to convert into a key it returns
	public Key importKeyFromFile() throws IOException {
		
		//select file
		JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        
        Path pathOfFile = Path.of(chooser.getSelectedFile().toString());
		String temporaryFileString = Files.readString(pathOfFile);
		
		//Gson gson = new Gson();
		
		//java.lang.reflect.Type type = new TypeToken<HashMap<Integer,ArrayList<ArrayList<Integer>>>>(){}.getType();
		
		//return gson.fromJson(temporaryFileString, Key.class);
		return null;
	}
	
	public static void main(String[] args) {

	}

}
