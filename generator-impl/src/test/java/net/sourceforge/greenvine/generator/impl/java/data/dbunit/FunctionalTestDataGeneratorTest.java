package net.sourceforge.greenvine.generator.impl.java.data.dbunit;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class FunctionalTestDataGeneratorTest extends BaseGeneratorTest {
	
	@Test
	public void testGenerate() throws Exception {
		
		// Export directory
		String exportDirectory = "target/greenvine/src/main/resources";
		createExportDirectory(exportDirectory);
		
		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/database/DmlTemplate.vm", exportDirectory);
		
		// Create generator and configure
		FunctionalTestDataGenerator generator = new FunctionalTestDataGenerator(100);
		
		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);
		
	}

}
