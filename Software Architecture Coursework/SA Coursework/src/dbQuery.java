import java.sql.*;

public class dbQuery 
{
	public static void query () //query()
	{
		try
		{
			// Load the driver
			Class.forName("com.mysql.jdbc.Driver");
			// First we need to establish a connection to the database
			Connection conn = DriverManager
			          .getConnection("jdbc:mysql://localhost/patients?user=Java&password=Java");
			// Next we create a statement to access the database
			Statement statement = conn.createStatement();
			// Now create a simple query to get all records from the database
			String query = "SELECT * FROM PatientRecords";
			// And then get the results from executing the query
			ResultSet results = statement.executeQuery(query);
			// Loop until no records are left
			while (results.next())
			{
				// Retrieve each field of the currently selected record
				String patName = results.getString("PatientName");
				String patNHSNo = results.getString("PatientNHSNo");
				String patAddress = results.getString("PatientAddress");
				String patCondition = results.getString("PatientCondition");
				// Display the student details
				System.out.println(patName);
				System.out.println(patNHSNo);
				System.out.println(patAddress);
				System.out.println(patCondition);
				System.out.println();
			}
			// Release resources held by statement
			statement.close();
			// Release resources held by DB connection
			conn.close();
		}
		catch (ClassNotFoundException cnf)
		{
			System.err.println("Could not load driver");
			System.err.println(cnf.getMessage());
			System.exit(-1);
		}
		catch (SQLException sqe)
		{
			System.out.println("Error performing SQL Query");
			System.out.println(sqe.getMessage());
			System.exit(-1);
		}
	}
}
