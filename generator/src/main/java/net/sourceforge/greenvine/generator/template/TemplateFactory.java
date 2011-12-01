package net.sourceforge.greenvine.generator.template;

public interface TemplateFactory {
	
	public Template assembleTemplate(String templatePath, String exportDirectory);

}
