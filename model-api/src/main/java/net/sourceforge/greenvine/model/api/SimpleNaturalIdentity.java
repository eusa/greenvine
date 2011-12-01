package net.sourceforge.greenvine.model.api;


public interface SimpleNaturalIdentity extends NaturalIdentity, ColumnField {

    public abstract Entity getFieldCollection();

    public abstract UniqueKey getUniqueKey();

}