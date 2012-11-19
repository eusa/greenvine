package net.sourceforge.greenvine.dbextractor.testutils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlDatabaseUtil {
    
    private static final String BASE_JDBC_URL = "jdbc:mysql://";
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    
    
    public static void createDatabase(String schemaFile, String catalog)
            throws SQLException, ClassNotFoundException, IOException {
        
        // Get database connection
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(BASE_JDBC_URL + "/mysql", "root", "");
        try {
        	Statement statement = connection.createStatement();
        	String delete = "DROP DATABASE IF EXISTS " + catalog;
        	statement.executeUpdate(delete);
            String create = "CREATE DATABASE " + catalog;
            statement.executeUpdate(create);
            connection.setCatalog(catalog);
            
        	ScriptRunner runner = new ScriptRunner(connection, false, true);
        	runner.runScript(new BufferedReader(new FileReader(schemaFile)));
        }
        finally {
            connection.close();
        }
    }
}
