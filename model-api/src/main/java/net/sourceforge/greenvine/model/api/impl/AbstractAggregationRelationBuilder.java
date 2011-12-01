package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.DatabaseObjectName;
import net.sourceforge.greenvine.model.naming.FieldName;
import net.sourceforge.greenvine.model.naming.impl.DatabaseObjectNameImpl;
import net.sourceforge.greenvine.model.naming.impl.FieldNameImpl;

public abstract class AbstractAggregationRelationBuilder {

    protected FieldName ownerField;
    protected FieldName inverseField;
    protected DatabaseObjectName foreignKey;
    protected CatalogImpl catalog;
    
    public AbstractAggregationRelationBuilder setOwnerField(CharSequence name) throws ModelException {
        this.ownerField = new FieldNameImpl(name);
        return this;
    }

    public AbstractAggregationRelationBuilder setInverseField(CharSequence inverseName) throws ModelException {
        this.inverseField = new FieldNameImpl(inverseName);
        return this;
    }

    public AbstractAggregationRelationBuilder setForeignKey(CharSequence foreignKey) throws ModelException {
        this.foreignKey = DatabaseObjectNameImpl.parse(foreignKey);
        return this;
    }
    
    public AbstractAggregationRelationBuilder setCatalog(CatalogImpl catalog) {
        this.catalog = catalog;
        return this;
    }

    public CharSequence getOwnerField() {
        return ownerField;
    }

    public FieldName getInverseField() {
        return inverseField;
    }

    public CharSequence getForeignKey() {
        return foreignKey;
    }
    
    public CatalogImpl getCatalog() {
        return catalog;
    }
    
    

}
