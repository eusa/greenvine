package net.sourceforge.greenvine.model.api.impl;

import java.util.SortedSet;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.Field;
import net.sourceforge.greenvine.model.api.IdentityField;
import net.sourceforge.greenvine.model.api.ManyToOneAggregationField;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.MutableComponentField;
import net.sourceforge.greenvine.model.api.PrimaryIdentityComponent;
import net.sourceforge.greenvine.model.api.PrimaryKey;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.PropertyValueGenerationStrategy;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.FieldName;
import net.sourceforge.greenvine.model.naming.impl.FieldNameImpl;

public class ComponentIdentityImpl extends AbstractFieldNamedObject implements PrimaryIdentityComponent, MutableComponentField {
    
    private final EntityImpl entity;
    private final PrimaryKey primaryKey;
    private final FieldCollectionDelegate fields;
    
    ComponentIdentityImpl(CharSequence name, EntityImpl entity) throws ModelException {
        
        super(name);
        
        // Validate parameters
        if (entity == null) {
            throw new ModelException(String.format("Can't create a ComplexIdentity with a null entity"));
        }
        if (entity.getTable().getPrimaryKey() == null) {
            throw new ModelException(String.format("Can't create a ComplexIdentity with a null primary key in entity %s, table %s.", entity.getName(), entity.getTable().getName()));    
        }
        // Set fields
        this.entity = entity;
        this.primaryKey = entity.getTable().getPrimaryKey();
        this.fields = new FieldCollectionDelegate(this);
        
        // Add to entity field collection
        this.entity.setIdentity(this);
    }
    
    ComponentIdentityImpl(ComponentIdentityImplBuilder builder) throws ModelException {
        super(builder.getName());
        
        // Validate parameters
        if (builder.getEntity() == null) {
            throw new ModelException(String.format("Can't create a ComplexIdentity with a null entity"));
        }
        if (builder.getEntity().getTable().getPrimaryKey() == null) {
            throw new ModelException(String.format("Can't create a ComplexIdentity with a null primary key in entity %s, table %s.", builder.getEntity().getName(), builder.getEntity().getTable().getName()));    
        }
        
        // Set fields
        this.entity = builder.getEntity();
        this.primaryKey = entity.getTable().getPrimaryKey();
        this.fields = new FieldCollectionDelegate(this);
        
        // Create properties
        for (IdentityFieldImplBuilder ifBuilder : builder.getSimpleProperties()) {
            ifBuilder.setComponentIdentity(this);
            ifBuilder.build();
        }
        // Create many-to-ones
        for (ManyToOneComponentRelationImplBuilder ifBuilder : builder.getManyToOnes()) {
            ifBuilder.setComponent(this);
            ifBuilder.setCatalog(this.entity.getCatalog());
            ifBuilder.build();
        }
        
        // Check all primary columns are mapped
        if (!getColumns().equals(primaryKey.getColumns())) {
            throw new ModelException(String.format("Can't create ComponentIdentity %s for entity %s as not all primary key columns are mapped.", this.name, this.entity.getName()));    
        }
        
        // Add to entity field collection
        this.entity.setIdentity(this);
    }
     
    IdentityFieldImpl createSimpleProperty(CharSequence name, PropertyType propertyType,
            PropertyValueGenerationStrategy keyGenerationStrategy, CharSequence columnName) throws ModelException {
        return new IdentityFieldImpl(name, propertyType, keyGenerationStrategy, columnName, this);
    }
    
    public EntityImpl getFieldCollection() {
        return this.entity;
    }

    public Field getField(int ordinal) {
        return this.fields.getField(ordinal);
    }

    public int getFieldCount() {
        return this.fields.getFieldCount();
    }

    public SortedSet<Field> getFields() {
        return this.fields.getFields();
    }
    
    public SortedSet<FieldName> getNames() {
        return this.fields.getFieldNames();
    }
    
    public <T extends Field> void addField(T field) throws ModelException {
        this.fields.addField(field);
    }
    
    public boolean containsName(CharSequence fieldName) throws ModelException {
        return this.fields.containsFieldName(fieldName);
    }
    
    public IdentityFieldImpl getSimpleProperty(CharSequence name) throws ModelException {
        return this.fields.getFieldByName(IdentityFieldImpl.class, name);
    }

    public int getSimplePropertyCount() {
        return this.fields.getFieldCount(IdentityFieldImpl.class);
    }

    public SortedSet<IdentityField> getSimpleProperties() {
        return this.fields.getFields(IdentityField.class);
    }
    
    public Table getTable() {
        return entity.getTable();
    }

    public boolean getNotNull() {
        return true;
    }

    public FieldNameImpl getName() {
        return this.name;
    }
    
    public PrimaryKey getPrimaryKey() {
        return this.primaryKey;
    }

    @Override
    public String toString() {
        return "ComplexIdentity [name=" + name + ", ields="
                + fields + "]";
    }

    public ManyToOneIdentityField getManyToOne(CharSequence name)
            throws ModelException {
        return fields.getFieldByName(ManyToOneIdentityField.class, name);
    }

    public int getManyToOneCount() {
        return fields.getFieldCount(ManyToOneIdentityField.class);
    }

    public SortedSet<ManyToOneIdentityField> getManyToOnes() {
        return fields.getFields(ManyToOneIdentityField.class);
    }

    public SortedSet<ColumnName> getColumnNames() {
        return fields.getColumnNames();
    }

    public SortedSet<Column> getColumns() {
        return fields.getColumns();
    }

    public Field getFieldBackedByColumns(SortedSet<? extends Column> columns) {
        return fields.getFieldBackedByColumns(columns);
    }

    public Field getFieldByName(CharSequence name) throws ModelException {
        return fields.getFieldByName(name);
    }

    public void addSimpleProperty(SimplePropertyImpl simpleProperty)
            throws ModelException {
        fields.addField(simpleProperty);
        
    }

    ManyToOneAggregationField createManyToOneRelationship(CharSequence fieldName,
            CharSequence inverseFieldName, CharSequence foreignKeyName) throws ModelException {
        
        // Create the relationship
        ManyToOneComponentRelationImpl rel = new ManyToOneComponentRelationImpl(fieldName, inverseFieldName, foreignKeyName, this, this.getFieldCollection().getCatalog());
        return rel.getChildField();
    }

}