package net.sourceforge.greenvine.generator.impl.java.dao.springhibernate;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplIntegrationTestGenerator;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class SpringHibernateDaoImplIntegrationTestGeneratorTest extends BaseGeneratorTest {
	
	@Test
	public void testGenerate() throws Exception {
		
		// Export directory
		String exportDirectory = "target/greenvine/src/test/java";
		createExportDirectory(exportDirectory);
		
		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/java/dao/springhibernate/SpringHibernateDaoImplIntegrationTestTemplate.vm", exportDirectory);
		
		// Create generator and configure
		SpringHibernateDaoImplIntegrationTestGenerator generator = new SpringHibernateDaoImplIntegrationTestGenerator(sourceConfig);
		
		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);
		
	}

}
