package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.ColumnField;
import net.sourceforge.greenvine.model.api.PropertyType;

public class StringJavaType extends AbstractTextJavaType {

    private static final PropertyType PROPERTY_TYPE = PropertyType.STRING;
    
    public StringJavaType(ColumnField property) {
        super(property);
        
        // Validate
        if (!property.getPropertyType().equals(PROPERTY_TYPE)) {
            throw new IllegalArgumentException(String.format("Property %s is not of type %s.", property, PROPERTY_TYPE));
        }
    }   

}
