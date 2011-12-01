package net.sourceforge.greenvine.generator.helper;


public interface JavaType {

    public abstract String getPackageName();

    public abstract String getClassName();
    
    public abstract boolean requiresImport();
    
}