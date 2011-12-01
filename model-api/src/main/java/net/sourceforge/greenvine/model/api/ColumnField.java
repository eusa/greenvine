package net.sourceforge.greenvine.model.api;


public interface ColumnField extends Field {
    
    public abstract Column getColumn();
    
    public abstract PropertyType getPropertyType();

}
