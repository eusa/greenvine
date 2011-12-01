package net.sourceforge.greenvine.generator.runner.impl;

import java.util.Collection;

import net.sourceforge.greenvine.generator.GeneratorContext;


public class GeneratorRunnerConfig {
    
    private final ThreadConfig threadConfig;
    
    private final Collection<GeneratorContext> generatorContexts;

    public GeneratorRunnerConfig(ThreadConfig threadConfig,
            Collection<GeneratorContext> generatorContexts) {
        super();
        this.threadConfig = threadConfig;
        this.generatorContexts = generatorContexts;
    }

    public Collection<GeneratorContext> getGeneratorContexts() {
        return generatorContexts;
    }

    public int getGeneratorThreads() {
        if (this.threadConfig != null) {
            return this.threadConfig.getGeneratorThreads();
        }
        return 2;
    }

    public int getTemplateThreads() {
        if (this.threadConfig != null) {
            return this.threadConfig.getTemplateThreads();
        }
        return 2;
    }

}
