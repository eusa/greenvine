package net.sourceforge.greenvine.generator.helper;

import java.util.Arrays;

import net.sourceforge.greenvine.model.api.ColumnField;
import net.sourceforge.greenvine.model.api.PropertyType;

public class ClobJavaType extends AbstractTextJavaType {

    private static final PropertyType[] allowedTypes = new PropertyType[]{PropertyType.CLOB, PropertyType.TEXT};
    
    
    public ClobJavaType(ColumnField property) {
        super(property);
        
        // Validate type
        if (!Arrays.asList(allowedTypes).contains(property.getPropertyType())) {
            throw new IllegalArgumentException(String.format("Property %s is not a valid type (either TEXT or CLOB).", property));
        }
        
        // Add facets
        facets.add(Facet.Lob);
    }

}
