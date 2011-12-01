package net.sourceforge.greenvine.generator.template.tests;

import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;
import net.sourceforge.greenvine.generator.template.TemplateFactory;
import net.sourceforge.greenvine.generator.template.impl.FreemarkerTemplateFactory;

import org.junit.Test;

public class FreemarkerTemplateTest {

	@Test
	public void testMerge() throws Exception {
		TemplateFactory factory = new FreemarkerTemplateFactory();
		Template template = factory.assembleTemplate("test.ftl", "target");
		TemplateContext ctx = new TemplateContext();
		ctx.put("test", "test");
		ctx.put("object", new MockContextObject());
		template.merge(ctx, "template-output", "freemarker.txt");
	}
	
}
