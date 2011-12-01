package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.naming.NameExtractor;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConvention;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;

public class PrimaryKeyNameExtractor implements NameExtractor<Table, Database> {

    public DatabaseObjectNameImpl extractName(Database container, Table table) throws ModelException {
        return getPrimaryKeyName(table);
    }
    
    private DatabaseObjectNameImpl getPrimaryKeyName(Table table) throws ModelException {
        RdbmsNamingConventions namingConvention = table.getDatabase().getNamingConventions();
        RdbmsNamingConvention pk = namingConvention.getPrimaryKey();
        RdbmsNamingConvention tbl = namingConvention.getTable();
        RdbmsNameSegmentBuilderImpl tableNameBuilder = new RdbmsNameSegmentBuilderImpl(table.getName().getObjectName(), tbl);
        tableNameBuilder.removeSuffixPrefixIncludingSeparators();
        RdbmsNameSegmentBuilderImpl pkName = new RdbmsNameSegmentBuilderImpl(tableNameBuilder, pk).surroundWithSuffixAndPrefix();
        return new DatabaseObjectNameImpl(table.getName().getSchemaName(), pkName.toCase());
    }
}
