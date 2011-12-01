package net.sourceforge.greenvine.model.api;

import net.sourceforge.greenvine.model.naming.RdbmsName;

public interface RdbmsNamedObject extends NamedObject {
    
    public RdbmsName getName();

}
