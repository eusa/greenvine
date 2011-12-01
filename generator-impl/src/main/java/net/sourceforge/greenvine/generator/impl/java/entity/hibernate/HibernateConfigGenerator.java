package net.sourceforge.greenvine.generator.impl.java.entity.hibernate;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.helper.JavaHelper;
import net.sourceforge.greenvine.generator.runner.impl.SourceConfig;
import net.sourceforge.greenvine.generator.task.TemplateTaskQueue;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;
import net.sourceforge.greenvine.model.api.Catalog;
import net.sourceforge.greenvine.model.api.Model;

public class HibernateConfigGenerator implements Generator {

    private final SourceConfig sourceConfig;
    
    public HibernateConfigGenerator(SourceConfig sourceConfig) {
        this.sourceConfig = sourceConfig;
    }
    
	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {

	    // Iterate through catalog
	    for (Catalog catalog : model.getCatalogs()) {
	        
    		// Create TemplateContext
    		TemplateContext context = new TemplateContext();
    		context.put("model", model);
    		context.put("catalog", catalog);
    		context.put("javaHelper", new JavaHelper(sourceConfig));
    
    		// Merge the template
    		queue.enqueue(template, context, null, "hibernate.cfg.xml");
        }
	}
}
