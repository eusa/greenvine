package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.impl.EntityImpl;
import net.sourceforge.greenvine.model.naming.NameExtractor;

public class NaturalIdentityNameExtractor implements NameExtractor<EntityImpl, EntityImpl> {
   
    public FieldNameImpl extractName(EntityImpl source,EntityImpl container) throws ModelException {
        CamelCaseNameSegmentBuilderImpl builder = new CamelCaseNameSegmentBuilderImpl(source.getName().getObjectName());
        return new FieldNameImpl(builder.append("NaturalIdentity"));
    }

}
