package net.sourceforge.greenvine.generator.impl.java.unitils;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class UnitilsH2DbSupportGeneratorTest extends BaseGeneratorTest {
	
	@Test
	public void testGenerate() throws Exception {
		
		// Export directory
		String exportDirectory = "target/greenvine/src/test/java";
		createExportDirectory(exportDirectory);
		
		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/java/unitils/UnitilsH2DbSupportTemplate.vm", exportDirectory);
		
		// Create generator and configure
		UnitilsH2DbSupportGenerator generator = new UnitilsH2DbSupportGenerator();
		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);
		
	}
}