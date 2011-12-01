package net.sourceforge.greenvine.generator.impl.java.dao.springhibernate;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoContextGenerator;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class SpringHibernateDaoContextGeneratorTest extends BaseGeneratorTest {
	
	@Test
	public void testGenerate() throws Exception {
		
		// Export directory
		String exportDirectory = "target/greenvine/src/main/resources";
		createExportDirectory(exportDirectory);
		
		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/java/dao/springhibernate/SpringHibernateDaoContextTemplate.vm", exportDirectory);
		
		// Create generator and configure
		SpringHibernateDaoContextGenerator generator = new SpringHibernateDaoContextGenerator(sourceConfig);
		
		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);
		
	}
	
	

}
