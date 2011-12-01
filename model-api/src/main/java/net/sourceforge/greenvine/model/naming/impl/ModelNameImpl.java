package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.ModelName;

public class ModelNameImpl extends AbstractName implements ModelName {
    
    public ModelNameImpl(CharSequence name) throws ModelException {
        super(name);
    }

}
