package net.sourceforge.greenvine.dbextractor.testutils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DatabaseUtil {
    
    // Note it is necessary to add the db close delay
    // parameter if you close the connection. That's because
    // H2 closes the database after the last connection
    // closes.
    private static final String BASE_JDBC_URL = "jdbc:h2:mem:~/";
    private static final String DB_DELAY_URL = ";DB_CLOSE_DELAY=-1";
    private static final String JDBC_DRIVER = "org.h2.Driver";
    
    
    public static void createDatabase(String schemaFile, String catalog)
            throws SQLException, ClassNotFoundException, IOException {
        
        // Get database connection
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(BASE_JDBC_URL + catalog + DB_DELAY_URL);
        try {
        	
        	ScriptRunner runner = new ScriptRunner(connection, false, true);
        	runner.runScript(new BufferedReader(new FileReader(schemaFile)));
        	
        }
        finally {
            // NB: Had to add db delay parameter
            // to allow manual closing - otherwise
            // database itself closes
            connection.close();
        }
    }
}
