package net.sourceforge.greenvine.model.api;

import java.util.SortedSet;

import net.sourceforge.greenvine.model.naming.ColumnName;

public interface MultiColumnField extends Field {
    
    public SortedSet<? extends Column> getColumns();
    
    public SortedSet<ColumnName> getColumnNames();

}
