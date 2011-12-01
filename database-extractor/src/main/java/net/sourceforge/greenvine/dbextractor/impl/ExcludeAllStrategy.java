package net.sourceforge.greenvine.dbextractor.impl;

import net.sourceforge.greenvine.model.naming.DatabaseObjectName;

public class ExcludeAllStrategy implements InclusionStrategy {

    public boolean isIncluded(final DatabaseObjectName name) {
        return false;
    }

}
