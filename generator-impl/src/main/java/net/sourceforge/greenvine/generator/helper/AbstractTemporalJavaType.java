package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.ColumnField;

public abstract class AbstractTemporalJavaType extends AbstractBasicJavaType {
    
    protected static final String TYPE_LITERAL = "java.util.Date";
    protected static final String CREATE_LITERAL = "new Date(%sL)";
    private static final String COPY_LITERAL = "new Date(%s.getTime())";
    
    protected static final DataHelper dataHelper = new DataHelper();
    
    public AbstractTemporalJavaType(ColumnField property) {
        super(TYPE_LITERAL, property);
        
        // Add facets
        facets.add(Facet.Temporal);
        
    }
    
    public boolean requiresImport() {
        return true;
    }
    
    public abstract TemporalType getTemporalType();
    
    public String getDefensiveCopyLiteral(String reference) {
        return String.format(COPY_LITERAL, reference);
    }
    
}

enum TemporalType {
    DATE, TIME, TIMESTAMP;
}

