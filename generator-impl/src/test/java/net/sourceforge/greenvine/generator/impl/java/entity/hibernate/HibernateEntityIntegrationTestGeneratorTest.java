package net.sourceforge.greenvine.generator.impl.java.entity.hibernate;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.impl.java.entity.hibernate.HibernateEntityIntegrationTestGenerator;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class HibernateEntityIntegrationTestGeneratorTest extends BaseGeneratorTest {
	
	@Test
	public void testGenerate() throws Exception {
		
		// Export directory
		String exportDirectory = "target/greenvine/src/test/java";
		createExportDirectory(exportDirectory);
		
		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/java/entity/hibernate/HibernateEntityIntegrationTestTemplate.vm", exportDirectory);
		
		// Create generator and configure
		HibernateEntityIntegrationTestGenerator generator = new HibernateEntityIntegrationTestGenerator(sourceConfig);
		
		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);
		
	}
}