package net.sourceforge.greenvine.model.api.impl;

import java.util.SortedSet;

import net.sourceforge.greenvine.model.api.AggregationRelation;
import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ComponentField;
import net.sourceforge.greenvine.model.api.ManyToOneAggregationField;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.MutableComponentField;
import net.sourceforge.greenvine.model.naming.ColumnName;

/**
 * Many-to-one field that forms
 * part of a one-to-many relation
 * between an identity (natural or
 * primary) of one entity and 
 * another entity.
 * 
 * @author patrick
 *
 */
public class ManyToOneIdentityField extends AbstractAggregationRelationField implements ManyToOneAggregationField {

    private final AggregationRelation manyToOne;
    private final MutableComponentField identityComponent;
    
    public ManyToOneIdentityField(CharSequence name, MutableComponentField component, AggregationRelation oneToOne)
            throws ModelException {
        super(name, component.getFieldCollection(), oneToOne);
        
        this.manyToOne = oneToOne;
        this.identityComponent = component;
        
        // Check the columns are not already in the component
        if (component.getColumns().containsAll(getColumns())) {
            throw new ModelException(String.format("Component %s already contains columns %s.", component.getName(), getColumnNames()));
        }
        
        // Add to component field collection
        component.addField(this);

    }

    public ComponentField getFieldCollection() {
        return this.identityComponent;
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
        return true;
    }
    
}
