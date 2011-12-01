package net.sourceforge.greenvine.dbextractor;

import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.impl.DatabaseImpl;

public interface DatabaseExtractor {

	/**
	 * Main method to extract a {@link DatabaseImpl}
	 * object from a JDBC database based on the
	 * configuration supplied in the 
	 * constructor
	 * @return
	 * @throws DatabaseExtractorException 
	 */
	public Database extractDatabase() throws DatabaseExtractorException;

}