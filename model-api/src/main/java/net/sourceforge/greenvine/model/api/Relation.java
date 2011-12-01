package net.sourceforge.greenvine.model.api;


public interface Relation {
    
    public abstract RelationField getParentField();
    
    public abstract RelationField getChildField();
    
    public abstract Entity getParentEntity();

    public abstract Entity getChildEntity();
    
    public abstract boolean isSelfReferencing();

}
