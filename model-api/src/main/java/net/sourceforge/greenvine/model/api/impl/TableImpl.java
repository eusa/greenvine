package net.sourceforge.greenvine.model.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PrimaryKey;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.api.UniqueKey;
import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.impl.DatabaseObjectNameImpl;
import net.sourceforge.greenvine.model.naming.impl.UniqueKeyNameExtractor;

public class TableImpl extends AbstractDataContainer implements Table {
    
    private PrimaryKeyImpl primaryKey;
    private final Map<DatabaseObjectNameImpl, UniqueKeyImpl> uniqueKeys;
    private final Map<DatabaseObjectNameImpl, ForeignKeyImpl> importedForeignKeys;
    private final Map<DatabaseObjectNameImpl, ForeignKeyImpl> exportedForeignKeys;
    private final boolean isView;
    
    TableImpl(CharSequence name, DatabaseImpl database) throws ModelException {
        super(DatabaseObjectNameImpl.parse(name), database);
        
        // Initialise fields
        this.uniqueKeys = new TreeMap<DatabaseObjectNameImpl, UniqueKeyImpl>();
        this.importedForeignKeys = new TreeMap<DatabaseObjectNameImpl, ForeignKeyImpl>();
        this.exportedForeignKeys = new TreeMap<DatabaseObjectNameImpl, ForeignKeyImpl>();
        this.isView = false;
        
        // Add table to database
        database.addTable(this);
    }
    
