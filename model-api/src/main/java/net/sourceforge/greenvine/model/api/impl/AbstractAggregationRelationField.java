package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.AggregationRelation;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.Relation;
import net.sourceforge.greenvine.model.api.RelationField;

public abstract class AbstractAggregationRelationField extends AbstractFieldNamedObject
        implements RelationField {

    protected final Entity entity;
    private final AggregationRelation relation;
    
    public AbstractAggregationRelationField(CharSequence name, Entity entity, AggregationRelation relation)
            throws ModelException {
        super(name);
        
        // Validate parameters
        if (entity == null) {
            throw new ModelException(String.format("Cannot create many-to-many field %s with null entity.", name));
        }
        if (relation == null) {
            throw new ModelException(String.format("Cannot create many-to-many field %s in entity %s with null many-to-many.", name, entity.getName()));
        }
        
        // Set fields
        this.entity = entity;
        this.relation = relation;
        
    }
      
    public Relation getRelation() {
        return this.relation;
    }
    
    public boolean referencesIdentity() {
        if (this.relation.getForeignKey().referencesPrimaryKey()) {
            return true;
        }
        return false;
    }
    
    public RelationField getReferencedField() {
        return relation.getParentField();
    }
    
    public Entity getRelatedEntity() {
        if (isOwner()) {
            return relation.getChildEntity();
        } else {
            return relation.getParentEntity();
        }
    }
    
    public RelationField getRelatedField() {
        if (isOwner()) {
            return relation.getChildField();
        } else {
            return relation.getParentField();
        }
    }

}
