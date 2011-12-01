package net.sourceforge.greenvine.model.api;



public interface NaturalIdentity extends Field {
    
    abstract public Entity getFieldCollection();
    
    abstract public UniqueKey getUniqueKey();

}
