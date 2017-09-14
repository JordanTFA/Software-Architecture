import java.sql.*;

public class dbUpdate {
	public static void update(String NHSno, String name, String address, String condition){
		try {

			// Load drivers
			Class.forName("com.mysql.jdbc.Driver");

			// Establish connection
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/patients?user=Java&password=Java");

			// Create new SQL statement
			Statement statement = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			// Generate query
			String query = "SELECT * FROM PatientRecords WHERE PatientNHSNo = '"
					+ NHSno + "'";

			// Execute query
			ResultSet results = statement.executeQuery(query);

			if (results.next()) {

				results.first();

				// Update data
				results.updateString("PatientNHSno", NHSno);
				results.updateString("PatientName", name);
				results.updateString("PatientAddress", address);
				results.updateString("PatientCondition", condition);

				results.updateRow();
				System.out.println("Record updated");
			} else {

				System.out.println("Record does not exist");
			}

			// Free resources
			statement.close();
			conn.close();
		} catch (ClassNotFoundException cnf) {
			System.err.println("Could not load driver");
			System.err.println(cnf.getMessage());
			System.exit(-1);
		}catch (SQLException sqe) {
			System.err.println("Error in SQL Update");
			System.err.println(sqe.getMessage());
			System.exit(-1);
		}
	}
}
