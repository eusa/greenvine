package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.naming.NameExtractor;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConvention;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;

public class UniqueKeyNameExtractor implements NameExtractor<Table, Database> {

    public DatabaseObjectNameImpl extractName(Database container, Table table) throws ModelException {
        return getUniqueKeyName(table);
    }
    
    private DatabaseObjectNameImpl getUniqueKeyName(Table table) throws ModelException {
        // Get conventions
        RdbmsNamingConventions namingConvention = table.getDatabase().getNamingConventions();
        RdbmsNamingConvention uk = namingConvention.getUniqueKey();
        RdbmsNamingConvention tbl = namingConvention.getTable();
        
        // Base on table name
        RdbmsNameSegmentBuilderImpl tblNameBuilder = new RdbmsNameSegmentBuilderImpl(table.getName().getObjectName(), tbl);
        tblNameBuilder.removeSuffixPrefixIncludingSeparators();
        
        // Suffix is the unique key number
        Integer suffix = Integer.valueOf(table.getUniqueKeyCount() + 1);
        tblNameBuilder.append(suffix.toString());
        
        // Add unique naming conventions
        RdbmsNameSegmentBuilderImpl ukName = new RdbmsNameSegmentBuilderImpl(tblNameBuilder, uk);
        ukName.surroundWithSuffixAndPrefix().toCase();
        
        // Note unique key has same schema as table
        return new DatabaseObjectNameImpl(table.getName().getSchemaName(), ukName);
    }

}
