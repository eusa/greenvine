package net.sourceforge.greenvine.dbextractor.testutils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.h2.util.ScriptReader;

public class H2DatabaseUtil {
    
    // Note it is necessary to add the db close delay
    // parameter if you close the connection. That's because
    // H2 closes the database after the last connection
    // closes.
    private static final String BASE_JDBC_URL = "jdbc:h2:mem:~/";
    private static final String DB_DELAY_URL = ";DB_CLOSE_DELAY=-1";
    private static final String JDBC_DRIVER = "org.h2.Driver";
    
    // TODO switch to use ScriptRunner utility
    
    public static void createDatabase(String schemaFile, String catalog)
            throws FileNotFoundException, SQLException, ClassNotFoundException {
        
        // Get database connection
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(BASE_JDBC_URL + catalog + DB_DELAY_URL);
        try {
            File ddlFile = new File(schemaFile);
            Reader reader = new FileReader(ddlFile);
            ScriptReader scriptReader = new ScriptReader(reader);
            while (true) {
                String statement = scriptReader.readStatement();
                if (statement == null) {
                    break;
                } 
                PreparedStatement ps = connection.prepareStatement(statement);
                try {
                    ps.execute();
                }
                finally {
                    ps.close();
                }
            }
        }
        finally {
            // NB: Had to add db delay parameter
            // to allow manual closing - otherwise
            // database itself closes
            connection.close();
        }
    }
}
