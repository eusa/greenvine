package net.sourceforge.greenvine.generator.impl.java.dao.springhibernate;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.helper.JavaHelper;
import net.sourceforge.greenvine.generator.runner.impl.SourceConfig;
import net.sourceforge.greenvine.generator.task.TemplateTaskQueue;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;
import net.sourceforge.greenvine.model.api.Model;

public class SpringHibernateDaoContextGenerator implements Generator {

    private final SourceConfig sourceConfig;
    
    public SpringHibernateDaoContextGenerator(SourceConfig sourceConfig) {
        this.sourceConfig = sourceConfig;
    }
    
	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {

		// Create utility helper class
		JavaHelper javaHelper = new JavaHelper(sourceConfig);

	    TemplateContext context = new TemplateContext();
		context.put("model", model);
		context.put("javaHelper", javaHelper);
		context.put("sourceConfig", sourceConfig);

		// Merge the template
		queue.enqueue(template, context, null, "app-ctx-hibernate-dao.xml");
	}

}
