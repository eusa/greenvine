package net.sourceforge.greenvine.model.api.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnCollection;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.NamedObject;
import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.impl.ColumnNameImpl;

public class ColumnCollectionDelegate implements ColumnCollection {

    private final NamedObject owner;
    private final Map<ColumnName, ColumnImpl> columns;
    private final Object locker = new Object();

    public ColumnCollectionDelegate(NamedObject owner) throws ModelException {
        this.owner = owner;
        this.columns = new TreeMap<ColumnName, ColumnImpl>();
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ColumnCollection#getColumns()
     */
    public SortedSet<Column> getColumns() {
        return Collections.unmodifiableSortedSet(new TreeSet<Column>(this.columns.values()));
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ColumnCollection#getColumnCount()
     */
    public int getColumnCount() {
        synchronized(locker) {
            return this.columns.size();
        }
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ColumnCollection#getColumnByName(java.lang.String)
     */
    public ColumnImpl getColumnByName(CharSequence cName) throws ModelException {
        ColumnName columnName = new ColumnNameImpl(cName);
        ColumnImpl column = this.columns.get(columnName);
        if (column == null) {
            throw new ModelException(String.format("Column %s not found in collection owned by %s", columnName, owner.getName()));
        }
        return column;
    }
    
    public ColumnImpl getColumn(int ordinal) {
        return new ArrayList<ColumnImpl>(this.columns.values()).get(ordinal);
    }
    
    
    void addColumn(ColumnImpl column) throws ModelException {
        if (column == null) {
            throw new ModelException(String.format("Null column cannot be added to collection owned by %s", this.owner.getName()));
        }
        synchronized(locker) {
            if (!columns.containsKey(column.getName())) {
                columns.put(column.getName(), column);
            } else {
                throw new ModelException(String.format("Column %s already in collection owned by %s", column.getName(), owner.getName()));
            }
        }
    }
    
    public SortedSet<ColumnName> getColumnNames() {
        SortedSet<ColumnName> colNames = new TreeSet<ColumnName>();
        for (Column colRef : this.getColumns()){
            colNames.add(colRef.getName());
        }
        return colNames;
    }
    
    public boolean columnsEqual(ColumnCollection other) {
        if (this == other)
            return true;
        if (other == null)
            return false;
        if (this.columns == null) {
            if (other.getColumns() != null)
                return false;
            else
                return true;
        } else {
            if (other.getColumns() == null) {
                return false;
            } else {
                List<Column> thisCols = new ArrayList<Column>(this.getColumns());
                List<Column> otherCols = new ArrayList<Column>(other.getColumns());
                return thisCols.equals(otherCols);
            }
        }
    }

    public boolean contains(Column column) {
        if (this.columns!= null) {
            return this.columns.values().contains(column);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((columns == null) ? 0 : columns.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ColumnCollectionDelegate other = (ColumnCollectionDelegate) obj;
        if (columns == null) {
            if (other.columns != null)
                return false;
        } else if (!columns.equals(other.columns))
            return false;
        if (owner == null) {
            if (other.owner != null)
                return false;
        } else if (!owner.equals(other.owner))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ColumnCollectionDelegate [columns="
                + columns + "]";
    }

}
