package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.AggregationRelation;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.OneToOneParentField;


public class OneToOneParentFieldImpl extends AbstractAggregationRelationField implements OneToOneParentField {

    private final AggregationRelation oneToOne;
    
    OneToOneParentFieldImpl(CharSequence ownerFieldName, EntityImpl entity, AggregationRelation oneToOne)
            throws ModelException {
        super(ownerFieldName, entity, oneToOne);
        
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
    
    public boolean getNotNull() {
        return false;
    }

    public boolean isOwner() {
        return true;
    }

}
