package net.sourceforge.greenvine.model.api;

import java.util.SortedSet;

import net.sourceforge.greenvine.model.naming.ColumnName;

public interface ColumnCollection {

    public abstract SortedSet<Column> getColumns();

    public abstract int getColumnCount();

    public abstract Column getColumnByName(CharSequence columnName)
            throws ModelException;
    
    public abstract Column getColumn(int ordinal);
    
    public abstract boolean contains(Column column);
    
    public abstract SortedSet<ColumnName> getColumnNames();
    
    public abstract boolean columnsEqual(ColumnCollection other);

}