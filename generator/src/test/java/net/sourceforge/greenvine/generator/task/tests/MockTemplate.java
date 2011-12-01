package net.sourceforge.greenvine.generator.task.tests;

import java.io.IOException;

import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;

public class MockTemplate implements Template {

	private final boolean fail;
	
	public MockTemplate() {
		this.fail = false;
	}
	
	public MockTemplate(boolean fail) {
		this.fail = fail;
	}
	
	public void merge(TemplateContext context, String directory, String fileName)
			throws IOException, MethodInvocationException, ParseErrorException,
			ResourceNotFoundException, Exception {
		if (fail)
			throw new Exception();

	}

	public void setExportDirectory(String exportDirectory) {

	}

	public void setTemplatePath(String templatePath) {

	}

}
