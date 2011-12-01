package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.OneToManyAssociationRelation;

/**
 * Create a one-to-many association relationship
 * where the one-to-many end "owns" the relationship.
 * Note that in an aggregation relation, only the
 * one-to-many end can own the relationship.
 * See the related ManyToOneAssociationRelation class
 * where the many-to-one end owns the relationship.
 * Can be bi or uni-directional.
 *
 */
public class OneToManyAssociationRelationImpl extends AbstractAssociationRelation implements OneToManyAssociationRelation {

    private final OneToManyAssociationFieldImpl owner;
    private final ManyToOneAssociationFieldImpl inverse;
    /**
     * Creates a bi-directional many-to-one
     * @param owner
     * @param inverse
     * @param ownerForeignKey
     * @param inverseForeignKey
     * @throws ModelException
     */
    OneToManyAssociationRelationImpl(CharSequence ownerFieldName, CharSequence ownerForeignKey, CharSequence inverseFieldName, CharSequence inverseForeignKey, CatalogImpl catalog)
            throws ModelException {
        
        super(ownerFieldName,  ownerForeignKey,inverseFieldName, inverseForeignKey, catalog);
        
        // Initialise fields
        this.owner = new OneToManyAssociationFieldImpl(ownerFieldName, parentEntity, this);
        this.inverse = new ManyToOneAssociationFieldImpl(inverseFieldName, childEntity, this);

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
    OneToManyAssociationRelationImpl(CharSequence ownerFieldName, CharSequence ownerForeignKey, CharSequence inverseForeignKey, CatalogImpl catalog)
            throws ModelException {
        
        super(ownerFieldName, ownerForeignKey, inverseForeignKey, catalog);
        
        // Initialise fields
        this.owner = new OneToManyAssociationFieldImpl(ownerFieldName, parentEntity, this);
        this.inverse = null;

        // Validate the foreign keys
        validateForeignKeys();
        
    }
    
    private void validateForeignKeys()
            throws ModelException {
        // Check that inverseForeignKey references a UniqueKey
        if (!inverseForeignKey.areReferencingColumnsUniqueKey()) {
            throw new ModelException(String.format("Inverse foreign key must reference a unique key in join table %s for a many-to-one relationship.", ownerForeignKey.getReferencingTable().getName()));
        }
        // Check that ownerForeignKey doesn't reference a UniqueKey
        if (ownerForeignKey.areReferencingColumnsUniqueKey()) {
            throw new ModelException(String.format("Owner foreign key must not reference a unique key in join table %s for a many-to-one relationship.", ownerForeignKey.getReferencingTable().getName()));
        }
    }
    
    public OneToManyAssociationFieldImpl getParentField() {
        return owner;
    }

    public ManyToOneAssociationFieldImpl getChildField() {
        return inverse;
    }
}
