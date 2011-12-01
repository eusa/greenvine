package net.sourceforge.greenvine.generator.helper;



public class ReferenceJavaType extends AbstractJavaType {
    
    public ReferenceJavaType (String qualifiedName) {
        super(qualifiedName);
    }
    
    public boolean isArray() {
        return false;
    }
    
    public boolean requiresImport() {
        return true;
    }
    
}
