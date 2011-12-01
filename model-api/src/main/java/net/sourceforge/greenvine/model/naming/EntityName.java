package net.sourceforge.greenvine.model.naming;

import net.sourceforge.greenvine.model.naming.impl.CamelCaseNameSegmentImpl;

public interface EntityName extends CompoundName<CamelCaseNameSegmentImpl> {

    public CamelCaseNameSegment getObjectName();
    
    public CamelCaseNameSegment getNamespace();
    
}