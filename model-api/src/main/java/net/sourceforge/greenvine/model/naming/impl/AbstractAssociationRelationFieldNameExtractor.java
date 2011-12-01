package net.sourceforge.greenvine.model.naming.impl;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.greenvine.model.api.Component;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.naming.CamelCaseNameSegmentBuilder;
import net.sourceforge.greenvine.model.naming.NameExtractor;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;

public abstract class AbstractAssociationRelationFieldNameExtractor implements NameExtractor<ForeignKey,Component> {

    protected final RdbmsNamingConventions namingConventions;
    
    public AbstractAssociationRelationFieldNameExtractor(RdbmsNamingConventions namingConventions) {
        this.namingConventions = namingConventions;
    }
    
    protected boolean detectSimilarAssociations(ForeignKey source) {
        Table joiner = source.getReferencingTable();
        List<? extends Table> joins = source.getDatabase().getAssociationTables();
        int matches = 0;
        for (Table join : joins) {
            if (!join.equals(joiner)) {
                List<Table> joined = getJoinedTables(join);
                if (joined.equals(getJoinedTables(joiner))) {
                    matches ++;
                }
            }
        }
        return matches > 0;
    }
    
    protected List<Table> getJoinedTables(Table join) {
        List<Table> joined = new ArrayList<Table>();
        for (ForeignKey key : join.getImportedForeignKeys()) {
            joined.add(key.getReferencedTable());
        }
        return joined;
    }
    
    protected boolean detectSelfAssociation(ForeignKey source) {
        Table joiner = source.getReferencingTable();
        assert(joiner.getImportedForeignKeyCount() == 2); 
        ForeignKey one = joiner.getImportedForeignKey(0);
        ForeignKey two = joiner.getImportedForeignKey(1);
        if (one.getReferencedTable().equals(two.getReferencedTable())) {
            return true;
        } else {
            return false;
        }
    }
    
    protected CharSequence extractNameFromTable(Table source) throws ModelException {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(namingConventions.getTable());
        return helper.extractModelName(source.getName().getObjectName());
    }
    
    protected CharSequence extractNameFromForeignKey(ForeignKey source) throws ModelException {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(namingConventions.getForeignKey());
        return helper.extractModelName(source.getName().getObjectName());
    }
    
    protected CharSequence extractPluralNameFromTable(Table source) throws ModelException {
        CamelCaseNameSegmentBuilder builder = new CamelCaseNameSegmentBuilderImpl(extractNameFromTable(source));
        return builder.pluralise();
    }
    
    protected CharSequence extractPluralNameFromForeignKey(ForeignKey source) throws ModelException {
        CamelCaseNameSegmentBuilder builder = new CamelCaseNameSegmentBuilderImpl(extractNameFromForeignKey(source));
        return builder.pluralise();
    }
    
}
