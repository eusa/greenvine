package net.sourceforge.greenvine.generator.task.tests;

import java.io.File;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.task.TemplateTaskQueue;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;
import net.sourceforge.greenvine.model.api.Model;

public class MockGenerator implements Generator {

	private final boolean fail;
	
	public MockGenerator() {
		this.fail = false;
	}
	
	public MockGenerator(boolean fail) {
		this.fail = fail;
	}
	
	public void generate(Model model, Template template,
			TemplateTaskQueue queue) throws Exception {
		if(!fail)
			queue.enqueue(template, new TemplateContext(), "test" + File.separatorChar + "directory", "test.file");
		else
			throw new Exception();

	}

}
