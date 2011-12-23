package net.sourceforge.greenvine.model.api.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import net.sourceforge.greenvine.model.api.AggregationRelationChildField;
import net.sourceforge.greenvine.model.api.CollectionField;
import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ComponentNaturalIdentity;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.Field;
import net.sourceforge.greenvine.model.api.Identity;
import net.sourceforge.greenvine.model.api.ManyToManyAssociationField;
import net.sourceforge.greenvine.model.api.ManyToOneAggregationField;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.MutableFieldCollection;
import net.sourceforge.greenvine.model.api.NamedObject;
import net.sourceforge.greenvine.model.api.NaturalIdentity;
import net.sourceforge.greenvine.model.api.OneToOneChildField;
import net.sourceforge.greenvine.model.api.OneToOneChildIdentity;
import net.sourceforge.greenvine.model.api.OneToOneChildNaturalIdentity;
import net.sourceforge.greenvine.model.api.PrimaryKey;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.PropertyValueGenerationStrategy;
import net.sourceforge.greenvine.model.api.RelationField;
import net.sourceforge.greenvine.model.api.SimpleIdentity;
import net.sourceforge.greenvine.model.api.SimpleNaturalIdentity;
import net.sourceforge.greenvine.model.api.SimpleProperty;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.api.UniqueKey;
import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.EntityName;
import net.sourceforge.greenvine.model.naming.FieldName;
import net.sourceforge.greenvine.model.naming.impl.EntityNameImpl;

public class EntityImpl implements Entity, MutableFieldCollection {

    private final EntityNameImpl name;
    private final boolean readOnly;
    private final CatalogImpl catalog;
    private final Table table;
    private Identity identity; 
    private NaturalIdentity naturalIdentity;
    private final FieldCollectionDelegate fields;
    
