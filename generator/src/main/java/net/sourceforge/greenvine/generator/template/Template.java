package net.sourceforge.greenvine.generator.template;

import java.io.IOException;

import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public interface Template {

	public void merge(TemplateContext context, String directory,
			String fileName) throws IOException, MethodInvocationException,
			ParseErrorException, ResourceNotFoundException, Exception;

}