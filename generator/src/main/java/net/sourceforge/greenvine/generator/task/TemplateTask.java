package net.sourceforge.greenvine.generator.task;

import java.util.concurrent.Callable;

import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;

public class TemplateTask implements Callable<MergeResult> {
	
	private Template template;
	private TemplateContext context;
	private String directory;
	private String fileName;
	
	public TemplateTask(Template template, TemplateContext context,
			String directory, String fileName) {
		super();
		this.template = template;
		this.context = context;
		this.directory = directory;
		this.fileName = fileName;
	}

	public MergeResult call() throws Exception {
		MergeResult mergeResult = null;
		try {
			this.template.merge(context, directory, fileName);
			mergeResult = new MergeResult(directory, fileName);
		} catch (Exception e) {
			mergeResult = new MergeResult(directory, fileName, e);
		}
		return mergeResult;
	}

}
