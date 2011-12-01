package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.NameContainer;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;

public class IdentityFieldForeignColumnNameExtractor extends AbstractNameExtractor<Column, NameContainer> {

   
    public IdentityFieldForeignColumnNameExtractor(RdbmsNamingConventions namingConventions) throws ModelException {
        super(namingConventions);
    }
    
    public FieldNameImpl extractName(NameContainer container, Column source) throws ModelException {
        
        // Create the name utility
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(namingConventions.getForeignColumn());
        
        // Remove all non-alphanumeric characters (except separator)
        CharSequence strip = helper.stripAllNonAlphanumeric(source.getName());
        
        // Strip off the prefix, suffix and separator
        strip = helper.removePrefixIncludingSeparator(strip);
        
        // Convert to camel case
        strip = helper.convertToCamelCase(strip);
        
        // Return a field name
        return new FieldNameImpl(strip);
        
    }

}
