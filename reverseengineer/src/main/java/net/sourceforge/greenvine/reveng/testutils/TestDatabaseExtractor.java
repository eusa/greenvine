package net.sourceforge.greenvine.reveng.testutils;

import java.io.IOException;
import java.sql.SQLException;

import net.sourceforge.greenvine.dbextractor.DatabaseExtractor;
import net.sourceforge.greenvine.dbextractor.DatabaseExtractorException;
import net.sourceforge.greenvine.dbextractor.impl.JdbcDatabaseExtractorConfig;
import net.sourceforge.greenvine.dbextractor.impl.JdbcDatabaseExtractorImpl;
import net.sourceforge.greenvine.dbextractor.testutils.DatabaseExtractorConfigFactory;
import net.sourceforge.greenvine.dbextractor.testutils.H2DatabaseUtil;
import net.sourceforge.greenvine.dbextractor.testutils.MySqlDatabaseUtil;
import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;
import net.sourceforge.greenvine.model.naming.testutils.NamingConventionFactory;

public class TestDatabaseExtractor {

	private static final String CATALOG = "GREENVINE_DB";

	public static Database extractH2Database(String schemaFile) throws SQLException, ClassNotFoundException, DatabaseExtractorException, IOException {

		// Create schema
		H2DatabaseUtil.createDatabase(schemaFile, CATALOG);

		// Get DatabaseExtractorConfig
		JdbcDatabaseExtractorConfig config = DatabaseExtractorConfigFactory.getH2Configuration();

		// Get DatabaseNamingConvention
		RdbmsNamingConventions conventions = NamingConventionFactory.getTestNamingConvention();

		// Extract database
		DatabaseExtractor dbExtractor = new JdbcDatabaseExtractorImpl(config, conventions);
		return dbExtractor.extractDatabase();
	}

	public static Database extractMySqlDatabase(String schemaFile) throws SQLException, ClassNotFoundException, DatabaseExtractorException, IOException {

		// Create schema
		MySqlDatabaseUtil.createDatabase(schemaFile, CATALOG);

		// Get DatabaseExtractorConfig
		JdbcDatabaseExtractorConfig config = DatabaseExtractorConfigFactory.getMySqlConfiguration();

		// Get DatabaseNamingConvention
		RdbmsNamingConventions conventions = NamingConventionFactory.getTestNamingConvention();

		// Extract database
		DatabaseExtractor dbExtractor = new JdbcDatabaseExtractorImpl(config, conventions);
		return dbExtractor.extractDatabase();
	}

}