    EntityImpl(CharSequence namespace, CharSequence name, CharSequence table, CatalogImpl catalog) throws ModelException {
        
        // Validate parameters
        if (catalog == null) {
            throw new ModelException(String.format("Catalog null in field collection %s.", name));
        }
        if (table == null) {
            throw new ModelException(String.format("Table null in field collection %s.", name));
        }
        
        // Initialise fields
        this.name = new EntityNameImpl(namespace, name);
        this.catalog = catalog;
        this.table = catalog.getDatabase().getTableByName(table);
        this.readOnly = this.table.isView();
        this.fields = new FieldCollectionDelegate(this);
        
        // Add back to model
        catalog.addEntity(this);
    }
    
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getCatalog()
     */
    public CatalogImpl getCatalog() {
        return this.catalog;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getTable()
     */
    public Table getTable() {
        return this.table;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getReadOnly()
     */
    public boolean getReadOnly() {
        return this.readOnly;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getIdentity()
     */
    public Identity getIdentity() {
        return this.identity;
    }
    
    void setIdentity(Identity identity) throws ModelException {
        
        if (this.identity != null) {
            throw new ModelException(String.format("Entity %s already has an identity %s", this, this.identity));
        }
        
        fields.addField(identity);
        this.identity = identity;
        
    }
    
    public SimpleIdentity createSimpleIdentity(CharSequence name, PropertyType propertyType, PropertyValueGenerationStrategy keyGenerationStrategy, CharSequence column) throws ModelException {
        return new SimpleIdentityImpl(name, propertyType, keyGenerationStrategy, column, this);
    }
    
    public ComponentIdentityImpl createComponentIdentity(CharSequence name) throws ModelException {
        return new ComponentIdentityImpl(name, this);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getSimpleIdentity()
     */
    public SimpleIdentityImpl getSimpleIdentity() {
        if (identity != null && identity instanceof SimpleIdentity) {
            return (SimpleIdentityImpl)identity;
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getComponentIdentity()
     */
    public ComponentIdentityImpl getComponentIdentity() {
        if (identity != null && identity instanceof ComponentIdentityImpl) {
            return (ComponentIdentityImpl)identity;
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getConstrainedIdentity()
     */
    public OneToOneChildIdentityImpl getConstrainedIdentity() {
        if (identity != null && identity instanceof OneToOneChildIdentity) {
            return (OneToOneChildIdentityImpl)identity;
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getManyToManies()
     */
    public SortedSet<ManyToManyAssociationFieldImpl> getManyToManies() {
        return fields.getFields(ManyToManyAssociationFieldImpl.class);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getManyToMany(java.lang.CharSequence)
     */
    public ManyToManyAssociationFieldImpl getManyToMany(CharSequence name) throws ModelException {
        return fields.getFieldByName(ManyToManyAssociationFieldImpl.class, name);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getManyToManyCount()
     */
    public int getManyToManyCount() {
        return fields.getFieldCount(ManyToManyAssociationFieldImpl.class);
    }
    
    public void addManyToMany(ManyToManyAssociationField manyToMany) throws ModelException {
        fields.addField(manyToMany);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getManyToOneAssociations()
     */
    public SortedSet<ManyToOneAssociationFieldImpl> getManyToOneAssociations() {
        return fields.getFields(ManyToOneAssociationFieldImpl.class);    
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getManyToOneAssociation(java.lang.CharSequence)
     */
    public ManyToOneAssociationFieldImpl getManyToOneAssociation(CharSequence name) throws ModelException {
        return fields.getFieldByName(ManyToOneAssociationFieldImpl.class, name);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getManyToOneAssociationCount()
     */
    public int getManyToOneAssociationCount() {
        return fields.getFieldCount(ManyToOneAssociationFieldImpl.class);
    }
    
    void addManyToOneAssociation(ManyToOneAssociationFieldImpl manyToOne) throws ModelException {
        fields.addField(manyToOne);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getOneToManyAssociations()
     */
    public SortedSet<OneToManyAssociationFieldImpl> getOneToManyAssociations() {
        return fields.getFields(OneToManyAssociationFieldImpl.class);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getOneToManyAssociation(java.lang.CharSequence)
     */
    public OneToManyAssociationFieldImpl getOneToManyAssociation(CharSequence name) throws ModelException {
        return fields.getFieldByName(OneToManyAssociationFieldImpl.class, name);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getOneToManyAssociationCount()
     */
    public int getOneToManyAssociationCount() {
        return fields.getFieldCount(OneToManyAssociationFieldImpl.class);
    }
    
    void addOneToManyAssociation(OneToManyAssociationFieldImpl oneToMany) throws ModelException {
        fields.addField(oneToMany);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getOneToOneAssociations()
     */
    public SortedSet<OneToOneAssociationFieldImpl> getOneToOneAssociations() {
        return fields.getFields(OneToOneAssociationFieldImpl.class);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getOneToOneAssociation(java.lang.CharSequence)
     */
    public OneToOneAssociationFieldImpl getOneToOneAssociation(CharSequence name) throws ModelException {
        return fields.getFieldByName(OneToOneAssociationFieldImpl.class, name);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getOneToOneAssociationCount()
     */
    public int getOneToOneAssociationCount() {
        return fields.getFieldCount(OneToOneAssociationFieldImpl.class);
    }
    
    void addOneToOneAssociation(OneToOneAssociationFieldImpl oneToOne) throws ModelException {
        fields.addField(oneToOne);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getOneToOneParents()
     */
    public SortedSet<OneToOneParentFieldImpl> getOneToOneParents() {
        return fields.getFields(OneToOneParentFieldImpl.class);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getOneToOneParent(java.lang.CharSequence)
     */
    public OneToOneParentFieldImpl getOneToOneParent(CharSequence name) throws ModelException {
        return fields.getFieldByName(OneToOneParentFieldImpl.class, name);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getOneToOneParentCount()
     */
    public int getOneToOneParentCount() {
        return fields.getFieldCount(OneToOneParentFieldImpl.class);
    }
    
    void addOneToOneParent(OneToOneParentFieldImpl oneToOne) throws ModelException {
        fields.addField(oneToOne);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getOneToOneChilds()
     */
    public SortedSet<OneToOneChildFieldImpl> getOneToOneChilds() {
        return fields.getFields(OneToOneChildFieldImpl.class);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getOneToOneChild(java.lang.CharSequence)
     */
    public OneToOneChildFieldImpl getOneToOneChild(CharSequence name) throws ModelException {
        return fields.getFieldByName(OneToOneChildFieldImpl.class, name);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getOneToOneChildCount()
     */
    public int getOneToOneChildCount() {
        return fields.getFieldCount(OneToOneChildFieldImpl.class);
    }
    
    void addOneToOneChild(OneToOneChildField oneToOne) throws ModelException {
        fields.addField(oneToOne);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getOneToManies()
     */
    public SortedSet<OneToManyFieldImpl> getOneToManies() {
        return fields.getFields(OneToManyFieldImpl.class);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getOneToMany(java.lang.CharSequence)
     */
    public OneToManyFieldImpl getOneToMany(CharSequence name) throws ModelException {
        return fields.getFieldByName(OneToManyFieldImpl.class, name);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getOneToManyCount()
     */
    public int getOneToManyCount() {
        return fields.getFieldCount(OneToManyFieldImpl.class);
    }
    
    void addOneToMany(OneToManyFieldImpl oneToMany) throws ModelException {
        fields.addField(oneToMany);
    }

    public NaturalIdentity createSimpleNaturalIdentity(CharSequence name, PropertyType propertyType, CharSequence column, UniqueKey uniqueKey) throws ModelException {
        return new SimpleNaturalIdentityImpl(name, propertyType, column, uniqueKey, this);
    }
    
    public ComponentNaturalIdentityImpl createNaturalIdentity(CharSequence name, UniqueKey uniqueKey) throws ModelException {
        return new ComponentNaturalIdentityImpl(name, uniqueKey, this);
    }
    
    
    void setNaturalIdentity(NaturalIdentity naturalIdentity) throws ModelException {
        fields.addField(naturalIdentity);
        this.naturalIdentity = naturalIdentity;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getNaturalIdentity()
     */
    public NaturalIdentity getNaturalIdentity() {
        return this.naturalIdentity;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getSimpleNaturalIdentity()
     */
    public SimpleNaturalIdentityImpl getSimpleNaturalIdentity() {
        if (naturalIdentity != null && naturalIdentity instanceof SimpleNaturalIdentity) {
            return (SimpleNaturalIdentityImpl)naturalIdentity;
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getComponentNaturalIdentity()
     */
    public ComponentNaturalIdentity getComponentNaturalIdentity() {
        if (naturalIdentity != null && naturalIdentity instanceof ComponentNaturalIdentityImpl) {
            return (ComponentNaturalIdentityImpl)naturalIdentity;
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getOneToOneNaturalIdentity()
     */
    public OneToOneChildNaturalIdentityImpl getOneToOneNaturalIdentity() {
        if (naturalIdentity != null && naturalIdentity instanceof OneToOneChildNaturalIdentity) {
            return (OneToOneChildNaturalIdentityImpl)naturalIdentity;
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getUniqueKeys()
     */
    public Collection<? extends UniqueKey> getUniqueKeys() {
        return table.getUniqueKeys();
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getPrimaryKey()
     */
    public PrimaryKey getPrimaryKey() {
        return table.getPrimaryKey();
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Entity#getNaturalKey()
     */
    public UniqueKey getNaturalKey() {
        return table.getNaturalKey();
    }
    
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getPrimaryKeyColumnNames()
     */
    public SortedSet<ColumnName> getPrimaryKeyColumnNames() {
        return table.getPrimaryKey().getColumnNames();
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#findDependencies()
     */
    public Map<FieldName, Entity> findDependencies() {
        
        // Map of dependencies
        Map<FieldName, Entity> dependencies = new HashMap<FieldName, Entity>();
        
        // Iterate and recurse through aggregation child fields
        for (AggregationRelationChildField relation : fields.getFields(AggregationRelationChildField.class)) {
            if (relation.isDependency()) {
                FieldName dependencyName = relation.getName();
                Entity dependencyEntity = relation.getRelation().getParentEntity();
                dependencies.put(dependencyName, dependencyEntity);
            }
        }
        
        return dependencies;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#hasDependencies()
     */
    public boolean hasDependencies() {
        if (fields.getFieldCount(AggregationRelationChildField.class) > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getUniqueDependentEntities()
     */
    public Set<Entity> getUniqueDependentEntities() {
        
        // Set of dependencies
        Set<Entity> dependencies = new HashSet<Entity>();
        
        // Iterate and recurse through dependencies
        for (AggregationRelationChildField oneToOne : fields.getFields(AggregationRelationChildField.class)) {
            if (oneToOne.isDependency()) {
                Entity dependencyEntity = oneToOne.getRelation().getParentEntity();
                dependencies.add(dependencyEntity);
            }
        }
        return dependencies;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getAllRelatedEntities()
     */
    public Set<Entity> getAllRelatedEntities() {
        Set<Entity> relations = new HashSet<Entity>();
        for (RelationField field : fields.getFields(RelationField.class)) {
            relations.add(field.getRelatedEntity());
        }
        return relations;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getCollectionFields()
     */
    public SortedSet<CollectionField> getCollectionFields() {
        return fields.getFields(CollectionField.class);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getCollectionFieldByName(java.lang.CharSequence)
     */
    public CollectionField getCollectionFieldByName(CharSequence name) throws ModelException {
        return fields.getFieldByName(CollectionField.class, name);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getCollectionFieldCount()
     */
    public int getCollectionFieldCount() {
        return fields.getFieldCount(CollectionField.class);
    }
    
    @Override
    public String toString() {
        return "EntityImpl [name=" + name + ", table=" + table.getName() + ", identity="
                + identity + ", fields="
                + fields + ", readOnly=" + readOnly + "]";
    }


    public void addSimpleProperty(SimpleProperty simpleProperty)
            throws ModelException {
        this.fields.addField(simpleProperty);
        
    }


    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getSimpleProperties()
     */
    public SortedSet<SimplePropertyImpl> getSimpleProperties() {
        return fields.getFields(SimplePropertyImpl.class);
    }


    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getSimpleProperty(java.lang.CharSequence)
     */
    public SimplePropertyImpl getSimpleProperty(CharSequence name)
            throws ModelException {
        return fields.getFieldByName(SimplePropertyImpl.class, name);
    }


    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getSimplePropertyCount()
     */
    public int getSimplePropertyCount() {
        return fields.getFieldCount(SimplePropertyImpl.class);
    }


    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getManyToOne(java.lang.CharSequence)
     */
    public ManyToOneAggregationField getManyToOne(CharSequence name)
            throws ModelException {
        return fields.getFieldByName(ManyToOneAggregationField.class, name);
    }


    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getManyToOneCount()
     */
    public int getManyToOneCount() {
        return fields.getFieldCount(ManyToOneAggregationField.class);
    }


    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getManyToOnes()
     */
    public SortedSet<? extends ManyToOneAggregationField> getManyToOnes() {
        return fields.getFields(ManyToOneAggregationField.class);
    }
    
    public void addManyToOne(ManyToOneFieldImpl manyToOneNormalField) throws ModelException {
        fields.addField(manyToOneNormalField);
    }


    public boolean containsName(CharSequence name) throws ModelException {
        return fields.containsFieldName(name);
    }


    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getColumnNames()
     */
    public SortedSet<ColumnName> getColumnNames() {
        return fields.getColumnNames();
    }


    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.impl.Entity#getColumns()
     */
    public SortedSet<Column> getColumns() {
        return fields.getColumns();
    }


    public Field getField(int index) {
        return fields.getField(index);
    }


    public Field getFieldBackedByColumns(SortedSet<? extends Column> columns) {
        return fields.getFieldBackedByColumns(columns);
    }


    public Field getFieldByName(CharSequence name) throws ModelException {
        return fields.getFieldByName(name);
    }


    public int getFieldCount() {
        return fields.getFieldCount();
    }


    public SortedSet<FieldName> getNames() {
        return fields.getFieldNames();
    }


    public SortedSet<Field> getFields() {
        return fields.getFields();
    }


    public EntityName getName() {
        return this.name;
    }


    public int compareTo(NamedObject obj) {
        return this.name.compareTo(obj.getName());
    }


    public SimpleProperty createSimpleProperty(CharSequence name, PropertyType type,
            boolean notNull, CharSequence columnName) throws ModelException {
        return new SimplePropertyImpl(name, type, notNull, columnName, this);
    }


    public <T extends Field> void addField(T field) throws ModelException {
        this.fields.addField(field);
    }

}
