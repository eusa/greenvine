package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PropertyValueGenerationStrategy;

public class IdentityFieldImplBuilder extends AbstractColumnFieldBuilder {
    
    protected ComponentIdentityImpl componentIdentity;
    protected PropertyValueGenerationStrategy keyGenerationStrategy = PropertyValueGenerationStrategy.ASSIGNED;
    
    public ComponentIdentityImpl getComponentIdentity() {
        return this.componentIdentity;
    }
    
    public PropertyValueGenerationStrategy getPropertyValueGenerationStrategy() {
        return this.keyGenerationStrategy;
    }
    
    public IdentityFieldImplBuilder setComponentIdentity(ComponentIdentityImpl componentIdentity) {
        this.componentIdentity = componentIdentity;
        return this;
    }
    
    public IdentityFieldImplBuilder setPropertyValueGenerationStrategy(PropertyValueGenerationStrategy strategy) {
        this.keyGenerationStrategy = strategy;
        return this;
    }
    
    public IdentityFieldImpl build() throws ModelException {
        return new IdentityFieldImpl(name, propertyType, keyGenerationStrategy, columnName, componentIdentity);
    }

}
