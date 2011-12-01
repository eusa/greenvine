package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.ColumnField;
import net.sourceforge.greenvine.model.api.PropertyType;

public class ByteJavaType extends AbstractBasicJavaType {

    private static final String TYPE_LITERAL = "java.lang.Byte";
    private static final PropertyType PROPERTY_TYPE = PropertyType.BYTE;
    private static final String CREATE_LITERAL = "Byte.valueOf(\"%s\")";
    
    private static final DataHelper dataHelper = new DataHelper();
    
    public ByteJavaType(ColumnField property) {
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
        return String.format(CREATE_LITERAL, dataHelper.getRandomTinyInteger());
    }
    
    public String getDefensiveCopyLiteral(String reference) {
        // No need for defensive copy
        return reference;
    }

}
