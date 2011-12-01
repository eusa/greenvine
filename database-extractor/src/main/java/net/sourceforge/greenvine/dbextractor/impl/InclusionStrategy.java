package net.sourceforge.greenvine.dbextractor.impl;

import net.sourceforge.greenvine.model.naming.DatabaseObjectName;

public interface InclusionStrategy {
    
    public abstract boolean isIncluded(DatabaseObjectName name);

}
