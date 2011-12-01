package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.ColumnField;
import net.sourceforge.greenvine.model.api.PropertyType;

public class BooleanJavaType extends AbstractBasicJavaType {
    
    private static final String TYPE_LITERAL = "java.lang.Boolean";
    private static final PropertyType PROPERTY_TYPE = PropertyType.BOOLEAN;
    
    private static final DataHelper dataHelper = new DataHelper();

    public BooleanJavaType(ColumnField property) {
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
        return "Boolean." + dataHelper.getCreateBoolean().toUpperCase();
    }
    
    public String getUpdateDataLiteral() {
        return "Boolean." + dataHelper.getUpdateBoolean().toUpperCase();
    }
    
    public String getRandomDataLiteral() {
        return "Boolean." + dataHelper.getRandomBoolean().toUpperCase();
    }
    
    public String getDefensiveCopyLiteral(String reference) {
        // No need for defensive copy
        return reference;
    }

}
