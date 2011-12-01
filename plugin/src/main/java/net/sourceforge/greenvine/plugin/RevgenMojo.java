package net.sourceforge.greenvine.plugin;

import java.io.File;

import net.sourceforge.greenvine.dbextractor.DatabaseExtractor;
import net.sourceforge.greenvine.dbextractor.DatabaseExtractorFactory;
import net.sourceforge.greenvine.generator.runner.GeneratorRunner;
import net.sourceforge.greenvine.generator.runner.GeneratorRunnerResult;
import net.sourceforge.greenvine.generator.runner.impl.GeneratorRunnerConfig;
import net.sourceforge.greenvine.generator.runner.impl.GeneratorRunnerConfigFactory;
import net.sourceforge.greenvine.generator.runner.impl.GeneratorRunnerImpl;
import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.Model;
import net.sourceforge.greenvine.plugin.util.GeneratorRunnerResultLogger;
import net.sourceforge.greenvine.reveng.ReverseEngineer;
import net.sourceforge.greenvine.reveng.ReverseEngineerFactory;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * Generates all the stuffs
 * 
 * @goal revgen
 */
public class RevgenMojo extends BaseMojo {

	/**
	 * The directory containing the config files
	 * 
	 * @parameter expression="${basedir}/src/main/greenvine"
	 */
	private File configDirectory;

	/**
	 * The directory to output the generated sources to
	 * 
	 * @parameter expression="${project.build.directory}/generated-sources/greenvine"
	 */
	private File outputDirectory;
	
	public void execute() throws MojoExecutionException {
		
		// No way baby let's go!
		getLog().info("Revgen starting.");

		// Create output directory if necessary
		checkOutputDirectory(outputDirectory);
		
		try {
		    
		    // Reverse engineering config file
            File revengConfigFile = new File(configDirectory, "reveng-ctx.xml");
            
            // Generator config file
            File generatorConfigFile = new File(configDirectory, "generator-ctx.xml");
            
            // Database extractor config file
            File dbextractorConfigFile = new File(configDirectory, "dbextractor-ctx.xml");
            
			// Extract database
			DatabaseExtractorFactory dbExtractorFactory = new DatabaseExtractorFactory(dbextractorConfigFile.getPath());
			DatabaseExtractor dbExtractor = dbExtractorFactory.getDatabaseExtractor();
			Database db = dbExtractor.extractDatabase();
			
			// Reverse engineer database
			ReverseEngineerFactory reverseEngineerFactory = new ReverseEngineerFactory(revengConfigFile.getPath());
			ReverseEngineer reverseEngineer = reverseEngineerFactory.getReverseEngineer();
			Model model = reverseEngineer.reverseEngineer(db);
			
			// Load generator configuration
			GeneratorRunnerConfigFactory generatorRunnerConfigFactory = new GeneratorRunnerConfigFactory(generatorConfigFile.getPath());
			GeneratorRunnerConfig generatorConfig = generatorRunnerConfigFactory.getGeneratorRunnerConfig();
			
			// Create GeneratorRunner
			GeneratorRunner generatorRunner = new GeneratorRunnerImpl(generatorConfig);
			
			// Generate
			GeneratorRunnerResult result = generatorRunner.generate(model, outputDirectory);
			
			// Log results
			GeneratorRunnerResultLogger.log(result, getLog());

			
		} catch (Exception e) {
			getLog().error(e);
			throw new MojoExecutionException(e.getMessage());
		}
		
		// All done. Sweet.
		getLog().info("Revgen finished.");
		
	}

}
