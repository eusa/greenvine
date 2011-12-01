package net.sourceforge.greenvine.model.api;

/** 
 * This class is a convenient
 * holder for "to-do" items.
 * 
 * @author patrick
 *
 */
public interface Notes {
    
    // TODO fix bug in DBUnit comparisons for binary data types and remove @Ignore attribute in integration tests
    // TODO [minor] add support for ARRAY and OTHER JDBC types
    // TODO [minor] Use the Builder pattern approach taken for ComponentIdentity on ComponentNaturalIdentity etc 
    // TODO [minor] add table/column/foreign-key overrides to reveng.xml to control names and types of generated fields

	// TODO Handle reserved java keywords in name extractors
	// TODO if a field in an entity has the same name as the entity, this could cause issues in some generated classes
	// TODO Make NaturalIdentities optional, defaulting to false
	
}
