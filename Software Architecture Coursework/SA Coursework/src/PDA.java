import java.io.File;
import java.util.ArrayList;

public class PDA {
	
	public static void receiveHospNo(int hospNo){
		
		// Get list of requests assigned to hospital
		ArrayList<String> files = new ArrayList<String>();
		
		File folder = new File("src\\req\\" + Integer.toString(hospNo));
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        files.add(file.getName());
		    }
		}
		
		// Send to be displayed
		GUIpda.pdaNext(files, hospNo);
	    	
	}
	

}
