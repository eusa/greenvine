package net.sourceforge.greenvine.model.naming.testutils;

import net.sourceforge.greenvine.model.naming.CaseStrategy;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConvention;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;

public class NamingConventionFactory {
    
    public static RdbmsNamingConventions getTestNamingConvention() {
        
        RdbmsNamingConventions nc = new RdbmsNamingConventions();
        nc.setDatabase(new RdbmsNamingConvention("_", null, "DB", "^([A-Za-z_0-9]+)*$", CaseStrategy.UPPERCASE));
        nc.setTable(new RdbmsNamingConvention("_", "TBL", null, "^([A-Za-z_0-9]+)*$", CaseStrategy.UPPERCASE));
        nc.setView(new RdbmsNamingConvention("_", "VIEW", null, "^([A-Za-z_0-9]+)*$", CaseStrategy.UPPERCASE));
        nc.setPrimaryColumn(new RdbmsNamingConvention("_", "PK", null, "^([A-Za-z_0-9]+)*$", CaseStrategy.UPPERCASE));
        nc.setForeignColumn(new RdbmsNamingConvention("_", "FK", "ID", "^([A-Za-z_0-9]+)*$", CaseStrategy.UPPERCASE));
        nc.setDataColumn(new RdbmsNamingConvention("_", null, null, "^([A-Za-z_0-9]+)*$", CaseStrategy.UPPERCASE));
        nc.setForeignKey(new RdbmsNamingConvention("_", "FK", null, "^([A-Za-z_0-9]+)*$", CaseStrategy.UPPERCASE));
        nc.setPrimaryKey(new RdbmsNamingConvention("_", "PK", null, "^([A-Za-z_0-9]+)*$", CaseStrategy.UPPERCASE));
        nc.setUniqueKey(new RdbmsNamingConvention("_", "UK", "UNIQUE", "^([A-Za-z_0-9]+)*$", CaseStrategy.UPPERCASE));
        return nc;
        
    }

}
