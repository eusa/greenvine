package net.sourceforge.greenvine.model.api.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import net.sourceforge.greenvine.model.api.Cardinality;
import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnConstraint;
import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConvention;
import net.sourceforge.greenvine.model.naming.impl.ColumnNameImpl;
import net.sourceforge.greenvine.model.naming.impl.DatabaseObjectNameImpl;
import net.sourceforge.greenvine.model.naming.impl.RdbmsNameSegmentBuilderImpl;

public class ForeignKeyImpl extends AbstractDatabaseObject implements ForeignKey {
    
    private final TableImpl referencingTable;
    private final TableImpl referencedTable;
    private final SortedSet<ColumnConstraintImpl> columnConstraints;
    private final SortedSet<ColumnImpl> referencedColumns;
    private final SortedSet<ColumnImpl> referencingColumns;
    private final Map<ColumnName, ColumnName> columnConstraintNames;
    private final Object locker = new Object();

    ForeignKeyImpl(CharSequence name, CharSequence referencingTable, CharSequence referencedTable, Map<? extends CharSequence, ? extends CharSequence> ownerFkColConstraints, DatabaseImpl database) throws ModelException {
        super(DatabaseObjectNameImpl.parse(name), database);
        
        // Set up fields
        this.referencingTable = database.getTableByName(referencingTable);
        this.referencedTable = database.getTableByName(referencedTable);
        this.columnConstraints = new TreeSet<ColumnConstraintImpl>();
        this.referencingColumns = new TreeSet<ColumnImpl>();
        this.referencedColumns = new TreeSet<ColumnImpl>();
        this.columnConstraintNames = new TreeMap<ColumnName, ColumnName>();
        
        // Create column constraints
        createColumnConstraints(name, ownerFkColConstraints, database);
        
        validateReferencedTable(name, referencedTable);
        
        // Add self to database and tables
        this.database.addForeignKey(this);
        this.referencingTable.addImportedForeignKey(this);
        this.referencedTable.addExportedForeignKey(this);
    }


    /**
     * Create foreign key and 
     * infer foreign columns 
     * based on primary key 
     * of referenced table
     */
    public ForeignKeyImpl(CharSequence name, CharSequence referencingTable, CharSequence referencedTable, DatabaseImpl database) throws ModelException {
        super(name, database);
        
        // Set up fields
        this.referencingTable = database.getTableByName(referencingTable);
        this.referencedTable = database.getTableByName(referencedTable);
        this.columnConstraints = new TreeSet<ColumnConstraintImpl>();
        this.referencingColumns = new TreeSet<ColumnImpl>();
        this.referencedColumns = new TreeSet<ColumnImpl>();
        this.columnConstraintNames = new TreeMap<ColumnName, ColumnName>();
        
        // Create column constraints, inferring referencing columns
        Map<ColumnName, ColumnName> ownerFkColConstraints = new TreeMap<ColumnName, ColumnName>();
        for (Column referenced : this.referencedTable.getPrimaryKey().getColumns()) {
            ColumnNameImpl referencing = getForeignColumnName(this.referencedTable, referenced);
            ownerFkColConstraints.put(referencing, referenced.getName());
            this.referencingTable.createColumn(referencing, referenced.getColumnType(), referenced.getNotNull(), referenced.getScale(), referenced.getPrecision(), referenced.getColumnValueGenerationStrategy());
        }
        createColumnConstraints(name, ownerFkColConstraints, database);

        // Validate referenced table
        validateReferencedTable(name, referencedTable);
        
        
        // Add self to database and tables
        this.database.addForeignKey(this);
        this.referencingTable.addImportedForeignKey(this);
        this.referencedTable.addExportedForeignKey(this);
    }
    
    private void validateReferencedTable(CharSequence name,
            CharSequence referencedTable) throws ModelException {
        // Check referenced table has primary key or unique key
        if (!(this.referencedTable.hasPrimaryKey() || this.referencedTable.hasUniqueKeys())) {
            throw new ModelException(String.format("Foreign key %s must reference a table with a primary or unique key.", this.getName()));
        }
        
        // Check that either a unique or primary key is referenced
        if (!(this.referencesPrimaryKey() || this.referencesUniqueKey())) {
            throw new ModelException(String.format("Foreign key %s does not reference a unique or primary key of table %s.", name, referencedTable));
        }
    }

