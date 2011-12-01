package net.sourceforge.greenvine.model.api;

import java.util.SortedSet;

import net.sourceforge.greenvine.model.api.impl.ManyToOneIdentityField;

public interface ComponentIdentity extends ComponentField {
    
    public abstract Entity getFieldCollection();
    
    public abstract SortedSet<ManyToOneIdentityField> getManyToOnes();
    
    public abstract ManyToOneIdentityField getManyToOne(CharSequence name) throws ModelException;
    
    public abstract int getManyToOneCount();
    
}
