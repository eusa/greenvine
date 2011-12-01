package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.OneToOneAssociationField;


public class OneToOneAssociationFieldImpl extends AbstractAssociationRelationField implements OneToOneAssociationField {

    private final OneToOneAssociationRelationImpl oneToOne;
    
    public OneToOneAssociationFieldImpl(CharSequence name, EntityImpl entity, OneToOneAssociationRelationImpl oneToOne)
            throws ModelException {
        super(name, entity, oneToOne);
        
        this.oneToOne = oneToOne;
        
        // Add to parent entity
        entity.addField(this);

    }

    public OneToOneAssociationRelationImpl getRelation() {
        return this.oneToOne;
    }
    
    public boolean getNotNull() {
        return false;
    }

}
