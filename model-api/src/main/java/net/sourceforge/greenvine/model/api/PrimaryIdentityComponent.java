package net.sourceforge.greenvine.model.api;

import java.util.SortedSet;

public interface PrimaryIdentityComponent extends ComponentIdentity, Identity {
    
    public abstract SortedSet<? extends IdentityField> getSimpleProperties();
    
    public abstract IdentityField getSimpleProperty(CharSequence name) throws ModelException;
    
    public abstract int getSimplePropertyCount();

    
    
}
