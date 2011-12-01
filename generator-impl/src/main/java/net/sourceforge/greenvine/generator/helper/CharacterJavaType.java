package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.ColumnField;
import net.sourceforge.greenvine.model.api.PropertyType;


public class CharacterJavaType extends AbstractBasicJavaType {

    private static final String TYPE_LITERAL = "java.lang.Character";
    private static final PropertyType PROPERTY_TYPE = PropertyType.CHARACTER;
    private static final String CREATE_LITERAL = "Character.valueOf(%s)";
    private static final DataHelper dataHelper = new DataHelper();
    
    public CharacterJavaType(ColumnField property) {
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
        return String.format(CREATE_LITERAL, dataHelper.getCreateCharacter());
    }
    
    public String getUpdateDataLiteral() {
        return String.format(CREATE_LITERAL, dataHelper.getUpdateCharacter());
    }
    
    public String getRandomDataLiteral() {
        return String.format(CREATE_LITERAL, dataHelper.getRandomCharacter());
    }

    public String getDefensiveCopyLiteral(String reference) {
        // No need for defensive copy
        return reference;
    }

    
}
