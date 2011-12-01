package net.sourceforge.greenvine.generator.impl.java.dao.springjpa;

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

public class SpringJpaDaoImplUnitTestGenerator implements Generator {

    private final SourceConfig sourceConfig;
    
    public SpringJpaDaoImplUnitTestGenerator(SourceConfig sourceConfig) {
        this.sourceConfig = sourceConfig;
    }
    
    public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {
    	
    	// Create utility helper class
        JavaHelper javaHelper = new JavaHelper(sourceConfig);

        // Iterate through catalogs
        for (Catalog catalog : model.getCatalogs()) {
        
            // Iterate through entities and build test cases.
            for (Entity entity :catalog.getEntities()) {
                if (!entity.getReadOnly()) {
                    // Entity type
                    JavaType entityType = javaHelper.getEntityType(entity);
                    
                 // DAO type
                    JavaType daoType = javaHelper.getDaoType(entity);
        
                    // Identity type
                    JavaType identityType = javaHelper.getUltimateNonConstrainedIdentityType(entity);
        
                    // DAO implementation type
                    JavaType daoImplType = javaHelper.getDaoImplType(entity, "springjpa");
        
                    // Generate DAO tests
                    TemplateContext context = new TemplateContext();
                    context.put("entity", entity);
                    context.put("entityType", entityType);
                    context.put("identityType", identityType);
                    context.put("daoType", daoType);
                    context.put("daoImplType", daoImplType);
                    context.put("model", model);
                    context.put("javaHelper", javaHelper);
    
                    // Get the directory path
                    String directory = javaHelper.packageToFolder(daoImplType.getPackageName());
    
                    // Merge the template
                    queue.enqueue(template, context, directory, daoImplType.getClassName()
                            + "UnitTest.java");
                }
            }
        }
    }
}
