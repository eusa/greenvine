package net.sourceforge.greenvine.generator.task;

import java.util.Collection;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.model.api.Model;



/**
 * Class that handles the execution 
 * of {@link GeneratorTask} and {@link TemplateTask}
 * by coordinating two {@link CompletionService}
 * that interact to decouple generation of {@link TemplateTask}
 * with the execution of {@link TemplateTask}
 * {@link Generator} implementations add tasks to
 * a {@link TemplateTaskQueue} that wraps a
 * {@link TemplateTaskCompletionService}. Thus,
 * {@link GeneratorTask} and {@link TemplateTask}
 * complete asynchronously and independently.
 *
 */
public class GeneratorTaskExecutor {
	
	
	/**
	 * {@link CompletionService} for {@link GeneratorTask}
	 * that tracks the number of submitted tasks
	 */
	private final GeneratorTaskCompletionService generatorTaskCompletionService;
	
	
	/**
	 * {@link CompletionService} for {@link TemplateTask} 
	 * that tracks the submitted task and provides
	 * an implementation of {@link TemplateTaskQueue} used by
	 * {@link Generator} objects to add TemplateTasks
	 */
	private final TemplateTaskCompletionService templateTaskCompletionService;
	
	
	/**
	 * Constructor that sets up the {@link CompletionService}
	 * allowing the number of threads for each thread pool 
	 * to be specified. This will enable fine tuning of 
	 * performance as depending on the IO/processing balance
	 * of the generations being performed more or fewer
	 * threads may be needed for each type of task
	 * 
	 * Each {@link ExecutorService} is created as
	 * a fixed-size thread pool using the static factory
	 * method in {@link Executors}
	 * 
	 * @param generatorTaskThreads size of thread pool used by {@link GeneratorTaskCompletionService}
	 * @param templateTaskThreads size of thread pool used by {@link TemplateTaskCompletionService}
	 */
	public GeneratorTaskExecutor(int generatorTaskThreads, int templateTaskThreads) {
		super();
		// Create the ExecutorService implementations and construct
		// the CompletionService implementations
		final ExecutorService generatorExecutor = Executors.newFixedThreadPool(generatorTaskThreads);
		final ExecutorService templateExecutor = Executors.newFixedThreadPool(templateTaskThreads);
		this.generatorTaskCompletionService = new GeneratorTaskCompletionService(new ExecutorCompletionService<GenerateResult>(generatorExecutor));	
		this.templateTaskCompletionService = new TemplateTaskCompletionService(new ExecutorCompletionService<MergeResult>(templateExecutor));
	}

	public void addGeneratorTask(Generator generator, Model model, Template template) throws InterruptedException, ExecutionException {
		
		// Create the GeneratorTask task and submit
		GeneratorTask generatorTask = new GeneratorTask(generator, model, template, templateTaskCompletionService);
		generatorTaskCompletionService.submit(generatorTask);
	}
		
		
	public void getResults(Collection<GenerateResult> generateResults, Collection<MergeResult> mergeResults) throws InterruptedException, ExecutionException {
		// As each GeneratorTask completes, get the GenerateResult
		for (int t = 0, n = generatorTaskCompletionService.getTaskCount(); t < n; t ++) {
			Future<GenerateResult> generateResultFuture = generatorTaskCompletionService.take();
			GenerateResult generateResult = generateResultFuture.get();
			generateResults.add(generateResult);
		}
		
		// At this point, we know that all GeneratorTasks have finished
		// Therefore, we can safely start taking the MergeResults
		// from the TemplateTaskCompletionService because
		// no further tasks will be added once the GeneratorTasks
		// have stopped.
		for (int t = 0, n = templateTaskCompletionService.getTaskCount(); t < n; t ++) {
			Future<MergeResult> mergeResultFuture = templateTaskCompletionService.take();
			MergeResult mergeResult = mergeResultFuture.get();
			mergeResults.add(mergeResult);
		}
	}

}