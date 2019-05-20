package resourceFunctions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dconnection {

    public Connection getCon() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library" , "root" , "administrator");
        return con;
    }
}
