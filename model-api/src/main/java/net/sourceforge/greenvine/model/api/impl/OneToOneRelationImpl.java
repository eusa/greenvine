package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.OneToOneRelation;

public class OneToOneRelationImpl extends AbstractAggregationRelation implements OneToOneRelation {
    
    private final OneToOneParentFieldImpl ownerField;
    private final OneToOneChildFieldImpl inverseField;
    /**
     * Creates a bi-directional one-to-one
     * @param ownerField
     * @param inverseField
     * @param foreignKey
     * @param inverseForeignKey
     * @throws ModelException
     */
    OneToOneRelationImpl(CharSequence ownerFieldName, CharSequence inverseFieldName, CharSequence foreignKey, CatalogImpl catalog)
            throws ModelException {
        
        super(ownerFieldName, inverseFieldName, foreignKey, catalog);
        
        // Initialise fields
        this.ownerField = new OneToOneParentFieldImpl(ownerFieldName, ownerEntity, this);
        this.inverseField = new OneToOneChildFieldImpl(inverseFieldName, inverseEntity, this);
        
        // Validate the foreign keys
        validateForeignKey();
        
    }
    
    /**
     * Creates a bi-directional one-to-one
     * @param ownerField
     * @param inverseField
     * @param foreignKey
     * @param inverseForeignKey
     * @throws ModelException
     */
    OneToOneRelationImpl(CharSequence inverseFieldName, CharSequence foreignKey, CatalogImpl catalog)
            throws ModelException {
        
        super(inverseFieldName, foreignKey, catalog);
        
        // Initialise fields
        this.ownerField = null;
        this.inverseField = new OneToOneChildFieldImpl(inverseFieldName, inverseEntity, this);
        
        // Validate the foreign keys
        validateForeignKey();
        
    }
    
    private void validateForeignKey()
            throws ModelException {

        // Check that ownerForeignKey references a UniqueKey
        if (!foreignKey.areReferencingColumnsUniqueKey()) {
            throw new ModelException(String.format("Owner foreign key must reference a unique key for a one-to-one relationship."));
        }
    }
    
    public OneToOneParentFieldImpl getParentField() {
        return ownerField;
    }

    public OneToOneChildFieldImpl getChildField() {
        return inverseField;
    }

}
