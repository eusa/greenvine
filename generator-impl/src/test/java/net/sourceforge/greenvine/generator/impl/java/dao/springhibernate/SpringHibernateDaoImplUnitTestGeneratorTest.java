package net.sourceforge.greenvine.generator.impl.java.dao.springhibernate;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplUnitTestGenerator;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class SpringHibernateDaoImplUnitTestGeneratorTest extends BaseGeneratorTest {
	
	@Test
	public void testGenerate() throws Exception {
		
		// Export directory
		String exportDirectory = "target/greenvine/src/test/java";
		createExportDirectory(exportDirectory);
		
		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/java/dao/springhibernate/SpringHibernateDaoImplUnitTestTemplate.vm", exportDirectory);
		
		// Create generator and configure
		SpringHibernateDaoImplUnitTestGenerator generator = new SpringHibernateDaoImplUnitTestGenerator(sourceConfig);
		
		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);
		
	}

}
