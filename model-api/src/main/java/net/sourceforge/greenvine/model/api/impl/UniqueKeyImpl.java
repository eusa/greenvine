package net.sourceforge.greenvine.model.api.impl;

import java.util.SortedSet;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.UniqueKey;


public class UniqueKeyImpl extends AbstractKey implements UniqueKey {

    public UniqueKeyImpl(CharSequence name, SortedSet<? extends CharSequence> cols, TableImpl table) throws ModelException {
        super(name, cols, table);
        
        // Set references back to self
        table.getDatabase().addUniqueKey(this);
        table.addUniqueKey(this);
    }
    
    @Override
    public String toString() {
        return "UniqueKey [name=" + name + ", columns=" + columns + "]";
    }
    
}