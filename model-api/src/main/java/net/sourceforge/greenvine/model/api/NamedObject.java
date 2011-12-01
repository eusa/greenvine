package net.sourceforge.greenvine.model.api;

import net.sourceforge.greenvine.model.naming.Name;

public interface NamedObject extends Comparable<NamedObject> {
    
    public abstract Name getName();

}
