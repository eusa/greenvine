package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.ColumnField;
import net.sourceforge.greenvine.model.api.PropertyType;

public class FloatJavaType extends AbstractDecimalJavaType {
    
    private static final String TYPE_LITERAL = "java.lang.Float";
    private static final PropertyType PROPERTY_TYPE = PropertyType.FLOAT;

    private static final String CREATE_LITERAL = "Float.valueOf(%sF)";
    
    public FloatJavaType(ColumnField property) {
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
        return String.format(CREATE_LITERAL, dataHelper.getCreateDecimal(scale, precision));
    }

    public String getUpdateDataLiteral() {
        return String.format(CREATE_LITERAL, dataHelper.getUpdateDecimal(scale, precision));
    }
    
    public String getRandomDataLiteral() {
        return String.format(CREATE_LITERAL, dataHelper.getRandomDecimal(scale, precision));
    }
    
    public String getDefensiveCopyLiteral(String reference) {
        // No need for defensive copy
        return reference;
    }

}
