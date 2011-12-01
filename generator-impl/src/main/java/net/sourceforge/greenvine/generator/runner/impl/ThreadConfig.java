package net.sourceforge.greenvine.generator.runner.impl;

public class ThreadConfig {
    
    private int generatorThreads;
    private int templateThreads;
    
    public int getGeneratorThreads() {
        return generatorThreads;
    }
    public void setGeneratorThreads(int generatorThreads) {
        this.generatorThreads = generatorThreads;
    }
    public int getTemplateThreads() {
        return templateThreads;
    }
    public void setTemplateThreads(int templateThreads) {
        this.templateThreads = templateThreads;
    }
    

}
