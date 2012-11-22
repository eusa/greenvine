package net.sourceforge.greenvine.generator.impl.database;

import net.sourceforge.greenvine.generator.impl.BaseGeneratorTest;
import net.sourceforge.greenvine.generator.impl.database.DatabaseGenerator;
import net.sourceforge.greenvine.generator.template.Template;

import org.junit.Test;

public class DatabaseGeneratorTest extends BaseGeneratorTest {
	
	@Test
	public void testGenerateH2() throws Exception {
		
		// Export directory
		String exportDirectory = "target/greenvine/src/main/database/h2";
		createExportDirectory(exportDirectory);
		
		// Create template
		Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/database/DatabaseH2Template.vm", exportDirectory);
		
		// Create generator and configure
		DatabaseGenerator generator = new DatabaseGenerator("001_initial.sql");
		
		// Execute generator task
		this.executor.addGeneratorTask(generator, model, template);
		
	}
	
	@Test
    public void testGenerateMySQL() throws Exception {
        
        // Export directory
        String exportDirectory = "target/greenvine/src/main/database/mysql";
        createExportDirectory(exportDirectory);
        
        // Create template
        Template template = this.factory.assembleTemplate("net/sourceforge/greenvine/generator/impl/database/DatabaseMySQLTemplate.vm", exportDirectory);
        
        // Create generator and configure
        DatabaseGenerator generator = new DatabaseGenerator("001_initial.sql");
        
        // Execute generator task
        this.executor.addGeneratorTask(generator, model, template);
        
    }

}
