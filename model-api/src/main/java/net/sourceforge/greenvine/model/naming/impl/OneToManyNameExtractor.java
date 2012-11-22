package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.Component;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.CamelCaseNameSegmentBuilder;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;

public class OneToManyNameExtractor extends ParentNameExtractor {

    public OneToManyNameExtractor(RdbmsNamingConventions namingConventions) {
        super(namingConventions);
    }
    
    public FieldNameImpl extractName(Component container, ForeignKey source) throws ModelException {
        
        // Generate name
    	CharSequence name;
        CharSequence defaultName = getDefaultName(source);
        if (!source.otherSimilarRelationsExist()) {
            // Use default strategy (related table name)
            name = defaultName;
        } else {
            // More than one matching key
            name = getFallbackName(source);
        }
       
        return new FieldNameImpl(name);
    }   
    
    private CharSequence getDefaultName(ForeignKey source) throws ModelException {
        CharSequence fname = extractNameFromRelatedTable(source.getReferencingTable());
        CamelCaseNameSegmentBuilder builder = new CamelCaseNameSegmentBuilderImpl(fname);
        builder.depluralise(); // In case already a plural
        return builder.pluralise();
    }

    private CharSequence getFallbackName(ForeignKey source) throws ModelException {
        if (source.getColumnConstraintCount() == 1) {
            Column fkCol = source.getColumnConstraint(0).getReferencingColumn();
            CamelCaseNameSegmentBuilderImpl builder = new CamelCaseNameSegmentBuilderImpl(extractNameFromColumn(fkCol));
            builder.appendCamelCase(extractNameFromRelatedTable(source.getReferencingTable()));
            return builder.pluralise();
        } else {
            CamelCaseNameSegmentBuilderImpl builder = new CamelCaseNameSegmentBuilderImpl(extractNameFromForeignKey(source));
            return builder.pluralise();
        }
    }
}
