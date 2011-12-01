package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.FieldNamedObject;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.NamedObject;
import net.sourceforge.greenvine.model.naming.impl.FieldNameImpl;

public abstract class AbstractFieldNamedObject implements FieldNamedObject {

    protected final FieldNameImpl name;

    public AbstractFieldNamedObject(CharSequence name) throws ModelException {
        this.name = new FieldNameImpl(name);
    }
    
    public FieldNameImpl getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "[name=" + name + "]";
    }

    public int compareTo(NamedObject obj) {
        return this.name.compareTo(obj.getName());
    }

}
