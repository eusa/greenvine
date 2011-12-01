package net.sourceforge.greenvine.model.api.impl;

//import net.sourceforge.greenvine.model.naming.ModelName;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.OneToOneAssociationRelation;

public class OneToOneAssociationRelationImpl extends AbstractAssociationRelation implements OneToOneAssociationRelation {
    
    private final OneToOneAssociationFieldImpl owner;
    private final OneToOneAssociationFieldImpl inverse;
    /**
     * Creates a bi-directional one-to-one
     * @param owner
     * @param inverse
     * @param ownerForeignKey
     * @param inverseForeignKey
     * @throws ModelException
     */
    OneToOneAssociationRelationImpl(CharSequence ownerFieldName, CharSequence ownerForeignKey, CharSequence inverseFieldName, CharSequence inverseForeignKey, CatalogImpl catalog)
            throws ModelException {
        
        super(ownerFieldName, ownerForeignKey, inverseFieldName, inverseForeignKey, catalog);
        
        // Initialise fields
        this.owner = new OneToOneAssociationFieldImpl(ownerFieldName, parentEntity, this);
        this.inverse = new OneToOneAssociationFieldImpl(inverseFieldName, childEntity, this);

        // Validate the foreign keys
        validateForeignKeys();
    }
    
    /**
     * Creates a bi-directional one-to-one
     * @param owner
     * @param inverse
     * @param ownerForeignKey
     * @param inverseForeignKey
     * @throws ModelException
     */
    
    OneToOneAssociationRelationImpl(CharSequence ownerFieldName, CharSequence ownerForeignKey, CharSequence inverseForeignKey, CatalogImpl catalog)
            throws ModelException {
        
        super(ownerFieldName, ownerForeignKey, inverseForeignKey, catalog);
        
        // Initialise fields
        this.owner = new OneToOneAssociationFieldImpl(ownerFieldName, parentEntity, this);
        this.inverse = null;

        // Validate the foreign keys
        validateForeignKeys();
    }
    
    
    private void validateForeignKeys()
            throws ModelException {
        // Check that inverseForeignKey references a UniqueKey
        if (!inverseForeignKey.areReferencingColumnsUniqueKey()) {
            throw new ModelException(String.format("Inverse foreign key must reference a unique key in join table %s for a one-to-one relationship via join table.", ownerForeignKey.getReferencingTable().getName()));
        }
        // Check that ownerForeignKey references a UniqueKey
        if (!ownerForeignKey.areReferencingColumnsUniqueKey()) {
            throw new ModelException(String.format("Owner foreign key must reference a unique key in join table %s for a one-to-one relationship via join table.", ownerForeignKey.getReferencingTable().getName()));
        }
    }
    
    public OneToOneAssociationFieldImpl getParentField() {
        return owner;
    }

    public OneToOneAssociationFieldImpl getChildField() {
        return inverse;
    }

}
