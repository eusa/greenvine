package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.Component;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;

public class SimplePropertyNameExtractor extends AbstractNameExtractor<Column, Component> {

   
    public SimplePropertyNameExtractor(RdbmsNamingConventions namingConventions) throws ModelException {
        super(namingConventions);
    }
    
    public FieldNameImpl extractName(Component container, Column source) throws ModelException {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(namingConventions.getDataColumn());
        return new FieldNameImpl(helper.extractModelName(source));
    }

}
