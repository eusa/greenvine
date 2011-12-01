package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.AssociationRelation;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;

/**
 * Models an association relation
 * mediated by a join table in the 
 * database. Can be many-to-many,
 * one-to-many and one-to-one 
 * depending on the existence
 * of unique constraints on the
 * join table columns
 *
 */
public abstract class AbstractAssociationRelation implements AssociationRelation {

    // TODO [minor] should the constructors just take a join table instead of the 2 foreign keys?
    
    protected final EntityImpl parentEntity;
    protected final EntityImpl childEntity;
    protected final ForeignKey ownerForeignKey;
    protected final ForeignKey inverseForeignKey;

    /**
     * Creates a bi-directional many-to-many
     * @param owner
     * @param inverse
     * @param ownerForeignKey
     * @param inverseForeignKey
     * @throws ModelException
     */
     AbstractAssociationRelation(CharSequence ownerFieldName, CharSequence ownerForeignKey, CharSequence inverseFieldName, CharSequence inverseForeignKey, CatalogImpl catalog)
            throws ModelException {
        
        // Validate parameters
        if (ownerFieldName == null) {
            throw new ModelException(String.format("Null owner field name in many-to-many."));
        }
        if (inverseFieldName == null) {
            throw new ModelException(String.format("Null inverse field name in many-to-many."));
        }
        
        // Initialise fields
        this.ownerForeignKey = catalog.getDatabase().getForeignKeyByName(ownerForeignKey);
        this.inverseForeignKey = catalog.getDatabase().getForeignKeyByName(inverseForeignKey);
        this.parentEntity = catalog.getEntityByTable(this.ownerForeignKey.getReferencedTable());
        this.childEntity = catalog.getEntityByTable(this.inverseForeignKey.getReferencedTable());
        
        // Validate relation
        validate();
        
    }

    /**
     * Creates a uni-directional association relation.
     * @param owner
     * @param inverse
     * @param ownerForeignKey
     * @param inverseForeignKey
     * @throws ModelException
     */
    AbstractAssociationRelation(CharSequence ownerFieldName, CharSequence ownerForeignKey, CharSequence inverseForeignKey, CatalogImpl catalog)
            throws ModelException {
        
        // Validate parameters
        if (ownerFieldName == null) {
            throw new ModelException(String.format("Null field name in many-to-many."));
        }
        
        // Initialise fields
        this.ownerForeignKey = catalog.getDatabase().getForeignKeyByName(ownerForeignKey);
        this.inverseForeignKey = catalog.getDatabase().getForeignKeyByName(inverseForeignKey);
        this.parentEntity = catalog.getEntityByTable(this.ownerForeignKey.getReferencedTable());
        this.childEntity = catalog.getEntityByTable(this.inverseForeignKey.getReferencedTable());
        
        // Validate relation
        validate();
    }
    
    private void validate() throws ModelException {
        
        // Check for nullity
        if (ownerForeignKey == null) {
            throw new ModelException(String.format("Null owner foreign key in many-to-many."));
        }
        if (inverseForeignKey == null) {
            throw new ModelException(String.format("Null inverse foreign key in many-to-many."));
        }
        
        // Verify that foreign keys reference the same association table
        if (!ownerForeignKey.getReferencingTable().equals(inverseForeignKey.getReferencingTable())) {
            throw new ModelException("Association relation requires foreign keys to reference the same association table.");
        }
        
        // Verify the association table
        if (!ownerForeignKey.getReferencingTable().isAssociationTable()) {
            throw new ModelException("Association relation requires an association table.");
        }
        
        // Check that the parent entity has an identity
        if (parentEntity.getIdentity() == null) {
            throw new ModelException(String.format("Entity %s must have an identity to take part in aggregation relation", parentEntity.getName()));
        }
        
        // Check that the child entity has an identity
        if (childEntity.getIdentity() == null) {
            throw new ModelException(String.format("Entity %s must have an identity to take part in aggregation relation", parentEntity.getName()));
        }
    }
    
    public Entity getParentEntity() {
        return parentEntity;
    }

    public Entity getChildEntity() {
        return childEntity;
    }

    public ForeignKey getOwnerForeignKey() {
        return ownerForeignKey;
    }

    public ForeignKey getInverseForeignKey() {
        return inverseForeignKey;
    }
    
    public boolean isSelfReferencing() {
        return getParentEntity().equals(getChildEntity());
    }

}