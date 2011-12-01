package net.sourceforge.greenvine.model.api.impl;

import java.util.SortedSet;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnCollection;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ColumnValueGenerationStrategy;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.impl.ColumnNameImpl;

public abstract class AbstractDataContainer extends AbstractDatabaseObject implements Table {
    
    protected final ColumnCollectionDelegate columns;

    public AbstractDataContainer(CharSequence name, DatabaseImpl database) throws ModelException {
        super(name, database);
        this.columns = new ColumnCollectionDelegate(this);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ColumnCollection#getColumns()
     */
    public SortedSet<Column> getColumns() {
        return columns.getColumns();
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ColumnCollection#getColumnCount()
     */
    public int getColumnCount() {
        return columns.getColumnCount();
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ColumnCollection#getColumnByName(java.lang.String)
     */
    public ColumnImpl getColumnByName(CharSequence columnName) throws ModelException {
        ColumnName name = new ColumnNameImpl(columnName);
        return columns.getColumnByName(name);
    }
    
    public ColumnImpl getColumn(int ordinal) {
        return columns.getColumn(ordinal);
    }
    
    public SortedSet<ColumnName> getColumnNames() {
        return columns.getColumnNames();
    }

    public boolean columnsEqual(ColumnCollection other) {
        return columns.columnsEqual(other);
    }
    
    public ColumnImpl createColumn(ColumnNameImpl name, ColumnType type, boolean notNull,
            int scale, int precision) throws ModelException {
        return createColumn(name, type, notNull, scale, precision, ColumnValueGenerationStrategy.ASSIGNED);    
    }
    
    public ColumnImpl createColumn(ColumnNameImpl name, ColumnType type, boolean notNull,
            int scale, int precision, ColumnValueGenerationStrategy valueGenerationStrategy) throws ModelException {
        ColumnImpl column = new ColumnImpl(this, name, type, notNull, scale, precision, valueGenerationStrategy);
        this.columns.addColumn(column);
        return column;
    }
    
    public ColumnImpl createColumn(ColumnNameImpl name, ColumnType type, boolean notNull, ColumnValueGenerationStrategy valueGenerationStrategy) throws ModelException {
        ColumnImpl column = new ColumnImpl(this, name, type, notNull, valueGenerationStrategy);
        this.columns.addColumn(column);
        return column;
    }
    
    public ColumnImpl createColumn(CharSequence name, ColumnType type, boolean notNull,
            int scale, int precision) throws ModelException {
        ColumnNameImpl columnName = new ColumnNameImpl(name);
        return createColumn(columnName, type, notNull, scale, precision, ColumnValueGenerationStrategy.ASSIGNED);
    }
    
    public ColumnImpl createColumn(CharSequence name, ColumnType type,
            boolean notNull, ColumnValueGenerationStrategy valueGenerationStrategy) throws ModelException {
        ColumnNameImpl columnName = new ColumnNameImpl(name);
        return createColumn(columnName, type, notNull, valueGenerationStrategy);
    }

    public boolean contains(Column column) {
        return this.columns.contains(column);
    }

}
