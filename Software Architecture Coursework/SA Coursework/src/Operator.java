public class Operator {
	
	private static Boolean exists;
	
	public static void operator(String NHSno, String name, String address, String condition, int location) {
		
		// Find out if patient exists in the database
		exists = FindPatient.findpatient(NHSno);
		
		// Tell the next window that patient exists
		GUIoperator.operatorNext(exists);
		
		// Allocate hospital
		Hospital.allocateHospital(location);

		
	}

}
