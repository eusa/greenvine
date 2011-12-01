package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;


public class SimpleIdentityNameExtractor extends AbstractNameExtractor<Column, Entity> {

   
    public SimpleIdentityNameExtractor(RdbmsNamingConventions namingConventions) throws ModelException {
        super(namingConventions);
    }
    
    public FieldNameImpl extractName(Entity container, Column source) throws ModelException {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(namingConventions.getPrimaryColumn());
        return new FieldNameImpl(helper.extractModelName(source));
    }

}
