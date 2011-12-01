package net.sourceforge.greenvine.generator.impl.java.unitils;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.helper.JavaHelper;
import net.sourceforge.greenvine.generator.runner.impl.SourceConfig;
import net.sourceforge.greenvine.generator.task.TemplateTaskQueue;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;
import net.sourceforge.greenvine.model.api.Catalog;
import net.sourceforge.greenvine.model.api.Model;

public class UnitilsPropertiesGenerator implements Generator {

    private final SourceConfig sourceConfig;
    
    public UnitilsPropertiesGenerator(SourceConfig sourceConfig) {
        this.sourceConfig = sourceConfig;
    }
    
	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {
		
		// Create utility helper class
		JavaHelper javaHelper = new JavaHelper(sourceConfig);
		
		// Possible future task:
		// Support more than one database possible in unitils.
		// Not currently possible.
		// https://sourceforge.net/projects/unitils/forums/forum/570578/topic/3772170
		// Set property to unitils file in surefire config
		// -Dunitils.configuration.localFileName will set the name of the file in the home directory 
		// -Dunitils.configuration.customFileName will set the name of the custom properties in the classpath.
		
		// Iterate through catalogs
		for (Catalog catalog : model.getCatalogs()) {
		
    		// Create TemplateContext
    		TemplateContext context = new TemplateContext();
    		context.put("catalog", catalog);
    		context.put("javaHelper", javaHelper);
    		
    		// Add to the template processing queue
    		queue.enqueue(template, context, null, "unitils.properties");
		}
	}
}
