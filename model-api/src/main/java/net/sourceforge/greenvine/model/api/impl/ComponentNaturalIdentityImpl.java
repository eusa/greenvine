package net.sourceforge.greenvine.model.api.impl;

import java.util.SortedSet;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ComponentNaturalIdentity;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.Field;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ManyToOneAggregationField;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.MutableComponentField;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.SimpleProperty;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.api.UniqueKey;
import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.FieldName;
import net.sourceforge.greenvine.model.naming.impl.ColumnNameImpl;

public class ComponentNaturalIdentityImpl extends AbstractFieldNamedObject implements
        ComponentNaturalIdentity, MutableComponentField {

    private final EntityImpl entity;
    private final UniqueKey uniqueKey;
    private final FieldCollectionDelegate fields;

    public ComponentNaturalIdentityImpl(CharSequence name, UniqueKey uniqueKey,
            EntityImpl entity) throws ModelException {

        super(name);

        // Validate parameters
        validate(uniqueKey, entity);

        // Initialise fields
        this.entity = entity;
        this.uniqueKey = uniqueKey;
        this.fields = new FieldCollectionDelegate(this);

        // Set reference back to entity
        entity.setNaturalIdentity(this);
    }

    /**
     * Check the {@link UniqueKeyImpl}
     * and {@link EntityImpl}
     * used to define this natural identity
     * are valid (i.e. not null)
     * @param uniqueKey
     * @param entity
     * @throws ModelException
     */
    private void validate(UniqueKey uniqueKey, Entity entity)
            throws ModelException {
        if (uniqueKey == null) {
            throw new ModelException(
                    "Cannot create natural identity without a unique key.");
        }
        if (entity == null) {
            throw new ModelException(
                    "Cannot create natural identity without a entity.");
        }
    }

    /**
     * Get the {@link DataContainer}
     * that the containing {@link EntityImpl}
     * is mapped to.
     */
    public Table getTable() {
        return this.entity.getTable();
    }

    /**
     * Get the Entity that this
     * {@link ComponentNaturalIdentityImpl} belongs to.
     */
    public EntityImpl getFieldCollection() {
        return entity;
    }

    /**
     * This method returns true as
     * a {@link ComponentNaturalIdentityImpl} field
     * can never be null.
     */
    public boolean getNotNull() {
        return true;
    }
    
    /**
     * Get the {@link UniqueKeyImpl} that
     * defines this natural identity
     * @return
     */
    public UniqueKey getUniqueKey() {
        return this.uniqueKey;
    }

    public ManyToOneAggregationField createManyToOneRelationship(CharSequence fieldName,
            CharSequence inverseFieldName, CharSequence foreignKeyName) throws ModelException {
        
        // Find the ForeignKey
        ForeignKey foreignKey = getTable().getForeignKeyByName(foreignKeyName);
        
        // Check that the entire foreign key is within the unique key
        if (!uniqueKey.getColumnNames().containsAll(foreignKey.getReferencingColumnNames())) {
            throw new ModelException(String.format("Cannot create many-to-one in ComponentNaturalIdentity unless all referencing columns are within the unique key."));
        }
        
        // Create the relationship
        ManyToOneComponentRelationImpl rel = new ManyToOneComponentRelationImpl(fieldName, inverseFieldName, foreignKey.getName(), this, this.getFieldCollection().getCatalog());
        return rel.getChildField();
    }

    
    public ManyToOneIdentityField getManyToOne(CharSequence name)
            throws ModelException {
        return fields.getFieldByName(ManyToOneIdentityField.class, name);
    }

    public int getManyToOneCount() {
        return fields.getFieldCount(ManyToOneAggregationField.class);
    }

    public SortedSet<ManyToOneIdentityField> getManyToOnes() {
        return fields.getFields(ManyToOneIdentityField.class);
    }

    public SortedSet<SimplePropertyImpl> getSimpleProperties() {
        return fields.getFields(SimplePropertyImpl.class);
    }

    public SimplePropertyImpl getSimpleProperty(CharSequence name)
            throws ModelException {
        return fields.getFieldByName(SimplePropertyImpl.class, name);
    }

    public int getSimplePropertyCount() {
        return fields.getFieldCount(SimplePropertyImpl.class);
    }

    public boolean containsName(CharSequence name) throws ModelException {
        return fields.containsFieldName(name);
    }

    public SortedSet<ColumnName> getColumnNames() {
        return fields.getColumnNames();
    }

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
    
    public <T extends Field> void addField(T field) throws ModelException {
        this.fields.addField(field);
    }

    public void addSimpleProperty(SimpleProperty simpleProperty)
            throws ModelException {
        this.fields.addField(simpleProperty);
    }

    public SimpleProperty createSimpleProperty(CharSequence name,
            PropertyType type, CharSequence column)
            throws ModelException {
        
        // Validate that the column is in the primary key
        if (!this.uniqueKey.getColumnNames().contains(new ColumnNameImpl(column))) {
            throw new ModelException(String.format("Unique key %s does not contain column %s for ComponentNaturalIdentity %s", this.uniqueKey.getName(), column, this.getName()));
        }
        
        return new SimplePropertyImpl(name, type, true, column, this);
    }

    public void addManyToOne(ManyToOneIdentityField manyToOne)
            throws ModelException {
        this.fields.addField(manyToOne);
        
    }
    
}
