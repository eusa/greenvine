package net.sourceforge.greenvine.model.api.impl;

import java.util.SortedSet;

import net.sourceforge.greenvine.model.api.AggregationRelation;
import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.OneToOneChildField;
import net.sourceforge.greenvine.model.naming.ColumnName;

public class OneToOneChildFieldImpl extends AbstractAggregationRelationField implements OneToOneChildField {

    private final AggregationRelation oneToOne;
    
    public OneToOneChildFieldImpl(CharSequence name, EntityImpl entity, AggregationRelation oneToOne)
            throws ModelException {
        super(name, entity, oneToOne);
        
        this.oneToOne = oneToOne;
        
        // Add to parent entity
        entity.addField(this);

    }

    public Entity getFieldCollection() {
        return this.entity;
    }
    
    public AggregationRelation getRelation() {
        return this.oneToOne;
    }

    public SortedSet<? extends Column> getColumns() {
        return this.oneToOne.getForeignKey().getReferencingColumns();
    }
    
    public SortedSet<ColumnName> getColumnNames() {
        return this.oneToOne.getForeignKey().getReferencingColumnNames();
    }

    public boolean getNotNull() {
        return !oneToOne.getForeignKey().areReferencingColumnsNullable();
    }

    public boolean isOwner() {
        return false;
    }
    
    public boolean isDependency() {
        return getNotNull();
    }
    
}
