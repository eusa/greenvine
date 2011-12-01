package net.sourceforge.greenvine.dbextractor.impl;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.greenvine.model.naming.DatabaseObjectName;
import net.sourceforge.greenvine.model.naming.impl.DatabaseObjectNameImpl;

public class ExcludeOnlyStrategy implements InclusionStrategy {

    private transient final List<DatabaseObjectNameImpl> excluded = new ArrayList<DatabaseObjectNameImpl>();
    
    public ExcludeOnlyStrategy(final List<DatabaseObjectNameImpl> excluded) {
        this.excluded.addAll(excluded);
    }
    
    public boolean isIncluded(final DatabaseObjectName name) {
        return !this.excluded.contains(name);
    }

}
