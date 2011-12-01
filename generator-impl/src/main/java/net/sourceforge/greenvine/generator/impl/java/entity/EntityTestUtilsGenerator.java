package net.sourceforge.greenvine.generator.impl.java.entity;

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
import net.sourceforge.greenvine.model.api.Identity;
import net.sourceforge.greenvine.model.api.Model;
import net.sourceforge.greenvine.model.naming.FieldName;


public class EntityTestUtilsGenerator implements Generator {

    private final SourceConfig sourceConfig;
    
    public EntityTestUtilsGenerator(SourceConfig sourceConfig) {
        this.sourceConfig = sourceConfig;
    }
    
	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {
    	
        // Create utility helper class
        JavaHelper javaHelper = new JavaHelper(sourceConfig);

        // Iterate through catalogs
        for (Catalog catalog : model.getCatalogs()) {
        
            // Iterate through entities and build test cases.
            for (Entity entity : catalog.getEntities()) {
    
                // Get Java type of entity
                JavaType entityType = javaHelper.getEntityType(entity);
    
                // Get the identity type
                JavaType identityType = javaHelper.getUltimateNonConstrainedIdentityType(entity);
                
                // Get the final "non-constrained" identity
                Identity identity = javaHelper.getUltimateNonConstrainedIdentity(entity);
                
                // Get the natural identity type
                JavaType naturalIdentityType = javaHelper.getNaturalIdentityType(entity);
                
                // Get the identity name
                FieldName identityName = identity.getName();
                
                // Get dependencies
                Map<FieldName, Entity> dependencies = entity
                        .findDependencies();
                
                // Get simple property imports
                SortedSet<JavaType> imports = javaHelper.getSimplePropertyImportsForEntity(entity);
                if (entity.getComponentIdentity() != null) {
                    imports.addAll(javaHelper.getSimplePropertyImportsForComponentIdentity(entity.getComponentIdentity()));
                }
                if (entity.getComponentNaturalIdentity() != null) {
                    imports.addAll(javaHelper.getSimplePropertyImportsForComponentIdentity(entity.getComponentNaturalIdentity()));
                }
                
                // Get related imports
                SortedSet<JavaType> uniqueDependencies = javaHelper.getDependentRelatedEntityImportsForEntity(entity);
                if (entity.getComponentIdentity() != null) {
                    uniqueDependencies.addAll(javaHelper.getRelatedEntityImportsForComponentIdentity(entity.getComponentIdentity()));
                }
                
                // Generate DAO tests
                TemplateContext context = new TemplateContext();
                context.put("entity", entity);
                context.put("entityType", entityType);
                context.put("dependencies", dependencies);
                context.put("uniqueDependencies", uniqueDependencies);
                context.put("identity", identity);
                context.put("identityName", identityName);
                context.put("identityType", identityType);
                context.put("imports", imports);
                context.put("naturalIdentityType", naturalIdentityType);
                context.put("javaHelper", javaHelper);
    
                // Get the directory path
                String directory = javaHelper
                        .packageToFolder(entityType.getPackageName());
    
                // Merge the template
                queue.enqueue(template, context, directory, entityType.getClassName()
                        + "TestUtils.java");
            }
        }
    }


}
