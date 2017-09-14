import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Hospital {
	
	static ArrayList<Hospital> h = new ArrayList<Hospital>();
	static ArrayList<Integer> hNo = new ArrayList<Integer>();

	// Generate hospitals
	public static void generateHospitals(){
		
		getH();
		
		for(int i = 0; i < 20; i++){
			h.add(	new Hospital("Hospital " + Integer.toString((i)),	i * 5)	);
		}

	}
	
	// Constructor
	public Hospital(String name, int hospNumber){
		
		hNo.add(hospNumber);
	}
	
	// Allocate hospital
	public static void allocateHospital(int location){
		
		gethNo();
		
		ArrayList<Integer> distances = new ArrayList<Integer>();
		
		for(int i = 0; i < hNo.size(); i++){
			distances.add(Math.abs(hNo.get(i) - location));
		}
		
		int closest = (distances.indexOf(Collections.min(distances))+1);
		
		generateRequest(closest);
		
		
	}
	
	// Generate request
	public static void generateRequest(int assignedHospital){
		

		try {
			
		       DateFormat df = new SimpleDateFormat("ddMMyy_HHmm");
		       Date date = new Date();
			
		       File path = new File("src\\req", Integer.toString(assignedHospital));
		       File file = new File("src\\req\\" + Integer.toString(assignedHospital) + "\\" + Integer.toString(assignedHospital) + "_" + df.format(date) + ".txt");

		       if(!path.exists()){
		    	   path.mkdirs();
		       }
		       
		       FileWriter fw = new FileWriter (file.getAbsoluteFile());
		       BufferedWriter bw = new BufferedWriter(fw);
			
		       System.out.print("Writing...");
			
			
		       bw.write(GUIoperator.getNHSno() + "," + GUIoperator.getName() + "," + GUIoperator.getAddress() + "," + GUIoperator.getCondition());
		       bw.newLine();
		       bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	// Close Request TODO: Not fully implemented
	public static void closeRequest(String selected, int hospNo){
		
		
		File folder = new File("src\\req\\" + Integer.toString(hospNo));
		File newFile = new File(folder + "CLOSED_" + selected);
		
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
		    if (file.equals(folder + "\\" + selected)) {
		        file.renameTo(newFile);
		    }
		}
		
	}
	
	public static ArrayList<Hospital> getH() {
		return h;
	}

	public void setH(ArrayList<Hospital> h) {
		Hospital.h = h;
	}
	
	public static ArrayList<Integer> gethNo() {
		return hNo;
	}

	public void sethNo(ArrayList<Integer> hNo) {
		Hospital.hNo = hNo;
	}

}
