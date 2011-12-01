package net.sourceforge.greenvine.model.api.impl;

import java.util.SortedSet;

import net.sourceforge.greenvine.model.api.Field;
import net.sourceforge.greenvine.model.api.ManyToOneAggregationField;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.SimpleComponent;
import net.sourceforge.greenvine.model.api.SimpleProperty;

public abstract class AbstractSimpleComponent extends AbstractComponent implements SimpleComponent {

    AbstractSimpleComponent(CharSequence name) throws ModelException {
        super(name);
    }
    
    public <T extends Field> void addField(T field) throws ModelException {
        this.fields.addField(field);
    }
    
    public SortedSet<SimplePropertyImpl> getSimpleProperties() {
        return fields.getFields(SimplePropertyImpl.class);
    }

    public SimplePropertyImpl getSimpleProperty(CharSequence name) throws ModelException {
        return fields.getFieldByName(SimplePropertyImpl.class, name);
    }

    public int getSimplePropertyCount() {
        return fields.getFieldCount(SimplePropertyImpl.class);
    }
    
    public void addSimpleProperty(SimpleProperty simpleProperty) throws ModelException {
        fields.addField(simpleProperty);
    }
    
    public SimpleProperty createSimpleProperty(CharSequence name, PropertyType propertyType, boolean notNull, CharSequence column) throws ModelException {
        return new SimplePropertyImpl(name, propertyType, notNull, column, this);
    }
    
    public SortedSet<ManyToOneAggregationField> getManyToOnes() {
        return fields.getFields(ManyToOneAggregationField.class);
    }

    public ManyToOneAggregationField getManyToOne(CharSequence name) throws ModelException {
        return fields.getFieldByName(ManyToOneFieldImpl.class, name);
    }

    public int getManyToOneCount() {
        return fields.getFieldCount(ManyToOneFieldImpl.class);
    }
    
    void addManyToOne(ManyToOneAggregationField manyToOne) throws ModelException {
        fields.addField(manyToOne);
    }

}
