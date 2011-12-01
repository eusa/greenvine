package net.sourceforge.greenvine.generator.impl.java.entity;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.impl.java.entity.EntityIdentityComparatorGenerator;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class EntityIdentityComparatorGeneratorTest extends BaseGeneratorTest {
	
	@Test
	public void testGenerate() throws Exception {
		
		// Export directory
		String exportDirectory = "target/greenvine/src/main/java";
		createExportDirectory(exportDirectory);
		
		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/java/entity/EntityIdentityComparatorTemplate.vm", exportDirectory);
		
		// Create generator and configure
		EntityIdentityComparatorGenerator generator = new EntityIdentityComparatorGenerator(sourceConfig);
		
		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);
		
	}
	
}
