package net.sourceforge.greenvine.model.api;

public interface AggregationRelationChildField extends ColumnRelationField {
 
    public abstract AggregationRelation getRelation();
    
    public abstract boolean isDependency();
    
}
