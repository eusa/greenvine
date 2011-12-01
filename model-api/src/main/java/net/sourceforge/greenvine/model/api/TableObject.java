package net.sourceforge.greenvine.model.api;

public interface TableObject extends ColumnCollection, DatabaseObject {
    
    public abstract Table getTable();

}
