package net.sourceforge.greenvine.generator.task;

import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;

public interface TemplateTaskQueue {
	
	public void enqueue(Template template, TemplateContext context,
			String directory, String fileName);

}
