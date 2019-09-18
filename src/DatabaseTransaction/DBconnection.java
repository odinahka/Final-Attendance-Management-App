package DatabaseTransaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by odinahka on 9/7/2019.
 */
public class DBconnection {

    public static Connection Dbconnect()
    {
        Connection conn;
        try{

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:AttendanceDatabase.sqlite");
            System.out.println("Connection to SQLite has been established");

            return conn;
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
