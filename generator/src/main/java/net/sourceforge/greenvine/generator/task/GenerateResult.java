package net.sourceforge.greenvine.generator.task;

import net.sourceforge.greenvine.generator.Generator;

public class GenerateResult {

	private final GenerateStatus generateStatus;
	private final Class<? extends Generator> generatorClass;
	private final Exception exception;

	public GenerateResult(Class<? extends Generator> generatorClass) {
		this.generatorClass = generatorClass;
		this.generateStatus = GenerateStatus.SUCCESS;
		this.exception = null;
	}
	
	public GenerateResult(Class<? extends Generator> generatorClass, Exception e) {
		this.generatorClass = generatorClass;
		this.generateStatus = GenerateStatus.FAILURE;
		this.exception = e;
	}

	public GenerateStatus getGenerateStatus() {
		return generateStatus;
	}

	public Class<? extends Generator> getGeneratorClass() {
		return generatorClass;
	}

	public Exception getException() {
		return exception;
	}
	
	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder();
		toString.append("Generator: " + this.generatorClass);
		toString.append(", Status: " + this.generateStatus);
		toString.append(", Exception: " + exception);
		return toString.toString();
	}	

	public enum GenerateStatus {SUCCESS, FAILURE}
}
