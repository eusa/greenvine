package net.sourceforge.greenvine.generator.runner;

import java.io.File;

import net.sourceforge.greenvine.model.api.Model;

public interface GeneratorRunner {

	public abstract GeneratorRunnerResult generate(Model model,
			File outputDirectory) throws Exception;

}