package net.sourceforge.greenvine.model.api;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.EntityName;
import net.sourceforge.greenvine.model.naming.FieldName;

public interface Entity extends SimpleComponent {
    
    public abstract EntityName getName();

    public abstract Catalog getCatalog();

    public abstract Table getTable();

    public abstract boolean getReadOnly();

    public abstract Identity getIdentity();

    public abstract SimpleIdentity getSimpleIdentity();

    public abstract ComponentIdentity getComponentIdentity();

    public abstract OneToOneChildIdentity getConstrainedIdentity();

    public abstract SortedSet<? extends ManyToManyAssociationField> getManyToManies();

    public abstract ManyToManyAssociationField getManyToMany(
            CharSequence name) throws ModelException;

    public abstract int getManyToManyCount();

    public abstract SortedSet<? extends ManyToOneAssociationField> getManyToOneAssociations();

    public abstract ManyToOneAssociationField getManyToOneAssociation(
            CharSequence name) throws ModelException;

    public abstract int getManyToOneAssociationCount();

    public abstract SortedSet<? extends OneToManyAssociationField> getOneToManyAssociations();

    public abstract OneToManyAssociationField getOneToManyAssociation(
            CharSequence name) throws ModelException;

    public abstract int getOneToManyAssociationCount();

    public abstract SortedSet<? extends OneToOneAssociationField> getOneToOneAssociations();

    public abstract OneToOneAssociationField getOneToOneAssociation(
            CharSequence name) throws ModelException;

    public abstract int getOneToOneAssociationCount();

    public abstract SortedSet<? extends OneToOneParentField> getOneToOneParents();

    public abstract OneToOneParentField getOneToOneParent(CharSequence name)
            throws ModelException;

    public abstract int getOneToOneParentCount();

    public abstract SortedSet<? extends OneToOneChildField> getOneToOneChilds();

    public abstract OneToOneChildField getOneToOneChild(CharSequence name)
            throws ModelException;

    public abstract int getOneToOneChildCount();

    public abstract SortedSet<? extends OneToManyField> getOneToManies();

    public abstract OneToManyField getOneToMany(CharSequence name)
            throws ModelException;

    public abstract int getOneToManyCount();

    public abstract NaturalIdentity getNaturalIdentity();

    public abstract SimpleNaturalIdentity getSimpleNaturalIdentity();

    public abstract ComponentNaturalIdentity getComponentNaturalIdentity();

    public abstract OneToOneChildNaturalIdentity getOneToOneNaturalIdentity();

    public abstract Collection<? extends UniqueKey> getUniqueKeys();

    public abstract PrimaryKey getPrimaryKey();
    
    public abstract UniqueKey getNaturalKey();

    public abstract SortedSet<ColumnName> getPrimaryKeyColumnNames();

    /**
     * Find all other entities that this entity
     * depends upon.
     * @return
     */
    public abstract Map<FieldName, Entity> findDependencies();

    public abstract boolean hasDependencies();

    /** 
     * Return a unique collection
     * of entities that this entity 
     * depends on.
     * @return
     */
    public abstract Set<? extends Entity> getUniqueDependentEntities();

    public abstract Set<? extends Entity> getAllRelatedEntities();

    public abstract SortedSet<? extends CollectionField> getCollectionFields();

    public abstract CollectionField getCollectionFieldByName(CharSequence name)
            throws ModelException;

    public abstract int getCollectionFieldCount();

    public abstract SortedSet<? extends SimpleProperty> getSimpleProperties();

    public abstract SimpleProperty getSimpleProperty(CharSequence name)
            throws ModelException;

    public abstract int getSimplePropertyCount();

    public abstract ManyToOneAggregationField getManyToOne(CharSequence name)
            throws ModelException;

    public abstract int getManyToOneCount();

    public abstract SortedSet<? extends ManyToOneAggregationField> getManyToOnes();

   
}