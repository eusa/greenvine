package net.sourceforge.greenvine.model.api;

import java.util.SortedSet;

import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.FieldName;
import net.sourceforge.greenvine.model.naming.NameContainer;

public interface FieldCollection extends NamedObject, NameContainer {
    
    public abstract Table getTable();
    
    public abstract SortedSet<Field> getFields();
    
    public abstract Field getField(int index);
    
    public abstract Field getFieldByName(CharSequence name) throws ModelException;
    
    public abstract int getFieldCount();
    
    public abstract SortedSet<? extends FieldName> getNames();
    
    public abstract boolean containsName(CharSequence name) throws ModelException;

    public Field getFieldBackedByColumns(SortedSet<? extends Column> columns);
    
    public SortedSet<? extends Column> getColumns();
    
    public SortedSet<? extends ColumnName> getColumnNames();

}
