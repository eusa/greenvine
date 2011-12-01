package net.sourceforge.greenvine.generator.impl.java.dao;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.impl.java.dao.DaoInterfaceGenerator;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class DaoInterfaceGeneratorTest extends BaseGeneratorTest {
	
	@Test
	public void testGenerate() throws Exception {
		
		// Export directory
		String exportDirectory = "target/greenvine/src/main/java";
		createExportDirectory(exportDirectory);
		
		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/java/dao/DaoInterfaceTemplate.vm", exportDirectory);
		
		// Create generator and configure
		DaoInterfaceGenerator generator = new DaoInterfaceGenerator(sourceConfig);
		
		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);
		
	}
}