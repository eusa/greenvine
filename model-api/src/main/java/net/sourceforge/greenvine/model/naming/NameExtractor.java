package net.sourceforge.greenvine.model.naming;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.NamedObject;

public interface NameExtractor<T extends NamedObject, U extends NameContainer> {
    
    public abstract Name extractName(U container, T source) throws ModelException;
    
}