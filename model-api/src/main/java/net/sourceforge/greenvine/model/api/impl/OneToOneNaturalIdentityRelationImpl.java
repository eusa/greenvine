package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.OneToOneNaturalIdentityRelation;
import net.sourceforge.greenvine.model.api.UniqueKey;

public class OneToOneNaturalIdentityRelationImpl extends AbstractAggregationRelation implements OneToOneNaturalIdentityRelation {
    
    private final OneToOneParentFieldImpl ownerField;
    private final OneToOneChildNaturalIdentityImpl inverseField;
    private final UniqueKey uniqueKey;
    
    /**
     * Creates a bi-directional one-to-one
     * @param ownerField
     * @param inverseField
     * @param foreignKey
     * @param inverseForeignKey
     * @throws ModelException
     */
    OneToOneNaturalIdentityRelationImpl(CharSequence ownerFieldName, CharSequence inverseFieldName, CharSequence foreignKey, CharSequence uniqueKey, CatalogImpl catalog)
            throws ModelException {
        
        super(ownerFieldName, inverseFieldName, foreignKey, catalog);
        
        // Initialise fields
        this.ownerField = new OneToOneParentFieldImpl(ownerFieldName, ownerEntity, this);
        this.inverseField = new OneToOneChildNaturalIdentityImpl(inverseFieldName, inverseEntity, this);
        this.uniqueKey = catalog.getDatabase().getUniqueKeyByName(uniqueKey);
        
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
    OneToOneNaturalIdentityRelationImpl(CharSequence inverseFieldName, CharSequence ownerForeignKey, CharSequence uniqueKey, CatalogImpl catalog)
            throws ModelException {
        
        super(inverseFieldName, ownerForeignKey, catalog);
        
        // Initialise fields
        this.ownerField = null;
        this.inverseField = new OneToOneChildNaturalIdentityImpl(inverseFieldName, inverseEntity, this);
        this.uniqueKey = catalog.getDatabase().getUniqueKeyByName(uniqueKey);
        
        // Validate the foreign keys
        validateForeignKey();
        
    }
    
    

    private void validateForeignKey()
            throws ModelException {

        // Check that ownerForeignKey references a UniqueKey
        if (!foreignKey.areReferencingColumnsUniqueKey()) {
            throw new ModelException(String.format("Foreign key must reference a unique key for a natural identity one-to-one relationship."));
        }
        if (!foreignKey.getReferencingColumns().equals(uniqueKey.getColumns())) {
            throw new ModelException(String.format("Foreign key must reference a unique key for a natural identity one-to-one relationship."));
        }
    }
    
    public OneToOneParentFieldImpl getParentField() {
        return ownerField;
    }

    public OneToOneChildNaturalIdentityImpl getChildField() {
        return inverseField;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.OneToOneNaturalIdentityRelation#getUniqueKey()
     */
    public UniqueKey getUniqueKey() {
        return this.uniqueKey;
    }
}