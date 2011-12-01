package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnField;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.MutableFieldCollection;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.naming.impl.ColumnNameImpl;


public abstract class AbstractColumnField extends AbstractField implements ColumnField {

    private final PropertyType propertyType;
    private final boolean notNull;
    private final Column column;
    
    
    public AbstractColumnField(CharSequence name, PropertyType propertyType, boolean notNull, CharSequence column, MutableFieldCollection component)
            throws ModelException {
        super(name, component);
        
        // Validate fields
        validatePropertyType(propertyType);
        validateColumn(column, component);
        
        // Set fields
        this.propertyType = propertyType;
        this.notNull = notNull;
        this.column = component.getTable().getColumnByName(column);
    }
       
    
    private void validatePropertyType(PropertyType propertyType) throws ModelException {
        if (propertyType == null) {
            throw new ModelException(String.format("Property type null in simple property type %s.", name));
        }
    }
    
    private void validateColumn(CharSequence column,
            MutableFieldCollection component) throws ModelException {
        if (column == null) {
            throw new ModelException(String.format("Null column in field %s of field collection %s.", name, component.getName()));
        }
        if (component.getTable().getColumnByName(column) == null) {
            throw new ModelException(String.format("Column %s not in data container %s.", column, component.getTable().getName()));
        }
        if (component.getColumnNames().contains(new ColumnNameImpl(column))) {
            throw new ModelException(String.format("Column %s is already mapped in field collection %s.", column, component.getName()));
        }
    }
    
    public Column getColumn() {
        return this.column;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public boolean getNotNull() {
        return notNull;
    }
    
}
