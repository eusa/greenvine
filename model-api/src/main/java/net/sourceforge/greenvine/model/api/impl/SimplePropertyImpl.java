package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.MutableFieldCollection;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.SimpleProperty;

public class SimplePropertyImpl extends AbstractColumnField implements SimpleProperty {
    
    SimplePropertyImpl(CharSequence name, PropertyType propertyType, boolean notNull, CharSequence column, MutableFieldCollection component) throws ModelException {
        super(name, propertyType, notNull, column, component);
        
        // Add to containing field collection
        component.addField(this);
    }
   
}
