package net.sourceforge.greenvine.plugin;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;

public abstract class BaseMojo extends AbstractMojo {
	
	/**
	 * Ensure that the output directory
	 * supplied exists and if not 
	 * create it
	 * @param outputDirectory
	 */
	protected void checkOutputDirectory(File outputDirectory) {
		
	    if (outputDirectory == null) {
	        throw new IllegalArgumentException("Output directory is null.");
	    }

		if (!outputDirectory.exists()) {
		    outputDirectory.mkdirs();
		}
	}
	
	
	
}
