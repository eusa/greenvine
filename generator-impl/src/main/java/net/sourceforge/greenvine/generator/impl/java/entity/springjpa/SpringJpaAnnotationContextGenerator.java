package net.sourceforge.greenvine.generator.impl.java.entity.springjpa;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.helper.JavaHelper;
import net.sourceforge.greenvine.generator.runner.impl.SourceConfig;
import net.sourceforge.greenvine.generator.task.TemplateTaskQueue;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;
import net.sourceforge.greenvine.model.api.Model;

public class SpringJpaAnnotationContextGenerator implements Generator {

	private final SourceConfig sourceConfig;

	public SpringJpaAnnotationContextGenerator(SourceConfig sourceConfig) {
		this.sourceConfig = sourceConfig;
	}

	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {

		// Create utility helper class
		JavaHelper javaHelper = new JavaHelper(sourceConfig);

		// Create package
		String rootPackage = javaHelper.getRootPackage();
		String configPackage = rootPackage + ".config";
		String dataPackage = javaHelper.getDataPackage();

		// Get the directory path
		String directory = javaHelper.packageToFolder(configPackage);

		// Create TemplateContext
		TemplateContext context = new TemplateContext();
		context.put("model", model);
		context.put("dataPackage", dataPackage);
		context.put("configPackage", configPackage);

		// Add to the template processing queue
		queue.enqueue(template, context, directory, "JpaConfig.java");
	}
}
