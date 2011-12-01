package net.sourceforge.greenvine.model.api.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnCollection;
import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.Key;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.NamedObject;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.impl.DatabaseObjectNameImpl;

public class AbstractKey implements Key {

    protected final DatabaseObjectNameImpl name;
    protected final TableImpl table;
    protected final ColumnCollectionDelegate columns;
    
    AbstractKey(CharSequence name, SortedSet<? extends CharSequence> columns, TableImpl table) throws ModelException {
        
        // Check table not null
        validateTable(table);
        
        // Set up fields
        this.name = DatabaseObjectNameImpl.parse(name);
        this.table = table;
        this.columns = new ColumnCollectionDelegate(this);
        
        // Create columns
        createColumns(table, columns);
    }
    
    /**
     * Create a column in the key
     * based on the supplied
     * column names
     * 
     * @param table
     * @param columns
     * @throws ModelException
     */
    private void createColumns(Table table,
            SortedSet<? extends CharSequence> columns) throws ModelException {
        if (columns == null || columns.size() == 0) {
            throw new ModelException(String.format("Key %s in table %s cannot be created without columns.", name, table));
        } else {
            for (CharSequence columnName : columns) {
                ColumnImpl column = this.table.getColumnByName(columnName);
                this.columns.addColumn(column);
            }
        }
    }

    /**
     * Validate the supplied table
     * 
     * @param table
     * @throws ModelException
     */
    private void validateTable(Table table)
            throws ModelException {
        if (table == null) {
            throw new ModelException(String.format("Can't create key %s with a null table.", name));
        }
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ColumnCollection#getColumnByName(java.lang.CharSequence)
     */
    public Column getColumnByName(CharSequence columnName) throws ModelException {
        return columns.getColumnByName(columnName);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ColumnCollection#getColumn(int)
     */
    public Column getColumn(int ordinal) {
        return this.columns.getColumn(ordinal);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ColumnCollection#getColumnCount()
     */
    public int getColumnCount() {
        return columns.getColumnCount();
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ColumnCollection#getColumns()
     */
    public SortedSet<Column> getColumns() {
        return columns.getColumns();
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.DatabaseObject#getDatabase()
     */
    public Database getDatabase() {
        return table.getDatabase();
    }

    
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.DatabaseObject#getName()
     */
    public DatabaseObjectNameImpl getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.TableObject#getTable()
     */
    public Table getTable() {
        return table;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ColumnCollection#getColumnNames()
     */
    public SortedSet<ColumnName> getColumnNames() {
        return columns.getColumnNames();
    }
    
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ColumnCollection#columnsEqual(net.sourceforge.greenvine.model.api.ColumnCollection)
     */
    public boolean columnsEqual(ColumnCollection other) {
        return columns.columnsEqual(other);
    }
    
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ColumnCollection#contains(net.sourceforge.greenvine.model.api.Column)
     */
    public boolean contains(Column column) {
        return this.columns.contains(column);
    }

    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Key#getForeignKeys()
     */
    public Set<ForeignKeyImpl> getForeignKeys() {
        Set<ForeignKeyImpl> keys = new HashSet<ForeignKeyImpl>();
        for (ForeignKeyImpl foreignKey : this.table.getImportedForeignKeys()) {
            if (this.getColumns().containsAll(foreignKey.getReferencingColumns())) {
                keys.add(foreignKey);
            }
        }
        return keys;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Key#getNonForeignColumns()
     */
    public SortedSet<Column> getNonForeignColumns() {
        
        SortedSet<Column> all = new TreeSet<Column>(getColumns());
        for (ForeignKey key : getForeignKeys()) {
            all.removeAll(key.getReferencingColumns());
        }
        
        return all;
        
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Key#getForeignKey()
     */
    public ForeignKeyImpl getForeignKey() {
        
        // Check all foreign keys to see if they match 
        for (ForeignKeyImpl foreignKey : this.table.getImportedForeignKeys()) {
            
            // Get referencing columns from foreign key
            SortedSet<ColumnName> referencingColumns = foreignKey.getReferencingColumnNames();
            
            // If they match this primary key's columns, true
            if (referencingColumns.equals(this.getColumnNames())) {
                return foreignKey;
            }
        }
        
        // No matches
        return null;
    }
    
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Key#isForeignKey()
     */
    public boolean isForeignKey() {
        
        // False if table has no foreign key
        if (getForeignKey() != null) {
            return true;
        }

        // Otherwise, false
        return false;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(NamedObject obj) {
        return this.name.compareTo(obj.getName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((columns == null) ? 0 : columns.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((table == null) ? 0 : table.hashCode());
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
        AbstractKey other = (AbstractKey) obj;
        if (columns == null) {
            if (other.columns != null)
                return false;
        } else if (!columns.equals(other.columns))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (table == null) {
            if (other.table != null)
                return false;
        } else if (!table.equals(other.table))
            return false;
        return true;
    }

}
