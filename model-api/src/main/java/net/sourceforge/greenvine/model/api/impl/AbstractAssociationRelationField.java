package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.AssociationRelation;
import net.sourceforge.greenvine.model.api.AssociationRelationField;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;


public abstract class AbstractAssociationRelationField extends AbstractFieldNamedObject
        implements AssociationRelationField {

    private final Entity entity;
    private final AssociationRelation relation;
    
    public AbstractAssociationRelationField(CharSequence name, Entity entity, AssociationRelation relation)
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

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Field#getFieldCollection()
     */
    public Entity getFieldCollection() {
        return this.entity;
    }
      
    public AssociationRelation getRelation() {
        return this.relation;
    }
    
    public boolean isOwner() {
        if (this.equals(relation.getParentField())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean referencesIdentity() {
        if (isOwner()) {
            if (this.relation.getInverseForeignKey().referencesPrimaryKey()) {
                return true;
            }
        } else {
            if (this.relation.getOwnerForeignKey().referencesPrimaryKey()) {
                return true;
            }
        }
        return false;
    }
    
    public AssociationRelationField getReferencedField() {
        if (isOwner()) {
            return relation.getChildField();
        } else {
            return relation.getParentField();
        }
    }

    public Entity getRelatedEntity() {
        if (isOwner()) {
            return relation.getChildEntity();
        } else {
            return relation.getParentEntity();
        }
    }
    
    public AssociationRelationField getRelatedField() {
        if (isOwner()) {
            return relation.getChildField();
        } else {
            return relation.getParentField();
        }
    }
    
    public ForeignKey getForeignKey() {
        if (isOwner()) {
            return relation.getOwnerForeignKey();
        } else {
            return relation.getInverseForeignKey();
        }
    }
    
    public ForeignKey getRelatedForeignKey() {
        if (isOwner()) {
            return relation.getInverseForeignKey();
        } else {
            return relation.getOwnerForeignKey();
        }
    }
    
}
