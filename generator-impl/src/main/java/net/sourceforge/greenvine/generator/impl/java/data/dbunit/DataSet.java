package net.sourceforge.greenvine.generator.impl.java.data.dbunit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.naming.DatabaseObjectName;

public class DataSet {
    
    private final List<TableData> tables = new ArrayList<TableData>();
    
    public Collection<TableData> getTables() {
        return tables;
    }
    
    
    
    public TableData createTable(Table metadata) {
        TableData table = new TableData(metadata, this);
        this.tables.add(table);
        return table;
    }



    public TableData getTable(DatabaseObjectName name) {
        for (TableData table : tables) {
            if (table.getName().equals(name)) {
                return table;
            }
        }
        throw new IllegalArgumentException(String.format("Table %s not found in data set.", name));
    }
    
}
