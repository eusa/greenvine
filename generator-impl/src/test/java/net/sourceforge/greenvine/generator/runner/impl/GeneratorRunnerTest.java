package net.sourceforge.greenvine.generator.runner.impl;

import java.io.File;
import java.io.IOException;

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
	public void testGeneratorRunnerH2() throws Exception {

		// Get the runner config
		GeneratorRunner runner = getRunnerConfig();

		// Create model
		Model model = TestModelExtractor.getH2Model("../database-extractor/src/test/resources/test-schema-h2.sql");

		// Create output directory
		File outputDirectory = createOutputDirectory("target/greenvine-h2");

		// Test the runner
		GeneratorRunnerResult result = runner.generate(model, outputDirectory);

		// Check results 
		validateResult(result);
	}

	@Test
	public void testGeneratorRunnerMySql() throws Exception {

		// Get the runner config
		GeneratorRunner runner = getRunnerConfig();

		// Create model
		Model model = TestModelExtractor.getMySqlModel("../database-extractor/src/test/resources/test-schema-mysql.sql");

		// Create output directory
		File outputDirectory = createOutputDirectory("target/greenvine-mysql");

		// Test the runner
		GeneratorRunnerResult result = runner.generate(model, outputDirectory);

		// Check results 
		validateResult(result);
	}

	

	@Test
	public void testMavenH2() throws Exception {

		GeneratorRunner runner = getRunnerConfig();

		// Create model
		Model model = TestModelExtractor.getH2Model("../database-extractor/src/test/resources/test-schema-h2.sql");

		// Create output directory
		File outputDirectory = createOutputDirectory("target/greenvine-h2");

		// Execute the runner
		runner.generate(model, outputDirectory);

		// Run a maven build
		Verifier verifier = new Verifier(outputDirectory.getAbsolutePath());
		verifier.executeGoal("test");
	}
	
	@Test
	public void testMavenMySql() throws Exception {

		GeneratorRunner runner = getRunnerConfig();

		// Create model
		Model model = TestModelExtractor.getMySqlModel("../database-extractor/src/test/resources/test-schema-mysql.sql");

		// Create output directory
		File outputDirectory = createOutputDirectory("target/greenvine-mysql");

		// Execute the runner
		runner.generate(model, outputDirectory);

		// Run a maven build
		Verifier verifier = new Verifier(outputDirectory.getAbsolutePath());
		verifier.executeGoal("test");
	}

	private GeneratorRunner getRunnerConfig() {
		GeneratorRunnerConfigFactory factory = new GeneratorRunnerConfigFactory("src/test/resources/generator-ctx.xml");
		GeneratorRunnerConfig generatorConfig = factory.getGeneratorRunnerConfig();

		// Create GeneratorRunner
		GeneratorRunner runner = new GeneratorRunnerImpl(generatorConfig);
		return runner;
	}

	private void validateResult(GeneratorRunnerResult result) {
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
	
	private File createOutputDirectory(String path) throws IOException {
		File outputDirectory = new File(path);
		if (outputDirectory.exists()) {
			delete(outputDirectory);
			outputDirectory = new File(path);
		}
		return outputDirectory;
	}

	private void delete(File f) throws IOException {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				delete(c);
		}
		f.delete();
	}

}
