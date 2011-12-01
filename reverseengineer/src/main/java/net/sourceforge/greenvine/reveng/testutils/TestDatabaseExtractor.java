package net.sourceforge.greenvine.reveng.testutils;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import net.sourceforge.greenvine.dbextractor.DatabaseExtractor;
import net.sourceforge.greenvine.dbextractor.DatabaseExtractorException;
import net.sourceforge.greenvine.dbextractor.impl.JdbcDatabaseExtractorConfig;
import net.sourceforge.greenvine.dbextractor.impl.JdbcDatabaseExtractorImpl;
import net.sourceforge.greenvine.dbextractor.testutils.DatabaseExtractorConfigFactory;
import net.sourceforge.greenvine.dbextractor.testutils.H2DatabaseUtil;
import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;
import net.sourceforge.greenvine.model.naming.testutils.NamingConventionFactory;

public class TestDatabaseExtractor {
    
    public static Database extractTestDatase(String schemaFile) throws FileNotFoundException, SQLException, ClassNotFoundException, DatabaseExtractorException {
        
        // Create schema
        H2DatabaseUtil.createDatabase(schemaFile, "GREENVINE_DB");
        
        // Get DatabaseExtractorConfig
        JdbcDatabaseExtractorConfig config = DatabaseExtractorConfigFactory.getTestDatabaseExtractorConfig();
        
        // Get DatabaseNamingConvention
        RdbmsNamingConventions conventions = NamingConventionFactory.getTestNamingConvention();
        
        // Extract database
        DatabaseExtractor dbExtractor = new JdbcDatabaseExtractorImpl(config, conventions);
        return dbExtractor.extractDatabase();
    }

}
