package net.sourceforge.greenvine.generator.runner;

import java.util.Collection;

import net.sourceforge.greenvine.generator.task.GenerateResult;
import net.sourceforge.greenvine.generator.task.MergeResult;

public class GeneratorRunnerResult {
	
	private final Collection<GenerateResult> generateResults;
	private final Collection<MergeResult> mergeResults;

	public GeneratorRunnerResult(Collection<GenerateResult> generateResults,
			Collection<MergeResult> mergeResults) {
		this.generateResults = generateResults;
		this.mergeResults = mergeResults;
	}

	public Collection<GenerateResult> getGenerateResults() {
		return generateResults;
	}

	public Collection<MergeResult> getMergeResults() {
		return mergeResults;
	}

}
