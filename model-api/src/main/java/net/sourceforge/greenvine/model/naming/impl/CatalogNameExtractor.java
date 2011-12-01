package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.Model;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;

public class CatalogNameExtractor extends AbstractNameExtractor<Database, Model> {

    
    public CatalogNameExtractor(RdbmsNamingConventions namingConventions) throws ModelException {
        super (namingConventions);
    }
    
    public ModelNameImpl extractName(Model model, Database source) throws ModelException {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(namingConventions.getDatabase());
        return new ModelNameImpl(helper.extractModelName(source));
    }

}
