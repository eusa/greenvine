package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.ColumnField;

public abstract class AbstractIntegerJavaType extends AbstractBasicJavaType implements BasicJavaType {

    protected static final DataHelper dataHelper = new DataHelper();
    
    public AbstractIntegerJavaType(String qualifiedName, ColumnField property) {
        super(qualifiedName, property);
    }
    
    public String getDefensiveCopyLiteral(String reference) {
        // No need for defensive copy
        return reference;
    }

}   
    

