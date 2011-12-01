package net.sourceforge.greenvine.generator.runner.impl;

public class SourceConfig {

    private String basePackage;
    private String dataPackage;
    private String daoSuffix;
    
    public String getBasePackage() {
        return basePackage;
    }
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
    public String getDataPackage() {
        return dataPackage;
    }
    public void setDataPackage(String dataPackage) {
        this.dataPackage = dataPackage;
    }
    public String getDaoSuffix() {
        return daoSuffix;
    }
    public void setDaoSuffix(String daoSuffix) {
        this.daoSuffix = daoSuffix;
    }
    
    

}
