package net.sourceforge.greenvine.model.naming;

import java.util.SortedSet;

import net.sourceforge.greenvine.model.api.ModelException;

public interface NameContainer {
    
    public SortedSet<? extends Name> getNames();
    
    public boolean containsName(CharSequence name) throws ModelException;

}
