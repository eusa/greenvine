package net.sourceforge.greenvine.generator;

import net.sourceforge.greenvine.generator.task.TemplateTaskQueue;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.model.api.Model;

public interface Generator {
	
	public <T extends Object> void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception;
}
