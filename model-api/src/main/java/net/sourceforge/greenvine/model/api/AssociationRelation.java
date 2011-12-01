package net.sourceforge.greenvine.model.api;

public interface AssociationRelation extends Relation {
    
    public abstract AssociationRelationField getParentField();
    
    public abstract AssociationRelationField getChildField();

    public abstract ForeignKey getOwnerForeignKey();
    
    public abstract ForeignKey getInverseForeignKey();
}
