package Interface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


/**
 This is the DBQuery class.  This class allows the program to pass statements to the database.
 */
public class DBQuery {

    private static PreparedStatement statement; // Statement reference

    /**
     * This is the setPreparedStatement method.  This is the setter method.
     * @param conn
     * @param sqlStatement
     * @throws SQLException
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement);
    }

    /**
     * This is the getPreparedStatement method.  This is a getter method.
     * @return Returns a SQL statement
     */
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }
}



