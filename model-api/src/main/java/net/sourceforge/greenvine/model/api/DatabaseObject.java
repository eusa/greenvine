package net.sourceforge.greenvine.model.api;

import net.sourceforge.greenvine.model.naming.DatabaseObjectName;

public interface DatabaseObject extends NamedObject {

    public abstract DatabaseObjectName getName();
    
    public abstract Database getDatabase();

}