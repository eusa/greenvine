package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.Component;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;

public class ChildNameExtractor extends AbstractAggregationRelationFieldNameExtractor<ForeignKey, Component> {

    public ChildNameExtractor(RdbmsNamingConventions namingConventions) {
        super(namingConventions);
    }
    
    public FieldNameImpl extractName(Component container, ForeignKey source) throws ModelException {
        
        
        // Generate name
        CharSequence defaultName;
        if (!source.otherSimilarRelationsExist() && !source.isSelfReferencing()) {
            // Use default strategy (related table name)
            defaultName = getDefaultName(source);
        } else {
            // More than one matching key
            defaultName = getFallbackName(source);
        }
        return new FieldNameImpl(defaultName);
    }
    
    

    private CharSequence getDefaultName(ForeignKey source) throws ModelException {
        return extractNameFromRelatedTable(source.getReferencedTable());
    }
    
    private CharSequence getFallbackName(ForeignKey source)
            throws ModelException {
        CharSequence name;
        if (source.getColumnConstraintCount() == 1) {
            Column fkCol = source.getColumnConstraint(0).getReferencingColumn();
            name = extractNameFromColumn(fkCol);
        } else {
            name = extractNameFromForeignKey(source);
        }
        return name;
    }
    
}
