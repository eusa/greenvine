package net.sourceforge.greenvine.generator.impl.java.entity.hibernate;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.impl.java.entity.hibernate.HibernateEntityMappingGenerator;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class HibernateEntityMappingGeneratorTest extends BaseGeneratorTest {
	
	@Test
	public void testGenerate() throws Exception {
		
		// Export directory
		String exportDirectory = "target/greenvine/src/main/resources";
		createExportDirectory(exportDirectory);
		
		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/java/entity/hibernate/HibernateEntityMappingTemplate.vm", exportDirectory);
		
		// Create generator and configure
		HibernateEntityMappingGenerator generator = new HibernateEntityMappingGenerator(sourceConfig);
		
		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);
		
	}
}