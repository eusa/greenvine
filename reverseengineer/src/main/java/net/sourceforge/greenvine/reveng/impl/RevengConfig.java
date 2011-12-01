package net.sourceforge.greenvine.reveng.impl;



public class RevengConfig {

    private String modelName;
    private boolean createManyToOneInComponentIdentities = false;

    
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public boolean getCreateManyToOneInComponentIdentities() {
        return this.createManyToOneInComponentIdentities;
    }

    public void setCreateManyToOneInComponentIdentities(boolean createManyToOneInComponentIdentities) {
        this.createManyToOneInComponentIdentities = createManyToOneInComponentIdentities;
    }
}
