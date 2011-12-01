package net.sourceforge.greenvine.generator.impl.java.entity.hibernate;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.impl.java.entity.hibernate.HibernateEhcacheConfigGenerator;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class HibernateEhcacheConfigGeneratorTest extends BaseGeneratorTest {
	
	@Test
	public void testGenerate() throws Exception {
		
		// Export directory
		String exportDirectory = "target/greenvine/src/main/resources";
		createExportDirectory(exportDirectory);
		
		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/java/entity/hibernate/HibernateEhcacheConfigTemplate.vm", exportDirectory);
		
		// Create generator and configure
		HibernateEhcacheConfigGenerator generator = new HibernateEhcacheConfigGenerator(sourceConfig);
		
		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);
		
	}
}