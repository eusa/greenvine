package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.NameContainer;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;

public class IdentityFieldNameExtractor extends AbstractNameExtractor<Column, NameContainer> {
   
    public IdentityFieldNameExtractor(RdbmsNamingConventions namingConventions) throws ModelException {
        super(namingConventions);
    }
    
    public FieldNameImpl extractName(NameContainer container, Column source) throws ModelException {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(namingConventions.getPrimaryColumn());
        return new FieldNameImpl(helper.extractModelName(source));
    }

}
