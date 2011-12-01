package net.sourceforge.greenvine.generator.template.tests;

import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;
import net.sourceforge.greenvine.generator.template.TemplateFactory;
import net.sourceforge.greenvine.generator.template.impl.VelocityTemplateFactory;

import org.junit.Test;

public class VelocityTemplateTest {

	@Test
	public void testMerge() throws Exception {
		TemplateFactory factory = new VelocityTemplateFactory();
		Template template = factory.assembleTemplate("test.vm", "target");
		TemplateContext ctx =  new TemplateContext();
		ctx.put("test", "test");
		ctx.put("object", new MockContextObject());
		template.merge(ctx, "template-output", "velocity.txt");
	}
	
}
