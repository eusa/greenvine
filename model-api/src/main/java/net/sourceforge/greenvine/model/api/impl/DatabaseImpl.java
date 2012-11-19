package net.sourceforge.greenvine.model.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.DatabaseObject;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.NamedObject;
import net.sourceforge.greenvine.model.api.RdbmsNamedObject;
import net.sourceforge.greenvine.model.naming.DatabaseObjectName;
import net.sourceforge.greenvine.model.naming.Name;
import net.sourceforge.greenvine.model.naming.NameContainer;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;
import net.sourceforge.greenvine.model.naming.impl.DatabaseNameImpl;
import net.sourceforge.greenvine.model.naming.impl.DatabaseObjectNameImpl;
import net.sourceforge.greenvine.model.naming.impl.RdbmsNameSegmentImpl;

public class DatabaseImpl implements RdbmsNamedObject, NameContainer, Database {
    
    private final DatabaseNameImpl name;
    private final Map<DatabaseObjectNameImpl, TableImpl> tables;
    private final Map<DatabaseObjectNameImpl, PrimaryKeyImpl> primaryKeys;
    private final Map<DatabaseObjectNameImpl, UniqueKeyImpl> uniqueKeys;
    private final Map<DatabaseObjectNameImpl, ForeignKeyImpl> foreignKeys;
    private final Map<DatabaseObjectNameImpl, DatabaseObject> objects;
    private final Object locker = new Object();
    private final RdbmsNamingConventions namingConventions;
    
    public DatabaseImpl(CharSequence name) throws ModelException {
        
        this.name = new DatabaseNameImpl(name);
        this.tables = new TreeMap<DatabaseObjectNameImpl, TableImpl>();
        this.primaryKeys = new TreeMap<DatabaseObjectNameImpl, PrimaryKeyImpl>();
        this.uniqueKeys = new TreeMap<DatabaseObjectNameImpl, UniqueKeyImpl>();
        this.foreignKeys = new TreeMap<DatabaseObjectNameImpl, ForeignKeyImpl>();
        this.objects = new TreeMap<DatabaseObjectNameImpl, DatabaseObject>();
        this.namingConventions = RdbmsNamingConventions.getDefault();
    }
    
    public DatabaseImpl(CharSequence name, RdbmsNamingConventions namingConventions) throws ModelException {
        
        this.name = new DatabaseNameImpl(name);
        this.tables = new TreeMap<DatabaseObjectNameImpl, TableImpl>();
        this.primaryKeys = new TreeMap<DatabaseObjectNameImpl, PrimaryKeyImpl>();
        this.uniqueKeys = new TreeMap<DatabaseObjectNameImpl, UniqueKeyImpl>();
        this.foreignKeys = new TreeMap<DatabaseObjectNameImpl, ForeignKeyImpl>();
        this.objects = new TreeMap<DatabaseObjectNameImpl, DatabaseObject>();
        this.namingConventions = namingConventions;
    }
    
