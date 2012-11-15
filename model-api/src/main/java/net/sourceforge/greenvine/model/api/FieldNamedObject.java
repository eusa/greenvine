package net.sourceforge.greenvine.model.api;

import net.sourceforge.greenvine.model.naming.FieldName;

public interface FieldNamedObject extends NamedObject, Comparable<FieldNamedObject> {
    
    public FieldName getName();

}
