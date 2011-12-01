package net.sourceforge.greenvine.generator.impl.java.entity.springhibernate;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class SpringHibernateContextGeneratorTest extends BaseGeneratorTest {
	
	@Test
	public void testGenerate() throws Exception {
		
		// Export directory
		String exportDirectory = "target/greenvine/src/main/resources";
		createExportDirectory(exportDirectory);
		
		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/java/entity/springhibernate/SpringHibernateContextTemplate.vm", exportDirectory);
		
		// Create generator and configure
		SpringHibernateContextGenerator generator = new SpringHibernateContextGenerator();
		
		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);
		
	}
}