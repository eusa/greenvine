package net.sourceforge.greenvine.generator.impl.java.maven;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.runner.impl.SourceConfig;
import net.sourceforge.greenvine.generator.task.TemplateTaskQueue;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;
import net.sourceforge.greenvine.model.api.Model;

public class PomGenerator implements Generator {

private final SourceConfig sourceConfig;
    
    public PomGenerator(SourceConfig sourceConfig) {
        this.sourceConfig = sourceConfig;
    }
    
	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {
			
		TemplateContext context = new TemplateContext();
		context.put("model", model);
        context.put("sourceConfig", sourceConfig);

		// Merge the template
		queue.enqueue(template, context, null, "pom.xml");
	}
}