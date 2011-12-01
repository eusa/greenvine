package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.AssociationRelation;
import net.sourceforge.greenvine.model.api.ManyToOneAssociationField;
import net.sourceforge.greenvine.model.api.ModelException;


public class ManyToOneAssociationFieldImpl extends AbstractAssociationRelationField implements ManyToOneAssociationField {
    
    private final AssociationRelation oneToMany;
    
    ManyToOneAssociationFieldImpl(CharSequence name, EntityImpl entity, AssociationRelation oneToMany) throws ModelException {
        super(name, entity, oneToMany);
        
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
