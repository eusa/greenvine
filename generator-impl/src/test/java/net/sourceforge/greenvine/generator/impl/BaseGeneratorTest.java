package net.sourceforge.greenvine.generator.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.greenvine.generator.runner.impl.SourceConfig;
import net.sourceforge.greenvine.generator.task.GenerateResult;
import net.sourceforge.greenvine.generator.task.GeneratorTaskExecutor;
import net.sourceforge.greenvine.generator.task.MergeResult;
import net.sourceforge.greenvine.generator.task.GenerateResult.GenerateStatus;
import net.sourceforge.greenvine.generator.task.MergeResult.MergeStatus;
import net.sourceforge.greenvine.generator.template.TemplateFactory;
import net.sourceforge.greenvine.generator.template.impl.VelocityTemplateFactory;
import net.sourceforge.greenvine.model.api.Model;
import net.sourceforge.greenvine.reveng.testutils.TestModelExtractor;

import org.junit.After;
import org.junit.Before;

public abstract class BaseGeneratorTest {
	
	protected Model model;
	protected GeneratorTaskExecutor executor;
	protected TemplateFactory factory;
	protected SourceConfig sourceConfig;
	
	@Before
	public void setUp() throws Exception {
		
		// Create new template factory
		this.factory = new VelocityTemplateFactory();
		
		// Create with 2 threads to each thread group
		this.executor = new GeneratorTaskExecutor(2, 2);
		
		// Create model
		this.model = TestModelExtractor.getH2Model("../database-extractor/src/test/resources/test-schema-h2.sql");
		
		// Create source config
		this.sourceConfig = new SourceConfig();
		sourceConfig.setBasePackage("test.pack");
		sourceConfig.setDataPackage("data");
		sourceConfig.setDaoSuffix("Dao");
		
	}
	
	@After
	public void checkResults() throws Exception {
		
		// Get the results
		List<GenerateResult> generateResults = new ArrayList<GenerateResult>();
		List<MergeResult> mergeResults = new ArrayList<MergeResult>();
		this.executor.getResults(generateResults, mergeResults);
		
		// Check the GenerateResults - check no errors
		for (GenerateResult generateResult : generateResults) {
			if (generateResult.getGenerateStatus().equals(GenerateStatus.FAILURE)) {
				throw generateResult.getException();
			}
		}
		
		// Check the MergeResults - check no errors
		for (MergeResult mergeResult : mergeResults) {
			if (mergeResult.getMergeStatus().equals(MergeStatus.FAILURE)) {
				throw mergeResult.getException();
			}
		}
	}
	
	protected void createExportDirectory(String exportDirectory) {
		File expDirFile = new File(exportDirectory);
		if (!expDirFile.exists()) {
			expDirFile.mkdirs();
		}
	}

}
