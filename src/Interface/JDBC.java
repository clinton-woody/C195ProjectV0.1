package Interface;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 This is the JDBC class.  This controls the connection with the database.
 */
public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection conn;  // Connection Interface

    /**
     *This is the openConnection method.  This method brokers the interface between the program and the database and
     *returns connection as conn.
     * @return Returns connection as conn.
     */
    public static Connection openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            conn = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
        return conn;//had to change this from return null to return connection so that setStatement() would work
    }

    /**
     *This is the closeConnection method.  This method closes the conn connection.
     */
    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
