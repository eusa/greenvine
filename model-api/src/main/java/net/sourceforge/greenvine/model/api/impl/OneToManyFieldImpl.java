package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.AggregationRelation;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.OneToManyField;


public class OneToManyFieldImpl extends AbstractAggregationRelationField implements OneToManyField {

    private final AggregationRelation manyToOne;
    
    public OneToManyFieldImpl(CharSequence ownerFieldName, EntityImpl entity, AggregationRelation oneToOne)
            throws ModelException {
        super(ownerFieldName, entity, oneToOne);
        
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
    
    public boolean getNotNull() {
        return false;
    }

    public boolean isOwner() {
        return true;
    }

}
