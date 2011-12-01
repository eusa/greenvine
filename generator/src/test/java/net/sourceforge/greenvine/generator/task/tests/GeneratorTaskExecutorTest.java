package net.sourceforge.greenvine.generator.task.tests;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.Assert;
import net.sourceforge.greenvine.generator.task.GenerateResult;
import net.sourceforge.greenvine.generator.task.GeneratorTaskExecutor;
import net.sourceforge.greenvine.generator.task.MergeResult;
import net.sourceforge.greenvine.generator.task.GenerateResult.GenerateStatus;
import net.sourceforge.greenvine.generator.task.MergeResult.MergeStatus;
import net.sourceforge.greenvine.model.api.Model;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.impl.ModelImpl;

import org.junit.Before;
import org.junit.Test;

public class GeneratorTaskExecutorTest {
	
	private Model model;
	private GeneratorTaskExecutor executor;
	
	
	@Before
	public void setUp() throws ModelException {
		
		// Model and TemplateContext can be shared
		this.model = new ModelImpl("test");
		
		// Create with 2 threads to each thread group
		this.executor = new GeneratorTaskExecutor(2, 2);
		
	}
	
	@Test
	public void testTaskExecution() throws Exception {
		
		// Add some Generator and Template that should succeed
		this.executor.addGeneratorTask(new MockGenerator(), model, new MockTemplate());
		this.executor.addGeneratorTask(new MockGenerator(), model, new MockTemplate());
		
		// Add a Template that should fail
		this.executor.addGeneratorTask(new MockGenerator(), model, new MockTemplate(true));
		
		// Add a Generator that should fail
		this.executor.addGeneratorTask(new MockGenerator(true), model, new MockTemplate());
		
		// Get the results
		Collection<GenerateResult> generateResults = new ArrayList<GenerateResult>();
		Collection<MergeResult> mergeResults = new ArrayList<MergeResult>();
		this.executor.getResults(generateResults, mergeResults);
		
		// Check the results - there should be 4
		Assert.assertEquals(Integer.valueOf(generateResults.size()), Integer.valueOf(4));
		int generateFailCount = 0;
		for (GenerateResult generateResult : generateResults) {
			System.out.println(generateResult);
			if (generateResult.getGenerateStatus() == GenerateStatus.FAILURE) {
				generateFailCount ++;
				Assert.assertEquals(generateResult.getException().getClass(), Exception.class);
			}
		}
		Assert.assertEquals(Integer.valueOf(generateFailCount), Integer.valueOf(1));
		
		// Check the results - there should be 3, as one Generator should not have added any tasks
		Assert.assertEquals(Integer.valueOf(mergeResults.size()), Integer.valueOf(3));
		int mergeFailCount = 0;
		for (MergeResult mergeResult : mergeResults) {
			System.out.println(mergeResult);
			if (mergeResult.getMergeStatus() == MergeStatus.FAILURE) {
				mergeFailCount ++;
				Assert.assertEquals(mergeResult.getException().getClass(), Exception.class);
			}
		}
		Assert.assertEquals(Integer.valueOf(mergeFailCount), Integer.valueOf(1));
	}
	
}
