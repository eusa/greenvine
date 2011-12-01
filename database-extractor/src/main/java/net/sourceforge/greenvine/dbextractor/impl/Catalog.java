package net.sourceforge.greenvine.dbextractor.impl;



public class Catalog {
    
    private String name;
    private Connection connection;
    private InclusionStrategy inclusions;
    
    public Connection getConnection() {
        return this.connection;
    }
    
    public void setConnection(final Connection connection) {
        this.connection = connection;
    }
    
    public void setInclusions(final InclusionStrategy inclusions) {
        this.inclusions = inclusions;
    }

    public InclusionStrategy getInclusions() {
        return inclusions;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
