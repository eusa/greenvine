package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.Component;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;

public class ManyToOneAssociationNameExtractor extends AbstractAssociationRelationFieldNameExtractor {

    public ManyToOneAssociationNameExtractor(RdbmsNamingConventions namingConventions) throws ModelException {
        super(namingConventions);
    }
    
    public FieldNameImpl extractName(Component container, ForeignKey source) throws ModelException {
        
        CharSequence simple;
        if (detectSelfAssociation(source)) {
            // Use the name of the foreign key
            simple = extractNameFromForeignKey(source);
        } else if (detectSimilarAssociations(source)) {
            // Use the name of the join table 
            simple = extractNameFromTable(source.getReferencingTable());
        } else {
            // Use the name of the mapped entity's table
            simple = extractNameFromTable(source.getReferencedTable());
        }
        
        return new FieldNameImpl(simple);
    }

}
