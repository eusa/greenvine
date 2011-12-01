package net.sourceforge.greenvine.model.api.impl;

import java.util.SortedSet;

import net.sourceforge.greenvine.model.api.AggregationRelation;
import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.OneToOneChildNaturalIdentity;
import net.sourceforge.greenvine.model.api.UniqueKey;
import net.sourceforge.greenvine.model.naming.ColumnName;

public class OneToOneChildNaturalIdentityImpl extends AbstractAggregationRelationField implements OneToOneChildNaturalIdentity {

    private final OneToOneNaturalIdentityRelationImpl oneToOne;
    
    public OneToOneChildNaturalIdentityImpl(CharSequence name, EntityImpl entity, OneToOneNaturalIdentityRelationImpl oneToOne)
            throws ModelException {
        super(name, entity, oneToOne);
        
        this.oneToOne = oneToOne;
        
        // Add to parent entity
        entity.setNaturalIdentity(this);

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

    public UniqueKey getUniqueKey() {
        return this.oneToOne.getUniqueKey();
    }
    
    public boolean isDependency() {
        return true;
    }
}
