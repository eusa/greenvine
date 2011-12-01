package net.sourceforge.greenvine.dbextractor.impl;

import net.sourceforge.greenvine.model.naming.DatabaseObjectName;

public class IncludeAllStrategy implements InclusionStrategy {

    public boolean isIncluded(DatabaseObjectName name) {
        return true;
    }

}
