package net.sourceforge.greenvine.generator.impl.java.entity;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.helper.JavaHelper;
import net.sourceforge.greenvine.generator.helper.JavaType;
import net.sourceforge.greenvine.generator.runner.impl.SourceConfig;
import net.sourceforge.greenvine.generator.task.TemplateTaskQueue;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;
import net.sourceforge.greenvine.model.api.Catalog;
import net.sourceforge.greenvine.model.api.ComponentIdentity;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.Model;

public class EntityComponentIdentityGenerator implements Generator {

    private final SourceConfig sourceConfig;
    
    public EntityComponentIdentityGenerator(SourceConfig sourceConfig) {
        this.sourceConfig = sourceConfig;
    }
    
	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {
			
		// Create utility helper class
		JavaHelper javaHelper = new JavaHelper(sourceConfig);
		
		for (Catalog catalog : model.getCatalogs()) {
        
    		// Iterate through entities and generate
    		for (Entity entity : catalog.getEntities()) {
    			
    		    ComponentIdentity componentIdentity = entity.getComponentIdentity();
    			if (componentIdentity != null) {
    			    
    			    // Get the identity name and type
    			    //JavaType identityType = javaHelper.getUltimateNonConstrainedIdentityType(entity);
    			    JavaType identityType = javaHelper.getComponentType(componentIdentity);
    			    
        			// Create the TemplateContext
        			TemplateContext context = new TemplateContext();
        			context.put("complexIdentity", componentIdentity);
        			context.put("identityType", identityType);
        			context.put("javaHelper", javaHelper);
        
        			// Get the directory path
        			String directory = javaHelper.packageToFolder(identityType.getPackageName());
        
        			// Merge the template
        			queue.enqueue(template, context, directory, identityType.getClassName() + ".java");
        			
    			} 
    		}
		}
	}
}
