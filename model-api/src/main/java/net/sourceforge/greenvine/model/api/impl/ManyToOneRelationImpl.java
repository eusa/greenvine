package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.ManyToOneRelation;
import net.sourceforge.greenvine.model.api.ModelException;

/**
 * Models a many-to-one relation.
 * Consists of a required owner entity
 * which is backed by the referenced table
 * in the foreign key and an "inverse" entity
 * which is backed by the referencing table
 * in the foreign key.
 * The owner field is the field within the 
 * owner entity that is involved in this relation.
 * It is therefore a "one-many" field.
 * The inverseField is the field in the
 * inverse entity, which is a "many-one" field
 * The inverse many-one field is required as 
 * it maps the foreign key columns in the 
 * inverse entity, which would otherwise
 * have to be mapped as simple properties. 
 * Therefore, this is the only uni-directional
 * relation possibility.
 *
 */
public class ManyToOneRelationImpl extends AbstractAggregationRelation implements ManyToOneRelation {
    
    private final OneToManyFieldImpl ownerField;
    private final ManyToOneFieldImpl inverseField;
    /**
     * Creates a bi-directional many-to-one
     * @param owner
     * @param inverse
     * @param foreignKey
     * @param inverseForeignKey
     * @throws ModelException
     */
    ManyToOneRelationImpl(CharSequence ownerFieldName, CharSequence inverseFieldName, CharSequence foreignKey, CatalogImpl catalog)
            throws ModelException {
        
        super(ownerFieldName, inverseFieldName, foreignKey, catalog);
        
        // Initialise fields
        this.ownerField = new OneToManyFieldImpl(ownerFieldName, ownerEntity, this);
        this.inverseField = new ManyToOneFieldImpl(inverseFieldName, inverseEntity, this);
        
        // Validate the foreign keys
        validateForeignKey();
        
    }
    
    /**
     * Creates a bi-directional one-to-one
     * @param owner
     * @param inverse
     * @param ownerForeignKey
     * @param inverseForeignKey
     * @throws ModelException
     */
    ManyToOneRelationImpl(CharSequence inverseFieldName, CharSequence ownerForeignKey, CatalogImpl catalog)
            throws ModelException {
        
        super(inverseFieldName, ownerForeignKey, catalog);
        
        // Initialise fields
        this.ownerField = null;
        this.inverseField = new ManyToOneFieldImpl(inverseFieldName, inverseEntity, this);
        
        // Validate the foreign keys
        validateForeignKey();
        
    }
    
    private void validateForeignKey()
            throws ModelException {

        // Check that ForeignKey doesn't reference a UniqueKey
        if (foreignKey.areReferencingColumnsUniqueKey()) {
            throw new ModelException(String.format("Foreign key must not reference a unique key for a many-to-one relationship."));
        }
        
        // Check that ForeignKey doesn't reference a PrimaryKey
        if (foreignKey.areReferencingColumnsPrimaryKey()) {
            throw new ModelException(String.format("Foreign key must not reference a primary key for a many-to-one relationship."));
        }
    }
    
    public OneToManyFieldImpl getParentField() {
        return ownerField;
    }

    public ManyToOneFieldImpl getChildField() {
        return inverseField;
    }

}
