package net.sourceforge.greenvine.model.api.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnField;
import net.sourceforge.greenvine.model.api.Field;
import net.sourceforge.greenvine.model.api.FieldCollection;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.MultiColumnField;
import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.FieldName;
import net.sourceforge.greenvine.model.naming.impl.FieldNameImpl;

public class FieldCollectionDelegate {

    private final FieldCollection owner;
    private final Map<FieldName, Field> fields;
    private final Object locker = new Object();
    
    public FieldCollectionDelegate(FieldCollection owner) throws ModelException {
        if (owner == null) {
            throw new ModelException("Field collection must have an owner");
        }
        this.owner = owner;
        this.fields = new TreeMap<FieldName, Field>();
        
    }
    
    public <T extends Field> T getFieldByName(Class<T> type, CharSequence name) throws ModelException {
        synchronized(locker) {
            Field f = this.fields.get(new FieldNameImpl(name));
            if (f == null || !type.isInstance(f)) {
                throw new ModelException(String.format("Field collection %s doesn't contain field named %s of type %s", owner.getName(), name, type)); 
            }
            return type.cast(f);
        }
    }

    public int getFieldCount() {
        return this.fields.size();
    }
    
    public <T extends Field> int getFieldCount(Class<T> type) {
        synchronized(locker) {
            int count = 0;
            for (Field f : this.fields.values()) {
                if (type.isInstance(f)) {
                    count ++;
                }
            }
            return count;
        }
    }

    public SortedSet<Field> getFields() {
        return Collections.unmodifiableSortedSet(new TreeSet<Field>(fields.values()));
    }
    
    public <T extends Field> SortedSet<T> getFields(Class<T> type) {
        synchronized(locker) {
            SortedSet<T> fields = new TreeSet<T>();
            for (Field f : this.fields.values()) {
                if (type.isInstance(f)) {
                    fields.add(type.cast(f));
                }
            }
            return fields;
        }
    }
    
    public <T extends Field> void addField(T field) throws ModelException {
        synchronized(locker) {
            
            // Check the name
            if (this.fields.containsKey(field.getName())) {
                throw new ModelException(String.format("Field collection owned by %s already contains field with name %s.", owner.getName(), field.getName()));
            }
            // Check the column is not already mapped to another field
            // Also, check that column is in the table
            if (field instanceof ColumnField) {
                ColumnField cf = (ColumnField)field;
                if (this.getColumns().contains(cf.getColumn())) {
                    throw new ModelException(String.format("Field %s cannot be added to collection owned by %s because it already contains field mapped to column %s.", field.getName(), owner.getName(), cf.getColumn().getName()));
                }
                if (!this.owner.getTable().getColumns().contains(cf.getColumn())) {
                    throw new ModelException(String.format("Field %s cannot be added to collection owned by %s because table %s doesn't contain the column %s.", field.getName(), owner.getName(), owner.getTable().getName(), cf.getColumn().getName()));
                }
            }
            if (field instanceof MultiColumnField) {
                MultiColumnField cf = (MultiColumnField)field;
                for (Column column : cf.getColumns()) {
                    
                    // Due to limitations with EmbeddedIds, containing 
                    // ManyToOne it is necessary
                    // to duplicate columns in the EmbeddedId 
                    // when the ManyToOne is added as a field
                    // in the Entity
                    //if (this.getColumns().contains(column)) {
                    //    throw new ModelException(String.format("Field %s cannot be added to collection owned by %s because it already contains field mapped to column %s.", field.getName(), owner.getName(), column.getName()));
                    //}
                    
                    // Check that the column is contained within the mapped table
                    if (!this.owner.getTable().getColumns().contains(column)) {
                        throw new ModelException(String.format("Field %s cannot be added to collection owned by %s because table %s doesn't contain the column %s.", field.getName(), owner.getName(), owner.getTable().getName(), column.getName()));
                    }
                }
            }
            fields.put(field.getName(), field);
        }
    }
    
    public SortedSet<Column> getColumns() {
        SortedSet<Column> cols = new TreeSet<Column>();
        for (Field field : fields.values()) {
            if (field instanceof ColumnField) {
                ColumnField columnField = (ColumnField)field;
                cols.add(columnField.getColumn());
            } else if (field instanceof MultiColumnField) {
                MultiColumnField columnRelationField = (MultiColumnField)field;
                cols.addAll(columnRelationField.getColumns());
            }
        }
        return cols;
    }
    
    public SortedSet<ColumnName> getColumnNames() {
        SortedSet<ColumnName> cols = new TreeSet<ColumnName>();
        for (Column col : getColumns()) {
            cols.add(col.getName());
        }
        return cols;
    }

    public Field getFieldBackedByColumns(SortedSet<? extends Column> columns) {
        for (Field field : fields.values()) {
            if (field instanceof ColumnField) {
                ColumnField columnField = (ColumnField)field;
                SortedSet<Column> cols = new TreeSet<Column>();
                cols.add(columnField.getColumn());
                if (cols.containsAll(columns)) {
                    return field;
                }
            } else if (field instanceof MultiColumnField) {
                MultiColumnField multiColumnField = (MultiColumnField)field;
                if (multiColumnField.getColumns().containsAll(columns)) {
                    return field;
                }
            }
        }
        return null;
    }

    public boolean containsFieldName(CharSequence name) throws ModelException {
        if (this.fields.containsKey(new FieldNameImpl(name))) {
            return true;
        }
        return false;
    }

    public Field getField(int index) {
        List<Field> fields = new ArrayList<Field>(this.fields.values());
        return fields.get(index);
    }

    public Field getFieldByName(CharSequence name) throws ModelException {
        synchronized(locker) {
            Field f = this.fields.get(new FieldNameImpl(name));
            if (f == null) {
                throw new ModelException(String.format("Field collection %s doesn't contain field named %s", owner.getName(), name)); 
            }
            return f;
        }
    }

    public SortedSet<FieldName> getFieldNames() {
        SortedSet<FieldName> fieldNames = new TreeSet<FieldName>();
        for (Field f : this.fields.values()) {
            fieldNames.add(f.getName());
        }
        return fieldNames;
    }

    @Override
    public String toString() {
        return "[" + fields + "]";
    }

}
