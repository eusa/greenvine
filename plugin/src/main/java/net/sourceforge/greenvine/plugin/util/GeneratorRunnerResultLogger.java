package net.sourceforge.greenvine.plugin.util;

import java.util.Collection;

import net.sourceforge.greenvine.generator.runner.GeneratorRunnerResult;
import net.sourceforge.greenvine.generator.task.GenerateResult;
import net.sourceforge.greenvine.generator.task.MergeResult;
import net.sourceforge.greenvine.generator.task.GenerateResult.GenerateStatus;
import net.sourceforge.greenvine.generator.task.MergeResult.MergeStatus;

import org.apache.maven.plugin.logging.Log;

public class GeneratorRunnerResultLogger {

	public static void log(GeneratorRunnerResult result, Log log) {
		
		// Log the results of the GeneratorTask executions
		logGenerateResults(result.getGenerateResults(), log);
		
		// Log the results of the TemplateTask executions
		logMergeResults(result.getMergeResults(), log);
	}
	
	private static void logMergeResults(Collection<MergeResult> mergeResults, Log log) {
		for (MergeResult mergeResult : mergeResults) {
			if (mergeResult.getMergeStatus() == MergeStatus.FAILURE) {
				log.error(mergeResult.toString(), mergeResult.getException());
			} else {
				log.info(mergeResult.toString());
			}
		}
	}

	private static void logGenerateResults(Collection<GenerateResult> generateResults, Log log) {
		for (GenerateResult generateResult : generateResults) {
			if (generateResult.getGenerateStatus() == GenerateStatus.FAILURE) {
				log.error(generateResult.toString(), generateResult.getException());
			} else {
				if (log.isInfoEnabled())
					log.info(generateResult.toString());
			}
		}
	}

}
