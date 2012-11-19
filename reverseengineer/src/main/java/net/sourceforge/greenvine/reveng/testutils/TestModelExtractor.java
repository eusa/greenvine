package net.sourceforge.greenvine.reveng.testutils;

import java.io.IOException;
import java.sql.SQLException;

import net.sourceforge.greenvine.dbextractor.DatabaseExtractorException;
import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.Model;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.reveng.impl.RevengConfig;
import net.sourceforge.greenvine.reveng.impl.ReverseEngineerImpl;

public class TestModelExtractor {

    public static Model getH2Model(String schemaFile) throws ModelException, SQLException, ClassNotFoundException, DatabaseExtractorException, IOException {
        // Create test database
        Database dbs = TestDatabaseExtractor.extractH2Database(schemaFile);
        
        // Set up RevengConfig
        ReverseEngineerImpl reveng = getReverseEngineer();
        
        // Reverse engineer
        return reveng.reverseEngineer(dbs);
    }

    public static Model getMySqlModel(String schemaFile) throws ModelException, SQLException, ClassNotFoundException, DatabaseExtractorException, IOException {
        // Create test database
        Database dbs = TestDatabaseExtractor.extractMySqlDatabase(schemaFile);
        
        // Set up RevengConfig
        ReverseEngineerImpl reveng = getReverseEngineer();
        
        // Reverse engineer
        return reveng.reverseEngineer(dbs);
    }
    
	private static ReverseEngineerImpl getReverseEngineer() {
		RevengConfig revengConfig = new RevengConfig();
        revengConfig.setModelName("model");
        revengConfig.setCreateNaturalIdentities(false);
        
        // Create ReverseEngineer
        ReverseEngineerImpl reveng = new ReverseEngineerImpl(revengConfig);
		return reveng;
	}
}
