package net.sourceforge.greenvine.model.api;


/**
 *
 * A field partaking in a relationship
 * between two entities that maps on to
 * columns in the entity - two concrete 
 * implementations are a many-to-one or
 * one-to-one field where values in the 
 * the referencing columns in the 
 * referencing table are governed by 
 * the value of the related entity
 *
 */
public interface ColumnRelationField extends MultiColumnField, RelationField {

    public boolean referencesIdentity();
    
    public RelationField getReferencedField();
    
}
