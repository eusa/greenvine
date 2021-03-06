package net.sourceforge.greenvine.model.api.impl;

import java.util.SortedSet;

import net.sourceforge.greenvine.model.api.AggregationRelation;
import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.ManyToOneAggregationField;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.ColumnName;

/**
 * Many-to-one field that forms
 * part of a one-to-many relation
 * between two entities
 * @author patrick
 *
 */
public class ManyToOneFieldImpl extends AbstractAggregationRelationField implements ManyToOneAggregationField {

    private final AggregationRelation manyToOne;
    
    public ManyToOneFieldImpl(CharSequence name, EntityImpl entity, AggregationRelation oneToOne)
            throws ModelException {
        super(name, entity, oneToOne);
        
        this.manyToOne = oneToOne;
        
        // Add to parent entity
        entity.addField(this);

    }
    
    public Entity getFieldCollection() {
        return this.entity;
    }

    public AggregationRelation getRelation() {
        return this.manyToOne;
    }

    public SortedSet<? extends Column> getColumns() {
        return this.manyToOne.getForeignKey().getReferencingColumns();
    }
    
    public SortedSet<ColumnName> getColumnNames() {
        return this.manyToOne.getForeignKey().getReferencingColumnNames();
    }

    public boolean getNotNull() {
        return !manyToOne.getForeignKey().areReferencingColumnsNullable();
    }

    public boolean isOwner() {
        return false;
    }
    
    public boolean isDependency() {
        return getNotNull();
    }
    
}
