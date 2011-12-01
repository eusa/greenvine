package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.ManyToManyRelation;
import net.sourceforge.greenvine.model.api.ModelException;

public class ManyToManyRelationImpl extends AbstractAssociationRelation implements ManyToManyRelation {
    
    private final ManyToManyAssociationFieldImpl owner;
    private final ManyToManyAssociationFieldImpl inverse;
    /**
     * Creates a bi-directional many-to-many
     * @param owner
     * @param inverse
     * @param ownerForeignKey
     * @param inverseForeignKey
     * @throws ModelException
     */
    ManyToManyRelationImpl(CharSequence ownerFieldName, CharSequence ownerForeignKey, CharSequence inverseFieldName, CharSequence inverseForeignKey, CatalogImpl catalog)
            throws ModelException {
        
        super(ownerFieldName, ownerForeignKey, inverseFieldName, inverseForeignKey, catalog);
        
        // Initialise fields
        this.owner = new ManyToManyAssociationFieldImpl(ownerFieldName, parentEntity, this);
        this.inverse = new ManyToManyAssociationFieldImpl(inverseFieldName, childEntity, this);
        
        // Validate the foreign keys
        validateForeignKeys();
        
    }
    
    /**
     * Creates a uni-directional many-to-many
     * @param owner
     * @param inverse
     * @param ownerForeignKey
     * @param inverseForeignKey
     * @throws ModelException
     */
    ManyToManyRelationImpl(CharSequence ownerFieldName, CharSequence ownerForeignKey, CharSequence inverseForeignKey, CatalogImpl catalog)
            throws ModelException {
        
        super(ownerFieldName, ownerForeignKey, inverseForeignKey, catalog);
        
        // Initialise fields
        this.owner = new ManyToManyAssociationFieldImpl(ownerFieldName, parentEntity, this);
        this.inverse = null;
     
        // Validate the foreign keys
        validateForeignKeys();
        
    }
    
    private void validateForeignKeys()
            throws ModelException {
        // Check that inverseForeignKey references a UniqueKey
        if (inverseForeignKey.areReferencingColumnsUniqueKey()) {
            throw new ModelException(String.format("Inverse foreign key must not reference a unique key in join table %s for a many-to-many relationship via join table.", ownerForeignKey.getReferencingTable().getName()));
        }
        // Check that ownerForeignKey doesn't reference a UniqueKey
        if (ownerForeignKey.areReferencingColumnsUniqueKey()) {
            throw new ModelException(String.format("Owner foreign key must not reference a unique key in join table %s for a many-to-many relationship via join table.", ownerForeignKey.getReferencingTable().getName()));
        }
    }
    
    public ManyToManyAssociationFieldImpl getParentField() {
        return owner;
    }

    public ManyToManyAssociationFieldImpl getChildField() {
        return inverse;
    }

}
