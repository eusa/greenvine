package net.sourceforge.greenvine.model.api;


public interface OneToOneNaturalIdentityRelation extends AggregationRelation {

    public abstract UniqueKey getUniqueKey();

}