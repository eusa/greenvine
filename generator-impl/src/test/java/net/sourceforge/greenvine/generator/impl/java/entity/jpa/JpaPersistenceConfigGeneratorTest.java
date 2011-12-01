package net.sourceforge.greenvine.generator.impl.java.entity.jpa;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class JpaPersistenceConfigGeneratorTest extends BaseGeneratorTest {
	
	@Test
	public void testGenerate() throws Exception {
		
		// Export directory
		String exportDirectory = "target/greenvine/src/main/resources/META-INF";
		createExportDirectory(exportDirectory);
		
		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/java/entity/jpa/JpaPersistenceConfigTemplate.vm", exportDirectory);
		
		// Create generator and configure
		JpaPersistenceConfigGenerator generator = new JpaPersistenceConfigGenerator(sourceConfig);
		
		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);
		
	}
}