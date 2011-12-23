package net.sourceforge.greenvine.reveng.impl;



public class RevengConfig {

	/**
	 * The model name to set
	 */
    private String modelName;
    
    /**
     * Whether or not to create many-to-one
     * fields in ComponentIdentities, or
     * instead use simple properties and create
     * a "shadow" many-to-one within the entity.
     * Exists only because it seems Hibernate
     * cannot handle certain cases where a
     * many-to-one field is declared inside
     * a ComponentIdentity. Also, it is easier
     * to handle creating an identity if it 
     * uses non-relation fields - you can 
     * just set primitive fields to do so.
     */
    private boolean createManyToOneInComponentIdentities = false;
    
    /**
     * Natural identities are meaningful
     * in Hibernate, where they can 
     * be used like normal identities.
     * They are defined by unique, business keys
     * in addition to the primary key.
     * They don't mean anything in 
     * JPA, so disable them by default
     */
    private boolean createNaturalIdentities = false;

    
    public boolean isCreateNaturalIdentities() {
		return createNaturalIdentities;
	}

	public void setCreateNaturalIdentities(boolean createNaturalIdentities) {
		this.createNaturalIdentities = createNaturalIdentities;
	}

	public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public boolean isCreateManyToOneInComponentIdentities() {
        return this.createManyToOneInComponentIdentities;
    }

    public void setCreateManyToOneInComponentIdentities(boolean createManyToOneInComponentIdentities) {
        this.createManyToOneInComponentIdentities = createManyToOneInComponentIdentities;
    }
}
