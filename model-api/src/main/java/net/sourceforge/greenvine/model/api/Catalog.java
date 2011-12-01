package net.sourceforge.greenvine.model.api;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import net.sourceforge.greenvine.model.naming.ModelName;
import net.sourceforge.greenvine.model.naming.Name;
import net.sourceforge.greenvine.model.naming.NameContainer;

public interface Catalog extends NamedObject, NameContainer {

    public abstract ModelName getName();

    public abstract Model getModel();

    public abstract List<? extends Entity> getEntities();

    public abstract int getEntityCount();

    public abstract Entity getEntityByName(CharSequence name)
            throws ModelException;

    public abstract Database getDatabase();

    public abstract Entity getEntityByTable(Table table)
            throws ModelException;

    /**
     * Returns all entities in order
     * of dependency
     * @return {@link Set} of {@link Entity} 
     * @throws ModelException 
     */
    public abstract Set<? extends Entity> getDependencyGraphFromRoot()
            throws ModelException;

    public abstract SortedSet<Name> getNames();

}