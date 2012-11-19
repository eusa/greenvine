package net.sourceforge.greenvine.dbextractor.testutils;

import net.sourceforge.greenvine.dbextractor.impl.Catalog;
import net.sourceforge.greenvine.dbextractor.impl.Connection;
import net.sourceforge.greenvine.dbextractor.impl.IncludeAllStrategy;
import net.sourceforge.greenvine.dbextractor.impl.JdbcDatabaseExtractorConfig;

public class DatabaseExtractorConfigFactory {

	private static final String CATALOG = "GREENVINE_DB";

	public static JdbcDatabaseExtractorConfig getH2Configuration() {

		// JDBC data
		final String JDBC_URL = "jdbc:h2:mem:~/" + CATALOG;
		final String JDBC_DRIVER = "org.h2.Driver";

		// Create configuration
		JdbcDatabaseExtractorConfig config = createConfig(JDBC_URL, JDBC_DRIVER, null, null);

		// Return completed configuration
		return config;
	}

	public static JdbcDatabaseExtractorConfig getMySqlConfiguration() {

		// JDBC data
		final String JDBC_URL = "jdbc:mysql:///" + CATALOG;
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

		// Create configuration
		JdbcDatabaseExtractorConfig config = createConfig(JDBC_URL, JDBC_DRIVER, "root", "");

		// Return completed configuration
		return config;
	}

	private static JdbcDatabaseExtractorConfig createConfig(
			final String JDBC_URL, final String JDBC_DRIVER, final String username, final String password) {
		JdbcDatabaseExtractorConfig config = new JdbcDatabaseExtractorConfig();

		// Create connection
		Connection con = new Connection();
		con.setJdbcDriver(JDBC_DRIVER);
		con.setJdbcUrl(JDBC_URL);
		con.setUsername(username);
		con.setPassword(password);

		// Create server
		Catalog server = new Catalog();
		config.setCatalog(server);
		server.setConnection(con);

		// Create catalog
		server.setName(CATALOG);

		// Create inclusions
		server.setInclusions(new IncludeAllStrategy());
		return config;
	}

}