    TableImpl(CharSequence name, boolean isView, DatabaseImpl database) throws ModelException {
        super(DatabaseObjectNameImpl.parse(name), database);
        
        // Initialise fields
        this.uniqueKeys = new TreeMap<DatabaseObjectNameImpl, UniqueKeyImpl>();
        this.importedForeignKeys = new TreeMap<DatabaseObjectNameImpl, ForeignKeyImpl>();
        this.exportedForeignKeys = new TreeMap<DatabaseObjectNameImpl, ForeignKeyImpl>();
        this.isView = isView;
        
        // Add table to database
        database.addTable(this);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#getDatabase()
     */
    public DatabaseImpl getDatabase() {
        return this.database;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#getPrimaryKey()
     */
    public PrimaryKeyImpl getPrimaryKey() {
        return this.primaryKey;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#getNaturalKey()
     */
    public UniqueKeyImpl getNaturalKey() {
    	UniqueKeyImpl natural = null;
    	if (this.getUniqueKeyCount() > 0) {
    		natural = getUniqueKey(0);
    	}
        return natural;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#getUniqueKeys()
     */
    public Collection<UniqueKeyImpl> getUniqueKeys() {
        return Collections.unmodifiableCollection(this.uniqueKeys.values());
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#getImportedForeignKeys()
     */
    public Collection<ForeignKeyImpl> getImportedForeignKeys() {
        return Collections.unmodifiableCollection(this.importedForeignKeys.values());
    }
 
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#getExportedForeignKeys()
     */
    public Collection<ForeignKeyImpl> getExportedForeignKeys() {
        return Collections.unmodifiableCollection(this.exportedForeignKeys.values());
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#createPrimaryKey(java.lang.CharSequence, java.util.SortedSet)
     */
    public PrimaryKeyImpl createPrimaryKey(CharSequence name, SortedSet<? extends CharSequence> cols) throws ModelException {
        return new PrimaryKeyImpl(name, cols, this);
    }
    
    
    void setPrimaryKey(PrimaryKeyImpl primaryKey) throws ModelException {
        for (UniqueKeyImpl uniqueKey : this.getUniqueKeys()) {
            if (uniqueKey.columnsEqual(primaryKey)) {
                throw new ModelException(String.format("Can't set primary key with same columns as unique key in table %s.", this.name));
            }
        }
        this.primaryKey = primaryKey;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#createUniqueKey(java.lang.CharSequence, java.util.SortedSet)
     */
    public UniqueKeyImpl createUniqueKey(CharSequence ukName, SortedSet<? extends CharSequence> ukCols) throws ModelException {
        return new UniqueKeyImpl(ukName, ukCols, this);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#createUniqueKey(java.util.SortedSet)
     */
    public UniqueKey createUniqueKey(SortedSet<? extends CharSequence> ukCols) throws ModelException {
        UniqueKeyNameExtractor extractor = new UniqueKeyNameExtractor();
        return new UniqueKeyImpl(extractor.extractName(this.database, this), ukCols, this);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#createUniqueKey(java.lang.CharSequence, java.lang.CharSequence)
     */
    public UniqueKey createUniqueKey(CharSequence ukName, CharSequence... columns) throws ModelException {
        SortedSet<CharSequence> ukCols = new TreeSet<CharSequence>();
        for (CharSequence ukCol : columns) {
            ukCols.add(ukCol);
        }
        return new UniqueKeyImpl(ukName, ukCols, this);
    }
    
    void addUniqueKey(UniqueKeyImpl uniqueKey) throws ModelException {
        if (uniqueKey.columnsEqual(primaryKey)) {
            throw new ModelException(String.format("Can't add unique key with same columns as primary key in table %s.", this.name));
        } 
        for (UniqueKeyImpl existing : this.getUniqueKeys()) {
            if (existing.columnsEqual(uniqueKey)) {
                throw new ModelException(String.format("Can't add unique key with same columns as existing unique key in table %s.", this.name));
            }
        }
        this.uniqueKeys.put(uniqueKey.getName(), uniqueKey);
    }
    
    void addImportedForeignKey(ForeignKeyImpl imported) throws ModelException {
        this.importedForeignKeys.put(imported.getName(), imported);
    }
    
    void addExportedForeignKey(ForeignKeyImpl exported) throws ModelException {
        this.exportedForeignKeys.put(exported.getName(), exported);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#getImportedForeignKeyCount()
     */
    public int getImportedForeignKeyCount() {
        return this.importedForeignKeys.size();
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#getExportedForeignKeyCount()
     */
    public int getExportedForeignKeyCount() {
        return this.exportedForeignKeys.size();
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#getUniqueKeyCount()
     */
    public int getUniqueKeyCount() {
        return this.uniqueKeys.size();
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#getUniqueKeyByName(java.lang.CharSequence)
     */
    public UniqueKey getUniqueKeyByName(CharSequence uniqueKeyName) throws ModelException {
        return this.database.getUniqueKeyByName(uniqueKeyName); 
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#getForeignKeyByName(java.lang.CharSequence)
     */
    public ForeignKeyImpl getForeignKeyByName(CharSequence foreignKeyName) throws ModelException {
        return this.database.getForeignKeyByName(foreignKeyName);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#hasPrimaryKey()
     */
    public boolean hasPrimaryKey() {
        if (this.primaryKey != null) {
            return true;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#hasUniqueKeys()
     */
    public boolean hasUniqueKeys() {
        if (this.uniqueKeys != null && this.uniqueKeys.size() > 0) {
            return true;
        }
        return false;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#hasImportedForeignKeys()
     */
    public boolean hasImportedForeignKeys() {
        if (this.importedForeignKeys != null && this.importedForeignKeys.size() > 0) {
            return true;
        }
        return false;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#hasExportedForeignKeys()
     */
    public boolean hasExportedForeignKeys() {
        if (this.exportedForeignKeys != null && this.exportedForeignKeys.size() > 0) {
            return true;
        }
        return false;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#getUniqueKey(int)
     */
    public UniqueKeyImpl getUniqueKey(int ordinal) {
        List<UniqueKeyImpl> keys = new ArrayList<UniqueKeyImpl>(this.uniqueKeys.values());
        return keys.get(ordinal);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#getImportedForeignKey(int)
     */
    public ForeignKeyImpl getImportedForeignKey(int ordinal) {
        List<ForeignKeyImpl> keys = new ArrayList<ForeignKeyImpl>(this.importedForeignKeys.values());
        return keys.get(ordinal);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#getExportedForeignKey(int)
     */
    public ForeignKey getExportedForeignKey(int ordinal) {
        List<ForeignKeyImpl> keys = new ArrayList<ForeignKeyImpl>(this.exportedForeignKeys.values());
        return keys.get(ordinal);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#isAssociationTable()
     */
    public boolean isAssociationTable() {
        
        boolean associationTable = false;
        
        // There can be at most 2 foreign key 
        // relationships in an association table.
        if (this.getImportedForeignKeyCount() == 2) {
            
            // Get all column names
            Set<ColumnName> columns = this.getColumnNames();
            
            // Get all foreign key column names
            List<ColumnName> foreignKeyColumns = new ArrayList<ColumnName>();
            for (ForeignKey foreignKey : this.getImportedForeignKeys()) {
                foreignKeyColumns.addAll(foreignKey.getReferencingColumnNames());
            }
            
            // Get all primary key column names
            List<ColumnName> primaryKeyColumns = new ArrayList<ColumnName>();
            if (this.getPrimaryKey() != null) {
                primaryKeyColumns.addAll(this.getPrimaryKey().getColumnNames());
            }
            
            // Remove foreign key columns from column name list
            columns.removeAll(foreignKeyColumns);
            
            // Remove primary key columns from column name list
            columns.removeAll(primaryKeyColumns);
            
            // If no columns remain, this is a
            // candidate association table
            if (columns.size() == 0) {
                associationTable = true;
            }
            
            // A join table must also not 
            // be referenced by foreign keys
            if (this.getExportedForeignKeyCount() > 0) {
                associationTable = false;
            }
        }
        return associationTable;
    }

    @Override
    public String toString() {
        return "Table [name=" + name + ", columns=" + columns + ", primaryKey="
                + primaryKey + "]";
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#getReferencedTables()
     */
    public Collection<TableImpl> getReferencedTables() {
        Collection<TableImpl> dependencies = new ArrayList<TableImpl>();
        Collection<ForeignKeyImpl> fk = getImportedForeignKeys();
        for (ForeignKeyImpl foreignKey : fk) {
            TableImpl referenced = foreignKey.getReferencedTable();
            if (!referenced.getName().equals(getName())) {
                if (!dependencies.contains(referenced)) {
                    dependencies.add(referenced);
                }
            }
        }
        return dependencies;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#getDependentTablesInDependencyOrder()
     */
    public List<TableImpl> getDependentTablesInDependencyOrder() {
        
        List<TableImpl> dependencies = new ArrayList<TableImpl>();
        addDependencyTables(this, dependencies);
        Collections.reverse(dependencies);
        return dependencies;
        
    }
    
    private void addDependencyTables(TableImpl table, List<TableImpl> dependencies) {
        Collection<TableImpl> immediates = table.getReferencedTables();
        dependencies.addAll(immediates);
        for (TableImpl immediate : immediates) {
            addDependencyTables(immediate, dependencies);
        }
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Table#isView()
     */
    public boolean isView() {
        return isView;
    }
    
    /**
     * Create a compound primary key
     * @param name
     * @param cols
     * @return
     * @throws ModelException
     */
    public PrimaryKey createPrimaryKey(CharSequence name, CharSequence... columns) throws ModelException {
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        for (CharSequence column : columns) {
            cols.add(column);
        }
        return new PrimaryKeyImpl(name, cols, this);
        
    }

    
}
