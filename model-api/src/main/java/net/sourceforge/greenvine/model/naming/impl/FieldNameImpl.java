package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.FieldName;

public class FieldNameImpl extends AbstractName implements FieldName {

    public FieldNameImpl(CharSequence name) throws ModelException {
        super(name);
    }

}
