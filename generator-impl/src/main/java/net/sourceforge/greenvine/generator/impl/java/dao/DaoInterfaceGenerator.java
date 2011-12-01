package net.sourceforge.greenvine.generator.impl.java.dao;

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

public class DaoInterfaceGenerator implements Generator {

    private final SourceConfig sourceConfig;
    
    public DaoInterfaceGenerator(SourceConfig sourceConfig) {
        this.sourceConfig = sourceConfig;
    }
    
    public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {
		
		// Create utility helper class
		JavaHelper javaHelper = new JavaHelper(sourceConfig);

		for (Catalog catalog : model.getCatalogs()) {
    		for (Entity entity : catalog.getEntities()) {

    		    // Get the entity type
    		    JavaType entityType = javaHelper.getEntityType(entity);
    		    
    		    // Get the Dao type
    		    JavaType daoType = javaHelper.getDaoType(entity);
    			
    			// Get the identity name and type
                JavaType identityType = javaHelper.getUltimateNonConstrainedIdentityType(entity);
    
    			// Generate DAO interfaces
    			TemplateContext context = new TemplateContext();
    			context.put("entity", entity);
    			context.put("entityType", entityType);
    			context.put("daoType", daoType);
    			context.put("identityType", identityType);
    			context.put("model", model);
    			context.put("javaHelper", javaHelper);
    
    			// Get the directory path
    			String directory = javaHelper.packageToFolder(daoType.getPackageName());
    
    			// Merge the template
    			queue.enqueue(template, context, directory, daoType.getClassName() + ".java");
    		}
		}
	}
}
