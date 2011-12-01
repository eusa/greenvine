package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.Field;
import net.sourceforge.greenvine.model.api.FieldCollection;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.MutableFieldCollection;



public abstract class AbstractField extends AbstractFieldNamedObject implements Field {

    private final MutableFieldCollection fieldCollection;
    
    public AbstractField(CharSequence name, MutableFieldCollection fieldCollection) throws ModelException {
        super(name);
     
        // Validate FieldCollection
        validateFieldCollection(fieldCollection);
        
        // Set reference to FieldCollection
        this.fieldCollection = fieldCollection;
        
    }
    
    private void validateFieldCollection(FieldCollection fieldCollection) throws ModelException {
        if (fieldCollection == null) {
            throw new ModelException(String.format("Null field collection in field %s.", name));
        }
    }

    public FieldCollection getFieldCollection() {
        return this.fieldCollection;
    }

}
