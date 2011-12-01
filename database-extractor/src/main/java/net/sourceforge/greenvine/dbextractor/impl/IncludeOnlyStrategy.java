package net.sourceforge.greenvine.dbextractor.impl;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.greenvine.model.naming.DatabaseObjectName;
import net.sourceforge.greenvine.model.naming.impl.DatabaseObjectNameImpl;

public class IncludeOnlyStrategy implements InclusionStrategy {

    private final List<DatabaseObjectNameImpl> included = new ArrayList<DatabaseObjectNameImpl>();
    
    public IncludeOnlyStrategy(List<DatabaseObjectNameImpl> included) {
        this.included.addAll(included);
    }
    
    public boolean isIncluded(DatabaseObjectName name) {
        return this.included.contains(name);
    }

}
