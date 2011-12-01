package net.sourceforge.greenvine.generator.impl.java.entity.springjpa;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.task.TemplateTaskQueue;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;
import net.sourceforge.greenvine.model.api.Model;

public class SpringJpaContextGenerator implements Generator {

	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {
		
	    // Create TemplateContext
        TemplateContext context = new TemplateContext();
        context.put("model", model);
        
        // Add to the template processing queue
        queue.enqueue(template, context, null, "app-ctx-jpa.xml");
	}
}
