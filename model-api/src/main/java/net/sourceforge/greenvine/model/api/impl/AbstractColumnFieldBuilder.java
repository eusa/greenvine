package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.FieldName;
import net.sourceforge.greenvine.model.naming.impl.ColumnNameImpl;
import net.sourceforge.greenvine.model.naming.impl.FieldNameImpl;

public abstract class AbstractColumnFieldBuilder {
    
    protected FieldName name;
    protected ColumnName columnName;
    protected PropertyType propertyType;
    protected boolean notNull;
    
    public FieldName getName() {
        return name;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public CharSequence getColumnName() {
        return columnName;
    }

    public AbstractColumnFieldBuilder setName(CharSequence name) throws ModelException {
        this.name = new FieldNameImpl(name);
        return this;
    }

    public AbstractColumnFieldBuilder setColumnName(CharSequence columnName) throws ModelException {
        this.columnName = new ColumnNameImpl(columnName);
        return this;
    }

    public AbstractColumnFieldBuilder setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
        return this;
    }

    public AbstractColumnFieldBuilder setNotNull(boolean notNull) {
        this.notNull = notNull;
        return this;
    }
    
}
