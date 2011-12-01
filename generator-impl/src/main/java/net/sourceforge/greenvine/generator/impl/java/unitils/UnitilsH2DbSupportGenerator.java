package net.sourceforge.greenvine.generator.impl.java.unitils;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.task.TemplateTaskQueue;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;
import net.sourceforge.greenvine.model.api.Model;


/**
 * Until Unitils offers support for H2
 * out of the box, we need to add this
 * support class from the Unitis JIRA
 * http://jira.unitils.org/browse/UNI-79
 * @author patrick
 *
 */
public class UnitilsH2DbSupportGenerator implements Generator {

	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {
		
		// Create TemplateContext
		TemplateContext context = new TemplateContext();
		
		// Add to the template processing queue
		queue.enqueue(template, context, "org/unitils/core/dbsupport", "H2DbSupport.java");
	}

}
