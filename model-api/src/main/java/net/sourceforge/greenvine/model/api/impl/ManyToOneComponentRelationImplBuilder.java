package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.MutableComponentField;

public class ManyToOneComponentRelationImplBuilder extends
        AbstractAggregationRelationBuilder {
    
    private MutableComponentField component;
    
    public ManyToOneComponentRelationImplBuilder setComponent(MutableComponentField component) {
        this.component = component;
        return this;
    }
    
    public MutableComponentField getComponent() {
        return component;
    }
    
    public ManyToOneComponentRelationImpl build() throws ModelException {
        return new ManyToOneComponentRelationImpl(ownerField, inverseField, foreignKey, component, catalog);
    }

}
