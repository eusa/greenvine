package net.sourceforge.greenvine.generator.runner.impl;

import java.io.File;

import junit.framework.Assert;
import net.sourceforge.greenvine.generator.runner.GeneratorRunner;
import net.sourceforge.greenvine.generator.runner.GeneratorRunnerResult;
import net.sourceforge.greenvine.generator.task.GenerateResult;
import net.sourceforge.greenvine.generator.task.MergeResult;
import net.sourceforge.greenvine.model.api.Model;
import net.sourceforge.greenvine.reveng.testutils.TestModelExtractor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.it.Verifier;
import org.junit.Test;

public class GeneratorRunnerTest {
    
    private static Log logger = LogFactory.getLog(GeneratorRunnerTest.class);
	
	@Test
	public void testGeneratorRunner() throws Exception {
		
	    // Get the runner config
        GeneratorRunnerConfigFactory factory = new GeneratorRunnerConfigFactory("src/test/resources/generator-ctx.xml");
        GeneratorRunnerConfig generatorConfig = factory.getGeneratorRunnerConfig();
	    
	    // Create GeneratorRunner
		GeneratorRunner runner = new GeneratorRunnerImpl(generatorConfig);
		
		// Create model
		Model model = TestModelExtractor.getTestModel("../database-extractor/src/test/resources/test-schema-h2.sql");
		
		// Create output directory
		File outputDirectory = new File("target/greenvine");
		
		// Test the runner
		GeneratorRunnerResult result = runner.generate(model, outputDirectory);
		
		// Check results 
		int errorCount = 0;
		for (GenerateResult gr : result.getGenerateResults()) {
		    if (gr.getException() != null) {
		        logger.error(gr.getGeneratorClass().getName(), gr.getException());
		        errorCount ++;
		    }
		}
		
		for (MergeResult mr : result.getMergeResults()) {
            if (mr.getException() != null) {
                logger.error(mr.getFileName() , mr.getException());
                errorCount ++;
            }
        }
		if (errorCount > 0) {
		    Assert.fail("Either some generators or templates failed, see logs for details.");
		}
	}
	
	@Test
	public void testMaven() throws Exception {
		
		// Get the runner config
        GeneratorRunnerConfigFactory factory = new GeneratorRunnerConfigFactory("src/test/resources/generator-ctx.xml");
        GeneratorRunnerConfig generatorConfig = factory.getGeneratorRunnerConfig();
	    
	    // Create GeneratorRunner
		GeneratorRunner runner = new GeneratorRunnerImpl(generatorConfig);
		
		// Create model
		Model model = TestModelExtractor.getTestModel("../database-extractor/src/test/resources/test-schema-h2.sql");
		
		// Create output directory
		File outputDirectory = new File("target/greenvine-maven");
		if (outputDirectory.exists()) {
			outputDirectory.delete();
			outputDirectory.createNewFile();
		}
		
		// Execute the runner
		runner.generate(model, outputDirectory);
		
		// Run a maven build
		Verifier verifier = new Verifier(outputDirectory.getAbsolutePath());
		verifier.executeGoal("test");
	}

}
