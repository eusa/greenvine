package net.sourceforge.greenvine.model.api;

import java.util.SortedSet;

import net.sourceforge.greenvine.model.api.impl.PrimaryKeyImpl;


public interface Identity extends Field {
    
    /**
     * Get the {@link Entity} that owns this identity
     */
    abstract public Entity getFieldCollection();
    
    /**
     * Get all columns mapped by this {@link Identity}
     * @return
     */
    abstract public SortedSet<? extends Column> getColumns();
    
    /**
     * Get the {@link PrimaryKeyImpl} that this
     * identity corresponds to.
     * @return
     */
    abstract public PrimaryKey getPrimaryKey();

}
