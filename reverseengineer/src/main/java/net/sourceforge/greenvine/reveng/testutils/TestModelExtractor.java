package net.sourceforge.greenvine.reveng.testutils;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import net.sourceforge.greenvine.dbextractor.DatabaseExtractorException;
import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.Model;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.reveng.impl.RevengConfig;
import net.sourceforge.greenvine.reveng.impl.ReverseEngineerImpl;

public class TestModelExtractor {

    public static Model getTestModel(String schemaFile) throws ModelException, FileNotFoundException, SQLException, ClassNotFoundException, DatabaseExtractorException {
        // Create test database
        Database dbs = TestDatabaseExtractor.extractTestDatase(schemaFile);
        
        // Set up RevengConfig
        RevengConfig revengConfig = new RevengConfig();
        revengConfig.setModelName("model");
        revengConfig.setCreateNaturalIdentities(false);
        
        // Create ReverseEngineer
        ReverseEngineerImpl reveng = new ReverseEngineerImpl(revengConfig);
        
        // Reverse engineer
        return reveng.reverseEngineer(dbs);
    }
}
