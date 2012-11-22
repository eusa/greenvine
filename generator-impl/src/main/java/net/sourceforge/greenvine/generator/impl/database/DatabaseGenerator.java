package net.sourceforge.greenvine.generator.impl.database;

import java.util.Collections;
import java.util.List;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.task.TemplateTaskQueue;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;
import net.sourceforge.greenvine.model.api.Catalog;
import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.Model;
import net.sourceforge.greenvine.model.api.Table;

public class DatabaseGenerator implements Generator {

	private final String fileName;
	
	public DatabaseGenerator(String fileName) {
		this.fileName = fileName;
	}

	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {
		
	    for (Catalog catalog : model.getCatalogs()) {
	        
    		// Get the database
    		Database database = catalog.getDatabase();
    		
    		// Order the tables in reverse dependency order
    		List<? extends Table> reversedTables = database.getTablesInDependencyOrder();
    		Collections.reverse(reversedTables);
    		
    		// Create the TemplateContext
    		TemplateContext context = new TemplateContext();
    		context.put("database", database);
    		context.put("reversedTables", reversedTables);
    		context.put("generator", this);
    
    		// Add to the merge queue
    		queue.enqueue(template, context, null, fileName);
	    }

	}

	//public void setTypeMappingFile(String typeMapping) {
	//	this.typeMappingFile = typeMapping;
	//}
	

}
