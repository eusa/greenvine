package net.sourceforge.greenvine.generator.impl.java.dao.springhibernate;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class SpringHibernateDaoImplGeneratorTest extends BaseGeneratorTest {
	
	@Test
	public void testGenerate() throws Exception {
		
		// Export directory
		String exportDirectory = "target/greenvine/src/main/java";
		createExportDirectory(exportDirectory);
		
		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/java/dao/springhibernate/SpringHibernateDaoImplTemplate.vm", exportDirectory);
		
		// Create generator and configure
		SpringHibernateDaoImplGenerator generator = new SpringHibernateDaoImplGenerator(sourceConfig);
				
		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);
		
	}

}
