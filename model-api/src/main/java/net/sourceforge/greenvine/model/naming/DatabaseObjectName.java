package net.sourceforge.greenvine.model.naming;

import net.sourceforge.greenvine.model.naming.impl.RdbmsNameSegmentImpl;

public interface DatabaseObjectName extends CompoundName<RdbmsNameSegmentImpl> {

    public RdbmsNameSegment getObjectName();
    
    public RdbmsNameSegment getSchemaName();
    
}