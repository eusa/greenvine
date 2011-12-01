package net.sourceforge.greenvine.generator.impl.java.entity;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.impl.java.entity.EntityUnitTestGenerator;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class EntityUnitTestGeneratorTest extends BaseGeneratorTest {
	
	@Test
	public void testGenerate() throws Exception {
		
		// Export directory
		String exportDirectory = "target/greenvine/src/test/java";
		createExportDirectory(exportDirectory);
		
		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/java/entity/EntityUnitTestTemplate.vm", exportDirectory);
		
		// Create generator and configure
		EntityUnitTestGenerator generator = new EntityUnitTestGenerator(sourceConfig);
		
		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);
		
	}
}