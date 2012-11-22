package net.sourceforge.greenvine.model.api;

/** 
 * This class is a convenient
 * holder for "to-do" items.
 * 
 * @author patrick
 *
 */
public interface Notes {
    
	// TODO set build encoding to UTF-8 on POMs
	// TODO minor bugfixes
    // Fix bug in DBUnit comparisons for binary data types and remove @Ignore attribute in integration tests
    // Add support for ARRAY and OTHER JDBC types
    // Use the Builder pattern approach taken for ComponentIdentity on ComponentNaturalIdentity etc 
    
	// TODO naming improvements
	// Refactor the naming classes - too complex
	// Add table/column/foreign-key overrides to reveng.xml to control names and types of generated fields
	// Handle reserved java keywords in name extractors
	// If a field in an entity has the same name as the entity, this could cause issues in some generated classes
	
	// TODO simplifications
	// Get rid of Hibernate
	// Get rid of DAOs
	// Get rid of unneeded integration tests
	// Get rid of NaturalIdentities
	// Database name needs to be set in 3 locations - should just be once.
	
}
