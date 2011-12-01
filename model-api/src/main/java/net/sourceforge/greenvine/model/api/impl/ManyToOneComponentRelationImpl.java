package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.ManyToOneAggregationField;
import net.sourceforge.greenvine.model.api.ManyToOneRelation;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.MutableComponentField;


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
public class ManyToOneComponentRelationImpl extends AbstractAggregationRelation implements ManyToOneRelation {
    
    private final OneToManyFieldImpl ownerField;
    private final ManyToOneIdentityField inverseField;
    
    /**
     * Creates a bi-directional many-to-one
     * @param owner
     * @param inverse
     * @param foreignKey
     * @param inverseForeignKey
     * @throws ModelException
     */
    
    ManyToOneComponentRelationImpl(CharSequence ownerFieldName, CharSequence inverseFieldName, CharSequence foreignKey,MutableComponentField component, CatalogImpl catalog)
            throws ModelException {
        
        super(ownerFieldName, inverseFieldName, foreignKey, catalog);
        
        // Initialise fields
        this.ownerField = new OneToManyFieldImpl(ownerFieldName, ownerEntity, this);
        this.inverseField = new ManyToOneIdentityField(inverseFieldName, component, this);
        
        // Validate the foreign keys
        validateForeignKey();
        
    }
    

    private void validateForeignKey()
            throws ModelException {

        // Check that ForeignKey isn't a UniqueKey
        if (foreignKey.areReferencingColumnsUniqueKey()) {
            throw new ModelException(String.format("Foreign key referencing columns must not be a unique key for a many-to-one relationship."));
        }
        
        // Check that ForeignKey isn't a PrimaryKey
        if (foreignKey.areReferencingColumnsPrimaryKey()) {
            throw new ModelException(String.format("Foreign key referencing columns must not be a primary key for a many-to-one relationship."));
        }
    }
    
    public OneToManyFieldImpl getParentField() {
        return ownerField;
    }

    public ManyToOneAggregationField getChildField() {
        return inverseField;
    }

}
