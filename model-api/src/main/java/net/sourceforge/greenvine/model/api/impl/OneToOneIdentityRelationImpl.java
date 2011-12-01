package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.OneToOneIdentityRelation;
import net.sourceforge.greenvine.model.api.PrimaryKey;

public class OneToOneIdentityRelationImpl extends AbstractAggregationRelation implements OneToOneIdentityRelation {
    
    private final OneToOneParentFieldImpl ownerField;
    private final OneToOneChildIdentityImpl inverseField;
    private final PrimaryKey primaryKey;
    
    /**
     * Creates a bi-directional one-to-one
     * @param ownerField
     * @param inverseField
     * @param foreignKey
     * @param inverseForeignKey
     * @throws ModelException
     */
    OneToOneIdentityRelationImpl(CharSequence ownerFieldName, CharSequence inverseFieldName, CharSequence foreignKey, CatalogImpl catalog)
            throws ModelException {
        
        super(ownerFieldName, inverseFieldName, foreignKey, catalog);
        
        // Initialise fields
        this.ownerField = new OneToOneParentFieldImpl(ownerFieldName, ownerEntity, this);
        this.inverseField = new OneToOneChildIdentityImpl(inverseFieldName, inverseEntity, this);
        this.primaryKey = this.foreignKey.getReferencingTable().getPrimaryKey();
        
        // Validate the foreign keys
        validateForeignKey();
        
    }
    
    /**
     * Creates a uni-directional one-to-one
     * @param ownerField
     * @param inverseField
     * @param ownerForeignKey
     * @param inverseForeignKey
     * @throws ModelException
     */
    OneToOneIdentityRelationImpl(CharSequence inverseFieldName, CharSequence ownerForeignKey, CatalogImpl catalog)
            throws ModelException {
        
        super(inverseFieldName, ownerForeignKey, catalog);
        
        // Initialise fields
        this.ownerField = null;
        this.inverseField = new OneToOneChildIdentityImpl(inverseFieldName, inverseEntity, this);
        this.primaryKey = foreignKey.getReferencingTable().getPrimaryKey();
        
        // Validate the foreign keys
        validateForeignKey();
        
    }
    
    
    private void validateForeignKey()
            throws ModelException {
        
        // Check that ownerForeignKey referencing columns are a primary key
        if (!foreignKey.areReferencingColumnsPrimaryKey()) {
            throw new ModelException(String.format("Foreign key must reference a primary key for a constrained one-to-one relationship."));
        }
    }
    
    public OneToOneParentFieldImpl getParentField() {
        return ownerField;
    }

    public OneToOneChildIdentityImpl getChildField() {
        return inverseField;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.OneToOneIdentityRelation#getPrimaryKey()
     */
    public PrimaryKey getPrimaryKey() {
        return this.primaryKey;
    }
}