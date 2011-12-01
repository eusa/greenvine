package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.NamedObject;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.naming.NameContainer;
import net.sourceforge.greenvine.model.naming.NameExtractor;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;

public abstract class AbstractAggregationRelationFieldNameExtractor<T extends NamedObject, U extends NameContainer> implements NameExtractor<T, U> {

    protected final RdbmsNamingConventions namingConventions;
    
    public AbstractAggregationRelationFieldNameExtractor(RdbmsNamingConventions namingConventions) {
        this.namingConventions = namingConventions;
    }
    
    protected CharSequence extractNameFromRelatedTable(Table name) throws ModelException {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(namingConventions.getTable());
        return new FieldNameImpl(helper.extractModelName(name.getName().getObjectName()).depluralise());
    }

    protected CharSequence extractNameFromColumn(Column name) throws ModelException {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(namingConventions.getForeignColumn());
        return new FieldNameImpl(helper.extractModelName(name));
    }
    
    protected CharSequence extractNameFromTable(Table name) throws ModelException {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(namingConventions.getTable());
        return new FieldNameImpl(helper.extractModelName(name.getName().getObjectName()));
    }
    
    protected CharSequence extractNameFromForeignKey(ForeignKey name) throws ModelException {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(namingConventions.getForeignKey());
        return new FieldNameImpl(helper.extractModelName(name.getName().getObjectName()));
    }
}
