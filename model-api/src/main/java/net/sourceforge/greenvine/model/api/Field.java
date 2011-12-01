package net.sourceforge.greenvine.model.api;

import net.sourceforge.greenvine.model.naming.FieldName;

public interface Field extends FieldNamedObject {
    
    public abstract FieldName getName();
    
    public abstract FieldCollection getFieldCollection();
    
    public abstract boolean getNotNull();

}