    public DatabaseImpl(DatabaseNameImpl name, RdbmsNamingConventions namingConventions) throws ModelException {
  
        if (name == null) {
            throw new ModelException("Cannot create a Database with a null name.");
        }
        
        this.name = name;
        this.tables = new TreeMap<DatabaseObjectNameImpl, TableImpl>();
        this.primaryKeys = new TreeMap<DatabaseObjectNameImpl, PrimaryKeyImpl>();
        this.uniqueKeys = new TreeMap<DatabaseObjectNameImpl, UniqueKeyImpl>();
        this.foreignKeys = new TreeMap<DatabaseObjectNameImpl, ForeignKeyImpl>();
        this.objects = new TreeMap<DatabaseObjectNameImpl, DatabaseObject>();
        this.namingConventions = namingConventions;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#getTables()
     */
    public Collection<TableImpl> getTables() {
        return Collections.unmodifiableCollection(this.tables.values());
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#getPrimaryKeys()
     */
    public Collection<PrimaryKeyImpl> getPrimaryKeys() {
        return Collections.unmodifiableCollection(this.primaryKeys.values());
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#getUniqueKeys()
     */
    public Collection<UniqueKeyImpl> getUniqueKeys() {
        return Collections.unmodifiableCollection(this.uniqueKeys.values());
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#getForeignKeys()
     */
    public Collection<ForeignKeyImpl> getForeignKeys() {
        return Collections.unmodifiableCollection(this.foreignKeys.values());
    }

    void addTable(TableImpl table) throws ModelException {
        synchronized(locker) {
            if (!this.objects.keySet().contains(table.getName())) {
                tables.put(table.getName(), table);
                objects.put(table.getName(), table);
            }
            else {
                throw new ModelException(String.format("Object named %s already in database.", table.getName()));
            }
        }
    }
    
    void addPrimaryKey(PrimaryKeyImpl primaryKey) throws ModelException {
        synchronized(locker) {
            if (!this.objects.keySet().contains(primaryKey.getName())) {
                primaryKeys.put(primaryKey.getName(), primaryKey);
                objects.put(primaryKey.getName(), primaryKey);
            }
            else {
                throw new ModelException(String.format("Object named %s already in database.", primaryKey.getName()));
            }
        }
    }
    
    void addUniqueKey(UniqueKeyImpl uniqueKey) throws ModelException {
        synchronized(locker) {
            if (!this.objects.keySet().contains(uniqueKey.getName())) {
                uniqueKeys.put(uniqueKey.getName(), uniqueKey);
                objects.put(uniqueKey.getName(), uniqueKey);
            }
            else {
                throw new ModelException(String.format("Object named %s already in database.", uniqueKey.getName()));
            }
        }
    }
    
    void addForeignKey(ForeignKeyImpl foreignKey) throws ModelException {
        synchronized(locker) {
            if (!this.objects.keySet().contains(foreignKey.getName())) {
                foreignKeys.put(foreignKey.getName(), foreignKey);
                objects.put(foreignKey.getName(), foreignKey);
            }
            else {
                throw new ModelException(String.format("Object named %s already in database.", foreignKey.getName()));
            }
        }
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#getTableByName(java.lang.CharSequence)
     */
    public TableImpl getTableByName(CharSequence tableName) throws ModelException {
        DatabaseObjectName name = DatabaseObjectNameImpl.parse(tableName);
        TableImpl table = this.tables.get(name);
        if (table == null) {
            throw new ModelException(String.format("Table %s not found", tableName));
        }
        return table;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#getPrimaryKeyByName(java.lang.CharSequence)
     */
    public PrimaryKeyImpl getPrimaryKeyByName(CharSequence pkName) throws ModelException {
        DatabaseObjectName name = DatabaseObjectNameImpl.parse(pkName);
        PrimaryKeyImpl primaryKey = this.primaryKeys.get(name);
        if (primaryKey == null) {
            throw new ModelException(String.format("PrimaryKey %s not found", pkName));
        }
        return primaryKey;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#getUniqueKeyByName(java.lang.CharSequence)
     */
    public UniqueKeyImpl getUniqueKeyByName(CharSequence uniqueName) throws ModelException {
        DatabaseObjectName name = DatabaseObjectNameImpl.parse(uniqueName);
        UniqueKeyImpl uniqueKey = this.uniqueKeys.get(name);
        if (uniqueKey == null) {
            throw new ModelException(String.format("UniqueKey %s not found", name));
        }
        return uniqueKey;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#getForeignKeyByName(java.lang.CharSequence)
     */
    public ForeignKeyImpl getForeignKeyByName(CharSequence foreignKeyName) throws ModelException {
        DatabaseObjectName name = DatabaseObjectNameImpl.parse(foreignKeyName);
        ForeignKeyImpl foreignKey = this.foreignKeys.get(name);
        if (foreignKey == null) {
            throw new ModelException(String.format("ForeignKey %s not found", foreignKeyName));
        }
        return foreignKey;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#getDatabaseObjectByName(java.lang.CharSequence)
     */
    public DatabaseObject getDatabaseObjectByName(CharSequence objectName) throws ModelException {
        DatabaseObject object = this.objects.get(objectName);
        if (object == null) {
            throw new ModelException(String.format("Object %s not found", objectName));
        }
        return object;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#getTableCount()
     */
    public int getTableCount() {
        return this.tables.size();
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#getForeignKeyCount()
     */
    public int getForeignKeyCount() {
        return this.foreignKeys.size();
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#createTable(java.lang.CharSequence)
     */
    public TableImpl createTable(CharSequence name) throws ModelException {
        DatabaseObjectNameImpl tableName = DatabaseObjectNameImpl.parse(name);
        return new TableImpl(tableName, this);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#createTable(java.lang.CharSequence, boolean)
     */
    public TableImpl createTable(CharSequence name, boolean isView) throws ModelException {
        DatabaseObjectNameImpl tableName = DatabaseObjectNameImpl.parse(name);
        return new TableImpl(tableName, isView, this);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#createForeignKey(java.lang.CharSequence, java.lang.CharSequence, java.lang.CharSequence, java.util.Map)
     */
    public ForeignKeyImpl createForeignKey(CharSequence name, CharSequence referencingTable,
            CharSequence referencedTable, Map<? extends CharSequence, ? extends CharSequence> columnConstraints) throws ModelException {
        DatabaseObjectNameImpl fkName = DatabaseObjectNameImpl.parse(name);
        DatabaseObjectNameImpl referencingTableName = DatabaseObjectNameImpl.parse(referencingTable);
        DatabaseObjectNameImpl referencedTableName = DatabaseObjectNameImpl.parse(referencedTable);
        return new ForeignKeyImpl(fkName, referencingTableName, referencedTableName, columnConstraints, this);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#getNamingConventions()
     */
    public RdbmsNamingConventions getNamingConventions() {
        return this.namingConventions;
    }

    public DatabaseNameImpl getName() {
        return this.name;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#getTablesInDependencyOrder()
     */
    public List<TableImpl> getTablesInDependencyOrder() {
        
        // Get root tables
        Collection<TableImpl> roots = new ArrayList<TableImpl>();
        for (TableImpl table : getTables()) {
            if (table.getImportedForeignKeyCount() == 0) {
                roots.add(table);
            }
        }
        
        // Start the array of ordered tables with the roots
        List<TableImpl> ordered = new ArrayList<TableImpl>(roots);
        
        // Iterate through tables until all have been added
        while (ordered.size() < getTableCount()) {
            for (TableImpl table : getTables()) {
                // Get the tables this table references
                Collection<TableImpl> referenced = table.getReferencedTables();
                
                // Handle the case of self-referencing tables
                Collection<TableImpl> temp = new ArrayList<TableImpl>(ordered); 
                temp.add(table);
                
                // If the table only references tables
                // already added to the list or it references itself
                // and isn't already in the list, then add it
                if (temp.containsAll(referenced) &&  !ordered.contains(table)) {
                    ordered.add(table);
                }
            }
        }
        return ordered;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#getSchemaNames()
     */
    public SortedSet<RdbmsNameSegmentImpl> getSchemaNames() {
        SortedSet<RdbmsNameSegmentImpl> schemaNames = new TreeSet<RdbmsNameSegmentImpl>();
        for (TableImpl table : this.getTables()) {
        	if (table.getName().getSchemaName() != null) {
                schemaNames.add(table.getName().getSchemaName());
        	}
        }
        return schemaNames;
    }

    public int compareTo(NamedObject obj) {
        return this.name.compareTo(obj.getName());
    }

    public boolean containsName(CharSequence name) throws ModelException {
        return this.objects.containsKey(DatabaseObjectNameImpl.parse(name));
    }

    public SortedSet<? extends Name> getNames() {
        return Collections.unmodifiableSortedSet(new TreeSet<DatabaseObjectNameImpl>(this.objects.keySet()));
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#getForeignKeys(java.lang.CharSequence, java.lang.CharSequence)
     */
    public Set<ForeignKeyImpl> getForeignKeys(CharSequence referencingTable, CharSequence referencedTable) throws ModelException {

        // Set of matching keys
        Set<ForeignKeyImpl> matches = new HashSet<ForeignKeyImpl>();
        
        // Referencing table
        TableImpl referencing = getTableByName(referencingTable);
        
        // Imported keys
        Collection<ForeignKeyImpl> keys = referencing.getImportedForeignKeys();
        
        // Find matching
        for (ForeignKeyImpl key : keys) {
            if (key.getReferencedTable().getName().equals(DatabaseObjectNameImpl.parse(referencedTable))) {
                matches.add(key);
            }
        }
        
        // Return matches
        return matches;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Database#getAssociationTables()
     */
    public List<TableImpl> getAssociationTables() {
        List<TableImpl> joins = new ArrayList<TableImpl>();
        for (TableImpl table : tables.values()) {
            if (table.isAssociationTable()) {
                joins.add(table);
            }
        }
        return joins;
    }
}
