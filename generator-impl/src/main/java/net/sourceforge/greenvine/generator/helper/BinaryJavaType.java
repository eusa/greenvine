package net.sourceforge.greenvine.generator.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sourceforge.greenvine.model.api.ColumnField;
import net.sourceforge.greenvine.model.api.PropertyType;

public class BinaryJavaType implements BasicJavaType {

    
    private final static String className = "byte";
    private static final PropertyType[] allowedTypes = new PropertyType[]{PropertyType.BINARY, PropertyType.BLOB};
    
    private static final String CREATE_LITERAL = "new byte[]{%s}";
    
    private final List<Facet> facets = new ArrayList<Facet>(); 
    private final ColumnField property;
    
    private final DataHelper dataHelper = new DataHelper();
    
    public BinaryJavaType(ColumnField property) {
        
        // validate type
        if (!Arrays.asList(allowedTypes).contains(property.getPropertyType())) {
            throw new IllegalArgumentException(String.format("Type %s is not a binary type (BINARY or BLOB).", property));
        }
        
        // Add facets
        facets.add(Facet.Binary);
        if (PropertyType.BLOB.equals(property)) {
            facets.add(Facet.Lob);
        }
        
        this.property = property;
    }
    
    public String getClassName() {
        return className;
    }

    public String getPackageName() {
        return null;
    }
    
    public ColumnField getProperty() {
        return property;
    }
   
    public boolean requiresImport() {
        return false;
    }
    
    public boolean hasFacet(Facet facet) {
        return facets.contains(facet);
    }

    public List<Facet> getFacets() {
        return facets;
    }
    
    public List<String> getFacetNames() {
        List<String> names = new ArrayList<String>();
        for (Facet facet : facets) {
            names.add(facet.toString());
        }
        return names;
    }
    
    public String getCreateDataLiteral() {
        return String.format(CREATE_LITERAL, dataHelper.getCreateBinaryBytes());
    }
    
    public String getUpdateDataLiteral() {
        return String.format(CREATE_LITERAL, dataHelper.getUpdateBinaryBytes());
    }
    
    public String getRandomDataLiteral() {
        return String.format(CREATE_LITERAL, dataHelper.getRandomBinaryBytes(4));
    }
    
    public String getDefensiveCopyLiteral(String reference) {
        // No need for defensive copy
        return reference;
    }
    
}