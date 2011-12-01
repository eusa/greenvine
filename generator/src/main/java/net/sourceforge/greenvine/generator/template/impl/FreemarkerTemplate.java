package net.sourceforge.greenvine.generator.template.impl;

import java.io.File;
import java.io.Writer;

import net.sourceforge.greenvine.generator.template.TemplateContext;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.MapModel;
import freemarker.template.Configuration;

public class FreemarkerTemplate extends BaseTemplate implements net.sourceforge.greenvine.generator.template.Template {
		
	public FreemarkerTemplate(String templatePath, String exportDirectory) {
		super(templatePath, exportDirectory);
	}

	public void merge(TemplateContext context, String directory, String fileName) throws Exception {
		
		// Configure Velocity
		Configuration configuration = new Configuration();
		
		// Create template loader
		TemplateLoader templateLoader = new ClassTemplateLoader(this.getClass(), "/");
		configuration.setTemplateLoader(templateLoader);
		
		// Get a Writer
		Writer writer = getWriter(directory, fileName);
		
		// Set up the TemplateModel	
		MapModel model = new MapModel(context, new BeansWrapper());
		
		// Load velocity template
		freemarker.template.Template template = configuration.getTemplate(templatePath);
		
		// Merge the template
		template.process(model, writer);
		
		// Clean up
		writer.flush();
		writer.close();
		
	}

	protected String buildFullDirectoryPath(String directory) {
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
		return fullDirectoryPath;
	}

}
