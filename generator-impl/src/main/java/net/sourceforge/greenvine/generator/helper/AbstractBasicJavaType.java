package net.sourceforge.greenvine.generator.helper;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.greenvine.model.api.ColumnField;

public abstract class AbstractBasicJavaType extends AbstractJavaType implements BasicJavaType {
    
    protected final ColumnField property;
    
    protected final List<Facet> facets = new ArrayList<Facet>();

    public AbstractBasicJavaType(String qualifiedName, ColumnField property) {
        super(qualifiedName);
        
        this.property = property;
    }

    public ColumnField getProperty() {
        return property;
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
    
}   
    

