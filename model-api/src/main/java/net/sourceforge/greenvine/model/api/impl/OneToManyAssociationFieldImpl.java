package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.AssociationRelation;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.OneToManyAssociationField;


public class OneToManyAssociationFieldImpl extends AbstractAssociationRelationField implements OneToManyAssociationField {
    
    private final AssociationRelation oneToMany;
    
    OneToManyAssociationFieldImpl(CharSequence ownerFieldName, EntityImpl entity, AssociationRelation oneToMany) throws ModelException {
        super(ownerFieldName, entity, oneToMany);
        
        this.oneToMany = oneToMany;
        
        // Add to parent entity
        entity.addField(this);

    }

    public AssociationRelation getRelation() {
        return this.oneToMany;
    }
    
    public boolean getNotNull() {
        return false;
    }

}
