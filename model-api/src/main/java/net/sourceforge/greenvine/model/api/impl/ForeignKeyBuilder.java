package net.sourceforge.greenvine.model.api.impl;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.DatabaseObjectName;
import net.sourceforge.greenvine.model.naming.impl.ColumnNameImpl;
import net.sourceforge.greenvine.model.naming.impl.DatabaseObjectNameImpl;

public class ForeignKeyBuilder {
    
    private final DatabaseObjectNameImpl name;
    private final DatabaseObjectNameImpl referencingTable;
    private final DatabaseObjectNameImpl referencedTable;
    private final Map<ColumnNameImpl, ColumnNameImpl> mapping;
    private final DatabaseImpl database;
    
    public ForeignKeyBuilder(DatabaseObjectNameImpl name, DatabaseObjectNameImpl referencingTable,
            DatabaseObjectNameImpl referencedTable, DatabaseImpl database) throws ModelException {
        super();
        this.name = name;
        this.referencingTable = referencingTable;
        this.referencedTable = referencedTable;
        this.mapping = new TreeMap<ColumnNameImpl, ColumnNameImpl>();
        this.database = database;
    }

    public DatabaseObjectName getName() {
        return name;
    }

    public DatabaseObjectName getReferencingTable() {
        return referencingTable;
    }

    public DatabaseObjectName getReferencedTable() {
        return referencedTable;
    }

    public Map<ColumnNameImpl, ColumnNameImpl> getColumnMapping() {
        return Collections.unmodifiableMap(this.mapping);
    }
 
    public void addColumnMapping(CharSequence referencingColumn, CharSequence referencedColumn) throws ModelException  {
        this.mapping.put(new ColumnNameImpl(referencingColumn), new ColumnNameImpl(referencedColumn));
    }
    
    public ForeignKey buildForeignKey() throws ModelException {
        return new ForeignKeyImpl(name, referencingTable, referencedTable, mapping, database);
    }

}
