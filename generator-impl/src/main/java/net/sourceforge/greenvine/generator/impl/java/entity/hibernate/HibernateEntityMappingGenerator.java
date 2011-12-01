package net.sourceforge.greenvine.generator.impl.java.entity.hibernate;

import java.util.SortedSet;
import java.util.TreeSet;

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
import net.sourceforge.greenvine.model.api.NaturalIdentity;
import net.sourceforge.greenvine.model.api.impl.ComponentNaturalIdentityImpl;
import net.sourceforge.greenvine.model.naming.FieldName;

public class HibernateEntityMappingGenerator implements Generator {

    private final SourceConfig sourceConfig;
    
    public HibernateEntityMappingGenerator(SourceConfig sourceConfig) {
        this.sourceConfig = sourceConfig;
    }
    
	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {
		
	    // Iterate through catalogs
	    for (Catalog catalog : model.getCatalogs()) {
		    
    		// Create java utility helper class
    		JavaHelper javaHelper = new JavaHelper(sourceConfig);
    
    		// Iterate through entities and create mappings
    		for (Entity entity :  catalog.getEntities()) {
    		    
    		    // Get the entity type
    		    JavaType entityType = javaHelper.getEntityType(entity);
    		    
    		    // Get the identity type
                JavaType identityType = javaHelper.getUltimateNonConstrainedIdentityType(entity);
    		    
    		    // Get the final "non-constrained" identity
                Identity identity = javaHelper.getUltimateNonConstrainedIdentity(entity);
    			
                // Get the identity name
                FieldName identityName = identity.getName();
                
    			// Create a natural-id property group (if exists)
    			NaturalIdentity naturalId = entity.getNaturalIdentity();
    			
    			// Get the natural identity type
                JavaType naturalIdentityType = javaHelper.getNaturalIdentityType(entity);
                
    			// Get all of the field names in the natural-id
    			// so that they are not mapped twice
    			SortedSet<FieldName> naturalIdFields = new TreeSet<FieldName>();
    			if (naturalId != null && naturalId instanceof ComponentNaturalIdentityImpl) {
    			    for (FieldName field : ComponentNaturalIdentityImpl.class.cast(naturalId).getNames()) {
    			        naturalIdFields.add(field);
    			    }
    			}
    			
    			// Create TemplateContext
    			TemplateContext context = new TemplateContext();
    			context.put("entity", entity);
    			context.put("entityType", entityType);
    			context.put("identity", identity);
    			context.put("identityType", identityType);
                context.put("identityName", identityName);
    			context.put("naturalId", naturalId);
    			//context.put("naturalIdFields", naturalIdFields);
    			context.put("naturalIdentityType", naturalIdentityType);
    			context.put("javaHelper", javaHelper);
    
    			// Get the directory path
    			String directory = javaHelper.packageToFolder(entityType.getPackageName());
    
    			// Merge the template
    			queue.enqueue(template, context, directory, entityType.getClassName() + ".hbm.xml");
    		}
		}
	}

}
