package net.sourceforge.greenvine.generator.runner.impl;

import java.util.Map;

import net.sourceforge.greenvine.generator.GeneratorContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class GeneratorRunnerConfigFactory { 
    
    private final GeneratorRunnerConfig generatorConfig;
    
    public GeneratorRunnerConfigFactory(String filePath) {
        
        // Set up Spring context
        ApplicationContext context = new FileSystemXmlApplicationContext(
                new String[] {filePath});
        
        // Get thread configuration
        ThreadConfig threadConfig = (ThreadConfig)context.getBean("threadConfig");
        
        // Get generators
        Map<String, GeneratorContext> generators = context.getBeansOfType(GeneratorContext.class);
        
        // Create GeneratorConfig
        this.generatorConfig = new GeneratorRunnerConfig(threadConfig, generators.values());
    }
    
    public GeneratorRunnerConfig getGeneratorRunnerConfig() {
        return this.generatorConfig;
    }

}
