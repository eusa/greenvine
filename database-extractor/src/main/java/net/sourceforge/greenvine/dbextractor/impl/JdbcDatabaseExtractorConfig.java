package net.sourceforge.greenvine.dbextractor.impl;


public class JdbcDatabaseExtractorConfig {
    
    private Catalog catalog;
    
    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public Catalog getCatalog() {
        return catalog;
    }
}
