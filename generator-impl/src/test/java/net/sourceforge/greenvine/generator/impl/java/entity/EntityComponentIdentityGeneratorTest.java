package net.sourceforge.greenvine.generator.impl.java.entity;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.impl.java.entity.EntityComponentIdentityGenerator;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class EntityComponentIdentityGeneratorTest extends BaseGeneratorTest {
	
	@Test
	public void testGenerate() throws Exception {
		
		// Export directory
		String exportDirectory = "target/greenvine/src/main/java";
		createExportDirectory(exportDirectory);
		
		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/java/entity/EntityComponentIdentityTemplate.vm", exportDirectory);
		
		// Create generator and configure
		EntityComponentIdentityGenerator generator = new EntityComponentIdentityGenerator(sourceConfig);
		
		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);
		
	}
	
}
