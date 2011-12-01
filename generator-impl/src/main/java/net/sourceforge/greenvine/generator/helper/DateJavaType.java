package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.ColumnField;
import net.sourceforge.greenvine.model.api.PropertyType;


public class DateJavaType extends AbstractTemporalJavaType {
    
    private static final PropertyType PROPERTY_TYPE = PropertyType.DATE;
    private static final TemporalType TEMPORAL_TYPE = TemporalType.DATE;
    
    public DateJavaType(ColumnField property) {
        super(property);
        
        // Validate
        if (!property.getPropertyType().equals(PROPERTY_TYPE)) {
            throw new IllegalArgumentException(String.format("Property %s is not of type %s.", property, PROPERTY_TYPE));
        }
    }

    public TemporalType getTemporalType() {
        return TEMPORAL_TYPE;
    }

    public String getCreateDataLiteral() {
        return String.format(CREATE_LITERAL, dataHelper.getCreateDate().getTime());
    }
    
    public String getUpdateDataLiteral() {
        return String.format(CREATE_LITERAL, dataHelper.getUpdateDate().getTime());
    }
    
    public String getRandomDataLiteral() {
        return String.format(CREATE_LITERAL, dataHelper.getRandomDate().getTime());
    }
    
}
