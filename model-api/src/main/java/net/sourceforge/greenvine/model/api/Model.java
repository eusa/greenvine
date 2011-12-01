package net.sourceforge.greenvine.model.api;

import java.util.SortedSet;

import net.sourceforge.greenvine.model.naming.NameContainer;

public interface Model extends NamedObject, NameContainer {

    public abstract SortedSet<? extends Catalog> getCatalogs();

    public abstract Catalog getCatalogByName(CharSequence name)
            throws ModelException;

    public abstract int getCatalogCount();

    public abstract Catalog getCatalog(int ordinal);

}