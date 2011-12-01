package net.sourceforge.greenvine.model.naming;

import net.sourceforge.greenvine.model.api.ModelException;

public interface NameValidator<T extends CharSequence> {

    public abstract void validateName(T name) throws ModelException;
    
}
