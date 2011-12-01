package net.sourceforge.greenvine.generator.template.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BaseTemplate {

	protected final String templatePath;
	protected final String exportDirectory;

	public BaseTemplate(String templatePath, String exportDirectory) {
		super();
		this.templatePath = templatePath;
		this.exportDirectory = exportDirectory;
	}

	protected BufferedWriter getWriter(String directory, String fileName) throws IOException {
		// Create the full directory path
		String fullDirectoryPath;
		if (directory != null) {
			fullDirectoryPath = exportDirectory + File.separatorChar
					+ directory;
		} else {
			fullDirectoryPath = exportDirectory;
		}
		
		// Check the path exists
		File _dir = new File(fullDirectoryPath);
		if (!_dir.exists()) {
			_dir.mkdirs();
		}
		
		// Create a writer
		BufferedWriter writer = new BufferedWriter(new FileWriter(
				fullDirectoryPath + File.separatorChar + fileName));
		return writer;
	}

}