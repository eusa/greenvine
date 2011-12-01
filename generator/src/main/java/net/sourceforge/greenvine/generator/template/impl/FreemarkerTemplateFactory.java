package net.sourceforge.greenvine.generator.template.impl;

import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateFactory;

public class FreemarkerTemplateFactory implements TemplateFactory {

	public Template assembleTemplate(String templatePath, String exportDirectory) {
		return new FreemarkerTemplate(templatePath, exportDirectory);
	}

}
