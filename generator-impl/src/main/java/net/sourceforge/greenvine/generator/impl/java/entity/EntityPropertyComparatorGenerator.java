package net.sourceforge.greenvine.generator.impl.java.entity;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.helper.JavaHelper;
import net.sourceforge.greenvine.generator.helper.JavaType;
import net.sourceforge.greenvine.generator.helper.ReferenceJavaType;
import net.sourceforge.greenvine.generator.runner.impl.SourceConfig;
import net.sourceforge.greenvine.generator.task.TemplateTaskQueue;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;
import net.sourceforge.greenvine.model.api.Catalog;
import net.sourceforge.greenvine.model.api.ColumnField;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.Model;

public class EntityPropertyComparatorGenerator implements Generator {

   private final SourceConfig sourceConfig;
    
    public EntityPropertyComparatorGenerator(SourceConfig sourceConfig) {
        this.sourceConfig = sourceConfig;
    }
    
	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {
		
		// Create utility helper class
		JavaHelper javaHelper = new JavaHelper(sourceConfig);

		// Iterate through catalogs
		for (Catalog catalog : model.getCatalogs()) {
		    
    		for (Entity entity : catalog.getEntities()) {
    			// Class and package name for comparator
    			JavaType entityType = javaHelper.getEntityType(entity);
    			
    			// Get the identity type
    			JavaType identityType = javaHelper.getUltimateNonConstrainedIdentityType(entity);
    			
    			for (ColumnField property : entity.getSimpleProperties()) {
    			    String comparatorPackage = entityType.getPackageName() + ".comparator";
    				String comparatorName = entityType.getClassName()
    						+ javaHelper.firstToUpperCase(property.getName().toString())
    						+ "Comparator";
    				JavaType comparatorType = new ReferenceJavaType(comparatorPackage + "." + comparatorName);
    				
    				// Create the TemplateContext
    				TemplateContext context = new TemplateContext();
    				context.put("entity", entity);
    				context.put("entityType", entityType);
    				context.put("comparatorType", comparatorType);
    				context.put("propertyName", property.getName());
    				context.put("identityType", identityType);
    				context.put("javaHelper", javaHelper);
    
    				// Get the directory path
    				String directory = javaHelper
    						.packageToFolder(comparatorPackage);
    
    				// Merge the template
    				queue.enqueue(template, context, directory, comparatorName
    						+ ".java");
    				
    			}
    		}

		}
	}
}
