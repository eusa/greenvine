package net.sourceforge.greenvine.generator.task;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;

public class TemplateTaskCompletionService implements CompletionService<MergeResult>, TemplateTaskQueue {

	/**
	 * {@link CompletionService} that is wrapped
	 * by this class that does the 
	 * actual work
	 */
	private final CompletionService<MergeResult> completionService;
	
	
	/**
	 * Counter variable to track how many tasks
	 * got submitted. It is not a count of how 
	 * many tasks are in the queue as it is not decremented
	 * on poll() or take().
	 * It is used to know when to stop taking() when
	 * iterating through to get the {@link MergeResult}
	 */
	private int taskCount = 0;
	
	/**
	 * Lock object to ensure that 
	 * incrementing and submitting
	 * occur atomically
	 */
	private final Object locker = new Object();

	/**
	 * Main constructor that takes
	 * the {@link CompletionService} to wrap
	 * as a parameter
	 * @param completionService {@link CompletionService} 
	 */
	public TemplateTaskCompletionService(CompletionService<MergeResult> completionService) {
		this.completionService = completionService;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.CompletionService#poll()
	 */
	public Future<MergeResult> poll() {
		return completionService.poll();
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.CompletionService#poll(long, java.util.concurrent.TimeUnit)
	 */
	public Future<MergeResult> poll(long timeout, TimeUnit unit)
			throws InterruptedException {
		return completionService.poll(timeout, unit);
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.CompletionService#take()
	 */
	public Future<MergeResult> take() throws InterruptedException {
		return completionService.take();
	}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.CompletionService#submit(java.util.concurrent.Callable)
	 */
	public Future<MergeResult> submit(Callable<MergeResult> task) {
		synchronized(locker) {
			Future<MergeResult> future = completionService.submit(task);
			taskCount ++;
			return future;
		}
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.CompletionService#submit(java.lang.Runnable, java.lang.Object)
	 */
	public Future<MergeResult> submit(Runnable task, MergeResult result) {
		synchronized(locker) {
			Future<MergeResult> future = completionService.submit(task, result);
			taskCount ++;
			return future;
		}
	}

	
	/* (non-Javadoc)
	 * @see net.sourceforge.greenvine.generator.task.TemplateTaskQueue#enqueue(net.sourceforge.greenvine.generator.template.Template, net.sourceforge.greenvine.generator.template.TemplateContext, java.lang.String, java.lang.String)
	 */
	public void enqueue(Template template, TemplateContext context,
			String directory, String fileName) {
		TemplateTask templateTask = new TemplateTask(template, context, directory, fileName);
		synchronized(locker) {
			completionService.submit(templateTask);
			taskCount ++;
		}
		
	}
	
	
	/**
	 * Get the current task count
	 * @return the current number of submitted tasks
	 */
	public int getTaskCount() {
		synchronized(locker) {
			return this.taskCount;
		}
	}
	
	
	
}
