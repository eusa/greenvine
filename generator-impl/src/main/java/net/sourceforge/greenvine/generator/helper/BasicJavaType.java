package net.sourceforge.greenvine.generator.helper;

import java.util.List;

import net.sourceforge.greenvine.model.api.ColumnField;

public interface BasicJavaType extends JavaType {
    
    public ColumnField getProperty();

    public abstract boolean hasFacet(Facet facet);
    
    public abstract List<Facet> getFacets();
    
    public abstract List<String> getFacetNames(); 
    
    public abstract String getCreateDataLiteral();
    
    public abstract String getUpdateDataLiteral();
    
    public abstract String getRandomDataLiteral();

    public abstract String getDefensiveCopyLiteral(String reference);
    
}

enum Facet {
    Lob, Temporal, Binary
}
