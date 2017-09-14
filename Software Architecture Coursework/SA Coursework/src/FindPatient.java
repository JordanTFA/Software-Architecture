
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FindPatient {
	
	public static Boolean exists;

	
	public static Boolean findpatient(String NHSno){
		
		try {

			// Load drivers
			Class.forName("com.mysql.jdbc.Driver");

			// Establish Connection
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/patients?user=Java&password=Java");
			

			Statement statement = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			// Generate query
			String query = "SELECT * FROM PatientRecords WHERE PatientNHSNo = '"
					+ NHSno + "'";

			// Use query to find results
			ResultSet results = statement.executeQuery(query);
			
			// Record has been found
			if (results.next()) {
				
				results.first();			
				
				setExists(true);
			} else {
				// Record has not been found
				setExists(false);

			}

			// Close
			statement.close();
			conn.close();
			
			
		} catch (ClassNotFoundException cnf) {
			System.err.println("Could not load driver");
			System.err.println(cnf.getMessage());
			System.exit(-1);
		} catch (SQLException sqe) {
			System.err.println("Error in SQL Update");
			System.err.println(sqe.getMessage());
			System.exit(-1);
		}
		
		return exists;
	}


	public static Boolean getExists() {
		return exists;
	}


	public static void setExists(Boolean exists) {
		FindPatient.exists = exists;
	}
}