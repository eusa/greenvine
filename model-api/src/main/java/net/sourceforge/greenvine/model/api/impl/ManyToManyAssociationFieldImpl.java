package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.ManyToManyAssociationField;
import net.sourceforge.greenvine.model.api.ModelException;

public class ManyToManyAssociationFieldImpl extends AbstractAssociationRelationField implements ManyToManyAssociationField {

    private final ManyToManyRelationImpl manyToMany;
    
    public ManyToManyAssociationFieldImpl(CharSequence name, EntityImpl entity, ManyToManyRelationImpl manyToMany)
            throws ModelException {
        super(name, entity, manyToMany);
        
        this.manyToMany = manyToMany;
        
        // Add to parent entity
        entity.addField(this);

    }

    public ManyToManyRelationImpl getRelation() {
        return this.manyToMany;
    }

    public boolean getNotNull() {
        return false;
    }

}
