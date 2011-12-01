package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.ColumnField;

public abstract class AbstractDecimalJavaType extends AbstractBasicJavaType implements BasicJavaType {

    /**
     * Used for creating data literals
     */
    protected final DataHelper dataHelper = new DataHelper();
    protected final int scale;
    protected final int precision;
    
    public AbstractDecimalJavaType(String qualifiedName, ColumnField property) {
        super(qualifiedName, property);
        
        // Get the scale and precision
        scale = property.getColumn().getScale();
        precision = property.getColumn().getPrecision();
        
    }
    

}   
    

