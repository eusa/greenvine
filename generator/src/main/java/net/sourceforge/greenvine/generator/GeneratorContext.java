package net.sourceforge.greenvine.generator;

import net.sourceforge.greenvine.generator.template.TemplateFactory;

public class GeneratorContext {
    
    private Generator generator;
    
    private String templatePath;
    
    private TemplateFactory templateFactory;
    
    private String exportDirectory;

    public Generator getGenerator() {
        return generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public TemplateFactory getTemplateFactory() {
        return templateFactory;
    }

    public void setTemplateFactory(TemplateFactory templateFactory) {
        this.templateFactory = templateFactory;
    }

    public void setExportDirectory(String exportDirectory) {
        this.exportDirectory = exportDirectory;
    }

    public String getExportDirectory() {
        return exportDirectory;
    }

}
