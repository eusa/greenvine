package net.sourceforge.greenvine.generator.impl.java.data.dbunit;

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.naming.ColumnName;

public class RowData {
    
    private final Table table;
    
    public RowData(Table table) {
        this.table = table;
    }
    
    private final Map<ColumnName, String> data = new HashMap<ColumnName, String>();
    
    public void put(ColumnName column, String value) {
        if (table.getColumnNames().contains(column)) {
            data.put(column, value);
        } else {
            throw new IllegalArgumentException(String.format("Illegal column name %s.", column));
        }
    }

    public String getValue(ColumnName column) {
        if (table.getColumnNames().contains(column)) {
            return data.get(column);
        } else {
            throw new IllegalArgumentException(String.format("Illegal column name %s.", column));
        }
    }

    

    public boolean hasValue(ColumnName name) {
        return data.containsKey(name);
    }

}
