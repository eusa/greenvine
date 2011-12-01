package net.sourceforge.greenvine.model.api;

import java.util.Set;
import java.util.SortedSet;

public interface Key extends DatabaseObject, TableObject, ColumnCollection {

    /**
     * Return all foreign keys that have
     * referencing columns within this
     * Key - including the case
     * where the entire Key is also 
     * a ForeignKey
     */
    public Set<? extends ForeignKey> getForeignKeys();
    
    /**
     * Returns a ForeignKey only if all of the
     * key columns are the entire referencing columns
     * 
     * @return
     */
    public ForeignKey getForeignKey();
    
    /**
     * Returns true of all of the columns
     * in the PrimaryKey are also the referencing
     * column of a ForeignKey
     * @return
     */
    public boolean isForeignKey();
    
    /**
     * Return all columns in the Key
     * that are not referencing columns
     * in a foreign key
     * @return
     */
    public SortedSet<Column> getNonForeignColumns();

}