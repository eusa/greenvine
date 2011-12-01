package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.Component;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;

public class ManyToManyNameExtractor extends AbstractAssociationRelationFieldNameExtractor {

    public ManyToManyNameExtractor(RdbmsNamingConventions namingConventions) throws ModelException {
        super(namingConventions);
    }
    
    public FieldNameImpl extractName(Component container, ForeignKey source) throws ModelException {
        
        CharSequence simple;
        if (detectSelfAssociation(source)) {
            // Use the name of the foreign key
            simple = extractPluralNameFromForeignKey(source);
        } else if (detectSimilarAssociations(source)) {
            // Use the name of the foreign 
            simple = extractPluralNameFromTable(source.getReferencingTable());
        } else {
            // Use the name of the mapped entity's table by default
            simple = extractPluralNameFromTable(source.getReferencedTable());
        }
        
        return new FieldNameImpl(simple);
        
    }
    
}
