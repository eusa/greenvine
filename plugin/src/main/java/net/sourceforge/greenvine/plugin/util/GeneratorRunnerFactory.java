package net.sourceforge.greenvine.plugin.util;

import net.sourceforge.greenvine.generator.runner.GeneratorRunner;

public class GeneratorRunnerFactory {

	public static GeneratorRunner getInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		Class<?> generatorRunnerClass = Thread.currentThread().getContextClassLoader().loadClass(className);
		Object generatorRunnerObject = generatorRunnerClass.newInstance();
		return (GeneratorRunner)generatorRunnerObject;
		
	}

}
