package net.sourceforge.greenvine.generator.impl.java.entity;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.helper.JavaHelper;
import net.sourceforge.greenvine.generator.helper.JavaType;
import net.sourceforge.greenvine.generator.runner.impl.SourceConfig;
import net.sourceforge.greenvine.generator.task.TemplateTaskQueue;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;
import net.sourceforge.greenvine.model.api.Catalog;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.Model;

public class EntityUnitTestGenerator implements Generator {

    private final SourceConfig sourceConfig;
    
    public EntityUnitTestGenerator(SourceConfig sourceConfig) {
        this.sourceConfig = sourceConfig;
    }
    
	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {
    	
        // Create utility helper class
        JavaHelper javaHelper = new JavaHelper(sourceConfig);

        // Iterate through catalogs
        for (Catalog catalog : model.getCatalogs())
        
        // Iterate through entities and build test cases.
        for (Entity entity : catalog.getEntities()) {

            JavaType entityType = javaHelper.getEntityType(entity);

            // Generate DAO tests
            TemplateContext context = new TemplateContext();
            context.put("entityType", entityType);

            // Get the directory path
            String directory = javaHelper
                    .packageToFolder(entityType.getPackageName());

            // Merge the template
            queue.enqueue(template, context, directory, entityType.getClassName()
                    + "UnitTest.java");
        }
    }


}
