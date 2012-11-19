package net.sourceforge.greenvine.model.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import net.sourceforge.greenvine.model.api.Catalog;
import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.ManyToManyRelation;
import net.sourceforge.greenvine.model.api.ManyToOneRelation;
import net.sourceforge.greenvine.model.api.Model;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.NamedObject;
import net.sourceforge.greenvine.model.api.OneToManyAssociationRelation;
import net.sourceforge.greenvine.model.api.OneToOneAssociationRelation;
import net.sourceforge.greenvine.model.api.OneToOneIdentityRelation;
import net.sourceforge.greenvine.model.api.OneToOneNaturalIdentityRelation;
import net.sourceforge.greenvine.model.api.OneToOneRelation;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.naming.EntityName;
import net.sourceforge.greenvine.model.naming.Name;
import net.sourceforge.greenvine.model.naming.impl.EntityNameImpl;
import net.sourceforge.greenvine.model.naming.impl.ModelNameImpl;

public class CatalogImpl implements Catalog, Comparable<CatalogImpl> {

    private final ModelNameImpl name;
    private final Map<EntityName, EntityImpl> entities;
    private final Database database;
    private final ModelImpl model;
    private final Object locker = new Object();
    
    
    CatalogImpl(CharSequence name, ModelImpl model, Database database) throws ModelException {
        
        // Validate
        if (model == null) {
            throw new ModelException(String.format("Null Data container supplied for catalog %s.", name));
        }
        if (database == null) {
            throw new ModelException(String.format("Null Database supplied for catalog %s.", name));
        }
        
        // Initialise fields
        this.name = new ModelNameImpl(name);
        this.entities = new TreeMap<EntityName, EntityImpl>();
        this.database = database; 
        this.model = model;
        
        // Add reference back to Data
        this.model.addCatalog(this);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#getName()
     */
    public ModelNameImpl getName() {
        return this.name;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#getModel()
     */
    public Model getModel() {
        return this.model;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#getEntities()
     */
    public List<EntityImpl> getEntities() {
        return Collections.unmodifiableList(new ArrayList<EntityImpl>(entities.values()));
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#getEntityCount()
     */
    public int getEntityCount() {
        return entities.size();
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#getEntityByName(java.lang.CharSequence)
     */
    public EntityImpl getEntityByName(CharSequence name) throws ModelException {
        EntityImpl entity = entities.get(EntityNameImpl.parse(name));
        if (entity == null) {
            throw new ModelException(String.format("Entity %s not found.", name));
        }
        return entity;
    }
 
    void addEntity(EntityImpl entity) throws ModelException {
        synchronized(locker) {
            if (this.entities.containsKey(entity.getName())) {
                throw new ModelException(String.format("Model %s already contains field collection with name %s.", this.name, name));
            }
            entities.put(entity.getName(), entity);
        }
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#createEntity(java.lang.CharSequence, java.lang.CharSequence, java.lang.CharSequence)
     */
    public EntityImpl createEntity(CharSequence namespace, CharSequence name, CharSequence dataContainer) throws ModelException {
        return new EntityImpl(namespace, name, dataContainer, this);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#getDatabase()
     */
    public Database getDatabase() {
        return this.database;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#getEntityByTable(net.sourceforge.greenvine.model.api.DataContainer)
     */
    public EntityImpl getEntityByTable(Table table) throws ModelException {
        if (this.entities != null) {
            for (EntityImpl entity : this.entities.values()) {
                if (entity.getTable().getName().equals(table.getName())) {
                    return entity;
                }
            }
        }
        throw new ModelException(String.format("No entity found matching table %s.", table.getName()));
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#createNaturalIdentityOneToOneRelation(java.lang.CharSequence, java.lang.CharSequence, java.lang.CharSequence, java.lang.CharSequence)
     */
    public OneToOneNaturalIdentityRelation createNaturalIdentityOneToOneRelation(CharSequence ownerFieldName, CharSequence inverseFieldName, CharSequence foreignKey, CharSequence uniqueKey) throws ModelException {
        return new OneToOneNaturalIdentityRelationImpl(ownerFieldName, inverseFieldName, foreignKey, uniqueKey, this);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#createBiDirectionalConstrainedOneToOneRelationship(java.lang.CharSequence, java.lang.CharSequence, java.lang.CharSequence)
     */
    public OneToOneIdentityRelation createBiDirectionalConstrainedOneToOneRelationship(CharSequence ownerFieldName, CharSequence inverseFieldName, CharSequence foreignKey) throws ModelException {
        return new OneToOneIdentityRelationImpl(ownerFieldName, inverseFieldName, foreignKey, this);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#createUniDirectionalConstrainedOneToOneRelationship(java.lang.CharSequence, java.lang.CharSequence)
     */
    public OneToOneIdentityRelation createUniDirectionalConstrainedOneToOneRelationship(CharSequence inverseFieldName, CharSequence foreignKey) throws ModelException {
        return new OneToOneIdentityRelationImpl(inverseFieldName, foreignKey, this);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#createBiDirectionalManyToManyRelationship(java.lang.CharSequence, java.lang.CharSequence, java.lang.CharSequence, java.lang.CharSequence)
     */
    public ManyToManyRelation createBiDirectionalManyToManyRelationship(CharSequence ownerFieldName, 
            CharSequence ownerForeignKey,CharSequence inverseFieldName,  
            CharSequence inverseForeignKey) throws ModelException {
        return new ManyToManyRelationImpl(ownerFieldName, ownerForeignKey, inverseFieldName, inverseForeignKey, this);
    }
    
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#createUniDirectionalManyToMany(java.lang.CharSequence, java.lang.CharSequence, java.lang.CharSequence)
     */
    public ManyToManyRelation createUniDirectionalManyToMany(CharSequence inverseFieldName, CharSequence ownerForeignKey, CharSequence inverseForeignKey) throws ModelException {
        return new ManyToManyRelationImpl(inverseFieldName, ownerForeignKey, inverseForeignKey, this);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#createBiDirectionalManyToOneRelationshipViaAssociation(java.lang.CharSequence, net.sourceforge.greenvine.model.api.ForeignKey, java.lang.CharSequence, net.sourceforge.greenvine.model.api.ForeignKey)
     */
    public OneToManyAssociationRelation createBiDirectionalManyToOneRelationshipViaAssociation(
            CharSequence ownerFieldName, CharSequence ownerForeignKey, 
            CharSequence inverseFieldName, CharSequence inverseForeignKey) throws ModelException {
        return new OneToManyAssociationRelationImpl(ownerFieldName, ownerForeignKey, inverseFieldName, inverseForeignKey, this);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#createUniDirectionalManyToOneRelationshipViaAssociation(java.lang.CharSequence, net.sourceforge.greenvine.model.api.ForeignKey, net.sourceforge.greenvine.model.api.ForeignKey)
     */
    public OneToManyAssociationRelation createUniDirectionalManyToOneRelationshipViaAssociation(
            CharSequence inverseFieldName,
            CharSequence ownerForeignKey, CharSequence inverseForeignKey) throws ModelException {
        return new OneToManyAssociationRelationImpl(inverseFieldName, ownerForeignKey, inverseForeignKey, this);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#createBiDirectionalOneToOneRelationshipViaAssociation(java.lang.CharSequence, java.lang.CharSequence, java.lang.CharSequence, java.lang.CharSequence)
     */
    public OneToOneAssociationRelation createBiDirectionalOneToOneRelationshipViaAssociation(
            CharSequence ownerFieldName, CharSequence ownerForeignKey,  
            CharSequence inverseFieldName, CharSequence inverseForeignKey) throws ModelException {
        return new OneToOneAssociationRelationImpl(ownerFieldName, ownerForeignKey, inverseFieldName, inverseForeignKey, this);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#createUniDirectionalOneToOneRelationshipViaAssociation(java.lang.CharSequence, java.lang.CharSequence, java.lang.CharSequence)
     */
    public OneToOneAssociationRelation createUniDirectionalOneToOneRelationshipViaAssociation(
            CharSequence inverseFieldName,
            CharSequence ownerForeignKey, CharSequence inverseForeignKey) throws ModelException {
        return new OneToOneAssociationRelationImpl(inverseFieldName, ownerForeignKey, inverseForeignKey, this);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#createBiDirectionalOneToOneRelationship(java.lang.CharSequence, java.lang.CharSequence, java.lang.CharSequence)
     */
    public OneToOneRelation createBiDirectionalOneToOneRelationship(CharSequence ownerFieldName,
            CharSequence inverseFieldName, CharSequence foreignKey) throws ModelException {
        return new OneToOneRelationImpl(ownerFieldName, inverseFieldName, foreignKey, this);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#createUniDirectionalOneToOneRelationship(java.lang.CharSequence, java.lang.CharSequence)
     */
    public OneToOneRelation createUniDirectionalOneToOneRelationship(CharSequence inverseFieldName,
            CharSequence foreignKey) throws ModelException {
        return new OneToOneRelationImpl(inverseFieldName, foreignKey, this);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#createBiDirectionalManyToOneRelationship(java.lang.CharSequence, java.lang.CharSequence, java.lang.CharSequence)
     */
    public ManyToOneRelation createBiDirectionalManyToOneRelationship(CharSequence ownerFieldName,
            CharSequence inverseFieldName, CharSequence foreignKey) throws ModelException {
        return new ManyToOneRelationImpl(ownerFieldName, inverseFieldName, foreignKey, this);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#createUniDirectionalManyToOneRelationship(java.lang.CharSequence, java.lang.CharSequence)
     */
    public ManyToOneRelation createUniDirectionalManyToOneRelationship(CharSequence inverseFieldName, CharSequence foreignKey) throws ModelException {
        return new ManyToOneRelationImpl(inverseFieldName, foreignKey, this);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#getDependencyGraphFromRoot()
     */
    public Set<EntityImpl> getDependencyGraphFromRoot() throws ModelException {
        
        Collection<? extends Table> tables = database.getTablesInDependencyOrder();
        Set<EntityImpl> entities = new HashSet<EntityImpl>();
        for (Table table : tables) {
            if (!table.isAssociationTable()) {
                entities.add(getEntityByTable(table));
            }
        }
        return entities;
    }

    public int compareTo(NamedObject o) {
        return this.name.compareTo(o.getName());
    }

    public boolean containsName(CharSequence name) throws ModelException {
        EntityName entityName = EntityNameImpl.parse(name);
        synchronized(locker) {
            if (this.entities.containsKey(entityName)) {
                return true;
            }
        }
        return false;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Catalog#getNames()
     */
    public SortedSet<Name> getNames() {
        return Collections.unmodifiableSortedSet(new TreeSet<Name>(this.entities.keySet()));
    }

	@Override
	public int compareTo(CatalogImpl arg0) {
		return this.name.compareTo(arg0.getName());
	}
    
}
