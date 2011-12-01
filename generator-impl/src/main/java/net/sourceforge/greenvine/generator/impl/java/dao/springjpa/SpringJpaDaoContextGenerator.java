package net.sourceforge.greenvine.generator.impl.java.dao.springjpa;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.helper.JavaHelper;
import net.sourceforge.greenvine.generator.runner.impl.SourceConfig;
import net.sourceforge.greenvine.generator.task.TemplateTaskQueue;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;
import net.sourceforge.greenvine.model.api.Catalog;
import net.sourceforge.greenvine.model.api.Model;

public class SpringJpaDaoContextGenerator implements Generator {

    private final SourceConfig sourceConfig;
    
    public SpringJpaDaoContextGenerator(SourceConfig sourceConfig) {
        this.sourceConfig = sourceConfig;
    }
    
	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {

		// Create utility helper class
		JavaHelper javaHelper = new JavaHelper(sourceConfig);

		for (Catalog catalog : model.getCatalogs()) {
		    
    		// Set up context
    		TemplateContext context = new TemplateContext();
    		context.put("model", model);
    		context.put("javaHelper", javaHelper);
            context.put("sourceConfig", sourceConfig);
    		
    		String filename = String.format("app-ctx-%s-jpa-dao.xml", catalog.getName());
    
    		// Merge the template
    		queue.enqueue(template, context, null, filename);
		}
	}

}
