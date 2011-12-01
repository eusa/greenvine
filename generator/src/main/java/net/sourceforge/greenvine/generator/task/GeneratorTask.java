package net.sourceforge.greenvine.generator.task;

import java.util.concurrent.Callable;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.model.api.Model;

public class GeneratorTask implements Callable<GenerateResult> {
	
	private final Generator generator;
	private final Model model;
	private final Template template;
	private final TemplateTaskQueue queue;
	
	
	public GeneratorTask(Generator generator, Model model, Template template, TemplateTaskQueue queue) {
		super();
		this.generator = generator;
		this.model = model;
		this.template = template;
		this.queue = queue;
	}
	
	public GenerateResult call() throws Exception {
		
		GenerateResult generateResult = null;
		try {
			this.generator.generate(model, template, queue);
			generateResult = new GenerateResult(generator.getClass());
		} catch (Exception e) {
			generateResult = new GenerateResult(generator.getClass(), e);
		}
		return generateResult;
	}

}
