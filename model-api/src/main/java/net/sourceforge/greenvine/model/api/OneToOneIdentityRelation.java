package net.sourceforge.greenvine.model.api;


public interface OneToOneIdentityRelation extends AggregationRelation {

    public abstract PrimaryKey getPrimaryKey();

}