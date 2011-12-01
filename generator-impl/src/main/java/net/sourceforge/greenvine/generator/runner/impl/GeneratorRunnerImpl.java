package net.sourceforge.greenvine.generator.runner.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.GeneratorContext;
import net.sourceforge.greenvine.generator.runner.GeneratorRunner;
import net.sourceforge.greenvine.generator.runner.GeneratorRunnerResult;
import net.sourceforge.greenvine.generator.task.GenerateResult;
import net.sourceforge.greenvine.generator.task.GeneratorTaskExecutor;
import net.sourceforge.greenvine.generator.task.MergeResult;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateFactory;
import net.sourceforge.greenvine.model.api.Model;

public class GeneratorRunnerImpl implements GeneratorRunner {
    
    private final GeneratorRunnerConfig generatorRunnerConfig;
    
    public GeneratorRunnerImpl(GeneratorRunnerConfig generatorRunnerConfig) {
        this.generatorRunnerConfig = generatorRunnerConfig;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.sourceforge.greenvine.generator.runner.GeneratorRunner#generate(net
     * .sourceforge.greenvine.generator.config.GeneratorConfig, java.io.File,
     * java.io.File)
     */
    public GeneratorRunnerResult generate(Model model, File outputDirectory) throws InterruptedException,
            ExecutionException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, NoSuchMethodException,
            InvocationTargetException, FileNotFoundException {

        // Create the GeneratorTaskExecutor
        GeneratorTaskExecutor executor = new GeneratorTaskExecutor(generatorRunnerConfig
                .getGeneratorThreads(), generatorRunnerConfig.getTemplateThreads());

        // Add the Generator tasks to the executor
        for (GeneratorContext genImpl : generatorRunnerConfig.getGeneratorContexts()) {
            submitGeneratorTask(model, outputDirectory, executor, genImpl);
        }

        // Get the results from the executor
        Collection<GenerateResult> generateResults = new ArrayList<GenerateResult>();
        Collection<MergeResult> mergeResults = new ArrayList<MergeResult>();
        executor.getResults(generateResults, mergeResults);

        return new GeneratorRunnerResult(generateResults, mergeResults);

    }

    private void submitGeneratorTask(Model model, File outputDirectory,
            GeneratorTaskExecutor executor, GeneratorContext genImpl)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, NoSuchMethodException,
            InvocationTargetException, InterruptedException, ExecutionException {

       // Load the Generator class
        Generator generator = genImpl.getGenerator();

        // Load the TemplateFactory class
        TemplateFactory templateFactory = genImpl.getTemplateFactory();

        // Set up the export directory
        String exportDirectory = createExportDirectory(outputDirectory, genImpl);

        // Get template from factory
        Template template = templateFactory.assembleTemplate(genImpl
                .getTemplatePath(), exportDirectory);

        // Generate code
        executor.addGeneratorTask(generator, model, template);

    }



   
    private String createExportDirectory(File outputDirectory,
            GeneratorContext genImpl) {
        String exportDirectory = outputDirectory.getPath() + File.separatorChar
                + genImpl.getExportDirectory();
        File expDirFile = new File(exportDirectory);
        if (!expDirFile.exists()) {
            expDirFile.mkdirs();
        }
        return exportDirectory;
    }

}