    private void createColumnConstraints(CharSequence name,
            Map<? extends CharSequence, ? extends CharSequence> ownerFkColConstraints, Database database)
            throws ModelException {
        if (ownerFkColConstraints == null || ownerFkColConstraints.size() == 0) {
            throw new ModelException(String.format("Foreign key %s cannot be created without columns.", name));
        } else {
            for (CharSequence referencingColumn : ownerFkColConstraints.keySet()) {
                CharSequence referencedColumn = ownerFkColConstraints.get(referencingColumn);
                ColumnName referencingDbName = new ColumnNameImpl(referencingColumn);
                ColumnName referencedDbName = new ColumnNameImpl(referencedColumn);
                if (this.getReferencedColumnNames().contains(referencedDbName)) {
                    throw new ModelException(String.format("Column %s has already been referenced in foreign key %s.", referencedColumn, name));
                }
                ColumnConstraintImpl constraint = this.createColumnConstraint(referencingColumn, referencedColumn);
                this.columnConstraintNames.put(referencingDbName, referencedDbName);
                this.referencingColumns.add(constraint.getReferencingColumn());
                this.referencedColumns.add(constraint.getReferencedColumn());
            }
        }
        
        // Check that an identical set of column constraints has not been created
        for (ForeignKeyImpl fk : this.database.getForeignKeys()) {
            if (fk.constraintsEqual(this) 
                    && fk.getReferencedTable().equals(this.referencedTable)
                    && fk.getReferencingTable().equals(this.referencingTable)) {
                throw new ModelException(String.format("Foreign key %s cannot be created with identical constraints to existing foreign key %s.", name, fk.getName()));
            }
        }
    }
  
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#referencesUniqueKey()
     */
    public boolean referencesUniqueKey() {
        SortedSet<ColumnName> refCols = this.getReferencedColumnNames();
        for (UniqueKeyImpl uniqueKey : referencedTable.getUniqueKeys()) {
            SortedSet<ColumnName> ukCols = uniqueKey.getColumnNames();
            if (refCols.containsAll(ukCols) && ukCols.containsAll(refCols)) {
                return true;
            } 
        }
        return false;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#referencesPrimaryKey()
     */
    public boolean referencesPrimaryKey() {
        SortedSet<ColumnName> pkCols = referencedTable.getPrimaryKey().getColumnNames();
        SortedSet<ColumnName> refCols = this.getReferencedColumnNames();
        if (refCols.containsAll(pkCols) && pkCols.containsAll(refCols)) {
            return true;
        } else {
            return false;
        }
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#getReferencingColumnNames()
     */
    public SortedSet<ColumnName> getReferencingColumnNames() {
        return Collections.unmodifiableSortedSet(new TreeSet<ColumnName>(this.columnConstraintNames.keySet()));
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#getReferencedColumnNames()
     */
    public SortedSet<ColumnName> getReferencedColumnNames() {
        return Collections.unmodifiableSortedSet(new TreeSet<ColumnName>(this.columnConstraintNames.values()));
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#getColumnConstraintNames()
     */
    public Map<ColumnName, ColumnName> getColumnConstraintNames() {
        return Collections.unmodifiableMap(this.columnConstraintNames);
    }

    private boolean constraintsEqual(ForeignKey foreignKey) {
        if (foreignKey == null) {
            return false;
        }
        if (foreignKey.getColumnConstraints() == null) {
            return false;
        }
        if (this.columnConstraints.equals(foreignKey.getColumnConstraints())) {
            return true;
        }
        return false;
    }

    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#getReferencingTable()
     */
    public TableImpl getReferencingTable() {
        return this.referencingTable;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#getReferencedTable()
     */
    public TableImpl getReferencedTable() {
        return this.referencedTable;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#getColumnConstraints()
     */
    public Set<ColumnConstraintImpl> getColumnConstraints() {
        return Collections.unmodifiableSet(this.columnConstraints);
    }
 
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#getColumnConstraintCount()
     */
    public int getColumnConstraintCount() {
        return this.columnConstraints.size();
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#getColumnConstraint(int)
     */
    public ColumnConstraint getColumnConstraint(int index) {
        return new ArrayList<ColumnConstraintImpl>(this.columnConstraints).get(index);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#getColumnConstraintByReferencingColumnName(net.sourceforge.greenvine.model.naming.ColumnName)
     */
    public ColumnConstraint getColumnConstraintByReferencingColumnName(ColumnName referencingColumnName) throws ModelException {
        for (ColumnConstraint columnConstraint : this.columnConstraints) {
            if (columnConstraint.getReferencingColumn().getName().equals(referencingColumnName)) {
                return columnConstraint;
            }
        }
        throw new ModelException(String.format("Column constraint not found with referencing column %s in foreign key %s.", referencingColumnName, this.getName()));
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#getColumnConstraintByReferencedColumnName(net.sourceforge.greenvine.model.naming.ColumnName)
     */
    public ColumnConstraint getColumnConstraintByReferencedColumnName(ColumnName referencedColumnName) throws ModelException {
        for (ColumnConstraint columnConstraint : this.columnConstraints) {
            if (columnConstraint.getReferencedColumn().getName().equals(referencedColumnName)) {
                return columnConstraint;
            }
        }
        throw new ModelException(String.format("Column constraint not found with referencing column %s in foreign key %s.", referencedColumnName, this.getName()));
    }
    
    private ColumnConstraintImpl createColumnConstraint(CharSequence referencingColumnName, CharSequence referencedColumnName) throws ModelException {
        ColumnImpl referencingColumn = referencingTable.getColumnByName(referencingColumnName);
        ColumnImpl referencedColumn = referencedTable.getColumnByName(referencedColumnName);
        if (!referencedColumn.compatibleWith(referencingColumn)) {
            throw new ModelException(String.format("Column constraint cannot be created between incompatible columns %s and %s in foreign key %s.", referencingColumn, referencedColumnName, this.getName()));
        }
        return new ColumnConstraintImpl(this, referencingColumn, referencedColumn);
    }

    void addColumnConstraint(ColumnConstraintImpl columnConstraint) throws ModelException {
        synchronized(locker) {
            if (!this.columnConstraints.contains(columnConstraint)) {
                this.columnConstraints.add(columnConstraint);
            } else {
                throw new ModelException(String.format("Duplicate column constraint with column %s referencing column %s in foreign key %s.", columnConstraint.getReferencingColumn().getName(), columnConstraint.getReferencedColumn().getName(), this.getName()));
            }
        }
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#hasConstraint(java.lang.CharSequence, java.lang.CharSequence)
     */
    public boolean hasConstraint(CharSequence referencingColumn, CharSequence referencedColumn) throws ModelException {
        ColumnName referencing = new ColumnNameImpl(referencingColumn);
        ColumnName referenced = new ColumnNameImpl(referencedColumn);
        if (this.columnConstraintNames == null) {
            return false;
        }
        if (this.columnConstraintNames.containsKey(referencing)) {
            if (this.columnConstraintNames.get(referencing).equals(referenced)) {
                return true;
            }
        }
        return false;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#hasReferencingColumn(net.sourceforge.greenvine.model.api.Column)
     */
    public boolean hasReferencingColumn(Column column) {
        boolean hasReferencingColumn = false;
        if (this.columnConstraints != null) {
            for (ColumnConstraint constraint : this.columnConstraints) {
                if (constraint.getReferencingColumn().equals(column)) {
                    hasReferencingColumn = true;
                    break;
                }
            }
        }
        return hasReferencingColumn;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#hasReferencedColumn(net.sourceforge.greenvine.model.api.Column)
     */
    public boolean hasReferencedColumn(Column column) {
        boolean hasReferencedColumn = false;
        if (this.columnConstraints != null) {
            for (ColumnConstraint constraint : this.columnConstraints) {
                if (constraint.getReferencedColumn().equals(column)) {
                    hasReferencedColumn = true;
                    break;
                }
            }
        }
        return hasReferencedColumn;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#areReferencingColumnsUniqueKey()
     */
    public boolean areReferencingColumnsUniqueKey() {
        Set<ColumnName> referencingColumns = this.getReferencingColumnNames();
        for (UniqueKeyImpl uniqueConstraint : referencingTable.getUniqueKeys()) {
            Set<ColumnName> uniqueColumns = uniqueConstraint.getColumnNames();
            if (uniqueColumns.containsAll(referencingColumns) && referencingColumns.containsAll(uniqueColumns)) {
                return true;
            }
        }
        return false;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#areReferencingColumnsPrimaryKey()
     */
    public boolean areReferencingColumnsPrimaryKey() {
        
        // False if referencing table has no primary key
        if (referencingTable.getPrimaryKey() == null) {
            return false;
        }
        
        // Get referencing columns and primary columns from referencing table
        Set<ColumnName> referencingColumns = this.getReferencingColumnNames();
        Set<ColumnName> primaryColumns = this.referencingTable.getPrimaryKey().getColumnNames();
        
        // If they match, true
        if (referencingColumns.equals(primaryColumns)) {
            return true;
        }
        
        // Otherwise, false
        return false;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#getReferencingColumns()
     */
    public SortedSet<Column> getReferencingColumns() {
        if (this.columnConstraints != null) {
            SortedSet<Column> columns = new TreeSet<Column>();
            for (ColumnConstraint colCon : this.columnConstraints) {
                columns.add(colCon.getReferencingColumn());
            }
            return columns;
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#getReferencedColumns()
     */
    public SortedSet<Column> getReferencedColumns() {
        if (this.columnConstraints != null) {
            SortedSet<Column> columns = new TreeSet<Column>();
            for (ColumnConstraint colCon : this.columnConstraints) {
                columns.add(colCon.getReferencedColumn());
            }
            return columns;
        }
        return null;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#areReferencingColumnsNullable()
     */
    public boolean areReferencingColumnsNullable() {
        if (this.columnConstraints != null) {
            for (ColumnConstraint colCon : this.columnConstraints) {
                if (colCon.getReferencingColumn().getNotNull()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "ForeignKey [name=" + name 
                + ", referencedTable=" + referencedTable.getName()
                + ", referencingTable=" + referencingTable.getName() 
                + ", columnConstraintNames=" + columnConstraintNames
                + "]";
    }


    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#isSelfReferencing()
     */
    public boolean isSelfReferencing() {
        return referencedTable.equals(referencingTable);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#getCardinality()
     */
    public Cardinality getCardinality() {
        if (areReferencingColumnsUniqueKey() || areReferencingColumnsPrimaryKey()) {
            return Cardinality.OneToOne;
        } else {
            return Cardinality.OneToMany;
        }
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ForeignKey#otherSimilarRelationsExist()
     */
    public boolean otherSimilarRelationsExist() throws ModelException {
        
        // Get all foreign keys linking these tables
        DatabaseImpl database = getDatabase();
        Set<ForeignKeyImpl> matches = database.getForeignKeys(getReferencingTable().getName(), getReferencedTable().getName());
        
        // Number of matches
        int numberOfMatches = 0;
        
        // Check cardinality of relations
        for (ForeignKey key : matches) {
            // Don't compare the key to itself
            if (!key.equals(this)) {
                if (getCardinality().equals(key.getCardinality())) {
                    numberOfMatches ++;
                }
            }
            
        }
        return numberOfMatches > 0;
    }
    
    private ColumnNameImpl getForeignColumnName(Table referencedTable,
            Column referencedColumn) throws ModelException {
        RdbmsNamingConvention fkCol = database.getNamingConventions().getForeignColumn();
        RdbmsNamingConvention tbl =  database.getNamingConventions().getTable();
        RdbmsNameSegmentBuilderImpl tblNameBuilder = new RdbmsNameSegmentBuilderImpl(referencedTable.getName().getObjectName(), tbl);
        tblNameBuilder.removeSuffixPrefixIncludingSeparators();
        RdbmsNameSegmentBuilderImpl colName = new RdbmsNameSegmentBuilderImpl(tblNameBuilder, fkCol);
        colName.appendSeparator().append(referencedColumn.getName());
        colName.prependPrefix().toCase();
        return colName.toColumnName();
    }
    
}