package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.DatabaseObject;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.NamedObject;
import net.sourceforge.greenvine.model.naming.impl.DatabaseObjectNameImpl;

public abstract class AbstractDatabaseObject implements DatabaseObject {

    protected final DatabaseObjectNameImpl name;
    protected final DatabaseImpl database;

    public AbstractDatabaseObject(CharSequence name, DatabaseImpl database) throws ModelException {
        
        // Validate database parameter
        if (database == null) {
            throw new ModelException(String.format("Cannot create a database object with a null database"));
        }
        
        // Assign fields
        this.name = DatabaseObjectNameImpl.parse(name);
        this.database = database;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.DatabaseObject#getDatabase()
     */
    public DatabaseImpl getDatabase() {
        return this.database;
    }
    
    public DatabaseObjectNameImpl getName() {
        return this.name;
    }
    
    public int compareTo(NamedObject obj) {
        return this.name.compareTo(obj.getName());
    }

}