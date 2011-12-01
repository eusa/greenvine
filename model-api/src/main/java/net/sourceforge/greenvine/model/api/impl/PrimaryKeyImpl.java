package net.sourceforge.greenvine.model.api.impl;

import java.util.SortedSet;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PrimaryKey;

public class PrimaryKeyImpl extends AbstractKey implements PrimaryKey {
    
    PrimaryKeyImpl(CharSequence name, SortedSet<? extends CharSequence> columns, TableImpl table) throws ModelException {
        super(name, columns, table);
        
        // Check no nullable columns are included
        validateColumns();
        
        // Set references back to self
        table.getDatabase().addPrimaryKey(this);
        table.setPrimaryKey(this);
    }

    private void validateColumns() throws ModelException {
        for (Column column : this.getColumns()) {
            if (!column.getNotNull()) {
                throw new ModelException(String.format("Nullable column in primary key %s", this.getName()));
            }
        }
    }
    
    @Override
    public String toString() {
        return "PrimaryKey [name=" + name + ", columns=" + columns + "]";
    }

}
