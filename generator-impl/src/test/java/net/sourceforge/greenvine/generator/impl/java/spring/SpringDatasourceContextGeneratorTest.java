package net.sourceforge.greenvine.generator.impl.java.spring;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class SpringDatasourceContextGeneratorTest extends BaseGeneratorTest {
	
	@Test
	public void testGenerate() throws Exception {
		
		// Export directory
		String exportDirectory = "target/greenvine/src/main/resources";
		createExportDirectory(exportDirectory);
		
		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/java/spring/SpringDatasourceContextTemplate.vm", exportDirectory);
		
		// Create generator and configure
		SpringDatasourceContextGenerator generator = new SpringDatasourceContextGenerator();
		
		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);
		
	}
}