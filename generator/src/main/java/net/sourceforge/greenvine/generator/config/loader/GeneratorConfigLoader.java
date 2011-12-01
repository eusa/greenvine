package net.sourceforge.greenvine.generator.config.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import net.sourceforge.greenvine.generator.config.GeneratorConfig;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

public class GeneratorConfigLoader {
	
	/**
	 * Load the GeneratorConfig 
	 * from the generator.xml file
	 * @param configDirectory
	 * @return
	 * @throws FileNotFoundException 
	 * @throws ValidationException 
	 * @throws MarshalException 
	 */
	public GeneratorConfig getGeneratorConfig(File configFile) throws FileNotFoundException, MarshalException, ValidationException {
		
		
		GeneratorConfig config = null;
		
		
		Reader r1 = new FileReader(configFile);

		// Unmarshal the generator into the object using Castor
		config = (GeneratorConfig) GeneratorConfig
				.unmarshal(r1);
		config.validate();

		return config;
	}

}
