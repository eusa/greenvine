package net.sourceforge.greenvine.generator.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractJavaType implements Comparable<AbstractJavaType>, JavaType {
    
    private final String packageName;
    private final String typeName;
    
    public AbstractJavaType(String qualifiedName) {
        
        // Validate name
        validate(qualifiedName);
        
        // Set properties
        this.typeName = getTypeName(qualifiedName);
        this.packageName = getPackageName(qualifiedName);
    }
    
    private void validate(String name) {
        Pattern pattern = Pattern.compile("([\\p{L}_$][\\p{L}\\p{N}_$]*\\.)*[\\p{L}_$][\\p{L}\\p{N}_$]*");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(String.format("Cannot create a model object with an Java type name %s.", name));
        }
        
    }

    private String getTypeName(String qualifiedName) {
        int pos = qualifiedName.lastIndexOf(".");
        return qualifiedName.substring(++pos);
    }
    
    private String getPackageName(String qualifiedName) {
        int pos = qualifiedName.lastIndexOf(".");
        return qualifiedName.substring(0, pos);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.generator.helper.JavaType#getPackageName()
     */
    public String getPackageName() {
        return packageName;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.generator.helper.JavaType#getClassName()
     */
    public String getClassName() {
        return typeName;
    }
    
    @Override
    public String toString() {
        return packageName + "." + typeName;
    }

    public int compareTo(AbstractJavaType o) {
        return this.toString().compareTo(o.toString());
    }
    
 
    
    

}
