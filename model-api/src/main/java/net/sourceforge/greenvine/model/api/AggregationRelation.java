package net.sourceforge.greenvine.model.api;

public interface AggregationRelation extends Relation {
    
    public abstract ForeignKey getForeignKey();
    
    public abstract AggregationRelationChildField getChildField();

    public abstract AggregationRelationParentField getParentField();
    
}
