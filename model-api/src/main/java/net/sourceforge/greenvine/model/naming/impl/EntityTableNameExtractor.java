package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.Catalog;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;

public class EntityTableNameExtractor extends AbstractNameExtractor<Table, Catalog> {

    
    public EntityTableNameExtractor(RdbmsNamingConventions namingConventions) throws ModelException {
        super (namingConventions);
    }
    
    public EntityNameImpl extractName(Catalog container, Table source) throws ModelException {
        
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(namingConventions.getTable());
        CharSequence namespace = null;
        CharSequence name = null;
        
        // Get the namespace from the schema
        if (source.getName().getSchemaName() != null) {
        	// TODO temporary workaround due to H2 default namespace being reserved word
        	if (!source.getName().getSchemaName().equals("PUBLIC")) {
        		namespace = helper.extractNameSpace(source.getName().getSchemaName());
        	}
        }
        
        // Get the name from the table name
        name = helper.extractModelName(source.getName().getObjectName()).depluralise();
        
        // Return the entity name
        return new EntityNameImpl(namespace, name);
    }

}
