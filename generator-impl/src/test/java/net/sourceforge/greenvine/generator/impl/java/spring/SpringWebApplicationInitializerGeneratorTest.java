package net.sourceforge.greenvine.generator.impl.java.spring;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class SpringWebApplicationInitializerGeneratorTest extends BaseGeneratorTest {

	@Test
	public void testGenerate() throws Exception {

		// Export directory
		String exportDirectory = "target/greenvine/src/main/java";
		createExportDirectory(exportDirectory);

		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/java/spring/SpringWebApplicationInitializerTemplate.vm", exportDirectory);

		// Create generator and configure
		SpringWebApplicationInitializerGenerator generator = new SpringWebApplicationInitializerGenerator(sourceConfig);

		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);

	}
}