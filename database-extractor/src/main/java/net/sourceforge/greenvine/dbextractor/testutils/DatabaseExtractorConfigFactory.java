package net.sourceforge.greenvine.dbextractor.testutils;

import net.sourceforge.greenvine.dbextractor.impl.Catalog;
import net.sourceforge.greenvine.dbextractor.impl.IncludeAllStrategy;
import net.sourceforge.greenvine.dbextractor.impl.JdbcDatabaseExtractorConfig;

public class DatabaseExtractorConfigFactory {
    
    private static final String CATALOG = "GREENVINE_DB";
    private static final String JDBC_URL = "jdbc:h2:mem:~/" + CATALOG;
    private static final String JDBC_DRIVER = "org.h2.Driver";
    
    public static JdbcDatabaseExtractorConfig getTestDatabaseExtractorConfig() {
        JdbcDatabaseExtractorConfig config = new JdbcDatabaseExtractorConfig();
        
        
        // Create server
        Catalog server = new Catalog();
        config.setCatalog(server);
        
        // Create connection
        net.sourceforge.greenvine.dbextractor.impl.Connection con = new net.sourceforge.greenvine.dbextractor.impl.Connection();
        con.setJdbcDriver(JDBC_DRIVER);
        con.setJdbcUrl(JDBC_URL);
        con.setUsername(null);
        con.setPassword(null);
        server.setConnection(con);
        
        // Create catalog
        server.setName(CATALOG);
        
        // Create inclusions
        server.setInclusions(new IncludeAllStrategy());
        
        // Return completed configuration
        return config;
    }

}
