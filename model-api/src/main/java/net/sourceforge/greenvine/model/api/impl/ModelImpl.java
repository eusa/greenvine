package net.sourceforge.greenvine.model.api.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import net.sourceforge.greenvine.model.api.Catalog;
import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.Model;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.NamedObject;
import net.sourceforge.greenvine.model.naming.ModelName;
import net.sourceforge.greenvine.model.naming.Name;
import net.sourceforge.greenvine.model.naming.impl.ModelNameImpl;

public class ModelImpl implements Model {

    private final ModelNameImpl name;
    private final Map<ModelNameImpl, CatalogImpl> catalogs;
    private final Object locker = new Object();
    
    public ModelImpl(CharSequence name) throws ModelException {
 
        // Initialise parameters
        this.name = new ModelNameImpl(name);
        this.catalogs = new TreeMap<ModelNameImpl, CatalogImpl>();
    }

    public ModelNameImpl getName() {
        return this.name;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Model#getCatalogs()
     */
    public SortedSet<CatalogImpl> getCatalogs() {
        return new TreeSet<CatalogImpl>(this.catalogs.values());
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Model#getCatalogByName(java.lang.CharSequence)
     */
    public Catalog getCatalogByName(CharSequence name) throws ModelException {
        return this.catalogs.get(new ModelNameImpl(name));
    }
    
    void addCatalog(CatalogImpl catalog) throws ModelException {
        synchronized (locker) {
            if (this.catalogs.containsKey(catalog.getName())) {
                throw new ModelException(String.format("Catalog named %s already exists.", catalog));
            }
            this.catalogs.put(catalog.getName(), catalog);
        }
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Model#getCatalogCount()
     */
    public int getCatalogCount() {
        return this.catalogs.size();
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Model#getCatalog(int)
     */
    public CatalogImpl getCatalog(int ordinal) {
        return new ArrayList<CatalogImpl>(this.catalogs.values()).get(ordinal);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Model#createCatalog(java.lang.CharSequence, net.sourceforge.greenvine.model.api.Database)
     */
    public CatalogImpl createCatalog(CharSequence name, Database database) throws ModelException {
        return new CatalogImpl(name, this, database);
    }
    
    public int compareTo(NamedObject obj) {
        return this.name.compareTo(obj.getName());
    }

    public boolean containsName(CharSequence name) throws ModelException {
        ModelName catName = new ModelNameImpl(name);
        synchronized(locker) {
            if (this.catalogs.containsKey(catName)) {
                return true;
            }
            return false;
        }
    }

    public SortedSet<? extends Name> getNames() {
        return Collections.unmodifiableSortedSet(new TreeSet<ModelNameImpl>(this.catalogs.keySet()));
    }

}
