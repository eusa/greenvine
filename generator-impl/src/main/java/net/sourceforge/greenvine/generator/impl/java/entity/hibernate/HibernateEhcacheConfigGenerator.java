package net.sourceforge.greenvine.generator.impl.java.entity.hibernate;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.helper.JavaHelper;
import net.sourceforge.greenvine.generator.runner.impl.SourceConfig;
import net.sourceforge.greenvine.generator.task.TemplateTaskQueue;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;
import net.sourceforge.greenvine.model.api.Catalog;
import net.sourceforge.greenvine.model.api.Model;

public class HibernateEhcacheConfigGenerator implements Generator {

    private final SourceConfig sourceConfig;
    
    public HibernateEhcacheConfigGenerator(SourceConfig sourceConfig) {
        this.sourceConfig = sourceConfig;
    }
    
	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {
		
		// Create utility helper class
		JavaHelper javaHelper = new JavaHelper(sourceConfig);
		
		// Iterate through catalog
        for (Catalog catalog : model.getCatalogs()) {

    		// Create TemplateContext
    		TemplateContext context = new TemplateContext();
    		context.put("model", model);
    		context.put("javaHelper", javaHelper);
    
    		// Merge the template
    		queue.enqueue(template, context, null, catalog.getName() + "-ehcache.xml");
        }
	}
}
