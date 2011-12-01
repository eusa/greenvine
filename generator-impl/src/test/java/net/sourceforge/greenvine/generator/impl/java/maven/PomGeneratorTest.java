package net.sourceforge.greenvine.generator.impl.java.maven;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.impl.java.maven.PomGenerator;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class PomGeneratorTest extends BaseGeneratorTest {
	
	@Test
	public void testGenerate() throws Exception {
		
		// Export directory
		String exportDirectory = "target/greenvine";
		createExportDirectory(exportDirectory);
		
		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/java/maven/PomTemplate.vm", exportDirectory);
		
		// Create generator and configure
		PomGenerator generator = new PomGenerator(sourceConfig);
		
		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);
		
	}

}
