package net.sourceforge.greenvine.generator.impl.java.entity.hibernate;

import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;

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
import net.sourceforge.greenvine.model.naming.FieldName;

public class HibernateEntityIntegrationTestGenerator implements Generator {

    private final SourceConfig sourceConfig;
    
    public HibernateEntityIntegrationTestGenerator(SourceConfig sourceConfig) {
        this.sourceConfig = sourceConfig;
    }
   
	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {
    	
        // Create utility helper class
        JavaHelper javaHelper = new JavaHelper(sourceConfig);

        // Iterate through catalogs
        for (Catalog catalog : model.getCatalogs()) {

            // Iterate through entities and build test cases.
            for (Entity entity : catalog.getEntities()) {
                if (!entity.getReadOnly()) {
                    
                    // Get the java type of the entity
                    JavaType entityType = javaHelper.getEntityType(entity);
    
                    // Get the identity type
                    JavaType identityType = javaHelper.getUltimateNonConstrainedIdentityType(entity);
    
                    // Get dependencies
                    Map<FieldName, Entity> dependencies = entity
                            .findDependencies();
                    Collection<? extends Entity> uniqueDependencies = entity
                            .getUniqueDependentEntities();
                    
                    // All imports for the class
                    SortedSet<JavaType> imports = javaHelper.getImportsForIntegrationTest(entity);
    
                    // Does the entity contain binary types?
                    boolean containsBinary = javaHelper.entityContainsBinaryField(entity);
                    
                    // Generate DAO tests
                    TemplateContext context = new TemplateContext();
                    context.put("catalog", catalog);
                    context.put("entity", entity);
                    context.put("entityType", entityType);
                    context.put("dependencies", dependencies);
                    context.put("uniqueDependencies", uniqueDependencies);
                    context.put("identityType", identityType);
                    context.put("model", model);
                    context.put("imports", imports);
                    context.put("containsBinary", containsBinary);
                    context.put("javaHelper", javaHelper);
    
                    // Get the directory path
                    String directory = javaHelper
                            .packageToFolder(entityType.getPackageName());
    
                    // Merge the template
                    queue.enqueue(template, context, directory, entityType.getClassName()
                            + "HibernateIntegrationTest.java");
                }
            }
        }
    }
}
