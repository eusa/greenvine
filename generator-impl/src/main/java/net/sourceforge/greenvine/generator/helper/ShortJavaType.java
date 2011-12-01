package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.ColumnField;
import net.sourceforge.greenvine.model.api.PropertyType;

public class ShortJavaType extends AbstractIntegerJavaType {
    
    private static final String TYPE_LITERAL = "java.lang.Short";
    private static final PropertyType PROPERTY_TYPE = PropertyType.SHORT;
    private static final String CREATE_LITERAL = "Short.valueOf((short)%s)";

    public ShortJavaType(ColumnField property) {
        super(TYPE_LITERAL, property);
        
        // Validate
        if (!property.getPropertyType().equals(PROPERTY_TYPE)) {
            throw new IllegalArgumentException(String.format("Property %s is not of type %s.", property, PROPERTY_TYPE));
        }
    }

    public boolean requiresImport() {
        return false;
    }
    
    public String getCreateDataLiteral() {
        return String.format(CREATE_LITERAL, dataHelper.getCreateInteger());
    }
    
    public String getUpdateDataLiteral() {
        return String.format(CREATE_LITERAL, dataHelper.getUpdateInteger());
    }
    
    public String getRandomDataLiteral() {
        return String.format(CREATE_LITERAL, dataHelper.getRandomSmallInteger());
    }

}
