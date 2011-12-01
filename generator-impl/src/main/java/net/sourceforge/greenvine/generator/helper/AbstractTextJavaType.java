package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.ColumnField;


public abstract class AbstractTextJavaType extends AbstractBasicJavaType {

    private static final String TYPE_LITERAL = "java.lang.String";
    private static final DataHelper dataHelper = new DataHelper();
    
    private static final String CREATE_LITERAL = "\"%s\"";
    
    public AbstractTextJavaType(ColumnField property) {
        super(TYPE_LITERAL, property);
    }

    public boolean requiresImport() {
        return false;
    }
    
    public String getCreateDataLiteral() {
        return String.format(CREATE_LITERAL, dataHelper.getCreateString());
    }

    public String getUpdateDataLiteral() {
        return String.format(CREATE_LITERAL, dataHelper.getUpdateString());
    }
    
    public String getRandomDataLiteral() {
        return String.format(CREATE_LITERAL, dataHelper.getRandomString(this.property.getColumn().getPrecision()));
    }
    
    public String getDefensiveCopyLiteral(String reference) {
        // No need for defensive copy
        return reference;
    }

}
