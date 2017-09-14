import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dbInsertCallOut
{

	public static void insert(String NHSno, String name, String condition, String date, String time, String location, String actionTaken, String timeSpent)
	{	
		try
		{
			// Load the driver
			Class.forName("com.mysql.jdbc.Driver");
			// First we need to establish a connection to the database
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/patients?user=Java&password=Java");

			// Create a new SQL statement
			Statement statement = conn.createStatement();
			// Build the INSERT statement
			String update = "INSERT INTO calloutdetails (NHSNo, PatientName, pCondition, Date, Time, LocationOfAccident, ActionTaken, TimeSpent, AmbulanceNo) " +
							"VALUES ('" + NHSno + "', '" + name + "', '" + condition + "', '" + date + "', '" + time +  "', '" + location + "', '"  + actionTaken + "', '" + timeSpent + "', '" + "23" +"')";
			
			// Execute the statement
			statement.executeUpdate(update);

			// Close
			conn.close();
			statement.close();
			System.out.println("Update successful");
		}
		catch (ClassNotFoundException cnf)
		{
			System.err.println("Could not load driver");
			System.err.println(cnf.getMessage());
			System.exit(-1);
		}

		catch (SQLException sqe)
		{
			System.err.println("Error performing SQL Update");
			System.err.println(sqe.getMessage());
			System.exit(-1);
		}
	}
}
