package net.sourceforge.greenvine.model.api;

public interface AssociationRelationField extends RelationField {
    
    public abstract AssociationRelation getRelation();
    
    public abstract boolean isOwner();
    
    public boolean referencesIdentity();
    
    public AssociationRelationField getReferencedField();
    
    public ForeignKey getForeignKey();
    
    public ForeignKey getRelatedForeignKey();

}
