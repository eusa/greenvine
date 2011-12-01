package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.Component;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;

public abstract class ParentNameExtractor extends AbstractAggregationRelationFieldNameExtractor<ForeignKey, Component> {

     public ParentNameExtractor(RdbmsNamingConventions namingConventions) {
         super(namingConventions);
     }

}
