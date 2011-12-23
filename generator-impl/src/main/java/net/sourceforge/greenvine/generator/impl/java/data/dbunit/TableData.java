package net.sourceforge.greenvine.generator.impl.java.data.dbunit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PrimaryKey;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.api.UniqueKey;
import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.DatabaseObjectName;
import net.sourceforge.greenvine.model.naming.impl.ColumnNameImpl;

public class TableData {
    
    private final Table metadata;
    private final DataSet dataSet;
    private final List<RowData> rows = new ArrayList<RowData>();
    private final Object locker = new Object();
    
    private final Map<String, Map<String, RowData>> indexes = new HashMap<String, Map<String, RowData>>();
    
    public TableData (Table metadata, DataSet dataSet) {
        this.metadata = metadata;
        this.dataSet = dataSet;
        
        // Create primary key index
        indexes.put(metadata.getPrimaryKey().getName().toString(), new HashMap<String, RowData>());
        
        // Unique key indexes
        for (UniqueKey unique : metadata.getUniqueKeys()) {
            indexes.put(unique.getName().toString(), new HashMap<String, RowData>());
        }
    }
    
    public Collection<ColumnName> getColumnNames() {
        return metadata.getColumnNames();
    }
    
    public Collection<String> getColumnValues(ColumnName column) {
        Collection<String> values = new ArrayList<String>();
        for (RowData row : rows) {
            values.add(row.getValue(column));
        }
        return values;
    }

    public Collection<RowData> getRows() {
        return rows;
    }
    
    public String getValue(int row, String column) throws ModelException {
        RowData rowData = rows.get(row); 
        if (rowData == null) {
            throw new IllegalArgumentException(String.format("Table %s doesn't contain a row with identifier %s", this.metadata.getName(), row));
        }
        return rowData.getValue(new ColumnNameImpl(column));
    }
    
    public void addRow(RowData row) {
        synchronized(locker) {
            if (validate(row)) {
                rows.add(row);
                updateIndexes(row);
            }
        }
    }

    private boolean validate(RowData row) {
        if (detectNull(row) || detectDuplicateKeys(row)) {
            return false;
        }
        return true;
    }

    private boolean detectNull(RowData row) {
        for (Column column : getColumns()) {
            if (column.getNotNull()) {
                if (!row.hasValue(column.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean detectDuplicateKeys(RowData row) {
        
        boolean duplicate = false;
        
        // Check pk hash
        String pkHash = getValueHash(getPrimaryKeyColumns(), row);
        if (indexes.get(metadata.getPrimaryKey().getName().toString()).containsKey(pkHash)) {
            duplicate = true;
        }
        
        // Check uk hashes
        for (UniqueKey unique : metadata.getUniqueKeys()) {
            String ukHash = getValueHash(unique.getColumns(), row);
            if (indexes.get(unique.getName().toString()).containsKey(ukHash)) {
                duplicate = true;
            }
        }
        
        return duplicate;
    }

    private void updateIndexes(RowData row) {
        
        // Primary key index
        String pkHash = getValueHash(getPrimaryKeyColumns(), row);
        indexes.get(metadata.getPrimaryKey().getName().toString()).put(pkHash, row);
        
        // Unique key index
        for (UniqueKey unique : metadata.getUniqueKeys()) {
            String ukHash = getValueHash(unique.getColumns(), row);
            indexes.get(unique.getName().toString()).put(ukHash, row);
        }
    }

    private String getValueHash(SortedSet<Column> columns, RowData row) {
        StringBuilder hash = new StringBuilder();
        for (Column column : columns) {
            hash.append(row.getValue(column.getName()));
            hash.append('-');
        }
        hash.deleteCharAt(hash.lastIndexOf("-"));
        return hash.toString();
    }

    public SortedSet<Column> getColumns() {
        return metadata.getColumns();
    }
    
    public DatabaseObjectName getName() {
        return metadata.getName();
    }

    public PrimaryKey getPrimaryKey() {
        return metadata.getPrimaryKey();
    }

    public Collection<? extends ForeignKey> getForeignKeys() {
        return metadata.getImportedForeignKeys();
    }
    
    public SortedSet<Column> getPrimaryKeyColumns() {
        return metadata.getPrimaryKey().getColumns();
    }
    
    public SortedSet<Column> getForeignKeyColumns() {
        SortedSet<Column> columns = new TreeSet<Column>();
        for (ForeignKey foreignKey : metadata.getImportedForeignKeys()) {
            for (Column column : foreignKey.getReferencingColumns()) {
                columns.add(column);
            }
        }
        return columns;
    }
    

    public SortedSet<Column> getNaturalKeyColumns() {
        SortedSet<Column> columns = new TreeSet<Column>();
        UniqueKey natural = metadata.getNaturalKey();
        if (natural != null) {
        	columns.addAll(natural.getColumns());
        }
        return columns; 
    }
    
    public SortedSet<Column> getUniqueKeyColumns() {
        SortedSet<Column> columns = new TreeSet<Column>();
        for (UniqueKey ukey : metadata.getUniqueKeys()) {
            for (Column column : ukey.getColumns()) {
                columns.add(column);
            }
        }
        return columns;
    }

    public DataSet getDataSet() {
        return this.dataSet;
    }

    public String getRandomValue(ColumnName name) {
        int numRows = rows.size();
        Random random = new Random();
        int rowNum = random.nextInt(numRows);
        RowData row = rows.get(rowNum);
        return row.getValue(name);
    }

    public int getRowCount() {
        return rows.size();
    }

    public Table getMetadata() {
        return metadata;
    }
   
    
}
