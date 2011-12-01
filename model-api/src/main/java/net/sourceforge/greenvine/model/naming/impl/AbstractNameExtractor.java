package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.NamedObject;
import net.sourceforge.greenvine.model.naming.NameContainer;
import net.sourceforge.greenvine.model.naming.NameExtractor;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;

public abstract class AbstractNameExtractor<T extends NamedObject, U extends NameContainer> implements NameExtractor<T, U> {

    protected final RdbmsNamingConventions namingConventions;
    
    public AbstractNameExtractor(RdbmsNamingConventions namingConventions) {
        this.namingConventions = namingConventions;
    }
}
