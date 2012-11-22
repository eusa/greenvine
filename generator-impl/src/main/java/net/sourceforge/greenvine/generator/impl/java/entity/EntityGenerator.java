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
import net.sourceforge.greenvine.model.api.Identity;
import net.sourceforge.greenvine.model.api.Model;
import net.sourceforge.greenvine.model.naming.FieldName;

public class EntityGenerator implements Generator {

	private final SourceConfig sourceConfig;

	public EntityGenerator(SourceConfig sourceConfig) {
		this.sourceConfig = sourceConfig;
	}

	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {

		// Create utility helper class
		JavaHelper javaHelper = new JavaHelper(sourceConfig);

		for (Catalog catalog : model.getCatalogs()) {

			// Iterate through entities and generate
			for (Entity entity : catalog.getEntities()) {

				// Get the entity type
				JavaType entityType = javaHelper.getEntityType(entity);

				// Get the identity type
				JavaType identityType = javaHelper.getUltimateNonConstrainedIdentityType(entity);

				// Get the natural identity type
				JavaType naturalIdentityType = javaHelper.getNaturalIdentityType(entity);

				// Get the final "non-constrained" identity
				Identity identity = javaHelper.getUltimateNonConstrainedIdentity(entity);

				// Get the identity name
				FieldName identityName = identity.getName();

				// Create the TemplateContext
				TemplateContext context = new TemplateContext();
				context.put("entity", entity);
				context.put("entityType", entityType);
				context.put("table", entity.getTable());
				context.put("identity", identity);
				context.put("identityType", identityType);
				context.put("identityName", identityName);
				context.put("naturalIdentityType", naturalIdentityType);
				context.put("javaHelper", javaHelper);

				// Get the directory path
				String directory = javaHelper.packageToFolder(entityType.getPackageName());

				// Merge the template
				queue.enqueue(template, context, directory, entityType.getClassName() + ".java");

			}
		}
	}
}
