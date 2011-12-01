package net.sourceforge.greenvine.model.api;



public interface RelationField extends Field {
    
    public abstract Relation getRelation();
    
    public abstract FieldCollection getFieldCollection();
    
    public abstract RelationField getRelatedField();
    
    public abstract Entity getRelatedEntity();
    
    public boolean isOwner();

}
