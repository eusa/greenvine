package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.AggregationRelation;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;

public abstract class AbstractAggregationRelation implements AggregationRelation {

    protected final EntityImpl ownerEntity;
    protected final EntityImpl inverseEntity;
    protected final ForeignKey foreignKey;

    /**
     * Creates a bi-directional direct relation
     * @param owner
     * @param inverse
     * @param foreignKey
     * @throws ModelException
     */
     AbstractAggregationRelation(CharSequence ownerFieldName, CharSequence inverseFieldName, CharSequence foreignKey, CatalogImpl catalog)
            throws ModelException {
        
        // Validate parameters
        if (ownerFieldName == null) {
            throw new ModelException(String.format("Null owner field name in many-to-many."));
        }
        if (inverseFieldName == null) {
            throw new ModelException(String.format("Null inverse field name in many-to-many."));
        }
        
        // Initialise fields
        this.foreignKey = catalog.getDatabase().getForeignKeyByName(foreignKey);
        this.ownerEntity = catalog.getEntityByTable(this.foreignKey.getReferencedTable());
        this.inverseEntity = catalog.getEntityByTable(this.foreignKey.getReferencingTable());
        
        // Validate
        validate();
        
    }
    
    /**
     * Creates a uni-directional direct relationship
     * which is either a one-to-one or many-to-one.
     * @param owner
     * @param inverse
     * @param foreignKeyName
     * @throws ModelException
     */
    AbstractAggregationRelation(CharSequence inverseFieldName, CharSequence foreignKeyName, CatalogImpl catalog)
            throws ModelException {
        
        // Validate parameters
        if (inverseFieldName == null) {
            throw new ModelException(String.format("Null owner field name in many-to-many."));
        }
        
        
        // Initialise fields
        this.foreignKey = catalog.getDatabase().getForeignKeyByName(foreignKeyName);
        this.ownerEntity = catalog.getEntityByTable(this.foreignKey.getReferencedTable());
        this.inverseEntity = catalog.getEntityByTable(this.foreignKey.getReferencingTable());
        
        // Validate
        validate();
    }
    
    private void validate()
            throws ModelException {
     
        // Check that the owner entity has an identity
        if (ownerEntity.getIdentity() == null) {
            throw new ModelException(String.format("Owner entity %s must have an identity to take part in aggregation relation", ownerEntity.getName()));
        }
        
        // Check the foreign key is not null
        if (foreignKey == null) {
            throw new ModelException(String.format("Null foreign key in aggregation relation."));
        }
        
        // Check that ownerForeignKey references a PrimaryKey
        if (!foreignKey.referencesPrimaryKey()) {
            throw new ModelException(String.format("Foreign key must reference a primary key for a constrained one-to-one relationship."));
        }
    }

    public Entity getParentEntity() {
        return ownerEntity;
    }

    public Entity getChildEntity() {
        return inverseEntity;
    }

    public ForeignKey getForeignKey() {
        return foreignKey;
    }

    public boolean isSelfReferencing() {
        return getParentEntity().equals(getChildEntity());
    }
    
}