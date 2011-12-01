package net.sourceforge.greenvine.model.api.impl;

import java.util.SortedSet;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.Component;
import net.sourceforge.greenvine.model.api.Field;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.MutableFieldCollection;
import net.sourceforge.greenvine.model.api.OneToOneChildField;
import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.FieldName;

public abstract class AbstractComponent extends AbstractFieldNamedObject implements Component, MutableFieldCollection {

    protected final FieldCollectionDelegate fields;
    
    AbstractComponent(CharSequence name)
            throws ModelException {
        super(name);
        this.fields = new FieldCollectionDelegate(this);
        
    }
    
    public Field getFieldByName(CharSequence name) throws ModelException {
        return fields.getFieldByName(name);
    }

    public Field getField(int index) {
        return fields.getField(index);
    }
    
    public int getFieldCount() {
        return fields.getFieldCount();
    }

    public SortedSet<Field> getFields() {
        return fields.getFields();
    }
    
    public SortedSet<? extends FieldName> getNames() {
        return fields.getFieldNames();
    }
    
    public boolean containsName(CharSequence fieldName) throws ModelException {
        return fields.containsFieldName(fieldName);
    }
    
    public SortedSet<OneToOneChildFieldImpl> getOneToOneChilds() {
        return fields.getFields(OneToOneChildFieldImpl.class);
    }

    public OneToOneChildField getOneToOneChildByName(CharSequence name) throws ModelException {
        return fields.getFieldByName(OneToOneChildFieldImpl.class, name);
    }

    public int getOneToOneChildCount() {
        return fields.getFieldCount(OneToOneChildFieldImpl.class);
    }
    
    void addOneToOneInverse(OneToOneChildField oneToOne) throws ModelException {
        fields.addField(oneToOne);
    }
    
    public SortedSet<? extends Column> getColumns() {
        return fields.getColumns();
    }
    
    public SortedSet<? extends ColumnName> getColumnNames() {
        return fields.getColumnNames();
    }

    public Field getFieldBackedByColumns(SortedSet<? extends Column> columns) {
        return fields.getFieldBackedByColumns(columns);
    }
}
