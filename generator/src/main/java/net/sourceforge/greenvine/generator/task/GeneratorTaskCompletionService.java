package net.sourceforge.greenvine.generator.task;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class GeneratorTaskCompletionService implements CompletionService<GenerateResult> {

	/**
	 * {@link CompletionService} that is wrapped
	 * by this class that does the 
	 * actual work
	 */
	private final CompletionService<GenerateResult> completionService;
	
	
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
	public GeneratorTaskCompletionService(CompletionService<GenerateResult> completionService) {
		this.completionService = completionService;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.CompletionService#poll()
	 */
	public Future<GenerateResult> poll() {
		return completionService.poll();
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.CompletionService#poll(long, java.util.concurrent.TimeUnit)
	 */
	public Future<GenerateResult> poll(long timeout, TimeUnit unit)
			throws InterruptedException {
		return completionService.poll(timeout, unit);
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.CompletionService#take()
	 */
	public Future<GenerateResult> take() throws InterruptedException {
		return completionService.take();
	}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.CompletionService#submit(java.util.concurrent.Callable)
	 */
	public Future<GenerateResult> submit(Callable<GenerateResult> task) {
		synchronized(locker) {
			Future<GenerateResult> future = completionService.submit(task);
			taskCount ++;
			return future;
		}
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.CompletionService#submit(java.lang.Runnable, java.lang.Object)
	 */
	public Future<GenerateResult> submit(Runnable task, GenerateResult result) {
		synchronized(locker) {
			Future<GenerateResult> future = completionService.submit(task, result);
			taskCount ++;
			return future;
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
